package appliedlife.pvtltd.SHEROES.presenters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.contactdetail.UserContactDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
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

/**
 * Created by Praveen on 14/02/18.
 */

public class InviteFriendViewPresenterImp extends BasePresenter<IInviteFriendView> {
    SheroesAppServiceApi sheroesAppServiceApi;

    //region Constructor

    @Inject
    public InviteFriendViewPresenterImp(SheroesAppServiceApi sheroesAppServiceApi) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
    }

    //endregion

    public void getContactsFromMobile(final Context context) {
        getMvpView().startProgressBar();
        getPhoneContacts(context).compose(this.<List<UserContactDetail>>bindToLifecycle()).subscribe(new DisposableObserver<List<UserContactDetail>>() {
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

    public void fetchSuggestedFriends(CommentReactionRequestPojo commentRequestBuilder) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_BOOKMARK_UNBOOKMARK);
            return;
        }
        getMvpView().startProgressBar();
        getFriendList(commentRequestBuilder)
                .compose(this.<CommentReactionResponsePojo>bindToLifecycle())
                .subscribe(new DisposableObserver<CommentReactionResponsePojo>() {
                    @Override
                    public void onComplete() {
                        getMvpView().stopProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_GENERIC_ERROR), ERROR_COMMENT_REACTION);

                    }

                    @Override
                    public void onNext(CommentReactionResponsePojo commentResponsePojo) {
                        getMvpView().stopProgressBar();
                    }
                });

    }

    private Observable<CommentReactionResponsePojo> getFriendList(CommentReactionRequestPojo commentReactionRequestPojo) {
        return sheroesAppServiceApi.getFriendsFromApi(commentReactionRequestPojo)
                .map(new Function<CommentReactionResponsePojo, CommentReactionResponsePojo>() {
                    @Override
                    public CommentReactionResponsePojo apply(CommentReactionResponsePojo commentReactionResponsePojo) {
                        return commentReactionResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
