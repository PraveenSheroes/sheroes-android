package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.viewholder.UserPostCompactViewHolder;
import appliedlife.pvtltd.SHEROES.viewholder.UserProfileCompactViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CarouselViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CommunityCompactViewHolder;

/**
 * Created by ujjwal on 16/02/17.
 */
public class CarouselListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<FeedDetail> mFeedDetails;
    private BaseHolderInterface mBaseHolderInterface;
    private CarouselDataObj mCarouselDataObj;
    private CarouselViewHolder carouselViewHolder;

    //region Constructor
    public CarouselListAdapter(Context context, BaseHolderInterface baseHolderInterface, CarouselDataObj carouselDataObj, CarouselViewHolder carouselViewHolder) {
        mContext = context;
        this.mFeedDetails = new ArrayList<>();
        this.mBaseHolderInterface = baseHolderInterface;
        this.mCarouselDataObj = carouselDataObj;
        this.carouselViewHolder = carouselViewHolder;
    }
    //endregion

    //region CarouselListAdapter methods
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_COMMUNITY:
                View viewArticle = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_compact_layout, parent, false);
                return new CommunityCompactViewHolder(viewArticle, mBaseHolderInterface, carouselViewHolder);
            case TYPE_USER:
                View viewUser = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_compact_item, parent, false);
                return new UserProfileCompactViewHolder(viewUser, mContext, mBaseHolderInterface);
            case TYPE_USER_POST:
                View viewPost = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_post_compact_item, parent, false);
                return new UserPostCompactViewHolder(viewPost, mContext, mBaseHolderInterface);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }

        if (holder.getItemViewType() == -1) {
            return;
        }

        switch (holder.getItemViewType()) {

            case TYPE_COMMUNITY:
                CommunityCompactViewHolder communityCompactViewHolder = (CommunityCompactViewHolder) holder;
                CommunityFeedSolrObj communityFeedSolrObj = (CommunityFeedSolrObj) mFeedDetails.get(position);
                communityFeedSolrObj.setItemPosition(mCarouselDataObj != null ? mCarouselDataObj.getItemPosition() : 0);
                communityCompactViewHolder.bindData(communityFeedSolrObj, mContext, position);
                break;

            case TYPE_USER:
                UserProfileCompactViewHolder userProfileCompactViewHolder = (UserProfileCompactViewHolder) holder;
                UserSolrObj userProfileSolrObj = (UserSolrObj) mFeedDetails.get(position);
                userProfileSolrObj.setCompactView(true);
                userProfileCompactViewHolder.bindData(userProfileSolrObj, mContext);
                break;

            case TYPE_USER_POST:
                UserPostCompactViewHolder userPostCompactViewHolder = (UserPostCompactViewHolder) holder;
                UserPostSolrObj userPostSolrObj = (UserPostSolrObj) mFeedDetails.get(position);
                userPostSolrObj.setItemPosition(mCarouselDataObj != null ? mCarouselDataObj.getItemPosition() : 0);
                userPostCompactViewHolder.bindData(userPostSolrObj, mContext);
                break;

        }

    }

    private static final int TYPE_COMMUNITY = 1;
    private static final int TYPE_USER = 2;
    private static final int TYPE_MENTOR = 5;
    private static final int TYPE_USER_POST = 4;

    @Override
    public int getItemViewType(int position) {
        if (position < getDataItemCount() && getDataItemCount() > 0) {
            FeedDetail feedDetail = mFeedDetails.get(position);
            if (feedDetail instanceof CommunityFeedSolrObj) {
                return TYPE_COMMUNITY;
            } else if (feedDetail instanceof UserSolrObj) {
                if (feedDetail.getEntityOrParticipantTypeId() == AppConstants.CHAMPION_TYPE_ID) {
                    return TYPE_MENTOR;
                } else {
                    return TYPE_USER;
                }
            }
            if (feedDetail instanceof UserPostSolrObj) {
                return TYPE_USER_POST;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        if (getDataItemCount() > 0 && !CommonUtil.isNotEmpty(mCarouselDataObj.getEndPointUrl())) {
            return mFeedDetails == null ? 0 : mFeedDetails.size();
        }
        return mFeedDetails == null ? 0 : mFeedDetails.size();
    }
    //endregion

    //region public methods
    public void setData(final List<FeedDetail> feedList) {
        mFeedDetails = null;
        mFeedDetails = feedList;
    }

    public void setData(final FeedDetail feedDetail) {
        int pos = findPositionById(feedDetail.getIdOfEntityOrParticipant());
        if (pos != RecyclerView.NO_POSITION) {
            mFeedDetails.set(pos, feedDetail);
            notifyItemChanged(pos);
        }
    }

    public int findPositionById(long id) {
        if (CommonUtil.isEmpty(mFeedDetails)) {
            return -1;
        }

        for (int i = 0; i < mFeedDetails.size(); ++i) {
            FeedDetail feedDetail = mFeedDetails.get(i);
            if (feedDetail != null && feedDetail.getIdOfEntityOrParticipant() == id) {
                return i;
            }
        }
        return -1;
    }
    //endregion

    //region Private Helper methods
    private int getDataItemCount() {
        return mFeedDetails == null ? 0 : mFeedDetails.size();
    }
    //endregion
}
