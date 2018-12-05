package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ImageSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LeaderBoardUserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.PollSolarObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.viewholder.LoaderViewHolder;
import appliedlife.pvtltd.SHEROES.viewholder.UserProfileCompactViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.AppIntroCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CarouselViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ChallengeFeedHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CommunityFlatViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedArticleHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedCommunityPostHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedPollCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.HomeHeaderViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ImageViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.LeaderBoardViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.NoStoriesHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ujjwal on 28/12/17.
 */

public class FeedAdapter extends HeaderRecyclerViewAdapter {

    //region private variables
    public static final String TAG = "feedAdapter";
    private final Context mContext;
    private List<FeedDetail> mFeedDetailList;
    private boolean showLoader = false;
    private BaseHolderInterface mBaseHolderInterface;

    @Inject
    Preference<AppConfiguration> mConfiguration;

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
                return new FeedCommunityPostHolder(mInflater.inflate(R.layout.feed_comunity_user_post_normal, parent, false), mBaseHolderInterface);
            case TYPE_POLL:
                return new FeedPollCardHolder(mInflater.inflate(R.layout.feed_poll_card_holder, parent, false), mBaseHolderInterface);
            case TYPE_LOADER:
                return new LoaderViewHolder(mInflater.inflate(R.layout.infinite_loading, parent, false));
            case TYPE_CHALLENGE:
                return new ChallengeFeedHolder(mInflater.inflate(R.layout.challenge_feed_item, parent, false), mBaseHolderInterface);
            case TYPE_INRO:
                return new AppIntroCardHolder(mInflater.inflate(R.layout.app_intro_card, parent, false), mBaseHolderInterface);
            case TYPE_CAROUSEL:
                return new CarouselViewHolder(mInflater.inflate(R.layout.champion_suggested_card_holder, parent, false), mBaseHolderInterface);
            case TYPE_USER_COMPACT:
                return new UserProfileCompactViewHolder(mInflater.inflate(R.layout.list_user_flat_item, parent, false), mContext, mBaseHolderInterface);
            case TYPE_LEADERBOARD:
                return new LeaderBoardViewHolder(mInflater.inflate(R.layout.list_leaderboard_item, parent, false), mBaseHolderInterface);
            case TYPE_COMMUNITY:
                return new CommunityFlatViewHolder(mInflater.inflate(R.layout.community_flat_layout, parent, false), mBaseHolderInterface);
            case TYPE_HOME_FEED_HEADER:
                return new HomeHeaderViewHolder(mInflater.inflate(R.layout.header_view_layout, parent, false), mBaseHolderInterface);
            case TYPE_IMAGE:
                return new ImageViewHolder(mInflater.inflate(R.layout.image_item, parent, false), mBaseHolderInterface);
            case TYPE_EMPTY_VIEW:
                return new NoStoriesHolder(mInflater.inflate(R.layout.empty_view_holder, parent, false), mBaseHolderInterface);
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
                FeedCommunityPostHolder feedCommunityPostHolder = (FeedCommunityPostHolder) holder;
                UserPostSolrObj userPostSolrObj = (UserPostSolrObj) mFeedDetailList.get(position);
                feedCommunityPostHolder.bindData(userPostSolrObj, mContext, position);
                break;
            case TYPE_POLL:
                FeedPollCardHolder feedPollHolder = (FeedPollCardHolder) holder;
                PollSolarObj pollSolarObj = (PollSolarObj) mFeedDetailList.get(position);
                feedPollHolder.bindData(pollSolarObj, mContext, position);
                break;
            case TYPE_LOADER:
                LoaderViewHolder loaderViewHolder = ((LoaderViewHolder) holder);
                loaderViewHolder.bindData(holder.getAdapterPosition(), showLoader);
                break;
            case TYPE_CHALLENGE:
                ChallengeFeedHolder challengeFeedHolder = (ChallengeFeedHolder) holder;
                ChallengeSolrObj challengeSolrObj = (ChallengeSolrObj) mFeedDetailList.get(position);
                challengeFeedHolder.bindData(challengeSolrObj, mContext, position);
                break;
            case TYPE_INRO:
                AppIntroCardHolder appIntroCardHolder = (AppIntroCardHolder) holder;
                FeedDetail feedDetail = mFeedDetailList.get(position);
                appIntroCardHolder.bindData(feedDetail, mContext, position);
                break;
            case TYPE_CAROUSEL:
                CarouselViewHolder carouselViewHolder = (CarouselViewHolder) holder;
                CarouselDataObj carouselDataObj = (CarouselDataObj) mFeedDetailList.get(position);
                carouselViewHolder.bindData(carouselDataObj, mContext, position);
                break;
            case TYPE_USER_COMPACT:
                UserProfileCompactViewHolder userProfileCompactViewHolder = (UserProfileCompactViewHolder) holder;
                UserSolrObj userProfileSolrObj = (UserSolrObj) mFeedDetailList.get(position);
                userProfileSolrObj.setCompactView(true);
                userProfileCompactViewHolder.bindData(userProfileSolrObj, mContext);
                break;
            case TYPE_COMMUNITY:
                CommunityFlatViewHolder communityFlatViewHolder = (CommunityFlatViewHolder) holder;
                CommunityFeedSolrObj communityFeedSolrObj = (CommunityFeedSolrObj) mFeedDetailList.get(position);
                communityFlatViewHolder.bindData(communityFeedSolrObj, mContext, position);
                break;
            case TYPE_LEADERBOARD:
                LeaderBoardViewHolder leaderBoardViewHolder = (LeaderBoardViewHolder) holder;
                LeaderBoardUserSolrObj leaderBoardUserSolrObj = (LeaderBoardUserSolrObj) mFeedDetailList.get(position);
                leaderBoardViewHolder.bindData(leaderBoardUserSolrObj, mContext, position);
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
                break;

            case TYPE_EMPTY_VIEW:
                NoStoriesHolder noStoriesHolder = (NoStoriesHolder) holder;
                FeedDetail noStoryFeed = mFeedDetailList.get(position);
                noStoriesHolder.bindData(noStoryFeed, mContext, position);
                break;
        }
    }

    private static final int TYPE_ARTICLE = 1;
    private static final int TYPE_USER_POST = 2;
    private static final int TYPE_CHALLENGE = 5;
    private static final int TYPE_INRO = 8;
    private static final int TYPE_CAROUSEL = 9;
    private static final int TYPE_USER_COMPACT = 10;
    private static final int TYPE_LEADERBOARD = 11;
    private static final int TYPE_COMMUNITY = 12;
    private static final int TYPE_HOME_FEED_HEADER = 13;
    private static final int TYPE_IMAGE = 14;
    private static final int TYPE_EMPTY_VIEW = 15;
    private static final int TYPE_POLL = 3;
    private static final int TYPE_LOADER = -1;

    @Override
    public int customGetItemViewType(int position) {
        if (position < getDataItemCount() && getDataItemCount() > 0) {
            FeedDetail feedDetail = mFeedDetailList.get(position);
            if (feedDetail instanceof ArticleSolrObj) {
                return TYPE_ARTICLE;
            }

            if (feedDetail instanceof LeaderBoardUserSolrObj) {
                return TYPE_LEADERBOARD;
            }

            if (feedDetail instanceof UserPostSolrObj) {
                return TYPE_USER_POST;
            }
            if (feedDetail instanceof PollSolarObj) {
                return TYPE_POLL;
            }
            if (feedDetail instanceof ChallengeSolrObj) {
                return TYPE_CHALLENGE;
            }
            if (feedDetail.getSubType().equalsIgnoreCase(AppConstants.APP_INTRO_SUB_TYPE)) {
                return TYPE_INRO;
            }

            if (feedDetail instanceof CarouselDataObj) {
                return TYPE_CAROUSEL;
            }

            if (feedDetail instanceof UserSolrObj) {
                return TYPE_USER_COMPACT;
            }

            if (feedDetail instanceof CommunityFeedSolrObj) {
                return TYPE_COMMUNITY;
            }

            if (feedDetail instanceof ImageSolrObj) {
                return TYPE_IMAGE;
            }

            if (feedDetail.getSubType().equalsIgnoreCase(AppConstants.HOME_FEED_HEADER)) {
                return TYPE_HOME_FEED_HEADER;
            }
            if (feedDetail.getSubType().equalsIgnoreCase(AppConstants.TYPE_EMPTY_VIEW)) {
                return TYPE_EMPTY_VIEW;
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

    public void setData(int outerPosition, int innerPosition, FeedDetail updatedInnerFeedItem) {
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
    public class FeedHeaderViewHolder extends HeaderRecyclerViewAdapter.HeaderViewHolder {

        @Bind(R.id.update_later)
        TextView later;
        @Bind(R.id.update_now)
        TextView update;
        @Bind(R.id.update_title)
        TextView updateTitle;
        @Bind(R.id.update_description)
        TextView updateDescription;

        public FeedHeaderViewHolder(View headerView) {
            super(headerView);
            ButterKnife.bind(this, headerView);
        }

        @OnClick(R.id.update_now)
        public void onUpdateNowClicked() {
            if (mBaseHolderInterface instanceof FeedItemCallback) {
                ((FeedItemCallback) mBaseHolderInterface).onUpdateNowClicked();
            }
        }

        @OnClick(R.id.update_later)
        public void onUpdateLaterClicked() {
            if (mBaseHolderInterface instanceof FeedItemCallback) {
                ((FeedItemCallback) mBaseHolderInterface).onUpdateLaterClicked();
            }
        }

    }

    @Override
    public void bindHeaderViewHolder(HeaderRecyclerViewAdapter.HeaderViewHolder holder) {
        FeedHeaderViewHolder feedHeaderViewHolder = (FeedHeaderViewHolder) holder;
        feedHeaderViewHolder.updateTitle.setText((mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configData != null && CommonUtil.isNotEmpty(mConfiguration.get().configData.updateTitle)) ? mConfiguration.get().configData.updateTitle : mContext.getString(R.string.update_title));
        feedHeaderViewHolder.updateDescription.setText((mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configData != null && CommonUtil.isNotEmpty(mConfiguration.get().configData.updateDescription)) ? mConfiguration.get().configData.updateDescription : mContext.getString(R.string.update_description));
    }
    //endregion
}