package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.Member;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SHEROES-TECH on 08-03-2017.
 */

public class OwnerHolder extends BaseViewHolder<Member> {
    private final String TAG = LogUtils.makeLogTag(UserHolder.class);
    @Bind(R.id.iv_user)
    CircleImageView ivFeedUserCircleIcon;
    @Bind(R.id.tv_owner_title)
    TextView tvowOer_title;
    @Bind(R.id.tv_owner)
    TextView tvOwner;
    @Bind(R.id.tv_owner_cross)
    TextView mTvownerclose;
    BaseHolderInterface viewInterface;
    private Member dataItem;
    Context mContext;

    public OwnerHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        tvowOer_title.setOnClickListener(this);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(dataItem.getComName())) {
            tvowOer_title.setText(dataItem.getCommunityUserFirstName());
          //  tvOwner.setText("New Delhi");
        }

        if (StringUtil.isNotNullOrEmptyString(dataItem.getCommunityUserPhotoUrlPath())) {
            ivFeedUserCircleIcon.setCircularImage(true);
            ivFeedUserCircleIcon.bindImage(dataItem.getCommunityUserPhotoUrlPath());
        }
        if(dataItem.getIsOwner())
        {
            mTvownerclose.setVisibility(View.VISIBLE);
        }
        else
        {
            mTvownerclose.setVisibility(View.GONE);

        }
    }


    @Override
    public void bindData(Member obj, Context context, int position) {
        this.dataItem = obj;
        this.mContext = context;
        // imageOperations(context);
        mTvownerclose.setOnClickListener(this);
        allTextViewStringOperations(context);
        tvOwner.setText("Admin "+(position+1));
       // tvOwner.setTextColor(context.getColor(R.color.red_oval_shap));
        tvOwner.setTextColor(ContextCompat.getColor(context, R.color.red_oval_shap));



    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.tv_owner_cross:
                viewInterface.handleOnClick(this.dataItem,view);
                break;
            default:
                LogUtils.error("", AppConstants.CASE_NOT_HANDLED + " " + "" + " " + id);
        }



    }

}