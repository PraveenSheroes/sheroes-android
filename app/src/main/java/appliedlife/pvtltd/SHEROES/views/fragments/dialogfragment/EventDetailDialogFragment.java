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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.EventDetailPojo;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
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
    FeedDetail mFeedDetail;
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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.event_detail_dialog_fragment, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            Bundle bundle = getArguments();
            mFeedDetail = bundle.getParcelable(AppConstants.EVENT_DETAIL);
            if (null != bundle.get(AppConstants.EVENT_ID)) {
                mEventId = bundle.getLong(AppConstants.EVENT_ID);
                if (mEventId > 0) {
                    mFeedDetail = new FeedDetail();
                    mFeedDetail.setDispThirdPartyUniqueId(String.valueOf(mEventId));
                }
            }
            if (null != mFeedDetail) {
                mHomePresenter.attachView(this);
                mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new GenericRecyclerViewAdapter(getActivity(), (HomeActivity) getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getDispThirdPartyUniqueId())) {
                    Long uniqueId = Long.parseLong(mFeedDetail.getDispThirdPartyUniqueId());
                    mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.FEED_EVENT, 1, uniqueId));
                    try {
                        feedDetailCard = (FeedDetail) mFeedDetail.clone();
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
            Glide.with(this)
                    .load(feedDetail.getImageUrl()).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            ivEventDetail.setImageBitmap(resource);
                            Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    ((HomeActivity) getActivity()).supportStartPostponedEnterTransition();
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
            mFeedDetail = feedDetail;
            feedDetail.setItemPosition(mFeedDetail.getItemPosition());
            setImageBackground(feedDetail);
            eventDetailPojo.setFeedDetail(feedDetail);
            eventList.add(eventDetailPojo);
            mAdapter.setSheroesGenericListData(eventList);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void eventInterestedListData(FeedDetail feedDetail) {
        mFeedDetail.setReactionValue(feedDetail.getReactionValue());
        if (mFeedDetail.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            mHomePresenter.getUnLikesFromPresenter(mAppUtils.unLikeRequestBuilder(mFeedDetail.getEntityOrParticipantId()));
        } else {
            mHomePresenter.getLikesFromPresenter(mAppUtils.likeRequestBuilder(mFeedDetail.getEntityOrParticipantId(), AppConstants.EVENT_CONSTANT));
        }
    }

    public void eventGoingListData(FeedDetail feedDetail) {
        mFeedDetail.setBookmarked(feedDetail.isBookmarked());
        mHomePresenter.addBookMarkFromPresenter(mAppUtils.bookMarkRequestBuilder(mFeedDetail.getEntityOrParticipantId()), mFeedDetail.isBookmarked());
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
        if (null != mFeedDetail) {
            if (baseResponse instanceof BookmarkResponsePojo) {
                switch (baseResponse.getStatus()) {
                    case AppConstants.SUCCESS:
                        isOperationPerformed = true;
                        feedDetailCard.setBookmarked(mFeedDetail.isBookmarked());
                        eventDetailPojo.setFeedDetail(mFeedDetail);
                        eventList.clear();
                        eventList.add(eventDetailPojo);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case AppConstants.FAILED:
                        if (!mFeedDetail.isBookmarked()) {
                            mFeedDetail.setBookmarked(true);
                            eventDetailPojo.setFeedDetail(mFeedDetail);
                        } else {
                            mFeedDetail.setBookmarked(false);
                            eventDetailPojo.setFeedDetail(mFeedDetail);
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
        if (StringUtil.isNotNullOrEmptyString(baseResponse.getStatus()) && baseResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS) && null != mFeedDetail) {
            isOperationPerformed = true;
            feedDetailCard.setReactionValue(mFeedDetail.getReactionValue());
            eventDetailPojo.setFeedDetail(mFeedDetail);
            eventList.clear();
            eventList.add(eventDetailPojo);
            mAdapter.notifyDataSetChanged();

        } else {
            if (mFeedDetail.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
                mFeedDetail.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() - AppConstants.ONE_CONSTANT);
            } else {
                mFeedDetail.setReactionValue(AppConstants.EVENT_CONSTANT);
                mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
            }
            eventDetailPojo.setFeedDetail(mFeedDetail);
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
                if (isOperationPerformed) {
                    ((HomeActivity) getActivity()).refreshHomeFragment(feedDetailCard);
                } else {
                    dismissAllowingStateLoss();//dismiss dialog on back button press
                }
                dismiss();
            }
        };
    }

    @OnClick(R.id.tv_event_detail_back)
    public void onEventDetailBack() {
        if (isOperationPerformed) {
            ((HomeActivity) getActivity()).refreshHomeFragment(feedDetailCard);
        }
        dismiss();
    }
}
