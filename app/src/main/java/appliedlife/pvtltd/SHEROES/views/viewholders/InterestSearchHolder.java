package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingInterestJobSearch;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 26-03-2017.
 */

public class InterestSearchHolder extends BaseViewHolder<BoardingInterestJobSearch> {
    private final String TAG = LogUtils.makeLogTag(InterestSearchHolder.class);
    BaseHolderInterface viewInterface;
    private BoardingInterestJobSearch dataItem;
    private Context mContext;
    @Bind(R.id.tv_interest_job_tag)
    TextView tvInterestJobTag;

    public InterestSearchHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(BoardingInterestJobSearch item, final Context context, int position) {
        dataItem = item;
        mContext = context;
        if (null != dataItem && StringUtil.isNotNullOrEmptyString(dataItem.getTitle())) {
            tvInterestJobTag.setText(dataItem.getTitle());
        }
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {

    }

    @OnClick(R.id.tv_interest_job_tag)
    public void onSearchTagClick() {
        dataItem.setOnBoardingEnum(OnBoardingEnum.INTEREST_SEARCH);
        LabelValue labelValue =new LabelValue();
        labelValue.setLabel(dataItem.getTitle());
        labelValue.setValue(Long.parseLong(dataItem.getId()));
        tvInterestJobTag.setTag(labelValue);
        viewInterface.handleOnClick(dataItem, tvInterestJobTag);
    }
}