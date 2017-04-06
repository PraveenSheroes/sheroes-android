package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import appliedlife.pvtltd.SHEROES.models.entities.community.MembersList;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMember;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.presenters.MembersPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
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
    private final String TAG = LogUtils.makeLogTag(AllMembersFragment.class);
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
    private SwipPullRefreshList mPullRefreshList;
    @Inject
    AppUtils mAppUtils;
    LinearLayoutManager manager;
    private FragmentListRefreshData mFragmentListRefreshData;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    private GenericRecyclerViewAdapter mAdapter;
    FeedDetail mFeedDetail;
    List<MembersList> memberdata = new ArrayList<>();
    int position;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_members, container, false);
        ButterKnife.bind(this, view);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.MEMBER_FRAGMENT, AppConstants.EMPTY_STRING);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mmemberpresenter.attachView(this);
        if (null != getArguments()) {
            mFeedDetail = getArguments().getParcelable(AppConstants.COMMUNITY_DETAIL);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunitiesDetailActivity) getActivity());
        manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnScrollListener(new HidingScrollListener(mmemberpresenter, mRecyclerView, manager, mFragmentListRefreshData) {
            @Override
            public void onHide() {
            }

            @Override
            public void onShow() {
            }

            @Override
            public void dismissReactions() {

            }
        });
        super.setAllInitializationForMember(mFragmentListRefreshData, mPullRefreshList, mAdapter, manager, mPageNo, null, mRecyclerView, 0, 0, mmemberpresenter, mAppUtils, mProgressBar);
        mmemberpresenter.getAllMembers(getMemberRequestBuilder(mFeedDetail.getIdOfEntityOrParticipant(), mFragmentListRefreshData.getPageNo()));
        mFragmentListRefreshData.setEnitityOrParticpantid(mFeedDetail.getIdOfEntityOrParticipant());

        return view;
    }

    @OnClick(R.id.fmCommunityMembersClose)
    public void communityClosePress() {
        ((CommunitiesDetailActivity)getActivity()).closeMembersFragment(memberdata.size());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mmemberpresenter.detachView();
    }

    @Override
    public void getAllMembers(List<MembersList> listOfMember) {
        if (StringUtil.isNotEmptyCollection(listOfMember)) {
            memberdata.addAll(listOfMember);
            for (MembersList membersList : listOfMember) {
                if (mFeedDetail.isOwner()) {
                    membersList.setIsOwner(true);
                } else {
                    membersList.setIsOwner(false);
                }
            }
            tv_member_count.setText(AppConstants.LEFT_BRACKET+ mFeedDetail.getNoOfMembers() +AppConstants.RIGHT_BRACKET);
            mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mAdapter.setSheroesGenericListData(memberdata);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void removeMember(String data) {
        memberdata.remove(position);
        mAdapter.setSheroesGenericListData(memberdata);
        mAdapter.notifyDataSetChanged();
    }

    public void callRemoveMember(Long userId, Long communityId, int position) {
        this.position = position;
        RemoveMember removeMember = new RemoveMember();
        removeMember.setCommunityId((communityId));
        removeMember.setUserId(userId);
        removeMember.setCloudMessagingId("string");
        removeMember.setDeviceUniqueId("string");
        removeMember.setAppVersion("string");
        removeMember.setSource("string");
        mmemberpresenter.unJoinedApi(removeMember);
    }
}