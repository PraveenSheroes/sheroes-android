package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.f2prateek.rx.preferences.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.Member;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.MembersList;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.presenters.MembersPresenter;
import appliedlife.pvtltd.SHEROES.presenters.OwnerPresenter;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.AllMembersView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.communityRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.makeFeedRequest;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.ownerRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.removeMemberRequestBuilder;

/**
 * Created by Ajit Kumar on 01-02-2017.
 */

public class CommunityOpenAboutFragment extends BaseFragment implements CommunityView, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, AllMembersView {
    private static final String SCREEN_LABEL = "Community Info Screen";
    private final String TAG = LogUtils.makeLogTag(CommunityOpenAboutFragment.class);
    @Inject
    MembersPresenter mMemberpresenter;
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    HomePresenter mHomePresenter;
    List<Member> mOwnerListResponse;
    @Bind(R.id.rv_about_community_owner_list)
    RecyclerView mAboutCommunityOwnerRecyclerView;
    @Bind(R.id.iv_community_cover_img)
    ImageView mIvcommunity_cover_img;
    @Bind(R.id.tv_about_community_des)
    TextView mTvAboutCommunityDes;
    @Bind(R.id.tv_community_name)
    TextView mTvCommunityName;
    @Bind(R.id.tv_community_tags)
    TextView mTvCommunityTags;
    @Bind(R.id.tv_community_organisation)
    TextView mTvCommunityOrganization;
    @Bind(R.id.tv_community_members)
    TextView mTvCommunityMembers;
    @Bind(R.id.tv_community_requested)
    TextView mTvCommunityRequested;
    @Inject
    OwnerPresenter mOwnerPresenter;
    @Bind(R.id.iv_about_community)
    ImageView mOptionIv;

    @Bind(R.id.iv_community_post_icon)
    ImageView mFbAboutCommunityLogo;

    @Bind(R.id.li_spam_post_ui)
    LinearLayout liSpamPostUi;

    @Bind(R.id.tv_community_join_invite)
    TextView mTvJoinInviteView;
    @Bind(R.id.tv_post_moderation_count)
    TextView tvPostModerationCount;
    @Bind(R.id.tv_community_add_more)
    TextView tvCommunityAddMore;
    @Bind(R.id.pb_communities_open_about_progress_bar)
    ProgressBar mProgressbar;
    TextView tvLeave;
    TextView tvEdit;
    FeedDetail mFeedDetail;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    String joinTxt = "";
    private PopupWindow popupWindow;
    private CommunityEnum communityEnum = null;
    @Inject
    AppUtils mAppUtils;
   private MoEngageUtills moEngageUtills;
    private Long mcommunityid;
    String mTag;
    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private long startedTime;
    private boolean isSpamPostCount;
    private FeedRequestPojo  feedRequestPojo;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.community_open_about_fragment, container, false);
        ButterKnife.bind(this, view);
        mTvCommunityRequested.setVisibility(View.GONE);
        mOwnerPresenter.attachView(this);
        mMemberpresenter.attachView(this);
        mHomePresenter.attachView(this);
        mMoEHelper = MoEHelper.getInstance(getActivity());
        payloadBuilder = new PayloadBuilder();
        moEngageUtills=MoEngageUtills.getInstance();
        startedTime=System.currentTimeMillis();
        setProgressBar(mProgressbar);
        if (null != getArguments()) {
            communityEnum = (CommunityEnum) getArguments().getSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT);
            mFeedDetail = getArguments().getParcelable(AppConstants.COMMUNITY_DETAIL);
        }
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAboutCommunityOwnerRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunitiesDetailActivity) getActivity());
        mAboutCommunityOwnerRecyclerView.setLayoutManager(mLayoutManager);
        mAboutCommunityOwnerRecyclerView.setAdapter(mAdapter);
        mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.FEED_COMMUNITY, AppConstants.ONE_CONSTANT, mFeedDetail.getIdOfEntityOrParticipant()));
        int adminId=0;
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()&&null != mUserPreference.get().getUserSummary().getUserBO()) {
            adminId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
        }
        if(mFeedDetail.isOwner()||adminId==AppConstants.TWO_CONSTANT)
        {
           liSpamPostUi.setVisibility(View.VISIBLE);
        }else
        {
           liSpamPostUi.setVisibility(View.GONE);
        }
        feedRequestPojo =makeFeedRequest(AppConstants.FEED_COMMUNITY_POST,AppConstants.ONE_CONSTANT);
        feedRequestPojo.setCommunityId(mFeedDetail.getIdOfEntityOrParticipant());
        feedRequestPojo.setSpamPost(true);
        mHomePresenter.getFeedFromPresenter(feedRequestPojo);
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_ABOUT_COMMUNITY_SCREEN));
        return view;
    }
    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        if(isSpamPostCount)
        {
            isSpamPostCount=false;
            if(feedResponsePojo.getNumFound()>0) {
                setTvPostModerationCount(feedResponsePojo.getNumFound());
            }
        }else {
            List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
            if (StringUtil.isNotEmptyCollection(feedDetailList)) {
                isSpamPostCount=true;
                mFeedDetail = feedDetailList.get(0);
                mcommunityid = mFeedDetail.getIdOfEntityOrParticipant();
                mOwnerPresenter.getCommunityOwnerList(ownerRequestBuilder(mcommunityid));
                refreshOpeAboutCommunityContent(mFeedDetail);
                mTvJoinInviteView.setVisibility(View.VISIBLE);
            }
        }
    }
    public void setTvPostModerationCount(int count)
    {
        if(count>0) {
            tvPostModerationCount.setText(String.valueOf(count));
        }else
        {
            tvPostModerationCount.setText(AppConstants.EMPTY_STRING);
        }
    }
    public void refreshOpeAboutCommunityContent(FeedDetail feedDetail) {
        mFeedDetail = feedDetail;
        if (null != mFeedDetail) {
            setAllViewsOfFragment();
            displayTabAsCommunityType(feedDetail);
        }
    }
    private void setAllViewsOfFragment() {
        if (null != mFeedDetail) {
            if (!mFeedDetail.isOwner() && !mFeedDetail.isMember()) {
                mOptionIv.setVisibility(View.GONE);
            } else {
                mOptionIv.setVisibility(View.VISIBLE);
            }
            Glide.with(this)
                    .load(mFeedDetail.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(mIvcommunity_cover_img);
            Glide.with(this).load(mFeedDetail.getThumbnailImageUrl()).transform(new CircleTransform(getActivity())).into(mFbAboutCommunityLogo);
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getListDescription())) {
                mTvAboutCommunityDes.setText(mFeedDetail.getListDescription());
            }
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getNameOrTitle())) {
                mTvCommunityName.setText(mFeedDetail.getNameOrTitle());
            }
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getCommunityType())) {
                mTvCommunityOrganization.setText(mFeedDetail.getCommunityType());
            }
            if (null != mFeedDetail.getTags()) {
                if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getTags().toString()))
                     mTag=mFeedDetail.getTags().toString();
                    mTag=mTag.replaceAll("\\[","").replaceAll("\\]","");
                    mTvCommunityTags.setText(mTag);
            }
            int count = mFeedDetail.getNoOfMembers();
            mTvCommunityRequested.setText(mFeedDetail.getNoOfPendingRequest() + AppConstants.SPACE + getString(R.string.ID_COMMUNITY_REQUESTED));
            mTvCommunityMembers.setText(count + AppConstants.SPACE + getString(R.string.ID_MEMBERS));
            displayTabAsCommunityType(mFeedDetail);
        }
    }
    private void displayTabAsCommunityType(FeedDetail mFeedDetail) {
        if (mFeedDetail.isClosedCommunity()) {
            mTvCommunityOrganization.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_lock, 0);
        } else {
            mTvCommunityOrganization.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if (null != communityEnum) {
            switch (communityEnum) {
                case SEARCH_COMMUNITY:
                    if (!mFeedDetail.isMember() && !mFeedDetail.isOwner() && !mFeedDetail.isRequestPending()) {
                        mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.footer_icon_text));
                        mTvJoinInviteView.setText(getString(R.string.ID_JOIN));
                        mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                    } else if (mFeedDetail.isRequestPending()) {
                        mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        mTvJoinInviteView.setText(getString(R.string.ID_REQUESTED));
                        mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_feed_community_requested);
                        mTvJoinInviteView.setEnabled(false);
                    } else if (mFeedDetail.isOwner() || mFeedDetail.isMember()) {
                        mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        mTvJoinInviteView.setText(getString(R.string.ID_INVITE));
                        mTvJoinInviteView.setText(getActivity().getString(R.string.ID_INVITE));
                        mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_community_invite);
                        mTvJoinInviteView.setVisibility(View.VISIBLE);
                    } else {
                        mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        mTvJoinInviteView.setText(getString(R.string.ID_INVITE));
                        mTvJoinInviteView.setText(getActivity().getString(R.string.ID_INVITE));
                        mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_community_invite);
                        mTvJoinInviteView.setVisibility(View.VISIBLE);
                    }
                    break;
                case FEATURE_COMMUNITY:
                    if (!mFeedDetail.isMember() && !mFeedDetail.isOwner() && !mFeedDetail.isRequestPending()) {
                        mTvJoinInviteView.setText(getActivity().getString(R.string.ID_JOIN));
                        mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.footer_icon_text));
                        mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                    } else if (mFeedDetail.isRequestPending()) {
                        mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        mTvJoinInviteView.setText(getString(R.string.ID_REQUESTED));
                        mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_feed_community_requested);
                        mTvJoinInviteView.setEnabled(false);
                    } else if (mFeedDetail.isOwner() || mFeedDetail.isMember()) {
                        mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        mTvJoinInviteView.setText(getString(R.string.ID_INVITE));
                        mTvJoinInviteView.setText(getActivity().getString(R.string.ID_INVITE));
                        mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_community_invite);
                        mTvJoinInviteView.setVisibility(View.VISIBLE);
                    }
                    break;
                case MY_COMMUNITY:
                    mTvJoinInviteView.setText(getActivity().getString(R.string.ID_INVITE));
                    mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_community_invite);
                    mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + communityEnum);
            }
            if (mFeedDetail.isClosedCommunity()) {
                if (mFeedDetail.isOwner()) {
                    mTvCommunityRequested.setVisibility(View.VISIBLE);
                    tvCommunityAddMore.setVisibility(View.VISIBLE);
                } else if (mFeedDetail.isMember()) {
                    mTvCommunityRequested.setVisibility(View.GONE);
                    tvCommunityAddMore.setVisibility(View.GONE);
                }
            } else {
                if (mFeedDetail.isOwner()) {
                    mTvCommunityRequested.setVisibility(View.GONE);
                    tvCommunityAddMore.setVisibility(View.VISIBLE);
                } else if (mFeedDetail.isMember()) {
                    mTvCommunityRequested.setVisibility(View.GONE);
                    tvCommunityAddMore.setVisibility(View.GONE);
                }
            }
        }
    }

    @OnClick(R.id.tv_about_community_back)
    public void clickBackButton() {
        ((CommunitiesDetailActivity) getActivity()).onBackPressed();
    }

    @OnClick(R.id.tv_about_community_share)
    public void clickCommunityShare() {
        ((CommunitiesDetailActivity) getActivity()).shareClick();
    }

    @OnClick(R.id.tv_community_requested)
    public void requestClick() {
        ((CommunitiesDetailActivity) getActivity()).requestApproveAndRemoveOnClick();
    }

    @OnClick(R.id.tv_community_members)
    public void membersClick() {
        ((CommunitiesDetailActivity) getActivity()).openAllMemberFragmentClick();
    }

    @OnClick(R.id.tv_community_join_invite)
    public void inviteJoinOnClick() {
        joinTxt = mTvJoinInviteView.getText().toString();
        if (StringUtil.isNotNullOrEmptyString(joinTxt)) {
            if (joinTxt.equalsIgnoreCase(getString(R.string.ID_JOIN))) {
                if (mFeedDetail.isClosedCommunity()) {
                    ((CommunitiesDetailActivity) getActivity()).showCommunityJoinReason(mFeedDetail);
                    ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_COMMUNITY_MEMBERSHIP, GoogleAnalyticsEventActions.REQUEST_JOIN_CLOSE_COMMUNITY, AppConstants.EMPTY_STRING);
                } else {
                    callJoinApi();
                    if(mFeedDetail.isRequestPending())
                    {
                        ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_COMMUNITY_MEMBERSHIP, GoogleAnalyticsEventActions.UNDO_REQUEST_JOIN_CLOSE_COMMUNITY, AppConstants.EMPTY_STRING);
                    }else
                    {
                        ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_COMMUNITY_MEMBERSHIP, GoogleAnalyticsEventActions.REQUEST_JOIN_OPEN_COMMUNITY, AppConstants.EMPTY_STRING);
                    }
                }
            } else if (joinTxt.equalsIgnoreCase(getString(R.string.ID_INVITE))) {
                ((CommunitiesDetailActivity) getActivity()).inviteJoinEventClick(joinTxt, mFeedDetail);
            }
        }
    }

    @OnClick(R.id.tv_community_add_more)
    public void addMoreClick() {
        ((CommunitiesDetailActivity) getActivity()).addOwnerOnClick();
    }

    @OnClick(R.id.iv_about_community)
    void onOptionClick() {
        clickMenuItem(mOptionIv);
    }

    private void callJoinApi() {
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            List<Long> userIdList = new ArrayList();
            userIdList.add((long) mUserPreference.get().getUserSummary().getUserId());
            mHomePresenter.communityJoinFromPresenter(communityRequestBuilder(userIdList, mFeedDetail.getIdOfEntityOrParticipant(), ""));
        }
    }

    public void callRemoveOwner(FeedDetail feedDetail) {
        mFeedDetail = feedDetail;
        mTvCommunityMembers.setText(mFeedDetail.getNoOfMembers() + AppConstants.SPACE + getString(R.string.ID_MEMBERS));
        mOwnerPresenter.getCommunityOwnerList(ownerRequestBuilder(mcommunityid));

    }

    private void clickMenuItem(View view) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.menu_option_layout, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
            }
        });
        tvEdit = (TextView) popupView.findViewById(R.id.tv_article_menu_edit);
        tvLeave = (TextView) popupView.findViewById(R.id.tv_article_menu_delete);
        popupWindow.showAsDropDown(view, -100,0);
        tvLeave.setText(getActivity().getString(R.string.ID_LEAVE));
        if (mFeedDetail.isOwner()) {
            if (StringUtil.isNotEmptyCollection(mOwnerListResponse) && mOwnerListResponse.size() > AppConstants.ONE_CONSTANT) {
                tvLeave.setVisibility(View.VISIBLE);
            } else {
                tvLeave.setVisibility(View.GONE);
            }
            tvEdit.setVisibility(View.VISIBLE);
        } else if (mFeedDetail.isMember()) {
            tvLeave.setVisibility(View.VISIBLE);
            tvEdit.setVisibility(View.GONE);
        } else {
            tvLeave.setVisibility(View.GONE);
            tvEdit.setVisibility(View.GONE);
        }

        popupWindow.showAsDropDown(view, -180, 0);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        tvLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mShareCommunityIntractionListner.
                callRemoveMember();
                ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_COMMUNITY_MEMBERSHIP, GoogleAnalyticsEventActions.LEAVE_COMMUNITY, AppConstants.EMPTY_STRING);
                popupWindow.dismiss();
            }
        });


    }

    public void callRemoveMember() {
        LoginResponse loginResponse = mUserPreference.get();
        UserSummary userSummary = loginResponse.getUserSummary();
        RemoveMemberRequest removeMemberRequest = removeMemberRequestBuilder(mcommunityid, userSummary.getUserId());
        mMemberpresenter.leaveCommunityAndRemoveMemberToPresenter(removeMemberRequest);
    }

    public void setStatusOfButton(FeedDetail feedDetail) {
        if (feedDetail.isRequestPending()) {
            mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            mTvJoinInviteView.setText(getString(R.string.ID_REQUESTED));
            mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_feed_community_requested);
            mTvJoinInviteView.setEnabled(false);
        } else if (feedDetail.isOwner() || feedDetail.isMember()) {
            mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            mTvJoinInviteView.setText(getString(R.string.ID_INVITE));
            mTvJoinInviteView.setText(getActivity().getString(R.string.ID_INVITE));
            mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_community_invite);
            mTvJoinInviteView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                mTvJoinInviteView.setText(getString(R.string.ID_JOINED));
                mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
                mTvJoinInviteView.setVisibility(View.VISIBLE);
                mOptionIv.setVisibility(View.VISIBLE);
                mFeedDetail.setMember(true);
                ((CommunitiesDetailActivity)getActivity()).updateFeedDetailWithCommunityStatus(mFeedDetail);
                break;
            case AppConstants.FAILED:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_JOIN_INVITE);
                break;
            default:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), ERROR_JOIN_INVITE);

        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (null != view) {
            String item = (String) parent.getItemAtPosition(position);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != view) {
            String item = (String) parent.getItemAtPosition(position);
        }
    }

    @Override
    public void getSelectedCommunityListSuccess(List<CommunityPostResponse> selected_community_response) {

    }

    @Override
    public void getOwnerListSuccess(OwnerListResponse ownerResponse) {
        List<Member> ownerList = ownerResponse.getMembers();
        if (StringUtil.isNotEmptyCollection(ownerList)) {
            this.mOwnerListResponse = ownerList;
            for (Member member : ownerList) {
                if (ownerList.size() > AppConstants.ONE_CONSTANT && mFeedDetail.isOwner()) {
                    member.setOwner(true);
                } else {
                    member.setOwner(false);
                }
            }
            mAdapter.setSheroesGenericListData(ownerList);
            mAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void getAllMembers(List<MembersList> data) {

    }

    @Override
    public void removeMember(MemberListResponse memberListResponse) {

        switch (memberListResponse.getStatus()) {
            case AppConstants.SUCCESS:
                mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.footer_icon_text));
                mTvJoinInviteView.setText(getString(R.string.ID_JOIN));
                mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                ((CommunitiesDetailActivity) getActivity()).onLeaveClick(CommunityEnum.FEATURE_COMMUNITY);
                if(null!=mFeedDetail)
                {
                    moEngageUtills.entityMoEngageLeaveCommunity(getActivity(),mMoEHelper,payloadBuilder,mFeedDetail.getNameOrTitle(), mFeedDetail.getIdOfEntityOrParticipant(), mFeedDetail.isClosedCommunity(), MoEngageConstants.COMMUNITY_TAG, mFeedDetail.isMember());
                    HashMap<String, Object> properties = new EventProperty.Builder().id(Long.toString(mFeedDetail.getIdOfEntityOrParticipant())).name(mFeedDetail.getNameOrTitle()).build();
                    AnalyticsManager.trackEvent(Event.COMMUNITY_LEFT, properties);
                }
                break;
            case AppConstants.FAILED:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(memberListResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_JOIN_INVITE);
                break;
            default:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), ERROR_JOIN_INVITE);
        }
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }


    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        long timeSpent=System.currentTimeMillis()-startedTime;
        moEngageUtills.entityMoEngageAboutCommunity(getActivity(),mMoEHelper,payloadBuilder,mFeedDetail.getIdOfEntityOrParticipant(),timeSpent);
    }

    @OnClick(R.id.tv_post_in_moderation)
    public void tvPostModerationClick() {
        ((CommunitiesDetailActivity)getActivity()).spamPostListFragment(feedRequestPojo);
    }

    @Override
    protected Map<String, Object> getExtraProperties() {
        HashMap<String, Object> properties = new
                EventProperty.Builder()
                .id(Long.toString(mFeedDetail.getIdOfEntityOrParticipant()))
                .build();
        return properties;
    }

}
