package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
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
    private ArticleDetailActivityIntractionListner mArticleDetailActivityIntractionListner;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof ArticleDetailActivityIntractionListner) {
                mArticleDetailActivityIntractionListner = (ArticleDetailActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            mFeedDetail = getArguments().getParcelable(AppConstants.ARTICLE_DETAIL);
        }
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.ARTICLE_DETAIL, mFeedDetail.getId());
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
        List<FeedDetail> feedDetailList=feedResponsePojo.getFeedDetails();
        if (StringUtil.isNotEmptyCollection(feedDetailList) && mAdapter != null) {
            articleList = new ArrayList<>();
            articleDetailPojo = new ArticleDetailPojo();
            articleDetailPojo.setId(AppConstants.ONE_CONSTANT);
            articleDetailPojo.setFeedDetail(feedDetailList.get(0));
            mFeedDetail.setNoOfViews(feedDetailList.get(0).getNoOfViews());
            ((ArticleDetailActivity) getActivity()).mTvArticleDetailTotalViews.setVisibility(View.VISIBLE);
            ((ArticleDetailActivity) getActivity()).mTvArticleDetailTotalViews.setText(mFeedDetail.getNoOfViews() + AppConstants.SPACE + getActivity().getString(R.string.ID_VIEWS));
            articleList.add(articleDetailPojo);
            mAdapter.setSheroesGenericListData(articleList);
            mAdapter.notifyDataSetChanged();
            if (mRecyclerView.getItemAnimator() instanceof SimpleItemAnimator) {
                ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setAddDuration(AppConstants.NO_REACTION_CONSTANT);
            }

            mArticleDetailActivityIntractionListner.onBookmarkClick(mFeedDetail, AppConstants.TWO_CONSTANT);
        }
    }
    @Override
    public void getSuccessForAllResponse(String success, FeedParticipationEnum feedParticipationEnum) {
        switch (feedParticipationEnum) {
            case LIKE_UNLIKE:
                likeSuccess(success);
                break;
            case BOOKMARK_UNBOOKMARK:
                articleBookMarkSuccess(success);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + feedParticipationEnum);
        }
    }



    protected void articleBookMarkSuccess(String success) {
        if (null != mFeedDetail) {
            switch (success) {
                case AppConstants.SUCCESS:
                    if (!mFeedDetail.isBookmarked()) {
                        mFeedDetail.setBookmarked(true);
                        articleDetailPojo.setFeedDetail(mFeedDetail);
                        mFeedDetail.setBookmarked(false);
                    } else {
                        mFeedDetail.setBookmarked(false);
                        articleDetailPojo.setFeedDetail(mFeedDetail);
                        mFeedDetail.setBookmarked(true);
                    }
                    articleList.clear();
                    articleList.add(articleDetailPojo);
                    mAdapter.notifyDataSetChanged();
                    mArticleDetailActivityIntractionListner.onBookmarkClick(mFeedDetail, AppConstants.ONE_CONSTANT);
                    break;
                case AppConstants.FAILED:
                    showError(getString(R.string.ID_ALREADY_BOOKMARK), ERROR_BOOKMARK_UNBOOKMARK);
                    break;
                default:
                    showError(AppConstants.HTTP_401_UNAUTHORIZED,ERROR_BOOKMARK_UNBOOKMARK);
            }
        }
    }

    protected void likeSuccess(String success) {

        if (success.equalsIgnoreCase(AppConstants.SUCCESS) && null != mFeedDetail) {

            if (mFeedDetail.isLongPress()) {
                if (mFeedDetail.getReactionValue() == AppConstants.NO_REACTION_CONSTANT) {
                    mFeedDetail.setReactionValue(mPressedEmoji);
                    mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                } else {
                    mFeedDetail.setReactionValue(mPressedEmoji);
                }

            } else {

                if (mFeedDetail.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
                    mFeedDetail.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                    mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                } else {
                    mFeedDetail.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                    mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                }
            }
            articleDetailPojo.setFeedDetail(mFeedDetail);
            articleList.clear();
            articleList.add(articleDetailPojo);
            mAdapter.notifyDataSetChanged();
            if (mRecyclerView.getItemAnimator() instanceof SimpleItemAnimator) {
                ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setAddDuration(AppConstants.NO_REACTION_CONSTANT);
            }
            mArticleDetailActivityIntractionListner.onBookmarkClick(mFeedDetail, AppConstants.TWO_CONSTANT);
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
        if (mRecyclerView.getItemAnimator() instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setAddDuration(AppConstants.NO_REACTION_CONSTANT);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }

    public interface ArticleDetailActivityIntractionListner {
        void onBookmarkClick(FeedDetail feedDetail, int successFrom);
    }


}

