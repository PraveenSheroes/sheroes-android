package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
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
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityInviteSearchFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityOpenAboutFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityRequestedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CurrentStatusDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.InviteCommunityMember;
import appliedlife.pvtltd.SHEROES.views.fragments.InviteCommunityOwner;
import appliedlife.pvtltd.SHEROES.views.fragments.OwnerRemoveDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareCommunityFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.InviteFragmentListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ACTIVITY_FOR_REFRESH_FRAGMENT_LIST;


public class CommunitiesDetailActivity extends BaseActivity implements  InviteFragmentListener, InviteCommunityOwner.InviteOwnerDoneIntractionListner, ShareCommunityFragment.ShareCommunityActivityIntractionListner, CommunityInviteSearchFragment.InviteSearchActivityIntractionListner, CommunityRequestedFragment.RequestHomeActivityIntractionListner, CommentReactionFragment.HomeActivityIntractionListner {
    private final String TAG = LogUtils.makeLogTag(CommunitiesDetailActivity.class);
    @Bind(R.id.app_bar_coomunities_detail)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.iv_communities_detail)
    ImageView ivCommunitiesDetail;
    @Bind(R.id.view_pager_communities_detail)
    ViewPager mViewPagerCommunitiesDetail;
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
    private FragmentOpen mFragmentOpen;
    ViewPagerAdapter viewPagerAdapter;
    private Fragment mFragment;
    CommunityRequest communityRequest = new CommunityRequest();
    List<Long> mUserIdsList = new ArrayList<>();
    Long mCommunityId;
    private CommunityOpenAboutFragment communityOpenAboutFragment;
    private CommunityEnum communityEnum = null;

    public static void navigate(AppCompatActivity activity, View transitionImage, FeedDetail feedDetail) {
        Intent intent = new Intent(activity, CommunitiesDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, feedDetail);
        intent.putExtras(bundle);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, AppConstants.COMMUNITY_DETAIL);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        mFragmentOpen = new FragmentOpen();
        setAllValues(mFragmentOpen);
        initActivityTransitions();
        setContentView(R.layout.activity_communities_detail);
        ButterKnife.bind(this);
        if (null != getIntent()) {
            mFeedDetail = getIntent().getParcelableExtra(AppConstants.COMMUNITY_DETAIL);
            LogUtils.error("***********tagId In detail page=", mFeedDetail.getTag_ids() + "");
            communityEnum = (CommunityEnum) getIntent().getSerializableExtra(AppConstants.MY_COMMUNITIES_FRAGMENT);
        }
        setPagerAndLayouts();
    }

    private void setPagerAndLayouts() {
        ViewCompat.setTransitionName(mAppBarLayout, AppConstants.COMMUNITY_DETAIL);
        supportPostponeEnterTransition();
        setSupportActionBar(mToolbarCommunitiesDetail);
        mCollapsingToolbarLayout.setExpandedSubTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        mCollapsingToolbarLayout.setExpandedTitleMarginStart(200);
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        if (null != mFeedDetail) {
            if (mFeedDetail.isClosedCommunity()) {
                mCommunityDetailActivity.setVisibility(View.GONE);
                communityOpenAboutFragment();
            } else {
                mCommunityDetailActivity.setVisibility(View.VISIBLE);
                mTvMemebr.setText(mFeedDetail.getNoOfMembers() + AppConstants.SPACE + getString(R.string.ID_MEMBERS));
                //  mFloatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_feed_article_top_left));
                mCollapsingToolbarLayout.setTitle(mFeedDetail.getNameOrTitle());
                mCollapsingToolbarLayout.setSubtitle(mFeedDetail.getNameOrTitle());
                viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                viewPagerAdapter.addFragment(CommunitiesDetailFragment.createInstance(getIntent()), getString(R.string.ID_COMMUNITIES));
                mViewPagerCommunitiesDetail.setAdapter(viewPagerAdapter);
                if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getImageUrl())) {
                    Glide.with(this)
                            .load(mFeedDetail.getImageUrl()).asBitmap()
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
                    ivCommunitiesDetail.setImageDrawable(getResources().getDrawable(R.drawable.blank_image));
                    supportStartPostponedEnterTransition();
                }
            }
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

    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(getResources().getColor(android.R.color.white));
        int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.colorPrimary));
        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }

    @Override
    public void startActivityFromHolder(Intent intent) {

    }


    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FeedDetail) {
            communityDetailHandled(view, baseResponse);
        } else if (baseResponse instanceof OwnerList) {
            showOwnerRemoveDialog(true, baseResponse);
        } else if (baseResponse instanceof Member) {
            showOwnerRemoveDialog(false, baseResponse);
        } else if (baseResponse instanceof MembersList) {
            MembersList membersList = (MembersList) baseResponse;
            CallCommunityMemberRemoveFragment(membersList.getUsersId(), membersList.getCommunityId(), membersList.getPosition());

        } else if (baseResponse instanceof PandingMember) {
            PandingMember pandingMember = (PandingMember) baseResponse;

            switch (view.getId()) {
                case R.id.tv_panding_member_cross:
                    CallCommunityPandingMemberRemoveFragment(pandingMember.getUsersId(), pandingMember.getCommunityId(), pandingMember.getPosition());

                    break;
                case R.id.tv_panding_member_check:
                    CallCommunityAproveMemberFragment(pandingMember.getUsersId(), pandingMember.getCommunityId(), pandingMember.getPosition());

                    break;
            }

        }
    }

    public DialogFragment showOwnerRemoveDialog(boolean isOwner, BaseResponse baseResponse) {
        OwnerRemoveDialog ownerRemoveDialog = (OwnerRemoveDialog) getFragmentManager().findFragmentByTag(OwnerRemoveDialog.class.getName());
        if (ownerRemoveDialog == null) {
            ownerRemoveDialog = new OwnerRemoveDialog();
            Bundle bundleCommunity = new Bundle();
            if (isOwner) {
                OwnerList ownerList = (OwnerList) baseResponse;
                bundleCommunity.putParcelable(AppConstants.OWNER_SUB_TYPE, ownerList);
            } else {
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
        FeedDetail callAddOwner = (FeedDetail) baseResponse;

        int id = view.getId();
        if (!mFeedDetail.isClosedCommunity()) {
            mFragment = viewPagerAdapter.getActiveFragment(mViewPagerCommunitiesDetail, AppConstants.NO_REACTION_CONSTANT);
            setFragment(mFragment);
            mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.THREE_CONSTANT);
            mFragmentOpen.setOwner(mFeedDetail.isOwner());
            setAllValues(mFragmentOpen);
        }
        super.feedCardsHandled(view, baseResponse);
        switch (id) {
            case R.id.card_community_detail:
                communityOpenAboutFragment();
            case R.id.tv_add_invite:
                if (null != baseResponse) {
                    Long userid=((FeedDetail) baseResponse).getIdOfEntityOrParticipant();
                    if(null!=userid&&userid>AppConstants.NO_REACTION_CONSTANT) {
                        mUserIdsList.add(userid);
                        mCommunityId = mFeedDetail.getIdOfEntityOrParticipant();
                    }
                }
                break;
            case R.id.tv_owner_cross:
                if (baseResponse instanceof FeedDetail)
                    CallCommunityOwnerSearchFragment(callAddOwner.getIdOfEntityOrParticipant());
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);

                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }

    }

    private void communityOpenAboutFragment() {
        if (null != mFeedDetail) {
            communityOpenAboutFragment = new CommunityOpenAboutFragment();
            communityOpenAboutFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                    .replace(R.id.about_community_container, communityOpenAboutFragment, CommunityOpenAboutFragment.class.getName()).addToBackStack(CommunityOpenAboutFragment.class.getName()).commitAllowingStateLoss();
        }
    }

    private void CallCommunityOwnerSearchFragment(Long userId) {
        final Fragment fragmentCommunityOwnerSearch = getSupportFragmentManager().findFragmentByTag(InviteCommunityOwner.class.getName());
        if (AppUtils.isFragmentUIActive(fragmentCommunityOwnerSearch)) {
            ((InviteCommunityOwner) fragmentCommunityOwnerSearch).callAddOwner(userId);
        }

    }

    private void CallCommunityMemberRemoveFragment(Long userId, Long communityId, int position) {

        final Fragment frgmentAllMember = getSupportFragmentManager().findFragmentByTag(AllMembersFragment.class.getName());
        if (AppUtils.isFragmentUIActive(frgmentAllMember)) {
            ((AllMembersFragment) frgmentAllMember).callRemoveMember(userId, communityId, position);
        }

    }

    private void CallCommunityPandingMemberRemoveFragment(long userId, long communityId, int position) {

        final Fragment frgmentAllMember = getSupportFragmentManager().findFragmentByTag(CommunityRequestedFragment.class.getName());
        if (AppUtils.isFragmentUIActive(frgmentAllMember)) {
            ((CommunityRequestedFragment) frgmentAllMember).removePandingRequest(userId, communityId, position);
        }

    }

    private void CallCommunityAproveMemberFragment(long userId, long communityId, int position) {

        final Fragment frgmentAllMember = getSupportFragmentManager().findFragmentByTag(CommunityRequestedFragment.class.getName());
        if (AppUtils.isFragmentUIActive(frgmentAllMember)) {
            ((CommunityRequestedFragment) frgmentAllMember).approvePandingRequest(userId, communityId, position);
        }

    }


    public void memberClick() {
        AllMembersFragment frag = new AllMembersFragment();
        Bundle bundleCommunity = new Bundle();
        bundleCommunity.putParcelable(AppConstants.COMMUNITY_DETAIL, mFeedDetail);
        frag.setArguments(bundleCommunity);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                .replace(R.id.about_community_container, frag, AllMembersFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

    }

    public void inviteJoinEventClick(String pressedEventName, FeedDetail feedDetail) {
        if (pressedEventName.equals(getString(R.string.ID_JOIN))) {
            if (feedDetail.isClosedCommunity()) {
                showCommunityJoinReason(feedDetail);
            } else {
                Fragment fragmentCommunityDetail = getSupportFragmentManager().findFragmentByTag(CommunitiesDetailFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragmentCommunityDetail)) {
                    ((CommunitiesDetailFragment) fragmentCommunityDetail).joinRequestForOpenCommunity(mFeedDetail);
                }
            }
        } else {
            InviteCommunityMember myCommunityInviteMemberFragment = new InviteCommunityMember();
            Bundle bundleInvite = new Bundle();
            bundleInvite.putParcelable(AppConstants.COMMUNITIES_DETAIL, mFeedDetail);
            myCommunityInviteMemberFragment.setArguments(bundleInvite);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                    .replace(R.id.about_community_container, myCommunityInviteMemberFragment, InviteCommunityMember.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        }
    }

    public void inviteJoinEventClickFromAboutPage(String pressedEventName, FeedDetail feedDetail, CommunityOpenAboutFragment communityOpenAboutFragment) {
        if (pressedEventName.equals(getString(R.string.ID_JOIN))) {
            if (mFeedDetail.isClosedCommunity()) {
                showCommunityJoinReasonFromAboutCommunity(feedDetail, communityOpenAboutFragment);
            } else {

            }
        } else {
            InviteCommunityMember myCommunityInviteMemberFragment = new InviteCommunityMember();
            Bundle bundleInvite = new Bundle();
            bundleInvite.putParcelable(AppConstants.COMMUNITIES_DETAIL, mFeedDetail);
            myCommunityInviteMemberFragment.setArguments(bundleInvite);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                    .replace(R.id.about_community_container, myCommunityInviteMemberFragment, InviteCommunityMember.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        }
    }

    public void ownerClick() {
        if (null != mFeedDetail) {
            InviteCommunityOwner myCommunityInviteMemberFragment = new InviteCommunityOwner();
            Bundle bundleInvite = new Bundle();
            bundleInvite.putParcelable(AppConstants.COMMUNITIES_DETAIL, mFeedDetail);
            myCommunityInviteMemberFragment.setArguments(bundleInvite);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                    .replace(R.id.about_community_container, myCommunityInviteMemberFragment, InviteCommunityOwner.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        }
    }

    public void requestClick() {
        CommunityRequestedFragment frag = new CommunityRequestedFragment();
        Bundle bundleCommunity = new Bundle();
        bundleCommunity.putParcelable(AppConstants.COMMUNITY_DETAIL, mFeedDetail);
        frag.setArguments(bundleCommunity);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                .replace(R.id.about_community_container, frag, CommunityRequestedFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

    }

    public void createCommunityClick(FeedDetail feedDetail) {
        Intent intent = new Intent(this, CreateCommunityActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.COMMUNITIES_DETAIL, feedDetail);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void shareClick() {
        ShareCommunityFragment frag = new ShareCommunityFragment();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                .replace(R.id.about_community_container, frag).addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    public void onErrorOccurence() {
    }

    public void onOwnerClose() {
        final Fragment fragmentCommunityOwnerSearch = getSupportFragmentManager().findFragmentByTag(CommunityOpenAboutFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragmentCommunityOwnerSearch)) {
            ((CommunityOpenAboutFragment) fragmentCommunityOwnerSearch).callRemoveOwner(mFeedDetail.getIdOfEntityOrParticipant());
        }
    }

    @Override
    public void OwnerAddDone() {
        getSupportFragmentManager().popBackStackImmediate();

    }

    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mFeedDetail = feedDetail;
        onBackPressed();
    }

    @Override
    public void onClickReactionList(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mFeedDetail = feedDetail;
        if (mFragmentOpen.isReactionList()) {
            mFragmentOpen.setOpenCommentReactionFragmentFor(AppConstants.THREE_CONSTANT);
            setAllValues(mFragmentOpen);
            super.openCommentReactionFragment(mFeedDetail);
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragmentOpen.isCommentList()) {
            getSupportFragmentManager().popBackStackImmediate();
            if (AppUtils.isFragmentUIActive(mFragment)) {
                ((CommunitiesDetailFragment) mFragment).commentListRefresh(mFeedDetail, ACTIVITY_FOR_REFRESH_FRAGMENT_LIST);
            }
            mFragmentOpen.setCommentList(false);
        } else if (mFragmentOpen.isReactionList()) {
            getSupportFragmentManager().popBackStack();
            mFragmentOpen.setReactionList(false);
            mFragmentOpen.setCommentList(true);
        } else {
            onBackClick();
        }
    }

    public void closeOwner() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onShareClose() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void closeInvite() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void closeRequestFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onClickReactionList(FragmentOpen isFragmentOpen) {

    }

    public void closeMembersFragment(int size) {
        getSupportFragmentManager().popBackStack();
        final Fragment frgmentAllMember = getSupportFragmentManager().findFragmentByTag(CommunityOpenAboutFragment.class.getName());
        if (AppUtils.isFragmentUIActive(frgmentAllMember)) {
            ((CommunityOpenAboutFragment) frgmentAllMember).refreshData(size);
        }

    }

    public void onClose() {
        if (mFeedDetail.isClosedCommunity()) {
            finish();
        } else {
            mCommunityDetailActivity.setVisibility(View.VISIBLE);
            ivCommunitiesDetail.setVisibility(View.VISIBLE);
            mToolbarCommunitiesDetail.setVisibility(View.VISIBLE);
            getSupportFragmentManager().popBackStack();
        }

    }

    public void onLeaveClick() {
        finish();
    }

    @OnClick(R.id.iv_community_detail_back)
    public void onBackClick() {
        if (mFeedDetail.isClosedCommunity()) {
            finish();
        } else {
            if (communityOpenAboutFragment != null) communityOpenAboutFragment.dismissPopup();
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstants.COMMUNITIES_DETAIL, mFeedDetail);
            bundle.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, communityEnum);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            super.onBackPressed();
        }
    }

    @Override
    public void callInviteMembers() {
        final Fragment fragmentCommunityMember = getSupportFragmentManager().findFragmentByTag(InviteCommunityMember.class.getName());
        if (AppUtils.isFragmentUIActive(fragmentCommunityMember)) {
            ((InviteCommunityMember) fragmentCommunityMember).AddMembersOnCallInviteCommunity(mUserIdsList, mCommunityId);
            mUserIdsList.clear();
        }
    }



    @Override
    public void onShowErrorDialog(String errorReason, FeedParticipationEnum feedParticipationEnum) {
        if (StringUtil.isNotNullOrEmptyString(errorReason)) {
            switch (errorReason) {
                case AppConstants.CHECK_NETWORK_CONNECTION:
                    showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                    break;
                default:
                    showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
            }
        } else {
            showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }
    }

    @Override
    public void onInviteDone() {
       /* final Fragment fragmentCommunityOwnerSearch = getSupportFragmentManager().findFragmentByTag(CommunityOpenAboutFragment.class.getName());
            ((CommunityOpenAboutFragment) fragmentCommunityOwnerSearch).refreshMemberCount();*/
        getSupportFragmentManager().popBackStack();
    }
}
