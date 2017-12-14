package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import org.parceler.Parcels;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.CreateCommunityPresenter;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_LIKE_UNLIKE;

/**
 * Created by Ajit Kumar on 30-01-2017.
 */

public class ShareCommunityFragment extends BaseFragment {
    private static final String SCREEN_LABEL = "Share Community Screen";
    private final String TAG = LogUtils.makeLogTag(ShareCommunityFragment.class);
    @Bind(R.id.tv_close_community)
    TextView mTvCloseCommunity;
    @Bind(R.id.tv_share_via_social_media)
    TextView mTvShareviasocialmedia;
    @Bind(R.id.tv_share_community_title)
    TextView mTvShareCommunityTitle;
    @Bind(R.id.tv_community_title)
    TextView mTvCommunityTitle;
    @Bind(R.id.et_share_community_via_email)
    EditText mEtShareViaEmail;
    @Bind(R.id.tv_share_community_url)
    TextView mTvShareUrl;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.tv_share)
    TextView mTvShare;
    private FeedDetail mFeedDetail;
    @Inject
    CreateCommunityPresenter mCreateCommunityPresenter;
    @Inject
    AppUtils mAppUtils;
    private MoEHelper mMoEHelper;
    private MoEngageUtills moEngageUtills;
    private PayloadBuilder payloadBuilder;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.community_share_fragment, container, false);
        ButterKnife.bind(this, view);
        mMoEHelper = MoEHelper.getInstance(getActivity());
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        if (null != getArguments()) {
            mFeedDetail = Parcels.unwrap(getArguments().getParcelable(AppConstants.SHARE));
        }
        mCreateCommunityPresenter.attachView(this);
        setProgressBar(mProgressBar);
        if (null != mFeedDetail) {
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getNameOrTitle())) {
                mTvShareCommunityTitle.setText(mFeedDetail.getNameOrTitle());
            }
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getDeepLinkUrl())) {
                mTvShareUrl.setText(mFeedDetail.getDeepLinkUrl());
            }
        }
        mTvCommunityTitle.setText(getString(R.string.ID_SHARE));
        //Fabric.with(getActivity(), new Crashlytics());
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_SHARE_COMMUNITY_SCREEN));
        return view;
    }

    @OnClick(R.id.tv_share_via_social_media)
    public void socialShareFunction() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, mFeedDetail.getDeepLinkUrl());
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));
        ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_COMMUNITY_LINK, AppConstants.EMPTY_STRING);
        HashMap<String, Object> properties = new EventProperty.Builder().id(Long.toString(mFeedDetail.getIdOfEntityOrParticipant())).name(mFeedDetail.getNameOrTitle()).build();
        AnalyticsManager.trackEvent(Event.COMMUNITY_SHARED, getScreenName(), properties);
        moEngageUtills.entityMoEngageCardShareVia(getActivity(),mMoEHelper,payloadBuilder,mFeedDetail, MoEngageConstants.SHARE_VIA_SOCIAL);
    }

    @OnClick(R.id.tv_share)
    public void shareViaEmail() {
        if (StringUtil.isNotNullOrEmptyString(mEtShareViaEmail.getText().toString())) {
            boolean cancel = false;
            View focusView = null;
            if (!mAppUtils.checkEmail(mEtShareViaEmail.getText().toString())) {
                mEtShareViaEmail.setError(getString(R.string.ID_ERROR_INVALID_EMAIL));
                focusView = mEtShareViaEmail;
                cancel = true;
            }
            if (cancel) {
                focusView.requestFocus();

            } else {
                mTvShare.setEnabled(false);
                mCreateCommunityPresenter.shareViaEmailPresenter(mAppUtils.shareRequestBuilder(mFeedDetail.getDeepLinkUrl(), mFeedDetail.getIdOfEntityOrParticipant(), mEtShareViaEmail.getText().toString(), getString(R.string.ID_COMM_SHARE_EMAIL_LINK)));
                ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_COMMUNITY_LINK_ON_EMAIL, AppConstants.EMPTY_STRING);
                HashMap<String, Object> properties = new EventProperty.Builder().id(Long.toString(mFeedDetail.getIdOfEntityOrParticipant())).name(mFeedDetail.getNameOrTitle()).build();
                AnalyticsManager.trackEvent(Event.COMMUNITY_SHARED, getScreenName(), properties);
            }

        } else {
            Toast.makeText(getContext(), getString(R.string.ID_EMAIL_HINT_TEXT), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.tv_close_community)
    public void onCloseClick() {
        ((CommunitiesDetailActivity) getActivity()).getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, CommunityEnum communityEnum) {
        mTvShare.setEnabled(true);
        switch (communityEnum) {
            case SHARE_COMMUNITY:
                sharedMail(baseResponse);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + communityEnum);
        }
    }

    protected void sharedMail(BaseResponse baseResponse) {
        if (null != mFeedDetail) {
            switch (baseResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    mEtShareViaEmail.setText(AppConstants.EMPTY_STRING);
                    Toast.makeText(getContext(), getString(R.string.ID_SHARED), Toast.LENGTH_SHORT).show();
                    moEngageUtills.entityMoEngageCardShareVia(getActivity(),mMoEHelper,payloadBuilder,mFeedDetail, MoEngageConstants.SHARE_VIA_EMAIL);
                    break;
                case AppConstants.FAILED:
                    showError(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_LIKE_UNLIKE);
                    break;
                default:
                    showError(getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);
            }
        }
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}
