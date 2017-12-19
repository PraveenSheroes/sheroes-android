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
import appliedlife.pvtltd.SHEROES.models.entities.feed.JobFeedSolrObj;
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
    private JobFeedSolrObj jobFeedObj;
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
        jobFeedObj = (JobFeedSolrObj) dataItem.getFeedDetail();
        if (null != jobFeedObj) {
            jobTextViewOperation();
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void jobTextViewOperation() {
        String description =jobFeedObj.getListDescription();
        if (StringUtil.isNotNullOrEmptyString(description)) {
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                mTvJobDes.setText(Html.fromHtml(description, 0)); // for 24 api and more
            } else {
                mTvJobDes.setText(Html.fromHtml(description));// or for older api
            }
        }
        if (StringUtil.isNotEmptyCollection(jobFeedObj.getSearchTextJobEmpTypes())) {
            List<String> jobTypes = jobFeedObj.getSearchTextJobEmpTypes();
            String mergeJobTypes = AppConstants.EMPTY_STRING;
            for (String jobType : jobTypes) {
                mergeJobTypes += jobType + AppConstants.PIPE;
            }
            mTvJobOpt.setText(mergeJobTypes.substring(0, mergeJobTypes.length() - 1));
        } else {
            mTvJobOpt.setVisibility(View.GONE);
        }

        if (StringUtil.isNotEmptyCollection(jobFeedObj.getSearchTextJobSkills())) {
            List<String> jobSkills = jobFeedObj.getSearchTextJobSkills();
            StringBuilder mergeJobSkills = new StringBuilder();
            for (String skill : jobSkills) {
                mergeJobSkills.append(skill);
                mergeJobSkills.append(AppConstants.COMMA);
                mergeJobSkills.append(AppConstants.SPACE);
            }
            String skills = mergeJobSkills.toString();
            if(skills.length()>2) {
                mTvJobSkill.setText(skills.substring(0, skills.length() - 2));
            }
        } else {
            mTvJobSkill.setVisibility(View.GONE);
        }
        if (StringUtil.isNotNullOrEmptyString(jobFeedObj.getFormattedSolrStartDate())) {
            mTvJobStartDate.setText(jobFeedObj.getFormattedSolrStartDate());
        } else {
            mTvJobStartDate.setVisibility(View.GONE);
        }
        if (StringUtil.isNotNullOrEmptyString(jobFeedObj.getFormattedSolrEndDate())) {
            mTvJobEndDate.setText(jobFeedObj.getFormattedSolrEndDate());
        } else {
            mTvJobEndDate.setVisibility(View.GONE);
        }
        if (StringUtil.isNotNullOrEmptyString(jobFeedObj.getAuthorCityName())) {
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append(jobFeedObj.getAuthorCityName()).append(AppConstants.COMMA).append(AppConstants.COUNTRY_NAME);
            mTvJobLocation.setText(stringBuilder.toString());
        } else {
            mTvJobLocation.setText(mContext.getString(R.string.ID_REMOTE));
        }
        if (jobFeedObj.getCompensationFrom() > 0 && jobFeedObj.getCompensationTo() > 0) {
            StringBuilder jobPackage = new StringBuilder();
            if(StringUtil.isNotNullOrEmptyString(jobFeedObj.getCompensationCurrency())) {
                jobPackage.append(jobFeedObj.getCompensationCurrency()).append(jobFeedObj.getCompensationFrom()).append(AppConstants.DASH).append(jobFeedObj.getCompensationTo());
            }
            else
            {
                jobPackage.append(jobFeedObj.getCompensationFrom()).append(AppConstants.DASH).append(jobFeedObj.getCompensationTo());
            }
            if (StringUtil.isNotNullOrEmptyString(jobFeedObj.getCompensationDetails())) {
                jobPackage.append(AppConstants.SPACE).append(jobFeedObj.getCompensationDetails());
            } else {
                jobPackage.append(AppConstants.SPACE).append(AppConstants.PER_ANUM);
            }
            mTvJobPackage.setText(jobPackage.toString());
        }
        else {
            mTvJobPackage.setVisibility(View.GONE);
        }
        // TODO : ujjwal
       /* if (StringUtil.isNotEmptyCollection(jobFeedObj.getEducationDegreeNames())) {
            StringBuilder education = new StringBuilder();
            for (String degreeName : jobFeedObj.getEducationDegreeNames()) {
                education.append(degreeName);
                education.append(AppConstants.COMMA);
            }
            if (StringUtil.isNotNullOrEmptyString(education.toString())) {
                mTvJobEducation.setText(education.toString().substring(0, education.toString().length() - 1));
            }
        } else {
            mTvJobEducation.setVisibility(View.GONE);
        }*/
        if (jobFeedObj.getNoOfOpenings() > 0) {
            mTvJobOpening.setText(jobFeedObj.getNoOfOpenings() + AppConstants.SPACE + mContext.getString(R.string.ID_JOB_OPENING));
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
