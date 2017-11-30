package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.linkifyURLs;

/**
 * Created by deepakpoptani on 18/09/17.
 */
public class OrgReviewCardHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(EventCardHolder.class);
    BaseHolderInterface viewInterface;
    private static final String LEFT_HTML_TAG = "<font color='#3c3c3c'>";
    private static final String RIGHT_HTML_TAG = "</font>";
    @Inject
    DateUtil mDateUtil;
    //Organisations handling
    @Bind(R.id.iv_feed_review_post_author_icon)
    CircleImageView ivReviewPostAuthor;
    @Bind(R.id.iv_review_author_is_mentor)
    ImageView ivReviewPostMentorVerified;
    @Bind(R.id.tv_feed_review_card_title)
    TextView tvReviewPostTitle;
    @Bind(R.id.tv_feed_review_card_time)
    TextView tvReviewPostTime;
    @Bind(R.id.tv_feed_review_post_text)
    TextView tvReviewPostText;
    @Bind(R.id.tv_feed_orgnization_view_more)
    TextView tvOrganizationViewMore;
    @Bind(R.id.iv_company_icon)
    ImageView ivCompanyThumbnail;
    @Bind(R.id.tv_review_company_title)
    TextView tvCompanyTitle;
    @Bind(R.id.tv_review_company_rating)
    TextView tvComapnyRating;
    @Bind(R.id.tv_review_upvote_reacted)
    TextView upvoteReacted;
    @Bind(R.id.tv_no_of_upvotes)
    TextView tvNoOfUpVotes;
    @Bind(R.id.tv_feed_review_post_user_share_ic)
    TextView reviewPostShareIc;
    @Bind(R.id.tv_review_post_share)
    TextView tvReviewPostShare;
    private Handler mHandler;
    @Inject
    Preference<LoginResponse> userPreference;
    private UserPostSolrObj userPostObj;
    private Context mContext;
    private long mUserId;

    public OrgReviewCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mHandler = new Handler();
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            mUserId = userPreference.get().getUserSummary().getUserId();
        }
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        userPostObj = (UserPostSolrObj) item;
        mContext = context;
        userPostObj.setItemPosition(position);
        organisationReviewPostUI(context);
    }


    private void organisationReviewPostUI(Context context) {
        upvoteReacted.setEnabled(true);
        tvNoOfUpVotes.setEnabled(true);
        imageOperations(context);
        allReviewTextViewAndImageOperations(context);
    }


    private void imageOperations(Context context) {
        String authorImageUrl = userPostObj.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(authorImageUrl)) {
            ivReviewPostAuthor.setCircularImage(true);
            ivReviewPostAuthor.bindImage(authorImageUrl);
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void allReviewTextViewAndImageOperations(Context context) {

        if (StringUtil.isNotNullOrEmptyString(userPostObj.getAuthorName())) {
            StringBuilder postText = new StringBuilder();
            tvReviewPostText.setVisibility(View.VISIBLE);
            String feedAuthorTitle = userPostObj.getAuthorName();
            String communityName = userPostObj.getPostCommunityName();
            if (StringUtil.isNotNullOrEmptyString(feedAuthorTitle) && StringUtil.isNotNullOrEmptyString(communityName)) {
                if (!feedAuthorTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                    if (userPostObj.isAuthorMentor()) {
                        ivReviewPostMentorVerified.setVisibility(View.VISIBLE);
                    } else {
                        ivReviewPostMentorVerified.setVisibility(View.GONE);
                    }
                } else {
                    ivReviewPostMentorVerified.setVisibility(View.GONE);
                }
                if (!feedAuthorTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                    postText.append(feedAuthorTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_POSTED_REVIEW)).append(AppConstants.SPACE).append(communityName);

                } else {
                    postText.append(feedAuthorTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.ID_POSTED_REVIEW)).append(AppConstants.SPACE).append(communityName);
                }
                clickOnMentorAndCommunityName(postText.toString(), feedAuthorTitle, mContext.getString(R.string.ID_POSTED_REVIEW));
                tvCompanyTitle.setText(communityName);
            }
        }
        if (StringUtil.isNotNullOrEmptyString(userPostObj.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(userPostObj.getCreatedDate(), AppConstants.DATE_FORMAT);
            tvReviewPostTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
        }

        if (!userPostObj.isTrending()) {
            if (StringUtil.isNotNullOrEmptyString(userPostObj.getSolrIgnorePostCommunityLogo())) {
                Glide.with(context)
                        .load(userPostObj.getSolrIgnorePostCommunityLogo())
                        .into(ivCompanyThumbnail);
            }
            if (!userPostObj.isCommentAllowed()) {
                tvComapnyRating.setText(String.valueOf(userPostObj.getRating()));
            }
        }
        populatePostText();
        setUpvoteButtonAndText();
    }
    private void populatePostText() {
        final String listDescription = userPostObj.getListDescription();
        if (!StringUtil.isNotNullOrEmptyString(listDescription)) {
            return;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                tvReviewPostText.setMaxLines(Integer.MAX_VALUE);
                tvReviewPostText.setText(listDescription);
                linkifyURLs(tvReviewPostText);
                if (tvReviewPostText.getLineCount() > 4) {
                    collapseFeedPostText();
                } else {
                    tvReviewPostText.setVisibility(View.VISIBLE);
                    tvOrganizationViewMore.setVisibility(View.GONE);
                }
            }
        });
    }
    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void collapseFeedPostText() {
        tvReviewPostText.setMaxLines(4);
        tvReviewPostText.setVisibility(View.VISIBLE);
        String dots = LEFT_HTML_TAG + AppConstants.DOTS + RIGHT_HTML_TAG;
        if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
            tvOrganizationViewMore.setText(Html.fromHtml(dots +mContext.getString(R.string.ID_VIEW_MORE), 0)); // for 24 api and more
        } else {
            tvOrganizationViewMore.setText(Html.fromHtml(dots + mContext.getString(R.string.ID_VIEW_MORE)));// or for older api
        }
        tvOrganizationViewMore.setVisibility(View.VISIBLE);
    }

    private void expandFeedPostText() {
        tvReviewPostText.setMaxLines(Integer.MAX_VALUE);
        tvReviewPostText.setVisibility(View.VISIBLE);
        tvOrganizationViewMore.setText(mContext.getString(R.string.ID_LESS));
        tvOrganizationViewMore.setVisibility(View.VISIBLE);
    }
    private void setUpvoteButtonAndText() {
        String upvoteText = "";
        int likes = userPostObj.getNoOfLikes();
        if (likes == 0) {
            upvoteText = mContext.getString(R.string.ID_REVIEW_POST_NO_UPVOTE);
        } else if (likes == 1) {
            upvoteText = String.valueOf(userPostObj.getNoOfLikes()) + AppConstants.SPACE + mContext.getString(R.string.ID_REVIEW_POST_NO_UPVOTE);
        } else {
            upvoteText = String.valueOf(userPostObj.getNoOfLikes()) + AppConstants.SPACE + mContext.getString(R.string.ID_REVIEW_POST_NON_ZERO_UPVOTES);
        }
        switch (userPostObj.getReactionValue()) {
            case AppConstants.NO_REACTION_CONSTANT:
                upvoteReacted.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_upvote_inactive, 0, 0, 0);
                tvNoOfUpVotes.setText(upvoteText);
                break;
            default:
                upvoteReacted.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_upvote_active, 0, 0, 0);
                tvNoOfUpVotes.setText(upvoteText);
        }
    }

    @OnClick(R.id.tv_feed_review_post_user_share_ic)
    public void reviewShareClick() {
        viewInterface.handleOnClick(userPostObj, reviewPostShareIc);
        ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_ORGANISATION_REVIEW_POST, userPostObj.communityId + AppConstants.DASH + mUserId + AppConstants.DASH + userPostObj.getIdOfEntityOrParticipant());
    }


    @OnClick(R.id.tv_review_upvote_reacted)
    public void onReviewUpvoteClick() {
        upvoteReacted.setEnabled(false);
        userPostObj.setLongPress(false);
        userPostObj.setTrending(true);
        if (userPostObj.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            viewInterface.userCommentLikeRequest(userPostObj, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UNDO_REACTIONS, GoogleAnalyticsEventActions.UNDO_REACTIONS_ON_ORGANISATION_REVIEW_POST, userPostObj.getCommunityId() + AppConstants.DASH + mUserId + AppConstants.DASH + userPostObj.getIdOfEntityOrParticipant());
        } else {
            viewInterface.userCommentLikeRequest(userPostObj, AppConstants.HEART_REACTION_CONSTANT, getAdapterPosition());
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_REACTIONS, GoogleAnalyticsEventActions.REACTED_TO_ORGANISATION_REVIEW_POST, userPostObj.getCommunityId() + AppConstants.DASH + mUserId + AppConstants.DASH + userPostObj.getIdOfEntityOrParticipant());

        }
        if (userPostObj.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            userPostObj.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
            userPostObj.setNoOfLikes(userPostObj.getNoOfLikes() - AppConstants.ONE_CONSTANT);
        } else {
            userPostObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
            userPostObj.setNoOfLikes(userPostObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
        }
        setUpvoteButtonAndText();
    }


    private void clickOnMentorAndCommunityName(String nameAndCommunity, String feedTitle, String postedIn) {

        SpannableString SpanString = new SpannableString(nameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                if (userPostObj.isAuthorMentor()) {
                    viewInterface.championProfile(userPostObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                }
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        ClickableSpan postedInClick = new ClickableSpan() {
            @Override
            public void onClick(View textView) {


            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };

        ClickableSpan community = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                viewInterface.handleOnClick(userPostObj, tvReviewPostTitle);

            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        if (StringUtil.isNotNullOrEmptyString(feedTitle)) {
            SpanString.setSpan(authorTitle, 0, feedTitle.length(), 0);
            if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                if (userPostObj.isAuthorMentor()) {
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.footer_icon_text)), 0, feedTitle.length(), 0);
                } else {
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
                }
            } else {
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
            }

            if (StringUtil.isNotNullOrEmptyString(postedIn) && StringUtil.isNotNullOrEmptyString(nameAndCommunity)) {
                SpanString.setSpan(postedInClick, feedTitle.length(), feedTitle.length() + postedIn.length() + 3, 0);
                SpanString.setSpan(community, feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.posted_in)), feedTitle.length(), feedTitle.length() + postedIn.length() + 3, 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.footer_icon_text)), feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);
            }
            tvReviewPostTitle.setMovementMethod(LinkMovementMethod.getInstance());
            tvReviewPostTitle.setText(SpanString, TextView.BufferType.SPANNABLE);
            tvReviewPostTitle.setSelected(true);

        }
    }
    @OnClick(R.id.tv_feed_orgnization_view_more)
    public void onViewMoreClicked(){
        if (tvOrganizationViewMore.getText().equals(mContext.getString(R.string.ID_LESS))) {
            collapseFeedPostText();
        } else {
            expandFeedPostText();
        }
    }
    @Override
    public void onClick(View view) {
    }

    @Override
    public void viewRecycled() {
    }

}
