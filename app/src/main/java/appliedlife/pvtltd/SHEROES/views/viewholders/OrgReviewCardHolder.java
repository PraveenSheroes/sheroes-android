package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.f2prateek.rx.preferences.Preference;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by deepakpoptani on 18/09/17.
 */

public class OrgReviewCardHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(EventCardHolder.class);
    BaseHolderInterface viewInterface;
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
    @Bind(R.id.tv_feed_review_post_text_full_view)
    TextView tvReviewPostFullText;
    @Bind(R.id.tv_feed_review_post_view_more)
    TextView tvReviewPostMoreText;
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



    private String mViewMoreDescription;
    private String mViewMore, mLess;

    @Inject
    Preference<LoginResponse> userPreference;
    private FeedDetail dataItem;
    private Context mContext;
    private long mUserId;
    private int mAdminId;
    public OrgReviewCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            mUserId = userPreference.get().getUserSummary().getUserId();
            if(null != userPreference.get().getUserSummary().getUserBO()) {
                mAdminId = userPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
        }

    }


    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        mContext = context;
        dataItem.setItemPosition(position);
        organisationReviewPostUI(context);
    }


    private void organisationReviewPostUI(Context context){
        mViewMore = context.getString(R.string.ID_VIEW_MORE);
        mLess = context.getString(R.string.ID_LESS);
        tvReviewPostText.setTag(mViewMore);
        tvReviewPostFullText.setTag(mViewMore);
        upvoteReacted.setEnabled(true);
        tvNoOfUpVotes.setEnabled(true);
        imageOperations(context);
        allReviewTextViewAndImageOperations(context);
    }



    private void imageOperations(Context context) {
        String authorImageUrl = dataItem.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(authorImageUrl)) {
                ivReviewPostAuthor.setCircularImage(true);
                ivReviewPostAuthor.bindImage(authorImageUrl);
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void allReviewTextViewAndImageOperations(Context context){

        if(StringUtil.isNotNullOrEmptyString(dataItem.getAuthorName())) {
            StringBuilder postText = new StringBuilder();
            tvReviewPostText.setVisibility(View.VISIBLE);
            String feedAuthorTitle = dataItem.getAuthorName();
            String communityName = dataItem.getPostCommunityName();
            if (StringUtil.isNotNullOrEmptyString(feedAuthorTitle) && StringUtil.isNotNullOrEmptyString(communityName)) {
                if (!feedAuthorTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                    if (dataItem.isAuthorMentor()) {
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
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(dataItem.getCreatedDate(), AppConstants.DATE_FORMAT);
            tvReviewPostTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
        }

        if (!dataItem.isTrending()) {
            if (StringUtil.isNotNullOrEmptyString(dataItem.getSolrIgnorePostCommunityLogo())) {
                Glide.with(context)
                        .load(dataItem.getSolrIgnorePostCommunityLogo())
                        .into(ivCompanyThumbnail);
            }
            if (!dataItem.isCommentAllowed()) {
                tvComapnyRating.setText(String.valueOf(dataItem.getRating()));
            }
        }
        mViewMoreDescription = dataItem.getListDescription();
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            int index = 0;
            int lengthOfDesc = mViewMoreDescription.length();
            try {
                if (lengthOfDesc < AppConstants.WORD_LENGTH) {
                    if (mViewMoreDescription.contains(AppConstants.SLASH_N))
                        index = AppUtils.findNthIndexOf(mViewMoreDescription, AppConstants.SLASH_N, 1);
                }
            } catch (Exception e) {

            }
            if (lengthOfDesc > AppConstants.WORD_LENGTH || index > 0 && index < 50) {
                tvReviewPostMoreText.setVisibility(View.VISIBLE);
                tvReviewPostMoreText.setTag(mViewMore);
                tvReviewPostMoreText.setText(mContext.getString(R.string.ID_VIEW_MORE));
                tvReviewPostText.setText(StringEscapeUtils.unescapeHtml4(mViewMoreDescription));
                tvReviewPostFullText.setVisibility(View.GONE);
                tvReviewPostText.setVisibility(View.VISIBLE);
            } else {
                tvReviewPostMoreText.setText(mContext.getString(R.string.ID_LESS));
                tvReviewPostMoreText.setTag(mLess);
                tvReviewPostMoreText.setVisibility(View.GONE);
                tvReviewPostFullText.setVisibility(View.VISIBLE);
                tvReviewPostText.setVisibility(View.GONE);
                tvReviewPostFullText.setText(StringEscapeUtils.unescapeHtml4(mViewMoreDescription));
            }

        } else {
            tvReviewPostMoreText.setText(mContext.getString(R.string.ID_LESS));
            tvReviewPostMoreText.setTag(mLess);
            tvReviewPostFullText.setVisibility(View.GONE);
            tvReviewPostText.setVisibility(View.GONE);
            tvReviewPostMoreText.setVisibility(View.GONE);
        }
        setUpvoteButtonAndText();
    }


    private void setUpvoteButtonAndText(){
        String upvoteText = "";
        int likes = dataItem.getNoOfLikes();
        if(likes == 0){
            upvoteText=mContext.getString(R.string.ID_REVIEW_POST_NO_UPVOTE);
        }
        else if(likes == 1) {
            upvoteText=String.valueOf(dataItem.getNoOfLikes())+AppConstants.SPACE+mContext.getString(R.string.ID_REVIEW_POST_NO_UPVOTE);
        }else{
            upvoteText=String.valueOf(dataItem.getNoOfLikes())+AppConstants.SPACE+mContext.getString(R.string.ID_REVIEW_POST_NON_ZERO_UPVOTES);
        }
        switch (dataItem.getReactionValue()){
            case AppConstants.NO_REACTION_CONSTANT:
                upvoteReacted.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_upvote_inactive, 0, 0, 0);
                tvNoOfUpVotes.setText(upvoteText);
                break;
            default:
                upvoteReacted.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_upvote_active,0,0,0);
                tvNoOfUpVotes.setText(upvoteText);
        }
    }


    @OnClick(R.id.tv_feed_review_post_user_share_ic)
    public void reviewShareClick(){
        viewInterface.handleOnClick(dataItem,reviewPostShareIc);
        ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_ORGANISATION_REVIEW_POST, dataItem.communityId + AppConstants.DASH + mUserId + AppConstants.DASH + dataItem.getIdOfEntityOrParticipant());
    }


    @OnClick(R.id.tv_review_upvote_reacted)
    public void onReviewUpvoteClick(){
        upvoteReacted.setEnabled(false);
        dataItem.setLongPress(false);
        dataItem.setTrending(true);
        if(dataItem.getReactionValue()  != AppConstants.NO_REACTION_CONSTANT){
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UNDO_REACTIONS, GoogleAnalyticsEventActions.UNDO_REACTIONS_ON_ORGANISATION_REVIEW_POST, dataItem.getCommunityId()+AppConstants.DASH+mUserId+AppConstants.DASH+dataItem.getIdOfEntityOrParticipant());
        }else{
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.HEART_REACTION_CONSTANT, getAdapterPosition());
            ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_REACTIONS, GoogleAnalyticsEventActions.REACTED_TO_ORGANISATION_REVIEW_POST, dataItem.getCommunityId()+AppConstants.DASH+mUserId+AppConstants.DASH+dataItem.getIdOfEntityOrParticipant());

        }
        if(dataItem.getReactionValue() != AppConstants.NO_REACTION_CONSTANT){
            dataItem.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
            dataItem.setNoOfLikes(dataItem.getNoOfLikes() - AppConstants.ONE_CONSTANT);
        }else{
            dataItem.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
            dataItem.setNoOfLikes(dataItem.getNoOfLikes() + AppConstants.ONE_CONSTANT);
        }
        setUpvoteButtonAndText();
    }





    private void clickOnMentorAndCommunityName(String nameAndCommunity, String feedTitle, String postedIn) {

        SpannableString SpanString = new SpannableString(nameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                if (dataItem.isAuthorMentor()) {
                    viewInterface.championProfile(dataItem, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
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
                    viewInterface.handleOnClick(dataItem, tvReviewPostTitle);

            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        if(StringUtil.isNotNullOrEmptyString(feedTitle)) {
            SpanString.setSpan(authorTitle, 0, feedTitle.length(), 0);
            if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                if (dataItem.isAuthorMentor()) {
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.footer_icon_text)), 0, feedTitle.length(), 0);
                } else {
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
                }
            }else {
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
            }

            if(StringUtil.isNotNullOrEmptyString(postedIn)&&StringUtil.isNotNullOrEmptyString(nameAndCommunity)) {
                SpanString.setSpan(postedInClick, feedTitle.length(), feedTitle.length() + postedIn.length()+3, 0);
                SpanString.setSpan(community, feedTitle.length() + postedIn.length()+2, nameAndCommunity.length(), 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.posted_in)),  feedTitle.length(), feedTitle.length() + postedIn.length()+3, 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.footer_icon_text)),feedTitle.length() + postedIn.length()+2, nameAndCommunity.length(), 0);
            }
                tvReviewPostTitle.setMovementMethod(LinkMovementMethod.getInstance());
                tvReviewPostTitle.setText(SpanString, TextView.BufferType.SPANNABLE);
                tvReviewPostTitle.setSelected(true);

        }
    }


    @OnClick(R.id.tv_feed_review_post_view_more)
    public void textViewMoreClick() {
        mViewMoreDescription=dataItem.getListDescription();
        if (StringUtil.isNotNullOrEmptyString(mViewMoreDescription)) {
            int index = 0;
            int lengthOfDesc = mViewMoreDescription.length();
            try {
                if (lengthOfDesc < AppConstants.WORD_LENGTH) {
                    if (mViewMoreDescription.contains(AppConstants.SLASH_N))
                        index = AppUtils.findNthIndexOf(mViewMoreDescription, AppConstants.SLASH_N, 1);
                }
            } catch (Exception e) {

            }
            if (lengthOfDesc > AppConstants.WORD_LENGTH || index > 0 && index < 50) {
                viewMoreTextClick();
            }
        }
    }


    private void viewMoreTextClick() {
        if (tvReviewPostMoreText.getTag().toString().equalsIgnoreCase(mViewMore)) {
            tvReviewPostMoreText.setText(mContext.getString(R.string.ID_LESS));
            tvReviewPostMoreText.setTag(mLess);
            tvReviewPostText.setTag(mLess);
            tvReviewPostFullText.setTag(mLess);
            tvReviewPostFullText.setVisibility(View.VISIBLE);
            tvReviewPostText.setVisibility(View.GONE);
            tvReviewPostFullText.setText(StringEscapeUtils.unescapeHtml4(mViewMoreDescription));
            tvReviewPostFullText.scrollTo(0, 0);
        } else {
            mViewMoreDescription=dataItem.getShortDescription();
            tvReviewPostFullText.setVisibility(View.GONE);
            tvReviewPostText.setVisibility(View.VISIBLE);
            tvReviewPostText.setTag(mViewMore);
            tvReviewPostFullText.setTag(mViewMore);
            int index = 0;
            int lengthOfDesc = mViewMoreDescription.length();
            try {
                if (lengthOfDesc < AppConstants.WORD_LENGTH) {
                    if (mViewMoreDescription.contains(AppConstants.SLASH_N))
                        index = AppUtils.findNthIndexOf(mViewMoreDescription, AppConstants.SLASH_N, 1);
                }
            } catch (Exception e) {

            }
            if (lengthOfDesc > AppConstants.WORD_LENGTH || index > 0 && index < 50) {
                tvReviewPostMoreText.setVisibility(View.VISIBLE);
                tvReviewPostMoreText.setText(mContext.getString(R.string.ID_VIEW_MORE));
                tvReviewPostMoreText.setTag(mViewMore);
                tvReviewPostText.setText(StringEscapeUtils.unescapeHtml4(mViewMoreDescription));
                tvReviewPostFullText.scrollTo(0, 0);
            } else {
                tvReviewPostMoreText.setVisibility(View.GONE);
                tvReviewPostMoreText.setText(mContext.getString(R.string.ID_LESS));
                tvReviewPostMoreText.setTag(mLess);
                tvReviewPostFullText.setText(StringEscapeUtils.unescapeHtml4(mViewMoreDescription));
                tvReviewPostText.scrollTo(0, 0);
            }
        }
    }


    @Override
    public void onClick(View view) {
    }

    @Override
    public void viewRecycled() {
    }

}
