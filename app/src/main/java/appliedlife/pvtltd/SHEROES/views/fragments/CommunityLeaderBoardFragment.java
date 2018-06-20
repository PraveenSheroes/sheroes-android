package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.post.Winner;
import appliedlife.pvtltd.SHEROES.presenters.ContestWinnerPresenterImpl;
import appliedlife.pvtltd.SHEROES.views.adapters.LeaderBoardListAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.EmptyRecyclerView;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.BadgeDetailsDialogFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ravi on 11/06/18.
 */

public class CommunityLeaderBoardFragment extends BaseFragment implements LeaderBoardListAdapter.OnItemClickListener {
    private static final String SCREEN_LABEL = "Contest Winner Fragment";

    @Inject
    ContestWinnerPresenterImpl mContestWinnerPresenter;

    //region view variable
    @Bind(R.id.winner_list)
    EmptyRecyclerView mRecyclerView;



    @Bind(R.id.empty_view)
    View emptyView;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;
    //endregion

    BottomSheetBehavior sheetBehavior;

    //region private methods
    private LeaderBoardListAdapter mWinnerListAdapter;
    //private Contest mContest;
    //endregion

    //region Fragment lifecycle methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_leaderbaord, container, false);
        ButterKnife.bind(this, view);

        Parcelable parcelable = null;
        if (getArguments() != null) {
            if (getArguments().getParcelable(Contest.CONTEST_OBJ) != null) {
                parcelable = getArguments().getParcelable(Contest.CONTEST_OBJ);
            }
        } else {
            if (getActivity().getIntent() != null) {
                parcelable = getActivity().getIntent().getParcelableExtra(Contest.CONTEST_OBJ);
            }
        }

        mWinnerListAdapter = new LeaderBoardListAdapter(getContext(), this);

        List<Winner> data = new ArrayList<>();
        for(int i=0; i<10; i++) {
            Winner winner = new Winner();
            data.add(winner);
        }

        mWinnerListAdapter.setData(data);

        SheroesApplication.getAppComponent(getActivity()).inject(this);
       // mContestWinnerPresenter.attachView(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
     //   mRecyclerView.setEmptyViewWithImage(emptyView, getActivity().getResources().getString(R.string.empty_winner_text), R.drawable.vector_empty_winner, getActivity().getResources().getString(R.string.empty_winner_subtext, getDateString(mContest.winnerAnnouncementDate)));
        mRecyclerView.setAdapter(mWinnerListAdapter);
       // mContestWinnerPresenter.fetchWinners(Integer.toString(mContest.remote_id));

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
        return mContestWinnerPresenter;
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
    public void onItemClick(Winner item) {
        //Open BadgeDetail dialog
        if(getActivity() ==null || getActivity().isFinishing()) return;
        BadgeDetailsDialogFragment.showDialog(getActivity(), BadgeDetailsDialogFragment.SCREEN_NAME, true);
    }
}
