package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import appliedlife.pvtltd.SHEROES.models.Spam;
import appliedlife.pvtltd.SHEROES.models.SpamReasons;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FollowedUsersResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunitiesResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamPostRequest;
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
 * Report user profile
 */
public class ReportUserProfileDialogFragment extends BaseDialogFragment implements IProfileView {

    //region bind variable
    @Bind(R.id.reason_title)
    TextView mReasonTitle;

    @Bind(R.id.reason_sub_title)
    TextView mReasonSubTitle;

    @Bind(R.id.options_container)
    RadioGroup mSpamOptions;

    @Bind(R.id.edit_text_reason)
    EditText mReason;
    //endregion bind variable

    //region injected variable
    @Inject
    ProfilePresenterImpl mProfilePresenter;

    @Inject
    Preference<AppConfiguration> mConfiguration;
    //endregion injected variable

    //region private variable
    private UserSolrObj mUserSolrObj;
    private SpamPostRequest mSpamRequest;
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
        View view = inflater.inflate(R.layout.dialog_spam_options, container, false);
        ButterKnife.bind(this, view);
        mProfilePresenter.attachView(this);

        SpamReasons spamReasons;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null && mConfiguration.get().configData.reasonOfSpamCategory != null) {
            spamReasons = mConfiguration.get().configData.reasonOfSpamCategory;
        } else {
            String spamReasonsContent = AppUtils.getStringContent(AppConstants.SPAM_REASONS_FILE); //read spam reasons from local file
            spamReasons = AppUtils.parseUsingGSONFromJSON(spamReasonsContent, SpamReasons.class.getName());
        }

        if (spamReasons == null) return null;

        mSourceScreenName = getArguments().getString(AppConstants.SOURCE_NAME);
        long loggedInUserId = getArguments().getLong(AppConstants.LOGGED_IN_USER);
        Parcelable parcelable = getArguments().getParcelable(AppConstants.USER);
        if (null != parcelable) {
            mUserSolrObj = Parcels.unwrap(parcelable);
        }

        if(mUserSolrObj ==null || loggedInUserId == -1) return null;

        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(CommonUtil.convertDpToPixel(16, getActivity()), CommonUtil.convertDpToPixel(10, getActivity()), 0, 0);

        mReasonTitle.setLayoutParams(layoutParams);
        mReasonSubTitle.setLayoutParams(layoutParams);

        List<Spam> spamList = spamReasons.getUserTypeSpams();
        mSpamRequest = SpamUtil.createProfileSpamByUser(mUserSolrObj, loggedInUserId);

        if (mSpamRequest == null || spamList == null) return null;
        SpamUtil.addRadioToView(getActivity(), spamList, mSpamOptions);

        return view;
    }
    //endregion fragment lifecycle method

    //region onclick method
    @OnClick(R.id.submit)
    public void onSubmitClick() {
        if (mSpamOptions.getCheckedRadioButtonId() != -1) {
            RadioButton radioButton = mSpamOptions.findViewById(mSpamOptions.getCheckedRadioButtonId());
            Spam spam = (Spam) radioButton.getTag();
            if (spam != null) {
                mSpamRequest.setSpamReason(spam.getReason());
                mSpamRequest.setScore(spam.getScore());
                if (spam.getLabel().equalsIgnoreCase(getString(R.string.others))) {
                    if (mReason.getVisibility() == View.VISIBLE) {
                        if (mReason.getText().length() > 0 && mReason.getText().toString().trim().length() > 0) {
                            mSpamRequest.setSpamReason(spam.getReason().concat(":" + mReason.getText().toString()));
                            mProfilePresenter.reportSpamPostOrComment(mSpamRequest); //mSubmit
                            dismiss();
                            onProfileReported(mUserSolrObj);   //report the profile

                        } else {
                            mReason.setError(getString(R.string.add_reason));
                        }
                    } else {
                        mReason.setVisibility(View.VISIBLE);
                        SpamUtil.hideSpamReason(mSpamOptions, mSpamOptions.getCheckedRadioButtonId());
                    }
                } else {
                    mProfilePresenter.reportSpamPostOrComment(mSpamRequest);  //mSubmit request
                    dismiss();
                    onProfileReported(mUserSolrObj);   //report the profile
                }
            }
        }
    }
    //endregion onclick method

    //region override methods
    private void onProfileReported(UserSolrObj userSolrObj) {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(userSolrObj.getIdOfEntityOrParticipant()))
                        .name(userSolrObj.getNameOrTitle())
                        .isMentor(userSolrObj.isAuthorMentor())
                        .build();
        AnalyticsManager.trackEvent(Event.PROFILE_REPORTED, mSourceScreenName, properties);
    }

    @Override
    public void getFollowedMentors(FollowedUsersResponse profileFeedResponsePojo) {
    }

    @Override
    public void getUsersCommunities(ProfileCommunitiesResponsePojo userCommunities) {
    }

    @Override
    public void onSpamPostOrCommentReported(SpamResponse communityFeedSolrObj) {
        if (mContext instanceof IProfileView) {
            ((IProfileView) mContext).onSpamPostOrCommentReported(communityFeedSolrObj);
            mContext = null;
        }
    }

    @Override
    public void onUserDeactivation(BaseResponse baseResponse, boolean isDeactivated) {
    }
    //endregion override methods
}
