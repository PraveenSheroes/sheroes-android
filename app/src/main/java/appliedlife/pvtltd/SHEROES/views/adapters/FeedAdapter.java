package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.media.Image;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.EventSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ImageSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.JobFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LeaderObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.OrganizationFeedObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.viewholder.LoaderViewHolder;
import appliedlife.pvtltd.SHEROES.viewholder.UserPostCompactViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.AppIntroCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CarouselViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ChallengeFeedHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CommunityFlatViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.EventCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedArticleHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedCommunityPostHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedJobHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HomeHeaderViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ImageViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.LeaderViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.MentorCard;
import appliedlife.pvtltd.SHEROES.views.viewholders.OrgReviewCardHolder;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 28/12/17.
 */

public class FeedAdapter extends HeaderRecyclerViewAdapter {

    //region private variables
    public static final String TAG = "feedAdapter";
    public static final String LIST_FEED = "FEED";
    private final Context mContext;
    private List<FeedDetail> mFeedDetailList;
    private SparseArray<Parcelable> scrollStatePositionsMap = new SparseArray<>();
    private boolean showLoader = false;
    private BaseHolderInterface mBaseHolderInterface;
    //endregion

    //region Constructor
    public FeedAdapter(Context context, BaseHolderInterface baseHolderInterface) {
        mContext = context;
        this.mFeedDetailList = new ArrayList<>();
        this.mBaseHolderInterface = baseHolderInterface;
    }
    //endregion

    //region feedAdapter methods
    @Override
    public RecyclerView.ViewHolder customOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);

        switch (viewType) {
            case TYPE_ARTICLE:
                return new FeedArticleHolder(mInflater.inflate(R.layout.feed_article_card_normal, parent, false), mBaseHolderInterface);
            case TYPE_USER_POST:
                View viewPost = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_post_compact_item, parent, false);
                //return new UserPostCompactViewHolder(viewPost,mContext, mBaseHolderInterface);

                return new FeedCommunityPostHolder(mInflater.inflate(R.layout.feed_comunity_user_post_normal, parent, false), mBaseHolderInterface);
            case TYPE_LOADER:
                return new LoaderViewHolder(mInflater.inflate(R.layout.infinite_loading, parent, false));
            case TYPE_JOB:
                return new FeedJobHolder(mInflater.inflate(R.layout.feed_job_normal_card, parent, false), mBaseHolderInterface);
            case TYPE_CHALLENGE:
                return new ChallengeFeedHolder(mInflater.inflate(R.layout.challenge_feed_item, parent, false), mBaseHolderInterface);
            case TYPE_EVENT:
                return new EventCardHolder(mInflater.inflate(R.layout.event_card_holder, parent, false), mBaseHolderInterface);
            case TYPE_ORGANIZATION:
                return new OrgReviewCardHolder(mInflater.inflate(R.layout.review_card_holder, parent, false), mBaseHolderInterface);
            case TYPE_INRO:
                return new AppIntroCardHolder(mInflater.inflate(R.layout.app_intro_card, parent, false), mBaseHolderInterface);
            case TYPE_MENTOR_SUGGESTION_CAROSEL:
                return new CarouselViewHolder(mInflater.inflate(R.layout.mentor_suggested_card_holder, parent, false), mBaseHolderInterface);
            case TYPE_MENTOR_COMPACT:
                return new MentorCard(mInflater.inflate(R.layout.feed_mentor_card, parent, false), mBaseHolderInterface);
            case TYPE_LEADER:
                return new LeaderViewHolder(mInflater.inflate(R.layout.list_leader_item, parent, false), mBaseHolderInterface);
            case TYPE_COMMUNITY:
                return new CommunityFlatViewHolder(mInflater.inflate(R.layout.community_flat_layout, parent, false), mBaseHolderInterface);
            case TYPE_HOME_FEED_HEADER:
                return new HomeHeaderViewHolder(mInflater.inflate(R.layout.header_view_layout, parent, false), mBaseHolderInterface);
            case TYPE_IMAGE:
                return new ImageViewHolder(mInflater.inflate(R.layout.image_item, parent, false), mBaseHolderInterface);
        }
        return null;
    }

    @Override
    public void customOnBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {

            case TYPE_ARTICLE:
                FeedArticleHolder articleViewHolder = (FeedArticleHolder) holder;
                ArticleSolrObj articleSolrObj = (ArticleSolrObj) mFeedDetailList.get(position);
                articleViewHolder.bindData(articleSolrObj, mContext, position);
                break;
            case TYPE_USER_POST:
                /*UserPostCompactViewHolder userPostCompactViewHolder = (UserPostCompactViewHolder) holder;
                UserPostSolrObj userPostSolrObj = (UserPostSolrObj) mFeedDetailList.get(position);
                userPostCompactViewHolder.bindData(userPostSolrObj, mContext);*/

                FeedCommunityPostHolder feedCommunityPostHolder = (FeedCommunityPostHolder) holder;
                UserPostSolrObj userPostSolrObj = (UserPostSolrObj) mFeedDetailList.get(position);
                feedCommunityPostHolder.bindData(userPostSolrObj, mContext, position);
                break;
            case TYPE_LOADER:
                LoaderViewHolder loaderViewHolder = ((LoaderViewHolder) holder);
                loaderViewHolder.bindData(holder.getAdapterPosition(), showLoader);
                break;
            case TYPE_JOB:
                FeedJobHolder feedJobHolder = (FeedJobHolder) holder;
                JobFeedSolrObj jobFeedSolrObj = (JobFeedSolrObj) mFeedDetailList.get(position);
                feedJobHolder.bindData(jobFeedSolrObj, mContext, position);
                break;
            case TYPE_CHALLENGE:
                ChallengeFeedHolder challengeFeedHolder = (ChallengeFeedHolder) holder;
                ChallengeSolrObj challengeSolrObj = (ChallengeSolrObj) mFeedDetailList.get(position);
                challengeFeedHolder.bindData(challengeSolrObj, mContext, position);
                break;
            case TYPE_EVENT:
                EventCardHolder eventCardHolder = (EventCardHolder) holder;
                UserPostSolrObj userPostSolrObj1 = (UserPostSolrObj) mFeedDetailList.get(position);
                eventCardHolder.bindData(userPostSolrObj1, mContext, position);
                break;
            case TYPE_ORGANIZATION:
                OrgReviewCardHolder orgReviewCardHolder = (OrgReviewCardHolder) holder;
                UserPostSolrObj userPostSolrObj2 = (UserPostSolrObj) mFeedDetailList.get(position);
                orgReviewCardHolder.bindData(userPostSolrObj2, mContext, position);
                break;
            case TYPE_INRO:
                AppIntroCardHolder appIntroCardHolder = (AppIntroCardHolder) holder;
                FeedDetail feedDetail = mFeedDetailList.get(position);
                appIntroCardHolder.bindData(feedDetail, mContext, position);
                break;
            case TYPE_MENTOR_SUGGESTION_CAROSEL:
                CarouselViewHolder carouselViewHolder = (CarouselViewHolder) holder;
                CarouselDataObj carouselDataObj = (CarouselDataObj) mFeedDetailList.get(position);
                carouselViewHolder.bindData(carouselDataObj, mContext, position);
                break;
            case TYPE_MENTOR_COMPACT:
                MentorCard mentorCard = (MentorCard) holder;
                UserSolrObj userSolrObj = (UserSolrObj) mFeedDetailList.get(position);
                mentorCard.bindData(userSolrObj, mContext, position);
                break;
            case TYPE_COMMUNITY:
                CommunityFlatViewHolder communityFlatViewHolder = (CommunityFlatViewHolder) holder;
                CommunityFeedSolrObj communityFeedSolrObj = (CommunityFeedSolrObj) mFeedDetailList.get(position);
                communityFlatViewHolder.bindData(communityFeedSolrObj, mContext, position);
                break;
            case TYPE_LEADER:
                LeaderViewHolder leaderViewHolder = (LeaderViewHolder) holder;
                LeaderObj leaderObj = (LeaderObj) mFeedDetailList.get(position);
                leaderViewHolder.bindData(leaderObj, mContext, position);
                break;

            case TYPE_HOME_FEED_HEADER:
                HomeHeaderViewHolder homeHeaderViewHolder = (HomeHeaderViewHolder) holder;
                FeedDetail feedDetail1 = mFeedDetailList.get(position);
                homeHeaderViewHolder.bindData(feedDetail1, mContext, position);
                break;

            case TYPE_IMAGE:
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                FeedDetail feedDetail2 = mFeedDetailList.get(position);
                imageViewHolder.bindData(feedDetail2, mContext, position);
        }
    }

    private static final int TYPE_ARTICLE = 1;
    private static final int TYPE_USER_POST = 2;
    private static final int TYPE_CAROUSEL = 3;
    private static final int TYPE_JOB = 4;
    private static final int TYPE_CHALLENGE = 5;
    private static final int TYPE_EVENT = 6;
    private static final int TYPE_ORGANIZATION = 7;
    private static final int TYPE_INRO = 8;
    private static final int TYPE_MENTOR_SUGGESTION_CAROSEL = 9;
    private static final int TYPE_MENTOR_COMPACT = 10;
    private static final int TYPE_LEADER = 11;
    private static final int TYPE_COMMUNITY = 12;
    private static final int TYPE_HOME_FEED_HEADER = 13;
    private static final int TYPE_IMAGE = 14;
    private static final int TYPE_LOADER = -1;

    @Override
    public int customGetItemViewType(int position) {
        if (position < getDataItemCount() && getDataItemCount() > 0) {
            FeedDetail feedDetail = mFeedDetailList.get(position);
            if (feedDetail instanceof ArticleSolrObj) {
                return TYPE_ARTICLE;
            }
            if (feedDetail instanceof UserPostSolrObj) {
                UserPostSolrObj userPostSolrObj = (UserPostSolrObj) feedDetail;
                if (userPostSolrObj.getCommunityId() == AppConstants.EVENT_COMMUNITY_ID && userPostSolrObj.getCommunityTypeId() != AppConstants.ORGANISATION_COMMUNITY_TYPE_ID) {
                    return TYPE_EVENT;

                } else if (userPostSolrObj.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID && (!userPostSolrObj.isCommentAllowed())) {
                    return TYPE_ORGANIZATION;
                }else {
                    return TYPE_USER_POST;
                }
            }
            if (feedDetail instanceof JobFeedSolrObj) {
                return TYPE_JOB;
            }
            if (feedDetail instanceof ChallengeSolrObj) {
                return TYPE_CHALLENGE;
            }
            if (feedDetail instanceof EventSolrObj) {
                return TYPE_EVENT;
            }
            if (feedDetail instanceof OrganizationFeedObj) {
                return TYPE_ORGANIZATION;
            }
            if (feedDetail.getSubType().equalsIgnoreCase(AppConstants.APP_INTRO_SUB_TYPE)) {
                return TYPE_INRO;
            }
            if (feedDetail instanceof CarouselDataObj) {
                return TYPE_MENTOR_SUGGESTION_CAROSEL;
            }

            if (feedDetail instanceof UserSolrObj) {
                return TYPE_MENTOR_COMPACT;
            }

            if (feedDetail instanceof CommunityFeedSolrObj) {
                return TYPE_COMMUNITY;
            }

            if (feedDetail instanceof LeaderObj) {
                return TYPE_LEADER;
            }

            if (feedDetail instanceof ImageSolrObj) {
                return TYPE_IMAGE;
            }

            if(feedDetail.getSubType().equalsIgnoreCase(AppConstants.HOME_FEED_HEADER)){
                return TYPE_HOME_FEED_HEADER;
            }
        }
        return TYPE_LOADER;
    }

    @Override
    public long getItemId(int position) {
        if (getItemViewType(position) == TYPE_LOADER) {
            return -1L;
        }
        return super.getItemId(position);
    }

    @Override
    public int customGetItemCount() {
        return getDataItemCount() + (showLoader ? 1 : 0);
    }

    @Override
    public HeaderViewHolder getHeaderViewHolder(ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        return null;
    }

    @Override
    public void bindHeaderViewHolder(HeaderRecyclerViewAdapter.HeaderViewHolder holder) {

    }
    //endregion

    //region Public methods
    public void feedStartedLoading() {
        if (showLoader) return;
        showLoader = true;
        notifyItemInserted(getLoaderPosition());
    }

    public void feedFinishedLoading() {
        if (!showLoader) return;
        final int loadingPos = getLoaderPosition();
        showLoader = false;
        notifyItemRemoved(loadingPos);
    }

    public void setItem(int position, FeedDetail feedDetail) {
        mFeedDetailList.set(position, feedDetail);
        customNotifyItemChanged(position);
    }

    public void setData(final List<FeedDetail> feedList) {
        mFeedDetailList = feedList;
    }
    //endregion

    //region Private Helper methods
    private int getLoaderPosition() {
        return showLoader ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    private int getDataItemCount() {
        return mFeedDetailList == null ? 0 : mFeedDetailList.size();
    }
    //endregion

    //region Public methods
    public void showToolTip() {
        notifyDataSetChanged();
    }

    public void setData(int position, FeedDetail feedDetail) {
        mFeedDetailList.set(position, feedDetail);
        notifyItemChanged(position);
    }

    public void setData(int outerPosition, int innerPosition,  FeedDetail updatedInnerFeedItem) {
        FeedDetail feedDetail = mFeedDetailList.get(outerPosition);
        if (feedDetail instanceof CarouselDataObj) {
            FeedDetail innerFeedItem = ((CarouselDataObj) feedDetail).getFeedDetails().get(innerPosition);
            innerFeedItem = updatedInnerFeedItem;
            ((CarouselDataObj) feedDetail).getFeedDetails().set(innerPosition, updatedInnerFeedItem);
        }
        mFeedDetailList.set(outerPosition, feedDetail);
        notifyItemChanged(outerPosition);
    }

    public void removeItem(int position) {
        mFeedDetailList.remove(position);
        notifyItemRemoved(position);
    }

    public List<FeedDetail> getDataList() {
        return mFeedDetailList;
    }

    public void addAll(List<FeedDetail> feedList) {
        int startPosition = mFeedDetailList.size();
        mFeedDetailList.addAll(feedList);
        notifyItemRangeChanged(startPosition, mFeedDetailList.size());
    }
    //endregion

    //region feedHeaderview
    public class HeaderViewHolder extends HeaderRecyclerViewAdapter.HeaderViewHolder {

        public HeaderViewHolder(View headerView) {
            super(headerView);
            ButterKnife.bind(this, headerView);
        }
    }
    //endregion
}