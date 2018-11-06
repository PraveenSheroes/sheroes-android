package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.BellNotificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;

/**
 * Created by Ajit Kumar on 17-04-2017.
 */

public class BellNotificationDialogFragment extends BaseDialogFragment implements HomeView {
    private static final String SCREEN_LABEL = "Notifications Screen";
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_bell_notification_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar mProgressBar;
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.tv_setting_tittle)
    TextView tvTitle;
    @Bind(R.id.iv_back_setting)
    ImageView ivBackNotification;
    private GenericRecyclerViewAdapter mAdapter;
    LinearLayoutManager mLinearLayoutmanager;
    private SwipPullRefreshList mPullRefreshList;
    private FragmentListRefreshData mFragmentListRefreshData;
    private int mPageNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View v = inflater.inflate(R.layout.community_bell_notification_list, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, v);
        mHomePresenter.attachView(this);
        tvTitle.setText(getString(R.string.ID_NOTIFICATION));
        bellNotificationListPagination();
        AnalyticsManager.trackScreenView(SCREEN_LABEL);
        return v;
    }

    private void bellNotificationListPagination() {
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.BELL_NOTIFICATION_LISTING, AppConstants.NO_REACTION_CONSTANT);
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), (HomeActivity) getActivity());
        mLinearLayoutmanager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutmanager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, mLinearLayoutmanager, mFragmentListRefreshData) {
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
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        startProgressBar();
        mHomePresenter.getBellNotificationFromPresenter(mAppUtils.getBellNotificationRequest());

    }

    @Override
    public void onStart() {
        super.onStart();
        // safety check
        if (getDialog() == null) {
            return;
        }
        // set the animations to use on showing and hiding the dialog
    }

    @Override
    public void showNotificationList(BelNotificationListResponse bellNotificationResponse) {
        switch (bellNotificationResponse.getStatus()) {
            case AppConstants.SUCCESS:
                List<BellNotificationResponse> bellNotificationResponseList = bellNotificationResponse.getBellNotificationResponses();
                if (StringUtil.isNotEmptyCollection(bellNotificationResponseList)) {
                    stopProgressBar();
                    mPageNo = mFragmentListRefreshData.getPageNo();
                    mFragmentListRefreshData.setPageNo(++mPageNo);
                    mPullRefreshList.allListData(bellNotificationResponseList);
                    List<FeedDetail> data = null;
                    FeedDetail feedProgressBar = new FeedDetail();
                    feedProgressBar.setSubType(AppConstants.FEED_PROGRESS_BAR);
                    data = mPullRefreshList.getFeedResponses();
                    int position = data.size() - bellNotificationResponseList.size();
                    if (position > 0) {
                        data.remove(position - 1);
                    }
                    data.add(feedProgressBar);
                    mAdapter.setSheroesGenericListData(data);
                    mAdapter.notifyDataSetChanged();
                } else if (StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses()) && mAdapter != null) {
                    List<FeedDetail> data = mPullRefreshList.getFeedResponses();
                    data.remove(data.size() - 1);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case AppConstants.FAILED:
                ((HomeActivity) getActivity()).onShowErrorDialog(bellNotificationResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_FEED_RESPONSE);
                break;
            default:
                ((HomeActivity) getActivity()).onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), ERROR_FEED_RESPONSE);

        }
    }

    @OnClick(R.id.iv_back_setting)
    public void backClick() {
        dismiss();
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
        mHomePresenter.detachView();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth) {
            @Override
            public void onBackPressed() {
                backClick();
            }
        };
    }
}
