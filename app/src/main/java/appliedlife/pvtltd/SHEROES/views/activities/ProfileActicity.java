package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfessionalBasicDetailsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileHorList;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePersonalViewList;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePreferredWorkLocationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTravelFlexibilityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileViewList;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.PersonalBasicDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.PersonalProfileFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfessionalEditBasicDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProffestionalProfileFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileAboutMeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileAddEducationFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileAddOtherFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileCityWorkFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileEditVisitingCardFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileGoodAtFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileOpportunityTypeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileOtherFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileShareYourIntrestFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileTravelClientFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileVisitingCardView;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileWorkExperienceFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 13-02-2017.
 */

public class ProfileActicity extends BaseActivity implements ProfileView,BaseHolderInterface,AppBarLayout.OnOffsetChangedListener,ProfileTravelClientFragment.ProfileTravelClientFragmentListener,ProfileCityWorkFragment.ProfileWorkLocationFragmentListener,ProfileAboutMeFragment.ProfileAboutMeFragmentListener {
    private final String TAG = LogUtils.makeLogTag(ProfileActicity.class);
    private static final String EXTRA_IMAGE = "extraImage";
    private static final String DECRIPTION = "desc";
    @Bind(R.id.iv_profile_full_view_icon)
    RoundedImageView mProfileIcon;
    @Bind(R.id.app_bar_profile)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.iv_profile_image)
    ImageView ivCommunitiesDetail;
    @Bind(R.id.view_pager_profile)
    ViewPager mViewPager;
    @Bind(R.id.toolbar_profile)
     Toolbar mToolbar;
    @Bind(R.id.collapsing_toolbar_profile)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.tab_profile)
    public TabLayout mTabLayout;
    @Bind(R.id.li_user_profile_status)
    public LinearLayout mLytUserProfileStatus;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_profile_completed)
    TextView mTv_profile_completed;
    @Bind(R.id.pb_profile_full_view)
    ProgressBar mPb_profile_full_view;
    @Bind(R.id.profile_container)
    FrameLayout flprofile_container;
    @Bind(R.id.cl_profile)
    CoordinatorLayout coordinatorLayout;
    int mflag=0;
    private Handler handler = new Handler();
    private int progressStatus = 0;
    public static void navigate(AppCompatActivity activity, View transitionImage, String profile) {
        Intent intent = new Intent(activity, ProfileActicity.class);
        intent.putExtra(EXTRA_IMAGE,profile);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        initActivityTransitions();
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setPagerAndLayouts();
        setprogressbar();
    }

    @Override
    public void onBackPressed() {
        if(mflag==1) {
            mflag=0;
            getSupportFragmentManager().popBackStack();
            coordinatorLayout.setVisibility(View.VISIBLE);
        }
        else
            finish();

    }

    //set function for progressbar

    private void setprogressbar()
    {

        // Start the lengthy operation in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {

                progressStatus = 0;

                while(progressStatus < 40){
                    // Update the progress status
                    progressStatus +=1;

                    // Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(20);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mPb_profile_full_view.setProgress(progressStatus);
                            // Show the progress on TextView
                            mTv_profile_completed.setText(progressStatus+""+"% Profile Completed");
                        }
                    });
                }
            }
        }).start(); // Start the operation

    }

    private void setPagerAndLayouts() {
        ViewCompat.setTransitionName(mAppBarLayout, EXTRA_IMAGE);
        supportPostponeEnterTransition();
        setSupportActionBar(mToolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbarLayout.setTitle("  ");
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(),android.R.color.transparent));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(PersonalProfileFragment.createInstance(), getString(R.string.ID_PERSONAL));
        viewPagerAdapter.addFragment(ProffestionalProfileFragment.createInstance(), getString(R.string.ID_PROFESSIONAL));
        mViewPager.setAdapter(viewPagerAdapter);
       mTabLayout.setupWithViewPager(mViewPager);
        Glide.with(this)
                .load(getIntent().getStringExtra(EXTRA_IMAGE))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(mProfileIcon);
        Glide.with(this)
                .load(getIntent().getStringExtra(EXTRA_IMAGE)).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        ivCommunitiesDetail.setImageBitmap( resource );
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            public void onGenerated(Palette palette) {
                                applyPalette(palette);
                            }
                        });
                    }
                });
        mAppBarLayout.addOnOffsetChangedListener(this);

    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }


    private void applyPalette(Palette palette) {
        int primaryDark = ContextCompat.getColor(getApplication(), R.color.colorPrimaryDark);
        int primary = ContextCompat.getColor(getApplication(), R.color.colorPrimary);
        //  mCollapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        //   mCollapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        //  updateBackground((FloatingActionButton) findViewById(R.id.fab), palette);
        supportStartPostponedEnterTransition();
    }

    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(getResources().getColor(android.R.color.white));
        int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.colorPrimary));
        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }

    @Override
    public void startActivityFromHolder(Intent intent) {

    }
    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {

        if (baseResponse instanceof ProfileHorList) {

            ProfileHorList dataItem= (ProfileHorList) baseResponse;

            ProfileCardlHandled(view.getId(),((ProfileHorList) baseResponse).getTag());

        }else  if (baseResponse instanceof ProfileViewList) {

            ProfileViewList dataItem1= (ProfileViewList) baseResponse;

            ProfileCardlHandled(view.getId(),((ProfileViewList) baseResponse).getTag());


        }else if (baseResponse instanceof ProfilePersonalViewList) {

            ProfilePersonalViewList dataItem2= (ProfilePersonalViewList) baseResponse;
            ProfileCardlHandled(view.getId(),((ProfilePersonalViewList) baseResponse).getTag());

        }else {


        }
    }

    private void ProfileCardlHandled(int id,String tag) {
        switch (id) {
            case R.id.tv_edit_other_text:
                if(tag.equals("Are you willing to travel to client side location?")) {
                    flprofile_container.setVisibility(View.VISIBLE);
                    ProfileTravelClientFragment profiletravelFragment = new ProfileTravelClientFragment();
                    Bundle bundleTravel = new Bundle();
                    ButterKnife.bind(this);
                    profiletravelFragment.setArguments(bundleTravel);
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                            .replace(R.id.profile_container, profiletravelFragment, ProfileTravelClientFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                    mflag = 1;
                    break;
                }else {

                    flprofile_container.setVisibility(View.VISIBLE);
                    ProfileCityWorkFragment profileCityWorkFragment = new ProfileCityWorkFragment();
                    Bundle bundleTravel = new Bundle();
                    ButterKnife.bind(this);
                    profileCityWorkFragment.setArguments(bundleTravel);
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                            .replace(R.id.profile_container, profileCityWorkFragment, ProfileCityWorkFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                    break;
                }
            case R.id.tv_add_education:
                if(tag.equals("EDUCATION")) {
                    flprofile_container.setVisibility(View.VISIBLE);
                    ProfileAddEducationFragment profileAddEducationFragment = new ProfileAddEducationFragment();
                    Bundle bundleTravel = new Bundle();
                    ButterKnife.bind(this);
                    profileAddEducationFragment.setArguments(bundleTravel);
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                            .replace(R.id.profile_container, profileAddEducationFragment, ProfileAddEducationFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                    break;
                }else{
                    flprofile_container.setVisibility(View.VISIBLE);
                    ProfileWorkExperienceFragment profileWorkExperienceFragment = new ProfileWorkExperienceFragment();
                    Bundle bundleTravel = new Bundle();
                    ButterKnife.bind(this);
                    profileWorkExperienceFragment.setArguments(bundleTravel);
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                            .replace(R.id.profile_container, profileWorkExperienceFragment, ProfileWorkExperienceFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

                    break;

                }

            case R.id.tv_edit_other_textline:
                flprofile_container.setVisibility(View.VISIBLE);
                 ProfileOtherFragment profileOtherFragment= new ProfileOtherFragment();
                Bundle bundleOther = new Bundle();
                ButterKnife.bind(this);
                profileOtherFragment.setArguments(bundleOther);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileOtherFragment, ProfileWorkExperienceFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

                break;

            case R.id.tv_professional_edit_basic_details:

                flprofile_container.setVisibility(View.VISIBLE);
                ProfessionalEditBasicDetailsFragment profileEditBasicDetailsFragment= new ProfessionalEditBasicDetailsFragment();
                Bundle bundleEditBasicDetails = new Bundle();
                ButterKnife.bind(this);
                profileEditBasicDetailsFragment.setArguments(bundleEditBasicDetails);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileEditBasicDetailsFragment, ProfessionalEditBasicDetailsFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                break;


            case R.id.tv_add_about_me:

                flprofile_container.setVisibility(View.VISIBLE);
                ProfileAboutMeFragment profileAboutMeFragment= new ProfileAboutMeFragment();
                Bundle bundleAddAboutMeFragment = new Bundle();
                ButterKnife.bind(this);
                profileAboutMeFragment.setArguments(bundleAddAboutMeFragment);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileAboutMeFragment, ProfileAboutMeFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

                break;

            case R.id.tv_download_my_card:

                flprofile_container.setVisibility(View.VISIBLE);

                ProfileVisitingCardView profileVisitingCardView= new ProfileVisitingCardView();
                Bundle bundleProfileVisitingCardFragment = new Bundle();
                ButterKnife.bind(this);
                profileVisitingCardView.setArguments(bundleProfileVisitingCardFragment);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileVisitingCardView, ProfileVisitingCardView.class.getName()).addToBackStack(null).commitAllowingStateLoss();

                break;


            case R.id.tv_edit_basic_details:

                flprofile_container.setVisibility(View.VISIBLE);
                PersonalBasicDetailsFragment personalBasicDetailsFragment= new PersonalBasicDetailsFragment();
                Bundle bundlePersonalBasicDetailsFragment = new Bundle();
                ButterKnife.bind(this);
                personalBasicDetailsFragment.setArguments(bundlePersonalBasicDetailsFragment);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, personalBasicDetailsFragment, PersonalBasicDetailsFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                break;



            case R.id.tv_add_good_at:
                flprofile_container.setVisibility(View.VISIBLE);
                ProfileGoodAtFragment profileGoodAtFragment= new ProfileGoodAtFragment();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileGoodAtFragment, ProfileGoodAtFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                break;

            case R.id.tv_looking_for:
                flprofile_container.setVisibility(View.VISIBLE);
                ProfileOpportunityTypeFragment profileOpportunityTypeFragment= new ProfileOpportunityTypeFragment();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileOpportunityTypeFragment, ProfileOpportunityTypeFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                break;


            case R.id.tv_add_interest_details:
                flprofile_container.setVisibility(View.VISIBLE);
                ProfileShareYourIntrestFragment profileShareYourIntrestFragment= new ProfileShareYourIntrestFragment();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileShareYourIntrestFragment, ProfileShareYourIntrestFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                break;

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);


        }
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

    /**
     * Called when the {@link AppBarLayout}'s layout offset has been changed. This allows
     * child views to implement custom behavior based on the offset (for instance pinning a
     * view at a certain y value).
     *
     * @param appBarLayout   the {@link AppBarLayout} which offset has changed
     * @param verticalOffset the vertical offset for the parent {@link AppBarLayout}, in px
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        LogUtils.info("appbar","**************"+verticalOffset);
        if(verticalOffset>-500)
        {
            mLytUserProfileStatus.setVisibility(View.GONE);
        }
        else
        {
            mLytUserProfileStatus.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.iv_back)
    public void backOnclick()
    {
        finish();
        overridePendingTransition(R.anim.top_to_bottom_exit, R.anim.top_bottom_exit_anim);
    }




    @Override
    public void backListener(int id) {


 /*       switch (id) {

            case R.id.tv_edit_visiting_card:

                Toast.makeText(getApplicationContext(), "click_button", Toast.LENGTH_SHORT).show();
                flprofile_container.setVisibility(View.VISIBLE);

                ProfileEditVisitingCardFragment profileEditVisitingCardFragment= new ProfileEditVisitingCardFragment();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileEditVisitingCardFragment, ProfileEditVisitingCardFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                break;

            default:

                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);

        }*/


        //   getSupportFragmentManager().popBackStack();

    }

    @Override
    public void visitingCardOpen(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {



        flprofile_container.setVisibility(View.VISIBLE);

        Gson gson1 = new Gson();
        String jsonSections1 = gson1.toJson(profileEditVisitingCardResponse);
        Bundle bundle1 = new Bundle();
        bundle1.putString("user_visiting_card_value", jsonSections1);

        ProfileEditVisitingCardFragment profileEditVisitingCardFragment= new ProfileEditVisitingCardFragment();
        profileEditVisitingCardFragment.setArguments(bundle1);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.profile_container, profileEditVisitingCardFragment, ProfileEditVisitingCardFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();


    }

    @Override
    public void callFragment(int id) {
        switch (id) {

            case R.id.fab_add_other:
            ProfileAddOtherFragment profileAddOtherFragment = new ProfileAddOtherFragment();
            Bundle bundleAddOther = new Bundle();
            ButterKnife.bind(this);
            profileAddOtherFragment.setArguments(bundleAddOther);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                    .replace(R.id.profile_container, profileAddOtherFragment, ProfileAddOtherFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
             break;


            case R.id.tv_edit_other_textline:
                ProfileAddOtherFragment profileEditOtherFragment = new ProfileAddOtherFragment();
                Bundle bundleEditOther = new Bundle();
                ButterKnife.bind(this);
                profileEditOtherFragment.setArguments(bundleEditOther);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileEditOtherFragment, ProfileAddOtherFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                break;

        }

    }

    @Override
    public void getEducationResponse(EducationResponse educationResponse) {

    }

    @Override
    public void getPersonalBasicDetailsResponse(PersonalBasicDetailsResponse personalBasicDetailsResponse) {

    }

    @Override
    public void getprofiletracelflexibilityResponse(ProfileTravelFlexibilityResponse profileTravelFlexibilityResponse) {

    }

    @Override
    public void getUserSummaryResponse(UserSummaryResponse userSummaryResponse) {

    }

    @Override
    public void getProfessionalBasicDetailsResponse(ProfessionalBasicDetailsResponse professionalBasicDetailsResponse) {

    }

    @Override
    public void getProfessionalWorkLocationResponse(ProfilePreferredWorkLocationResponse profilePreferredWorkLocationResponse) {

    }

    @Override
    public void getProfileVisitingCardResponse(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {

    }

    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }


    @Override
    public void onErrorOccurence() {

    }

    @Override
    public void AboutMeBack() {

        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void locationBack() {

        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void clintBack() {
        getSupportFragmentManager().popBackStack();
    }
}