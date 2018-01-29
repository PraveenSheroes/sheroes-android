package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.ContestStatus;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ujjwal on 10/10/17.
 */

public class ChallengeFeedHolder extends BaseViewHolder<FeedDetail> {

    @Bind(R.id.card_challenge)
    CardView mCardChallenge;

    @Bind(R.id.author_image)
    ImageView mAuthorImage;

    @Bind(R.id.author_name)
    TextView mAuthorName;

    @Bind(R.id.share)
    ImageView mShare;

    @Bind(R.id.feature_image)
    ImageView mFeatureImage;

    @Bind(R.id.contest_tag)
    TextView mContestTag;

    @Bind(R.id.title)
    TextView mTitle;

    @Bind(R.id.contest_end_text)
    TextView mContestEndText;

    @Bind(R.id.contest_status)
    TextView mContestStatus;

    @Bind(R.id.live_dot)
    ImageView mLiveDot;

    @Bind(R.id.join_challenge_text)
    TextView mJoinChallengeText;

    @Bind(R.id.response_view_count)
    TextView mResponseViewCount;

    BaseHolderInterface viewInterface;
    private Contest mContest;
    private FeedDetail mFeedDetail;
    private Context mContext;

    ValueAnimator mAlphaAnimator;

    public ChallengeFeedHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail feedDetail, Context context, int position) {
        mContext = context;
        mFeedDetail = feedDetail;
        ChallengeSolrObj challengeSolrObj = (ChallengeSolrObj) feedDetail;
        mContest = new Contest();
        if (StringUtil.isNotNullOrEmptyString(challengeSolrObj.getPostShortBranchUrls())) {
            mContest.shortUrl = challengeSolrObj.getPostShortBranchUrls();
        } else {
            mContest.shortUrl = challengeSolrObj.getDeepLinkUrl();
        }
        mContest.title = challengeSolrObj.getChallengeTitle();
        mContest.remote_id = (int) challengeSolrObj.getIdOfEntityOrParticipant();
        mContest.body = challengeSolrObj.getListDescription();
        mContest.createdDateString = challengeSolrObj.getChallengeStartDate();
        mContest.endDateString = challengeSolrObj.getChallengeEndDate();
        mContest.hasWinner = challengeSolrObj.isChallengeHasWinner();
        mContest.isWinner = challengeSolrObj.isChallengeIsWinner();
        mContest.authorName = challengeSolrObj.getAuthorName();
        mContest.authorType = challengeSolrObj.getChallengeAuthorTypeS();
        mContest.authorImageUrl = challengeSolrObj.getAuthorImageUrl();
        mContest.submissionCount = challengeSolrObj.getNoOfChallengeAccepted();
        mContest.hasMyPost = challengeSolrObj.isChallengeAccepted();
        mContest.tag = challengeSolrObj.getChallengeAcceptPostText();
        mContest.thumbImage = challengeSolrObj.getThumbnailImageUrl();
        mContest.mWinnerAddress = challengeSolrObj.getWinnerAddress();
        mContest.winnerAddressUpdated = challengeSolrObj.winnerAddressUpdated;
        mContest.winnerAnnouncementDate = challengeSolrObj.getChallengeAnnouncementDate(); //Fix for winner announcement
        mTitle.setText(mContest.title);
        int featureImageHeight = (CommonUtil.getWindowWidth(mContext) / 2);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, featureImageHeight);
        mFeatureImage.setLayoutParams(params);

        ContestStatus contestStatus = CommonUtil.getContestStatus(mContest.getStartAt(), mContest.getEndAt());
        if (contestStatus == ContestStatus.ONGOING) {
            mContestEndText.setText("Ends" + " " + DateUtil.getRelativeTimeSpanString(mContest.getEndAt()));
            mContestStatus.setText(R.string.contest_status_ongoing);
            mLiveDot.setImageResource(R.drawable.vector_live_dot);
            animateLiveDot();
            mContestStatus.setVisibility(View.VISIBLE);
        }
        if (contestStatus == ContestStatus.UPCOMING) {
            mContestStatus.setText(context.getString(R.string.contest_status_upcoming));
            mLiveDot.setVisibility(View.GONE);
            mContestStatus.setVisibility(View.VISIBLE);
        }
        if (contestStatus == ContestStatus.COMPLETED) {
            mContestStatus.setText(context.getString(R.string.contest_status_completed));
            mContestStatus.setTextColor(context.getResources().getColor(R.color.light_green));
            mLiveDot.setImageResource(R.drawable.vector_contest_completed);
            mContestStatus.setVisibility(View.VISIBLE);
        }
        if (mContest.hasMyPost) {
            mJoinChallengeText.setText("completed");
            mJoinChallengeText.setTextColor(context.getResources().getColor(R.color.light_green));
            mJoinChallengeText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_contest_completed, 0, 0, 0);
        } else {
            mJoinChallengeText.setText("Join the Challenge");
            mJoinChallengeText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            mJoinChallengeText.setTextColor(context.getResources().getColor(R.color.email));
        }
        mResponseViewCount.setText(Integer.toString(mContest.submissionCount) + " " + mContext.getResources().getQuantityString(R.plurals.numberOfResponses, mContest.submissionCount));
        if (CommonUtil.isNotEmpty(mContest.tag)) {
            String tag = "#" + mContest.tag;
            String tagText = tag + " " + "Challenge";
            final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(tagText);
            final ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.email));
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, tag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mContestTag.setText(spannableStringBuilder);
        } else {
            mContestTag.setText("Challenge");
        }

        if (CommonUtil.isNotEmpty(mContest.thumbImage)) {
            String imageKitUrl = CommonUtil.getImgKitUri(mContest.thumbImage, CommonUtil.getWindowWidth(mContext), featureImageHeight);
            if (CommonUtil.isNotEmpty(imageKitUrl)) {
                Glide.with(mContext)
                        .load(imageKitUrl)
                        .into(mFeatureImage);
            }
        } else {
            mFeatureImage.setImageResource(R.drawable.challenge_placeholder);
        }

        if (mContest != null && CommonUtil.isNotEmpty(mContest.authorImageUrl)) {
            Glide.with(mContext)
                    .load(mContest.authorImageUrl)
                    .bitmapTransform(new CommonUtil.CircleTransform(mContext))
                    .into(mAuthorImage);
        }
        mAuthorName.setText(mContest.authorName);
    }

    @Override
    public void viewRecycled() {

    }


    @OnClick(R.id.card_challenge)
    public void onChallengeClicked() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onChallengeClicked(mContest);
        } else {
            viewInterface.contestOnClick(mContest, mCardChallenge);
        }
    }

    @OnClick(R.id.share)
    public void onShareClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onChallengePostShared(mFeedDetail);
        } else {
            viewInterface.handleOnClick(mFeedDetail, mShare);
        }
    }

    @Override
    public void onClick(View view) {

    }

    private void animateLiveDot() {
        mAlphaAnimator = ObjectAnimator.ofFloat(mLiveDot, "alpha", 0f, 1f);
        mAlphaAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mAlphaAnimator.setRepeatCount(Animation.INFINITE);
        mAlphaAnimator.setDuration(300);

        Interpolator mSelectedInterpolator = new FastOutSlowInInterpolator();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(mSelectedInterpolator);
        animatorSet.play(mAlphaAnimator);
        animatorSet.start();
    }
}

