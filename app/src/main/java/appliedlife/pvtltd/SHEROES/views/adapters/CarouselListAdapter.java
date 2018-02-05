package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.views.viewholders.CommunityCompactViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.MentorCard;
import appliedlife.pvtltd.SHEROES.views.viewholders.SeeMoreCompactViewHolder;

/**
 * Created by ujjwal on 16/02/17.
 */
public class CarouselListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<FeedDetail> mFeedDetails;
    private BaseHolderInterface mBaseHolderInterface;
    //region Constructor
    public CarouselListAdapter(Context context, BaseHolderInterface baseHolderInterface) {
        mContext = context;
        this.mFeedDetails = new ArrayList<>();
        this.mBaseHolderInterface = baseHolderInterface;
    }
    //endregion

    //region CarouselListAdapter methods
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_COMMUNITY:
                View viewArticle = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_compact_layout, parent, false);
                return new CommunityCompactViewHolder(viewArticle, mBaseHolderInterface);
            case TYPE_USER:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_mentor_card, parent, false);
                return new MentorCard(view, mBaseHolderInterface);
            case TYPE_SEE_MORE:
                View viewMore = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_see_more_item, parent, false);
                return new SeeMoreCompactViewHolder(viewMore, mBaseHolderInterface);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {

            case TYPE_COMMUNITY:
                CommunityCompactViewHolder communityCompactViewHolder = (CommunityCompactViewHolder) holder;
                CommunityFeedSolrObj communityFeedSolrObj = (CommunityFeedSolrObj) mFeedDetails.get(position);
                communityCompactViewHolder.bindData(communityFeedSolrObj, mContext, position);
                break;

            case TYPE_USER:
                MentorCard mentorCard = (MentorCard) holder;
                UserSolrObj userSolrObj = (UserSolrObj) mFeedDetails.get(position);
                mentorCard.bindData(userSolrObj, mContext,position);
                break;
            case TYPE_SEE_MORE:
                SeeMoreCompactViewHolder seeMoreCompactViewHolder = (SeeMoreCompactViewHolder) holder;
                seeMoreCompactViewHolder.bindData();
                break;

        }

    }

    private static final int TYPE_COMMUNITY = 1;
    private static final int TYPE_USER = 2;
    private static final int TYPE_SEE_MORE = 3;

    @Override
    public int getItemViewType(int position) {
        if (position < getDataItemCount() && getDataItemCount() > 0) {
            FeedDetail feedDetail = mFeedDetails.get(position);
            if (feedDetail instanceof CommunityFeedSolrObj) {
                return TYPE_COMMUNITY;
            }
            if (feedDetail instanceof UserSolrObj) {
                return TYPE_USER;
            }
        }
        if(position == mFeedDetails.size()){
            return TYPE_SEE_MORE;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mFeedDetails == null ? 0 : mFeedDetails.size() + 1;
    }
    //endregion

    //region public methods
    public void setData(final List<FeedDetail> feedList) {
        mFeedDetails = null;
        mFeedDetails = feedList;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }
    //endregion

    //region Private Helper methods
    private int getDataItemCount() {
        return mFeedDetails == null ? 0 : mFeedDetails.size();
    }
    //endregion
}
