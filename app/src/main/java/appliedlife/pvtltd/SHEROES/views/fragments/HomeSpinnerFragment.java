package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Praveen_Singh on 05-01-2017.
 */

public class HomeSpinnerFragment extends BaseFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(HomeSpinnerFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_spinner_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_spinner_progress_bar)
    ProgressBar mProgressBar;
    GenericRecyclerViewAdapter mAdapter;
    private List<HomeSpinnerItem> mHomeSpinnerItemList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            mHomeSpinnerItemList =bundle.getParcelableArrayList(AppConstants.HOME_SPINNER_FRAGMENT);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_home_spinner_layout, container, false);
        ButterKnife.bind(this, view);
        mHomePresenter.attachView(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        checkForSpinnerItemSelection();
        return view;
    }

     private void checkForSpinnerItemSelection()
     {
         if(mHomeSpinnerItemList!=null)
         {

             mAdapter.setSheroesGenericListData(mHomeSpinnerItemList);
         }
     }



    @Override
    public void getFeedListSuccess(List<FeedDetail> feedDetailList) {

    }

    @Override
    public void getSuccessForAllResponse(String success, int successFrom) {

    }


    @Override
    public void getDB(List<RecentSearchData> recentSearchDatas) {

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
    public void showError(String errorMsg) {
    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
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

}