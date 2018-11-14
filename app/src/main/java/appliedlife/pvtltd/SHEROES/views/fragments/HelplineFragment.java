package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
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
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineChatDoc;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadResponse;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.HelplinePresenter;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HelplineActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HelplineView;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.helplineGetChatThreadRequestBuilder;

/**
 * Created by Deepak on 19-05-2017.
 */

public class HelplineFragment extends BaseFragment implements HelplineView {
    private static final String SCREEN_LABEL = "Helpline Screen";
    private String mSpeechAppPackageName = "com.google.android.googlequicksearchbox";
    private final String TAG = LogUtils.makeLogTag(HelplineFragment.class);
    @Inject
    HelplinePresenter mHelplinePresenter;
    @Bind(R.id.rv_chat_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_chat_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.et_question_chat)
    EditText questionText;
    @Bind(R.id.iv_chat_send)
    ImageView sendChat;
    @Bind(R.id.iv_chat_voice)
    ImageView chatVoice;
    private FragmentListRefreshData mFragmentListRefreshData;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipPullRefreshList mPullRefreshList;
    private AppUtils mAppUtils;
    private boolean mListLoad = true;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    private String sourceScreen ;


    public static HelplineFragment createInstance(String sourceScreen) {
        HelplineFragment helplineFragment = new HelplineFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.SOURCE_NAME, sourceScreen);
        helplineFragment.setArguments(bundle);
        return helplineFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.helpline_fragment, container, false);
        ButterKnife.bind(this, view);
        LocaleManager.setLocale(getContext());
        mAppUtils = AppUtils.getInstance();
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.HELPLINE_FRAGMENT, AppConstants.NO_REACTION_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mHelplinePresenter.attachView(this);
        sendChat.setVisibility(View.GONE);

        if(getArguments()!=null) {
           sourceScreen =  getArguments().getString(AppConstants.SOURCE_NAME);
        }

        if(getActivity() instanceof HomeActivity){
            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) ((HomeActivity) getActivity()).mToolbar.getLayoutParams();
            CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) ((HomeActivity) getActivity()).mAppBarLayout.getLayoutParams();
            params.setScrollFlags(0);
            appBarLayoutParams.setBehavior(null);
            ((HomeActivity) getActivity()).mAppBarLayout.setLayoutParams(appBarLayoutParams);
            ((HomeActivity)getActivity()).changeFragmentWithCommunities();
            ((HomeActivity)getActivity()).helplineUi();
        }

        setUpRecyclerView();
        questionText.addTextChangedListener(editTextWatcher());
        questionText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!keyboardShown(questionText.getRootView())) {
                    if(questionText.getText().toString().length()!=0){
                        sendChat.setVisibility(View.VISIBLE);
                        chatVoice.setVisibility(View.GONE);
                    } else {
                        sendChat.setVisibility(View.GONE);
                        chatVoice.setVisibility(View.VISIBLE);
                    }
                } else {
                    sendChat.setVisibility(View.VISIBLE);
                    chatVoice.setVisibility(View.GONE);
                }
            }
        });
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(AppConstants.HELPLINE);
        return view;
    }

    @OnClick(R.id.iv_chat_send)
    public void sendQuestionText() {
        String text = questionText.getText().toString();
        if (StringUtil.isNotNullOrEmptyString(text)) {
            text = text.trim();
            sendChat.setEnabled(false);
            mHelplinePresenter.postQuestionHelpline(AppUtils.helplineQuestionBuilder(text));
        } else {
            Toast.makeText(getContext(), R.string.helpline_msg, Toast.LENGTH_SHORT).show();
        }
        ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_MESSAGE, GoogleAnalyticsEventActions.SENT_A_HELPLINE_MESSAGE, AppConstants.EMPTY_STRING);
    }

    @OnClick(R.id.iv_chat_voice)
    public void speechToText() {
        promptSpeechInput();
    }

    @Override
    public void onResume() {
        super.onResume();
        DrawerViewHolder.selectedOptionName = AppConstants.NAV_ASK_SHEROES;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(getActivity() instanceof HomeActivity){
            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) ((HomeActivity) getActivity()).mToolbar.getLayoutParams();
            CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) ((HomeActivity) getActivity()).mAppBarLayout.getLayoutParams();
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
            ((HomeActivity) getActivity()).mAppBarLayout.setLayoutParams(appBarLayoutParams);
        }
        mHelplinePresenter.detachView();
    }

    private void promptSpeechInput() {
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.ID_SPEECH_PROMPT));
            startActivityForResult(intent, AppConstants.REQ_CODE_SPEECH_INPUT);
        } catch(ActivityNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + mSpeechAppPackageName)));
            } catch (android.content.ActivityNotFoundException exc) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + mSpeechAppPackageName)));
            }
        }
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
            mAdapter.notifyDataSetChanged();
            if (!mPullRefreshList.isPullToRefresh()) {
                mLayoutManager.scrollToPositionWithOffset(mPullRefreshList.getFeedResponses().size() - chatDocsList.size(), 0);
            } else {
                mLayoutManager.scrollToPositionWithOffset(0, 0);
            }
            //mRecyclerView.smoothScrollToPosition(0);
        }

    }

    @Override
    public void getPostQuestionSuccess(HelplinePostQuestionResponse helplinePostQuestionResponse) {
        sendChat.setEnabled(true);
        if (helplinePostQuestionResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
            HashMap<String, Object> screenProperties = null;
            if (sourceScreen != null) {
                screenProperties = new EventProperty.Builder()
                        .sourceScreenId(sourceScreen)
                        .build();
            }
            trackEvent(Event.HELPLINE_MESSAGE_CREATED, screenProperties);
            questionText.setText(AppConstants.EMPTY_STRING);
            AppUtils.hideKeyboard(getView(), TAG);
            refreshChatMethod();
        }
    }

    @Override
    public void getPostRatingSuccess(BaseResponse baseResponse,HelplineChatDoc helplineChatDoc) {
        if(baseResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED)) {
            mAdapter.notifyItemChanged(helplineChatDoc.getItemPosition());
        }
        mAdapter.removeDataOnPosition(helplineChatDoc.getItemPosition());
        mAdapter.notifyDataSetChanged();
        HashMap<String, Object> screenProperties = null;
        if (sourceScreen != null) {
            screenProperties = new EventProperty.Builder()
                    .sourceScreenId(sourceScreen)
                    .build();
        }
        trackEvent(Event.HELPLINE_RATEUS_CARD_CLICKED, screenProperties);
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mHelplinePresenter;
    }

    public void getSpeechText(String questionBySpeech) {
        questionText.append(questionBySpeech);
        questionText.setSelection(questionText.getText().length());
    }

    public void setUpRecyclerView(){
        mLayoutManager = new LinearLayoutManager(getContext());
        if(getActivity() instanceof HelplineActivity){
            mAdapter = new GenericRecyclerViewAdapter(getContext(), (HelplineActivity) getActivity());
        }
        if(getActivity() instanceof CommunityDetailActivity){
            mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunityDetailActivity) getActivity());
        }
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
        sendChat.setEnabled(true);
           }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    private boolean keyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

    private TextWatcher editTextWatcher() {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (questionText.getText().toString().length() != 0) {
                    sendChat.setColorFilter(getResources().getColor(R.color.email), android.graphics.PorterDuff.Mode.MULTIPLY);
                } else {
                    sendChat.setColorFilter(getResources().getColor(R.color.red_opacity), android.graphics.PorterDuff.Mode.MULTIPLY);
                }
            }

            @Override
            public void afterTextChanged(Editable inputSearch) {

                if (StringUtil.isNotNullOrEmptyString(inputSearch.toString()) && inputSearch.toString().length() > 0) {
                    if (!keyboardShown(questionText.getRootView())) {
                        if(questionText.getText().toString().length()!=0){
                            sendChat.setVisibility(View.VISIBLE);
                            chatVoice.setVisibility(View.GONE);
                        } else {
                            sendChat.setVisibility(View.GONE);
                            chatVoice.setVisibility(View.VISIBLE);
                        }
                    } else {
                        sendChat.setVisibility(View.VISIBLE);
                        chatVoice.setVisibility(View.GONE);
                    }
                }
            }
        };
    }


    public void checkHelplineRating(HelplineChatDoc helplineChatDoc) {
        mHelplinePresenter.postHelplineRating(mAppUtils.helpLinePostRatingRequestBuilder(false, helplineChatDoc.getQuestionOrAnswerId()), helplineChatDoc);
    }

}

