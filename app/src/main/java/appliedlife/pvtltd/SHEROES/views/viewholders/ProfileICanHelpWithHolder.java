package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePersonalViewList;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sheroes on 17/02/17.
 */

public class ProfileICanHelpWithHolder extends BaseViewHolder<ProfilePersonalViewList> {
    @Bind(R.id.tv_lookinfor_number)
    TextView mTv_lookinfor_number;
    @Bind(R.id.tv_lookingfor_text1)
    TextView mTv_lookingfor_text1;
    @Bind(R.id.tv_lookingfor_text2)
    TextView mTv_lookingfor_text2;
    @Bind(R.id.tv_looking_more)
    TextView mTv_looking_more;
    BaseHolderInterface viewInterface;

    private ProfilePersonalViewList dataItem;


    public ProfileICanHelpWithHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    public ProfileICanHelpWithHolder(View itemView, EditNameDialogListener baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }



    @Override
    public void bindData(ProfilePersonalViewList obj, Context context, int position) {

        this.dataItem = obj;
        itemView.setOnClickListener(this);

        mTv_lookinfor_number.setText(dataItem.getTag());
        mTv_lookingfor_text1.setText(dataItem.getItem1());
        mTv_lookingfor_text2.setText(dataItem.getItem2());

    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

        //viewInterface.handleOnClick(this.dataItem,view);


    }
}
