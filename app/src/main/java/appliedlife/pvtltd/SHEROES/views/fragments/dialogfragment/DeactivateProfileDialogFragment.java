package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.DeactivationReason;
import appliedlife.pvtltd.SHEROES.models.DeactivationReasons;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FollowedUsersResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.spam.DeactivateUserRequest;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.SpamUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author ravi
 *
 * Deactive user profile. Only community mod and admin have access deactive user profile and clear its all
 * activity (if they tick checkbox i.e clear user activity)
 */
public class DeactivateProfileDialogFragment extends BaseDialogFragment implements IProfileView {

    //region bind variable
    @Bind(R.id.reason_title)
    TextView mReasonTitle;

    @Bind(R.id.reason_sub_title)
    TextView mReasonSubTitle;

    @Bind(R.id.delete_user_activity)
    CheckBox mDeleteUserActivityCheck;

    @Bind(R.id.options_container)
    RadioGroup mDeactivationOptions;

    @Bind(R.id.scroll_container)
    ScrollView mScrollView;

    @Bind(R.id.submit)
    Button mSubmit;

    @Bind(R.id.edit_text_reason)
    EditText mReasonEdit;
    //endregion bind variable

    //region injected variable
    @Inject
    ProfilePresenterImpl mProfilePresenter;

    @Inject
    Preference<AppConfiguration> mConfiguration;
    //endregion injected variable

    //region private variable
    private UserSolrObj mUserSolrObj;
    private String mSourceScreenName;
    private Activity mContext;
    //endregion private variable

    //region fragment lifecycle method
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (getActivity() == null) return null;

        mContext = getActivity();
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.dialog_deactivate_user, container, false);
        ButterKnife.bind(this, view);
        mProfilePresenter.attachView(this);

        List<DeactivationReason> deactivationReasons;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null && mConfiguration.get().configData.deactivationReasons != null && mConfiguration.get().configData.deactivationReasons.getDeactivationReasons() != null) {
            deactivationReasons = mConfiguration.get().configData.deactivationReasons.getDeactivationReasons();
        } else {
            String deactivateReasonsContent = AppUtils.getStringContent(AppConstants.DEACTIVATE_REASONS_FILE); //read user deactivation reasons from local file
            DeactivationReasons reasons = AppUtils.parseUsingGSONFromJSON(deactivateReasonsContent, DeactivationReasons.class.getName());
            deactivationReasons = reasons != null ? reasons.getDeactivationReasons() : null;
        }

        if (deactivationReasons == null || deactivationReasons.size() <= 0) return null;

        mSourceScreenName = getArguments().getString(AppConstants.SOURCE_NAME);
        Parcelable parcelable = getArguments().getParcelable(AppConstants.USER);
        if (null != parcelable) {
            mUserSolrObj = Parcels.unwrap(parcelable);
        }

        if(mUserSolrObj ==null) return null;

        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(CommonUtil.convertDpToPixel(16, getActivity()), CommonUtil.convertDpToPixel(10, getActivity()), 0, 0);

        mReasonTitle.setLayoutParams(layoutParams);
        mReasonSubTitle.setLayoutParams(layoutParams);

        SpamUtil.addDeactivationReasonsToRadioGroup(getActivity(), deactivationReasons, mDeactivationOptions);
        LinearLayout.LayoutParams scrollviewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CommonUtil.convertDpToPixel(250, getActivity()));
        mScrollView.setLayoutParams(scrollviewParams);

        return view;
    }
    //endregion fragment lifecycle method

    //region onclick method
    @OnClick(R.id.submit)
    public void onSubmitClick() {
        if (mDeactivationOptions.getCheckedRadioButtonId() != -1) {

            DeactivateUserRequest deactivateUserRequest = new DeactivateUserRequest();
            deactivateUserRequest.setUserId(mUserSolrObj.getIdOfEntityOrParticipant());
            deactivateUserRequest.setReactivateUser(false);
            deactivateUserRequest.setReasonForDeactivationDetails("");

            if (mDeleteUserActivityCheck.isChecked()) {
                deactivateUserRequest.setRemoveCommentByUser(true);
                deactivateUserRequest.setRemovePostByUser(true);
            } else {
                deactivateUserRequest.setRemoveCommentByUser(false);
                deactivateUserRequest.setRemovePostByUser(false);
            }

            RadioButton radioButton = mDeactivationOptions.findViewById(mDeactivationOptions.getCheckedRadioButtonId());
            DeactivationReason deactivationReason = (DeactivationReason) radioButton.getTag();
            if (deactivationReason != null) {
                deactivateUserRequest.setDeactivationReason(deactivationReason.getDeactivationReasonId());
                if (deactivationReason.getDeactivationReason().equalsIgnoreCase(getString(R.string.other))) {

                    if (mReasonEdit.getVisibility() == View.VISIBLE) {
                        if (mReasonEdit.getText().length() > 0 && mReasonEdit.getText().toString().trim().length() > 0) {
                            deactivateUserRequest.setReasonForDeactivationDetails(mReasonEdit.getText().toString());
                            mProfilePresenter.deactivateUser(deactivateUserRequest); //mSubmit
                            dismiss();

                            analyticsOnDeactivation(mUserSolrObj); //add profile deactivation analytics
                        } else {
                            mReasonEdit.setVisibility(View.VISIBLE);
                            mReasonEdit.setError(getResources().getString(R.string.add_reason));
                        }
                    } else {
                        mReasonEdit.setVisibility(View.VISIBLE);
                        SpamUtil.hideSpamReason(mDeactivationOptions, mDeactivationOptions.getCheckedRadioButtonId());
                        LinearLayout.LayoutParams scrollviewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CommonUtil.convertDpToPixel(50, getActivity()));
                        mScrollView.setLayoutParams(scrollviewParams);
                    }
                } else {
                    mProfilePresenter.deactivateUser(deactivateUserRequest);
                    dismiss();
                    analyticsOnDeactivation(mUserSolrObj); //add profile deactivation analytics
                }
            }
        }
    }
    //endregion onclick method

    //region override methods
    private void analyticsOnDeactivation(UserSolrObj userSolrObj) {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(userSolrObj.getIdOfEntityOrParticipant()))
                        .name(userSolrObj.getNameOrTitle())
                        .isMentor(userSolrObj.isAuthorMentor())
                        .build();
        AnalyticsManager.trackEvent(Event.PROFILE_DEACTIVATE, mSourceScreenName, properties);
    }

    @Override
    public void getFollowedMentors(FollowedUsersResponse profileFeedResponsePojo) {
    }

    @Override
    public void getUsersCommunities(ProfileCommunitiesResponsePojo userCommunities) {
    }

    @Override
    public void onSpamPostOrCommentReported(SpamResponse communityFeedSolrObj) {
    }

    @Override
    public void onUserDeactivation(BaseResponse baseResponse, boolean isUserDeactivated) {
        if (mContext instanceof IProfileView) {
            UserSolrObj userSolrObj = (UserSolrObj) baseResponse;
            ((IProfileView) mContext).onUserDeactivation(userSolrObj, isUserDeactivated);
            mContext = null;
        }
    }
    //endregion override methods
}
