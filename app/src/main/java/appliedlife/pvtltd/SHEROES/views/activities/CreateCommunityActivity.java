package appliedlife.pvtltd.SHEROES.views.activities;

import android.os.Bundle;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.views.fragments.CreateCommunityFragment;
import butterknife.ButterKnife;

/** Created by Ajit Kumar on 11/01/2017.
 *
 * @author Ajit Kumar
 * @version 5.0
 * @since 11/01/2017.
 * Title: Create community screen for Create community.
 */
public class CreateCommunityActivity extends BaseActivity implements CreateCommunityFragment.CreateCommunityActivityIntractionListner{

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
        callFirstFragment(R.id.fl_fragment_container, frag);

    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void onErrorOccurence() {

    }
}
