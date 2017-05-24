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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.MembersList;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
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

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;

/**
 * Created by Ajit Kumar on 03-02-2017.
 */

public class AllMembersDialogFragment extends BaseDialogFragment implements AllMembersView {
    private final String TAG = LogUtils.makeLogTag(AllMembersDialogFragment.class);
    @Inject
    MembersPresenter mmemberpresenter;
    @Bind(R.id.rv_member_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_member_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.tv_member_count)
    TextView tv_member_count;
    @Inject
    AppUtils mAppUtils;
    LinearLayoutManager manager;
    private FragmentListRefreshData mFragmentListRefreshData;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    private GenericRecyclerViewAdapter mAdapter;
    FeedDetail mFeedDetail;
    List<MembersList> memberdata = new ArrayList<>();
    int position;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.fragment_members, container, false);
        ButterKnife.bind(this, view);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.MEMBER_FRAGMENT, AppConstants.NO_REACTION_CONSTANT);
        mmemberpresenter.attachView(this);
        if (null != getArguments()) {
            mFeedDetail = getArguments().getParcelable(AppConstants.COMMUNITY_DETAIL);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), (CommunitiesDetailActivity) getActivity());
        manager = new LinearLayoutManager(getActivity());
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
        mmemberpresenter.getAllMembers(mAppUtils.getPandingMemberRequestBuilder(mFeedDetail.getIdOfEntityOrParticipant(), mFragmentListRefreshData.getPageNo()));
        mFragmentListRefreshData.setEnitityOrParticpantid(mFeedDetail.getIdOfEntityOrParticipant());
        return view;
    }

    @OnClick(R.id.fm_community_members_close)
    public void communityClosePress() {
        ((CommunitiesDetailActivity) getActivity()).updateOpenAboutFragment(mFeedDetail);
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
            tv_member_count.setText(AppConstants.LEFT_BRACKET + mFeedDetail.getNoOfMembers() + AppConstants.RIGHT_BRACKET);
            mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mAdapter.setSheroesGenericListData(memberdata);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void removeMember(MemberListResponse memberListResponse) {
        switch (memberListResponse.getStatus()) {
            case AppConstants.SUCCESS:
                if (StringUtil.isNotEmptyCollection(memberdata)) {
                    memberdata.remove(position);
                    tv_member_count.setText(AppConstants.LEFT_BRACKET + memberdata.size() + AppConstants.RIGHT_BRACKET);
                    mFeedDetail.setNoOfMembers(memberdata.size());
                    mAdapter.setSheroesGenericListData(memberdata);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case AppConstants.FAILED:
                ((CommunitiesDetailActivity)getActivity()).onShowErrorDialog(memberListResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_JOIN_INVITE);
                break;
            default:
                ((CommunitiesDetailActivity)getActivity()).onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), ERROR_JOIN_INVITE);
        }
    }

    public void callRemoveMember(Long userId, Long communityId, int position) {
        this.position = position;
        mmemberpresenter.leaveCommunityAndRemoveMemberToPresenter(mAppUtils.removeMemberRequestBuilder(communityId,userId));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth) {
            @Override
            public void onBackPressed() {
                communityClosePress();
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