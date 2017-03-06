package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeSearchActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 03-02-2017.
 */

public class SearchRecentFragment extends BaseFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(SearchRecentFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.rv_search_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_search_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.li_no_search_result)
    LinearLayout liNoSearchResult;
    @Bind(R.id.tv_search_result)
    TextView tvSearchResult;
    private GenericRecyclerViewAdapter mAdapter;
    private FragmentIntractionWithActivityListner mHomeSearchActivityFragmentIntractionWithActivityListner;
    private List<RecentSearchData> recentSearchDatas;

    public static SearchRecentFragment createInstance() {
        SearchRecentFragment searchRecentFragment = new SearchRecentFragment();
        return searchRecentFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof FragmentIntractionWithActivityListner) {
                mHomeSearchActivityFragmentIntractionWithActivityListner = (FragmentIntractionWithActivityListner) getActivity();
            }
        } catch (Fragment.InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        mHomePresenter.attachView(this);
        mHomePresenter.fetchMasterDataTypes();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeSearchActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        liNoSearchResult.setVisibility(View.VISIBLE);
        return view;
    }


    @Override
    public void getFeedListSuccess(List<FeedDetail> feedDetailList) {
    }

    @Override
    public void getSuccessForAllResponse(String success, int successFrom) {

    }

    @Override
    public void getDB(List<RecentSearchData> recentSearchDatas) {
        this.recentSearchDatas = recentSearchDatas;
    }

    public void updateUiAfterSwip() {
        if (StringUtil.isNotEmptyCollection(recentSearchDatas)) {
            List<FeedDetail> feedDetailList = new ArrayList<>();
            for (RecentSearchData master : recentSearchDatas) {
                Gson gson = new Gson();
                FeedDetail feedObject = gson.fromJson(master.getRecentSearchFeed(), FeedDetail.class);
                feedDetailList.add(feedObject);
            }
            if (StringUtil.isNotEmptyCollection(feedDetailList) && mAdapter != null) {
                liNoSearchResult.setVisibility(View.GONE);
                mAdapter.setCallForRecycler(AppConstants.ALL_SEARCH);
                mAdapter.setSheroesGenericListData(feedDetailList);
                mAdapter.notifyDataSetChanged();

            } else {
                liNoSearchResult.setVisibility(View.VISIBLE);
                tvSearchResult.setText(getString(R.string.ID_NO_RESULT_FOUND));
            }
        } else {
            liNoSearchResult.setVisibility(View.VISIBLE);
            tvSearchResult.setText(getString(R.string.ID_NO_RESULT_FOUND));
        }
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
        mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog();
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