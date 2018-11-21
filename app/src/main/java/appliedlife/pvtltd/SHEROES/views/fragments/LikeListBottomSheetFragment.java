package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.presenters.UserLikedListPresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.LikeListAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IUserLikedListView;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.PROFILE_NOTIFICATION_ID;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getCommentRequestBuilder;

/**
 * Created by ujjwal on 27/10/17.
 */

public class LikeListBottomSheetFragment extends BottomSheetDialogFragment implements IUserLikedListView, LikeListAdapter.LikeHolderListener {
    private static final String SCREEN_LABEL = "PostBottomSheetFragment";
    private static final String USER_POST_ID = "UserPostId";

    private LikeListAdapter mLikeListAdapter;
    public long mUserPostId;

    @Inject
    UserLikedListPresenterImpl mUserLikedListPresenter;

    // region View variables
    @Bind(R.id.like_count)
    TextView mLikeCount;

    @Bind(R.id.user_like_list)
    RecyclerView mRecyclerView;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;
    // endregion

    //region Fragment LifeCycle Methods
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(getArguments()!=null){
            mUserPostId = getArguments().getLong(USER_POST_ID, -1);
            if(mUserPostId == -1 && getDialog()!=null){
                getDialog().dismiss();
            }
        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        mUserLikedListPresenter.attachView(this);
        super.setupDialog(dialog, style);
        View containerView = View.inflate(getContext(), R.layout.dialog_like_list, null);
        dialog.setContentView(containerView);
        ButterKnife.bind(this, containerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mUserLikedListPresenter.getUserLikedList(getCommentRequestBuilder(mUserPostId, 1, 200), true, AppConstants.NO_REACTION_CONSTANT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mLikeCount.setVisibility(View.GONE);
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mLikeCount.setVisibility(View.VISIBLE);
    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
       // mCommunityPostActivity.showError(s, feedParticipationEnum);
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }


    //endregion

    //region private methods
    //endregion

    //region Public Static methods
    public static LikeListBottomSheetFragment showDialog(AppCompatActivity activity, String sourceScreen, long userPostId) {
        LikeListBottomSheetFragment postBottomSheetFragment = new LikeListBottomSheetFragment();
        Bundle args = new Bundle();
        args.putLong(USER_POST_ID, userPostId);
        postBottomSheetFragment.setArguments(args);
        args.putString(BaseActivity.SOURCE_SCREEN, sourceScreen);
        postBottomSheetFragment.show(activity.getSupportFragmentManager(), SCREEN_LABEL);
        return postBottomSheetFragment;
    }


    @Override
    public void showUserLikedList(List<Comment> commentList) {
        if(getActivity()!=null && isAdded()){
            mRecyclerView.setAdapter(mLikeListAdapter = new LikeListAdapter(getActivity(), commentList, this));

            if (mLikeListAdapter != null) {
                mLikeListAdapter.notifyDataSetChanged();
            }
            String pluralLikes = getResources().getQuantityString(R.plurals.numberOfLikes, commentList.size());
            mLikeCount.setText(CommonUtil.getRoundedMetricFormat(commentList.size()) + " " + pluralLikes);
        }
    }

    @Override
    public void onLikeRowItemClicked(LikeListAdapter.LikeListItemViewHolder likeListItemViewHolder) {
        int adapterPosition = likeListItemViewHolder.getAdapterPosition();
        if(adapterPosition != RecyclerView.NO_POSITION) {
            Comment comment = mLikeListAdapter.getComment(adapterPosition);
            if(comment!=null) {
                ProfileActivity.navigateTo(getActivity(), comment.getParticipantUserId(), comment.isVerifiedMentor(),
                        PROFILE_NOTIFICATION_ID, SCREEN_LABEL, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL, false);
            }
        }
    }
    //endregion
}
