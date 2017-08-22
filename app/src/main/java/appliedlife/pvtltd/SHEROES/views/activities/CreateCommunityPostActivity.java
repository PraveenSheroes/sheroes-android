package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityList;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustiomActionBarToggle;
import appliedlife.pvtltd.SHEROES.views.fragments.CreateCommunityPostFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ImageUploadFragment;
import butterknife.ButterKnife;


public class CreateCommunityPostActivity extends BaseActivity implements BaseHolderInterface, CustiomActionBarToggle.DrawerStateListener, NavigationView.OnNavigationItemSelectedListener, ImageUploadFragment.ImageUploadCallable {
    private FeedDetail mFeedDetail;
    private CreateCommunityPostFragment mCommunityFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            mFeedDetail = getIntent().getExtras().getParcelable(AppConstants.COMMUNITY_POST_FRAGMENT);
        }

        renderLoginFragmentView();
        ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_CREATE_COMMUNITY_POST));
    }

    public void renderLoginFragmentView() {
        setContentView(R.layout.activity_create_community_post);
        ButterKnife.bind(this);
        mCommunityFragment=new CreateCommunityPostFragment();
        Bundle bundle = new Bundle();
        LogUtils.info("feed data",mFeedDetail+"");
        bundle.putParcelable(AppConstants.COMMUNITY_POST_FRAGMENT, mFeedDetail);

        mCommunityFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.create_community_post_container, mCommunityFragment,CreateCommunityPostFragment.class.getName()).commitAllowingStateLoss();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void startActivityFromHolder(Intent intent) {

    }


    @Override
    public void handleOnClick(BaseResponse sheroesListDataItem, View view) {
        // Toast.makeText(getApplicationContext(),sheroesListDataItem+"hello",Toast.LENGTH_LONG).show();

        if (sheroesListDataItem instanceof CommunityList) {
            CommunityList communityList = (CommunityList) sheroesListDataItem;


            //  Toast.makeText(getApplicationContext(),communityList.getId(),Toast.LENGTH_LONG).show();

            // DetailActivity.navigate(this, view, communityList);
        }


        FragmentManager fm = getFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        //  getSupportFragmentManager().popBackStack();

    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {

    }


    @Override
    public void setListData(BaseResponse data, boolean flag) {

    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {

    }


    @Override
    public List getListData() {
        return null;
    }


    @Override
    public void onDrawerOpened() {

    }

    @Override
    public void onDrawerClosed() {

    }


    @Override
    public void onShowErrorDialog(String errorReason, FeedParticipationEnum feedParticipationEnum) {
        if (StringUtil.isNotNullOrEmptyString(errorReason)) {
            switch (errorReason) {
                case AppConstants.CHECK_NETWORK_CONNECTION:
                    showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                    break;
                case AppConstants.MARK_AS_SPAM:
                    showNetworkTimeoutDoalog(true, false, errorReason);
                    break;
                default:
                    showNetworkTimeoutDoalog(true, false, errorReason);
            }
        } else {
            showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }

    }
    @Override
    public void onBackPressed() {
        finish();
    }
    public void editedSuccessFully(FeedDetail feedDetail)
    {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.COMMUNITY_POST_FRAGMENT, feedDetail);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        onBackPressed();
    }
    @Override
    public void onCameraSelection() {
        mCommunityFragment.selectImageFrmCamera();
    }

    @Override
    public void onGallerySelection() {
        mCommunityFragment.selectImageFrmGallery();
    }
}
