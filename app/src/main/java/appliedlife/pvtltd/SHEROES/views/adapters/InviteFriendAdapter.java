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
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.viewholder.LoaderViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ContactCardHolder;

/**
 * Created by Praveen on 14/02/18.
 */

public class InviteFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<UserContactDetail> mContactDetail;
    private final ContactDetailCallBack mContactDetailCallBack;

    private static final int TYPE_PROGRESS_LOADER = -1;
    private static final int TYPE_CONTACT = 1;

    //region Member variable
    private boolean showLoader = false;
    private boolean hasMoreItem = false;
    //endregion

    //region Constructor
    public InviteFriendAdapter(Context context, ContactDetailCallBack mContactDetailCallBack) {
        this.mContext = context;
        mContactDetail = new ArrayList<>();
        this.mContactDetailCallBack = mContactDetailCallBack;
    }
    //endregion

    //region ContactListAdapter methods
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case TYPE_CONTACT:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_card_holder_layout, parent, false);
                return new ContactCardHolder(view, mContactDetailCallBack);
              case TYPE_PROGRESS_LOADER:
                  return new LoaderViewHolder(mInflater.inflate(R.layout.infinite_loading, parent, false));
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
                ContactCardHolder contactCardHolder = (ContactCardHolder) holder;
                UserContactDetail feedDetail = (UserContactDetail) mContactDetail.get(position);
                contactCardHolder.bindData(feedDetail, mContext, position);
                break;

            case TYPE_PROGRESS_LOADER:
                LoaderViewHolder loaderViewHolder = ((LoaderViewHolder) holder);
                loaderViewHolder.bindData(holder.getAdapterPosition(), showLoader);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mContactDetail == null ? 0 : mContactDetail.size();
    }

    public void setData(final List<UserContactDetail> contactDetails) {
        mContactDetail = contactDetails;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        BaseResponse feedDetail = mContactDetail.get(position);
        if (feedDetail instanceof UserContactDetail) {
            return TYPE_CONTACT;
        }
        return TYPE_PROGRESS_LOADER;
    }





    public void commentStartedLoading() {
        if (showLoader) return;
        showLoader = true;
        int loadingPos = getLoaderPostion();
        if (loadingPos != RecyclerView.NO_POSITION) {
            notifyItemChanged(loadingPos);
        }
    }

    public void commentFinishedLoading() {
        if (!showLoader) return;
        final int loadingPos = getLoaderPostion();
        showLoader = false;
        if (loadingPos != RecyclerView.NO_POSITION) {
            notifyItemChanged(loadingPos);
        }
    }

    public int getLoaderPostion() {
        int pos = RecyclerView.NO_POSITION;
        if(hasMoreItem){
            if(!CommonUtil.isEmpty(mContactDetail)){
                BaseResponse baseResponse = mContactDetail.get(0);
                if(baseResponse instanceof UserPostSolrObj){
                    pos = 1;
                }
            }
        }
        return pos;
    }

    public void setHasMoreComments(boolean hasMoreComments) {
        if(!hasMoreComments){
            int lodPos = getLoaderPostion();
            mContactDetail.remove(lodPos);
            notifyItemRemoved(lodPos);
        }
        this.hasMoreItem = hasMoreComments;
    }



    public void removeData(int index) {
        mContactDetail.remove(index);
        notifyItemRemoved(index);
    }



    //endregion
}
