package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.media.Image;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.AllCommunityItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
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
    private BaseHolderInterface viewInterface;
    private CarouselDataObj carouselDataObj;
    private boolean isUpdateFromProfile;
    private SparseArray<Parcelable> scrollStatePositionsMap = new SparseArray<>();
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

    private int position;
    //endregion

    //region constructor
    public CarouselViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE) {
                    scrollStatePositionsMap.put(position, recyclerView.getLayoutManager().onSaveInstanceState());
                }
            }
        });
    }
    //endregion

    //region public method
    @Override
    public void bindData(CarouselDataObj item, final Context context, final int position) {
        this.carouselDataObj = item;
        this.position = position;
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
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new CarouselListAdapter(context, viewInterface, item, this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setData(item.getFeedDetails());
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof UserSolrObj) {
                    boolean isSuggested = ((UserSolrObj) list.get(i)).isSuggested();
                    if (isSuggested) {
                        isUpdateFromProfile = true;
                        UserSolrObj userSolrObj = ((UserSolrObj) list.get(i));
                        mAdapter.notifyItemChanged(i, userSolrObj);
                        mRecyclerView.scrollToPosition(userSolrObj.getItemPosition());
                        break;
                    }
                }
            }
            if (!isUpdateFromProfile) {
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
        /*if (carouselDataObj != null && carouselDataObj.getUserSolrObj() != null && carouselDataObj.getUserSolrObj().get(0) != null) {
            if (carouselDataObj.getUserSolrObj().get(0) instanceof UserSolrObj) {
                if (viewInterface instanceof AllCommunityItemCallback) {
                    ((AllCommunityItemCallback) viewInterface).openChampionListingScreen(carouselDataObj);
                } else {
                    viewInterface.handleOnClick(carouselDataObj, mIcon);
                }
            } else {
                if (viewInterface instanceof AllCommunityItemCallback) {
                    ((AllCommunityItemCallback) viewInterface).onSeeMoreClicked(carouselDataObj);
                }else if(viewInterface instanceof FeedItemCallback){
                    ((FeedItemCallback)viewInterface).onSeeMoreClicked(carouselDataObj);
                } else {
                    viewInterface.handleOnClick(carouselDataObj, mIcon);
                }
            }
        }*/

        if(carouselDataObj!=null && carouselDataObj.getFeedDetails()!=null){
            if (viewInterface instanceof AllCommunityItemCallback) {
                ((AllCommunityItemCallback) viewInterface).onSeeMoreClicked(carouselDataObj);
            }else if(viewInterface instanceof FeedItemCallback){
                ((FeedItemCallback)viewInterface).onSeeMoreClicked(carouselDataObj);
            } else {
                viewInterface.handleOnClick(carouselDataObj, mIcon);
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

