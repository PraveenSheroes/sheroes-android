package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.GetInterestJobResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.presenters.OnBoardingPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.EditUserProfileActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.OnBoardingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.onBoardingSearchRequestBuilder;

/**
 * Created by Praveen_Singh on 02-05-2017.
 */

public class JobLocationSearchDialogFragment extends BaseDialogFragment implements OnBoardingView {
    private final String TAG = LogUtils.makeLogTag(JobLocationSearchDialogFragment.class);
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.rv_job_loc_search_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.et_search_edit_text)
    public EditText mSearchEditText;
    @Inject
    OnBoardingPresenter mOnBoardingPresenter;
    @Bind(R.id.pb_onboarding_search_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.tv_location_result)
    TextView tvSearchResult;
    @Bind(R.id.tv_save_job_location)
    TextView tvSaveJobLocation;
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    private GenericRecyclerViewAdapter mAdapter;
    private Handler mHandler = new Handler();
    private String mMasterDataSkill = AppConstants.EMPTY_STRING;
    private String sourceName;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.job_location_search_dialog, container, false);
        ButterKnife.bind(this, view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (null != getArguments()) {
            mMasterDataSkill = getArguments().getString(AppConstants.MASTER_SKILL);
            sourceName = getArguments().getString(AppConstants.SOURCE_NAME);
        }
        mOnBoardingPresenter.attachView(this);
        editTextWatcher();
        if (StringUtil.isNotNullOrEmptyString(sourceName) && AppConstants.PROFILE_FRAGMENT.equalsIgnoreCase(sourceName)) {
            mAdapter = new GenericRecyclerViewAdapter(getActivity(), (EditUserProfileActivity) getActivity());
            tvSaveJobLocation.setVisibility(View.GONE);
        } else {
            mAdapter = new GenericRecyclerViewAdapter(getActivity(), (HomeActivity) getActivity());
        }
        mSearchEditText.setHint(getString(R.string.ID_SEARCH_LOCATION));
        mOnBoardingPresenter.getOnBoardingSearchToPresenter(onBoardingSearchRequestBuilder(AppConstants.CITY_NAME_DEFAULT, mMasterDataSkill));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mRecyclerView, manager, new FragmentListRefreshData()) {
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
        setCancelable(true);
        return view;
    }

    @OnClick(R.id.tv_save_job_location)
    public void onSaveJobLocation() {
        ((HomeActivity) getActivity()).saveJobLocation();
    }

    @OnClick(R.id.iv_search_back)
    public void onSearchBack() {
        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mOnBoardingPresenter.detachView();
    }

    /**
     * When user type city name it works for each character.
     */
    protected void editTextWatcher() {
        mSearchEditText.addTextChangedListener(dataSearchTextWatcher());
        mSearchEditText.setFocusableInTouchMode(true);
        mSearchEditText.requestFocus();
    }

    /**
     * Text watcher workes on every character change and make hit for server accordingly.
     */
    private TextWatcher dataSearchTextWatcher() {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable inputSearch) {

                if (StringUtil.isNotNullOrEmptyString(inputSearch.toString()) && inputSearch.toString().length() > AppConstants.THREE_CONSTANT) {
                    mSearchDataName = inputSearch.toString();
                    /**hitting the servers to get data if length is greater than threshold defined **/
                    mHandler.removeCallbacks(mFilterTask);
                    mHandler.postDelayed(mFilterTask, AppConstants.SEARCH_CONSTANT_DELAY);
                }
            }
        };
    }

    /**
     * Runnable use to make network call on every character change while search for city name.
     */
    Runnable mFilterTask = new Runnable() {
        @Override
        public void run() {
            if (!isDetached()) {
                mSearchDataName = mSearchDataName.trim();//.replaceAll(AppConstants.SPACE, AppConstants.EMPTY_STRING);
                if (StringUtil.isNotNullOrEmptyString(mMasterDataSkill)) {
                    mOnBoardingPresenter.getOnBoardingSearchToPresenter(onBoardingSearchRequestBuilder(mSearchDataName, mMasterDataSkill));
                }
            }
        }
    };

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }

    @Override
    public void getAllDataResponse(GetAllData getAllData) {
        if (null != getAllData && StringUtil.isNotEmptyCollection(getAllData.getGetAllDataDocuments())) {
            List<GetAllDataDocument> getAllDataDocuments = getAllData.getGetAllDataDocuments();
            if (StringUtil.isNotEmptyCollection(getAllDataDocuments)) {
                mAdapter.setSheroesGenericListData(getAllDataDocuments);
                mAdapter.setCallForRecycler(AppConstants.FEED_SUB_TYPE);
                mAdapter.notifyDataSetChanged();
            }
            mRecyclerView.setVisibility(View.VISIBLE);
            tvSearchResult.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            tvSearchResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getIntersetJobResponse(GetInterestJobResponse getInterestJobResponse) {

    }

    @Override
    public void getBoardingJobResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void showDataList(List<FeedDetail> feedDetailList) {

    }

    @Override
    public void joinResponse(CommunityFeedSolrObj communityFeedSolrObj) {

    }

    @Override
    public void unJoinResponse(CommunityFeedSolrObj communityFeedSolrObj) {

    }

    @Override
    public void startProgressBar() {
        if(null!=mProgressBar) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.bringToFront();
        }
    }

    @Override
    public void stopProgressBar() {
        if(null!=mProgressBar) {
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
