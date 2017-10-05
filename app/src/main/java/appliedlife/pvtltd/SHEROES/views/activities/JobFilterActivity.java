package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.FunctionalAreaDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobFilterFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.JobLocationSearchDialogFragment;
import butterknife.ButterKnife;

/**
 * Created by Ajit Kumar on 13-02-2017.
 */

public class JobFilterActivity extends BaseActivity {
    private static final String SCREEN_LABEL = "Job Filter Screen";
    private final String TAG = LogUtils.makeLogTag(JobFilterActivity.class);
    private JobLocationSearchDialogFragment jobLocationSearchDialogFragment;
    private FunctionalAreaDialogFragment functionalAreaDialogFragment;
    public List<String> mListOfOpportunity = new ArrayList<>();
    public List<String> mFunctionArea = new ArrayList<>();
    private List<String> mJobLocationList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        renderLoginFragmentView();
        ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_JOB_FILTERS));
    }

    public void renderLoginFragmentView() {
        setContentView(R.layout.job_filter_activity);
        ButterKnife.bind(this);
        JobFilterFragment frag = new JobFilterFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.job_filter_container, frag, JobFilterFragment.class.getName()).addToBackStack(JobFilterFragment.class.getName()).commitAllowingStateLoss();

    }

    public void applyFilterData(FeedRequestPojo feedRequestPojo) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.JOB_FILTER, feedRequestPojo);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof OnBoardingData) {
            OnBoardingData onBoardingData = (OnBoardingData) baseResponse;
            if (null != onBoardingData && StringUtil.isNotNullOrEmptyString(onBoardingData.getFragmentName())) {
                LabelValue labelValue = (LabelValue) view.getTag();
                if (onBoardingData.getFragmentName().equalsIgnoreCase(AppConstants.JOB_DATA_OPPORTUNITY_KEY)) {
                    if (labelValue.isSelected()) {
                        if (StringUtil.isNotEmptyCollection(mListOfOpportunity)) {
                            mListOfOpportunity.remove(labelValue.getLabel());
                        }
                    } else {
                        mListOfOpportunity.add(labelValue.getLabel());
                    }
                } else {
                    if (labelValue.isSelected()) {
                        if (StringUtil.isNotEmptyCollection(mFunctionArea)) {
                            mFunctionArea.remove(labelValue.getLabel());
                        }
                    } else {
                        mFunctionArea.add(labelValue.getLabel());
                    }
                }

            }

        } else if (baseResponse instanceof GetAllDataDocument) {
            GetAllDataDocument getAllDataDocument = (GetAllDataDocument) baseResponse;
            if (StringUtil.isNotNullOrEmptyString(getAllDataDocument.getTitle())) {
                if (!getAllDataDocument.isChecked()) {
                    mJobLocationList.add(getAllDataDocument.getTitle());
                } else {
                    if (StringUtil.isNotEmptyCollection(mJobLocationList)) {
                        mJobLocationList.remove(getAllDataDocument.getTitle());
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        // code here to show dialog
        finish();
    }

    public void saveJobLocation() {
        if (null != jobLocationSearchDialogFragment) {
            jobLocationSearchDialogFragment.dismiss();
        }
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(JobFilterFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((JobFilterFragment) fragment).locationData(mJobLocationList);
        }
    }

    public DialogFragment searchLocationData(String masterDataSkill, OnBoardingEnum onBoardingEnum) {
        jobLocationSearchDialogFragment = (JobLocationSearchDialogFragment) getFragmentManager().findFragmentByTag(JobLocationSearchDialogFragment.class.getName());
        if (jobLocationSearchDialogFragment == null) {
            jobLocationSearchDialogFragment = new JobLocationSearchDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.MASTER_SKILL, masterDataSkill);
            jobLocationSearchDialogFragment.setArguments(bundle);
        }
        if (!jobLocationSearchDialogFragment.isVisible() && !jobLocationSearchDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            jobLocationSearchDialogFragment.show(getFragmentManager(), JobLocationSearchDialogFragment.class.getName());
        }
        return jobLocationSearchDialogFragment;
    }
    public DialogFragment functionAreaData() {
        functionalAreaDialogFragment = (FunctionalAreaDialogFragment) getFragmentManager().findFragmentByTag(FunctionalAreaDialogFragment.class.getName());
        if (functionalAreaDialogFragment == null) {
            functionalAreaDialogFragment = new FunctionalAreaDialogFragment();
            Bundle bundle = new Bundle();
            functionalAreaDialogFragment.setArguments(bundle);
        }
        if (!functionalAreaDialogFragment.isVisible() && !functionalAreaDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            functionalAreaDialogFragment.show(getFragmentManager(), FunctionalAreaDialogFragment.class.getName());
        }
        return functionalAreaDialogFragment;
    }
    public void onDoneFunctionArea() {
        if (null != functionalAreaDialogFragment) {
            functionalAreaDialogFragment.dismiss();
            Fragment fragmentJobFilter = getSupportFragmentManager().findFragmentByTag(JobFilterFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragmentJobFilter)) {
                ((JobFilterFragment) fragmentJobFilter).setFunctionAreaDataItem(mFunctionArea);
            }
        }
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}
