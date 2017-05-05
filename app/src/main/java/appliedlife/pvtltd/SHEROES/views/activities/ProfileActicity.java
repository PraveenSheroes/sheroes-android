package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.f2prateek.rx.preferences.Preference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ExprienceEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GoodAt;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.WorkExpListResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.BlurrImage;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitySearchTagsDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.CurrentStatusDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.FunctionalAreaDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobLocationSearchDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.PersonalBasicDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.PersonalProfileFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfessionalEditBasicDetailsFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProffestionalProfileFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileAboutMeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileAddEditEducationFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileAddEducationFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileAddOtherFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileCityWorkFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileDegreeDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileEditVisitingCardFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileGoodAtFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileOpportunityTypeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileOtherFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfilePersonelHowCanLookingForFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileSchoolDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileSearchIntrestIn;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileSearchLanguage;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileSectoreDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileSelectCurrentStatus;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileShareYourIntrestFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileStudyDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileTravelClientFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileVisitingCardView;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileWorkExperienceFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileWorkExperienceSelfEmploymentFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchGoodAt;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchProfileLocation;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Priyanka on 13-02-2017.
 */

public class ProfileActicity extends BaseActivity implements ProfileGoodAtFragment.ProfileGoodAtListener, ProfileView, BaseHolderInterface, AppBarLayout.OnOffsetChangedListener, ProfileTravelClientFragment.ProfileTravelClientFragmentListener, ProfileCityWorkFragment.ProfileWorkLocationFragmentListener, ProfileAboutMeFragment.ProfileAboutMeFragmentListener, ProfileOpportunityTypeFragment.ProfileOpportunityTypeListiner, ProfileShareYourIntrestFragment.MyProfileyYourInterestListener,
        ProfessionalEditBasicDetailsFragment.EditProfileCallable, ProfileOpportunityTypeFragment.OppertunitiesCallback, ProfileAddEditEducationFragment.ProfileEducationListener {
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
    @Bind(R.id.tv_profile_full_view_name)
    TextView tvProfileFullName;
    private SearchProfileLocation searchProfileLocation;
    private FunctionalAreaDialogFragment functionalAreaDialogFragment;
    private JobLocationSearchDialogFragment jobLocationSearchDialogFragment;
    private ProfileWorkExperienceSelfEmploymentFragment profileWorkExperienceSelfEmploymentFragment;
    private ProfileAddEditEducationFragment profileAddEditEducationFragment;
    @Inject
    Preference<LoginResponse> mUserPreference;
    private Handler handler = new Handler();
    private int progressStatus = 0;
    private ProfileSelectCurrentStatus mCurrentStatusDialog;
    private ProfileSectoreDialog mSectoreDialog;
    private List<GetAllDataDocument> mJobLocationList = new ArrayList<>();
    private ProfileSearchLanguage profileSearchLanguage;
    private FragmentOpen mFragmentOpen;
    private ProfileSchoolDialog profileSchoolDialog;
    private ProfileStudyDialog profileStudyDialog;
    private ProfileDegreeDialog profileDegreeDialog;
    public List<LabelValue> mFunctionArea = new ArrayList<>();
    private ExprienceEntity mExprienceEntity;
    private ViewPagerAdapter viewPagerAdapter;

    public static void navigate(AppCompatActivity activity, View transitionImage, String profile) {
        Intent intent = new Intent(activity, ProfileActicity.class);
        intent.putExtra(EXTRA_IMAGE, profile);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        initActivityTransitions();
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        mFragmentOpen = new FragmentOpen();
        setName();
        setAllValues(mFragmentOpen);
        setPagerAndLayouts();
        setprogressbar();

    }

    private void setName() {
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
            tvProfileFullName.setText(mUserPreference.get().getUserSummary().getFirstName() + AppConstants.SPACE + mUserPreference.get().getUserSummary().getLastName());

        }

    }

    @Override
    public void onBackPressed() {
        if (mFragmentOpen.isProfileWorkExpEditFragment()) {
            mFragmentOpen.setProfileWorkExpEditFragment(false);
            if (profileWorkExperienceSelfEmploymentFragment != null) {
                profileWorkExperienceSelfEmploymentFragment.dismiss();
            }
            updateProfileWorkExpListItem();
        } else if (mFragmentOpen.isGoodAtFragment()) {
            mFragmentOpen.setGoodAtFragment(false);
            getSupportFragmentManager().popBackStack();
        } else if (mFragmentOpen.isWorkExpFragment()) {
            mFragmentOpen.setWorkExpFragment(false);
            getSupportFragmentManager().popBackStack();
            updateProffesstionalWorkExpListItem();
        } else if (mFragmentOpen.isLookingForHowCanOpen()) {
            mFragmentOpen.setLookingForHowCanOpen(false);
            getSupportFragmentManager().popBackStack();
            updatePersonelWorkExpListItem();
        } else if (mFragmentOpen.isEducationFragment()) {
            mFragmentOpen.setEducationFragment(false);
            if (profileAddEditEducationFragment != null) {
                profileAddEditEducationFragment.dismiss();
            }
        } else if (mFragmentOpen.isProfesstionalEducationFragment()) {
            mFragmentOpen.setProfesstionalEducationFragment(false);
            getSupportFragmentManager().popBackStack();
            updateProffesstionalWorkExpListItem();
        } else {
            super.onBackPressed();
        }
    }

    //set function for progressbar

    private void setprogressbar() {

        // Start the lengthy operation in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {

                progressStatus = 0;

                while (progressStatus < 40) {
                    // Update the progress status
                    progressStatus += 1;

                    // Try to sleep the thread for 20 milliseconds
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mPb_profile_full_view.setProgress(progressStatus);
                            // Show the progress on TextView
                            mTv_profile_completed.setText(progressStatus + "" + "% Profile Completed");
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

        mCollapsingToolbarLayout.setTitle(AppConstants.EMPTY_STRING);
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(PersonalProfileFragment.getInstance(), getString(R.string.ID_PERSONAL));
        viewPagerAdapter.addFragment(ProffestionalProfileFragment.getInstance(), getString(R.string.ID_PROFESSIONAL));

        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {

            Glide.with(this)
                    .load(mUserPreference.get().getUserSummary().getPhotoUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(mProfileIcon);

            Glide.with(this)
                    .load(mUserPreference.get().getUserSummary().getPhotoUrl()).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                            Bitmap blurred = BlurrImage.blurRenderScript(ProfileActicity.this, profileImage, 10);
                            ivCommunitiesDetail.setImageBitmap(blurred);
                            Palette.from(profileImage).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    applyPalette(palette);
                                }
                            });
                        }
                    });
        }
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
        if (baseResponse instanceof MyProfileView) {
            profileCardHandled(view.getId(), ((MyProfileView) baseResponse).getType(), baseResponse);
        } else if (baseResponse instanceof EducationEntity) {
            EducationEntity educationEntity = (EducationEntity) baseResponse;
            callEditEducation(educationEntity);
        } else if (baseResponse instanceof GetAllDataDocument) {
            dataOnClickForCardItem(view, baseResponse);
        } else if (baseResponse instanceof LabelValue) {
            if (null != mCurrentStatusDialog) {
                mCurrentStatusDialog.dismiss();
                mCurrentStatusDialog = null;
                LabelValue dataItem = (LabelValue) baseResponse;
                final Fragment fragment = getSupportFragmentManager().findFragmentByTag(ProfessionalEditBasicDetailsFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((ProfessionalEditBasicDetailsFragment) fragment).submitCurrentStatus(dataItem.getLabel(), dataItem.getValue());
                }
            } else if (null != mSectoreDialog) {
                mSectoreDialog.dismiss();
                mSectoreDialog = null;
                LabelValue dataItem = (LabelValue) baseResponse;
                final Fragment fragment = getSupportFragmentManager().findFragmentByTag(ProfessionalEditBasicDetailsFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((ProfessionalEditBasicDetailsFragment) fragment).submitSectorStatus(dataItem.getLabel(), dataItem.getValue());
                }

            }
        } else if (baseResponse instanceof GoodAt) {
            GoodAt goodAt = (GoodAt) baseResponse;
            TextView textView = (TextView) view.findViewById(R.id.tv_good_at_data);
            final Fragment fragment = getSupportFragmentManager().findFragmentByTag(ProfileOpportunityTypeFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((ProfileOpportunityTypeFragment) fragment).submitOppartunityType(goodAt.getId(), textView.getText().toString());

            }
        } else if (baseResponse instanceof ExprienceEntity) {
            ExprienceEntity exprienceEntity = (ExprienceEntity) baseResponse;
            mExprienceEntity = exprienceEntity;
            openEditAddWorkExpFragment(exprienceEntity);
        } else if (baseResponse instanceof OnBoardingData) {
            OnBoardingData onBoardingData = (OnBoardingData) baseResponse;
            switch (onBoardingData.getFragmentName()) {
                case AppConstants.HOW_SHEROES_CAN_HELP:
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(ProfilePersonelHowCanLookingForFragment.class.getName());
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        ((ProfilePersonelHowCanLookingForFragment) fragment).setItemOnHeader(view);
                    }
                    break;
                case AppConstants.MASTER_DATA_FUNCTIONAL_AREA_KEY:
                    if (StringUtil.isNotNullOrEmptyString(onBoardingData.getFragmentName())) {
                        LabelValue labelValue = (LabelValue) view.getTag();
                        if (labelValue.isSelected()) {
                            if (StringUtil.isNotEmptyCollection(mFunctionArea)) {
                                mFunctionArea.remove(labelValue);
                            }
                        } else {
                            mFunctionArea.add(labelValue);
                            onDoneFunctionArea();
                            mFunctionArea.clear();
                        }
                    }
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + onBoardingData.getFragmentName());
            }


        }
    }

    private void dataOnClickForCardItem(View view, BaseResponse baseResponse) {
        int id = view.getId();
        switch (id) {
            case R.id.li_job_item:
                GetAllDataDocument getAllDataDocument = (GetAllDataDocument) baseResponse;
                if (StringUtil.isNotNullOrEmptyString(getAllDataDocument.getTitle())) {
                    if (!getAllDataDocument.isChecked()) {
                        mJobLocationList.add(getAllDataDocument);
                        saveJobLocation();
                        mJobLocationList.clear();
                    } else {
                        if (StringUtil.isNotEmptyCollection(mJobLocationList)) {
                            mJobLocationList.remove(getAllDataDocument);
                        }
                    }
                }
                break;
            case R.id.li_city_name_layout:
                goodAtDataItem(baseResponse);
                break;

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
        }
    }

    private void goodAtDataItem(BaseResponse baseResponse) {
        GetAllDataDocument dataItem = (GetAllDataDocument) baseResponse;
        if (null != searchProfileLocation) {
            searchProfileLocation.dismiss();
            final Fragment fragment = getSupportFragmentManager().findFragmentByTag(PersonalBasicDetailsFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((PersonalBasicDetailsFragment) fragment).submitLocation(dataItem.getId(), dataItem.getTitle());
            }
        }

        if (null != profileDegreeDialog) {

            profileDegreeDialog.dismiss();
            final Fragment fragment = getSupportFragmentManager().findFragmentByTag(ProfileAddEditEducationFragment.class.getName());
            if (null != profileAddEditEducationFragment) {
                profileAddEditEducationFragment.submitDegree(dataItem.getId(), dataItem.getTitle());
            }
            profileDegreeDialog = null;
        }
        if (null != profileSchoolDialog) {

            profileSchoolDialog.dismiss();
            final Fragment fragment = getSupportFragmentManager().findFragmentByTag(ProfileAddEditEducationFragment.class.getName());
            if (null != profileAddEditEducationFragment) {
                profileAddEditEducationFragment.submitSchool(dataItem.getId(), dataItem.getTitle());
            }
            profileSchoolDialog = null;
        }
        if (null != profileStudyDialog) {

            profileStudyDialog.dismiss();
            final Fragment fragment = getSupportFragmentManager().findFragmentByTag(ProfileAddEditEducationFragment.class.getName());
            if (null != profileAddEditEducationFragment) {
                profileAddEditEducationFragment.submitStudy(dataItem.getId(), dataItem.getTitle());
            }
            profileStudyDialog = null;
        }
    }

    public DialogFragment openEditAddWorkExpFragment(ExprienceEntity exprienceEntity) {
        profileWorkExperienceSelfEmploymentFragment = (ProfileWorkExperienceSelfEmploymentFragment) getFragmentManager().findFragmentByTag(ProfileWorkExperienceSelfEmploymentFragment.class.getName());
        if (profileWorkExperienceSelfEmploymentFragment == null) {
            mFragmentOpen.setProfileWorkExpEditFragment(true);
            profileWorkExperienceSelfEmploymentFragment = new ProfileWorkExperienceSelfEmploymentFragment();
            Bundle bundleBookMarks = new Bundle();
            bundleBookMarks.putParcelable(AppConstants.WORK_EXPERIENCE_TYPE, exprienceEntity);
            profileWorkExperienceSelfEmploymentFragment.setArguments(bundleBookMarks);
        }
        if (!profileWorkExperienceSelfEmploymentFragment.isVisible() && !profileWorkExperienceSelfEmploymentFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            profileWorkExperienceSelfEmploymentFragment.show(getFragmentManager(), ProfileWorkExperienceSelfEmploymentFragment.class.getName());
        }
        return profileWorkExperienceSelfEmploymentFragment;
    }

    public DialogFragment openAddEditEducation(EducationEntity exprienceEntity) {
        profileAddEditEducationFragment = (ProfileAddEditEducationFragment) getFragmentManager().findFragmentByTag(ProfileAddEditEducationFragment.class.getName());
        if (profileAddEditEducationFragment == null) {
            mFragmentOpen.setEducationFragment(true);
            profileAddEditEducationFragment = new ProfileAddEditEducationFragment();
            profileAddEditEducationFragment.setListener(this);
            Bundle bundleBookMarks = new Bundle();
            bundleBookMarks.putParcelable(AppConstants.EDUCATION_PROFILE, exprienceEntity);
            profileAddEditEducationFragment.setArguments(bundleBookMarks);
        }
        if (!profileAddEditEducationFragment.isVisible() && !profileAddEditEducationFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            profileAddEditEducationFragment.show(getFragmentManager(), ProfileWorkExperienceSelfEmploymentFragment.class.getName());
        }
        return profileAddEditEducationFragment;
    }

    private void profileCardHandled(int id, String tag, BaseResponse baseResponse) {
        switch (id) {
            case R.id.tv_edit_other_text:
                if (tag.equals("Are you willing to travel to client side location?")) {
                    flprofile_container.setVisibility(View.VISIBLE);
                    ProfileTravelClientFragment profiletravelFragment = new ProfileTravelClientFragment();
                    Bundle bundleTravel = new Bundle();
                    profiletravelFragment.setArguments(bundleTravel);
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                            .replace(R.id.profile_container, profiletravelFragment, ProfileTravelClientFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                    break;
                } else {
                    flprofile_container.setVisibility(View.VISIBLE);
                    ProfileCityWorkFragment profileCityWorkFragment = new ProfileCityWorkFragment();
                    Bundle bundleTravel = new Bundle();
                    profileCityWorkFragment.setArguments(bundleTravel);
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                            .replace(R.id.profile_container, profileCityWorkFragment, ProfileCityWorkFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                    break;
                }
            case R.id.tv_add_education:
                if (tag.equalsIgnoreCase(AppConstants.EDUCATION_PROFILE)) {
                    MyProfileView myEducation = (MyProfileView) baseResponse;
                    flprofile_container.setVisibility(View.VISIBLE);
                    mFragmentOpen.setProfesstionalEducationFragment(true);
                    ProfileAddEducationFragment profileAddEducationFragment = new ProfileAddEducationFragment();
                    Bundle bundleTravel = new Bundle();
                    bundleTravel.putParcelable(AppConstants.EDUCATION_PROFILE, myEducation);
                    profileAddEducationFragment.setArguments(bundleTravel);
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                            .replace(R.id.profile_container, profileAddEducationFragment, ProfileAddEducationFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                    break;
                }
            case R.id.tv_add_work_exp:
                mFragmentOpen.setWorkExpFragment(true);
                MyProfileView workExp = (MyProfileView) baseResponse;
                flprofile_container.setVisibility(View.VISIBLE);
                ProfileWorkExperienceFragment profileWorkExperienceFragment = new ProfileWorkExperienceFragment();
                Bundle bundleWorkExp = new Bundle();
                bundleWorkExp.putParcelableArrayList(AppConstants.EXPERIENCE_PROFILE, (ArrayList<? extends Parcelable>) workExp.getExprienceEntity());
                profileWorkExperienceFragment.setArguments(bundleWorkExp);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileWorkExperienceFragment, ProfileWorkExperienceFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

                break;
            case R.id.tv_edit_other_textline:
                flprofile_container.setVisibility(View.VISIBLE);
                ProfileOtherFragment profileOtherFragment = new ProfileOtherFragment();
                Bundle bundleOther = new Bundle();
                profileOtherFragment.setArguments(bundleOther);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileOtherFragment, ProfileWorkExperienceFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

                break;

            case R.id.tv_professional_edit_basic_details:
                MyProfileView editbasicDetail = (MyProfileView) baseResponse;
                flprofile_container.setVisibility(View.VISIBLE);
                ProfessionalEditBasicDetailsFragment profileEditBasicDetailsFragment = new ProfessionalEditBasicDetailsFragment();
                Bundle bundleEditBasicDetails = new Bundle();
                bundleEditBasicDetails.putParcelable(AppConstants.EDUCATION_PROFILE, editbasicDetail);
                profileEditBasicDetailsFragment.setArguments(bundleEditBasicDetails);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileEditBasicDetailsFragment, ProfessionalEditBasicDetailsFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                break;


            case R.id.tv_add_about_me: {
                flprofile_container.setVisibility(View.VISIBLE);
                ProfileAboutMeFragment profileAboutMeFragment = new ProfileAboutMeFragment();
                Bundle bundleAddAboutMeFragment = new Bundle();
                bundleAddAboutMeFragment.putParcelable(AppConstants.MODEL_KEY, baseResponse);
                profileAboutMeFragment.setArguments(bundleAddAboutMeFragment);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileAboutMeFragment, ProfileAboutMeFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

                break;
            }
            case R.id.tv_download_my_card:

                flprofile_container.setVisibility(View.VISIBLE);

                ProfileVisitingCardView profileVisitingCardView = new ProfileVisitingCardView();
                Bundle bundleProfileVisitingCardFragment = new Bundle();
                profileVisitingCardView.setArguments(bundleProfileVisitingCardFragment);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileVisitingCardView, ProfileVisitingCardView.class.getName()).addToBackStack(null).commitAllowingStateLoss();

                break;


            case R.id.tv_edit_basic_details: {
                MyProfileView basicDetail = (MyProfileView) baseResponse;
                flprofile_container.setVisibility(View.VISIBLE);
                PersonalBasicDetailsFragment personalBasicDetailsFragment = new PersonalBasicDetailsFragment();
                Bundle bundlePersonalBasicDetailsFragment = new Bundle();
                bundlePersonalBasicDetailsFragment.putParcelable(AppConstants.EDUCATION_PROFILE, basicDetail);
                personalBasicDetailsFragment.setArguments(bundlePersonalBasicDetailsFragment);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, personalBasicDetailsFragment, PersonalBasicDetailsFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                break;
            }

            case R.id.tv_add_good_at: {
                mFragmentOpen.setGoodAtFragment(true);
                flprofile_container.setVisibility(View.VISIBLE);
                ProfileGoodAtFragment profileGoodAtFragment = new ProfileGoodAtFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppConstants.MODEL_KEY, baseResponse);
                profileGoodAtFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileGoodAtFragment, ProfileGoodAtFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                break;
            }
            case R.id.tv_looking_for: {
                mFragmentOpen.setLookingForHowCanOpen(true);
                flprofile_container.setVisibility(View.VISIBLE);
                ProfilePersonelHowCanLookingForFragment profilePersonelHowCanLookingForFragment = new ProfilePersonelHowCanLookingForFragment();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profilePersonelHowCanLookingForFragment, ProfilePersonelHowCanLookingForFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                break;
            }
            case R.id.tv_add_interest_details: {
                flprofile_container.setVisibility(View.VISIBLE);
                ProfileShareYourIntrestFragment profileShareYourIntrestFragment = new ProfileShareYourIntrestFragment();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileShareYourIntrestFragment, ProfileShareYourIntrestFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                break;
            }
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);


        }
    }

    public DialogFragment showCurrentStatusDialog(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult) {
        mCurrentStatusDialog = (ProfileSelectCurrentStatus) getFragmentManager().findFragmentByTag(ProfileSelectCurrentStatus.class.getName());
        if (mCurrentStatusDialog == null) {
            mCurrentStatusDialog = new ProfileSelectCurrentStatus();
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstants.TAG_LIST, masterDataResult);
            mCurrentStatusDialog.setArguments(bundle);
        }
        if (!mCurrentStatusDialog.isVisible() && !mCurrentStatusDialog.isAdded() && !isFinishing() && !mIsDestroyed) {
            mCurrentStatusDialog.show(getFragmentManager(), CurrentStatusDialog.class.getName());
        }
        return mCurrentStatusDialog;
    }

    public DialogFragment showSectoreDialog(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult) {
        mSectoreDialog = (ProfileSectoreDialog) getFragmentManager().findFragmentByTag(ProfileSectoreDialog.class.getName());
        if (mSectoreDialog == null) {
            mSectoreDialog = new ProfileSectoreDialog();
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstants.TAG_LIST, masterDataResult);
            mSectoreDialog.setArguments(bundle);
        }
        if (!mSectoreDialog.isVisible() && !mSectoreDialog.isAdded() && !isFinishing() && !mIsDestroyed) {
            mSectoreDialog.show(getFragmentManager(), ProfileSectoreDialog.class.getName());
        }
        return mSectoreDialog;
    }

    public DialogFragment callProfileStudy() {

        profileStudyDialog = (ProfileStudyDialog) getFragmentManager().findFragmentByTag(ProfileStudyDialog.class.getName());
        if (profileStudyDialog == null) {
            profileStudyDialog = new ProfileStudyDialog();

        }
        if (!profileStudyDialog.isVisible() && !profileStudyDialog.isAdded() && !isFinishing() && !mIsDestroyed) {
            profileStudyDialog.show(getFragmentManager(), SearchProfileLocation.class.getName());
        }
        return profileStudyDialog;
    }

    public DialogFragment callProfileDegree() {

        profileDegreeDialog = (ProfileDegreeDialog) getFragmentManager().findFragmentByTag(ProfileDegreeDialog.class.getName());
        if (profileDegreeDialog == null) {
            profileDegreeDialog = new ProfileDegreeDialog();

        }
        if (!profileDegreeDialog.isVisible() && !profileDegreeDialog.isAdded() && !isFinishing() && !mIsDestroyed) {
            profileDegreeDialog.show(getFragmentManager(), SearchProfileLocation.class.getName());
        }
        return profileDegreeDialog;
    }

    public DialogFragment callProfileSchool() {

        profileSchoolDialog = (ProfileSchoolDialog) getFragmentManager().findFragmentByTag(ProfileSchoolDialog.class.getName());
        if (profileSchoolDialog == null) {
            profileSchoolDialog = new ProfileSchoolDialog();

        }
        if (!profileSchoolDialog.isVisible() && !profileSchoolDialog.isAdded() && !isFinishing() && !mIsDestroyed) {
            profileSchoolDialog.show(getFragmentManager(), SearchProfileLocation.class.getName());
        }
        return profileSchoolDialog;
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
        LogUtils.info("appbar", "**************" + verticalOffset);
        if (verticalOffset > -500) {
            mLytUserProfileStatus.setVisibility(View.GONE);
        } else {
            mLytUserProfileStatus.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.iv_back)
    public void backOnclick() {
        super.onBackPressed();
    }


    @Override
    public void onBackPressed(int id) {
        PersonalProfileFragment.getInstance().onDataRefresh();
        onBackPressed();
    }

    public DialogFragment callLanguage() {

        profileSearchLanguage = (ProfileSearchLanguage) getFragmentManager().findFragmentByTag(ProfileSearchLanguage.class.getName());
        if (profileSearchLanguage == null) {
            profileSearchLanguage = new ProfileSearchLanguage();

        }
        if (!profileSearchLanguage.isVisible() && !profileSearchLanguage.isAdded() && !isFinishing() && !mIsDestroyed) {
            profileSearchLanguage.show(getFragmentManager(), ProfileSearchLanguage.class.getName());
        }
        return profileSearchLanguage;
    }

    public void callEditEducation(EducationEntity educationEntity) {

       /* flprofile_container.setVisibility(View.VISIBLE);
        ProfileAddEditEducationFragment profileAddEditEducationFragment = new ProfileAddEditEducationFragment();
        Bundle bundleTravel = new Bundle();
        bundleTravel.putParcelable(AppConstants.EDUCATION_PROFILE, educationEntity);
        profileAddEditEducationFragment.setArguments(bundleTravel);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                .replace(R.id.profile_container, profileAddEditEducationFragment, ProfileAddEditEducationFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
*/
        openAddEditEducation(educationEntity);
    }

    @Override
    public void visitingCardOpen(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {


        flprofile_container.setVisibility(View.VISIBLE);
        Gson gson1 = new Gson();
        String jsonSections1 = gson1.toJson(profileEditVisitingCardResponse);
        Bundle bundle1 = new Bundle();
        bundle1.putString("user_visiting_card_value", jsonSections1);
        ProfileEditVisitingCardFragment profileEditVisitingCardFragment = new ProfileEditVisitingCardFragment();
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
                profileAddOtherFragment.setArguments(bundleAddOther);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileAddOtherFragment, ProfileAddOtherFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                break;


            case R.id.tv_edit_other_textline:
                ProfileAddOtherFragment profileEditOtherFragment = new ProfileAddOtherFragment();
                Bundle bundleEditOther = new Bundle();
                profileEditOtherFragment.setArguments(bundleEditOther);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.profile_container, profileEditOtherFragment, ProfileAddOtherFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

                break;
            default:

        }

    }

    @Override
    public void getEducationResponse(BoardingDataResponse boardingDataResponse) {

    }


    @Override
    public void getPersonalBasicDetailsResponse(BoardingDataResponse boardingDataResponse) {


    }

    @Override
    public void getprofiletracelflexibilityResponse(BoardingDataResponse boardingDataResponse) {

    }


    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getProfessionalBasicDetailsResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getProfessionalWorkLocationResponse(BoardingDataResponse boardingDataResponse) {

    }


    @Override
    public void getProfileVisitingCardResponse(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {

    }


    @Override
    public void getUserData(UserProfileResponse userProfileResponse) {


    }


    @Override
    public void getProfileListSuccess(GetTagData getAllData) {


    }

    @Override
    public void getProfileListSuccess(List<Doc> feedDetailList) {

    }

    @Override
    public void getWorkExpListSuccess(WorkExpListResponse workExpListResponse) {

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
    public void onBackPress() {
    }

    @Override
    public void onSearchClickIntrest() {

    }

    public DialogFragment callProfileLocation() {

        searchProfileLocation = (SearchProfileLocation) getFragmentManager().findFragmentByTag(SearchProfileLocation.class.getName());
        if (searchProfileLocation == null) {
            searchProfileLocation = new SearchProfileLocation();

        }
        if (!searchProfileLocation.isVisible() && !searchProfileLocation.isAdded() && !isFinishing() && !mIsDestroyed) {
            searchProfileLocation.show(getFragmentManager(), SearchProfileLocation.class.getName());
        }
        return searchProfileLocation;
    }

    public DialogFragment callProfileInterestPage() {
        ProfileSearchIntrestIn profileSearchIntrestIn = (ProfileSearchIntrestIn) getFragmentManager().findFragmentByTag(ProfileSearchIntrestIn.class.getName());
        if (profileSearchIntrestIn == null) {
            profileSearchIntrestIn = new ProfileSearchIntrestIn();

        }
        if (!profileSearchIntrestIn.isVisible() && !profileSearchIntrestIn.isAdded() && !isFinishing() && !mIsDestroyed) {
            profileSearchIntrestIn.show(getFragmentManager(), ProfileSearchIntrestIn.class.getName());
        }
        return profileSearchIntrestIn;
    }


    public DialogFragment callSearchGoodAtDialog() {

        SearchGoodAt searchGoodAt = (SearchGoodAt) getFragmentManager().findFragmentByTag(SearchGoodAt.class.getName());

        if (searchGoodAt == null) {
            searchGoodAt = new SearchGoodAt();
        }
        if (!searchGoodAt.isVisible() && !searchGoodAt.isAdded() && !isFinishing() && !mIsDestroyed) {

            searchGoodAt.show(getFragmentManager(), CommunitySearchTagsDialog.class.getName());
        }
        return searchGoodAt;
    }

    public void onTagsSubmit(String[] tagsval, long[] tagsid) {


        Fragment fragmentCommunityDetail = getSupportFragmentManager().findFragmentByTag(ProfileGoodAtFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragmentCommunityDetail)) {

            ((ProfileGoodAtFragment) fragmentCommunityDetail).setGoodAt(tagsval, tagsid);
        }


    }

    @Override
    public void aboutMeBack() {
        PersonalProfileFragment.getInstance().onDataRefresh();
        onBackPressed();
    }

    @Override
    public void locationBack() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void clintBack() {
        getSupportFragmentManager().popBackStack();
    }


    @Override
    public void OnLookinBack() {
        onBackPressed();
    }

    @Override
    public void onGoodAtBack() {
        ProffestionalProfileFragment.getInstance().onDataRefresh();
        onBackPressed();
    }

    @Override
    public void onBasicDetailsUpdate() {
        onGoodAtBack();
    }

    @Override
    public void onSaveSuccess() {
        aboutMeBack();
    }


    public DialogFragment functionAreaData() {
        functionalAreaDialogFragment = (FunctionalAreaDialogFragment) getFragmentManager().findFragmentByTag(FunctionalAreaDialogFragment.class.getName());
        if (functionalAreaDialogFragment == null) {
            functionalAreaDialogFragment = new FunctionalAreaDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.SOURCE_NAME, AppConstants.PROFILE_FRAGMENT);
            functionalAreaDialogFragment.setArguments(bundle);
        }
        if (!functionalAreaDialogFragment.isVisible() && !functionalAreaDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            functionalAreaDialogFragment.show(getFragmentManager(), FunctionalAreaDialogFragment.class.getName());
        }
        return functionalAreaDialogFragment;
    }

    public void onDoneFunctionArea() {
        if (null != functionalAreaDialogFragment) {
            functionalAreaDialogFragment.dismiss();
            if (null != profileWorkExperienceSelfEmploymentFragment) {
                profileWorkExperienceSelfEmploymentFragment.setFunctionAreaDataItem(mFunctionArea);
            }
        }
    }

    public DialogFragment searchLocationData(String masterDataSkill, OnBoardingEnum onBoardingEnum) {
        jobLocationSearchDialogFragment = (JobLocationSearchDialogFragment) getFragmentManager().findFragmentByTag(JobLocationSearchDialogFragment.class.getName());
        if (jobLocationSearchDialogFragment == null) {
            jobLocationSearchDialogFragment = new JobLocationSearchDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.MASTER_SKILL, masterDataSkill);
            bundle.putString(AppConstants.SOURCE_NAME, AppConstants.PROFILE_FRAGMENT);
            jobLocationSearchDialogFragment.setArguments(bundle);
        }
        if (!jobLocationSearchDialogFragment.isVisible() && !jobLocationSearchDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            jobLocationSearchDialogFragment.show(getFragmentManager(), JobLocationSearchDialogFragment.class.getName());
        }
        return jobLocationSearchDialogFragment;
    }

    public void saveJobLocation() {
        if (null != jobLocationSearchDialogFragment) {
            jobLocationSearchDialogFragment.dismiss();
        }
        if (null != profileWorkExperienceSelfEmploymentFragment) {
            profileWorkExperienceSelfEmploymentFragment.locationData(mJobLocationList);
        }
    }

    public void updateProfileWorkExpListItem() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ProfileWorkExperienceFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((ProfileWorkExperienceFragment) fragment).refreshWorkExpList();
        }
    }

    public void updateProffesstionalWorkExpListItem() {
        Fragment feature = viewPagerAdapter.getActiveFragment(mViewPager, AppConstants.ONE_CONSTANT);
        if (AppUtils.isFragmentUIActive(feature)) {
            ((ProffestionalProfileFragment) feature).onDataRefresh();
        }
    }

    public void updateProffesstionalEducationListItem() {

        ProffestionalProfileFragment.getInstance().onDataRefresh();
        onBackPressed();

    }

    public void backEducation() {
        mFragmentOpen.setEducationFragment(false);
        if (null != profileAddEditEducationFragment) {
            profileAddEditEducationFragment.dismiss();
        }
    }

    @Override
    public void onBasiEducationUpdate() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ProfileAddEducationFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((ProfileAddEducationFragment) fragment).refreshWorkExpList();
            mFragmentOpen.setEducationFragment(false);
            if (null != profileAddEditEducationFragment) {
                profileAddEditEducationFragment.dismiss();
            }
        }
    }

    public void updatePersonelWorkExpListItem() {
        Fragment feature = viewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
        if (AppUtils.isFragmentUIActive(feature)) {
            ((PersonalProfileFragment) feature).onDataRefresh();
        }
    }


}