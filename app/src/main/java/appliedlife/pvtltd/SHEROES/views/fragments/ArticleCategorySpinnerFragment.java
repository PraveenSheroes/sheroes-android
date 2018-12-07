package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.ArticleCategory;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 05-01-2017.
 */

public class ArticleCategorySpinnerFragment extends BaseFragment {
    private static final String SCREEN_LABEL = "Article Category Spinner Screen";
    public static final int CATEGORY_SELECTED_DONE = 1;
    public static final int CATEGORY_SELECTED_CANCEL = 0;

    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.rv_spinner_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_spinner_progress_bar)
    ProgressBar mProgressBar;
    GenericRecyclerViewAdapter mAdapter;
    @Bind(R.id.tv_cancel)
    TextView mTvCancel;
    @Bind(R.id.tv_done)
    TextView mTvDone;
    List<ArticleCategory> mArticleCategoryList;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.fragment_home_spinner_layout, container, false);
        ButterKnife.bind(this, view);
        mHomePresenter.attachView(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mArticleCategoryList = Parcels.unwrap(bundle.getParcelable(AppConstants.ARTICLE_CATEGORY_SPINNER_FRAGMENT));
        }
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), (HomeActivity) getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        if (StringUtil.isNotEmptyCollection(mArticleCategoryList)) {
            mAdapter.setSheroesGenericListData(mArticleCategoryList);
            mAdapter.notifyDataSetChanged();
        } else {
            mHomePresenter.getMasterDataToPresenter();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DrawerViewHolder.selectedOptionName = AppConstants.NAV_ARTICLES;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mHomePresenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }

    @OnClick(R.id.tv_cancel)
    public void onCancelClick() {
        if (getActivity() != null && !getActivity().isFinishing() && getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).onCancelDone(CATEGORY_SELECTED_CANCEL);
        }
    }

    @OnClick(R.id.tv_done)
    public void onDoneClick() {
        if (getActivity() != null && !getActivity().isFinishing() && getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).onCancelDone(CATEGORY_SELECTED_DONE);
        }
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
    public void showEmptyScreen(String s) {

    }
}