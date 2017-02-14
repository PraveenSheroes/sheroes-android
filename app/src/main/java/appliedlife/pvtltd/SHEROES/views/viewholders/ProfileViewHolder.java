package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.ProfileItems;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 13-02-2017.
 */

public class ProfileViewHolder  extends BaseViewHolder<ProfileItems> {
    private final String TAG = LogUtils.makeLogTag(ProfileViewHolder.class);
    BaseHolderInterface viewInterface;
    private ProfileItems dataItem;


    public ProfileViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ProfileItems item, final Context context, int position) {
        this.dataItem = item;

    }


    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {


    }

}
