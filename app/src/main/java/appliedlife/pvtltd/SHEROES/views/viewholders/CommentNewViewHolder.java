package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.CommentCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.usertagging.mentions.MentionSpan;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.BindDimen;
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

    @Bind(R.id.spam_comment_ui)
    RelativeLayout spamUiContainer;

    @Bind(R.id.spam_comment_menu)
    ImageView spamMenu;

    @Bind(R.id.comment_container)
    RelativeLayout commentContainer;

    @BindDimen(R.dimen.dp_size_40)
    int authorProfileSize;

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

        mCommentAuthorName.setText(mComment.getParticipantName());
        mUserProfilePic.setCircularImage(true);
        invalidateLikeView(item);

        invalidateSpamComment(item);

        if(!((Activity)mContext).isFinishing()){
            if (item.isAnonymous()&&StringUtil.isNotNullOrEmptyString(mComment.getParticipantName())) {
                String authorThumborUrl = CommonUtil.getThumborUri(mComment.getParticipantImageUrl(), authorProfileSize, authorProfileSize);
                mUserProfilePic.bindImage(authorThumborUrl);
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(mComment.getParticipantName()).append(AppConstants.COLON).append(AppConstants.SPACE).append(mComment.getComment());
                Spannable getCommentString = new SpannableString(stringBuilder.toString());
                int size = mComment.getParticipantName().length() + 1;
                TypefaceSpan typefaceSpan = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_REGULAR));
                getCommentString.setSpan(typefaceSpan, 0, size, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                getCommentString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), 0, size, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                if(mComment.isHasCommentMention())
                {
                    List<MentionSpan> mentionSpanList =mComment.getCommentUserMentionList();
                    if(StringUtil.isNotEmptyCollection(mentionSpanList)) {
                        clickOnUserMentionName(mComment.getComment(), mentionSpanList);
                    }
                }else
                {
                    mUserComment.setText(hashTagColorInString(mComment.getComment()), TextView.BufferType.SPANNABLE);
                }
                linkifyURLs(mUserComment);
                mProfileVerfied.setVisibility(View.GONE);
            } else {
                if (StringUtil.isNotNullOrEmptyString(mComment.getComment())&&StringUtil.isNotNullOrEmptyString(mComment.getParticipantName())) {
                    String authorThumborUrl = CommonUtil.getThumborUri(mComment.getParticipantImageUrl(), authorProfileSize, authorProfileSize);
                    mUserProfilePic.bindImage(authorThumborUrl);
                    if(mComment.isHasCommentMention())
                    {
                        List<MentionSpan> mentionSpanList =mComment.getCommentUserMentionList();
                        if(StringUtil.isNotEmptyCollection(mentionSpanList)) {
                            clickOnUserMentionName(mComment.getComment(), mentionSpanList);
                        }
                    }else
                    {
                        mUserComment.setText(hashTagColorInString(mComment.getComment()), TextView.BufferType.SPANNABLE);
                    };
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
    }

    private void invalidateSpamComment(Comment item) {
        if(item.isSpamComment()) {
            spamUiContainer.setVisibility(View.VISIBLE);
            commentContainer.setVisibility(View.GONE);
        } else  {
            spamUiContainer.setVisibility(View.GONE);
            commentContainer.setVisibility(View.VISIBLE);
        }

        if (!mComment.isSpamComment() && (mComment.isMyOwnParticipation() || mAdminId == AppConstants.TWO_CONSTANT)) { //hide 3 dot of menu
            spamMenu.setVisibility(View.VISIBLE);
        } else {
            spamMenu.setVisibility(View.GONE);
        }
    }

    private void invalidateLikeView(Comment item) {
        if(item.likeCount > 0){
            mCommentLike.setText(Integer.toString(item.likeCount));
        }else {
            mCommentLike.setText("");
        }
        if (item.isLiked) {
            mCommentLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_likes_heart, 0, 0, 0);
        } else {
            mCommentLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_likes_heart_in_active, 0, 0, 0);
        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.user_profile_pic)
    public void onUserImageClick() {
      mCommentCallback.userProfileNameClick(mComment,  mUserProfilePic);
    }

    @OnClick(R.id.comment_author_name)
    public void onUserNameClick() {
      mCommentCallback.userProfileNameClick(mComment,  mCommentAuthorName);
    }

    @OnClick(R.id.spam_comment_ui)
    public void onSpamCommentMenuClick() {
        mComment.setItemPosition(getAdapterPosition());
        mCommentCallback.onCommentMenuClicked(mComment, spamMenu);
    }

    @OnClick(R.id.tv_user_comment_list_menu)
    public void onCommentMenuClick() {
        mComment.setItemPosition(getAdapterPosition());
        mCommentCallback.onCommentMenuClicked(mComment, mUserCommentListMenu);
    }
    @OnClick(R.id.tv_list_user_comment)
    public void onCommentWithNameClick() {
       /* if (mComment.isVerifiedMentor()) {
            viewInterface.navigateToProfileView(mComment, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
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
    private void clickOnUserMentionName(String description,List<MentionSpan> mentionSpanList) {
        StringBuilder strWithAddExtra = new StringBuilder(description+" ");
        for (int i = 0; i <  mentionSpanList.size(); i++) {
            final MentionSpan mentionSpan = mentionSpanList.get(i);
            if (null != mentionSpan && null != mentionSpan.getMention()) {
                if (mentionSpan.getMention().getStartIndex() + i <= strWithAddExtra.length() -1) {
                    strWithAddExtra.insert(mentionSpan.getMention().getStartIndex()+i, '@');
                }
            }
        }
        SpannableString spannableString = new SpannableString(strWithAddExtra);
        for (int i = 0; i <  mentionSpanList.size(); i++) {
            final MentionSpan mentionSpan = mentionSpanList.get(i);
            if(null!=mentionSpan&&null!=mentionSpan.getMention()) {
                final ClickableSpan postedInClick = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        Comment comment = new Comment();
                        long participantId = mentionSpan.getMention().userId;
                        comment.setParticipantUserId(participantId);
                        if(mentionSpan.getMention().getUserType()==7) {
                            comment.setVerifiedMentor(true);
                        }else
                        {
                            comment.setVerifiedMentor(false);
                        }
                        mCommentCallback.userProfileNameClick(comment, mCommentAuthorName);
                    }

                    @Override
                    public void updateDrawState(final TextPaint textPaint) {
                        textPaint.setUnderlineText(false);
                    }
                };
                int start=mentionSpan.getMention().getStartIndex()+i;
                int end=mentionSpan.getMention().getEndIndex()+i;
                if (end + 1 <= spannableString.length() && start <= spannableString.length()) {
                    spannableString.setSpan(postedInClick, start, end+1, 0);
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.user_tagg)), start, end+1, 0);
                }

            }
        }
        mUserComment.setMovementMethod(LinkMovementMethod.getInstance());
        mUserComment.setText(hashTagColorInString(spannableString), TextView.BufferType.SPANNABLE);

        // tvMention.setSelected(true);
    }
}