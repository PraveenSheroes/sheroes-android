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

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.Member;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.MembersList;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.presenters.MembersPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.AllMembersView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 03-02-2017.
 */

public class AllMembersFragment extends BaseFragment implements AllMembersView {
    private final String TAG = LogUtils.makeLogTag(CommentReactionFragment.class);
    @Inject
    MembersPresenter mmemberpresenter;
    @Bind(R.id.rv_comment_reaction_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_comment_reaction_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.fmCommunityMembersClose)
    FrameLayout fmCommunityMembersClose;
    private FragmentOpen mFragmentOpen;
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    private GenericRecyclerViewAdapter mAdapter;
    private MembersHomeActivityIntractionListner mHomeActivityIntractionListner;
    FeedDetail mFeedDetail;
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
        mmemberpresenter.attachView(this);
        if(null!=getArguments())
        {
            mFeedDetail =getArguments().getParcelable(AppConstants.COMMUNITY_DETAIL);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunitiesDetailActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        MemberRequest memberRequest=new MemberRequest();
        memberRequest.setAppVersion("String");
        memberRequest.setCloudMessagingId("String");
        memberRequest.setCommunityId(mFeedDetail.getIdOfEntityOrParticipant());
        memberRequest.setDeviceUniqueId("String");
        memberRequest.setLastScreenName("String");
        memberRequest.setScreenName("String");
        mmemberpresenter.getAllMembers(memberRequest);



        return view;
    }
    @OnClick(R.id.fmCommunityMembersClose)
    public void communityClosePress()
    {
        mHomeActivityIntractionListner.closeMembersFragment();
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
        mAdapter.setSheroesGenericListData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNwError() {

    }


    @Override
    public void startNextScreen() {

    }



    public interface MembersHomeActivityIntractionListner {
        void onErrorOccurence();
        void closeMembersFragment();
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