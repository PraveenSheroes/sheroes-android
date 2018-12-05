package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import appliedlife.pvtltd.SHEROES.presenters.OnBoardingPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.EditUserProfileActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.OnBoardingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sheroes on 10/04/17.
 */

public class SearchProfileLocationDialogFragment extends BaseDialogFragment implements OnBoardingView {

    private final String TAG = LogUtils.makeLogTag(SearchProfileLocationDialogFragment.class);

    private String mSearchDataName = AppConstants.EMPTY_STRING;
    private GenericRecyclerViewAdapter mAdapter;
    private Handler mHandler = new Handler();
    private String mMasterDataSkill = AppConstants.EMPTY_STRING;
    @Inject
    AppUtils mAppUtils;

    @Bind(R.id.title_toolbar)
    TextView toolbarTitle;

    @Inject
    OnBoardingPresenter mOnBoardingPresenter;

    @Bind(R.id.rv_onboarding_search_list)
    RecyclerView mRecyclerView;

    @Bind(R.id.et_search_edit_text)
    public EditText mSearchEditText;

    @Bind(R.id.pb_onboarding_search_progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.rv_onboarding_search, container, false);
        ButterKnife.bind(this, view);
        setupToolbarItemsColor();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (null != getArguments()) {
            mMasterDataSkill = getArguments().getString(AppConstants.MASTER_SKILL);
        }

        if(mMasterDataSkill ==null) {
            mMasterDataSkill = "city";
        }
        mOnBoardingPresenter.attachView(this);
        editTextWatcher();
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), (EditUserProfileActivity) getActivity());

                mSearchEditText.setHint(getString(R.string.ID_SEARCH_LOCATION));
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
    private void setupToolbarItemsColor() {
        ((EditUserProfileActivity)getActivity()).setSupportActionBar(mToolbar);
        ((EditUserProfileActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((EditUserProfileActivity)getActivity()).getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
        ((EditUserProfileActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbarTitle.setText(R.string.ID_SEARCH_LOCATION);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onSearchBack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

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

    @OnClick(R.id.et_search_edit_text)
    public void searchLocation() {
        mOnBoardingPresenter.getOnBoardingSearchToPresenter(AppUtils.onBoardingSearchRequestBuilder("Delhi", mMasterDataSkill));
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
                mMasterDataSkill = "city";
                mSearchDataName = mSearchDataName.trim().replaceAll(AppConstants.SPACE, AppConstants.EMPTY_STRING);
                mOnBoardingPresenter.getOnBoardingSearchToPresenter(AppUtils.onBoardingSearchRequestBuilder(mSearchDataName, mMasterDataSkill));

            }
        }
    };

    @Override
    public void getAllDataResponse(GetAllData getAllData) {
        if (null != getAllData) {
            List<GetAllDataDocument> getAllDataDocuments = getAllData.getGetAllDataDocuments();
            if (StringUtil.isNotEmptyCollection(getAllDataDocuments)) {
                mAdapter.setSheroesGenericListData(getAllDataDocuments);
                mAdapter.notifyDataSetChanged();
            }

        }
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
    public void onConfigFetched() {

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