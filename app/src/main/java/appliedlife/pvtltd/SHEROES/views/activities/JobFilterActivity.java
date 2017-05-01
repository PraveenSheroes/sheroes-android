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
import appliedlife.pvtltd.SHEROES.views.fragments.JobFilterFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.OnBoardingSearchDialogFragment;
import butterknife.ButterKnife;

/**
 * Created by Ajit Kumar on 13-02-2017.
 */

public class JobFilterActivity extends BaseActivity {
    private final String TAG = LogUtils.makeLogTag(JobFilterActivity.class);
    private OnBoardingSearchDialogFragment mOnBoardingSearchDialogFragment;
    public List<String> listOfOpportunity = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        renderLoginFragmentView();
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
            LabelValue labelValue = (LabelValue) view.getTag();
            if (labelValue.isSelected()) {
                if (StringUtil.isNotEmptyCollection(listOfOpportunity)) {
                    listOfOpportunity.remove(labelValue.getLabel());
                }
            } else {
                listOfOpportunity.add(labelValue.getLabel());
            }

        } else if (baseResponse instanceof GetAllDataDocument) {
            GetAllDataDocument getAllDataDocument = (GetAllDataDocument) baseResponse;
            if (null != mOnBoardingSearchDialogFragment) {
                mOnBoardingSearchDialogFragment.dismiss();
            }
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(JobFilterFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((JobFilterFragment) fragment).locationData(getAllDataDocument);
            }
        }
    }

    public void openJobLocationFragment() {

      /*  JobLocationFilter articlesFragment = new JobLocationFilter();
        Bundle bundle = new Bundle();
        articlesFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.job_filter_container, articlesFragment).addToBackStack(null).commitAllowingStateLoss();*/
    }

    public void openJobFunctionalAreaFragment() {
       /* JobFunctionalAreaFragment articlesFragment = new JobFunctionalAreaFragment();
        Bundle bundle = new Bundle();
        articlesFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.job_filter_container, articlesFragment).addToBackStack(null).commitAllowingStateLoss();*/
    }

    @Override
    public void onBackPressed() {
        // code here to show dialog
        finish();
    }

    public DialogFragment searchLocationData(String masterDataSkill, OnBoardingEnum onBoardingEnum) {
        mOnBoardingSearchDialogFragment = (OnBoardingSearchDialogFragment) getFragmentManager().findFragmentByTag(OnBoardingSearchDialogFragment.class.getName());
        if (mOnBoardingSearchDialogFragment == null) {
            mOnBoardingSearchDialogFragment = new OnBoardingSearchDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstants.BOARDING_SEARCH, onBoardingEnum);
            bundle.putString(AppConstants.MASTER_SKILL, masterDataSkill);
            bundle.putBoolean(AppConstants.JOB_FILTER, true);
            mOnBoardingSearchDialogFragment.setArguments(bundle);
        }
        if (!mOnBoardingSearchDialogFragment.isVisible() && !mOnBoardingSearchDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            mOnBoardingSearchDialogFragment.show(getFragmentManager(), OnBoardingSearchDialogFragment.class.getName());
        }
        return mOnBoardingSearchDialogFragment;
    }
}
