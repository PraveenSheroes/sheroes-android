package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
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
import butterknife.OnClick;

/**
 * Created by SHEROES-TECH on 08-03-2017.
 */

public class OwnerHolder extends BaseViewHolder<Member> {
    private final String TAG = LogUtils.makeLogTag(OwnerHolder.class);
    @Bind(R.id.iv_user)
    CircleImageView ivFeedUserCircleIcon;
    @Bind(R.id.tv_owner_title)
    TextView tvowOer_title;
    @Bind(R.id.tv_owner_data)
    TextView tvOwner;
    @Bind(R.id.tv_owner_cross_from_open_about)
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

    @Override
    public void bindData(Member obj, Context context, int position) {
        this.dataItem = obj;
        this.mContext = context;
        allTextViewStringOperations(context, position);
    }

    private void allTextViewStringOperations(Context context, int position) {
        if (StringUtil.isNotNullOrEmptyString(dataItem.getComName())) {
            tvowOer_title.setText(dataItem.getCommunityUserFirstName());
        }

        if (StringUtil.isNotNullOrEmptyString(dataItem.getCommunityUserPhotoUrlPath())) {
            ivFeedUserCircleIcon.setCircularImage(true);
            ivFeedUserCircleIcon.bindImage(dataItem.getCommunityUserPhotoUrlPath());
        }
        tvOwner.setText(mContext.getString(R.string.ID_ADMIN) + (position + 1));
        if (dataItem.isOwner()) {
            mTvownerclose.setVisibility(View.VISIBLE);
            tvOwner.setTextColor(ContextCompat.getColor(context, R.color.red_oval_shap));
        } else {
            mTvownerclose.setVisibility(View.GONE);
            tvOwner.setText(AppConstants.EMPTY_STRING);
        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_owner_cross_from_open_about)
    public void onOwnerCrossClick() {
        viewInterface.handleOnClick(dataItem, mTvownerclose);
    }

    @Override
    public void onClick(View view) {


    }

}