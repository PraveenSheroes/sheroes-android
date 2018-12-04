package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.f2prateek.rx.preferences2.Preference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.viewholder.LoaderViewHolder;
import appliedlife.pvtltd.SHEROES.viewholder.UserProfileFlatViewHolder;

/**
 * Created by Ravi on 06/09/18.
 */

public class UserListAdapter extends HeaderRecyclerViewAdapter {

    //region private variables
    public static final String TAG = "UserListAdapter";
    private final Context mContext;
    private List<FeedDetail> mFeedDetailList;
    private boolean showLoader = false;
    private BaseHolderInterface mBaseHolderInterface;

    @Inject
    Preference<AppConfiguration> mConfiguration;

    //endregion

    //region Constructor
    public UserListAdapter(Context context, BaseHolderInterface baseHolderInterface) {
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
            case TYPE_LOADER:
                return new LoaderViewHolder(mInflater.inflate(R.layout.infinite_loading, parent, false));
           case TYPE_USER_FLAT:
                return new UserProfileFlatViewHolder(mInflater.inflate(R.layout.list_user_flat_item, parent, false), mContext, mBaseHolderInterface);
        }
        return null;
    }

    @Override
    public void customOnBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_LOADER:
                LoaderViewHolder loaderViewHolder = ((LoaderViewHolder) holder);
                loaderViewHolder.bindData(holder.getAdapterPosition(), showLoader);
                break;
            case TYPE_USER_FLAT:
                UserProfileFlatViewHolder userProfileCompactViewHolder = (UserProfileFlatViewHolder) holder;
                UserSolrObj userProfileSolrObj = (UserSolrObj) mFeedDetailList.get(position);
                userProfileCompactViewHolder.bindData(userProfileSolrObj, mContext);
                break;
        }
    }

    private static final int TYPE_USER_FLAT = 10;;
    private static final int TYPE_LOADER = -1;

    @Override
    public int customGetItemViewType(int position) {
        if (position < getDataItemCount() && getDataItemCount() > 0) {
            FeedDetail feedDetail = mFeedDetailList.get(position);
            if (feedDetail instanceof UserSolrObj) {
                return TYPE_USER_FLAT;
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
        return null;
    }

    @Override
    public void bindHeaderViewHolder(HeaderViewHolder holder) {
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
}