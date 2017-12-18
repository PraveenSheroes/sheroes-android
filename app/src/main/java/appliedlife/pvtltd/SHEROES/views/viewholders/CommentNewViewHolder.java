package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.CommentCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.hashTagColorInString;
import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.linkifyURLs;

/**
 * Created by Praveen_Singh on 24-01-2017.
 */

public class CommentNewViewHolder extends BaseViewHolder<Comment> {
    private final String TAG = LogUtils.makeLogTag(CommentNewViewHolder.class);
    @Inject
    DateUtil mDateUtil;

    @Inject
    Preference<LoginResponse> userPreference;

    @Bind(R.id.profile_verified)
    ImageView mProfileVerfied;

    @Bind(R.id.user_profile_pic)
    CircleImageView mUserProfilePic;

    @Bind(R.id.tv_list_user_comment)
    TextView mUserComment;

    @Bind(R.id.tv_user_comment_list_menu)
    ImageView mUserCommentListMenu;

    @Bind(R.id.tv_list_user_comment_time)
    TextView mCommentTime;

    @Bind(R.id.like)
    TextView mCommentLike;

    @Bind(R.id.comment_author_name)
    TextView mCommentAuthorName;

    Context mContext;
    CommentCallBack mCommentCallback;
    private Comment mComment;
    private long mAdminId;
    public CommentNewViewHolder(View itemView, CommentCallBack commentCallBack) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mCommentCallback = commentCallBack;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            if (null != userPreference.get().getUserSummary().getUserBO()) {
                mAdminId = userPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public void bindData(Comment item, final Context context, int position) {
        this.mComment = item;
        this.mComment.setItemPosition(position);
        this.mContext = context;
        if (StringUtil.isNotNullOrEmptyString(mComment.getPostedDate())) {
            mCommentTime.setText(mComment.getPostedDate());
        } else {
            mCommentTime.setText(mContext.getString(R.string.ID_JUST_NOW));
        }

        if (mComment.isMyOwnParticipation()||mAdminId==AppConstants.TWO_CONSTANT) {
            mUserCommentListMenu.setVisibility(View.VISIBLE);
        } else {
            mUserCommentListMenu.setVisibility(View.GONE);
        }
        mCommentAuthorName.setText(mComment.getParticipantName());
        mUserProfilePic.setCircularImage(true);
        invalidateLikeView(item);
        if (item.isAnonymous()&&StringUtil.isNotNullOrEmptyString(mComment.getParticipantName())) {
            mUserProfilePic.bindImage(mComment.getParticipantImageUrl());
            StringBuilder stringBuilder = new StringBuilder();
           // stringBuilder.append(mComment.getParticipantName()).append(AppConstants.COLON).append(AppConstants.SPACE).append(mComment.getComment());
            //Spannable getCommentString = new SpannableString(stringBuilder.toString());
            //int size = mComment.getParticipantName().length() + 1;
            //getCommentString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, size, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //getCommentString.setSpan(new StyleSpan(Typeface.BOLD), 0, size, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mUserComment.setText(hashTagColorInString(mComment.getComment()));
            linkifyURLs(mUserComment);
            mProfileVerfied.setVisibility(View.GONE);
        } else {
            if (StringUtil.isNotNullOrEmptyString(mComment.getComment())&&StringUtil.isNotNullOrEmptyString(mComment.getParticipantName())) {
                mUserProfilePic.bindImage(mComment.getParticipantImageUrl());
                /*StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(mComment.getParticipantName()).append(AppConstants.COLON).append(AppConstants.SPACE).append(mComment.getComment());
                Spannable getCommentString = new SpannableString(stringBuilder.toString());
                int size = mComment.getParticipantName().length() + 1;
                getCommentString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, size, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                getCommentString.setSpan(new StyleSpan(Typeface.BOLD), 0, size, Spannable.SPAN_INCLUSIVE_INCLUSIVE);*/
                mUserComment.setText(hashTagColorInString(mComment.getComment()));
                linkifyURLs(mUserComment);
                if (!mComment.getParticipantName().equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                    if (mComment.isVerifiedMentor()) {
                        mProfileVerfied.setVisibility(View.VISIBLE);
                    } else {
                        mProfileVerfied.setVisibility(View.GONE);
                    }
                }else {
                    mProfileVerfied.setVisibility(View.GONE);
                }
            }
        }
    }

    private void invalidateLikeView(Comment item) {
        if(item.likeCount > 0){
            mCommentLike.setText(Integer.toString(item.likeCount));
        }else {
            mCommentLike.setText("");
        }
        if (item.isLiked) {
            mCommentLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
        } else {
            mCommentLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_user_comment_list_menu)
    public void onCommentMenuClick() {
        mComment.setItemPosition(getAdapterPosition());
        mCommentCallback.onCommentMenuClicked(mComment, mUserCommentListMenu);
    }
    @OnClick(R.id.tv_list_user_comment)
    public void onCommentWithNameClick() {
       /* if (mComment.isVerifiedMentor()) {
            viewInterface.championProfile(mComment, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }*/
    }

    @OnClick(R.id.like)
    public void onCommentLikeClicked(){
        if (mComment.isLiked) {
            mCommentCallback.userCommentLikeRequest(mComment, false, getAdapterPosition());
        } else {
            mCommentCallback.userCommentLikeRequest(mComment, true, getAdapterPosition());
        }
        if (mComment.isLiked) {
            mComment.isLiked = false;
            mComment.likeCount--;
        } else {
            mComment.isLiked = true;
            mComment.likeCount++;
        }
        invalidateLikeView(mComment);
    }


    @Override
    public void onClick(View view) {
    }

}