package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.ChangeCommunityPrivacyDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitySearchTagsDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.CreateCommunityFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ImageUploadFragment;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment.DISMISS_PARENT_ON_OK_OR_BACK;

/**
 * Created by Ajit Kumar on 11/01/2017.
 *
 * @author Ajit Kumar
 * @version 5.0
 * @since 11/01/2017.
 * Title: Create community screen for Create community.
 */
public class CreateCommunityActivity extends BaseActivity implements ImageUploadFragment.ImageUploadCallable {

    private CreateCommunityFragment mCommunityFragment;
    private FeedDetail mFeedDetail;
    private FragmentOpen mFragmentOpen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            mFeedDetail = getIntent().getExtras().getParcelable(AppConstants.COMMUNITIES_DETAIL);
        }
        renderFragmentView();
    }

    public void renderFragmentView() {
        setContentView(R.layout.activity_create_community);
        ButterKnife.bind(this);
        mFragmentOpen = new FragmentOpen();
        setAllValues(mFragmentOpen);
        mCommunityFragment = new CreateCommunityFragment();
        Bundle bundle = new Bundle();
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

    public void onTagsSubmit(String[] tagsval, long[] tagsid) {
        Fragment fragmentCommunityDetail = getSupportFragmentManager().findFragmentByTag(CreateCommunityFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragmentCommunityDetail)) {
            ((CreateCommunityFragment) fragmentCommunityDetail).setCommunitiyTags(tagsval, tagsid);
        }
    }

    public DialogFragment callCommunityTagPage(FeedDetail mFeedDetail) {
        CommunitySearchTagsDialog communitySearchTagsDialog = (CommunitySearchTagsDialog) getFragmentManager().findFragmentByTag(CommunitySearchTagsDialog.class.getName());
        if (communitySearchTagsDialog == null) {
            communitySearchTagsDialog = new CommunitySearchTagsDialog();
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstants.COMMUNITIES_DETAIL, mFeedDetail);
            communitySearchTagsDialog.setArguments(bundle);
        }
        if (!communitySearchTagsDialog.isVisible() && !communitySearchTagsDialog.isAdded() && !isFinishing() && !mIsDestroyed) {
            communitySearchTagsDialog.show(getFragmentManager(), CommunitySearchTagsDialog.class.getName());
        }
        return communitySearchTagsDialog;
    }


    @Override
    public void onBackPressed() {
        onBackClickHandle(mFeedDetail);
    }

    public void onBackClickHandle(FeedDetail feedDetail) {
        if(null!=mFragmentOpen)
        {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstants.COMMUNITIES_DETAIL, feedDetail);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onShowErrorDialog(String errorReason, FeedParticipationEnum feedParticipationEnum) {
        if (StringUtil.isNotNullOrEmptyString(errorReason)) {
            switch (errorReason) {
                case AppConstants.CHECK_NETWORK_CONNECTION:
                    showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                    break;
                default:
                    showNetworkTimeoutDoalog(true, false, errorReason);
            }
        } else {
            showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }

    }


    public DialogFragment changePrivacy(boolean openClose) {
        ChangeCommunityPrivacyDialogFragment changeCommunityPrivacyDialogFragment = (ChangeCommunityPrivacyDialogFragment) getFragmentManager().findFragmentByTag(ChangeCommunityPrivacyDialogFragment.class.getName());
        if (changeCommunityPrivacyDialogFragment == null) {
            changeCommunityPrivacyDialogFragment = new ChangeCommunityPrivacyDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(DISMISS_PARENT_ON_OK_OR_BACK, openClose);
            changeCommunityPrivacyDialogFragment.setArguments(bundle);
        }
        if (!changeCommunityPrivacyDialogFragment.isVisible() && !changeCommunityPrivacyDialogFragment.isAdded()&& !isFinishing() && !mIsDestroyed) {
            changeCommunityPrivacyDialogFragment.show(getFragmentManager(), ChangeCommunityPrivacyDialogFragment.class.getName());
        }
        return changeCommunityPrivacyDialogFragment;
    }
}
