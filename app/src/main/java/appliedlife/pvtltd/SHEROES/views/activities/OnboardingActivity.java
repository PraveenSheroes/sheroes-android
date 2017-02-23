package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.views.fragments.CreateCommunityFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.OnboardingFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SheroesHelpYouFragment;
import butterknife.ButterKnife;

/**
 * Created by Ajit Kumar on 22-02-2017.
 */

public class OnboardingActivity extends BaseActivity implements OnboardingFragment.OnBoardingActivityIntractionListner, BaseHolderInterface {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        renderLoginFragmentView();


    }
    public void renderLoginFragmentView() {
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);

        OnboardingFragment frag = new OnboardingFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.onboarding_container, frag,CreateCommunityFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

    }

    @Override
    public void close() {

    }

    @Override
    public void onErrorOccurence() {

    }

    @Override
    public void callSheroesHelpYouTagPage() {
        SheroesHelpYouFragment frag = new SheroesHelpYouFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.onboarding_container, frag,CreateCommunityFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

    }

    @Override
    public void startActivityFromHolder(Intent intent) {

    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {

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
}
