package appliedlife.pvtltd.SHEROES.presenters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.ChampionFollowedResponse;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.AllContactListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.ContactListSyncRequest;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.UpdateInviteUrlRequest;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.UpdateInviteUrlResponse;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.UserContactDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IInviteFriendView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMENT_REACTION;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.FOLLOW_UNFOLLOW;

/**
 * Created by Praveen on 14/02/18.
 */

public class InviteFriendViewPresenterImp extends BasePresenter<IInviteFriendView> {
    public static final int NORMAL_REQUEST = 0;
    public static final int LOAD_MORE_REQUEST = 1;
    public static final int END_REQUEST = 2;

    private SheroesAppServiceApi mSheroesAppServiceApi;
    private SheroesApplication mSheroesApplication;
    private String mEndpointUrl;
    private String mNextToken = "";
    private String mNextTokenForSuggested = "";
    private boolean mIsContactLoading;
    private boolean mIsSuggestedLoading;
    private int mContactListState;
    private List<UserSolrObj> mUserDetalList = new ArrayList<>();
    private List<UserContactDetail> mContactDetailList = new ArrayList<>();
    @Inject
    Preference<LoginResponse> mUserPreference;
    //region Constructor

    @Inject
    public InviteFriendViewPresenterImp(SheroesAppServiceApi mSheroesApplication, SheroesApplication sheroesApplication) {
        this.mSheroesAppServiceApi = mSheroesApplication;
        this.mSheroesApplication = sheroesApplication;

    }

    //endregion

    public void getContactsFromMobile(final Context context) {
        getMvpView().startProgressBar();
        getPhoneContacts(context).compose(this.<List<UserContactDetail>>bindToLifecycle()).compose(this.<List<UserContactDetail>>bindToLifecycle()).subscribe(new DisposableObserver<List<UserContactDetail>>() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                Crashlytics.getInstance().core.logException(e);
            }

            @Override
            public void onNext(List<UserContactDetail> userContactDetails) {
                getMvpView().stopProgressBar();
                getMvpView().showContacts(userContactDetails);


            }
        });

    }

    private Observable<List<UserContactDetail>> getPhoneContacts(final Context context) {

        return Observable.create(new ObservableOnSubscribe<List<UserContactDetail>>() {
            @Override
            public void subscribe(ObservableEmitter<List<UserContactDetail>> observableEmitter) throws Exception {
                final Set<UserContactDetail> userContactDetailHashSet = new TreeSet<>(new Comparator<UserContactDetail>() {
                    @Override
                    public int compare(UserContactDetail userContactObj1, UserContactDetail userContactObj2) {
                        if (userContactObj2.getPhoneNumber().get(0).equalsIgnoreCase(userContactObj1.getPhoneNumber().get(0))) {
                            return 0;
                        }
                        return userContactObj1.getName().compareTo(userContactObj2.getName());
                    }
                });
                UserContactDetail userContactDetail = null;
                try {
                    Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                    Cursor cursor = context.getContentResolver().query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,}, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
                    if (cursor != null && cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        while (!cursor.isAfterLast()) {
                            String contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            userContactDetail = new UserContactDetail();
                            userContactDetail.setName(contactName);
                            List<String> phone = new ArrayList<>();
                            phone.add(contactNumber);
                            userContactDetail.setPhoneNumber(phone);
                            userContactDetailHashSet.add(userContactDetail);
                            cursor.moveToNext();
                        }
                        cursor.close();
                    }
                } catch (Exception e) {
                    Crashlytics.getInstance().core.logException(e);
                }
                observableEmitter.onNext(new ArrayList<>(userContactDetailHashSet));
            }
        });
    }

    public void fetchSuggestedUserDetailFromServer(final int feedState) {
        LogUtils.info("suggestd", "######### Suggested detail");
        if (mIsSuggestedLoading) {
            return;
        }
        // only load more requests should be disabled when end of feed is reached
        if (feedState == LOAD_MORE_REQUEST) {
            if (mContactListState != END_REQUEST) {
                mContactListState = feedState;
            }
        } else {
            mContactListState = feedState;
        }

        switch (mContactListState) {
            case NORMAL_REQUEST:
                mNextTokenForSuggested = null;
                getMvpView().startProgressBar();
                break;
            case LOAD_MORE_REQUEST:
                break;
            case END_REQUEST:
                getMvpView().startProgressBar();
                return;
        }
        mIsSuggestedLoading = true;

        ContactListSyncRequest contactListSyncRequest = new ContactListSyncRequest();
        contactListSyncRequest.setNextToken(mNextTokenForSuggested);

        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getUserDetailListFromServer(contactListSyncRequest, mEndpointUrl)
                .compose(this.<AllContactListResponse>bindToLifecycle())
        .subscribe(new DisposableObserver<AllContactListResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                mIsSuggestedLoading = false;
                getMvpView().stopProgressBar();
                Crashlytics.getInstance().core.logException(e);
            }

            @Override
            public void onNext(AllContactListResponse allContactListResponse) {
                mIsSuggestedLoading = false;
                getMvpView().stopProgressBar();
                if (allContactListResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                    //User detail means :- Sheroes user
                    List<UserSolrObj> userSolrObjList = allContactListResponse.getSheroesContacts();
                    if(StringUtil.isNotEmptyCollection(userSolrObjList)) {
                        mNextTokenForSuggested = allContactListResponse.getNextToken();
                        switch (mContactListState) {
                            case NORMAL_REQUEST:
                                getMvpView().stopProgressBar();
                                mUserDetalList = userSolrObjList;
                                getMvpView().setContactUserListEnded(false);
                                List<UserSolrObj> userSolrObjs = new ArrayList<>(mUserDetalList);
                                getMvpView().showUserDetail(userSolrObjs);
                                break;
                            case LOAD_MORE_REQUEST:
                                // append in case of load more
                                if (!CommonUtil.isEmpty(userSolrObjList)) {
                                    mUserDetalList.addAll(userSolrObjList);
                                    getMvpView().addAllUserData(userSolrObjList);
                                } else {
                                    getMvpView().setContactUserListEnded(true);
                                }
                                break;
                        }
                    }else if(!StringUtil.isNotEmptyCollection(mUserDetalList))
                    {
                        getMvpView().stopProgressBar();
                        getMvpView().showUserDetail(userSolrObjList);
                    }

                } else {
                    if (allContactListResponse.getStatus().equals(AppConstants.FAILED)) { //TODO -chk with ujjwal
                        getMvpView().setContactUserListEnded(true);
                    } else if (!CommonUtil.isEmpty(mUserDetalList) && mUserDetalList.size() < 5) {
                        getMvpView().setContactUserListEnded(true);
                    }
                    getMvpView().showUserDetail(mUserDetalList);

                }

            }
        });

    }

    public void fetchUserDetailFromServer(final int feedState) {
        LogUtils.info("userdetail", "######### User detail");
        if (mIsContactLoading) {
            return;
        }
        // only load more requests should be disabled when end of feed is reached
        if (feedState == LOAD_MORE_REQUEST) {
            if (mContactListState != END_REQUEST) {
                mContactListState = feedState;
            }
        } else {
            mContactListState = feedState;
        }

        switch (mContactListState) {
            case NORMAL_REQUEST:
                mNextToken = null;
                getMvpView().startProgressBar();
                break;
            case LOAD_MORE_REQUEST:
                break;
            case END_REQUEST:
                getMvpView().startProgressBar();
                return;
        }
        mIsContactLoading = true;

        ContactListSyncRequest contactListSyncRequest = new ContactListSyncRequest();
        contactListSyncRequest.setNextToken(mNextToken);

        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_FEED_RESPONSE);
            return;
        }
        getUserDetailListFromServer(contactListSyncRequest, mEndpointUrl)
                .compose(this.<AllContactListResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<AllContactListResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                mIsContactLoading = false;
                getMvpView().stopProgressBar();
                Crashlytics.getInstance().core.logException(e);
            }

            @Override
            public void onNext(AllContactListResponse allContactListResponse) {
                mIsContactLoading = false;
                getMvpView().stopProgressBar();
                if (allContactListResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {

                    //Contact detail :- User personnel contacts

                    List<UserContactDetail> userContactDetailList = allContactListResponse.getNonSheroesContacts();
                    if (StringUtil.isNotEmptyCollection(userContactDetailList)) {
                        mNextToken = allContactListResponse.getNextToken();
                        switch (mContactListState) {
                            case NORMAL_REQUEST:
                                getMvpView().stopProgressBar();
                                mContactDetailList = userContactDetailList;
                                getMvpView().setContactUserListEnded(false);
                                List<UserContactDetail> contactDetails = new ArrayList<>(mContactDetailList);
                                getMvpView().showContacts(contactDetails);
                                break;
                            case LOAD_MORE_REQUEST:
                                // append in case of load more
                                if (!CommonUtil.isEmpty(userContactDetailList)) {
                                    mContactDetailList.addAll(userContactDetailList);
                                    getMvpView().addAllUserContactData(userContactDetailList);
                                } else {
                                    getMvpView().setContactUserListEnded(true);
                                }
                                break;
                        }
                    }
                    else if(!StringUtil.isNotEmptyCollection(mContactDetailList))
                    {
                        getMvpView().stopProgressBar();
                        getMvpView().showContacts(userContactDetailList);
                    }

                } else {
                    if (allContactListResponse.getStatus().equals(AppConstants.FAILED)) { //TODO -chk with ujjwal
                        getMvpView().setContactUserListEnded(true);
                    } else if (!CommonUtil.isEmpty(mContactDetailList) && mContactDetailList.size() < 5) {
                        getMvpView().setContactUserListEnded(true);
                    }
                    getMvpView().showContacts(mContactDetailList);
                }

            }
        });

    }

    public boolean isSuggestedLoading() {
        return mIsSuggestedLoading;
    }

    public boolean isContactLoading() {
        return mIsContactLoading;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.mEndpointUrl = endpointUrl;
    }

    public Observable<AllContactListResponse> getUserDetailListFromServer(ContactListSyncRequest contactListSyncRequest, String endpoint) {
        return mSheroesAppServiceApi.getUserDetailList(endpoint, contactListSyncRequest)
                .map(new Function<AllContactListResponse, AllContactListResponse>() {
                    @Override
                    public AllContactListResponse apply(AllContactListResponse allContactListResponse) {
                        return allContactListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void syncFriendsToServer(List<UserContactDetail> userContactDetailList) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_BOOKMARK_UNBOOKMARK);
            return;
        }
        //Set user contact list to request
        ContactListSyncRequest contactListSyncRequest = new ContactListSyncRequest();
        contactListSyncRequest.setUsersContactList(userContactDetailList);

        getMvpView().startProgressBar();
        syncFriendListToServer(contactListSyncRequest)
                .compose(this.<AllContactListResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<AllContactListResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), ERROR_COMMENT_REACTION);

                    }

                    @Override
                    public void onNext(AllContactListResponse allContactListResponse) {
                        getMvpView().stopProgressBar();
                        if (allContactListResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            getMvpView().contactsFromServerAfterSyncFromPhoneData(allContactListResponse);
                        }
                    }
                });

    }

    private Observable<AllContactListResponse> syncFriendListToServer(ContactListSyncRequest contactListSyncRequest) {
        return mSheroesAppServiceApi.getAllFriendsInSyncResponseFromApi(contactListSyncRequest)
                .map(new Function<AllContactListResponse, AllContactListResponse>() {
                    @Override
                    public AllContactListResponse apply(AllContactListResponse allContactListResponse) {
                        return allContactListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getFollowFromPresenter(PublicProfileListRequest publicProfileListRequest, final UserSolrObj userSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        getFollowFromModel(publicProfileListRequest)
                .compose(this.<ChampionFollowedResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<ChampionFollowedResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), FOLLOW_UNFOLLOW);
                        userSolrObj.setSolrIgnoreIsMentorFollowed(false);
                    }

                    @Override
                    public void onNext(ChampionFollowedResponse championFollowedResponse) {
                        getMvpView().stopProgressBar();
                        if (championFollowedResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            userSolrObj.setFollowerCount(userSolrObj.getFollowerCount() + 1);
                            userSolrObj.setSolrIgnoreIsUserFollowed(true);
                            userSolrObj.setSolrIgnoreIsMentorFollowed(true);
                        } else {
                            userSolrObj.setSolrIgnoreIsUserFollowed(false);
                            userSolrObj.setSolrIgnoreIsMentorFollowed(false);
                        }
                        getMvpView().getFollowUnfollowResponse(userSolrObj);
                    }
                });

    }

    public void getUnFollowFromPresenter(PublicProfileListRequest publicProfileListRequest, final UserSolrObj userSolrObj) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        getUnFollowFromModel(publicProfileListRequest)
                .compose(this.<ChampionFollowedResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<ChampionFollowedResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), FOLLOW_UNFOLLOW);
                    }

                    @Override
                    public void onNext(ChampionFollowedResponse championFollowedResponse) {
                        getMvpView().stopProgressBar();
                        if (championFollowedResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                            if (userSolrObj.getFollowerCount() > 0) {
                                userSolrObj.setFollowerCount(userSolrObj.getFollowerCount() - 1);
                            }
                            userSolrObj.setSolrIgnoreIsMentorFollowed(false);
                            userSolrObj.setSolrIgnoreIsUserFollowed(false);
                        } else {
                            userSolrObj.setSolrIgnoreIsUserFollowed(true);
                            userSolrObj.setSolrIgnoreIsMentorFollowed(true);
                        }
                        getMvpView().getFollowUnfollowResponse(userSolrObj);
                    }
                });

    }


    private Observable<ChampionFollowedResponse> getFollowFromModel(PublicProfileListRequest publicProfileListRequest) {
        return mSheroesAppServiceApi.getMentorFollowFromApi(publicProfileListRequest)
                .map(new Function<ChampionFollowedResponse, ChampionFollowedResponse>() {
                    @Override
                    public ChampionFollowedResponse apply(ChampionFollowedResponse championFollowedResponse) {
                        return championFollowedResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<ChampionFollowedResponse> getUnFollowFromModel(PublicProfileListRequest publicProfileListRequest) {
        return mSheroesAppServiceApi.getMentorUnFollowFromApi(publicProfileListRequest)
                .map(new Function<ChampionFollowedResponse, ChampionFollowedResponse>() {
                    @Override
                    public ChampionFollowedResponse apply(ChampionFollowedResponse championFollowedResponse) {
                        return championFollowedResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void updateInviteUrlFromPresenter(String inviteAppUrl) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, FOLLOW_UNFOLLOW);
            return;
        }
        getMvpView().startProgressBar();
        final UpdateInviteUrlRequest updateInviteUrlRequest = new UpdateInviteUrlRequest();
        updateInviteUrlRequest.setAppInviteUrl(inviteAppUrl);
        getUpdatedInviteAppUrlFromModel(updateInviteUrlRequest)
                .compose(this.<UpdateInviteUrlResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<UpdateInviteUrlResponse>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), FOLLOW_UNFOLLOW);
                    }

                    @Override
                    public void onNext(UpdateInviteUrlResponse updateInviteUrlResponse) {
                        getMvpView().stopProgressBar();
                        if (updateInviteUrlResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS) && StringUtil.isNotNullOrEmptyString(updateInviteUrlResponse.getUpdatedAppInviteUrl())) {
                            if (null != mUserPreference && mUserPreference.isSet()) {
                                LoginResponse loginResponse = mUserPreference.get();
                                UserSummary userSummary = mUserPreference.get().getUserSummary();
                                userSummary.setAppShareUrl(updateInviteUrlResponse.getUpdatedAppInviteUrl());
                                mUserPreference.set(loginResponse);
                            }
                        }
                    }
                });

    }

    private Observable<UpdateInviteUrlResponse> getUpdatedInviteAppUrlFromModel(UpdateInviteUrlRequest updateInviteUrlRequest) {
        return mSheroesAppServiceApi.updateInviteUrl(updateInviteUrlRequest)
                .map(new Function<UpdateInviteUrlResponse, UpdateInviteUrlResponse>() {
                    @Override
                    public UpdateInviteUrlResponse apply(UpdateInviteUrlResponse updateInviteUrlResponse) {
                        return updateInviteUrlResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
