package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQSResponse;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMemberListResponse;
import appliedlife.pvtltd.SHEROES.presenters.SHEPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SHEView;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link }
 * interface.
 */
public class ICCMemberListFragment extends BaseFragment implements SHEView {
    private static final String SCREEN_LABEL = "ICC Member List Screen";
    @Inject
    SHEPresenter shePresenter;

    @Bind(R.id.rv_list_icc)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_icc_progress_bar)
    ProgressBar pbIccMemberProgreeBar;

    GenericRecyclerViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_iccmember_list, container, false);
        ButterKnife.bind(this, view);
        shePresenter.attachView(this);
        setProgressBar(pbIccMemberProgreeBar);
        assignNavigationRecyclerListView();

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) ((HomeActivity) getActivity()).mToolbar.getLayoutParams();
        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) ((HomeActivity) getActivity()).mAppBarLayout.getLayoutParams();
        params.setScrollFlags(0);
        appBarLayoutParams.setBehavior(null);
        ((HomeActivity) getActivity()).mAppBarLayout.setLayoutParams(appBarLayoutParams);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DrawerViewHolder.selectedOptionName = AppConstants.NAV_ICC_MEMBERS;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return shePresenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        shePresenter.detachView();

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) ((HomeActivity) getActivity()).mToolbar.getLayoutParams();
        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) ((HomeActivity) getActivity()).mAppBarLayout.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
        ((HomeActivity) getActivity()).mAppBarLayout.setLayoutParams(appBarLayoutParams);
    }

    @Override
    public void getAllFAQs(FAQSResponse faqsResponse) {

    }

    @Override
    public void getAllICCMembers(ICCMemberListResponse iccMemberListResponse){
        if(iccMemberListResponse != null && iccMemberListResponse.getListOfICCMembers()!=null){
            mAdapter.setSheroesGenericListData(iccMemberListResponse.getListOfICCMembers());
            mAdapter.notifyDataSetChanged();
        }
    }

    private void assignNavigationRecyclerListView() {
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        shePresenter.getAllICCMembers(AppUtils.sheICCMemberListRequestBuilder());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {

    }

    @Override
    public void showNotificationList(BelNotificationListResponse bellNotificationResponse) {

    }

    @Override
    public void getNotificationReadCountSuccess(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void onConfigFetched() {

    }

    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }
}
