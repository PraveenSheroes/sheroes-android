package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.InputStream;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LookingForLableValues;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 16/11/17.
 */

public class LookingForJobHolder extends BaseViewHolder<LookingForLableValues> {
    private final String TAG = LogUtils.makeLogTag(LookingForJobHolder.class);
    BaseHolderInterface viewInterface;
    @Bind(R.id.tv_job_tag)
    TextView tvLable;
    @Bind(R.id.tv_description_job)
    TextView tvDescription;
    @Bind(R.id.iv_job_looking_for_circle_icon)
    CircleImageView ivJobLookigCircleIcon;
    @Bind(R.id.rl_job_option)
    RelativeLayout mRlJobOption;
    private LookingForLableValues dataItem;
    private Context mContext;


    public LookingForJobHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
    }
    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public void bindData(LookingForLableValues lookingForLableValues, Context context, int position) {
        dataItem = lookingForLableValues;
        dataItem.setPosition(position);
        mContext = context;
        if(dataItem.isSelected())
        {
            mRlJobOption.setBackgroundResource(R.drawable.round_on_board_work);
            mRlJobOption.setElevation(6.0f);
        }else
        {
            mRlJobOption.setBackgroundResource(0);
            mRlJobOption.setElevation(0);
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getLabel())) {
            tvLable.setText(dataItem.getLabel());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getDesc())) {
            tvDescription.setText(dataItem.getDesc());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getImgUrl())) {
            ivJobLookigCircleIcon.setCircularImage(true);
            ivJobLookigCircleIcon.bindImage(dataItem.getImgUrl());
        }
        dataItem.setSelected(false);
    }
    @OnClick(R.id.rl_job_option)
    public void jobCardOptionClick() {
        dataItem.setSelected(true);
        viewInterface.handleOnClick(dataItem, mRlJobOption);
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {

    }

}
