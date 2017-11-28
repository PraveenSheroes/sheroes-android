package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.PandingMember;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
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
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getPandingMemberRequestBuilder;

/**
 * Created by Ajit Kumar on 07-02-2017.
 */

public class CommunityRequestedDialogFragment extends BaseDialogFragment implements RequestedView {
    private final String TAG = LogUtils.makeLogTag(CommunityRequestedDialogFragment.class);
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
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        ButterKnife.bind(this, view);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.PANDING_MEMBER_FRAGMENT, AppConstants.NO_REACTION_CONSTANT);
        if (null != getArguments()) {
            mFeedDetail = Parcels.unwrap(getArguments().getParcelable(AppConstants.COMMUNITY_DETAIL));
        }
        requestedPresenter.attachView(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), (CommunitiesDetailActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
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
        mFragmentListRefreshData.setEnitityOrParticpantid(mFeedDetail.getIdOfEntityOrParticipant());
        requestedPresenter.getAllPendingRequest(getPandingMemberRequestBuilder(mFeedDetail.getIdOfEntityOrParticipant(), mFragmentListRefreshData.getPageNo()));

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
        ((CommunitiesDetailActivity) getActivity()).updateOpenAboutFragment(mFeedDetail);
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requestedPresenter.detachView();
    }


    @Override
    public void getAllRequest(List<PandingMember> data) {
         mProgressBar.setVisibility(View.GONE);
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
                // TODO: ujjwal
                //mFeedDetail.setNoOfPendingRequest(pandingListData.size());
                mAdapter.setSheroesGenericListData(pandingListData);
                mAdapter.notifyDataSetChanged();
                ((CommunitiesDetailActivity) getActivity()).updateOpenAboutFragment(mFeedDetail);
                break;
            case AppConstants.FAILED:
                ((CommunitiesDetailActivity)getActivity()).onShowErrorDialog(memberListResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_JOIN_INVITE);
                break;
            default:
                ((CommunitiesDetailActivity)getActivity()).onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), ERROR_JOIN_INVITE);
        }
        dismiss();
    }

    private void approvePandingMember(MemberListResponse memberListResponse) {
        switch (memberListResponse.getStatus()) {
            case AppConstants.SUCCESS:
                pandingListData.remove(position);
                // TODO: ujjwal
                //mFeedDetail.setNoOfPendingRequest(pandingListData.size());
                mAdapter.setSheroesGenericListData(pandingListData);
                mAdapter.notifyDataSetChanged();
                ((CommunitiesDetailActivity) getActivity()).updateOpenAboutFragment(mFeedDetail);
                break;
            case AppConstants.FAILED:
                ((CommunitiesDetailActivity)getActivity()).onShowErrorDialog(memberListResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_JOIN_INVITE);
                break;
            default:
                ((CommunitiesDetailActivity)getActivity()).onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), ERROR_JOIN_INVITE);
        }
        dismiss();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth){
            @Override
            public void onBackPressed() {
                ((CommunitiesDetailActivity) getActivity()).updateOpenAboutFragment(mFeedDetail);
            }
        };
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

}