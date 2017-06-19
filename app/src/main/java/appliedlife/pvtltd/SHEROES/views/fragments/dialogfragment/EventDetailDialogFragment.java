package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;

import java.util.ArrayList;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.EventDetailPojo;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 16-06-2017.
 */

public class EventDetailDialogFragment extends BaseDialogFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(EventDetailDialogFragment.class);
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.pb_event_detail_progress_bar)
    ProgressBar mProgressBar;
    FeedDetail mFeedDetail;
    @Inject
    HomePresenter mHomePresenter;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    @Bind(R.id.rv_event_detail_list)
    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.event_detail_dialog_fragment, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            Bundle bundle = getArguments();
            mFeedDetail = bundle.getParcelable(AppConstants.SUCCESS);
            if (null != mFeedDetail) {
                mHomePresenter.attachView(this);
                mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new GenericRecyclerViewAdapter(getActivity(), (HomeActivity) getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                mHomePresenter.getEventDataFromPresenter(mAppUtils.eventRequestBuilder(mFeedDetail.getIdOfEntityOrParticipant()));
            }
        }
        return view;
    }

    @Override
    public void getEventListResponse(EventDetailPojo eventDetailPojo) {
        if (null != eventDetailPojo) {
            ArrayList<EventDetailPojo> eventDetailPojoArrayList = new ArrayList<>();
            eventDetailPojoArrayList.add(eventDetailPojo);
            mAdapter.setSheroesGenericListData(eventDetailPojoArrayList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void startProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.bringToFront();
        }
    }

    @Override
    public void stopProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth) {
            @Override
            public void onBackPressed() {
                dismissAllowingStateLoss();//dismiss dialog on back button press
                dismiss();
            }
        };
    }
}
