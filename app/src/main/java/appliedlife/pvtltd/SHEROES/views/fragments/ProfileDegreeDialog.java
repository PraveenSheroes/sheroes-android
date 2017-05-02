package appliedlife.pvtltd.SHEROES.views.fragments;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingInterestJobSearch;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.GetInterestJobResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.presenters.OnBoardingPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.OnBoardingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SHEROES-TECH on 16-04-2017.
 */

public class ProfileDegreeDialog extends BaseDialogFragment implements OnBoardingView {
    private final String TAG = LogUtils.makeLogTag(OnBoardingSearchDialogFragment.class);
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.rv_onboarding_search_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.et_search_edit_text)
    public EditText mSearchEditText;
    @Inject
    OnBoardingPresenter mOnBoardingPresenter;
    @Bind(R.id.pb_onboarding_search_progress_bar)
    ProgressBar mProgressBar;
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    private GenericRecyclerViewAdapter mAdapter;
    private Handler mHandler = new Handler();
    private String mMasterDataSkill = AppConstants.EMPTY_STRING;
    OnBoardingEnum SEARCH_TYPE = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.rv_onboarding_search, container, false);
        ButterKnife.bind(this, view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (null != getArguments()) {
            mMasterDataSkill = getArguments().getString(AppConstants.DEGREE_KEY);
            SEARCH_TYPE = (OnBoardingEnum) getArguments().getSerializable(AppConstants.BOARDING_SEARCH);
        }
        mOnBoardingPresenter.attachView(this);
        editTextWatcher();
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), (ProfileActicity) getActivity());

        mSearchEditText.setHint(getString(R.string.ID_SEARCH_LOCATION));
        mOnBoardingPresenter.getOnBoardingSearchToPresenter(mAppUtils.onBoardingSearchRequestBuilder("B Tech", mMasterDataSkill));
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
                mMasterDataSkill = AppConstants.DEGREE_KEY;
                mSearchDataName = mSearchDataName.trim().replaceAll(AppConstants.SPACE, AppConstants.EMPTY_STRING);
                mOnBoardingPresenter.getOnBoardingSearchToPresenter(mAppUtils.onBoardingSearchRequestBuilder(mSearchDataName, mMasterDataSkill));



            }
        }
    };

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }

    @Override
    public void getAllDataResponse(GetAllData getAllData) {
        if (null != getAllData) {

            List<GetAllDataDocument> getAllDataDocuments = getAllData.getGetAllDataDocuments();
            GetAllDataDocument getAllDataDocument=new GetAllDataDocument();
            getAllDataDocument.setId("0");
            getAllDataDocument.setTitle(mSearchEditText.getText().toString());
            getAllDataDocument.setCategory(mSearchEditText.getText().toString());
            getAllDataDocuments.add(0,getAllDataDocument);
            if (StringUtil.isNotEmptyCollection(getAllDataDocuments)) {
                mAdapter.setSheroesGenericListData(getAllDataDocuments);
                mAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void getIntersetJobResponse(GetInterestJobResponse getInterestJobResponse) {
        if (null != getInterestJobResponse) {

            List<BoardingInterestJobSearch> getAllDataDocuments = getInterestJobResponse.getGetBoardingInterestJobSearches();
            if (StringUtil.isNotEmptyCollection(getAllDataDocuments)) {
                switch (SEARCH_TYPE) {
                    case INTEREST_SEARCH:
                        mAdapter.setSheroesGenericListData(getAllDataDocuments);
                        mAdapter.setCallForRecycler(AppConstants.FEED_SUB_TYPE);
                        break;
                    case JOB_AT_SEARCH:
                        mAdapter.setSheroesGenericListData(getAllDataDocuments);
                        break;
                    default:
                        LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + SEARCH_TYPE);

                }
                mAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void getBoardingJobResponse(BoardingDataResponse boardingDataResponse) {

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
}