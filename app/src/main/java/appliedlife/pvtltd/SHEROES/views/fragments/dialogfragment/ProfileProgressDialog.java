package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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
import appliedlife.pvtltd.SHEROES.models.Configuration;
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

public class ProfileProgressDialog extends BaseDialogFragment implements ProgressbarView {

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
    private ProfileLevelType mProfileLevelType;
    //endregion

    //region enum
    public enum ProfileLevelType {
        BEGINNER, INTERMEDIATE, ALLSTAR
    }
    //endregion

    @Inject
    Preference<Configuration> mConfiguration;

    //region Bind view variables
    @Bind(R.id.dashed_progressbar)
    DashProgressBar dashProgressBar;

    @Bind(R.id.profile_status_level)
    TextView profileStatusLevel;

    @Bind(R.id.crown)
    ImageView crownIcon;

    @Bind(R.id.user_image)
    ImageView userImage;

    @Bind(R.id.level_achieved)
    TextView levelAchieved;

    @Bind(R.id.filled_left)
    TextView filledLeft;

    @Bind(R.id.tick)
    ImageView addIcon;

    @Bind(R.id.message)
    TextView message;

    @Bind(R.id.buttonPanel)
    Button nextLevel;

    @Bind(R.id.beginner)
    ImageView beginnerTick;

    @Bind(R.id.intermediate)
    ImageView intermediateTick;

    @Bind(R.id.all_star)
    ImageView allStarTick;
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
            mProfileLevelType = (ProfileLevelType) getArguments().getSerializable(PROFILE_LEVEL);
        } else {
            if (mUserSolrObj != null) {
                mProfileLevelType = userLevel(mUserSolrObj);
            }
        }

        if (mUserSolrObj != null) {
            dashProgressBar.setListener(this);
            dashProgressBar.setProgress(mUserSolrObj.getProfileCompletionWeight(), false);

            if (mConfiguration.isSet() && mConfiguration.get().configData != null) {
                dashProgressBar.setTotalDash(mConfiguration.get().configData.maxDash);
            } else {
                dashProgressBar.setTotalDash(new ConfigData().maxDash);
            }

            invalidateProfileProgressBar(mUserSolrObj.getProfileCompletionWeight());

            invalidateUserDetails(mProfileLevelType);

            //Add analytics screen open Event
            if (getArguments() != null && getArguments().getString(AppConstants.SOURCE_NAME) != null) {
                String strengthLevel = userLevel(mUserSolrObj).name();
                String sourceScreenName = getArguments().getString(AppConstants.SOURCE_NAME);
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .isMentor(mUserSolrObj.isAuthorMentor())
                                .profileStrength(strengthLevel)
                                .build();
                AnalyticsManager.trackScreenView(SCREEN_NAME, sourceScreenName, properties);
            }
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

        if (mProfileLevelType == ProfileLevelType.BEGINNER) {
            mProfileLevelType = ProfileLevelType.INTERMEDIATE;
            invalidateUserDetails(mProfileLevelType);
        } else if (mProfileLevelType == ProfileLevelType.INTERMEDIATE) {
            mProfileLevelType = ProfileLevelType.ALLSTAR;
            invalidateUserDetails(mProfileLevelType);
        } else {
            dismiss();
        }
    }

    @OnClick({R.id.beginner})
    protected void openBeginnerDialog() {
        if (mProfileLevelType != ProfileLevelType.BEGINNER) {
            mProfileLevelType = ProfileLevelType.BEGINNER;
            invalidateUserDetails(ProfileProgressDialog.ProfileLevelType.BEGINNER);
        }
    }

    @OnClick(R.id.intermediate)
    protected void openIntermediateProgressDialog() {
        if (mProfileLevelType != ProfileLevelType.INTERMEDIATE) {
            mProfileLevelType = ProfileLevelType.INTERMEDIATE;
            invalidateUserDetails(ProfileProgressDialog.ProfileLevelType.INTERMEDIATE);
        }
    }

    @OnClick(R.id.all_star)
    protected void openAllStarProgressDialog() {
        if (mProfileLevelType != ProfileLevelType.ALLSTAR) {
            mProfileLevelType = ProfileLevelType.ALLSTAR;
            invalidateUserDetails(ProfileProgressDialog.ProfileLevelType.ALLSTAR);
        }
    }

    @OnClick({R.id.tick})
    public void onAddFieldsClick() {
        dismiss();

        //Analytics Event for edit profile open
        String strengthLevel = userLevel(mUserSolrObj).name();
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .isMentor(mUserSolrObj.isAuthorMentor())
                        .profileStrength(strengthLevel)
                        .build();
        EditUserProfileActivity.navigateTo(getActivity(), SCREEN_NAME, mUserSolrObj.getImageUrl(), properties, 1);
    }
    //endregion

    //region Private methods
    private void invalidateProfileProgressBar(float progressPercentage) {
        if (progressPercentage > BEGINNER_START_LIMIT && progressPercentage <= BEGINNER_END_LIMIT) {
            if (mUserSolrObj.getProfileCompletionWeight() >= BEGINNER_END_LIMIT) {
                beginnerTick.setImageResource(R.drawable.ic_level_complete);
            } else {
                beginnerTick.setImageResource(R.drawable.ic_level_incomplete);
            }
            intermediateTick.setImageResource(R.drawable.ic_level_incomplete);
            allStarTick.setImageResource(R.drawable.ic_all_level_incomplete);

        } else if (progressPercentage > ALL_STAR_START_LIMIT && progressPercentage <= ALL_STAR_END_LIMIT) {
            if (mUserSolrObj.getProfileCompletionWeight() >= ALL_STAR_END_LIMIT || !CommonUtil.isNotEmpty(mUserSolrObj.getUnfilledProfileFields())) {
                allStarTick.setImageResource(R.drawable.ic_all_level_complete);
            } else {
                allStarTick.setImageResource(R.drawable.ic_all_level_incomplete);
            }
            beginnerTick.setImageResource(R.drawable.ic_level_complete);
            intermediateTick.setImageResource(R.drawable.ic_level_complete);

        } else {
            if (mUserSolrObj.getProfileCompletionWeight() >= INTERMEDIATE_END_LIMIT) {
                intermediateTick.setImageResource(R.drawable.ic_level_complete);
            } else {
                intermediateTick.setImageResource(R.drawable.ic_level_incomplete);
            }
            allStarTick.setImageResource(R.drawable.ic_all_level_incomplete);
            beginnerTick.setImageResource(R.drawable.ic_level_complete);
        }
    }

    private void invalidateUserDetails(ProfileLevelType profileLevelType) {
        String fields = unfilledFields(profileLevelType);

        boolean isAllFieldsDone;

        if (StringUtil.isNotNullOrEmptyString(fields)) {
            filledLeft.setText(fields);
            addIcon.setImageResource(R.drawable.vector_add);
            addIcon.setEnabled(true);
            addIcon.setClickable(true);
            isAllFieldsDone = false;
            levelAchieved.setVisibility(View.INVISIBLE);
        } else {
            addIcon.setImageResource(R.drawable.ic_green_tick);
            String names = filledFieldsMessage(profileLevelType);
            filledLeft.setText(names);
            addIcon.setEnabled(false);
            addIcon.setClickable(false);
            isAllFieldsDone = true;
            levelAchieved.setVisibility(View.VISIBLE);
        }

        //Add analytics screen open Event
        if (getArguments() != null && getArguments().getString(AppConstants.SOURCE_NAME) != null) {
            String strengthLevel = userLevel(mUserSolrObj).name();
            HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .isMentor(mUserSolrObj.isAuthorMentor())
                            .profileStrength(strengthLevel)
                            .build();
            AnalyticsManager.trackScreenView(SCREEN_NAME, properties);
        }

        updateDetails(profileLevelType, isAllFieldsDone);
    }

    private void updateDetails(ProfileLevelType profileLevelType, boolean isRequiredFieldsFilled) {

        switch (profileLevelType) {
            case BEGINNER:
                crownIcon.setVisibility(View.VISIBLE);
                userImage.setImageResource(R.drawable.vector_profile_beginner_user);
                profileStatusLevel.setText(R.string.beginner);
                nextLevel.setText(R.string.next_level);
                String beginnerMessage = getResources().getString(R.string.profile_progress_message, mUserSolrObj.getNameOrTitle(), getString(R.string.beginner_message));
                message.setText(beginnerMessage);
                break;

            case INTERMEDIATE:
                crownIcon.setVisibility(View.VISIBLE);
                userImage.setImageResource(R.drawable.vector_profile_intermediate_user);
                profileStatusLevel.setText(R.string.intermediate);
                nextLevel.setText(R.string.next_level);

                if (isRequiredFieldsFilled) {
                    String intermediateMessage = getResources().getString(R.string.profile_progress_message, mUserSolrObj.getNameOrTitle(), getString(R.string.intermediate_filled));
                    message.setText(intermediateMessage);
                } else {
                    String intermediateMessage = getResources().getString(R.string.profile_progress_message, mUserSolrObj.getNameOrTitle(), getString(R.string.intermediate_unfilled));
                    message.setText(intermediateMessage);
                }
                break;

            case ALLSTAR:
                userImage.setImageResource(R.drawable.vector_profile_allstar_user);
                profileStatusLevel.setText(R.string.all_star);
                nextLevel.setText(R.string.got_it);
                crownIcon.setVisibility(View.GONE);

                if (isRequiredFieldsFilled) {
                    String allStarMessage = getResources().getString(R.string.profile_progress_message, mUserSolrObj.getNameOrTitle(), getString(R.string.all_star_filled));
                    message.setText(allStarMessage);
                } else {
                    String allStarMessage = getResources().getString(R.string.profile_progress_message, mUserSolrObj.getNameOrTitle(), getString(R.string.all_star_unfilled));
                    message.setText(allStarMessage);
                }
                break;
        }
    }

    private String filledFieldsMessage(ProfileLevelType profileLevelType) {

        StringBuilder message = new StringBuilder();
        String options[];

        if (profileLevelType == ProfileLevelType.BEGINNER) {
            options = mBeginnerFields;
        } else if (profileLevelType == ProfileLevelType.INTERMEDIATE) {
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

    private String unfilledFields(ProfileLevelType profileLevelType) {

        StringBuilder message = new StringBuilder();
        String options[];

        if (profileLevelType == ProfileLevelType.BEGINNER) {
            options = mBeginnerFields;
        } else if (profileLevelType == ProfileLevelType.INTERMEDIATE) {
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

    private ProfileLevelType userLevel(UserSolrObj userSolrObj) {
        ProfileLevelType profileType;

        if (userSolrObj.getProfileCompletionWeight() > BEGINNER_START_LIMIT && userSolrObj.getProfileCompletionWeight() <= INTERMEDIATE_END_LIMIT) {
            profileType = ProfileLevelType.BEGINNER;
        } else if (userSolrObj.getProfileCompletionWeight() >= ALL_STAR_END_LIMIT) {
            profileType = ProfileLevelType.ALLSTAR;
        } else {
            profileType = ProfileLevelType.INTERMEDIATE;
        }

        return profileType;
    }
    //endregion


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
        beginnerTick.setLayoutParams(buttonLayoutParams);

        RelativeLayout.LayoutParams intermediateLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        intermediateLayoutParams.setMargins((int) (dashWidth * intermediateTickIndex), 0, 0, 0);
        intermediateTick.setLayoutParams(intermediateLayoutParams);
    }
}