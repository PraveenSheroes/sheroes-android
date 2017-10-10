package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineChatDoc;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadResponse;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.presenters.HelplinePresenter;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.helplineGetChatThreadRequestBuilder;

/**
 * Created by Deepak on 19-05-2017.
 */

public class HelplineFragment extends BaseFragment {
    private static final String SCREEN_LABEL = "Helpline Screen";
    private final String TAG = LogUtils.makeLogTag(HelplineFragment.class);
    @Inject
    HelplinePresenter mHelplinePresenter;
    @Bind(R.id.rv_chat_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_chat_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.swipe_view_chat)
    SwipeRefreshLayout mSwipeView;
    @Bind(R.id.et_question_chat)
    EditText questionText;
    @Bind(R.id.btn_chat_send)
    Button sendChatButton;
    private FragmentListRefreshData mFragmentListRefreshData;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipPullRefreshList mPullRefreshList;
    private AppUtils mAppUtils;
    private boolean mListLoad = true;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    private long startedTime;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.helpline_fragment, container, false);
        ButterKnife.bind(this, view);
        mAppUtils = AppUtils.getInstance();
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.HELPLINE_FRAGMENT, AppConstants.NO_REACTION_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mHelplinePresenter.attachView(this);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) ((HomeActivity) getActivity()).mToolbar.getLayoutParams();
        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) ((HomeActivity) getActivity()).mAppBarLayout.getLayoutParams();
        params.setScrollFlags(0);
        appBarLayoutParams.setBehavior(null);
        ((HomeActivity) getActivity()).mAppBarLayout.setLayoutParams(appBarLayoutParams);

        setUpRecyclerView();
        mSwipeView.setRefreshing(false);
        mSwipeView.setEnabled(false);
        ((HomeActivity)getActivity()).changeFragmentWithCommunities();
        ((HomeActivity)getActivity()).helplineUi();
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_HELP_FRAGMENT));
        return view;
    }

    @OnClick(R.id.btn_chat_send)
    public void sendQuestionText() {
        String text = questionText.getText().toString();
        if (StringUtil.isNotNullOrEmptyString(text)) {
            text = text.trim();
            sendChatButton.setEnabled(false);
            mHelplinePresenter.postQuestionHelpline(AppUtils.helplineQuestionBuilder(text));
        } else {
            Toast.makeText(getContext(), AppConstants.HELPlINE_NO_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_MESSAGE, GoogleAnalyticsEventActions.SENT_A_HELPLINE_MESSAGE, AppConstants.EMPTY_STRING);
    }

    @OnClick(R.id.btn_chat_voice)
    public void speechToText() {
        promptSpeechInput();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) ((HomeActivity) getActivity()).mToolbar.getLayoutParams();
        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) ((HomeActivity) getActivity()).mAppBarLayout.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
        ((HomeActivity) getActivity()).mAppBarLayout.setLayoutParams(appBarLayoutParams);
        mHelplinePresenter.detachView();
        long timeSpent = System.currentTimeMillis() - startedTime;
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.ID_SPEECH_PROMPT));
        startActivityForResult(intent, AppConstants.REQ_CODE_SPEECH_INPUT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case AppConstants.REQ_CODE_SPEECH_INPUT: {

                if (resultCode == Activity.RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    questionText.append(result.get(0));
                    questionText.setSelection(questionText.getText().length());
                }
                break;
            }

        }
    }

    private void refreshChatMethod() {
        mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        setRefreshList(mPullRefreshList);
        mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
        setUpRecyclerView();

    }

    @Override
    public void getHelpChatThreadSuccess(HelplineGetChatThreadResponse helplineGetChatThreadResponse) {
        List<HelplineChatDoc> chatDocsList = helplineGetChatThreadResponse.getHelplineChatDocs();
        if (StringUtil.isNotEmptyCollection(chatDocsList)) {
            mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(chatDocsList);
            mAdapter.setSheroesGenericListData(mPullRefreshList.getFeedResponses());
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
            mSwipeView.setRefreshing(false);
            mSwipeView.setEnabled(false);
            mAdapter.notifyDataSetChanged();
            if (!mPullRefreshList.isPullToRefresh()) {
                mLayoutManager.scrollToPositionWithOffset(mPullRefreshList.getFeedResponses().size() - chatDocsList.size(), 0);
            } else {
                mLayoutManager.scrollToPositionWithOffset(0, 0);
            }
        }

    }

    @Override
    public void getPostQuestionSuccess(HelplinePostQuestionResponse helplinePostQuestionResponse) {
        sendChatButton.setEnabled(true);
        if (helplinePostQuestionResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
            trackEvent(Event.HELPLINE_MESSAGE_CREATED, null);
            questionText.setText(AppConstants.EMPTY_STRING);
            AppUtils.hideKeyboard(getView(), TAG);
            refreshChatMethod();
        }
    }

    public void getSpeechText(String questionBySpeech) {
        questionText.append(questionBySpeech);
        questionText.setSelection(questionText.getText().length());
    }

    public void setUpRecyclerView(){
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.clearOnScrollListeners();
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mHelplinePresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
            @Override
            public void onHide() {
                mListLoad = true;
            }

            @Override
            public void onShow() {
                mListLoad = true;
            }

            @Override
            public void dismissReactions() {

            }
        });
        super.setInitializationForHelpline(mFragmentListRefreshData, mAdapter, mLayoutManager, mRecyclerView, mAppUtils, mProgressBar);
        mHelplinePresenter.getHelplineChatDetails(helplineGetChatThreadRequestBuilder(AppConstants.ONE_CONSTANT));
    }
    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        super.showError(errorMsg,feedParticipationEnum);
        sendChatButton.setEnabled(true);
           }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}

