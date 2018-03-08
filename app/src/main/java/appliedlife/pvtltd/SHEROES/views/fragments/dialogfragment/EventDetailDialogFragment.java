package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.EventSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.EventDetailPojo;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_LIKE_UNLIKE;

/**
 * Created by Praveen_Singh on 16-06-2017.
 */

public class EventDetailDialogFragment extends BaseDialogFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(EventDetailDialogFragment.class);
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.pb_event_detail_progress_bar)
    ProgressBar mProgressBar;
    UserPostSolrObj mUserPostObj;
    @Inject
    HomePresenter mHomePresenter;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    @Bind(R.id.rv_event_detail_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.iv_event_detail)
    ImageView ivEventDetail;
    private EventDetailPojo mEventDetailPojo;
    private long mEventId;
    private ArrayList<EventDetailPojo> eventList;
    private EventDetailPojo eventDetailPojo;
    private boolean isOperationPerformed;
    private FeedDetail feedDetailCard;
    private boolean isFromCommunityScreen;
    private EventSolrObj mEventSolrObj;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.event_detail_dialog_fragment, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            Bundle bundle = getArguments();
            isFromCommunityScreen = getArguments().getBoolean(AppConstants.IS_FROM_COMMUNITY_SCREEN, false);
            mUserPostObj = Parcels.unwrap(bundle.getParcelable(AppConstants.EVENT_DETAIL));
            if (null != bundle.get(AppConstants.EVENT_ID)) {
                mEventId = bundle.getLong(AppConstants.EVENT_ID);
                if (mEventId > 0) {
                    mUserPostObj = new UserPostSolrObj();
                    mUserPostObj.setDispThirdPartyUniqueId(String.valueOf(mEventId));
                }
            }
            if (null != mUserPostObj) {
                mHomePresenter.attachView(this);
                mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                if(isFromCommunityScreen){
                    mAdapter = new GenericRecyclerViewAdapter(getActivity(), (CommunityDetailActivity) getActivity());
                }else {
                    mAdapter = new GenericRecyclerViewAdapter(getActivity(), (HomeActivity) getActivity());
                }
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                if (StringUtil.isNotNullOrEmptyString(mUserPostObj.getDispThirdPartyUniqueId())) {
                    Long uniqueId = Long.parseLong(mUserPostObj.getDispThirdPartyUniqueId());
                    mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.FEED_EVENT, 1, uniqueId));
                    try {
                        feedDetailCard = (FeedDetail) mUserPostObj.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return view;
    }

    private void setImageBackground(FeedDetail feedDetail) {
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getImageUrl())) {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.color.photo_placeholder)
                    .error(R.color.photo_placeholder)
                    .priority(Priority.HIGH)
                    .skipMemoryCache(true);
            Glide.with(this)
                    .asBitmap()
                    .load(feedDetail.getImageUrl())
                    .apply(requestOptions)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            ivEventDetail.setImageBitmap(resource);
                            Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    if (isFromCommunityScreen) {
                                        ((CommunityDetailActivity) getActivity()).supportStartPostponedEnterTransition();
                                    } else {
                                        ((HomeActivity) getActivity()).supportStartPostponedEnterTransition();
                                    }
                                }
                                });
                        }
                    });
        }
    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
        if (StringUtil.isNotEmptyCollection(feedDetailList) && mAdapter != null) {
            eventList = new ArrayList<>();
            eventDetailPojo = new EventDetailPojo();
            eventDetailPojo.setId(AppConstants.ONE_CONSTANT);
            FeedDetail feedDetail = feedDetailList.get(0);
            mEventSolrObj = (EventSolrObj) feedDetail;
            feedDetail.setItemPosition(mEventSolrObj.getItemPosition());
            setImageBackground(feedDetail);
            eventDetailPojo.setFeedDetail(feedDetail);
            eventList.add(eventDetailPojo);
            mAdapter.setSheroesGenericListData(eventList);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void eventInterestedListData(FeedDetail feedDetail) {
        mEventSolrObj.setReactionValue(feedDetail.getReactionValue());
        if (mEventSolrObj.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            mHomePresenter.getUnLikesFromPresenter(mAppUtils.unLikeRequestBuilder(mEventSolrObj.getEntityOrParticipantId()));
        } else {
            mHomePresenter.getLikesFromPresenter(mAppUtils.likeRequestBuilder(mEventSolrObj.getEntityOrParticipantId(), AppConstants.EVENT_CONSTANT));
        }
    }

    public void eventGoingListData(FeedDetail feedDetail) {
        mEventSolrObj.setBookmarked(feedDetail.isBookmarked());
        mHomePresenter.addBookMarkFromPresenter(mAppUtils.bookMarkRequestBuilder(mEventSolrObj.getEntityOrParticipantId()), mUserPostObj.isBookmarked());
    }


    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (feedParticipationEnum) {
            case LIKE_UNLIKE:
                interested(baseResponse);
                break;
            case BOOKMARK_UNBOOKMARK:
                going(baseResponse);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + feedParticipationEnum);
        }
    }

    protected void going(BaseResponse baseResponse) {
        if (null != mEventSolrObj) {
            if (baseResponse instanceof BookmarkResponsePojo) {
                switch (baseResponse.getStatus()) {
                    case AppConstants.SUCCESS:
                        isOperationPerformed = true;
                        feedDetailCard.setBookmarked(mEventSolrObj.isBookmarked());
                        eventDetailPojo.setFeedDetail(mEventSolrObj);
                        eventList.clear();
                        eventList.add(eventDetailPojo);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case AppConstants.FAILED:
                        if (!mEventSolrObj.isBookmarked()) {
                            mEventSolrObj.setBookmarked(true);
                            eventDetailPojo.setFeedDetail(mEventSolrObj);
                        } else {
                            mEventSolrObj.setBookmarked(false);
                            eventDetailPojo.setFeedDetail(mEventSolrObj);
                        }
                        eventList.clear();
                        eventList.add(eventDetailPojo);
                        mAdapter.notifyDataSetChanged();
                        showError(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_BOOKMARK_UNBOOKMARK);
                        break;
                    default:
                        showError(getString(R.string.ID_GENERIC_ERROR), ERROR_BOOKMARK_UNBOOKMARK);
                }
            }
        }
    }

    protected void interested(BaseResponse baseResponse) {
        if (StringUtil.isNotNullOrEmptyString(baseResponse.getStatus()) && baseResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS) && null != mUserPostObj) {
            isOperationPerformed = true;
            feedDetailCard.setReactionValue(mEventSolrObj.getReactionValue());
            eventDetailPojo.setFeedDetail(mEventSolrObj);
            eventList.clear();
            eventList.add(eventDetailPojo);
            mAdapter.notifyDataSetChanged();

        } else {
            if (mEventSolrObj.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
                mEventSolrObj.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                mEventSolrObj.setNoOfLikes(mEventSolrObj.getNoOfLikes() - AppConstants.ONE_CONSTANT);
            } else {
                mEventSolrObj.setReactionValue(AppConstants.EVENT_CONSTANT);
                mEventSolrObj.setNoOfLikes(mEventSolrObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
            }
            eventDetailPojo.setFeedDetail(mEventSolrObj);
            eventList.clear();
            eventList.add(eventDetailPojo);
            mAdapter.notifyDataSetChanged();
        }
        showError(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_LIKE_UNLIKE);
    }

    @Override
    public void startProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.bringToFront();
        }
    }

    @Override
    public void stopProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth) {
            @Override
            public void onBackPressed() {
                dismiss();
            }
        };
    }

    @OnClick(R.id.tv_event_detail_back)
    public void onEventDetailBack() {
        dismiss();
    }
}
