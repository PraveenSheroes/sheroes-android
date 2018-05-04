package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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
import appliedlife.pvtltd.SHEROES.views.cutomeviews.DashedProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ravi on 02/05/18.
 */

public class ProfileLevelDialogFragment extends BaseDialogFragment {

    public enum ProfileLevelType {
        BEGINNER, INTERMEDIATE, COMPLETED
    }

    private String mBeginnerFields[] = {"Email Id", "Name"};
    private String mIntermediateFields[] = {"Location", "Mobile Number", "Bio"};
    private String mCompletedFields[] = {"Profile Pic", "DOB", "Relationship Status"};

    @Bind(R.id.dashed_progressbar)
    DashedProgressBar dashedProgressBar;

    @Bind(R.id.profile_status_level)
    TextView profileStatusLevel;

    @Bind(R.id.crown)
    ImageView crownIcon;

    @Bind(R.id.user_image)
    ImageView userImage;

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

    private UserSolrObj mUserMentorObj;
    private ProfileLevelType profileLevelType;

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

        if (getArguments() != null && getArguments().getSerializable("PROFILE_LEVEL") != null) {
            profileLevelType = (ProfileLevelType) getArguments().getSerializable("PROFILE_LEVEL");
        } else {
            if (mUserMentorObj != null) {
                profileLevelType = userLevel(mUserMentorObj);
            }
        }

        if (mUserMentorObj != null) {
            dashedProgressBar.setProgress(mUserMentorObj.getProfileWeighing(), false);

            invalidateProfileProgressBar(mUserMentorObj.getProfileWeighing());

            invalidateUserDetails(profileLevelType);
        }

        return view;
    }

    private void invalidateProfileProgressBar(int progressPercentage) {
        if (progressPercentage > 0 && progressPercentage <= 20) {
            if (mUserMentorObj.getProfileWeighing() >= 20) {
                beginnerTick.setBackgroundResource(R.drawable.ic_level_complete);
            } else {
                beginnerTick.setBackgroundResource(R.drawable.ic_level_incomplete);
            }
            intermediateTick.setBackgroundResource(R.drawable.ic_level_incomplete);
            allStarTick.setBackgroundResource(R.drawable.ic_all_level_incomplete);

        } else if (progressPercentage > 85 && progressPercentage <= 100) {
            if (mUserMentorObj.getProfileWeighing() >= 85) {
                allStarTick.setBackgroundResource(R.drawable.ic_all_level_complete);
            } else {
                allStarTick.setBackgroundResource(R.drawable.ic_all_level_incomplete);
            }
            beginnerTick.setBackgroundResource(R.drawable.ic_level_complete);
            intermediateTick.setBackgroundResource(R.drawable.ic_level_complete);

        } else {
            if (mUserMentorObj.getProfileWeighing() >= 60) {
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

        } else {
            CommonUtil.setImageSource(getActivity(), addIcon, R.drawable.green_tick);
            String names = filledFieldsMessage(profileLevelType);
            filledLeft.setText(names);
            addIcon.setEnabled(false);
            addIcon.setClickable(false);
            isAllFieldsDone = true;
        }

        updateDetails(profileLevelType, isAllFieldsDone);
    }

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

    private void updateDetails(ProfileLevelType profileLevelType, boolean isRequiredFieldsFilled) {

        switch (profileLevelType) {
            case BEGINNER:
                crownIcon.setVisibility(View.VISIBLE);
                CommonUtil.setImageSource(getActivity(), userImage, R.drawable.profile_level_beginner_user);
                profileStatusLevel.setText("Beginner");
                nextLevel.setText("Next level");
                break;
            case INTERMEDIATE:
                crownIcon.setVisibility(View.VISIBLE);
                CommonUtil.setImageSource(getActivity(), userImage, R.drawable.ic_profile_level_intermediate);
                profileStatusLevel.setText("Intermediate");
                nextLevel.setText("Next level");

                if (isRequiredFieldsFilled) {
                    String mutualCommunityText = getResources().getString(R.string.profile_progress_message, mUserMentorObj.getNameOrTitle(), "Your profile is shaping up. Each time you add to your profile, it improves your overall experience with SHEROES and is likely to get you more followers.");
                    message.setText(mutualCommunityText);
                } else {
                    String mutualCommunityText = getResources().getString(R.string.profile_progress_message, mUserMentorObj.getNameOrTitle(), "Your profile is shaping up. Make your profile trustworthy by adding more details about yourself and get more followers.");

                    message.setText(mutualCommunityText);
                }

                break;

            case COMPLETED:
                crownIcon.setVisibility(View.GONE);
                CommonUtil.setImageSource(getActivity(), userImage, R.drawable.ic_profile_level_all_star);
                profileStatusLevel.setText("All star");
                nextLevel.setText("Got it");

                if(isRequiredFieldsFilled) {
                    String mutualCommunityText = getResources().getString(R.string.profile_progress_message, mUserMentorObj.getNameOrTitle(), "Yay! You have reached the All-Star League. A rich profile enhances your experience with SHEROES and is likely to get you more followers.");
                    message.setText(mutualCommunityText);

                } else {
                    String mutualCommunityText = getResources().getString(R.string.profile_progress_message, mUserMentorObj.getNameOrTitle(), "Get to this level and you’ll be in our own league! Make yourself recognizable and gain more followers by adding a nice profile picture and a few more details.");
                    message.setText(mutualCommunityText);
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

            String name = options[i];
            message.append(name);

            if (i < length - 1) {
                message.append(AppConstants.COMMA);
                message.append(AppConstants.SPACE);
            }
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

        if(StringUtil.isNotNullOrEmptyString(unfilledSections)) {
            int length = options.length;
            for (int i = 0; i < length; i++) {

                String name = options[i];
                if (unfilledSections.contains(name)) {
                    if (i == 0) {
                        message.append("Add");
                        message.append(AppConstants.SPACE);
                    }
                    message.append(name);

                    if (i < length - 1) {
                        message.append(AppConstants.COMMA);
                        message.append(AppConstants.SPACE);
                    }
                }
            }
        }

        return message.toString();
    }


    private ProfileLevelType userLevel(UserSolrObj userSolrObj) {
        ProfileLevelType profileType;

        if (userSolrObj.getProfileWeighing() > 0 && userSolrObj.getProfileWeighing() <= 20) {
            profileType = ProfileLevelType.BEGINNER;
        } else if (userSolrObj.getProfileWeighing() > 85 && userSolrObj.getProfileWeighing() <= 100) {
            profileType = ProfileLevelType.COMPLETED;
        } else {
            profileType = ProfileLevelType.INTERMEDIATE;
        }

        return profileType;
    }

}