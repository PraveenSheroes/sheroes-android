package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitySearchTagsDialog;
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
public class CreateCommunityActivity extends BaseActivity implements ImageUploadFragment.ImageUploadCallable{

    private CreateCommunityFragment mCommunityFragment;
    private FeedDetail mFeedDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null && getIntent().getExtras() != null) {
            mFeedDetail = getIntent().getExtras().getParcelable(AppConstants.COMMUNITIES_DETAIL);
        }
        SheroesApplication.getAppComponent(this).inject(this);
        renderFragmentView();
    }

    public void renderFragmentView() {
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
    public void onCameraSelection() {
        mCommunityFragment.selectImageFrmCamera();
    }

    @Override
    public void onGallerySelection() {
         mCommunityFragment.selectImageFrmGallery();
    }

    public void onTagsSubmit(String[] tagsval,long[] tagsid) {
        Fragment fragmentCommunityDetail = getSupportFragmentManager().findFragmentByTag(CreateCommunityFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragmentCommunityDetail)) {
            ((CreateCommunityFragment) fragmentCommunityDetail).setCommunitiyTags(tagsval,tagsid);
        }
    }
    public DialogFragment callCommunityTagPage(FeedDetail mFeedDetail) {
        CommunitySearchTagsDialog communitySearchTagsDialog = (CommunitySearchTagsDialog) getFragmentManager().findFragmentByTag(CommunitySearchTagsDialog.class.getName());
        if (communitySearchTagsDialog == null) {
            communitySearchTagsDialog = new CommunitySearchTagsDialog();
            Bundle bundle=new Bundle();
            bundle.putParcelable(AppConstants.COMMUNITIES_DETAIL, mFeedDetail);
            communitySearchTagsDialog.setArguments(bundle);
        }
        if (!communitySearchTagsDialog.isVisible() && !communitySearchTagsDialog.isAdded() && !isFinishing() && !mIsDestroyed) {
            communitySearchTagsDialog.show(getFragmentManager(), CommunitySearchTagsDialog.class.getName());
        }
        return communitySearchTagsDialog;
    }
}
