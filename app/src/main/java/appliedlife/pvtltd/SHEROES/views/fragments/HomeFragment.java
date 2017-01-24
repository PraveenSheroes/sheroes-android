package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.MockService;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ListOfFeed;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Home fragment within Home activity perform all the UI operation .
 * Fragment will have all UI components and operate with activity .
 */
public class HomeFragment extends BaseFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(HomeFragment.class);
    private final String SCREEN_NAME = "Home Screen";
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_home_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_home_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.swipe_view_home)
    SwipeRefreshLayout swipeView;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private HomeActivityIntractionListner mHomeActivityIntractionListner;
    private SwipPullRefreshList mPullRefreshList;
    public static HomeFragment createInstance(int itemsCount) {
        HomeFragment partThreeFragment = new HomeFragment();
        // Bundle bundle = new Bundle();
        //  bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        //   partThreeFragment.setArguments(bundle);
        return partThreeFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof HomeActivityIntractionListner) {
                mHomeActivityIntractionListner = (HomeActivityIntractionListner) getActivity();
            }
        } catch (Fragment.InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Fabric.with(getActivity(), new Crashlytics());
     //   forceCrash();
        // TODO: Move this to where you establish a user session
        logUser();

        //  GoogleAnalyticsTracing.screenNameTracking(getActivity(),SCREEN_NAME);

        ButterKnife.bind(this, view);
        mPullRefreshList=new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mHomePresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLayoutManager) {
            @Override
            public void onHide() {
                // setCustomAnimation(((HomeActivity)getActivity()).mFlHomeFooterList);
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onShow() {
                //   setCustomAnimation(((HomeActivity)getActivity()).mFlHomeFooterList);
                ((HomeActivity) getActivity()).mFlHomeFooterList.setVisibility(View.VISIBLE);
            }
        });
        mHomePresenter.getFeedFromPresenter(MockService.makeCityRequest());
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                LogUtils.info("swipe", "*****************end called");
              //  mPullRefreshList.setPullToRefresh(true);
             //   mHomePresenter.getFeedFromPresenter(MockService.makeCityRequest());
            }
        });
        return view;
    }

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("ajit@sheroies.in");
        Crashlytics.setUserName("Test User");
    }
    public void forceCrash() {
        throw new RuntimeException(AppConstants.APP_CRASHED);
    }

    private void setCustomAnimation(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        viewToAnimate.startAnimation(animation);
    }

    @Override
    public void getCityListSuccess(List<ListOfFeed> listOfFeeds) {
        if(StringUtil.isNotEmptyCollection(listOfFeeds)) {
            mPullRefreshList.allListData(listOfFeeds);
            mAdapter.setSheroesGenericListData(mPullRefreshList.getFeedResponses());
            mAdapter.notifyDataSetChanged();
            if(!mPullRefreshList.isPullToRefresh()) {
                mLayoutManager.scrollToPositionWithOffset(mPullRefreshList.getFeedResponses().size() - listOfFeeds.size(), 0);
            }
            else
            {
                mLayoutManager.scrollToPositionWithOffset(0,0);
            }
            swipeView.setRefreshing(false);
        }
    }

    @Override
    public void getHomeSpinnerListSuccess(List<HomeSpinnerItem> data) {

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

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMsg) {
        mAdapter.notifyDataSetChanged();
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

    public interface HomeActivityIntractionListner {
        void onErrorOccurence();
    }

}
