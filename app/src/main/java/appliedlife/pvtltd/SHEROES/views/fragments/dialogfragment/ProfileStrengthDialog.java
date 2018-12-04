package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.ProgressbarView;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.EditUserProfileActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.DashProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ravi on 02/05/18.
 * Dialog to display the different profile completion level
 */

public class ProfileStrengthDialog extends BaseDialogFragment implements ProgressbarView {

    //region private member variable
    public static final String SCREEN_NAME = "Profile Progress Dialog";
    public static final String PROFILE_LEVEL = "PROFILE_LEVEL";
    public static final int BEGINNER_START_LIMIT = 0;
    public static final int BEGINNER_END_LIMIT = 20;
    public static final int INTERMEDIATE_END_LIMIT = 60;
    public static final int ALL_STAR_START_LIMIT = 85;
    public static final int ALL_STAR_END_LIMIT = 100;
    //endregion

    //region private member variable
    private String mBeginnerFields[] = {"Email Id", "Name"};
    private String mIntermediateFields[] = {"Location", "Mobile Number", "Bio"};
    private String mCompletedFields[] = {"Profile Pic", "DOB", "Relationship Status"};
    private UserSolrObj mUserSolrObj;
    private ProfileStrengthType mProfileStrengthType;
    //endregion

    //region enum
    public enum ProfileStrengthType {
        BEGINNER, INTERMEDIATE, ALLSTAR
    }
    //endregion

    //region inject variable
    @Inject
    Preference<AppConfiguration> mConfiguration;
    //endregion inject variable

    //region Bind view variables
    @Bind(R.id.dashed_progressbar)
    DashProgressBar mDashProgressBar;

    @Bind(R.id.profile_status_level)
    TextView mProfileStatusLevel;

    @Bind(R.id.crown)
    ImageView mCrownIcon;

    @Bind(R.id.user_image)
    ImageView mUserImage;

    @Bind(R.id.level_achieved)
    TextView mLevelAchieved;

    @Bind(R.id.filled_left)
    TextView mFieldsLeft;

    @Bind(R.id.tick)
    ImageView mAddIcon;

    @Bind(R.id.message)
    TextView mMessage;

    @Bind(R.id.buttonPanel)
    Button mNextLevel;

    @Bind(R.id.beginner)
    ImageView mBeginnerTick;

    @Bind(R.id.intermediate)
    ImageView mIntermediateTick;

    @Bind(R.id.all_star)
    ImageView mAllStarTick;
    //endregion

    //region Fragment methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (getActivity() == null) return null;

        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.profile_status_level, container, false);
        Parcelable parcelable = getArguments().getParcelable(AppConstants.USER);
        ButterKnife.bind(this, view);
        if (null != parcelable) {
            mUserSolrObj = Parcels.unwrap(parcelable);
        }

        if (getArguments() != null && getArguments().getSerializable(PROFILE_LEVEL) != null) {
            mProfileStrengthType = (ProfileStrengthType) getArguments().getSerializable(PROFILE_LEVEL);
        } else {
            if (mUserSolrObj != null) {
                mProfileStrengthType = userLevel(mUserSolrObj);
            }
        }

        if (mUserSolrObj != null) {
            mDashProgressBar.setListener(this);
            mDashProgressBar.setProgress(mUserSolrObj.getProfileCompletionWeight(), false);

            if (mConfiguration.isSet() && mConfiguration.get().configData != null) {
                mDashProgressBar.setTotalDash(mConfiguration.get().configData.maxDash);
            } else {
                mDashProgressBar.setTotalDash(new ConfigData().maxDash);
            }

            invalidateProfileProgressBar(mUserSolrObj.getProfileCompletionWeight());

            invalidateUserDetails(mProfileStrengthType, true);

        }

        return view;
    }
    //endregion

    //region onclick method
    @OnClick(R.id.cross)
    protected void crossClick() {
        dismiss();
    }

    @OnClick(R.id.buttonPanel)
    public void nextClick() {

        if (mProfileStrengthType == ProfileStrengthType.BEGINNER) {
            mProfileStrengthType = ProfileStrengthType.INTERMEDIATE;
            invalidateUserDetails(mProfileStrengthType, false);
        } else if (mProfileStrengthType == ProfileStrengthType.INTERMEDIATE) {
            mProfileStrengthType = ProfileStrengthType.ALLSTAR;
            invalidateUserDetails(mProfileStrengthType, false);
        } else {
            dismiss();
        }
    }

    @OnClick({R.id.beginner})
    protected void openBeginnerDialog() {
        if (mProfileStrengthType != ProfileStrengthType.BEGINNER) {
            mProfileStrengthType = ProfileStrengthType.BEGINNER;
            invalidateUserDetails(ProfileStrengthType.BEGINNER, false);
        }
    }

    @OnClick(R.id.intermediate)
    protected void openIntermediateProgressDialog() {
        if (mProfileStrengthType != ProfileStrengthType.INTERMEDIATE) {
            mProfileStrengthType = ProfileStrengthType.INTERMEDIATE;
            invalidateUserDetails(ProfileStrengthType.INTERMEDIATE, false);
        }
    }

    @OnClick(R.id.all_star)
    protected void openAllStarProgressDialog() {
        if (mProfileStrengthType != ProfileStrengthType.ALLSTAR) {
            mProfileStrengthType = ProfileStrengthType.ALLSTAR;
            invalidateUserDetails(ProfileStrengthType.ALLSTAR, false);
        }
    }

    @OnClick({R.id.tick})
    public void onAddFieldsClick() {
        dismiss();

        //Analytics Event for edit profile open
        String strengthLevel = userLevel(mUserSolrObj).name();
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .isMentor((mUserSolrObj.getUserSubType()!=null && mUserSolrObj.getUserSubType().equalsIgnoreCase(AppConstants.CHAMPION_SUBTYPE)) || mUserSolrObj.isAuthorMentor())
                        .profileStrength(strengthLevel)
                        .build();
        EditUserProfileActivity.navigateTo(getActivity(), SCREEN_NAME, mUserSolrObj.getImageUrl(), properties, 1);
    }
    //endregion

    //region private methods
    private void invalidateProfileProgressBar(float progressPercentage) {
        if (progressPercentage > BEGINNER_START_LIMIT && progressPercentage <= BEGINNER_END_LIMIT) {
            if (mUserSolrObj.getProfileCompletionWeight() >= BEGINNER_END_LIMIT) {
                mBeginnerTick.setImageResource(R.drawable.vector_level_complete);
            } else {
                mBeginnerTick.setImageResource(R.drawable.vector_level_incomplete);
            }
            mIntermediateTick.setImageResource(R.drawable.vector_level_incomplete);
            mAllStarTick.setImageResource(R.drawable.vector_all_level_incomplete);

        } else if (progressPercentage > ALL_STAR_START_LIMIT && progressPercentage <= ALL_STAR_END_LIMIT) {
            if (mUserSolrObj.getProfileCompletionWeight() >= ALL_STAR_END_LIMIT || !CommonUtil.isNotEmpty(mUserSolrObj.getUnfilledProfileFields())) {
                mAllStarTick.setImageResource(R.drawable.vector_all_level_complete);
            } else {
                mAllStarTick.setImageResource(R.drawable.vector_all_level_incomplete);
            }
            mBeginnerTick.setImageResource(R.drawable.vector_level_complete);
            mIntermediateTick.setImageResource(R.drawable.vector_level_complete);

        } else {
            if (mUserSolrObj.getProfileCompletionWeight() >= INTERMEDIATE_END_LIMIT) {
                mIntermediateTick.setImageResource(R.drawable.vector_level_complete);
            } else {
                mIntermediateTick.setImageResource(R.drawable.vector_level_incomplete);
            }
            mAllStarTick.setImageResource(R.drawable.vector_all_level_incomplete);
            mBeginnerTick.setImageResource(R.drawable.vector_level_complete);
        }
    }

    private void invalidateUserDetails(ProfileStrengthType profileStrengthType, boolean hasSource) {
        String fields = unfilledFields(profileStrengthType);

        boolean isAllFieldsDone;

        if (StringUtil.isNotNullOrEmptyString(fields)) {
            mFieldsLeft.setText(fields);
            mAddIcon.setImageResource(R.drawable.vector_add);
            mAddIcon.setEnabled(true);
            mAddIcon.setClickable(true);
            isAllFieldsDone = false;
            mLevelAchieved.setVisibility(View.INVISIBLE);
        } else {
            mAddIcon.setImageResource(R.drawable.vector_green_tick);
            String names = filledFieldsMessage(profileStrengthType);
            mFieldsLeft.setText(names);
            mAddIcon.setEnabled(false);
            mAddIcon.setClickable(false);
            isAllFieldsDone = true;
            mLevelAchieved.setVisibility(View.VISIBLE);
        }

        //Add analytics screen open Event
        String strengthLevel = userLevel(mUserSolrObj).name();
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .isMentor((mUserSolrObj.getUserSubType()!=null && mUserSolrObj.getUserSubType().equalsIgnoreCase(AppConstants.CHAMPION_SUBTYPE)) || mUserSolrObj.isAuthorMentor())
                        .profileStrength(strengthLevel)
                        .build();
        if (hasSource && getArguments() != null && getArguments().getString(AppConstants.SOURCE_NAME) != null) {
            String sourceScreenName = getArguments().getString(AppConstants.SOURCE_NAME);
            AnalyticsManager.trackScreenView(SCREEN_NAME, sourceScreenName, properties);
        } else {
            AnalyticsManager.trackScreenView(SCREEN_NAME, SCREEN_NAME, properties); //Moving next stage on same screen
        }

        updateDetails(profileStrengthType, isAllFieldsDone);
    }

    private void updateDetails(ProfileStrengthType profileStrengthType, boolean isRequiredFieldsFilled) {

        switch (profileStrengthType) {
            case BEGINNER:
                mCrownIcon.setVisibility(View.VISIBLE);
                mUserImage.setImageResource(R.drawable.vector_profile_beginner_user);
                mProfileStatusLevel.setText(R.string.beginner);
                mNextLevel.setText(R.string.next_level);
                String beginnerMessage = getResources().getString(R.string.profile_progress_message, mUserSolrObj.getNameOrTitle(), getString(R.string.beginner_message));
                mMessage.setText(beginnerMessage);
                break;

            case INTERMEDIATE:
                mCrownIcon.setVisibility(View.VISIBLE);
                mUserImage.setImageResource(R.drawable.vector_profile_intermediate_user);
                mProfileStatusLevel.setText(R.string.intermediate);
                mNextLevel.setText(R.string.next_level);

                if (isRequiredFieldsFilled) {
                    String intermediateMessage = getResources().getString(R.string.profile_progress_message, mUserSolrObj.getNameOrTitle(), getString(R.string.intermediate_filled));
                    mMessage.setText(intermediateMessage);
                } else {
                    String intermediateMessage = getResources().getString(R.string.profile_progress_message, mUserSolrObj.getNameOrTitle(), getString(R.string.intermediate_unfilled));
                    mMessage.setText(intermediateMessage);
                }
                break;

            case ALLSTAR:
                mUserImage.setImageResource(R.drawable.vector_profile_allstar_user);
                mProfileStatusLevel.setText(R.string.all_star);
                mNextLevel.setText(R.string.got_it);
                mCrownIcon.setVisibility(View.GONE);

                if (isRequiredFieldsFilled) {
                    String allStarMessage = getResources().getString(R.string.profile_progress_message, mUserSolrObj.getNameOrTitle(), getString(R.string.all_star_filled));
                    mMessage.setText(allStarMessage);
                } else {
                    String allStarMessage = getResources().getString(R.string.profile_progress_message, mUserSolrObj.getNameOrTitle(), getString(R.string.all_star_unfilled));
                    mMessage.setText(allStarMessage);
                }
                break;
        }
    }

    private String filledFieldsMessage(ProfileStrengthType profileStrengthType) {

        StringBuilder message = new StringBuilder();
        String options[];

        if (profileStrengthType == ProfileStrengthType.BEGINNER) {
            options = mBeginnerFields;
        } else if (profileStrengthType == ProfileStrengthType.INTERMEDIATE) {
            options = mIntermediateFields;
        } else {
            options = mCompletedFields;
        }

        int length = options.length;

        for (int i = 0; i < length; i++) {
            if (i > 0) {
                message.append(AppConstants.COMMA);
                message.append(AppConstants.SPACE);
            }
            String name = options[i];
            message.append(name);

        }
        return message.toString();
    }

    private String unfilledFields(ProfileStrengthType profileStrengthType) {

        StringBuilder message = new StringBuilder();
        String options[];

        if (profileStrengthType == ProfileStrengthType.BEGINNER) {
            options = mBeginnerFields;
        } else if (profileStrengthType == ProfileStrengthType.INTERMEDIATE) {
            options = mIntermediateFields;
        } else {
            options = mCompletedFields;
        }
        String unfilledSections = mUserSolrObj.getUnfilledProfileFields();

        if (StringUtil.isNotNullOrEmptyString(unfilledSections)) {
            int length = options.length;
            int count = 0;
            for (int i = 0; i < length; i++) {
                String name = options[i];
                if (unfilledSections.contains(name)) {

                    if (count == 0) {
                        count++;
                        message.append(getString(R.string.add));
                        message.append(AppConstants.SPACE);
                    }
                    message.append(name);

                    if (i < length - 1) {
                        message.append(AppConstants.COMMA);
                        message.append(AppConstants.SPACE);
                    }
                }
            }

            if (message.toString().endsWith(AppConstants.SPACE)) {
                return message.substring(0, message.lastIndexOf(AppConstants.COMMA));
            }
        }

        return message.toString();
    }

    private ProfileStrengthType userLevel(UserSolrObj userSolrObj) {
        ProfileStrengthType profileType;

        if (userSolrObj.getProfileCompletionWeight() > BEGINNER_START_LIMIT && userSolrObj.getProfileCompletionWeight() <= INTERMEDIATE_END_LIMIT) {
            profileType = ProfileStrengthType.BEGINNER;
        } else if (userSolrObj.getProfileCompletionWeight() >= ALL_STAR_END_LIMIT) {
            profileType = ProfileStrengthType.ALLSTAR;
        } else {
            profileType = ProfileStrengthType.INTERMEDIATE;
        }

        return profileType;
    }
    //endregion

    //region public methods
    @Override
    public void onViewRendered(float dashWidth) {
        ConfigData configData = new ConfigData();
        int beginnerTickIndex = configData.beginnerStartIndex;
        int intermediateTickIndex = configData.intermediateStartIndex;

        if (mConfiguration.isSet() && mConfiguration.get().configData != null) {
            beginnerTickIndex = mConfiguration.get().configData.beginnerStartIndex;
            intermediateTickIndex = mConfiguration.get().configData.intermediateStartIndex;
        }
        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins((int) (dashWidth * beginnerTickIndex), 0, 0, 0);
        mBeginnerTick.setLayoutParams(buttonLayoutParams);

        RelativeLayout.LayoutParams intermediateLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        intermediateLayoutParams.setMargins((int) (dashWidth * intermediateTickIndex), 0, 0, 0);
        mIntermediateTick.setLayoutParams(intermediateLayoutParams);
    }
    //endregion public methods
}