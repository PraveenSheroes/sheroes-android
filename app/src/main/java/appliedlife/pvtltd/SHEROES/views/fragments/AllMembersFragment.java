package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.MembersList;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMember;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.presenters.MembersPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.AllMembersView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getMemberRequestBuilder;

/**
 * Created by Ajit Kumar on 03-02-2017.
 */

public class AllMembersFragment extends BaseFragment implements AllMembersView {
    private final String TAG = LogUtils.makeLogTag(CommentReactionFragment.class);
    @Inject
    MembersPresenter mmemberpresenter;
    @Bind(R.id.rv_member_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_member_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.fmCommunityMembersClose)
    FrameLayout fmCommunityMembersClose;
    @Bind(R.id.tv_member_count)
    TextView tv_member_count;
   /* @Bind(R.id.swipe_view_member)
    SwipeRefreshLayout mSwipeView;*/
    private FragmentOpen mFragmentOpen;
    private SwipPullRefreshList mPullRefreshList;
    @Inject
    AppUtils mAppUtils;
    LinearLayoutManager manager;
    private FragmentListRefreshData mFragmentListRefreshData;
    private int mPageNo = AppConstants.ONE_CONSTANT;

    private String mSearchDataName = AppConstants.EMPTY_STRING;
    private GenericRecyclerViewAdapter mAdapter;
    private MembersHomeActivityIntractionListner mHomeActivityIntractionListner;
    FeedDetail mFeedDetail;
    List<MembersList>  memberdata=new ArrayList<>();
    int position;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof MembersHomeActivityIntractionListner) {
                mHomeActivityIntractionListner = (MembersHomeActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_members, container, false);
        ButterKnife.bind(this, view);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.MEMBER_FRAGMENT, AppConstants.EMPTY_STRING);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mmemberpresenter.attachView(this);
        if(null!=getArguments())
        {
            mFeedDetail =getArguments().getParcelable(AppConstants.COMMUNITY_DETAIL);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunitiesDetailActivity) getActivity());
        manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnScrollListener(new HidingScrollListener(mmemberpresenter, mRecyclerView, manager, mFragmentListRefreshData) {
            @Override
            public void onHide() {
                //((CommunitiesDetailActivity) getActivity()).mFlHomeFooterList.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onShow() {
               // ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.VISIBLE);
            }

            @Override
            public void dismissReactions() {

            }
        });
        super.setAllInitializationForMember(mFragmentListRefreshData, mPullRefreshList, mAdapter, manager, mPageNo, null, mRecyclerView, 0, 0, mmemberpresenter, mAppUtils, mProgressBar);
      /*  switch (articleCategory) {
            case AppConstants.ONE_CONSTANT:
                break;
            case AppConstants.TWO_CONSTANT:
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE+ TAG + AppConstants.SPACE + articleCategory);
        }*/
     //   mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_ARTICLE, mFragmentListRefreshData.getPageNo()));
      /*  mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setListLoadFlag(false);
                mPullRefreshList.setPullToRefresh(true);
                mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
                mPullRefreshList = new SwipPullRefreshList();
                setRefreshList(mPullRefreshList);
                mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
            //    mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.FEED_ARTICLE, mFragmentListRefreshData.getPageNo()));
            }
        });*/



        mmemberpresenter.getAllMembers(getMemberRequestBuilder(mFeedDetail.getIdOfEntityOrParticipant(),mFragmentListRefreshData.getPageNo()));


        mFragmentListRefreshData.setEnitityOrParticpantid(mFeedDetail.getIdOfEntityOrParticipant());



        return view;
    }
    @OnClick(R.id.fmCommunityMembersClose)
    public void communityClosePress()
    {
        mHomeActivityIntractionListner.closeMembersFragment(memberdata.size());
    }
    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mmemberpresenter.detachView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void getAllMembers(List<MembersList> data) {
       // memberdata.add(data);
        memberdata.addAll(data);
        for(int i=0;i<memberdata.size();i++)
        {
            if(mFeedDetail.isOwner() || mFeedDetail.isMember())
            memberdata.get(i).setIsOwner(true);
            else
                memberdata.get(i).setIsOwner(false);
        }
        List<MembersList> tempdata=new ArrayList<>();
            mAdapter.setSheroesGenericListData(memberdata);
        mAdapter.notifyDataSetChanged();
        if(null !=data) {
            tv_member_count.setText("(" + mFeedDetail.getNoOfMembers() + ")");
            mFragmentListRefreshData.setPageNo(mFragmentListRefreshData.getPageNo()+1);
        }
    }

    @Override
    public void removeMember(String data) {
        memberdata.remove(position);
        mAdapter.setSheroesGenericListData(memberdata);
    }

    public void callRemoveMember(Long userId,Long communityId,int position)
    {
        this.position=position;
        RemoveMember removeMember=new RemoveMember();
        removeMember.setCommunityId((communityId));
        removeMember.setUserId(userId);
        removeMember.setCloudMessagingId("string");
        removeMember.setDeviceUniqueId("string");
        removeMember.setAppVersion("string");
        removeMember.setSource("string");
        mmemberpresenter.unJoinedApi(removeMember);
    }
    @Override
    public void showNwError() {

    }


    @Override
    public void startNextScreen() {

    }



    public interface MembersHomeActivityIntractionListner {
        void onErrorOccurence();
        void closeMembersFragment(int size);
        void onClickReactionList(FragmentOpen isFragmentOpen);
    }
   /* @OnClick(R.id.tv_user_comment_header_text)
    public void dismissCommentDialog()
    {
        mHomeActivityIntractionListner.onDialogDismissWithListRefresh(mFragmentOpen);
    }
    @OnClick(R.id.fl_comment_reaction)
    public void openReactionList()
    {
        mFragmentOpen.setReactionList(true);
        mFragmentOpen.setCommentList(false);
        mHomeActivityIntractionListner.onClickReactionList(mFragmentOpen);
    }*/
}