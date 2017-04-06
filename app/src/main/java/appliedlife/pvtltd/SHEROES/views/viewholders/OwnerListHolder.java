package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerList;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ajit Kumar on 03-02-2017.
 */

public class OwnerListHolder extends BaseViewHolder<OwnerList> {
    @Bind(R.id.textView1)
    TextView tvCity;
    @Bind(R.id.tv_owner)
    TextView tv_owner;
    @Bind(R.id.img1)
    CircleImageView background;
    @Bind(R.id.tv_owner_add)
    TextView mTvownerclose;
    BaseHolderInterface viewInterface;
    private OwnerList dataItem;
    private int position;

    public OwnerListHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    public OwnerListHolder(View itemView, EditNameDialogListener baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }


    @Override
    public void bindData(OwnerList obj, Context context, int position) {
        this.dataItem = obj;
        mTvownerclose.setOnClickListener(this);

        tvCity.setText(dataItem.getName());
        int pos=position+1;
        tv_owner.setText("Admin"+pos);
        String images = dataItem.getBackground();

        background.setCircularImage(true);
        background.bindImage(images);
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.tv_owner_add:
                mTvownerclose.setBackgroundResource(R.drawable.selected_add_btn_shap);
                viewInterface.handleOnClick(this.dataItem,view);
                break;
            default:
                LogUtils.error("", AppConstants.CASE_NOT_HANDLED + " " + "" + " " + id);
        }

    }
}
