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
import appliedlife.pvtltd.SHEROES.views.activities.EditUserProfileActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.DashedProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ravi on 02/05/18.
 */

public class ProfileLevelDialogFragment extends BaseDialogFragment {

    enum ProfileLevelType {
        BEGINNER, INTERMEDIATE, COMPLETED
    }

    private String mBeginnerFields[] = {"Email Id", "Name"};
    private String mIntermediateFields[] = {"Location", "Mobile Number", "Bio"};
    private String mCompletedFields[] = {"Profile Pic", "DOB", "Relationship Status"};

    @Bind(R.id.progressBar2)
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

    @Bind(R.id.buttonPanel)
    Button nextLevel;

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

        if (mUserMentorObj != null) {
            profileLevelType = userLevel(mUserMentorObj);
            dashedProgressBar.setProgress(mUserMentorObj.getProfileWeighing(), false);

            invalidateUserDetails(profileLevelType);
        }

        return view;
    }

    private void invalidateUserDetails(ProfileLevelType profileLevelType) {
        String fields = unfilledFields(profileLevelType);

        if (StringUtil.isNotNullOrEmptyString(fields)) {
            filledLeft.setText(fields);
            CommonUtil.setImageSource(getActivity(), addIcon, R.drawable.ic_add);
            addIcon.setEnabled(true);
            addIcon.setClickable(true);

        } else {
            CommonUtil.setImageSource(getActivity(), addIcon, R.drawable.green_tick);
            String names = filledFieldsMessage(profileLevelType);
            filledLeft.setText(names);
            addIcon.setEnabled(false);
            addIcon.setClickable(false);
        }

        updateDetails(profileLevelType);
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

    private void updateDetails(ProfileLevelType profileLevelType) {

        switch (profileLevelType) {
            case BEGINNER:
                crownIcon.setVisibility(View.VISIBLE);
                CommonUtil.setImageSource(getActivity(), userImage, R.drawable.profile_level_beginner_user);
                profileStatusLevel.setText("Beginner");
                break;
            case INTERMEDIATE:
                crownIcon.setVisibility(View.VISIBLE);
                CommonUtil.setImageSource(getActivity(), userImage, R.drawable.ic_profile_level_intermediate);
                profileStatusLevel.setText("Intermediate");
                nextLevel.setText("Next level");
                break;

            case COMPLETED:
                crownIcon.setVisibility(View.GONE);
                CommonUtil.setImageSource(getActivity(), userImage, R.drawable.ic_profile_level_all_star);
                profileStatusLevel.setText("All star");
                nextLevel.setText("Got it");
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
