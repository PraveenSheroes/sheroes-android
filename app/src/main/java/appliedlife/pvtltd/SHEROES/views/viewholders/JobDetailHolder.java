package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobDetailPojo;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ajit Kumar on 20-02-2017.
 */

public class JobDetailHolder extends BaseViewHolder<JobDetailPojo> {
    BaseHolderInterface viewInterface;
    private JobDetailPojo dataItem;
    private FeedDetail mFeedDetail;
    private Context mContext;
    @Bind(R.id.tv_job_des)
    TextView mTvJobDes;
    @Bind(R.id.tv_job_end_date)
    TextView mTvJobEndDate;
    @Bind(R.id.tv_job_start_date)
    TextView mTvJobStartDate;
    @Bind(R.id.tv_job_skill)
    TextView mTvJobSkill;
    @Bind(R.id.tv_job_opt)
    TextView mTvJobOpt;
    @Bind(R.id.tv_job_opening)
    TextView mTvJobOpening;
    @Bind(R.id.tv_job_package)
    TextView mTvJobPackage;
    @Bind(R.id.tv_job_location)
    TextView mTvJobLocation;
    @Bind(R.id.tv_education)
    TextView mTvJobEducation;
    @Inject
    DateUtil mDateUtil;

    public JobDetailHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
    }

    @Override
    public void bindData(JobDetailPojo obj, Context context, int position) {
        dataItem = obj;
        mContext = context;
        mFeedDetail = dataItem.getFeedDetail();
        if (null != mFeedDetail) {
            jobTextViewOperation();
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void jobTextViewOperation() {
        String description =mFeedDetail.getListDescription();
        if (StringUtil.isNotNullOrEmptyString(description)) {
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                mTvJobDes.setText(Html.fromHtml(description, 0)); // for 24 api and more
            } else {
                mTvJobDes.setText(Html.fromHtml(description));// or for older api
            }
        }
        if (StringUtil.isNotEmptyCollection(mFeedDetail.getSearchTextJobEmpTypes())) {
            List<String> jobTypes = mFeedDetail.getSearchTextJobEmpTypes();
            String mergeJobTypes = AppConstants.EMPTY_STRING;
            for (String jobType : jobTypes) {
                mergeJobTypes += jobType + AppConstants.PIPE;
            }
            mTvJobOpt.setText(mergeJobTypes.substring(0, mergeJobTypes.length() - 1));
        } else {
            mTvJobOpt.setVisibility(View.GONE);
        }

        if (StringUtil.isNotEmptyCollection(mFeedDetail.getSearchTextJobSkills())) {
            List<String> jobSkills = mFeedDetail.getSearchTextJobSkills();
            StringBuilder mergeJobSkills = new StringBuilder();
            for (String skill : jobSkills) {
                mergeJobSkills.append(skill);
                mergeJobSkills.append(AppConstants.COMMA);
            }
            mTvJobSkill.setText(mergeJobSkills.toString().substring(0, mergeJobSkills.toString().length() - 1));
        } else {
            mTvJobSkill.setVisibility(View.GONE);
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getStartDate())) {
         //   long startDate = mDateUtil.getTimeInMillis(mFeedDetail.getStartDate(), AppConstants.DATE_FORMAT);
            mTvJobStartDate.setText(mFeedDetail.getStartDate());
        } else {
            mTvJobStartDate.setVisibility(View.GONE);
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getEndDate())) {
         //   long endDate = mDateUtil.getTimeInMillis(mFeedDetail.getEndDate(), AppConstants.DATE_FORMAT);
            mTvJobEndDate.setText(mFeedDetail.getEndDate());
        } else {
            mTvJobEndDate.setVisibility(View.GONE);
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getAuthorCityName())) {
            mTvJobLocation.setText(mFeedDetail.getAuthorCityName());
        } else {
            mTvJobLocation.setText(mContext.getString(R.string.ID_REMOTE));
        }
        if (mFeedDetail.getCompensationFrom() > 0 && mFeedDetail.getCompensationTo() > 0) {
            StringBuilder jobPackage = new StringBuilder();
            jobPackage.append(mFeedDetail.getCompensationCurrency()).append(mFeedDetail.getCompensationFrom()).append(AppConstants.DASH).append(mFeedDetail.getCompensationTo());
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getCompensationDetails())) {
                jobPackage.append(mFeedDetail.getCompensationDetails());
            } else {
                jobPackage.append(AppConstants.PER_ANUM);
            }
            mTvJobPackage.setText(jobPackage.toString());
        }
        else {
            mTvJobPackage.setVisibility(View.GONE);
        }
        if (StringUtil.isNotEmptyCollection(mFeedDetail.getEducationDegreeNames())) {
            StringBuilder education = new StringBuilder();
            for (String degreeName : mFeedDetail.getEducationDegreeNames()) {
                education.append(degreeName);
                education.append(AppConstants.COMMA);
            }
            if (StringUtil.isNotNullOrEmptyString(education.toString())) {
                mTvJobEducation.setText(education.toString().substring(0, education.toString().length() - 1));
            }
        } else {
            mTvJobEducation.setVisibility(View.GONE);
        }
        if (mFeedDetail.getNoOfOpenings() > 0) {
            mTvJobOpening.setText(mFeedDetail.getNoOfOpenings() + AppConstants.SPACE + mContext.getString(R.string.ID_JOB_OPENING));
        } else {
            mTvJobOpening.setVisibility(View.GONE);
        }


    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View v) {

    }
}
