package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.imageops.CropImage;
import appliedlife.pvtltd.SHEROES.imageops.CropImageView;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.CompressImageUtil;
import appliedlife.pvtltd.SHEROES.utils.ErrorUtil;
import appliedlife.pvtltd.SHEROES.utils.FeedUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileStrengthDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IFollowCallback;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.PROFILE_NOTIFICATION_ID;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_CHAMPION_TITLE;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_EDIT_PROFILE;
import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_SELF_PROFILE_DETAIL;

/**
 * Created by Praveen_Singh on 04-08-2017.
 */

public class ProfileActivity extends BaseActivity implements BaseHolderInterface, IFollowCallback {

    //region constants
    private final String TAG = LogUtils.makeLogTag(ProfileActivity.class);
    private static final String SCREEN_LABEL = "Profile Screen";

    //region injected variable
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    ProfilePresenterImpl mProfilePresenter;
    @Inject
    AppUtils mAppUtils;
    @Inject
    Preference<AppConfiguration> mConfiguration;
    @Inject
    HomePresenter mHomePresenter;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    @Inject
    FeedUtils mFeedUtils;
    @Inject
    ErrorUtil mErrorUtil;
    //endregion injected variable

    //region bind variables
    @Bind(R.id.profile_frame_layout)
    FrameLayout mProfileFrameLayout;
    //endregion bind variables

    //region member variable
    private String userNameTitle;
    private boolean isOwnProfile, mIsChampion, mIsWriteAStory;
    private long mChampionId, mLoggedInUserId = -1;
    private int mFromNotification;
    //endregion member variable

    //region view variable
    private FeedDetail mFeedDetail;
    private String mEncodeImageUrl;
    private Uri mImageCaptureUri;
    private UserSolrObj mUserSolarObject;
    private ProfileStrengthDialog.ProfileStrengthType mProfileStrengthType;
    private ProfileFragment mProfileFragment;
    //endregion view variable

    //region activity lifecycle methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_profile_layout);
        ButterKnife.bind(this);
        setupToolbarItemsColor();
        invalidateOptionsMenu();

        if (null != getIntent() && null != getIntent().getExtras()) {
            mUserSolarObject = Parcels.unwrap(getIntent().getParcelableExtra(AppConstants.GROWTH_PUBLIC_PROFILE));
            mFeedDetail = Parcels.unwrap(getIntent().getParcelableExtra(AppConstants.MENTOR_DETAIL));
            mFromNotification = getIntent().getExtras().getInt(AppConstants.FROM_PUSH_NOTIFICATION);
            mChampionId = getIntent().getExtras().getLong(AppConstants.CHAMPION_ID);
            mIsChampion = getIntent().getExtras().getBoolean(AppConstants.IS_CHAMPION_ID);
            mIsWriteAStory = getIntent().getExtras().getBoolean(STORIES_TAB);
            mProfileStrengthType = (ProfileStrengthDialog.ProfileStrengthType) getIntent().getSerializableExtra(ProfileStrengthDialog.PROFILE_LEVEL);
        }

        mProfileFragment = ProfileFragment.createInstance(mUserSolarObject, mFeedDetail, mProfileStrengthType, mChampionId, mIsChampion, mFromNotification, AppConstants.DRAWER_NAVIGATION, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL, mIsWriteAStory);;
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        addNewFragment(mProfileFragment, R.id.profile_frame_layout, ProfileFragment.class.getName(), null, false);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    //endregion activity lifecycle methods

    //region override methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public boolean shouldTrackScreen() {
        return true;
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
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void navigateToProfileView(BaseResponse baseResponse, int mValue) {
        if (mValue == REQUEST_CODE_FOR_SELF_PROFILE_DETAIL && mLoggedInUserId != -1) {
            championDetailActivity(mLoggedInUserId, 1, mIsChampion, AppConstants.FEED_SCREEN); //self profile
        } else if (mValue == REQUEST_CODE_CHAMPION_TITLE) {
            UserPostSolrObj feedDetail = (UserPostSolrObj) baseResponse;
            ProfileActivity.navigateTo(this, feedDetail.getAuthorParticipantId(), mIsChampion,
                    PROFILE_NOTIFICATION_ID, AppConstants.FEED_SCREEN, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL, false);
        }
    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {
    }

    @Override
    public void onProfileFollowed(UserSolrObj userSolrObj) {
        mProfileFragment.onProfileFollowed(userSolrObj);
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mHomePresenter;
    }

    @Override
    protected Map<String, Object> getExtraPropertiesToTrack() {
        return new EventProperty.Builder()
                .id(String.valueOf(mChampionId))
                .isMentor(mIsChampion)
                .isOwnProfile(isOwnProfile)
                .build();
    }

    @Override
    protected boolean trackScreenTime() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        if (null != intent) {
            if (resultCode == REQUEST_CODE_FOR_EDIT_PROFILE) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    updateProfileDetails(bundle.getString(EditUserProfileActivity.USER_NAME), bundle.getString(EditUserProfileActivity.LOCATION), bundle.getString(EditUserProfileActivity.USER_DESCRIPTION), bundle.getString(EditUserProfileActivity.IMAGE_URL),
                            bundle.getString(EditUserProfileActivity.FILLED_FIELDS), bundle.getString(EditUserProfileActivity.UNFILLED_FIELDS), bundle.getFloat(EditUserProfileActivity.PROFILE_COMPLETION_WEIGHT));
                }
            } else {
                switch (requestCode) {
                    case AppConstants.REQUEST_CODE_FOR_CAMERA:
                    case AppConstants.REQUEST_CODE_FOR_GALLERY:
                        mImageCaptureUri = intent.getData();
                        if (resultCode == Activity.RESULT_OK) {
                            mProfileFragment.croppingIMG();
                        }
                        break;
                    case AppConstants.REQUEST_CODE_FOR_IMAGE_CROPPING:
                        mProfileFragment.imageCropping(intent);
                        break;
                    case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                        CropImage.ActivityResult result = CropImage.getActivityResult(intent);
                        if (resultCode == RESULT_OK) {
                            try {
                                if (result != null && result.getUri() != null && result.getUri().getPath() != null) {
                                    File file = new File(result.getUri().getPath());
                                    Bitmap photo = CompressImageUtil.decodeFile(file);
                                    mEncodeImageUrl = CompressImageUtil.setImageOnHolder(photo);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                          mProfileFragment.getUserSummaryDetails(mAppUtils.getUserProfileRequestBuilder(AppConstants.PROFILE_PIC_SUB_TYPE, AppConstants.PROFILE_PIC_TYPE, mEncodeImageUrl));
                        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                            Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                        LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + requestCode);
                }
            }
        }
    }
    //endregion override methods

    private void setupToolbarItemsColor() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.vector_back_arrow);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
    }

    public void updateFollowOnAuthorFollowed(boolean isFollowed) {
        mProfileFragment.updateFollowOnAuthorFollowed(isFollowed);
    }

    private void championDetailActivity(Long userId, int position, boolean isMentor, String source) {
        CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
        communityFeedSolrObj.setIdOfEntityOrParticipant(userId);
        communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        communityFeedSolrObj.setItemPosition(position);
        mFeedDetail = communityFeedSolrObj;
        ProfileActivity.navigateTo(this, communityFeedSolrObj, userId, isMentor, position, source, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    private void updateProfileDetails(String name, String location, String userBio, String imageUrl, String filledFields, String unfilledFields, float progressPercentage) {
        mProfileFragment.updateProfileDetails(name, location, userBio, imageUrl, filledFields, unfilledFields, progressPercentage);
    }
    //endregion private methods

    //region public methods
    public String getUserNameTitle() {
        return userNameTitle;
    }

    public void selectImageFrmCamera(int viewType) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        openGalleryOrCamera(viewType);
    }

    public void openGalleryOrCamera(int viewType) {
        CropImage.activity(null, viewType).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(400, 400)
                .setAspectRatio(1, 1)
                .setAllowRotation(true)
                .start(this);
    }

    public void championDetailActivity(Long userId, boolean isMentor) {
        mUserSolarObject = new UserSolrObj();
        mUserSolarObject.setIdOfEntityOrParticipant(userId);
        mUserSolarObject.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        ProfileActivity.navigateTo(this, mUserSolarObject, userId, isMentor, AppConstants.PROFILE_CHAMPION, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
    }

    public static void navigateTo(Activity fromActivity, UserSolrObj dataItem, long userId, boolean isMentor, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);
        Bundle bundle = new Bundle();
        Parcelable parcelableFeedDetail = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.MENTOR_DETAIL, parcelableFeedDetail);
        Parcelable parcelableMentor = Parcels.wrap(dataItem);
        intent.putExtra(AppConstants.CHAMPION_ID, userId);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentor);
        intent.putExtra(AppConstants.IS_CHAMPION_ID, isMentor);
        intent.putExtras(bundle);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, CommunityFeedSolrObj dataItem, long mChampionId, boolean isMentor, int position, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);
        Bundle bundle = new Bundle();
        dataItem.setIdOfEntityOrParticipant(mChampionId);
        dataItem.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        dataItem.setItemPosition(position);
        Parcelable parcelable = Parcels.wrap(dataItem);
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, parcelable);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, null);
        intent.putExtra(AppConstants.CHAMPION_ID, mChampionId);
        intent.putExtra(AppConstants.IS_CHAMPION_ID, isMentor);
        intent.putExtras(bundle);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, long mChampionId, boolean isMentor, int notificationId, String sourceScreen, HashMap<String, Object> properties, int requestCode, boolean isWriteAStory) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);
        if(isWriteAStory) {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        if (notificationId != -1) {
            intent.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, notificationId);
        }
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        intent.putExtra(BaseActivity.STORIES_TAB, isWriteAStory);
        intent.putExtra(AppConstants.CHAMPION_ID, mChampionId);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        intent.putExtra(AppConstants.IS_CHAMPION_ID, isMentor);
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }
    //endregion public methods
}