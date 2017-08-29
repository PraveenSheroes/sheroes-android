package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f2prateek.rx.preferences.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageEvent;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.communityRequestBuilder;

/**
 * Created by SHEROES-TECH on 09-02-2017.
 */

public class CommunityOptionJoinDialog extends BaseDialogFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(CommunityOptionJoinDialog.class);
    @Inject
    Preference<LoginResponse> userPreference;
    private FeedDetail mFeedDetail;
    @Inject
    HomePresenter mHomePresenter;
    @Inject
    AppUtils mAppUtils;
    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.join_community_region_dialog, container, false);
        ButterKnife.bind(this, view);
        mHomePresenter.attachView(this);
        mMoEHelper = MoEHelper.getInstance(getActivity());
        payloadBuilder = new PayloadBuilder();
        mFeedDetail = getArguments().getParcelable(DISMISS_PARENT_ON_OK_OR_BACK);
        setCancelable(true);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismiss();
            }
        };
    }

    @OnClick(R.id.tv_higly_intreseted)
    public void highlyOnClick() {
        joinNewUser(getString(R.string.ID_COMMUNITY_JOIN_REGION1));
    }

    @OnClick(R.id.tv_already_member)
    public void alreadyOnClick() {
        joinNewUser(getString(R.string.ID_COMMUNITY_JOIN_REGION2));

    }

    private void joinNewUser(String reasonToJoin) {
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            List<Long> userIdList = new ArrayList();
            userIdList.add((long) userPreference.get().getUserSummary().getUserId());
            mHomePresenter.communityJoinFromPresenter(communityRequestBuilder(userIdList, mFeedDetail.getIdOfEntityOrParticipant(), reasonToJoin));
        }
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {

    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                mFeedDetail.setRequestPending(true);
                mFeedDetail.setOwner(false);
                if (mFeedDetail.isFromHome()) {
                    ((HomeActivity) getActivity()).onJoinEventSuccessResult(baseResponse.getStatus(), mFeedDetail);
                } else {
                    ((CommunitiesDetailActivity) getActivity()).onJoinDialogSuccessResult(baseResponse.getStatus(), mFeedDetail);
                }
                entityMoEngageJoinedCommunity(mFeedDetail.getNameOrTitle(), mFeedDetail.getIdOfEntityOrParticipant(), mFeedDetail.isClosedCommunity(), MoEngageConstants.COMMUNITY_TAG,TAG,mFeedDetail.getItemPosition());
                dismiss();
                break;
            case AppConstants.FAILED:
                if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getScreenName()) && mFeedDetail.getScreenName().equalsIgnoreCase(AppConstants.FEATURE_FRAGMENT)) {
                    ((HomeActivity) getActivity()).onJoinEventSuccessResult(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), mFeedDetail);
                } else {
                    ((CommunitiesDetailActivity) getActivity()).onJoinDialogSuccessResult(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), mFeedDetail);
                }
                dismiss();
                break;
            default:
        }

    }
    public void entityMoEngageJoinedCommunity(String communityName, long communityId, boolean isClose, String communityTag, String screenName, int position) {
        payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_NAME, communityName);
        payloadBuilder.putAttrLong(MoEngageConstants.COMMUNITY_ID, communityId);
        if (isClose) {
            payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_PRIVACY, getString(R.string.ID_CLOSE_PRIVACY));

        } else {
            payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_PRIVACY, getString(R.string.ID_OPEN));
        }
        payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_TAG, communityTag);
        payloadBuilder.putAttrString(MoEngageConstants.SCREEN_NAME, screenName);
        payloadBuilder.putAttrInt(MoEngageConstants.POSITION_OF_ENTITY, position);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_JOINED_COMMUNITY.value, payloadBuilder.build());
    }



}

