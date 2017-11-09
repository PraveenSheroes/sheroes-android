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

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ujjwal on 01/05/17.
 */

public class ContestWinnerFragment extends BaseFragment /*implements IContestWinnerView*/ {
    private static final String SCREEN_LABEL = "Contest Winner Fragment";

    //region view variable
/*    @Bind(R.id.winner_list)
    EmptyRecyclerView mRecyclerView;

    @Bind(R.id.empty_view)
    View emptyView;
    //endregion

    //region private methods
    private IContestWinnerPresenter mContentWinnerPresenter;
    private WinnerListAdapter mWinnerListAdapter;
    private Contest mContest;*/
    //endregion

    //region Fragment lifecycle methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       /* View view = inflater.inflate(R.layout.framgment_contest_winner, container, false);
        unbinder = ButterKnife.bind(this, view);

        Parcelable parcelable = getActivity().getIntent().getParcelableExtra(Contest.CONTEST_OBJ);
        if (parcelable != null) {
            mContest = Parcels.unwrap(parcelable);
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setEmptyViewWithImage(emptyView, getActivity().getResources().getString(R.string.empty_winner_text, getDateString(mContest.winnerAnnouncementDate)), R.drawable.winner_empty, getActivity().getResources().getString(R.string.empty_winner_subtext));
        if (CommonUtil.getContestStatus(mContest.startAt, mContest.endAt) == ContestStatus.COMPLETED) {
            if (!mContest.hasMyPost) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 0, 0);
                mRecyclerView.setLayoutParams(params);
            }
        }
        initPresenter();
        initAdapter();

        mContentWinnerPresenter.fetchPrizes(mContest.remote_id);

        return view;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    protected IPresenter getPresenter() {
        return mContentWinnerPresenter;
    }
    //endregion

    //region IContestWinnerView
    @Override
    public void showPrizes(List<Prize> prizeList) {
        mWinnerListAdapter.setData(prizeList);
    }

    @Override
    public void navigateToUserProfile(User user) {
        UserProfileActivity.navigateTo(getActivity(), user, getScreenName());
    }
    //endregion

    //region private methods
    private void initPresenter() {
        mContentWinnerPresenter = PresenterFactory.createContentWinnerPresenter(this);
    }

    private String getDateString(Date winnerAnnouncementDate) {
        if (winnerAnnouncementDate == null) {
            return this.getResources().getString(R.string.soon);
        } else {
            return "on" + " " + DateUtil.toPrettyDateWithoutTime(mContest.winnerAnnouncementDate);
        }
    }

    private void initAdapter() {
        mWinnerListAdapter = new WinnerListAdapter(getActivity(), mContentWinnerPresenter);
        mRecyclerView.setAdapter(mWinnerListAdapter);
    }
    //endregion

    //region static methods
    public static Fragment instance() {
        return new ContestWinnerFragment();
    }
    //endregion*/
       return null;
    }

    @Override
    public String getScreenName() {
        return null;
    }
}
