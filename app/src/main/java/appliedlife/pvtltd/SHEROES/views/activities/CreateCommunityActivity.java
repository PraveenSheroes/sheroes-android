package appliedlife.pvtltd.SHEROES.views.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.ChangeCommunityPrivacyDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitySearchTagsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CreateCommunityFragment;
import butterknife.ButterKnife;

/** Created by Ajit Kumar on 11/01/2017.
 *
 * @author Ajit Kumar
 * @version 5.0
 * @since 11/01/2017.
 * Title: Create community screen for Create community.
 */
public class CreateCommunityActivity extends BaseActivity implements CreateCommunityFragment.CreateCommunityActivityIntractionListner,CommunitySearchTagsFragment.MyCommunityTagListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        renderLoginFragmentView();


    }
    public void renderLoginFragmentView() {
        setContentView(R.layout.activity_create_community);
        ButterKnife.bind(this);
        CreateCommunityFragment frag = new CreateCommunityFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.fl_fragment_container, frag,CreateCommunityFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void onErrorOccurence() {

    }

    @Override
    public void onTagsSubmit(String[] tagsval) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(CreateCommunityFragment.class.getName());


                ((CreateCommunityFragment) fragment).showTagResult(tagsval);


        getSupportFragmentManager().popBackStack();


    }

    @Override
    public void onBackPress() {
        getSupportFragmentManager().popBackStack();

    }



    @Override
    public void callCommunityTagPage() {
        getSupportFragmentManager().popBackStack();

        CommunitySearchTagsFragment frag=new CommunitySearchTagsFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.fl_fragment_container, frag).addToBackStack(null).commitAllowingStateLoss();

        //  ChangeCommunityPrivacyDialogFragment frag1 = new ChangeCommunityPrivacyDialogFragment();
      //  callFirstFragment(R.id.fl_fragment_container, frag1);
    }
}
