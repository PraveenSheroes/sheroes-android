package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.community.LeaderBoardUserDetail;
import appliedlife.pvtltd.SHEROES.presenters.CommunityLeaderBoardPresenterImpl;
import appliedlife.pvtltd.SHEROES.views.adapters.LeaderBoardListAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.EmptyRecyclerView;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.BadgeDetailsDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunityLeaderBoardUser;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ravi on 11/06/18.
 */

public class CommunityLeaderBoardFragment extends BaseFragment implements LeaderBoardListAdapter.OnItemClickListener, ICommunityLeaderBoardUser {
    private static final String SCREEN_LABEL = "Contest Winner Fragment";
    public static final String COMMUNITY_ID = "Community_id";

    @Inject
    CommunityLeaderBoardPresenterImpl mLeaderBoardPresenter;

    //region view variable
    @Bind(R.id.winner_list)
    EmptyRecyclerView mRecyclerView;

    @Bind(R.id.empty_view)
    View emptyView;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;
    //endregion

    //region private methods
    private LeaderBoardListAdapter mLeaderBoardAdapter;
    //private Contest mContest;
    //endregion

    //region Fragment lifecycle methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_leaderbaord, container, false);
        ButterKnife.bind(this, view);
        SheroesApplication.getAppComponent(getActivity()).inject(this);

        if (getArguments() != null) {
            mLeaderBoardPresenter.attachView(this);

            mLeaderBoardAdapter = new LeaderBoardListAdapter(getContext(), this);

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mLeaderBoardAdapter);

            int communityId = getArguments().getInt(CommunityLeaderBoardFragment.COMMUNITY_ID);
          //  mLeaderBoardPresenter.fetchLeaderBoardUsers(communityId);

        }




        return view;
    }

    //endregion

    //region IContestWinnerView

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            AnalyticsManager.trackScreenView(getScreenName(), getExtraProperties());
        }
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    //endregion

    //region private methods

    @Override
    public void startProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void stopProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * manually opening / closing bottom sheet on button click
     */
    @OnClick(R.id.about_leaderboard)
    public void toggleLeaderBoardBottomSheet() {
        if(getContext()!=null) {
            LeaderBoardBottomSheetFragment.showDialog((AppCompatActivity) getContext());
        }
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mLeaderBoardPresenter;
    }
    //endregion

    //region static methods
    public static CommunityLeaderBoardFragment getInstance() {
        return new CommunityLeaderBoardFragment();
    }
    //endregion

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void onItemClick(LeaderBoardUserDetail item) {
        //Open BadgeDetail dialog
        if(getActivity() ==null || getActivity().isFinishing()) return;
        BadgeDetailsDialogFragment.showDialog(getActivity(), BadgeDetailsDialogFragment.SCREEN_NAME, true);
    }

    @Override
    public void onProfilePicClick(LeaderBoardUserDetail item) {
        //Open Profile dialog
        if(getActivity() ==null || getActivity().isFinishing()) return;
        BadgeDetailsDialogFragment.showDialog(getActivity(), BadgeDetailsDialogFragment.SCREEN_NAME, true);
    }


    @Override
    public void showUsersInLeaderBoard(List<LeaderBoardUserDetail> leaderBoardUserDetails) {
        mLeaderBoardAdapter.setData(leaderBoardUserDetails);
    }
}
