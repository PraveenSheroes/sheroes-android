package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ExprienceEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SHEROES-TECH on 16-02-2017.
 */

public class ProfileWorkExperienceHolder extends BaseViewHolder<MyProfileView> {
    @Bind(R.id.li_no_item_row)
    LinearLayout mLiNoItem;
    @Bind(R.id.tv_work_exp_lable)
    TextView mTvJobLanguageNumber;
    @Bind(R.id.tv_position)
    TextView mTvPosition;
    @Bind(R.id.tv_date)
    TextView mTvDate;
    @Bind(R.id.tv_commany_name)
    TextView mTvCompanyName;
    @Bind(R.id.tv_type)
    TextView mTvType;
    @Bind(R.id.tv_add_work_exp)
    TextView mTvAddWorkExp;
    BaseHolderInterface viewInterface;
    private MyProfileView dataItem;
    private ExprienceEntity workExprienceEntity;

    public ProfileWorkExperienceHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(MyProfileView myProfileView, Context context, int position) {
        this.dataItem = myProfileView;
        if (null != dataItem) {
            mTvJobLanguageNumber.setText(dataItem.getType());
            List<ExprienceEntity> exprienceEntities = dataItem.getExprienceEntity();
            if (StringUtil.isNotEmptyCollection(exprienceEntities)) {
                workExprienceEntity = exprienceEntities.get(0);
                if (null != workExprienceEntity.getId() && workExprienceEntity.getId() > 0) {
                    mLiNoItem.setVisibility(View.VISIBLE);
                } else {
                    mLiNoItem.setVisibility(View.GONE);
                }
                if (StringUtil.isNotNullOrEmptyString(workExprienceEntity.getTitle())) {
                    mTvPosition.setText(workExprienceEntity.getTitle());
                }
                if (StringUtil.isNotNullOrEmptyString(workExprienceEntity.getCompany())) {
                    mTvCompanyName.setText(workExprienceEntity.getCompany());
                }
                if (StringUtil.isNotNullOrEmptyString(workExprienceEntity.getAboutOrg())) {
                    mTvType.setText(workExprienceEntity.getAboutOrg());
                }
                if (workExprienceEntity.isCurrentlyWorkingHere()) {
                    if (workExprienceEntity.getStartYear() > 0) {
                        StringBuilder currentWork = new StringBuilder();
                        currentWork.append(workExprienceEntity.getStartYear()).append(AppConstants.DASH).append(AppConstants.PRESENT);
                        mTvDate.setText(currentWork.toString());
                    }
                } else {
                    if (workExprienceEntity.getStartYear() > 0) {
                        StringBuilder session = new StringBuilder();
                        if (workExprienceEntity.getEndYear() > 0) {
                            session.append(AppConstants.LEFT_BRACKET).append(workExprienceEntity.getStartYear()).append(AppConstants.DASH).append(workExprienceEntity.getEndYear()).append(AppConstants.RIGHT_BRACKET);
                        } else {
                            session.append(AppConstants.LEFT_BRACKET).append(workExprienceEntity.getStartYear()).append(AppConstants.RIGHT_BRACKET);
                        }
                        mTvDate.setText(session);
                    }
                }
            }
        }

    }

    @OnClick(R.id.tv_more)
    public void onClickViewMore() {
        viewInterface.handleOnClick(this.dataItem, mTvAddWorkExp);
    }

    @OnClick(R.id.tv_add_work_exp)
    public void onClickAddEducation() {
        viewInterface.handleOnClick(this.dataItem, mTvAddWorkExp);
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {

    }
}
