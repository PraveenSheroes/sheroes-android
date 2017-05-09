package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.models.entities.community.Member;
import appliedlife.pvtltd.SHEROES.models.entities.community.MembersList;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerList;
import appliedlife.pvtltd.SHEROES.models.entities.community.PandingMember;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustomeCollapsableToolBar.CustomCollapsingToolbarLayout;
import appliedlife.pvtltd.SHEROES.views.fragments.AllMembersFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesDetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityOpenAboutFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityRequestedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CurrentStatusDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.InviteCommunityMember;
import appliedlife.pvtltd.SHEROES.views.fragments.InviteCommunityOwner;
import appliedlife.pvtltd.SHEROES.views.fragments.OwnerRemoveDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareCommunityFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ACTIVITY_FOR_REFRESH_FRAGMENT_LIST;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_COMMENT_ON_CARD_MENU;


public class CommunitiesDetailActivity extends BaseActivity implements  CommentReactionFragment.HomeActivityIntractionListner, AppBarLayout.OnOffsetChangedListener {
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
    private FeedDetail mFeedDetail;
    private FeedDetail mMyCommunityPostFeedDetail;
    private FragmentOpen mFragmentOpen;
    ViewPagerAdapter mViewPagerAdapter;
    private Fragment mFragment;
    private CommunityOpenAboutFragment mCommunityOpenAboutFragment;
    private CommunityEnum communityEnum = null;
    private InviteCommunityMember mInviteCommunityMember;
    private AllMembersFragment mAllMembersFragment;
    private boolean isMemberRemoveDialog;
    private CommunityRequestedFragment communityRequestedFragment;
    boolean isCommunityDetailFragment;
    private long mCommunityId;
    private long mCommunityPostId=0;
    @Bind(R.id.tv_community_detail_title)
    TextView mTvCommunityDetailTitle;
    @Bind(R.id.tv_community_detail_subtitle)
    TextView mTvCommunityDetailSubTitle;
    @Bind(R.id.li_header)
    public LinearLayout mLiHeader;
    @Bind(R.id.fab_post_community)
    public ImageView ivFabPostCommunity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_communities_detail);
        ButterKnife.bind(this);
        mFragmentOpen = new FragmentOpen();
        setAllValues(mFragmentOpen);
        initActivityTransitions();
        mAppBarLayout.addOnOffsetChangedListener(this);
        if (null != getIntent() && null != getIntent().getExtras()) {
            if (null != getIntent().getExtras().get(AppConstants.COMMUNITY_ID)) {
                mCommunityId = (long) getIntent().getExtras().get(AppConstants.COMMUNITY_ID);
                if (null != getIntent().getExtras().get(AppConstants.COMMUNITY_POST_ID))
                {
                    mCommunityPostId = (long) getIntent().getExtras().get(AppConstants.COMMUNITY_POST_ID);
                }
                if (mCommunityId > 0) {
                    mFeedDetail = new FeedDetail();
                    mFeedDetail.setIdOfEntityOrParticipant(mCommunityId);
                }
            } else {
                mFeedDetail = getIntent().getParcelableExtra(AppConstants.COMMUNITY_DETAIL);
                communityEnum = (CommunityEnum) getIntent().getSerializableExtra(AppConstants.MY_COMMUNITIES_FRAGMENT);
            }
        }
        setPagerAndLayouts();
    }

    private void setPagerAndLayouts() {
        ViewCompat.setTransitionName(mAppBarLayout, AppConstants.COMMUNITY_DETAIL);
        supportPostponeEnterTransition();
        setSupportActionBar(mToolbarCommunitiesDetail);
        if (null != mFeedDetail && null != communityEnum) {
            if (mFeedDetail.isClosedCommunity() && !mFeedDetail.isOwner()) {
                isCommunityDetailFragment = true;
                mCommunityDetailActivity.setVisibility(View.GONE);
                communityOpenAboutFragment(mFeedDetail);
            } else {
                mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                mViewPagerAdapter.addFragment(CommunitiesDetailFragment.createInstance(mFeedDetail, communityEnum,mCommunityPostId), getString(R.string.ID_COMMUNITIES));
                mViewPager.setAdapter(mViewPagerAdapter);
            }
        } else {
            mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            mViewPagerAdapter.addFragment(CommunitiesDetailFragment.createInstance(mFeedDetail, communityEnum,mCommunityPostId), getString(R.string.ID_COMMUNITIES));
            mViewPager.setAdapter(mViewPagerAdapter);
        }
    }

    public void initializeUiContent(FeedDetail feedDetail) {
        mFeedDetail=feedDetail;
        mCommunityDetailActivity.setVisibility(View.VISIBLE);
        mTvMemebr.setText(feedDetail.getNoOfMembers() + AppConstants.SPACE + getString(R.string.ID_MEMBERS));
        if (feedDetail.getNoOfMembers() > 1) {
            mTvMemebr.setVisibility(View.VISIBLE);
            mTvMemebr.setText(mFeedDetail.getNoOfMembers() + AppConstants.SPACE + getString(R.string.ID_MEMBERS));
        } else if (feedDetail.getNoOfViews() == 1) {
            mTvMemebr.setVisibility(View.VISIBLE);
            mTvMemebr.setText(mFeedDetail.getNoOfMembers() + AppConstants.SPACE + getString(R.string.ID_MEMBER));
        } else {
            mTvMemebr.setVisibility(View.INVISIBLE);
        }
        mCollapsingToolbarLayout.setTitle(AppConstants.SPACE);
        mCollapsingToolbarLayout.setSubtitle(AppConstants.SPACE);
        mTvCommunityDetailTitle.setText(feedDetail.getNameOrTitle());
        mTvCommunityDetailSubTitle.setText(feedDetail.getCommunityType());
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getImageUrl())) {
            Glide.with(this)
                    .load(feedDetail.getImageUrl()).asBitmap()
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

        } else if (baseResponse instanceof CommentReactionDoc) {
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
                bundleCommunity.putParcelable(AppConstants.OWNER_SUB_TYPE, ownerList);
            } else {
                isMemberRemoveDialog = isOwner;
                Member member = (Member) baseResponse;
                bundleCommunity.putParcelable(AppConstants.MEMBER, member);
                bundleCommunity.putLong(AppConstants.COMMUNITY_DETAIL, mFeedDetail.getIdOfEntityOrParticipant());
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
        mFragmentOpen.setOwner(mFeedDetail.isOwner());
        setAllValues(mFragmentOpen);
        super.feedCardsHandled(view, baseResponse);
        switch (id) {
            case R.id.tv_join_view_holder:
               inviteJoinEventClick(getString(R.string.ID_JOIN), feedDetail);
                    break;
            case R.id.card_community_detail:
                communityOpenAboutFragment(feedDetail);
                break;
            case R.id.tv_add_invite:
                if (null != feedDetail) {
                    if (null != mInviteCommunityMember) {
                        mInviteCommunityMember.onAddMemberClick(feedDetail);
                    }
                }
                break;
            case R.id.tv_owner_add:
                CallCommunityOwnerSearchFragment(feedDetail, feedDetail.getIdOfEntityOrParticipant());
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);

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
            bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, feedDetail);
            bundle.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, communityEnum);
            mCommunityOpenAboutFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                    .add(R.id.about_community_container, mCommunityOpenAboutFragment, CommunityOpenAboutFragment.class.getName()).addToBackStack(CommunityOpenAboutFragment.class.getName()).commitAllowingStateLoss();
        }
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        mFragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
        if (AppUtils.isFragmentUIActive(mFragment)) {
            if (mFragment instanceof CommunitiesDetailFragment) {
                ((CommunitiesDetailFragment) mFragment).likeAndUnlikeRequest(baseResponse, reactionValue, position);
            }
        }
    }

    public void updateOpenAboutFragment(FeedDetail feedDetail) {
        mFeedDetail = feedDetail;
        if (null != mInviteCommunityMember) {
            mInviteCommunityMember.dismiss();
        }
        if (null != mAllMembersFragment) {
            mAllMembersFragment.dismiss();
        }
        if (null != communityRequestedFragment) {
            communityRequestedFragment.dismiss();
        }
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(CommunityOpenAboutFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((CommunityOpenAboutFragment) fragment).refreshOpeAboutCommunityContent(feedDetail);
        }
    }

    private void CallCommunityOwnerSearchFragment(FeedDetail feedDetail, Long userId) {
        final Fragment fragmentCommunityOwnerSearch = getSupportFragmentManager().findFragmentByTag(InviteCommunityOwner.class.getName());
        if (AppUtils.isFragmentUIActive(fragmentCommunityOwnerSearch)) {
            ((InviteCommunityOwner) fragmentCommunityOwnerSearch).callAddOwner(feedDetail, userId);
        }

    }

    private void CallCommunityMemberRemoveFragment(Long userId, Long communityId, int position) {
        if (null != mAllMembersFragment) {
            mAllMembersFragment.callRemoveMember(userId, communityId, position);
        }
    }

    private void callCommunityPendingMemberRemoveFragment(long userId, long communityId, int position) {

        if (null != communityRequestedFragment) {
            communityRequestedFragment.removePandingRequest(userId, communityId, position);
        }

    }

    private void callCommunityAproveMemberFragment(long userId, long communityId, int position) {
        if (null != communityRequestedFragment) {
            communityRequestedFragment.approvePandingRequest(userId, communityId, position);
        }
    }


    public DialogFragment openAllMemberFragmentClick() {
        mAllMembersFragment = (AllMembersFragment) getFragmentManager().findFragmentByTag(AllMembersFragment.class.getName());
        if (mAllMembersFragment == null) {
            mAllMembersFragment = new AllMembersFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstants.COMMUNITIES_DETAIL, mFeedDetail);
            mAllMembersFragment.setArguments(bundle);
        }
        if (!mAllMembersFragment.isVisible() && !mAllMembersFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            mAllMembersFragment.show(getFragmentManager(), AllMembersFragment.class.getName());
        }
        return mAllMembersFragment;
    }

    public void inviteJoinEventClick(String pressedEventName, FeedDetail feedDetail) {
        if (pressedEventName.equalsIgnoreCase(getString(R.string.ID_JOIN))) {
            if (feedDetail.isClosedCommunity()) {
                feedDetail.setScreenName(AppConstants.COMMUNITY_DETAIL);
                showCommunityJoinReason(feedDetail);
            } else {
                Fragment fragmentCommunityDetail = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                if (AppUtils.isFragmentUIActive(fragmentCommunityDetail)) {
                    ((CommunitiesDetailFragment) fragmentCommunityDetail).joinRequestForOpenCommunity(mFeedDetail,pressedEventName);
                }
            }
        } else if (pressedEventName.equalsIgnoreCase(getString(R.string.ID_INVITE))) {
            inviteCommunityMemberDialog();
        }
    }

    public DialogFragment inviteCommunityMemberDialog() {
        mInviteCommunityMember = (InviteCommunityMember) getFragmentManager().findFragmentByTag(InviteCommunityMember.class.getName());
        if (mInviteCommunityMember == null) {
            mInviteCommunityMember = new InviteCommunityMember();
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstants.COMMUNITIES_DETAIL, mFeedDetail);
            mInviteCommunityMember.setArguments(bundle);
        }
        if (!mInviteCommunityMember.isVisible() && !mInviteCommunityMember.isAdded() && !isFinishing() && !mIsDestroyed) {
            mInviteCommunityMember.show(getFragmentManager(), InviteCommunityMember.class.getName());
        }
        return mInviteCommunityMember;
    }

    public void addOwnerOnClick() {
        mFragmentOpen.setOpenAboutFragment(false);
        mFragmentOpen.setInviteCommunityOwner(true);
        if (null != mFeedDetail) {
            InviteCommunityOwner myCommunityInviteMemberFragment = new InviteCommunityOwner();
            Bundle bundleInvite = new Bundle();
            bundleInvite.putParcelable(AppConstants.COMMUNITIES_DETAIL, mFeedDetail);
            myCommunityInviteMemberFragment.setArguments(bundleInvite);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                    .replace(R.id.about_community_container, myCommunityInviteMemberFragment, InviteCommunityOwner.class.getName()).addToBackStack(null).commitAllowingStateLoss();
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
        communityRequestedFragment = (CommunityRequestedFragment) getFragmentManager().findFragmentByTag(CommunityRequestedFragment.class.getName());
        if (communityRequestedFragment == null) {
            communityRequestedFragment = new CommunityRequestedFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, mFeedDetail);
            communityRequestedFragment.setArguments(bundle);
        }
        if (!communityRequestedFragment.isVisible() && !communityRequestedFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            communityRequestedFragment.show(getFragmentManager(), CommunityRequestedFragment.class.getName());
        }
        return communityRequestedFragment;
    }

    public void createCommunityPostClick(FeedDetail feedDetail) {
        Intent intent = new Intent(this, CreateCommunityPostActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.COMMUNITY_POST_FRAGMENT, feedDetail);
        intent.putExtras(bundle);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_CREATE_COMMUNITY_POST);
        // overridePendingTransition(R.anim.bottom_to_top_slide_anim, R.anim.bottom_to_top_slide_reverse_anim);
    }

    public void createCommunityClick(FeedDetail feedDetail) {
        Intent intent = new Intent(this, CreateCommunityActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.COMMUNITIES_DETAIL, feedDetail);
        intent.putExtras(bundle);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_CREATE_COMMUNITY);
        overridePendingTransition(R.anim.bottom_to_top_slide_anim, R.anim.bottom_to_top_slide_reverse_anim);
    }

    @OnClick(R.id.tv_communities_detail_share)
    public void shareClick() {
        ShareCommunityFragment shareCommunityFragment = new ShareCommunityFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.SHARE, mFeedDetail);
        shareCommunityFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                .replace(R.id.about_community_container, shareCommunityFragment).addToBackStack(null).commitAllowingStateLoss();
    }


    public void onOwnerClose() {
        final Fragment fragmentCommunityOwnerSearch = getSupportFragmentManager().findFragmentByTag(CommunityOpenAboutFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragmentCommunityOwnerSearch)) {
            if (!isMemberRemoveDialog) {
                mFeedDetail.setNoOfMembers(mFeedDetail.getNoOfMembers() - 1);
            }
            ((CommunityOpenAboutFragment) fragmentCommunityOwnerSearch).callRemoveOwner(mFeedDetail);
        }
    }

    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mMyCommunityPostFeedDetail = feedDetail;
        onBackPressed();
    }

    @Override
    public void onClickReactionList(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mMyCommunityPostFeedDetail = feedDetail;
        if (mFragmentOpen.isReactionList()) {
            mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.THREE_CONSTANT);
            setAllValues(mFragmentOpen);
            super.openCommentReactionFragment(mMyCommunityPostFeedDetail);
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
            mFragmentOpen.setCommentList(true);
        } else if (mFragmentOpen.isOpenAboutFragment()) {
            if (isCommunityDetailFragment) {
                onBackClick();
            } else {
                getSupportFragmentManager().popBackStackImmediate();
                Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                if (AppUtils.isFragmentUIActive(fragment)) {
                    if (fragment instanceof CommunitiesDetailFragment) {
                        ((CommunitiesDetailFragment) fragment).updateUiAccordingToFeedDetail(mFeedDetail);
                    }
                }
                mCommunityDetailActivity.setVisibility(View.VISIBLE);
                mFragmentOpen.setOpenAboutFragment(false);
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
        mFeedDetail = feedDetail;

    }

    public void onLeaveClick(CommunityEnum communityEnum) {
       this.communityEnum=communityEnum;
        onBackClick();
    }

    @OnClick(R.id.iv_community_detail_back)
    public void onBackClick() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.COMMUNITIES_DETAIL, mFeedDetail);
        bundle.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, communityEnum);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
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
                    FeedDetail feedDetail = (FeedDetail) intent.getExtras().get(AppConstants.COMMUNITIES_DETAIL);
                    updateOpenAboutFragment(feedDetail);
                    break;
                case AppConstants.REQUEST_CODE_FOR_CREATE_COMMUNITY_POST:
                    FeedDetail feedCommunityPost = (FeedDetail) intent.getExtras().get(AppConstants.COMMUNITY_POST_FRAGMENT);
                    Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        if (fragment instanceof CommunitiesDetailFragment) {
                            ((CommunitiesDetailFragment) fragment).updateUiAccordingToFeedDetail(mFeedDetail);
                        }
                    }
                    break;
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                    FeedDetail feedCommunityPostEdit = (FeedDetail) intent.getExtras().get(AppConstants.COMMUNITY_POST_FRAGMENT);
                    Fragment fragmentCommunity = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                    if (AppUtils.isFragmentUIActive(fragmentCommunity)) {
                        if (fragmentCommunity instanceof CommunitiesDetailFragment) {
                            ((CommunitiesDetailFragment) fragmentCommunity).updateUiAccordingToFeedDetail(mFeedDetail);
                        }
                    }
                    break;
                default:
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
}
