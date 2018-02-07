package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.AllCommunityItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
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
    private final String TAG = LogUtils.makeLogTag(CarouselViewHolder.class);
    public CarouselListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Bind(R.id.icon)
    ImageView mIcon;

    @Bind(R.id.title)
    TextView mTitle;

    @Bind(R.id.body)
    TextView mBody;

    @Bind(R.id.rv_suggested_mentor_list)
    RecyclerView mRecyclerView;

    BaseHolderInterface viewInterface;
    private CarouselDataObj carouselDataObj;

    public CarouselViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(CarouselDataObj item, final Context context, int position) {
        this.carouselDataObj = item;
        if (StringUtil.isNotNullOrEmptyString(item.getTitle())) {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(item.getTitle());
        } else {
          //  mTitle.setVisibility(View.GONE);
        }

        if (StringUtil.isNotNullOrEmptyString(item.getBody())) {
            mBody.setVisibility(View.VISIBLE);
            mBody.setText(item.getBody());
        } else {
            mBody.setVisibility(View.GONE);
        }

        if (CommonUtil.isNotEmpty(item.getIconUrl())) {
            mIcon.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .asBitmap()
                    .load(item.getIconUrl())
                    .into(mIcon);
        } else {
         //   mIcon.setVisibility(View.GONE);
        }

        List<FeedDetail> list=item.getFeedDetails();
        if(StringUtil.isNotEmptyCollection(list)) {
            mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new CarouselListAdapter(context, viewInterface, item, this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.scrollToPosition(carouselDataObj.getItemPosition());
            mAdapter.setData(item.getFeedDetails());
            mAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.icon)
    public void onIconClicked() {
        if (CommonUtil.isNotEmpty(carouselDataObj.getEndPointUrl()) && CommonUtil.isNotEmpty(carouselDataObj.getScreenTitle())) {
            if(carouselDataObj.getFeedDetails().get(0) instanceof UserSolrObj){
                if (viewInterface instanceof AllCommunityItemCallback) {
                    ((AllCommunityItemCallback) viewInterface).openChampionListingScreen(carouselDataObj);
                } else {
                    viewInterface.handleOnClick(carouselDataObj, mIcon);
                }
            }else {
                if (viewInterface instanceof AllCommunityItemCallback) {
                    ((AllCommunityItemCallback) viewInterface).onSeeMoreClicked(carouselDataObj);
                } else {
                    viewInterface.handleOnClick(carouselDataObj, mIcon);
                }
            }
        }
    }

    @Override
    public void viewRecycled() {

    }
    @Override
    public void onClick(View view) {
    }

}

