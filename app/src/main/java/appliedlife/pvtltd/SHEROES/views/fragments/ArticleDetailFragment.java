package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleDetailPojo;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_LIKE_UNLIKE;

/**
 * Created by Praveen_Singh on 08-02-2017.
 */

public class ArticleDetailFragment extends BaseFragment {
    private final String TAG = LogUtils.makeLogTag(ArticleDetailFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_article_detail_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_article_progress_bar)
    ProgressBar mProgressBar;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private FeedDetail mFeedDetail;
    private FragmentListRefreshData mFragmentListRefreshData;
    @Inject
    AppUtils mAppUtils;
    private int mPosition;
    private int mPressedEmoji;
    boolean mListLoad = true;
    private List<ArticleDetailPojo> articleList;
    private ArticleDetailPojo articleDetailPojo;

    public static ArticleDetailFragment createInstance(FeedDetail feedDetail) {
        ArticleDetailFragment articleDetailFragment = new ArticleDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.ARTICLE_DETAIL, feedDetail);
        articleDetailFragment.setArguments(bundle);
        return articleDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            mFeedDetail = getArguments().getParcelable(AppConstants.ARTICLE_DETAIL);
        }
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.EMPTY_STRING, mFeedDetail.getIdOfEntityOrParticipant());
        mHomePresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (ArticleDetailActivity) getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        super.setAllInitializationForFeeds(mFragmentListRefreshData, mAdapter, mLayoutManager, mFeedDetail, mRecyclerView, mPosition, mPressedEmoji, mListLoad, mHomePresenter, mAppUtils, mProgressBar);
        mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.FEED_ARTICLE, mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getIdFeedDetail()));
        return view;
    }
    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
        if (StringUtil.isNotEmptyCollection(feedDetailList) && mAdapter != null) {
            articleList = new ArrayList<>();
            articleDetailPojo = new ArticleDetailPojo();
            articleDetailPojo.setId(AppConstants.ONE_CONSTANT);
            ((ArticleDetailActivity) getActivity()).setBackGroundImage(feedDetailList.get(0));
            articleDetailPojo.setFeedDetail(feedDetailList.get(0));
            articleList.add(articleDetailPojo);
            mAdapter.setSheroesGenericListData(articleList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (feedParticipationEnum) {
            case LIKE_UNLIKE:
                likeSuccess(baseResponse);
                break;
            case BOOKMARK_UNBOOKMARK:
                articleBookMarkSuccess(baseResponse);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + feedParticipationEnum);
        }
    }


    protected void articleBookMarkSuccess(BaseResponse baseResponse) {
        if (null != mFeedDetail) {
            if (baseResponse instanceof BookmarkResponsePojo) {
                switch (baseResponse.getStatus()) {
                    case AppConstants.SUCCESS:
                        ((ArticleDetailActivity) getActivity()).mTvArticleDetailBookmark.setEnabled(true);
                        break;
                    case AppConstants.FAILED:
                        if (!mFeedDetail.isBookmarked()) {
                            mFeedDetail.setBookmarked(true);
                            articleDetailPojo.setFeedDetail(mFeedDetail);
                        } else {
                            mFeedDetail.setBookmarked(false);
                            articleDetailPojo.setFeedDetail(mFeedDetail);
                        }
                        articleList.clear();
                        articleList.add(articleDetailPojo);
                        mAdapter.notifyDataSetChanged();
                        ((ArticleDetailActivity) getActivity()).onBookmarkClick(mFeedDetail, AppConstants.ONE_CONSTANT);
                        showError(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_BOOKMARK_UNBOOKMARK);
                        break;
                    default:
                        showError(getString(R.string.ID_GENERIC_ERROR), ERROR_BOOKMARK_UNBOOKMARK);
                }
            }
        }
    }

    protected void likeSuccess(BaseResponse baseResponse) {

        if (StringUtil.isNotNullOrEmptyString(baseResponse.getStatus()) && baseResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS) && null != mFeedDetail) {
            if (mFeedDetail.isLongPress()) {
                if (mFeedDetail.getReactionValue() == AppConstants.NO_REACTION_CONSTANT) {
                    mFeedDetail.setReactionValue(mPressedEmoji);
                    mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                } else {
                    mFeedDetail.setReactionValue(mPressedEmoji);
                }
            }
            articleDetailPojo.setFeedDetail(mFeedDetail);
            articleList.clear();
            articleList.add(articleDetailPojo);
            mAdapter.notifyDataSetChanged();
            ((ArticleDetailActivity) getActivity()).onBookmarkClick(mFeedDetail, AppConstants.TWO_CONSTANT);
        } else {
            if (!mFeedDetail.isLongPress()) {
                if (mFeedDetail.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
                    mFeedDetail.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                    mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                } else {
                    mFeedDetail.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                    mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                }
                mFeedDetail.setReactionValue(mFeedDetail.getLastReactionValue());
                articleDetailPojo.setFeedDetail(mFeedDetail);
                articleList.clear();
                articleList.add(articleDetailPojo);
                mAdapter.notifyDataSetChanged();
            }
            showError(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_LIKE_UNLIKE);
        }
    }

    public void likeAndUnlikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        mListLoad = false;
        mFeedDetail = (FeedDetail) baseResponse;
        this.mPosition = position;
        this.mPressedEmoji = reactionValue;
        if (null != mFeedDetail && mFeedDetail.isLongPress()) {
            mHomePresenter.getLikesFromPresenter(mAppUtils.likeRequestBuilder(mFeedDetail.getEntityOrParticipantId(), reactionValue));
        } else {
            if (reactionValue == AppConstants.NO_REACTION_CONSTANT) {
                mHomePresenter.getUnLikesFromPresenter(mAppUtils.unLikeRequestBuilder(mFeedDetail.getEntityOrParticipantId()));
            } else {
                mHomePresenter.getLikesFromPresenter(mAppUtils.likeRequestBuilder(mFeedDetail.getEntityOrParticipantId(), reactionValue));
            }
        }
    }

    public void bookMarkForCard(FeedDetail feedDetail) {
        super.bookMarkForCard(feedDetail);
    }

    public void commentListRefresh(FeedDetail feedDetail) {
        articleDetailPojo.setFeedDetail(feedDetail);
        articleList.clear();
        articleList.add(articleDetailPojo);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }
}

