package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.Map;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.community.Member;
import appliedlife.pvtltd.SHEROES.models.entities.community.MembersList;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerList;
import appliedlife.pvtltd.SHEROES.models.entities.community.PandingMember;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustomeCollapsableToolBar.CustomCollapsingToolbarLayout;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesDetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityOpenAboutFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.InviteCommunityOwner;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareCommunityFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.AllMembersDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.CommunityRequestedDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.CurrentStatusDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.OwnerRemoveDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.SpamPostListDialogFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ACTIVITY_FOR_REFRESH_FRAGMENT_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_COMMENT_ON_CARD_MENU;


public class CommunitiesDetailActivity extends BaseActivity implements  AppBarLayout.OnOffsetChangedListener {
    private static final String SCREEN_LABEL = "Community Screen Activity";
    private final String TAG = LogUtils.makeLogTag(CommunitiesDetailActivity.class);
    @Bind(R.id.app_bar_coomunities_detail)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.iv_communities_detail)
    ImageView ivCommunitiesDetail;
    @Bind(R.id.view_pager_communities_detail)
    ViewPager mViewPager;
    @Bind(R.id.toolbar_communities_detail)
    Toolbar mToolbarCommunitiesDetail;
    @Bind(R.id.collapsing_toolbar_communities_detail)
    CustomCollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.tv_member)
    TextView mTvMemebr;
    @Bind(R.id.tv_communities_time)
    TextView mTvCommunityTime;
    @Bind(R.id.community_detail_activity)
    CoordinatorLayout mCommunityDetailActivity;
    private CommunityFeedSolrObj mCommunityFeedObj;
    private FeedDetail mMyCommunityPostFeedDetail;
    private FragmentOpen mFragmentOpen;
    ViewPagerAdapter mViewPagerAdapter;
    private Fragment mFragment;
    private CommunityOpenAboutFragment mCommunityOpenAboutFragment;
    private CommunityEnum communityEnum = null;
    private AllMembersDialogFragment mAllMembersDialogFragment;
    private boolean isMemberRemoveDialog;
    private CommunityRequestedDialogFragment communityRequestedDialogFragment;
    boolean isCommunityDetailFragment;
    private long mCommunityId;
    private long mCommunityPostId = 0;
    @Bind(R.id.tv_community_detail_title)
    TextView mTvCommunityDetailTitle;
    @Bind(R.id.tv_community_detail_subtitle)
    TextView mTvCommunityDetailSubTitle;
    @Bind(R.id.li_header)
    public LinearLayout mLiHeader;
    @Bind(R.id.fab_post_community)
    public FloatingActionButton ivFabPostCommunity;
    private MoEHelper mMoEHelper;
    private MoEngageUtills moEngageUtills;
    private PayloadBuilder payloadBuilder;
    private long startedTime;
    private ProgressDialog mProgressDialog;
    private long communityId;
    private long idOFEntityParticipant;
    private boolean isFromFeedPost;
    private SpamPostListDialogFragment spamPostDialogFragment;
    private int mFromNotification;
    private CommunityFeedSolrObj communityFeedObjForHomeFeed;
    private CommunitiesDetailFragment mCommunitiesDetailFragment;
    private boolean isFromDeepLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_communities_detail);
        ButterKnife.bind(this);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        startedTime = System.currentTimeMillis();
        mFragmentOpen = new FragmentOpen();
        setAllValues(mFragmentOpen);
        initActivityTransitions();
        mAppBarLayout.addOnOffsetChangedListener(this);
        if (null != getIntent() && null != getIntent().getExtras()) {
            mFromNotification = getIntent().getExtras().getInt(AppConstants.BELL_NOTIFICATION);
            mCommunityId = getIntent().getExtras().getLong(AppConstants.COMMUNITY_ID);
            mCommunityPostId = getIntent().getExtras().getLong(AppConstants.COMMUNITY_POST_ID);
            isFromDeepLink = getIntent().getExtras().getBoolean(AppConstants.FROM_DEEPLINK, false);
            if(isFromDeepLink){
                if (mCommunityId >= 0) {
                    mCommunityFeedObj = new CommunityFeedSolrObj();
                    mCommunityFeedObj.setIdOfEntityOrParticipant(mCommunityId);
                }
            }else {
                if (mCommunityId > 0) {
                    mCommunityFeedObj = new CommunityFeedSolrObj();
                    mCommunityFeedObj.setIdOfEntityOrParticipant(mCommunityId);
                } else {
                    mCommunityFeedObj = Parcels.unwrap(getIntent().getParcelableExtra(AppConstants.COMMUNITY_DETAIL));
                    communityEnum = (CommunityEnum) getIntent().getSerializableExtra(AppConstants.MY_COMMUNITIES_FRAGMENT);
                    isFromFeedPost = getIntent().getBooleanExtra(AppConstants.COMMUNITY_POST_ID, false);
                }
            }

        }
        if (mCommunityId == 0) {
            ivFabPostCommunity.setVisibility(View.GONE);
        }
        setPagerAndLayouts();
            if (null != mCommunityFeedObj) {
                communityFeedObjForHomeFeed = mCommunityFeedObj;
            }
        if (null != mCommunityFeedObj && StringUtil.isNotNullOrEmptyString(mCommunityFeedObj.getNameOrTitle())) {
        }
        ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_VIEW_COMMUNITY));
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
            mCommunitiesDetailFragment.likeAndUnlikeRequest(baseResponse, reactionValue, position);
    }

    private void setPagerAndLayouts() {
        ViewCompat.setTransitionName(mAppBarLayout, AppConstants.COMMUNITY_DETAIL);
        supportPostponeEnterTransition();
        setSupportActionBar(mToolbarCommunitiesDetail);
        if (null != mCommunityFeedObj && null != communityEnum) {
            idOFEntityParticipant = mCommunityFeedObj.getIdOfEntityOrParticipant();
            // TODO : ujjwal
            // /communityId = (CommunityFeedSolrObj)mCommunityFeedObj.getCommunityId();
            if (isFromFeedPost) {
                mCommunityFeedObj.setIdOfEntityOrParticipant(communityId);
                // TODO : ujjwal
                //mCommunityFeedObj.setCommunityId(idOFEntityParticipant);
            }
            if (mCommunityFeedObj.isClosedCommunity() && !(mCommunityFeedObj).isOwner()) {
                isCommunityDetailFragment = true;
                mCommunityDetailActivity.setVisibility(View.GONE);
                communityOpenAboutFragment(mCommunityFeedObj);
            } else {
                mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                mCommunitiesDetailFragment = CommunitiesDetailFragment.createInstance(mCommunityFeedObj, communityEnum, mCommunityPostId);
                mViewPagerAdapter.addFragment(mCommunitiesDetailFragment, getString(R.string.ID_COMMUNITIES));
                mViewPager.setAdapter(mViewPagerAdapter);
            }
        } else {
            mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            mCommunitiesDetailFragment = CommunitiesDetailFragment.createInstance(mCommunityFeedObj, communityEnum, mCommunityPostId);
            mViewPagerAdapter.addFragment(mCommunitiesDetailFragment, getString(R.string.ID_COMMUNITIES));
            mViewPager.setAdapter(mViewPagerAdapter);
        }
    }

    public void initializeUiContent(FeedDetail feedDetail) {
        CommunityFeedSolrObj communityFeedSolrObj = (CommunityFeedSolrObj) feedDetail;
        mCommunityFeedObj = communityFeedSolrObj;
        mCommunityDetailActivity.setVisibility(View.VISIBLE);
        mTvMemebr.setText(communityFeedSolrObj.getNoOfMembers() + AppConstants.SPACE + getString(R.string.ID_MEMBERS));
        if (communityFeedSolrObj.getNoOfMembers() > 1) {
            mTvMemebr.setVisibility(View.VISIBLE);
            mTvMemebr.setText(mCommunityFeedObj.getNoOfMembers() + AppConstants.SPACE + getString(R.string.ID_MEMBERS));
        } else if (communityFeedSolrObj.getNoOfViews() == 1) {
            mTvMemebr.setVisibility(View.VISIBLE);
            mTvMemebr.setText(mCommunityFeedObj.getNoOfMembers() + AppConstants.SPACE + getString(R.string.ID_MEMBER));
        } else {
            mTvMemebr.setVisibility(View.INVISIBLE);
        }
        mCollapsingToolbarLayout.setTitle(AppConstants.SPACE);
        mCollapsingToolbarLayout.setSubtitle(AppConstants.SPACE);
        mTvCommunityDetailTitle.setText(communityFeedSolrObj.getNameOrTitle());
        mTvCommunityDetailSubTitle.setText(communityFeedSolrObj.getCommunityType());
        if (StringUtil.isNotNullOrEmptyString(communityFeedSolrObj.getImageUrl())) {
            Glide.with(this)
                    .load(communityFeedSolrObj.getImageUrl()).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            ivCommunitiesDetail.setImageBitmap(resource);
                            Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    supportStartPostponedEnterTransition();
                                }
                            });
                        }
                    });
        } else {
            ivCommunitiesDetail.setImageDrawable(getResources().getDrawable(R.drawable.ic_reaction_strip_background));
            supportStartPostponedEnterTransition();
        }
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    @Override
    public void startActivityFromHolder(Intent intent) {

    }


    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        int id = view.getId();
        if (baseResponse instanceof FeedDetail) {
            communityDetailHandled(view, baseResponse);
        } else if (baseResponse instanceof OwnerList) {
            showOwnerRemoveDialog(true, baseResponse);
        } else if (baseResponse instanceof Member) {
            switch (id) {
                case R.id.tv_owner_cross_from_open_about:
                    showOwnerRemoveDialog(false, baseResponse);
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
            }
        } else if (baseResponse instanceof MembersList) {
            MembersList membersList = (MembersList) baseResponse;
            switch (view.getId()) {
                case R.id.tv_member_cross:
                    CallCommunityMemberRemoveFragment(membersList.getUsersId(), membersList.getCommunityId(), membersList.getPosition());
                    break;
            }
        } else if (baseResponse instanceof PandingMember) {
            PandingMember pandingMember = (PandingMember) baseResponse;
            switch (id) {
                case R.id.tv_panding_member_cross:
                    callCommunityPendingMemberRemoveFragment(pandingMember.getUsersId(), pandingMember.getCommunityId(), pandingMember.getPosition());
                    break;
                case R.id.tv_panding_member_check:
                    callCommunityAproveMemberFragment(pandingMember.getUsersId(), pandingMember.getCommunityId(), pandingMember.getPosition());
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
            }

        } else if (baseResponse instanceof Comment) {
            setAllValues(mFragmentOpen);
             /* Comment mCurrentStatusDialog list  comment menu option edit,delete */
            super.clickMenuItem(view, baseResponse, USER_COMMENT_ON_CARD_MENU);
        }
    }

    public DialogFragment showOwnerRemoveDialog(boolean isOwner, BaseResponse baseResponse) {
        OwnerRemoveDialog ownerRemoveDialog = (OwnerRemoveDialog) getFragmentManager().findFragmentByTag(OwnerRemoveDialog.class.getName());
        if (ownerRemoveDialog == null) {
            ownerRemoveDialog = new OwnerRemoveDialog();
            Bundle bundleCommunity = new Bundle();
            if (isOwner) {
                isMemberRemoveDialog = isOwner;
                OwnerList ownerList = (OwnerList) baseResponse;
                Parcelable parcelable = Parcels.wrap(ownerList);
                bundleCommunity.putParcelable(AppConstants.OWNER_SUB_TYPE, parcelable);
            } else {
                isMemberRemoveDialog = isOwner;
                Member member = (Member) baseResponse;
                Parcelable parcelable = Parcels.wrap(member);
                bundleCommunity.putParcelable(AppConstants.MEMBER, parcelable);
                bundleCommunity.putLong(AppConstants.COMMUNITY_DETAIL, mCommunityFeedObj.getIdOfEntityOrParticipant());
            }
            bundleCommunity.putBoolean(AppConstants.COMMUNITY_POST_FRAGMENT, isOwner);
            ownerRemoveDialog.setArguments(bundleCommunity);
        }
        if (!ownerRemoveDialog.isVisible() && !ownerRemoveDialog.isAdded() && !isFinishing() && !mIsDestroyed) {
            ownerRemoveDialog.show(getFragmentManager(), CurrentStatusDialog.class.getName());
        }
        return ownerRemoveDialog;
    }

    //private void communityDetailHandled(View view, ) {
    private void communityDetailHandled(View view, BaseResponse baseResponse) {
        FeedDetail feedDetail = (FeedDetail) baseResponse;
        mMyCommunityPostFeedDetail = feedDetail;
        int id = view.getId();
        mFragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
        setFragment(mFragment);
        mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.THREE_CONSTANT);
        mFragmentOpen.setOwner(((CommunityFeedSolrObj)mCommunityFeedObj).isOwner());
        setAllValues(mFragmentOpen);
        super.feedCardsHandled(view, baseResponse);
        switch (id) {
            case R.id.tv_join_view_holder:
                inviteJoinEventClick(getString(R.string.ID_JOIN), feedDetail);
                break;
            case R.id.card_community_detail:
                communityOpenAboutFragment(feedDetail);
                break;
            case R.id.tv_owner_add:
                CallCommunityOwnerSearchFragment(feedDetail, feedDetail.getIdOfEntityOrParticipant());
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
                break;
            case R.id.tv_approve_spam_post:
                 communityFeedObjForHomeFeed.setViewed(true);
                if (null != spamPostDialogFragment) {
                    spamPostDialogFragment.approveSpamPost(feedDetail, true, false, true);
                } else {
                    Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        ((CommunitiesDetailFragment) fragment).approveSpamPost(feedDetail, true, false, true);
                    }
                }
                break;

            case R.id.tv_delete_spam_post:
                communityFeedObjForHomeFeed.setViewed(true);
                if (null != spamPostDialogFragment) {
                    spamPostDialogFragment.approveSpamPost(feedDetail, true, true, false);
                } else {
                    Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        ((CommunitiesDetailFragment) fragment).approveSpamPost(feedDetail, true, true, false);
                    }
                }
                break;

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
        }

    }

    public void communityOpenAboutFragment(FeedDetail feedDetail) {
        mFragmentOpen.setOpenAboutFragment(true);
        if (null != feedDetail) {
            mCommunityOpenAboutFragment = new CommunityOpenAboutFragment();
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(feedDetail);
            bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, parcelable);
            bundle.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, communityEnum);
            mCommunityOpenAboutFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.about_community_container, mCommunityOpenAboutFragment, CommunityOpenAboutFragment.class.getName()).addToBackStack(CommunityOpenAboutFragment.class.getName()).commitAllowingStateLoss();
        }
    }

    @Override
    public void championProfile(BaseResponse baseResponse, int championValue) {
        if (baseResponse instanceof FeedDetail) {
            FeedDetail feedDetail = (FeedDetail) baseResponse;
            championDetailActivity(feedDetail.getCreatedBy(), feedDetail.getItemPosition(), feedDetail.isAuthorMentor());
        } else if (baseResponse instanceof Comment) {
            Comment comment = (Comment) baseResponse;
            if(!comment.isAnonymous()) {
                Log.i("Profile: is verified", ":" + comment.isVerifiedMentor());
                championDetailActivity(comment.getParticipantId(), comment.getItemPosition(), comment.isVerifiedMentor());
            }
        }
    }

    private void championDetailActivity(Long userId, int position, boolean isMentor) {
        Intent intent = new Intent(this, MentorUserProfileActvity.class);
        Bundle bundle = new Bundle();
        mCommunityFeedObj = new CommunityFeedSolrObj();
        mCommunityFeedObj.setIdOfEntityOrParticipant(userId);
        mCommunityFeedObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
        mCommunityFeedObj.setItemPosition(position);
        Parcelable parcelable = Parcels.wrap(mCommunityFeedObj);
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, parcelable);
        bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, null);
        intent.putExtra(AppConstants.CHAMPION_ID,userId);
        intent.putExtra(AppConstants.IS_MENTOR_ID, isMentor);//todo - check if mentor
        intent.putExtras(bundle);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    public void updateOpenAboutFragment(FeedDetail feedDetail) {
        CommunityFeedSolrObj communityFeedSolrObj = (CommunityFeedSolrObj) feedDetail;
            mCommunityFeedObj = communityFeedSolrObj;

        if (null != mAllMembersDialogFragment) {
            mAllMembersDialogFragment.dismiss();
        }
        if (null != communityRequestedDialogFragment) {
            communityRequestedDialogFragment.dismiss();
        }
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(CommunityOpenAboutFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((CommunityOpenAboutFragment) fragment).refreshOpeAboutCommunityContent(mCommunityFeedObj);
        }
    }

    private void CallCommunityOwnerSearchFragment(FeedDetail feedDetail, Long userId) {
        final Fragment fragmentCommunityOwnerSearch = getSupportFragmentManager().findFragmentByTag(InviteCommunityOwner.class.getName());
        if (AppUtils.isFragmentUIActive(fragmentCommunityOwnerSearch)) {
            ((InviteCommunityOwner) fragmentCommunityOwnerSearch).callAddOwner(feedDetail, userId);
        }

    }

    private void CallCommunityMemberRemoveFragment(Long userId, Long communityId, int position) {
        if (null != mAllMembersDialogFragment) {
            mAllMembersDialogFragment.callRemoveMember(userId, communityId, position);
        }
    }

    private void callCommunityPendingMemberRemoveFragment(long userId, long communityId, int position) {

        if (null != communityRequestedDialogFragment) {
            communityRequestedDialogFragment.removePandingRequest(userId, communityId, position);
        }

    }

    private void callCommunityAproveMemberFragment(long userId, long communityId, int position) {
        if (null != communityRequestedDialogFragment) {
            communityRequestedDialogFragment.approvePandingRequest(userId, communityId, position);
        }
    }


    public DialogFragment openAllMemberFragmentClick() {
        mAllMembersDialogFragment = (AllMembersDialogFragment) getFragmentManager().findFragmentByTag(AllMembersDialogFragment.class.getName());
        if (mAllMembersDialogFragment == null) {
            mAllMembersDialogFragment = new AllMembersDialogFragment();
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(mCommunityFeedObj);
            bundle.putParcelable(AppConstants.COMMUNITIES_DETAIL, parcelable);
            mAllMembersDialogFragment.setArguments(bundle);
        }
        if (!mAllMembersDialogFragment.isVisible() && !mAllMembersDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            mAllMembersDialogFragment.show(getFragmentManager(), AllMembersDialogFragment.class.getName());
        }
        return mAllMembersDialogFragment;
    }

    public void inviteJoinEventClick(String pressedEventName, FeedDetail feedDetail) {
        if (pressedEventName.equalsIgnoreCase(getString(R.string.ID_JOIN))) {
        } else if (pressedEventName.equalsIgnoreCase(getString(R.string.ID_INVITE))) {
            inviteCommunityMemberDialog();
            HashMap<String, Object> properties = new EventProperty.Builder().id(Long.toString(feedDetail.getEntityOrParticipantId())).name(feedDetail.getNameOrTitle()).build();
            AnalyticsManager.trackEvent(Event.COMMUNITY_INVITE, getScreenName(), properties);
        }
    }

    public void inviteCommunityMemberDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.ID_INVITE_WOMEN_FRIEND));
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        LoginManager.getInstance().logOut();
        String appLinkUrl, previewImageUrl;
        appLinkUrl = AppConstants.FB_APP_LINK_URL;
        previewImageUrl = AppConstants.FB_APP_LINK_URL_PREVIEW_IMAGE;
        if (AppInviteDialog.canShow()) {
            AppEventsLogger logger = AppEventsLogger.newLogger(this);
            logger.logEvent(AppEventsConstants.EVENT_NAME_VIEWED_CONTENT);
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .build();
            AppInviteDialog.show(this, content);
        }
        ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_INVITES, GoogleAnalyticsEventActions.OPEN_INVITE_FB_FRDZ, AppConstants.EMPTY_STRING);
    }

    public void addOwnerOnClick() {
        mFragmentOpen.setOpenAboutFragment(false);
        mFragmentOpen.setInviteCommunityOwner(true);
        if (null != mCommunityFeedObj) {
            InviteCommunityOwner myCommunityInviteMemberFragment = new InviteCommunityOwner();
            Bundle bundleInvite = new Bundle();
            Parcelable parcelable = Parcels.wrap(mCommunityFeedObj);
            bundleInvite.putParcelable(AppConstants.COMMUNITIES_DETAIL, parcelable);
            myCommunityInviteMemberFragment.setArguments(bundleInvite);
            getSupportFragmentManager().beginTransaction().replace(R.id.about_community_container, myCommunityInviteMemberFragment, InviteCommunityOwner.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        }
    }

    public void onJoinDialogSuccessResult(String status, FeedDetail feedDetail) {
        if (StringUtil.isNotNullOrEmptyString(status)) {
            if (AppConstants.SUCCESS.equalsIgnoreCase(status)) {
                Fragment fragmentCommunityDetail = getSupportFragmentManager().findFragmentByTag(CommunityOpenAboutFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragmentCommunityDetail)) {
                    ((CommunityOpenAboutFragment) fragmentCommunityDetail).setStatusOfButton(feedDetail);
                }
            } else {
                onShowErrorDialog(status, JOIN_INVITE);
            }
        } else {
            onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), JOIN_INVITE);
        }
    }

    public DialogFragment requestApproveAndRemoveOnClick() {
        communityRequestedDialogFragment = (CommunityRequestedDialogFragment) getFragmentManager().findFragmentByTag(CommunityRequestedDialogFragment.class.getName());
        if (communityRequestedDialogFragment == null) {
            communityRequestedDialogFragment = new CommunityRequestedDialogFragment();
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(mCommunityFeedObj);
            bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, parcelable);
            communityRequestedDialogFragment.setArguments(bundle);
        }
        if (!communityRequestedDialogFragment.isVisible() && !communityRequestedDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            communityRequestedDialogFragment.show(getFragmentManager(), CommunityRequestedDialogFragment.class.getName());
        }
        return communityRequestedDialogFragment;
    }

    public void createCommunityPostClick(FeedDetail feedDetail) {
        CommunityPost communityPost = new CommunityPost();
        communityPost.community = new Community();
        communityPost.community.id = feedDetail.getIdOfEntityOrParticipant();
        communityPost.community.name = feedDetail.getNameOrTitle();
        // TODO : ujjwal
        // communityPost.community.isOwner = communityFeedSolrObj.isCommunityOwner();
        // TODO : ujjwal
        // communityPost.community.thumbImageUrl = feedDetail.getSolrIgnorePostCommunityLogo();
        CommunityPostActivity.navigateTo(this, communityPost, AppConstants.REQUEST_CODE_FOR_CREATE_COMMUNITY_POST, true);
    }

    @OnClick(R.id.tv_communities_detail_share)
    public void shareClick() {
        ShareCommunityFragment shareCommunityFragment = new ShareCommunityFragment();
        Bundle bundle = new Bundle();
        Parcelable parcelable = Parcels.wrap(mCommunityFeedObj);
        bundle.putParcelable(AppConstants.SHARE, parcelable);
        shareCommunityFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.about_community_container, shareCommunityFragment).addToBackStack(null).commitAllowingStateLoss();
    }


    public void onOwnerClose() {
        final Fragment fragmentCommunityOwnerSearch = getSupportFragmentManager().findFragmentByTag(CommunityOpenAboutFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragmentCommunityOwnerSearch)) {
            if (!isMemberRemoveDialog) {
            }
            ((CommunityOpenAboutFragment) fragmentCommunityOwnerSearch).callRemoveOwner(mCommunityFeedObj);
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragmentOpen.isCommentList()) {
            getSupportFragmentManager().popBackStackImmediate();
            if (AppUtils.isFragmentUIActive(mFragment)) {
                ((CommunitiesDetailFragment) mFragment).commentListRefresh(mMyCommunityPostFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
            }
            mFragmentOpen.setCommentList(false);
        } else if (mFragmentOpen.isReactionList()) {
            getSupportFragmentManager().popBackStack();
            mFragmentOpen.setReactionList(false);
        } else if (mFragmentOpen.isOpenAboutFragment()) {
            if (isCommunityDetailFragment) {
                onBackClick();
            } else {
                getSupportFragmentManager().popBackStackImmediate();
                Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                if (AppUtils.isFragmentUIActive(fragment)) {
                    if (fragment instanceof CommunitiesDetailFragment) {
                        ((CommunitiesDetailFragment) fragment).updateUiAccordingToFeedDetail(mCommunityFeedObj);
                    }
                }
                mCommunityDetailActivity.setVisibility(View.VISIBLE);
                mFragmentOpen.setOpenAboutFragment(false);
            }
            if (null != spamPostDialogFragment) {
                spamPostDialogFragment = null;
            }
        } else if (mFragmentOpen.isInviteCommunityOwner()) {
            mFragmentOpen.setInviteCommunityOwner(false);
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(InviteCommunityOwner.class.getName());
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((InviteCommunityOwner) fragment).inviteSubmit();
            }
        } else if (mFragmentOpen.isOpenImageViewer()) {
            mFragmentOpen.setOpenImageViewer(false);
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            onBackClick();
        }
    }

    public void closeOwner() {
        getSupportFragmentManager().popBackStack();
    }

    public void updateFeedDetailWithCommunityStatus(FeedDetail feedDetail) {
        CommunityFeedSolrObj communityFeedSolrObj = (CommunityFeedSolrObj) feedDetail;
        mCommunityFeedObj = communityFeedSolrObj;

    }

    public void onLeaveClick(CommunityEnum communityEnum) {
        this.communityEnum = communityEnum;
        onBackClick();
    }

    @OnClick(R.id.iv_community_detail_back)
    public void onBackClick() {
        if (mCommunityId > 0 || mCommunityPostId > 0) {
            if (mFromNotification == AppConstants.NO_REACTION_CONSTANT) {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            } else {
                deepLinkBackPressHandle();
            }
        } else {
            deepLinkBackPressHandle();
        }
        finish();
    }

    private void deepLinkBackPressHandle() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        if (isFromFeedPost) {
            // TODO : ujjwal
            //communityFeedObjForHomeFeed.setCommunityId(communityId);
            communityFeedObjForHomeFeed.setIdOfEntityOrParticipant(idOFEntityParticipant);
            Parcelable parcelable = Parcels.wrap(communityFeedObjForHomeFeed);
            bundle.putParcelable(AppConstants.COMMUNITIES_DETAIL, parcelable);
        } else {
            Parcelable parcelable = Parcels.wrap(mCommunityFeedObj);
            bundle.putParcelable(AppConstants.COMMUNITIES_DETAIL, parcelable);
        }
        bundle.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, communityEnum);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
    }

    @Override
    public void onShowErrorDialog(String errorReason, FeedParticipationEnum feedParticipationEnum) {
        if (StringUtil.isNotNullOrEmptyString(errorReason)) {
            switch (errorReason) {
                case AppConstants.CHECK_NETWORK_CONNECTION:
                    showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                    break;
                default:
                    if (AppConstants.BAD_RQUEST.contains(errorReason)) {
                        showNetworkTimeoutDoalog(true, false, getString(R.string.ID_BAD_RQUEST));
                    } else if (AppConstants.HTTP_401_UNAUTHORIZED.contains(errorReason)) {
                        showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_INVALID_USER_PASSWORD));
                    } else {
                        showNetworkTimeoutDoalog(true, false, errorReason);
                    }
            }
        } else {
            showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
         /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        if (null != intent) {
            switch (requestCode) {
                case AppConstants.REQUEST_CODE_FOR_CREATE_COMMUNITY:
                    CommunityFeedSolrObj communityFeedSolrObj = (CommunityFeedSolrObj) intent.getExtras().get(AppConstants.COMMUNITIES_DETAIL);
                    updateOpenAboutFragment(communityFeedSolrObj);
                    break;
                case AppConstants.REQUEST_CODE_FOR_CREATE_COMMUNITY_POST:
                   // FeedDetail feedCommunityPost = (FeedDetail) intent.getExtras().get(AppConstants.COMMUNITY_POST_FRAGMENT);
                    Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        if (fragment instanceof CommunitiesDetailFragment) {
                            ((CommunitiesDetailFragment) fragment).updateUiAccordingToFeedDetail(mCommunityFeedObj);
                        }
                    }
                    break;
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                    FeedDetail feedCommunityPostEdit = (FeedDetail) Parcels.unwrap(intent.getParcelableExtra(AppConstants.COMMUNITY_POST_FRAGMENT));
                    Fragment fragmentCommunity = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                    if (AppUtils.isFragmentUIActive(fragmentCommunity)) {
                        if (fragmentCommunity instanceof CommunitiesDetailFragment) {
                            ((CommunitiesDetailFragment) fragmentCommunity).updateUiAccordingToFeedDetail(mCommunityFeedObj);
                        }
                    }
                    break;
                case AppConstants.REQUEST_CODE_FOR_POST_DETAIL:
                    boolean isPostDeleted = false;
                    Fragment fragmentCommunityDetail = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                    if (AppUtils.isFragmentUIActive(fragmentCommunityDetail)) {
                        if (fragmentCommunityDetail instanceof CommunitiesDetailFragment) {
                            Parcelable parcelable = intent.getParcelableExtra(UserPostSolrObj.USER_POST_OBJ);
                            UserPostSolrObj userPostSolrObj = null;
                            if (parcelable != null) {
                                userPostSolrObj = Parcels.unwrap(parcelable);
                                isPostDeleted = intent.getBooleanExtra(PostDetailActivity.IS_POST_DELETED, false);
                            }
                            if(isPostDeleted){
                                ((CommunitiesDetailFragment) fragmentCommunityDetail).commentListRefresh(userPostSolrObj, FeedParticipationEnum.DELETE_COMMUNITY_POST);
                            }else {
                                ((CommunitiesDetailFragment) fragmentCommunityDetail).commentListRefresh(userPostSolrObj, FeedParticipationEnum.COMMENT_REACTION);
                            }
                        }
                    }
                default:
                    if (null != mProgressDialog) {
                        mProgressDialog.dismiss();
                    }
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + requestCode);
            }
        }

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset >= AppConstants.NO_REACTION_CONSTANT) {
            mCollapsingToolbarLayout.setTitle(AppConstants.SPACE);
            mCollapsingToolbarLayout.setSubtitle(AppConstants.SPACE);
            mLiHeader.setVisibility(View.INVISIBLE);
        } else {
            mLiHeader.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {
        mFragmentOpen.setOpenImageViewer(true);
        setAllValues(mFragmentOpen);
        super.dataOperationOnClick(baseResponse);
    }

    @OnClick(R.id.fab_post_community)
    public void communityPostClick() {
        mFragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
        if (AppUtils.isFragmentUIActive(mFragment)) {
            if (mFragment instanceof CommunitiesDetailFragment) {
                ((CommunitiesDetailFragment) mFragment).communityPostClick();
            }
        }
    }

    public DialogFragment spamPostListFragment(FeedRequestPojo feedRequestPojo) {
        spamPostDialogFragment = (SpamPostListDialogFragment) getFragmentManager().findFragmentByTag(SpamPostListDialogFragment.class.getName());
        if (spamPostDialogFragment == null) {
            spamPostDialogFragment = new SpamPostListDialogFragment();
            Bundle bundle = new Bundle();
            Parcelable parcelable = Parcels.wrap(feedRequestPojo);
            bundle.putParcelable(AppConstants.SPAM_POST, parcelable);
            spamPostDialogFragment.setArguments(bundle);
        }
        if (!spamPostDialogFragment.isVisible() && !spamPostDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            spamPostDialogFragment.show(getFragmentManager(), SpamPostListDialogFragment.class.getName());
        }
        return spamPostDialogFragment;
    }

    public void postModerationListItemCount(int count) {
        Fragment fragmentCommunityOpenAbout = getSupportFragmentManager().findFragmentByTag(CommunityOpenAboutFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragmentCommunityOpenAbout)) {

            ((CommunityOpenAboutFragment) fragmentCommunityOpenAbout).setTvPostModerationCount(count);
        }
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    protected Map<String, Object> getExtraPropertiesToTrack() {
        if (mCommunityFeedObj == null) {
            return null;
        }
        HashMap<String, Object> properties = new
                EventProperty.Builder()
                .id(Long.toString(mCommunityFeedObj.getIdOfEntityOrParticipant()))
                .build();
        return properties;
    }
}
