package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Priyanka on 16-02-2017.
 */

public class ProfileHorizantalListHolder extends BaseViewHolder<MyProfileView> {
    @Bind(R.id.tv_location_txt)
    TextView mTv_location_txt;
    @Bind(R.id.tv_location)
    TextView mTv_location;
    @Bind(R.id.tv_edit_other_text)
    TextView mtv_edit_other_text;
    BaseHolderInterface viewInterface;
    private MyProfileView dataItem;
    private final String TAG = LogUtils.makeLogTag(ProfileHorizantalListHolder.class);


    public ProfileHorizantalListHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }


    @Override
    public void bindData(MyProfileView profileView, Context context, int position) {
        this.dataItem = profileView;
        mtv_edit_other_text.setOnClickListener(this);
        mTv_location_txt.setText(dataItem.getType());
        //mTv_location.setText(dataItem.get);
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.tv_edit_other_text:

                viewInterface.handleOnClick(this.dataItem,mtv_edit_other_text);

                break;

            default:
                 LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + view.getId());
        }



    }
}
