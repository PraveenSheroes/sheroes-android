package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePersonalViewList;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileViewList;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sheroes on 02/03/17.
 */

public class VisitingCardholder  extends BaseViewHolder<ProfileViewList> {

    @Bind(R.id.tv_my_contacct_card)
    TextView mTv_contacct_my_card;
    @Bind(R.id.tv_download_my_card)
    TextView mtv_download_my_card;




    BaseHolderInterface viewInterface;
    private ProfileViewList dataItem;


    public VisitingCardholder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    public VisitingCardholder(View itemView, EditNameDialogListener baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }


    @Override
    public void bindData(ProfileViewList obj, Context context, int position) {
        this.dataItem = obj;
        mTv_contacct_my_card.setOnClickListener(this);

        mTv_contacct_my_card.setText(dataItem.getTag());
        mtv_download_my_card.setText(dataItem.getItem1());




    }

    @Override
    public void viewRecycled() {

    }
    @Override
    public void onClick(View view) {

        // viewInterface.handleOnClick(this.dataItem,view);
    }
}
