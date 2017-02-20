package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.presenters.CommentReactionPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.AllCommentReactionView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

/**
 * Created by Praveen_Singh on 24-01-2017.
 */

public class CommentReactionFragment extends BaseFragment implements AllCommentReactionView {
    private final String TAG = LogUtils.makeLogTag(CommentReactionFragment.class);
    @Inject
    CommentReactionPresenter mCommentReactionPresenter;
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
    @Bind(R.id.et_user_comment_description)
    EditText mEtUserCommentDescription;
    @Bind(R.id.iv_user_comment_profile_pic)
    CircleImageView ivUserCommentProfilePic;
    @Bind(R.id.li_user_comment_post_type_selection)
    LinearLayout liUserCommentPostTypeSelection;
    private FragmentOpen mFragmentOpen;
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    private GenericRecyclerViewAdapter mAdapter;
    private HomeActivityIntractionListner mHomeActivityIntractionListner;
    @Inject
    AppUtils mAppUtils;
    private FeedDetail mFeedDetail;
    boolean isAnonymous = false;
    private List<CommentReactionDoc> mCommentReactionDocList;
    CommentReactionDoc mCommentReactionDoc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            mFragmentOpen = getArguments().getParcelable(AppConstants.FRAGMENT_FLAG_CHECK);
            mFeedDetail = getArguments().getParcelable(AppConstants.COMMENTS);
        }
        mCommentReactionPresenter.attachView(this);
        initializeUiComponent();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (mFragmentOpen.isOpen()) {
            mAdapter = new GenericRecyclerViewAdapter(getContext(), (ArticleDetailActivity) getActivity());
        } else {
            mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());
        }
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        if (mFragmentOpen.isCommentList()) {
            mCommentReactionPresenter.getAllCommentListFromPresenter(mAppUtils.getCommentRequestBuilder(mFeedDetail.getEntityOrParticipantId()), mFragmentOpen.isReactionList());
        } else if (mFragmentOpen.isReactionList()) {
            mFlCommentReaction.setVisibility(View.GONE);
            liUserComment.setVisibility(View.GONE);
            mViewLineReaction.setVisibility(View.GONE);
            mCommentReactionPresenter.getAllCommentListFromPresenter(mAppUtils.getCommentRequestBuilder(mFeedDetail.getEntityOrParticipantId()), mFragmentOpen.isReactionList());
        }

        return view;
    }

    private void initializeUiComponent() {
        //TODO:: set user name here
        mTvUserNameForPost.setText("Praveen");
        if (null != mFeedDetail) {
            if (mFeedDetail.getNoOfLikes() < 1) {
                mFlCommentReaction.setVisibility(View.GONE);
                mViewLineReaction.setVisibility(View.GONE);
            }
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getAuthorImageUrl())) {
                ivUserCommentProfilePic.setCircularImage(true);
                ivUserCommentProfilePic.bindImage(mFeedDetail.getAuthorImageUrl());
            }
        }
    }

    @Override
    public void getAllCommentsAndReactions(CommentReactionResponsePojo commentReactionResponsePojo) {
        mCommentReactionDocList = commentReactionResponsePojo.getCommentReactionDocList();
        if (mAdapter != null) {
            mTvUserCommentHeaderText.setText(getString(R.string.ID_REPLIES) + getString(R.string.ID_OPEN_BRACKET) + String.valueOf(mCommentReactionDocList.size()) + getString(R.string.ID_CLOSE_BRACKET));
            if (null != mCommentReactionDoc && mCommentReactionDoc.isEdit()) {
                mCommentReactionDocList.remove(mCommentReactionDoc.getItemPosition());
                mEtUserCommentDescription.setText(mCommentReactionDoc.getComment());
            }
            mAdapter.setSheroesGenericListData(mCommentReactionDocList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addCommentSuccess(String success) {
        if (success.equalsIgnoreCase(AppConstants.SUCCESS)) {
            mEtUserCommentDescription.setText(AppConstants.EMPTY_STRING);
            mCommentReactionPresenter.getAllCommentListFromPresenter(mAppUtils.getCommentRequestBuilder(mFeedDetail.getEntityOrParticipantId()), mFragmentOpen.isReactionList());
        }
    }

    @Override
    public void showNwError() {
        mHomeActivityIntractionListner.onErrorOccurence();
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
    public void showError(String errorMsg) {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCommentReactionPresenter.detachView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public interface HomeActivityIntractionListner {
        void onErrorOccurence();

        void onDialogDissmiss(FragmentOpen isFragmentOpen);

        void onClickReactionList(FragmentOpen isFragmentOpen, FeedDetail feedDetail);
    }

    @OnClick(R.id.tv_user_comment_close)
    public void dismissCommentDialog() {
        mHomeActivityIntractionListner.onDialogDissmiss(mFragmentOpen);
    }

    @OnClick(R.id.fl_comment_reaction)
    public void openReactionList() {
        mFragmentOpen.setReactionList(true);
        mFragmentOpen.setCommentList(false);
        mHomeActivityIntractionListner.onClickReactionList(mFragmentOpen, mFeedDetail);
    }

    @OnFocusChange(R.id.et_user_comment_description)
    public void editTextForComment() {
        liUserCommentPostTypeSelection.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_user_name_for_post)
    public void userNamePostForComment() {
        isAnonymous = false;
        mTvUserNameForPost.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue));
        mTvAnonymousPost.setTextColor(ContextCompat.getColor(getActivity(), R.color.searchbox_text_color));
    }

    @OnClick(R.id.tv_anonymous_post)
    public void anonymousPostForComment() {
        isAnonymous = true;
        mTvUserNameForPost.setTextColor(ContextCompat.getColor(getActivity(), R.color.searchbox_text_color));
        mTvAnonymousPost.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue));
    }

    @OnClick(R.id.tv_post_comment)
    public void postComment() {
        mCommentReactionPresenter.addCommentListFromPresenter(mAppUtils.postCommentRequestBuilder(mFeedDetail.getEntityOrParticipantId(), mEtUserCommentDescription.getText().toString(), isAnonymous));
    }

    public void editCommentInList(CommentReactionDoc commentReactionDoc) {
        mCommentReactionDoc = commentReactionDoc;
        mCommentReactionPresenter.editCommentListFromPresenter(mAppUtils.editCommentRequestBuilder(commentReactionDoc.getEntityId(), commentReactionDoc.getComment(), isAnonymous, commentReactionDoc.isActive(), commentReactionDoc.getId()));
    }
}