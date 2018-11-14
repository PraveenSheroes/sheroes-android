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

import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.HelplineViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineChatDoc;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQS;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.viewholders.HolderMapping;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Generic Recycler adapter for every list view.
 * List respose will be bind with generic adapter.
 */
public class GenericRecyclerViewAdapter<T extends BaseResponse> extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {

    Context context;
    List<T> mSheroesGenericListData = new ArrayList<>();
    BaseHolderInterface viewHolderInterface;
    protected List<T> filterListData;
    private String mCallFromType = AppConstants.FOR_ALL;
    private long mUserId;
    private int mPosition;
    public GenericRecyclerViewAdapter(Context context, BaseHolderInterface viewHolderInterface) {
        this.context = context;
        this.viewHolderInterface = viewHolderInterface;
    }

    public void setSheroesGenericListData(List<T> mSheroesGenericListData) {
        this.mSheroesGenericListData = mSheroesGenericListData;
        this.filterListData = mSheroesGenericListData;
    }
    public void setSuggestedCardPosition(int position) {
        mPosition = position;
    }
    public void setUserId(long userId) {
        mUserId = userId;
    }
    public void setCallForRecycler(String callFromType) {
        this.mCallFromType = callFromType;
    }

    public void removeDataOnPosition(FeedDetail feedDetail, int position) {
        if (StringUtil.isNotEmptyCollection(filterListData) && filterListData.size() > position) {
            this.filterListData.remove(position);
        }
    }

    public void removeDataOnPosition(int position) {
        if (StringUtil.isNotEmptyCollection(filterListData) && filterListData.size() > position) {
            this.filterListData.remove(position);
        }
    }

    public void addAllDataForList(List<T> data) {
        this.filterListData.addAll(data);
    }
    public void setSuggestedDataOnPosition(CarouselDataObj carouselDataObj, int position) {
        if (StringUtil.isNotEmptyCollection(filterListData) && filterListData.size() > position) {
            this.filterListData.remove(position);
            this.filterListData.add(position, (T) carouselDataObj);
        }
    }
    public void setDataOnPosition(FeedDetail feedDetail, int position) {
        if (StringUtil.isNotEmptyCollection(filterListData) && filterListData.size() > position) {
            this.filterListData.remove(position);
            this.filterListData.add(position, (T) feedDetail);
        }
    }
    public void setMentoreDataOnPosition(UserSolrObj userSolrObj, int position) {
        if (StringUtil.isNotEmptyCollection(filterListData) && filterListData.size() > position) {
            this.filterListData.remove(position);
            this.filterListData.add(position, (T) userSolrObj);
        }
    }

    public void setFAQOnPosition(FAQS faqs, int position) {
        this.filterListData.remove(position);
        this.filterListData.add(position, (T) faqs);
    }
    public void setMentoreDataOnPosition(CarouselDataObj carouselDataObj, int position) {
        if (StringUtil.isNotEmptyCollection(filterListData) && filterListData.size() > position) {
            this.filterListData.remove(position);
            this.filterListData.add(position, (T) carouselDataObj);
        }
    }
    public void addDataOnPosition(FeedDetail feedDetail, int position) {
        if (StringUtil.isNotEmptyCollection(filterListData) && filterListData.size() > position) {
            this.filterListData.add(position, (T) feedDetail);
        }
    }
    public void clearAllDataForList() {
        this.filterListData.clear();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int mapOrdinal) {
        HolderMapping mapping = HolderMapping.values()[mapOrdinal];
        View view = mapping.getView(LayoutInflater.from(context), parent);
        BaseViewHolder holder = null;
        try {
            holder = mapping.getViewHolder(view, viewHolderInterface);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mCallFromType.equalsIgnoreCase(AppConstants.FOR_ALL)) {
            if (filterListData.get(position) instanceof UserSolrObj) {
                UserSolrObj userSolrObj = (UserSolrObj) filterListData.get(position);
                userSolrObj.currentItemPosition = mPosition;
                userSolrObj.setSuggested(true);
                filterListData.set(position, (T) userSolrObj);
            }
        }
        if (filterListData.get(position) instanceof HelplineChatDoc && holder instanceof HelplineViewHolder) {
            T prevObj = null;
            if (position + 1 < filterListData.size()) {
                prevObj = filterListData.get(position + 1);
            }
            ((HelplineViewHolder) holder).bindData(filterListData.get(position), context, position, prevObj);
        } else {
            holder.bindData(filterListData.get(position), context, position);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return HolderMapping.getOrdinal(filterListData.get(position),mUserId , mCallFromType);
    }

    @Override
    public int getItemCount() {
        return filterListData == null ? 0 : filterListData.size();
    }

    @Override
    public void onViewRecycled(BaseViewHolder holder) {
        super.onViewRecycled(holder);
        holder.viewRecycled();
    }


    @Override
    public void onViewDetachedFromWindow(BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.viewRecycled();
    }

    public List<T> getSheroesGenericListData() {
        return filterListData;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterListData = (List<T>) results.values;
                GenericRecyclerViewAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<T> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = mSheroesGenericListData;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }
                FilterResults results = new FilterResults();
                results.values = filteredResults;
                return results;
            }
        };
    }


    protected List<T> getFilteredResults(String constraint) {
        List<T> results = new ArrayList<>();
        for (T item : mSheroesGenericListData) {
          /*  if (item instanceof ListOfInviteSearch) {
                ListOfInviteSearch listOfInviteSearch=(ListOfInviteSearch) item;
                if (listOfInviteSearch.getFeedTitle().toLowerCase().contains(constraint)) {
                    results.add(item);
                }
            }
            if (item instanceof ListOfSearch) {
                ListOfSearch listOfSearch = (ListOfSearch) item;
                if (listOfSearch.getFeedTitle().toLowerCase().contains(constraint)) {
                    results.add(item);
                }
            }*/

        }
        return results;
    }
}
