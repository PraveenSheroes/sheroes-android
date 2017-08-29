package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeSearchActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
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
    LinearLayout mLiNoSearchResult;
    @Bind(R.id.tv_search_result)
    TextView mTvSearchResult;
    private GenericRecyclerViewAdapter mAdapter;


    public static SearchRecentFragment createInstance() {
        SearchRecentFragment searchRecentFragment = new SearchRecentFragment();
        return searchRecentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        mHomePresenter.attachView(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeSearchActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        super.setAllInitializationForFeeds(null,  mAdapter, manager, mRecyclerView, mHomePresenter, mAppUtils, mProgressBar);
        return view;
    }


    public void updateUiAfterSwip() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }


}