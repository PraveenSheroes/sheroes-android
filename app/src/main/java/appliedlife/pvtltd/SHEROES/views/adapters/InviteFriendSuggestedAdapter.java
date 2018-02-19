package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.ContactDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.contactdetail.UserContactDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.viewholder.LoaderViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ContactCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.SuggestedContactCardHolder;

/**
 * Created by Praveen on 19/02/18.
 */

public class InviteFriendSuggestedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private final Context mContext;
    private List<UserSolrObj> mUserSolrObjList;
    private final ContactDetailCallBack mContactDetailCallBack;

    private static final int TYPE_PROGRESS_LOADER = -1;
    private static final int TYPE_CONTACT = 1;

    //region Member variable
    private boolean showLoader = false;
    //endregion

    //region Constructor
    public InviteFriendSuggestedAdapter(Context context, ContactDetailCallBack mContactDetailCallBack) {
        this.mContext = context;
        mUserSolrObjList = new ArrayList<>();
        this.mContactDetailCallBack = mContactDetailCallBack;
    }
    //endregion

    //region ContactListAdapter methods
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case TYPE_CONTACT:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggested_contact_card_holder, parent, false);
                return new SuggestedContactCardHolder(view, mContactDetailCallBack);
            case TYPE_PROGRESS_LOADER:
                return new LoaderViewHolder(mInflater.inflate(R.layout.loading_progress_layout, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        switch (holder.getItemViewType()) {
            case TYPE_CONTACT:
                SuggestedContactCardHolder suggestedContactCardHolder = (SuggestedContactCardHolder) holder;
                UserSolrObj userSolrObj = (UserSolrObj) mUserSolrObjList.get(position);
                suggestedContactCardHolder.bindData(userSolrObj, mContext, position);
                break;

            case TYPE_PROGRESS_LOADER:
                LoaderViewHolder loaderViewHolder = ((LoaderViewHolder) holder);
                loaderViewHolder.bindData(holder.getAdapterPosition(), showLoader);
                break;
        }
    }

    //region Public methods
    @Override
    public int getItemCount() {
        return getDataItemCount() + (showLoader ? 1 : 0);
    }

    public void setData(final List<UserSolrObj> userSolrObjList) {
        mUserSolrObjList = userSolrObjList;
        notifyDataSetChanged();
    }
    public void contactStartedLoading() {
        if (showLoader) return;
        showLoader = true;
        notifyItemInserted(getLoaderPosition());
    }
    public void addAll(List<UserSolrObj> userContactDetailList) {
        int startPosition = userContactDetailList.size();
        mUserSolrObjList.addAll(userContactDetailList);
        notifyItemRangeChanged(startPosition, mUserSolrObjList.size());
    }
    @Override
    public int getItemViewType(int position) {
        if (position < getDataItemCount() && getDataItemCount() > 0) {
            return TYPE_CONTACT;
        }
        return TYPE_PROGRESS_LOADER;
    }

    public void contactsFinishedLoading() {
        if (!showLoader) return;
        final int loadingPos = getLoaderPosition();
        showLoader = false;
        notifyItemRemoved(loadingPos);
    }

    //endregion
    //region Private Helper methods
    private int getLoaderPosition() {
        return showLoader ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    private int getDataItemCount() {
        return mUserSolrObjList == null ? 0 : mUserSolrObjList.size();
    }
    //endregion
}
