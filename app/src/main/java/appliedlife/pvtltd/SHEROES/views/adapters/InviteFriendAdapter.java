package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.ContactDetailCallBack;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.UserContactDetail;
import appliedlife.pvtltd.SHEROES.viewholder.LoaderViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.ContactCardHolder;

/**
 * Created by Praveen on 14/02/18.
 */

public class InviteFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private final Context mContext;
    private List<UserContactDetail> mContactDetailList;
    List<UserContactDetail> mUserContactListForFilter = new ArrayList<>();
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
        mContactDetailList = new ArrayList<>();
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
                ContactCardHolder contactCardHolder = (ContactCardHolder) holder;
                UserContactDetail feedDetail = (UserContactDetail) mContactDetailList.get(position);
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
        return getDataItemCount() + (showLoader ? 1 : 0);
    }

    public void setData(final List<UserContactDetail> contactDetails) {
        mUserContactListForFilter.addAll(contactDetails);
        mContactDetailList.addAll(contactDetails);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getDataItemCount() && getDataItemCount() > 0) {
            return TYPE_CONTACT;
        }
        return TYPE_PROGRESS_LOADER;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mContactDetailList = (List<UserContactDetail>) results.values;
                InviteFriendAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<UserContactDetail> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = mUserContactListForFilter;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }
                FilterResults results = new FilterResults();
                results.values = filteredResults;
                return results;
            }
        };
    }

    private List<UserContactDetail> getFilteredResults(String constraint) {
        List<UserContactDetail> results = new ArrayList<>();
        for (UserContactDetail item : mUserContactListForFilter) {
            if (item.getName().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }

    public void addAll(List<UserContactDetail> userContactDetailList) {
        int startPosition = mContactDetailList.size();
        mContactDetailList.addAll(userContactDetailList);
        mUserContactListForFilter.addAll(userContactDetailList);
        notifyItemRangeChanged(startPosition, mContactDetailList.size());
    }

    //endregion
    //region Public methods
    public void contactStartedLoading() {
        if (showLoader) return;
        showLoader = true;
        notifyItemInserted(getLoaderPosition());
    }

    public void contactsFinishedLoading() {
        if (!showLoader) return;
        final int loadingPos = getLoaderPosition();
        showLoader = false;
        notifyItemRemoved(loadingPos);
    }

    //region Private Helper methods
    private int getLoaderPosition() {
        return showLoader ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    private int getDataItemCount() {
        return mContactDetailList == null ? 0 : mContactDetailList.size();
    }
    //endregion
}
