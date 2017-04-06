package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.ApproveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.PandingMember;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.presenters.MembersPresenter;
import appliedlife.pvtltd.SHEROES.presenters.RequestedPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.RequestedView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getMemberRequestBuilder;

/**
 * Created by Ajit Kumar on 07-02-2017.
 */

public class CommunityRequestedFragment extends BaseFragment implements RequestedView {
    private final String TAG = LogUtils.makeLogTag(CommentReactionFragment.class);
    @Inject
    MembersPresenter mmemberpresenter;
    @Inject
    RequestedPresenter requestedPresenter;
    @Bind(R.id.rv_community_requested_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_community_requested_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.fmCommunityMembersClose)
    FrameLayout fmCommunityMembersClose;
    private FragmentOpen mFragmentOpen;
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    private GenericRecyclerViewAdapter mAdapter;
    private RequestHomeActivityIntractionListner mHomeActivityIntractionListner;
    FeedDetail mFeedDetail;
    int position;
    private FragmentListRefreshData mFragmentListRefreshData;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    List<PandingMember> pandingListData=new ArrayList<>();
    @Inject
    AppUtils mAppUtils;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof RequestHomeActivityIntractionListner) {
                mHomeActivityIntractionListner = (RequestHomeActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        ButterKnife.bind(this, view);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.MEMBER_FRAGMENT, AppConstants.EMPTY_STRING);

        if(null!=getArguments())
        {
            mFeedDetail =getArguments().getParcelable(AppConstants.COMMUNITY_DETAIL);
        }
        requestedPresenter.attachView(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunitiesDetailActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
       /* MemberRequest memberRequest=new MemberRequest();
        memberRequest.setDeviceUniqueId("String");
        memberRequest.setScreenName("String");
        memberRequest.setLastScreenName("String");
        memberRequest.setCloudMessagingId("String");
        memberRequest.setPageSize(200);
        memberRequest.setPageNo(1);
        memberRequest.setAppVersion("String");
        memberRequest.setCommunityId(mFeedDetail.getIdOfEntityOrParticipant());
        requestedPresenter.getAllMembers(memberRequest);*/


        mRecyclerView.addOnScrollListener(new HidingScrollListener(requestedPresenter, mRecyclerView, manager, mFragmentListRefreshData) {
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
        super.setAllInitializationForPandingMember(mFragmentListRefreshData, mAdapter, manager, mPageNo, null, mRecyclerView, 0, 0, mmemberpresenter, mAppUtils, mProgressBar);
        requestedPresenter.getAllMembers(getMemberRequestBuilder(mFeedDetail.getIdOfEntityOrParticipant(),mFragmentListRefreshData.getPageNo()));
        mFragmentListRefreshData.setEnitityOrParticpantid(mFeedDetail.getIdOfEntityOrParticipant());

        return view;
    }
    public void removePandingRequest(long userId, long communityId, int position)
    {
        this.position=position;
        RemoveMemberRequest removeMemberRequest =new RemoveMemberRequest();
        removeMemberRequest.setCommunityId((communityId));
        removeMemberRequest.setUserId(userId);
        removeMemberRequest.setCloudMessagingId("string");
        removeMemberRequest.setDeviceUniqueId("string");
        removeMemberRequest.setAppVersion("string");
        removeMemberRequest.setSource("string");
        requestedPresenter.onRejectMemberApi(removeMemberRequest);
    }
    public void approvePandingRequest(long userId, long communityId, int position)
    {
        this.position=position;
        ApproveMemberRequest removeMember=new ApproveMemberRequest();
        removeMember.setCommunityId((communityId));
        removeMember.setUserId(userId);
        removeMember.setCloudMessagingId("string");
        removeMember.setDeviceUniqueId("string");
        removeMember.setAppVersion("string");
        removeMember.setSource("string");
        requestedPresenter.onApproveMember(removeMember);
    }


    @OnClick(R.id.fmCommunityMembersClose)
    public void communityClosePress()
    {
        mHomeActivityIntractionListner.closeRequestFragment();
    }
   /* @Override
    public void getAllCommentsAndReactions(List<CommentsList> data) {
        if(mAdapter!=null) {
            mTvUserCommentHeaderText.setText(getString(R.string.ID_REPLIES)+getString(R.string.ID_OPEN_BRACKET)+String.valueOf(data.size())+getString(R.string.ID_CLOSE_BRACKET));
            mAdapter.setSheroesGenericListData(data);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getAllReactions(List<ReactionList> data) {
        if(mAdapter!=null) {
            mTvUserCommentHeaderText.setText(getString(R.string.ID_REACTION)+getString(R.string.ID_OPEN_BRACKET)+String.valueOf(data.size())+getString(R.string.ID_CLOSE_BRACKET));
            mAdapter.setSheroesGenericListData(data);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showNwError() {
        mHomeActivityIntractionListner.onErrorOccurence();
    }


    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();
    }
*/

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
        requestedPresenter.detachView();
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
    public void getAllRequest(List<PandingMember> data) {
        mAdapter.setSheroesGenericListData(data);
        mAdapter.notifyDataSetChanged();
        if(null !=data) {
            pandingListData=data;
            mFragmentListRefreshData.setPageNo(mFragmentListRefreshData.getPageNo()+1);
        }
    }

    @Override
    public void removePandingMember(String members) {

        pandingListData.remove(position);
        mAdapter.setSheroesGenericListData(pandingListData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void approvePandingMember(String members) {
        Toast.makeText(getActivity(), members, Toast.LENGTH_SHORT).show();
        pandingListData.remove(position);
        mAdapter.setSheroesGenericListData(pandingListData);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void showNwError() {

    }


    @Override
    public void startNextScreen() {

    }


    public interface RequestHomeActivityIntractionListner {
        void onErrorOccurence();
        void closeRequestFragment();
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