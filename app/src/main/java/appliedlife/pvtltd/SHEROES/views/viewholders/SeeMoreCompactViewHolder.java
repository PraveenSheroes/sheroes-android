package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.AllCommunityItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ujjwal on 08/03/17.
 */

public class SeeMoreCompactViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private final BaseHolderInterface viewInterface;
    private CarouselDataObj mCarouselDataObj;

    @Bind(R.id.more_button)
    FloatingActionButton mMoreButton;

    @Bind(R.id.more_text)
    TextView mMoreText;

    public SeeMoreCompactViewHolder(View seeMoreView, BaseHolderInterface baseHolderInterface, CarouselDataObj carouselDataObj) {
        super(seeMoreView);
        ButterKnife.bind(this, seeMoreView);
        viewInterface = baseHolderInterface;
        this.mCarouselDataObj = carouselDataObj;
    }

    public void bindData() {

    }

    @OnClick({R.id.more_button, R.id.more_text})
    public void showMoreClicked(){
        if(viewInterface instanceof AllCommunityItemCallback){
            ((AllCommunityItemCallback)viewInterface).onSeeMoreClicked(mCarouselDataObj);
        }
        if(viewInterface instanceof FeedItemCallback){
            ((FeedItemCallback)viewInterface).onSeeMoreClicked(mCarouselDataObj);
        }
    }

}
