package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.PandingMember;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.presenters.MembersPresenter;
import appliedlife.pvtltd.SHEROES.presenters.RequestedPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.RequestedView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getMemberRequestBuilder;

/**
 * Created by Ajit Kumar on 07-02-2017.
 */

public class CommunityRequestedFragment extends BaseFragment implements RequestedView {
    private final String TAG = LogUtils.makeLogTag(CommunityRequestedFragment.class);
    @Inject
    MembersPresenter mmemberpresenter;
    @Inject
    RequestedPresenter requestedPresenter;
    @Bind(R.id.rv_community_requested_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_community_requested_progress_bar)
    ProgressBar mProgressBar;
    private GenericRecyclerViewAdapter mAdapter;
    FeedDetail mFeedDetail;
    int position;
    private FragmentListRefreshData mFragmentListRefreshData;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    List<PandingMember> pandingListData = new ArrayList<>();
    @Inject
    AppUtils mAppUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        ButterKnife.bind(this, view);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.MEMBER_FRAGMENT, AppConstants.EMPTY_STRING);

        if (null != getArguments()) {
            mFeedDetail = getArguments().getParcelable(AppConstants.COMMUNITY_DETAIL);
        }
        requestedPresenter.attachView(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunitiesDetailActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new HidingScrollListener(requestedPresenter, mRecyclerView, manager, mFragmentListRefreshData) {
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
        super.setAllInitializationForPandingMember(mFragmentListRefreshData, mAdapter, manager, mPageNo, null, mRecyclerView, 0, 0, mmemberpresenter, mAppUtils, mProgressBar);
        requestedPresenter.getAllMembers(getMemberRequestBuilder(mFeedDetail.getIdOfEntityOrParticipant(), mFragmentListRefreshData.getPageNo()));
        mFragmentListRefreshData.setEnitityOrParticpantid(mFeedDetail.getIdOfEntityOrParticipant());

        return view;
    }

    public void removePandingRequest(long userId, long communityId, int position) {
        this.position = position;
        requestedPresenter.onRejectMemberApi(mAppUtils.removeMemberRequestBuilder(communityId, userId));
    }

    public void approvePandingRequest(long userId, long communityId, int position) {
        this.position = position;
        requestedPresenter.onApproveMember(mAppUtils.approveMemberRequestBuilder(communityId, userId));
    }


    @OnClick(R.id.fmCommunityMembersClose)
    public void communityClosePress() {
        ((CommunitiesDetailActivity)getActivity()).getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requestedPresenter.detachView();
    }


    @Override
    public void getAllRequest(List<PandingMember> data) {
        if (StringUtil.isNotEmptyCollection(data)) {
            pandingListData = data;
            mAdapter.setSheroesGenericListData(data);
            mAdapter.notifyDataSetChanged();
            mFragmentListRefreshData.setPageNo(mFragmentListRefreshData.getPageNo() + 1);
        }
    }

    @Override
    public void removeApprovePandingMember(MemberListResponse memberListResponse, CommunityEnum communityEnum) {
        switch (communityEnum) {
            case APPROVE_REQUEST:
                approvePandingMember(memberListResponse);
                break;
            case REMOVE_REQUEST:
                removePandingMember(memberListResponse);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + communityEnum);
        }
    }

    private void removePandingMember(MemberListResponse memberListResponse) {
        switch (memberListResponse.getStatus()) {
            case AppConstants.SUCCESS:
                pandingListData.remove(position);
                mAdapter.setSheroesGenericListData(pandingListData);
                mAdapter.notifyDataSetChanged();
                break;
            case AppConstants.FAILED:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(memberListResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_JOIN_INVITE);
                break;
            default:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(AppConstants.HTTP_401_UNAUTHORIZED, ERROR_JOIN_INVITE);
        }
    }

    private void approvePandingMember(MemberListResponse memberListResponse) {
        switch (memberListResponse.getStatus()) {
            case AppConstants.SUCCESS:
                pandingListData.remove(position);
                mAdapter.setSheroesGenericListData(pandingListData);
                mAdapter.notifyDataSetChanged();
                break;
            case AppConstants.FAILED:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(memberListResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_JOIN_INVITE);
                break;
            default:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(AppConstants.HTTP_401_UNAUTHORIZED, ERROR_JOIN_INVITE);
        }
    }

}