package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.Space;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.DashProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ravi on 02/05/18.
 * Dialog to display the different profile completion level
 */

public class ProfileLevelDialogFragment extends BaseDialogFragment {

    //region private member variable
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
    private UserSolrObj mUserMentorObj;
    private ProfileLevelType profileLevelType;
    //endregion

    //region enum
    public enum ProfileLevelType {
        BEGINNER, INTERMEDIATE, COMPLETED
    }
    //endregion

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
    TextView beginnerTick;

    @Bind(R.id.intermediate)
    TextView intermediateTick;

    @Bind(R.id.expert)
    TextView allStarTick;
    //endregion

    //region Fragment methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.profile_status_level, container, false);
        Parcelable parcelable = getArguments().getParcelable(AppConstants.USER);
        ButterKnife.bind(this, view);
        if (null != parcelable) {
            mUserMentorObj = Parcels.unwrap(parcelable);
        }

        if (getArguments() != null && getArguments().getSerializable(PROFILE_LEVEL) != null) {
            profileLevelType = (ProfileLevelType) getArguments().getSerializable(PROFILE_LEVEL);
        } else {
            if (mUserMentorObj != null) {
                profileLevelType = userLevel(mUserMentorObj);
            }
        }

        if (mUserMentorObj != null) {
            dashProgressBar.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            dashProgressBar.setProgress(mUserMentorObj.getProfileCompletionWeight(), false);

            invalidateProfileProgressBar(mUserMentorObj.getProfileCompletionWeight());

            invalidateUserDetails(profileLevelType);
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

        if (profileLevelType == ProfileLevelType.BEGINNER) {
            profileLevelType = ProfileLevelType.INTERMEDIATE;
            invalidateUserDetails(profileLevelType);
        } else if (profileLevelType == ProfileLevelType.INTERMEDIATE) {
            profileLevelType = ProfileLevelType.COMPLETED;
            invalidateUserDetails(profileLevelType);
        } else {
            dismiss();
        }
    }


    @OnClick(R.id.tick)
    public void onaddFields() {
        dismiss();
        ((ProfileActivity) getActivity()).navigateToProfileEditing();
    }
    //endregion

    //region Private methods
    private void invalidateProfileProgressBar(int progressPercentage) {
        if (progressPercentage > BEGINNER_START_LIMIT && progressPercentage <= BEGINNER_END_LIMIT) {
            if (mUserMentorObj.getProfileCompletionWeight() >= BEGINNER_END_LIMIT) {
                beginnerTick.setBackgroundResource(R.drawable.ic_level_complete);
            } else {
                beginnerTick.setBackgroundResource(R.drawable.ic_level_incomplete);
            }
            intermediateTick.setBackgroundResource(R.drawable.ic_level_incomplete);
            allStarTick.setBackgroundResource(R.drawable.ic_all_level_incomplete);

        } else if (progressPercentage > ALL_STAR_START_LIMIT && progressPercentage <= ALL_STAR_END_LIMIT) {
            if (mUserMentorObj.getProfileCompletionWeight() >= ALL_STAR_START_LIMIT) {
                allStarTick.setBackgroundResource(R.drawable.ic_all_level_complete);
            } else {
                allStarTick.setBackgroundResource(R.drawable.ic_all_level_incomplete);
            }
            beginnerTick.setBackgroundResource(R.drawable.ic_level_complete);
            intermediateTick.setBackgroundResource(R.drawable.ic_level_complete);

        } else {
            if (mUserMentorObj.getProfileCompletionWeight() >= INTERMEDIATE_END_LIMIT) {
                intermediateTick.setBackgroundResource(R.drawable.ic_level_complete);
            } else {
                intermediateTick.setBackgroundResource(R.drawable.ic_level_incomplete);
            }
            allStarTick.setBackgroundResource(R.drawable.ic_level_incomplete);
            beginnerTick.setBackgroundResource(R.drawable.ic_level_complete);
        }
    }

    private void invalidateUserDetails(ProfileLevelType profileLevelType) {
        String fields = unfilledFields(profileLevelType);

        boolean isAllFieldsDone;

        if (StringUtil.isNotNullOrEmptyString(fields)) {
            filledLeft.setText(fields);
            CommonUtil.setImageSource(getActivity(), addIcon, R.drawable.ic_add);
            addIcon.setEnabled(true);
            addIcon.setClickable(true);
            isAllFieldsDone = false;
            levelAchieved.setVisibility(View.INVISIBLE);
        } else {
            CommonUtil.setImageSource(getActivity(), addIcon, R.drawable.green_tick);
            String names = filledFieldsMessage(profileLevelType);
            filledLeft.setText(names);
            addIcon.setEnabled(false);
            addIcon.setClickable(false);
            isAllFieldsDone = true;
            levelAchieved.setVisibility(View.VISIBLE);
        }

        updateDetails(profileLevelType, isAllFieldsDone);
    }

    private void updateDetails(ProfileLevelType profileLevelType, boolean isRequiredFieldsFilled) {

        switch (profileLevelType) {
            case BEGINNER:
                crownIcon.setVisibility(View.VISIBLE);
                CommonUtil.setImageSource(getActivity(), userImage, R.drawable.vector_profile_beginner_user);
                profileStatusLevel.setText(R.string.beginner);
                nextLevel.setText(R.string.next_level);
                String beginnerMessage = getResources().getString(R.string.profile_progress_message, mUserMentorObj.getNameOrTitle(), getString(R.string.beginner_message));
                message.setText(beginnerMessage);
                break;

            case INTERMEDIATE:
                crownIcon.setVisibility(View.VISIBLE);
                CommonUtil.setImageSource(getActivity(), userImage, R.drawable.vector_profile_intermediate_user);
                profileStatusLevel.setText(R.string.intermediate);
                nextLevel.setText(R.string.next_level);

                if (isRequiredFieldsFilled) {
                    String intermediateMessage = getResources().getString(R.string.profile_progress_message, mUserMentorObj.getNameOrTitle(), getString(R.string.intermediate_filled));
                    message.setText(intermediateMessage);
                } else {
                    String intermediateMessage = getResources().getString(R.string.profile_progress_message, mUserMentorObj.getNameOrTitle(), getString(R.string.intermediate_unfilled));
                    message.setText(intermediateMessage);
                }
                break;

            case COMPLETED:
                CommonUtil.setImageSource(getActivity(), userImage, R.drawable.vector_profile_allstar_user);
                profileStatusLevel.setText(R.string.all_star);
                nextLevel.setText(R.string.got_it);
                crownIcon.setVisibility(View.GONE);

                if (isRequiredFieldsFilled) {
                    String allStarMessage = getResources().getString(R.string.profile_progress_message, mUserMentorObj.getNameOrTitle(), getString(R.string.all_star_filled));
                    message.setText(allStarMessage);
                } else {
                    String allStarMessage = getResources().getString(R.string.profile_progress_message, mUserMentorObj.getNameOrTitle(), getString(R.string.all_star_unfilled));
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
        String unfilledSections = mUserMentorObj.getUnfilledProfileFields();

        if (StringUtil.isNotNullOrEmptyString(unfilledSections)) {
            int length = options.length;
            for (int i = 0; i < length; i++) {

                String name = options[i];
                if (unfilledSections.contains(name)) {
                    if (i == 0) {
                        message.append(getString(R.string.add));
                        message.append(AppConstants.SPACE);
                    } else {
                        message.append(AppConstants.COMMA);
                        message.append(AppConstants.SPACE);
                    }
                    message.append(name);
                }
            }
        }

        return message.toString();
    }


    private ProfileLevelType userLevel(UserSolrObj userSolrObj) {
        ProfileLevelType profileType;

        if (userSolrObj.getProfileCompletionWeight() > BEGINNER_START_LIMIT && userSolrObj.getProfileCompletionWeight() <= BEGINNER_END_LIMIT) {
            profileType = ProfileLevelType.BEGINNER;
        } else if (userSolrObj.getProfileCompletionWeight() > ALL_STAR_START_LIMIT && userSolrObj.getProfileCompletionWeight() <= ALL_STAR_END_LIMIT) {
            profileType = ProfileLevelType.COMPLETED;
        } else {
            profileType = ProfileLevelType.INTERMEDIATE;
        }

        return profileType;
    }
    //endregion

}