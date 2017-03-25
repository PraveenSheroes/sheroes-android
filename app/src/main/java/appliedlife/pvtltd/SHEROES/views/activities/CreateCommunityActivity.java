package appliedlife.pvtltd.SHEROES.views.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.ChangeCommunityPrivacyDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitySearchTagsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CreateCommunityFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ImageUploadFragment;
import butterknife.ButterKnife;

/** Created by Ajit Kumar on 11/01/2017.
 *
 * @author Ajit Kumar
 * @version 5.0
 * @since 11/01/2017.
 * Title: Create community screen for Create community.
 */
public class CreateCommunityActivity extends BaseActivity implements CreateCommunityFragment.CreateCommunityActivityIntractionListner,CommunitySearchTagsFragment.MyCommunityTagListener ,ImageUploadFragment.ImageUploadCallable{

    private CreateCommunityFragment mCommunityFragment;
    private FeedDetail mFeedDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null && getIntent().getExtras() != null) {
            mFeedDetail = getIntent().getExtras().getParcelable(AppConstants.COMMUNITIES_DETAIL);
        }
        SheroesApplication.getAppComponent(this).inject(this);
        renderLoginFragmentView();
    }

    public void renderLoginFragmentView() {
        setContentView(R.layout.activity_create_community);
        ButterKnife.bind(this);
        mCommunityFragment = new CreateCommunityFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable(AppConstants.COMMUNITIES_DETAIL, mFeedDetail);
        mCommunityFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.create_community_container, mCommunityFragment, CreateCommunityFragment.class.getName()).commitAllowingStateLoss();
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void onErrorOccurence() {

    }

    @Override
    public void onCameraSelection() {
        mCommunityFragment.selectImageFrmCamera();
    }

    @Override
    public void onGallerySelection() {
         mCommunityFragment.selectImageFrmGallery();
    }

    @Override
    public void onTagsSubmit(String[] tagsval) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(AppConstants.TAG_LIST, tagsval);
        mCommunityFragment = new CreateCommunityFragment();
        mCommunityFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.create_community_container, mCommunityFragment,CreateCommunityFragment.class.getName()).addToBackStack(CreateCommunityFragment.class.getSimpleName()).commitAllowingStateLoss();
    }

    @Override
    public void onBackPress() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void callCommunityTagPage() {
        getSupportFragmentManager().popBackStack();
        CommunitySearchTagsFragment frag=new CommunitySearchTagsFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.create_community_container, frag).addToBackStack(null).commitAllowingStateLoss();
    }

}
