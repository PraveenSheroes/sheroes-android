package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.FilterList;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.CreateCommunityFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobFilterFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobFunctionalAreaFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobLocationFilter;
import butterknife.ButterKnife;

/**
 * Created by Ajit Kumar on 13-02-2017.
 */

public class JobFilterActivity extends BaseActivity implements JobLocationFilter.HomeActivityJobLocationIntractionListner {
    private final String TAG = LogUtils.makeLogTag(JobFilterActivity.class);
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
                .replace(R.id.job_filter_container, frag,CreateCommunityFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

    }
    public void applyFilterData(FeedRequestPojo feedRequestPojo)
    {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.JOB_FILTER, feedRequestPojo);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FilterList) {
            int drawerItem = Integer.parseInt(((FilterList) baseResponse).getId());

            switch (drawerItem) {
                case 1:
                    break;
                case 2:
                    openJobLocationFragment();
                    break;
                case 3:
                    openJobFunctionalAreaFragment();
                    break;
                case 5:
                    // openSettingFragment();
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + drawerItem);
            }
        }
    }
    public void openJobLocationFragment()
    {
        JobLocationFilter articlesFragment = new JobLocationFilter();
        Bundle bundle = new Bundle();
        articlesFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.job_filter_container, articlesFragment).addToBackStack(null).commitAllowingStateLoss();
    }
    public void openJobFunctionalAreaFragment()
    {
        JobFunctionalAreaFragment articlesFragment = new JobFunctionalAreaFragment();
        Bundle bundle = new Bundle();
        articlesFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.job_filter_container, articlesFragment).addToBackStack(null).commitAllowingStateLoss();
    }
    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {

    }

    @Override
    public void setListData(BaseResponse data, boolean flag) {

    }



    @Override
    public List getListData() {
        return null;
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {

    }

    @Override
    public void onErrorOccurence() {

    }

    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen) {

    }

    @Override
    public void onClickSaveLocationList() {
        getSupportFragmentManager().popBackStack();

    }
    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        finish();
    }
}
