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
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityList;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustiomActionBarToggle;
import appliedlife.pvtltd.SHEROES.views.fragments.CreateCommunityPostFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ImageUploadFragment;
import butterknife.ButterKnife;


public class CreateCommunityPostActivity extends BaseActivity implements CreateCommunityPostFragment.CreateCommunityActivityPostIntractionListner, BaseHolderInterface, CustiomActionBarToggle.DrawerStateListener, NavigationView.OnNavigationItemSelectedListener, ImageUploadFragment.ImageUploadCallable {
    String data = "";
    private FeedDetail mFeedDetail;

    private CreateCommunityPostFragment mCommunityFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);

        renderLoginFragmentView();
    }

    public void renderLoginFragmentView() {
        data = getIntent().getStringExtra("value");
        try {
            if (null != data) {
            } else
                data = "";
        } catch (Exception e) {
            data = "";
        }

        setContentView(R.layout.activity_create_community_post);
        ButterKnife.bind(this);
        mCommunityFragment = new CreateCommunityPostFragment();
        callFirstFragment(R.id.create_community_post_container, mCommunityFragment);
        CreateCommunityPostFragment frag = new CreateCommunityPostFragment();
        callFirstFragment(R.id.create_community_post_container, frag);

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
    public void onErrorOccurence() {

    }

    @Override
    public void onClose() {
        finish();
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
