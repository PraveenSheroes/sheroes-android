package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.parceler.Parcels;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.post.Prize;
import appliedlife.pvtltd.SHEROES.presenters.ContestWinnerPresenterImpl;
import appliedlife.pvtltd.SHEROES.presenters.CreatePostPresenter;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.ContestStatus;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.WinnerListAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.EmptyRecyclerView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IContestWinnerView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ujjwal on 10/10/17.
 */

public class ContestWinnerFragment extends BaseFragment implements IContestWinnerView {
    private static final String SCREEN_LABEL = "Contest Winner Fragment";

    @Inject
    ContestWinnerPresenterImpl mContestWinnerPresenter;

    //region view variable
    @Bind(R.id.winner_list)
    EmptyRecyclerView mRecyclerView;

    @Bind(R.id.empty_view)
    View emptyView;
    //endregion

    //region private methods
    private WinnerListAdapter mWinnerListAdapter;
    private Contest mContest;
    //endregion

    //region Fragment lifecycle methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framgment_contest_winner, container, false);
        ButterKnife.bind(this, view);
        Parcelable parcelable = getActivity().getIntent().getParcelableExtra(Contest.CONTEST_OBJ);
        if (parcelable != null) {
            mContest = Parcels.unwrap(parcelable);
        }

        SheroesApplication.getAppComponent(getActivity()).inject(this);
        mContestWinnerPresenter.attachView(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setEmptyViewWithImage(emptyView, getActivity().getResources().getString(R.string.empty_winner_text, getDateString(mContest.winnerAnnouncementDate)), R.drawable.vector_empty_winner, getActivity().getResources().getString(R.string.empty_winner_subtext));
        if (CommonUtil.getContestStatus(mContest.getStartAt(), mContest.getEndAt()) == ContestStatus.COMPLETED) {
            if (!mContest.hasMyPost) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 0, 0);
                mRecyclerView.setLayoutParams(params);
            }
        }
        initAdapter();

        mContestWinnerPresenter.fetchPrizes(Integer.toString(mContest.remote_id));

        return view;
    }

    //endregion

    //region IContestWinnerView
    @Override
    public void showPrizes(List<Prize> prizeList) {
        mWinnerListAdapter.setData(prizeList);
    }

    //endregion

    //region private methods

    private String getDateString(Date winnerAnnouncementDate) {
        if (winnerAnnouncementDate == null) {
            return this.getResources().getString(R.string.soon);
        } else {
            return "on" + " " + DateUtil.toPrettyDateWithoutTime(mContest.winnerAnnouncementDate);
        }
    }

    private void initAdapter() {
        mWinnerListAdapter = new WinnerListAdapter(getActivity());
        mRecyclerView.setAdapter(mWinnerListAdapter);
    }
    //endregion

    //region static methods
    public static Fragment instance() {
        return new ContestWinnerFragment();
    }
    //endregion

    @Override
    public String getScreenName() {
        return null;
    }
}
