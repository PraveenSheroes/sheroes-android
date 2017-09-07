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

import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_FEED_RESPONSE;

/**
 * Created by Ajit Kumar on 17-04-2017.
 */

public class BellNotificationDialogFragment extends BaseDialogFragment implements HomeView {
    private FragmentListRefreshData mFragmentListRefreshData;
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
    private MoEHelper mMoEHelper;
    private MoEngageUtills moEngageUtills;
    private PayloadBuilder payloadBuilder;
    private long startedTime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View v = inflater.inflate(R.layout.community_bell_notification_list, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, v);
        mMoEHelper = MoEHelper.getInstance(getActivity());
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        startedTime=System.currentTimeMillis();

        tvTitle.setText(getString(R.string.ID_NOTIFICATION));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), (HomeActivity) getActivity());
        mLinearLayoutmanager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutmanager);
        mRecyclerView.setAdapter(mAdapter);
        mHomePresenter.attachView(this);
        mHomePresenter.getBellNotificationFromPresenter(mAppUtils.getBellNotificationRequest());
        long timeSpent=System.currentTimeMillis()-startedTime;
        moEngageUtills.entityMoEngageNotification(getActivity(),mMoEHelper,payloadBuilder,timeSpent);
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_NOTIFICATION_SCREEN));
        return v;
        }

    @Override
    public void getNotificationListSuccess(BelNotificationListResponse bellNotificationResponse) {
        switch (bellNotificationResponse.getStatus()) {
            case AppConstants.SUCCESS:
                if (null != bellNotificationResponse.getBellNotificationResponses()) {
                    mAdapter.setSheroesGenericListData(bellNotificationResponse.getBellNotificationResponses());
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case AppConstants.FAILED:
                ((HomeActivity)getActivity()).onShowErrorDialog(bellNotificationResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA),ERROR_FEED_RESPONSE);
                break;
            default:
                ((HomeActivity)getActivity()).onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR),ERROR_FEED_RESPONSE);

        }
    }
    @OnClick(R.id.iv_back_setting)
    public void backClick()
    {
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
        long timeSpent=System.currentTimeMillis()-startedTime;
        moEngageUtills.entityMoEngageNotification(getActivity(),mMoEHelper,payloadBuilder,timeSpent);
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
