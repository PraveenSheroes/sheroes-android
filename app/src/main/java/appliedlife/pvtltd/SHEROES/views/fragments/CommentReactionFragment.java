package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsEventType;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentAddDelete;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LastComment;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageEvent;
import appliedlife.pvtltd.SHEROES.presenters.CommentReactionPresenter;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.PublicProfileGrowthBuddiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.AllCommentReactionView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_LIKE_UNLIKE;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getCommentRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.postCommentRequestBuilder;

/**
 * Created by Praveen_Singh on 24-01-2017.
 */

public class CommentReactionFragment extends BaseFragment implements AllCommentReactionView {
    private static final String SCREEN_LABEL = "Reaction Screen";
    private final String TAG = LogUtils.makeLogTag(CommentReactionFragment.class);
    @Inject
    CommentReactionPresenter mCommentReactionPresenter;
    @Bind(R.id.fl_fragment_comment)
    FrameLayout mFlFragmentComment;
    @Bind(R.id.rv_comment_reaction_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_comment_reaction_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.tv_user_comment_header_text)
    TextView mTvUserCommentHeaderText;
    @Bind(R.id.fl_comment_reaction)
    FrameLayout mFlCommentReaction;
    @Bind(R.id.li_user_comment)
    LinearLayout liUserComment;
    @Bind(R.id.tv_post_comment)
    TextView tvPostComment;
    @Bind(R.id.tv_user_comment_close)
    TextView mTvUserCommentClose;
    @Bind(R.id.view_line_for_reaction)
    View mViewLineReaction;
    @Bind(R.id.tv_user_name_for_post)
    TextView mTvUserNameForPost;
    @Bind(R.id.tv_owner_post)
    TextView mTvOwnerPost;
    @Bind(R.id.tv_anonymous_post)
    TextView mTvAnonymousPost;
    @Bind(R.id.tv_reaction_text)
    TextView mTvReaction;
    @Bind(R.id.et_user_comment_description)
    EditText mEtUserCommentDescription;
    @Bind(R.id.iv_user_comment_profile_pic)
    CircleImageView ivUserCommentProfilePic;
    @Bind(R.id.li_user_comment_post_type_selection)
    LinearLayout liUserCommentPostTypeSelection;
    private FragmentOpen mFragmentOpen;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private HomeActivityIntractionListner mHomeActivityIntractionListner;
    @Bind(R.id.swipe_comment_view)
    SwipeRefreshLayout mSwipeView;
    @Bind(R.id.li_no_result)
    LinearLayout mLiNoResult;
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    AppUtils mAppUtils;
    private FeedDetail mFeedDetail;
    boolean mIsAnonymous;
    private List<CommentReactionDoc> mCommentReactionDocList;
    private CommentReactionDoc mCommentReactionDoc;
    private FragmentListRefreshData mFragmentListRefreshData;
    private SwipPullRefreshList mPullRefreshList;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    private int mTotalComments;
    private boolean addedComment;
    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private int typeOfPost;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof HomeActivityIntractionListner) {
                mHomeActivityIntractionListner = (HomeActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        ButterKnife.bind(this, view);
        mMoEHelper = MoEHelper.getInstance(getActivity());
        payloadBuilder = new PayloadBuilder();
        if (null != getArguments()) {
            mFragmentOpen = getArguments().getParcelable(AppConstants.FRAGMENT_FLAG_CHECK);
            mFeedDetail = getArguments().getParcelable(AppConstants.COMMENTS);
            if (null != mFeedDetail) {
                mTotalComments = mFeedDetail.getNoOfComments();
                if (mFeedDetail.getNoOfLikes() < 1) {
                    mFlCommentReaction.setVisibility(View.GONE);
                    mViewLineReaction.setVisibility(View.GONE);
                }
            }
        }
        initializeUiComponent();
        super.setCommentReaction(mFragmentListRefreshData, mAdapter, mLayoutManager, mFeedDetail, mRecyclerView, mCommentReactionPresenter, mAppUtils, mProgressBar);
        if (mFragmentOpen.isCommentList()) {
            if (null != mFeedDetail && !StringUtil.isNotNullOrEmptyString(mFeedDetail.getCallFromName())) {
                AppUtils.keyboardToggle(mEtUserCommentDescription, TAG);
            }
            mCommentReactionPresenter.getAllCommentListFromPresenter(getCommentRequestBuilder(mFeedDetail.getEntityOrParticipantId(), mFragmentListRefreshData.getPageNo()), mFragmentOpen.isReactionList(), AppConstants.NO_REACTION_CONSTANT);
            ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_VIEW_REPLIES));
        } else if (mFragmentOpen.isReactionList()) {
            mFlCommentReaction.setVisibility(View.GONE);
            liUserComment.setVisibility(View.GONE);
            mViewLineReaction.setVisibility(View.GONE);
            mCommentReactionPresenter.getAllCommentListFromPresenter(getCommentRequestBuilder(mFeedDetail.getEntityOrParticipantId(), mFragmentListRefreshData.getPageNo()), mFragmentOpen.isReactionList(), AppConstants.NO_REACTION_CONSTANT);
            ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_VIEW_REACTION));
        }
        mEtUserCommentDescription.addTextChangedListener(dataSearchTextWatcher());
        mEtUserCommentDescription.setFocusableInTouchMode(true);
        mEtUserCommentDescription.requestFocus();
        if (null != mFeedDetail) {
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getSubType())) {
                if (mFeedDetail.getSubType().equalsIgnoreCase(AppConstants.FEED_ARTICLE)) {
                    typeOfPost = AppConstants.ONE_CONSTANT;
                } else if (mFeedDetail.getSubType().equalsIgnoreCase(AppConstants.FEED_COMMUNITY_POST)) {
                    typeOfPost = AppConstants.TWO_CONSTANT;
                }

            }
        }
        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setListLoadFlag(false);
                mPullRefreshList.setPullToRefresh(true);
                mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
                mPullRefreshList = new SwipPullRefreshList();
                setRefreshList(mPullRefreshList);
                mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
                if (mFragmentOpen.isCommentList()) {
                    mCommentReactionPresenter.getAllCommentListFromPresenter(getCommentRequestBuilder(mFeedDetail.getEntityOrParticipantId(), mFragmentListRefreshData.getPageNo()), mFragmentOpen.isReactionList(), AppConstants.NO_REACTION_CONSTANT);
                } else if (mFragmentOpen.isReactionList()) {
                    mFlCommentReaction.setVisibility(View.GONE);
                    liUserComment.setVisibility(View.GONE);
                    mViewLineReaction.setVisibility(View.GONE);
                    mCommentReactionPresenter.getAllCommentListFromPresenter(getCommentRequestBuilder(mFeedDetail.getEntityOrParticipantId(), mFragmentListRefreshData.getPageNo()), mFragmentOpen.isReactionList(), AppConstants.NO_REACTION_CONSTANT);
                }
            }
        });
        return view;
    }

    private void initializeUiComponent() {
        liUserComment.setEnabled(false);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mCommentReactionPresenter.attachView(this);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.COMMENT_REACTION_FRAGMENT, mFragmentOpen.isReactionList(), mFeedDetail.getEntityOrParticipantId());

        //TODO:: set user name here
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getFirstName())) {
            mTvUserNameForPost.setText(mUserPreference.get().getUserSummary().getFirstName());
            ivUserCommentProfilePic.setCircularImage(true);
            ivUserCommentProfilePic.bindImage(mUserPreference.get().getUserSummary().getPhotoUrl());
        }
        if (mFragmentOpen.isCommentList()) {
            AnalyticsManager.trackScreenView("Replies Screen");
            if (mFeedDetail.getNoOfComments() > 1) {
                mTvUserCommentHeaderText.setText(getString(R.string.ID_REPLIES) + getString(R.string.ID_OPEN_BRACKET) + String.valueOf(mFeedDetail.getNoOfComments()) + getString(R.string.ID_CLOSE_BRACKET));
            } else if (mTotalComments == 1) {
                mTvUserCommentHeaderText.setText(getString(R.string.ID_REPLY) + getString(R.string.ID_OPEN_BRACKET) + String.valueOf(mTotalComments) + getString(R.string.ID_CLOSE_BRACKET));
            } else {
                mTvUserCommentHeaderText.setText(getString(R.string.ID_NO_REPLIES));
            }
            if (mFeedDetail.getNoOfLikes() > 1) {
                mTvReaction.setText(getString(R.string.ID_REACTIONS) + getString(R.string.ID_OPEN_BRACKET) + String.valueOf(mFeedDetail.getNoOfLikes()) + getString(R.string.ID_CLOSE_BRACKET));
            } else if (mFeedDetail.getNoOfLikes() == 1) {
                mTvReaction.setText(getString(R.string.ID_REACTION) + getString(R.string.ID_OPEN_BRACKET) + String.valueOf(mFeedDetail.getNoOfLikes()) + getString(R.string.ID_CLOSE_BRACKET));
            }
        } else if (mFragmentOpen.isReactionList()) {
            AnalyticsManager.trackScreenView("Reactions Screen");
            if (mFeedDetail.getNoOfLikes() > 1) {
                mTvUserCommentHeaderText.setText(getString(R.string.ID_REACTIONS) + getString(R.string.ID_OPEN_BRACKET) + String.valueOf(mFeedDetail.getNoOfLikes()) + getString(R.string.ID_CLOSE_BRACKET));
            } else if (mFeedDetail.getNoOfLikes() == 1) {
                mTvUserCommentHeaderText.setText(getString(R.string.ID_REACTION) + getString(R.string.ID_OPEN_BRACKET) + String.valueOf(mFeedDetail.getNoOfLikes()) + getString(R.string.ID_CLOSE_BRACKET));
            } else {
                mTvUserCommentHeaderText.setText(getString(R.string.ID_NO_REACTION));
            }
        }
        switch (mFragmentOpen.getOpenCommentReactionFragmentFor()) {
            case AppConstants.ONE_CONSTANT:
                mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());
                break;
            case AppConstants.TWO_CONSTANT:
                mAdapter = new GenericRecyclerViewAdapter(getContext(), (ArticleDetailActivity) getActivity());
                break;
            case AppConstants.THREE_CONSTANT:
                mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunitiesDetailActivity) getActivity());
                break;
            case AppConstants.FOURTH_CONSTANT:
                mAdapter = new GenericRecyclerViewAdapter(getContext(), (PublicProfileGrowthBuddiesDetailActivity) getActivity());
                break;
        }
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mCommentReactionPresenter, mRecyclerView, mLayoutManager, mFragmentListRefreshData) {
            @Override
            public void onHide() {
                // mFlCommentReaction.setVisibility(View.GONE);
            }

            @Override
            public void onShow() {
                //  mFlCommentReaction.setVisibility(View.VISIBLE);
            }

            @Override
            public void dismissReactions() {

            }
        });
    }

    private void entityData(FeedDetail feedDetail) {
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getSubType())) {
            String subType = feedDetail.getSubType();
            switch (subType) {
                case AppConstants.FEED_ARTICLE:
                    entityMoEngageReaction(getString(R.string.ID_ARTICLE), feedDetail.getEntityOrParticipantId(), feedDetail.getNameOrTitle(), MoEngageConstants.ARTICLE_CATEGORY, MoEngageConstants.ARTICLE_TAG, feedDetail.getAuthorName(), AppConstants.FEED_SCREEN, feedDetail.getItemPosition());
                    break;
                case AppConstants.FEED_COMMUNITY_POST:
                    entityMoEngageReaction(getString(R.string.ID_COMMUNITIY), feedDetail.getEntityOrParticipantId(), feedDetail.getNameOrTitle(), MoEngageConstants.COMMUNITY_POST_CATEGORY, MoEngageConstants.COMMUNITY_TAG, feedDetail.getAuthorName(), AppConstants.FEED_SCREEN, feedDetail.getItemPosition());
                    break;
            }
        }
    }

    private void entityMoEngageReaction(String entityValue, long entityId, String entityTitle, String entityType, String entityTag, String entityCreator, String screenName, int position) {
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY, entityValue);
        payloadBuilder.putAttrLong(MoEngageConstants.ENTITY_ID, entityId);
        if (mIsAnonymous) {
            payloadBuilder.putAttrString(MoEngageConstants.AS, getString(R.string.ID_ANONYMOUS));
        } else {
            payloadBuilder.putAttrString(MoEngageConstants.AS, AppConstants.USER);
        }
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY_TITLE, entityTitle);
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY_TYPE, entityType);
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY_TAG, entityTag);
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY_CREATOR, entityCreator);
        payloadBuilder.putAttrString(MoEngageConstants.SCREEN_NAME, screenName);
        payloadBuilder.putAttrInt(MoEngageConstants.POSITION_OF_ENTITY, position);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_REPLIED.value, payloadBuilder.build());
    }

    @OnClick(R.id.et_user_comment_description)
    public void editTextForComment() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(mEtUserCommentDescription.getWindowToken(), 0);
    }

    @OnClick(R.id.li_user_comment)
    public void liEditForComment() {
    }

    @OnClick(R.id.li_replies_header)
    public void onHeaderClick() {

    }

    @Override
    public void getAllCommentsAndReactions(CommentReactionResponsePojo commentReactionResponsePojo, int addEditOperation) {
        mEtUserCommentDescription.requestFocus();
        mLiNoResult.setVisibility(View.GONE);
        if (addEditOperation == AppConstants.ONE_CONSTANT) {
            if (StringUtil.isNotEmptyCollection(commentReactionResponsePojo.getCommentReactionDocList())) {
                mCommentReactionDocList = commentReactionResponsePojo.getCommentReactionDocList();
                mCommentReactionDoc = null;
                setLastComments();
                setAdapterData();
                addedComment = true;
            }
        } else {
            if (StringUtil.isNotEmptyCollection(commentReactionResponsePojo.getCommentReactionDocList())) {
                mPageNo = mFragmentListRefreshData.getPageNo();
                mFragmentListRefreshData.setPageNo(++mPageNo);
                mPullRefreshList.allListData(commentReactionResponsePojo.getCommentReactionDocList());
                mCommentReactionDocList = mPullRefreshList.getFeedResponses();
                if (mFeedDetail.isTrending()) {
                    menuItemDeleteOrEdit();
                    mFeedDetail.setTrending(false);
                }
                mAdapter.setSheroesGenericListData(mCommentReactionDocList);
                mAdapter.notifyDataSetChanged();
                if (!mPullRefreshList.isPullToRefresh()) {
                    mLayoutManager.scrollToPositionWithOffset(mPullRefreshList.getFeedResponses().size() - commentReactionResponsePojo.getCommentReactionDocList().size(), 0);
                } else {
                    mLayoutManager.scrollToPositionWithOffset(0, 0);
                }
                mSwipeView.setRefreshing(false);
            } else if (!StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses()) && !addedComment) {
                mFeedDetail.setNoOfComments(AppConstants.NO_REACTION_CONSTANT);
                mFeedDetail.setLastComments(null);
                mLiNoResult.setVisibility(View.VISIBLE);
                mTvUserCommentHeaderText.setText(getString(R.string.ID_NO_REPLIES));
                mTvUserCommentHeaderText.setText(getString(R.string.ID_NO_REPLIES));
            }

        }
    }

    private void menuItemDeleteOrEdit() {
        int editOrDelete = mFeedDetail.getExperienceFromI();
        switch (editOrDelete) {
            case AppConstants.ONE_CONSTANT:
                List<LastComment> lastCommentList = mFeedDetail.getLastComments();
                if (StringUtil.isNotEmptyCollection(lastCommentList)) {
                    int commentId = lastCommentList.get(mFeedDetail.getNoOfOpenings()).getId();
                    for (CommentReactionDoc commentReactionDoc : mCommentReactionDocList) {
                        if (commentId == commentReactionDoc.getId()) {

                            mEtUserCommentDescription.setText(commentReactionDoc.getComment());
                            mEtUserCommentDescription.setSelection(commentReactionDoc.getComment().length());

                            mEtUserCommentDescription.setTextColor(ContextCompat.getColor(getActivity(), R.color.feed_article_label));
                            mCommentReactionDocList.remove(commentReactionDoc);
                            commentReactionDoc.setActive(true);
                            commentReactionDoc.setEdit(true);
                            mCommentReactionDoc = commentReactionDoc;
                            break;
                        }
                    }
                }
                break;
            case AppConstants.TWO_CONSTANT:
                List<LastComment> lastDeleteCommentList = mFeedDetail.getLastComments();
                if (StringUtil.isNotEmptyCollection(lastDeleteCommentList)) {
                    int commentId = lastDeleteCommentList.get(mFeedDetail.getNoOfOpenings()).getId();
                    for (CommentReactionDoc commentReactionDoc : mCommentReactionDocList) {
                        if (commentId == commentReactionDoc.getId()) {
                            commentReactionDoc.setActive(false);
                            mCommentReactionPresenter.editCommentListFromPresenter(mAppUtils.editCommentRequestBuilder(commentReactionDoc.getEntityId(), commentReactionDoc.getComment(), mFeedDetail.isAnonymous(), commentReactionDoc.isActive(), commentReactionDoc.getId()), AppConstants.TWO_CONSTANT);
                            mCommentReactionDocList.remove(commentReactionDoc);
                            break;
                        }
                    }
                }
                break;
        }
    }

    private void setAdapterData() {
        mAdapter.setSheroesGenericListData(mCommentReactionDocList);
        mLayoutManager.scrollToPosition(0);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void commentSuccess(CommentAddDelete commentAddDelete, int operationId) {
        String postType = null;
        if (typeOfPost == AppConstants.ONE_CONSTANT) {
            postType = AnalyticsEventType.ARTICLE.toString();
        }
        if (typeOfPost == AppConstants.TWO_CONSTANT) {
            postType = AnalyticsEventType.COMMUNITY.toString();
        }

        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(commentAddDelete.getCommentReactionModel().getId()))
                        .postId(Long.toString(commentAddDelete.getCommentReactionModel().getEntityId()))
                        .postType(postType)
                        .body(commentAddDelete.getCommentReactionModel().getComment())
                        .build();
        mEtUserCommentDescription.setEnabled(true);
        tvPostComment.setEnabled(true);
        switch (commentAddDelete.getStatus()) {
            case AppConstants.SUCCESS:
                AppUtils.hideKeyboard(mEtUserCommentDescription, TAG);
                switch (operationId) {
                    case AppConstants.NO_REACTION_CONSTANT:
                        mCommentReactionPresenter.getAllCommentListFromPresenter(getCommentRequestBuilder(mFeedDetail.getEntityOrParticipantId(), AppConstants.ONE_CONSTANT), mFragmentOpen.isReactionList(), AppConstants.ONE_CONSTANT);
                        mEtUserCommentDescription.setText(AppConstants.EMPTY_STRING);
                        tvPostComment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_post_comment_active, 0, 0, 0);
                        tvPostComment.setVisibility(View.GONE);
                        mTotalComments++;
                        if (!(mCommentReactionDoc != null && mCommentReactionDoc.isEdit())) {
                            trackEvent(Event.REPLY_CREATED, properties);
                        }
                        entityData(mFeedDetail);
                        break;
                    case AppConstants.ONE_CONSTANT:
                        tvPostComment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_post_comment_active, 0, 0, 0);
                        mCommentReactionPresenter.addCommentListFromPresenter(postCommentRequestBuilder(mFeedDetail.getEntityOrParticipantId(), mEtUserCommentDescription.getText().toString(), mIsAnonymous), AppConstants.NO_REACTION_CONSTANT);
                       /* if (null != mCommentReactionDoc && mCommentReactionDoc.isEdit()) {
                            mCommentReactionDocList.add(mCommentReactionDoc.getItemPosition(),commentAddDelete.getCommentReactionModel());
                            setLastComments();
                            setAdapterData();
                            addedComment = true;
                        }*/
                        if (mCommentReactionDoc != null && mCommentReactionDoc.isEdit()) {
                            trackEvent(Event.REPLY_EDITED, properties);
                        }

                        break;
                    case AppConstants.TWO_CONSTANT:
                        mTotalComments--;
                        if (null != mCommentReactionDoc) {
                            mCommentReactionDocList.remove(mCommentReactionDoc.getItemPosition());
                            mCommentReactionDoc = null;
                        }
                        if (mFragmentOpen.isCommentList()) {
                            if (mCommentReactionDocList.size() > 1) {
                                mTvUserCommentHeaderText.setText(getString(R.string.ID_REPLIES) + getString(R.string.ID_OPEN_BRACKET) + String.valueOf(mCommentReactionDocList.size()) + getString(R.string.ID_CLOSE_BRACKET));
                            } else if (mTotalComments == 1) {
                                mTvUserCommentHeaderText.setText(getString(R.string.ID_REPLY) + getString(R.string.ID_OPEN_BRACKET) + String.valueOf(mTotalComments) + getString(R.string.ID_CLOSE_BRACKET));
                            } else {
                                mTvUserCommentHeaderText.setText(getString(R.string.ID_NO_REPLIES));
                            }
                        }
                        setLastComments();
                        setAdapterData();
                        trackEvent(Event.REPLY_DELETED, properties);
                        break;
                    default:
                        LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + operationId);
                }
                break;
            case AppConstants.FAILED:
                showError(commentAddDelete.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_LIKE_UNLIKE);
                break;
            default:
                showError(getString(R.string.ID_GENERIC_ERROR), ERROR_LIKE_UNLIKE);

        }

    }

    private void setLastComments() {
        List<LastComment> lastCommentList = new ArrayList<>();
        if (StringUtil.isNotEmptyCollection(mCommentReactionDocList)) {
            switch (mCommentReactionDocList.size()) {
                case AppConstants.ONE_CONSTANT:
                    lastCommentList = getListItem(mCommentReactionDocList.size(), lastCommentList);
                    break;
                case AppConstants.TWO_CONSTANT:
                    lastCommentList = getListItem(mCommentReactionDocList.size(), lastCommentList);
                    break;
                case AppConstants.THREE_CONSTANT:
                    lastCommentList = getListItem(mCommentReactionDocList.size(), lastCommentList);
                    break;
                default:
                    lastCommentList = getListItem(AppConstants.THREE_CONSTANT, lastCommentList);

            }
            mFeedDetail.setNoOfComments(mTotalComments);
            mFeedDetail.setLastComments(lastCommentList);
            if (mFragmentOpen.isCommentList()) {
                if (mTotalComments > 1) {
                    mTvUserCommentHeaderText.setText(getString(R.string.ID_REPLIES) + getString(R.string.ID_OPEN_BRACKET) + String.valueOf(mTotalComments) + getString(R.string.ID_CLOSE_BRACKET));
                } else if (mTotalComments == 1) {
                    mTvUserCommentHeaderText.setText(getString(R.string.ID_REPLY) + getString(R.string.ID_OPEN_BRACKET) + String.valueOf(mTotalComments) + getString(R.string.ID_CLOSE_BRACKET));
                } else {
                    mTvUserCommentHeaderText.setText(getString(R.string.ID_NO_REPLIES));
                }
            }
        } else {
            mFeedDetail.setNoOfComments(AppConstants.NO_REACTION_CONSTANT);
            mFeedDetail.setLastComments(null);
        }
    }

    private List<LastComment> getListItem(int size, List<LastComment> lastCommentList) {
        for (int i = size - 1; i >= 0; i--) {
            LastComment lastComment = new LastComment();
            lastComment.setId(mCommentReactionDocList.get(i).getId());
            lastComment.setAnonymous(mCommentReactionDocList.get(i).isAnonymous());
            lastComment.setComment(mCommentReactionDocList.get(i).getComment());
            lastComment.setParticipantImageUrl(mCommentReactionDocList.get(i).getParticipantImageUrl());
            lastComment.setParticipantName(mCommentReactionDocList.get(i).getParticipantName());
            lastComment.setMyOwnParticipation(mCommentReactionDocList.get(i).isMyOwnParticipation());
            lastComment.setCreatedOn(mCommentReactionDocList.get(i).getCreatedOn());
            lastCommentList.add(lastComment);
        }
        return lastCommentList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCommentReactionPresenter.detachView();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    public interface HomeActivityIntractionListner {
        void onDialogDissmiss(FragmentOpen isFragmentOpen, FeedDetail feedDetail);

        void onClickReactionList(FragmentOpen isFragmentOpen, FeedDetail feedDetail);
    }

    @OnClick(R.id.tv_user_comment_close)
    public void dismissCommentDialog() {
        View view = mEtUserCommentDescription;
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        mHomeActivityIntractionListner.onDialogDissmiss(mFragmentOpen, mFeedDetail);
    }

    @OnClick(R.id.fl_comment_reaction)
    public void onClickReactionList() {
        openReactionList();
    }

    @OnClick(R.id.li_user_comment_post_type_selection)
    public void onCommentSelection() {

    }

    private void openReactionList() {
        mTvUserCommentHeaderText.setText(getString(R.string.ID_REACTION));
        mFragmentOpen.setReactionList(true);
        mFragmentOpen.setCommentList(false);
        mHomeActivityIntractionListner.onClickReactionList(mFragmentOpen, mFeedDetail);
    }

    @OnClick(R.id.tv_user_name_for_post)
    public void userNamePostForComment() {
        mIsAnonymous = false;
        mTvUserNameForPost.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue));
        mTvAnonymousPost.setTextColor(ContextCompat.getColor(getActivity(), R.color.searchbox_text_color));
    }

    @OnClick(R.id.tv_anonymous_post)
    public void anonymousPostForComment() {
        mIsAnonymous = true;
        mTvUserNameForPost.setTextColor(ContextCompat.getColor(getActivity(), R.color.searchbox_text_color));
        mTvAnonymousPost.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue));
    }

    @OnClick(R.id.tv_post_comment)
    public void postComment() {
        mEtUserCommentDescription.setEnabled(false);
        tvPostComment.setEnabled(false);
        if (null != mCommentReactionDoc && mCommentReactionDoc.isEdit()) {
            mTotalComments--;
            mCommentReactionPresenter.editCommentListFromPresenter(mAppUtils.editCommentRequestBuilder(mCommentReactionDoc.getEntityId(), mCommentReactionDoc.getComment(), mIsAnonymous, false, mCommentReactionDoc.getId()), AppConstants.ONE_CONSTANT);

            if (typeOfPost == AppConstants.ONE_CONSTANT) {
                ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EDITED_REPLIED, GoogleAnalyticsEventActions.EDITED_REPLIED_TO_ARTICLE, AppConstants.EMPTY_STRING);
            } else if (typeOfPost == AppConstants.TWO_CONSTANT) {
                ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EDITED_REPLIED, GoogleAnalyticsEventActions.EDITED_REPLIED_ON_COMMUNITY_POST, AppConstants.EMPTY_STRING);
            }

        } else {
            tvPostComment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_post_comment_active, 0, 0, 0);
            mCommentReactionPresenter.addCommentListFromPresenter(postCommentRequestBuilder(mFeedDetail.getEntityOrParticipantId(), mEtUserCommentDescription.getText().toString(), mIsAnonymous), AppConstants.NO_REACTION_CONSTANT);
            if (typeOfPost == AppConstants.ONE_CONSTANT) {
                ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_REPLIED, GoogleAnalyticsEventActions.REPLIED_TO_ARTICLE, AppConstants.EMPTY_STRING);
            } else if (typeOfPost == AppConstants.TWO_CONSTANT) {
                ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_REPLIED, GoogleAnalyticsEventActions.REPLIED_TO_COMMUNITY_POST, AppConstants.EMPTY_STRING);
            }

        }

    }

    public void editCommentInList(CommentReactionDoc commentReactionDoc) {
        mCommentReactionDoc = commentReactionDoc;
        if (null != mCommentReactionDoc && mCommentReactionDoc.isEdit()) {
            AppUtils.hideKeyboard(mEtUserCommentDescription, TAG);
            AppUtils.keyboardToggle(mEtUserCommentDescription, TAG);
            AppUtils.showKeyboard(mEtUserCommentDescription, TAG);
            mCommentReactionDocList.remove(mCommentReactionDoc.getItemPosition());
            mEtUserCommentDescription.setText(mCommentReactionDoc.getComment());
            mEtUserCommentDescription.setSelection(mEtUserCommentDescription.getText().toString().length());
            mEtUserCommentDescription.setTextColor(ContextCompat.getColor(getActivity(), R.color.feed_article_label));
            //  mEtUserCommentDescription.setRawInputType(InputType.TYPE_CLASS_TEXT);
            //  mEtUserCommentDescription.setTextIsSelectable(true);
        }
        setAdapterData();
    }

    public void deleteCommentFromList(CommentReactionDoc commentReactionDoc) {
        mCommentReactionDoc = commentReactionDoc;
        mCommentReactionPresenter.editCommentListFromPresenter(mAppUtils.editCommentRequestBuilder(commentReactionDoc.getEntityId(), commentReactionDoc.getComment(), mIsAnonymous, commentReactionDoc.isActive(), commentReactionDoc.getId()), AppConstants.TWO_CONSTANT);
        if (typeOfPost == AppConstants.ONE_CONSTANT) {
            if (!commentReactionDoc.isActive()) {
                ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DELETED_REPLIED, GoogleAnalyticsEventActions.DELETED_REPLIED_TO_ARTICLE, AppConstants.EMPTY_STRING);
            }
        } else if (typeOfPost == AppConstants.TWO_CONSTANT) {
            ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DELETED_REPLIED, GoogleAnalyticsEventActions.DELETED_REPLIED_ON_COMMUNITY_POST, AppConstants.EMPTY_STRING);
        }
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
                if (StringUtil.isNotNullOrEmptyString(inputSearch.toString())) {
                    liUserCommentPostTypeSelection.setVisibility(View.VISIBLE);
                    if (tvPostComment.getVisibility() == View.GONE)
                        tvPostComment.setVisibility(View.VISIBLE);
                } else {
                    tvPostComment.setVisibility(View.GONE);
                    liUserCommentPostTypeSelection.setVisibility(View.GONE);
                }
            }
        };
    }
}