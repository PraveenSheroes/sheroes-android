package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.AllCommunityItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.CarouselListAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 24/11/17.
 */

public class CarouselViewHolder extends BaseViewHolder<CarouselDataObj> {
    //region private variable
    private final String TAG = LogUtils.makeLogTag(CarouselViewHolder.class);
    private BaseHolderInterface mViewInterface;
    private CarouselDataObj mCarouselDataObj;
    private boolean mIsUpdateFromProfile;
    private SparseArray<Parcelable> scrollStatePositionsMap = new SparseArray<>();
    private int mPosition;
    private boolean mIsSearch;
    //endregion

    //region public variable
    public CarouselListAdapter mAdapter = null;
    //endregion

    //region bind variable
    @Bind(R.id.icon)
    TextView mIcon;

    @Bind(R.id.title)
    TextView mTitle;

    @Bind(R.id.body)
    TextView mBody;

    @Bind(R.id.rv_suggested_mentor_list)
    RecyclerView mRecyclerView;
    //endregion

    //region constructor
    public CarouselViewHolder(View itemView, BaseHolderInterface baseHolderInterface, boolean isSearch) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mViewInterface = baseHolderInterface;
        this.mIsSearch = isSearch;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    scrollStatePositionsMap.put(mPosition, recyclerView.getLayoutManager().onSaveInstanceState());
                }
            }
        });
    }
    //endregion

    //region public method
    @Override
    public void bindData(CarouselDataObj item, final Context context, final int position) {
        this.mCarouselDataObj = item;
        this.mPosition = position;
        item.setItemPosition(position);
        if (StringUtil.isNotNullOrEmptyString(item.getTitle())) {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(item.getTitle());
        } else {
            //  mTitle.setVisibility(View.GONE); //TODO - Enable it once title coming for champion cards
        }

        if (StringUtil.isNotNullOrEmptyString(item.getBody())) {
            mBody.setVisibility(View.VISIBLE);
            mBody.setText(item.getBody());
        } else {
            mBody.setVisibility(View.GONE);
        }

        if (CommonUtil.isNotEmpty(item.getIconUrl())) {
            mIcon.setVisibility(View.VISIBLE);
           /* Glide.with(context)        //TODO - Enable it once title coming for champion cards
                    .asBitmap()
                    .load(item.getIconUrl())
                    .into(mIcon);*/
        } else {
            //   mIcon.setVisibility(View.GONE); //TODO - Enable it once title coming for champion cards
        }

        List<FeedDetail> list = item.getFeedDetails();
        if (StringUtil.isNotEmptyCollection(list)) {
            if (mIsSearch) {
                StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(gridLayoutManager);
            } else {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                mRecyclerView.setLayoutManager(linearLayoutManager);
            }

            mAdapter = new CarouselListAdapter(context, mViewInterface, item, this, mIsSearch);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setData(item.getFeedDetails());
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof UserSolrObj) {
                    boolean isSuggested = ((UserSolrObj) list.get(i)).isSuggested();
                    if (isSuggested) {
                        mIsUpdateFromProfile = true;
                        UserSolrObj userSolrObj = ((UserSolrObj) list.get(i));
                        mAdapter.notifyItemChanged(i, userSolrObj);
                        mRecyclerView.scrollToPosition(userSolrObj.getItemPosition());
                        break;
                    }
                }
            }
            if (!mIsUpdateFromProfile) {
                mAdapter.notifyDataSetChanged();
            }
            if (scrollStatePositionsMap.get(position) != null) {
                mRecyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        mRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                        mRecyclerView.getLayoutManager().onRestoreInstanceState(scrollStatePositionsMap.get(position));
                        return false;
                    }
                });
            }
        }
    }
    //endregion

    //region onclick method
    @OnClick(R.id.icon_container)
    public void onIconClicked() {
        if (mCarouselDataObj != null && mCarouselDataObj.getFeedDetails() != null) {
            if (mViewInterface instanceof AllCommunityItemCallback) {
                ((AllCommunityItemCallback) mViewInterface).onSeeMoreClicked(mCarouselDataObj);
            } else if (mViewInterface instanceof FeedItemCallback) {
                ((FeedItemCallback) mViewInterface).onSeeMoreClicked(mCarouselDataObj);
            } else {
                mViewInterface.handleOnClick(mCarouselDataObj, mIcon);
            }
        }
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {
    }
    //endregion

}

