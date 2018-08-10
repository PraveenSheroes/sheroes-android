package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.PostDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.feed.PollSolarObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.hashTagColorInString;
import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.linkifyURLs;

public class FeedPollCardHolder extends BaseViewHolder<PollSolarObj> {
    private static final String LEFT_HTML_TAG = "<font color='#3c3c3c'>";
    private static final String RIGHT_HTML_TAG = "</font>";
    //region Inject variables
    @Inject
    DateUtil mDateUtil;

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    Preference<Configuration> mConfiguration;

    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;

    //endregion

    //region views

    @Bind(R.id.li_poll_main_layout)
    LinearLayout mLiPollMainLayout;

    @Bind(R.id.iv_feed_poll_circle_icon)
    CircleImageView mIvFeedPollCircleIcon;

    @Bind(R.id.iv_feed_poll_circle_icon_verified)
    ImageView mIvFeedPollCircleIconVerified;

    @Bind(R.id.tv_feed_poll_card_title)
    TextView mTvFeedPollCardTitle;

    @Bind(R.id.tv_feed_poll_time)
    TextView mTvFeedCommunityPostTime;

    @Bind(R.id.tv_feed_poll_description)
    TextView mTvFeedPollDescription;

    @Bind(R.id.li_type_of_poll_view)
    LinearLayout mLiTypeOfPollView;

    @Bind(R.id.line_separate)
    View mLineSeparate;

    @Bind(R.id.rl_feed_poll_impression_count_view)
    RelativeLayout mRlFeedPollImpressionCountView;

    @Bind(R.id.tv_feed_poll_user_reaction)
    TextView mTvFeedPollUserReaction;

    @Bind(R.id.tv_feed_poll_user_share)
    TextView mTvFeedPollUserShare;

    @Bind(R.id.tv_feed_poll_user_comment)
    TextView tvFeedPollUserComment;

    @Bind(R.id.tv_feed_poll_total_reactions)
    TextView mTvFeedPollTotalReaction;

    @Bind(R.id.tv_feed_poll_total_votes)
    TextView mTvFeedPollTotalVotes;

    @Bind(R.id.tv_feed_poll_user_menu)
    TextView mTvFeedPollUserMenu;


    @BindDimen(R.dimen.poll_text_input_text_size)
    int mPollTextInputTextSize;


    @BindDimen(R.dimen.option_poll_margintop)
    int mPollMarginTop;

    @BindDimen(R.dimen.option_poll_margin_left_right)
    int mPollMarginLeftRight;

    @BindDimen(R.dimen.poll_text_input_padding)
    int mPollTextInputPadding;

    @BindDimen(R.dimen.dp_size_30)
    int authorPicSize;

    @BindDimen(R.dimen.dp_size_40)
    int authorPicSizeFourty;
    //endregion

    private BaseHolderInterface viewInterface;
    private PostDetailCallBack mPostDetailCallBack;
    private PollSolarObj mPollSolarObj;
    private Context mContext;
    private int mItemPosition;
    private long mUserId;
    private String loggedInUser;
    private int mAdminId;
    private String mPhotoUrl;
    private boolean isWhatappShareOption = false;
    private LayoutInflater inflater = null;

    public FeedPollCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        viewInterface = baseHolderInterface;
        initPollCardHolder();
    }

    public FeedPollCardHolder(View itemView, PostDetailCallBack postDetailCallBack) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mPostDetailCallBack = postDetailCallBack;
        initPollCardHolder();
    }

    private void initPollCardHolder() {
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary()) {
            mUserId = mUserPreference.get().getUserSummary().getUserId();
            if (null != mUserPreference.get().getUserSummary().getUserBO()) {
                mAdminId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
            if (StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
                mPhotoUrl = mUserPreference.get().getUserSummary().getPhotoUrl();
            }
            String first = mUserPreference.get().getUserSummary().getFirstName();
            String last = mUserPreference.get().getUserSummary().getLastName();
            if (StringUtil.isNotNullOrEmptyString(first) || StringUtil.isNotNullOrEmptyString(last)) {
                loggedInUser = first + AppConstants.SPACE + last;
            }
        }
        if (mUserPreferenceMasterData != null && mUserPreferenceMasterData.isSet() && mUserPreferenceMasterData.get().getData() != null && mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION) != null && !CommonUtil.isEmpty(mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION))) {
            String shareOption = "";
            shareOption = mUserPreferenceMasterData.get().getData().get(AppConstants.APP_CONFIGURATION).get(AppConstants.APP_SHARE_OPTION).get(0).getLabel();
            if (CommonUtil.isNotEmpty(shareOption)) {
                if (shareOption.equalsIgnoreCase("true")) {
                    isWhatappShareOption = true;
                }
            }
        }
    }

    @Override
    public void bindData(PollSolarObj item, final Context context, int position) {
        this.mPollSolarObj = item;
        mContext = context;
        mPollSolarObj.setItemPosition(position);
        //addTextPollInputViews();
        addImagePollViews();
        //  showPostUiFieldsWithData();
    }

    @Override
    public void viewRecycled() {

    }
    //region private methods

    private void addTextPollInputViews() {
        final RadioGroup rgTextPollInput = new RadioGroup(mContext);
        for (int i = 1; i < 5; i++) {
            final RadioButton radioButton = new RadioButton(mContext);
            radioButton.setButtonDrawable(null);
            radioButton.setTextSize(mPollTextInputTextSize);
            radioButton.setBackgroundResource(R.drawable.selecter_text_poll_type);
            radioButton.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            radioButton.setPadding(mPollTextInputPadding, mPollTextInputPadding, mPollTextInputPadding, mPollTextInputPadding);
            radioButton.setText("Data " + i);
            LinearLayout.LayoutParams radioParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
            radioParam.setMargins(0, mPollMarginTop, 0, mPollMarginTop);
            radioButton.setLayoutParams(radioParam);
            rgTextPollInput.addView(radioButton);
        }
        rgTextPollInput.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) group.getChildAt(i);
                    if (radioButton.isChecked()) {
                        radioButton.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    } else {
                        radioButton.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
                    }
                }
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
        params.setMargins(mPollMarginLeftRight, mPollMarginTop, mPollMarginLeftRight, mPollMarginTop);
        rgTextPollInput.setLayoutParams(params);
        mLiTypeOfPollView.addView(rgTextPollInput);
    }

    private void addTextPollResultViews() {
        final View pollLayout = LayoutInflater.from(mContext).inflate(R.layout.feed_poll_card_textpoll_result_layout, null);
        final LinearLayout liImageRatingRow = pollLayout.findViewById(R.id.li_feed_text_poll_row);
        ProgressBar pbPollPercent = pollLayout.findViewById(R.id.pb_poll_percent);
        TextView tvPollPercentNumber = pollLayout.findViewById(R.id.tv_poll_percent_number);
        ImageView ivPercentVerified = pollLayout.findViewById(R.id.iv_percent_verified);
        pbPollPercent.setProgress(50);
        tvPollPercentNumber.setText(50 + "%");
        ivPercentVerified.setVisibility(View.GONE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
        params.setMargins(mPollMarginLeftRight, mPollMarginTop, mPollMarginLeftRight, mPollMarginTop);
        liImageRatingRow.setLayoutParams(params);
        mLiTypeOfPollView.addView(liImageRatingRow);
    }

    private void addImagePollViews() {
        final View pollLayout = LayoutInflater.from(mContext).inflate(R.layout.feed_poll_card_imagepoll_layout, null);
        final LinearLayout liImageRatingRow = pollLayout.findViewById(R.id.li_feed_imagepoll_row);
        final ConstraintLayout clImagePollLeftContainer = pollLayout.findViewById(R.id.cl_imagepoll_left_container);
        final ConstraintLayout clImagePollRightContainer = pollLayout.findViewById(R.id.cl_imagepoll_right_container);
        clImagePollLeftContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        clImagePollRightContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ProgressBar pbPollPercentLeft = pollLayout.findViewById(R.id.pb_imagepoll_percent_left);
        ProgressBar pbPollPercentRight = pollLayout.findViewById(R.id.pb_imagepoll_percent_right);
        TextView tvPollPercentNumberLeft = pollLayout.findViewById(R.id.tv_imagepoll_percent_count_left);
        TextView tvPollPercentNumberRight = pollLayout.findViewById(R.id.tv_imagepoll_percent_count_right);
        TextView tvImagePollNameLeft = pollLayout.findViewById(R.id.tv_imagepoll_name_left);
        TextView tvImagePollNameRight = pollLayout.findViewById(R.id.tv_imagepoll_name_right);
        ImageView ivFeedImagePollLeft = pollLayout.findViewById(R.id.iv_feed_image_poll_left);
        ImageView ivFeedImagePollRight = pollLayout.findViewById(R.id.iv_feed_image_poll_right);
        pbPollPercentLeft.setProgress(50);
        pbPollPercentRight.setProgress(65);
        tvPollPercentNumberLeft.setText(50 + "%");
        tvPollPercentNumberRight.setText(65+ "%");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
        params.setMargins(mPollMarginLeftRight, mPollMarginTop, mPollMarginLeftRight, mPollMarginTop);
        liImageRatingRow.setLayoutParams(params);
        mLiTypeOfPollView.addView(liImageRatingRow);
    }


    /**
     * Show post Ui content with data
     * Different Ui formats like Spam,Video and Image rendering etc.
     * Only Normal and link rendering views are handled.
     */
    private void showPostUiFieldsWithData() {
        mLiPollMainLayout.setVisibility(View.VISIBLE);
        mTvFeedPollUserReaction.setTag(true);
        mPollSolarObj.setLastReactionValue(mPollSolarObj.getReactionValue());
        populatePostedPollText();
        postDataContentRendering();
        likeCommentOps();
    }


    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void postDataContentRendering() {
        if (StringUtil.isNotNullOrEmptyString(mPollSolarObj.getAuthorName())) {
            StringBuilder posted = new StringBuilder();
            String feedTitle = mPollSolarObj.getAuthorName();
            posted.append(feedTitle).append(AppConstants.SPACE).append(mContext.getString(R.string.created_poll)).append(AppConstants.SPACE);
            clickOnCommunityName(posted.toString(), feedTitle, mContext.getString(R.string.created_poll));
        }
        if (StringUtil.isNotNullOrEmptyString(mPollSolarObj.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(mPollSolarObj.getCreatedDate(), AppConstants.DATE_FORMAT);
            mTvFeedCommunityPostTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
        } else {
            mTvFeedCommunityPostTime.setText(mContext.getString(R.string.ID_JUST_NOW));
        }
    }

    private void likeCommentOps() {
        if (mPollSolarObj.getNoOfLikes() < AppConstants.ONE_CONSTANT && mPollSolarObj.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            mTvFeedPollUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
            mRlFeedPollImpressionCountView.setVisibility(View.GONE);
        }
        switch (mPollSolarObj.getNoOfLikes()) {
            case AppConstants.NO_REACTION_CONSTANT:

                if (mPollSolarObj.getNoOfComments() > AppConstants.NO_REACTION_CONSTANT) {
                    mRlFeedPollImpressionCountView.setVisibility(View.VISIBLE);
                } else {
                    mRlFeedPollImpressionCountView.setVisibility(View.GONE);
                }
                userLike();
                break;
            case AppConstants.ONE_CONSTANT:
                mRlFeedPollImpressionCountView.setVisibility(View.VISIBLE);
                userLike();
                break;
            default:
                mRlFeedPollImpressionCountView.setVisibility(View.VISIBLE);
                userLike();
        }
        switch (mPollSolarObj.getNoOfComments()) {
            case AppConstants.NO_REACTION_CONSTANT:
                if (mPollSolarObj.getNoOfLikes() > AppConstants.NO_REACTION_CONSTANT) {
                    mRlFeedPollImpressionCountView.setVisibility(View.VISIBLE);
                } else {
                    mRlFeedPollImpressionCountView.setVisibility(View.GONE);
                }
                break;
            case AppConstants.ONE_CONSTANT:
                break;
            default:

        }
        String pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfVotes, mPollSolarObj.getNoOfComments());

        mTvFeedPollTotalVotes.setText(String.valueOf(mPollSolarObj.getNoOfComments() + AppConstants.SPACE + pluralComments));
    }

    private void populatePostedPollText() {
        if (isWhatappShareOption) {
            mTvFeedPollUserShare.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.ic_share_card), null, null, null);
            mTvFeedPollUserShare.setText(mContext.getString(R.string.ID_SHARE_ON_WHATS_APP));
            mTvFeedPollUserShare.setTextColor(ContextCompat.getColor(mContext, R.color.share_color));

        } else {
            mTvFeedPollUserShare.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.ic_share_white_out), null, null, null);
            mTvFeedPollUserShare.setText(mContext.getString(R.string.ID_SHARE));
            mTvFeedPollUserShare.setTextColor(ContextCompat.getColor(mContext, R.color.recent_post_comment));

        }
        final String listDescription = mPollSolarObj.getListDescription();
        if (!StringUtil.isNotNullOrEmptyString(listDescription)) {
            mTvFeedPollDescription.setVisibility(View.GONE);
        } else {
            mTvFeedPollDescription.setVisibility(View.VISIBLE);
            mTvFeedPollDescription.setText(hashTagColorInString(listDescription), TextView.BufferType.SPANNABLE);
            linkifyURLs(mTvFeedPollDescription);
        }
    }

    private void userLike() {

        switch (mPollSolarObj.getReactionValue()) {
            case AppConstants.NO_REACTION_CONSTANT:
                mTvFeedPollUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                break;
            case AppConstants.HEART_REACTION_CONSTANT:
                mTvFeedPollUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
                break;
            case AppConstants.EMOJI_FIRST_REACTION_CONSTANT:
                break;
            case AppConstants.EMOJI_SECOND_REACTION_CONSTANT:
                break;
            case AppConstants.EMOJI_THIRD_REACTION_CONSTANT:
                break;
            case AppConstants.EMOJI_FOURTH_REACTION_CONSTANT:
                break;
            default:
        }
    }


    private void clickOnCommunityName(String nameAndCommunity, String feedTitle, String postedIn) {

        SpannableString SpanString = new SpannableString(nameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                ((FeedItemCallback) viewInterface).onCommunityTitleClicked(mPollSolarObj);
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };

        ClickableSpan community = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        if (StringUtil.isNotNullOrEmptyString(feedTitle)) {
            SpanString.setSpan(authorTitle, 0, feedTitle.length(), 0);
            if (!feedTitle.equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                if (mPollSolarObj.isAuthorMentor()) {
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
                } else {
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
                }
                TypefaceSpan typefaceSpan = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_MEDIUM));
                SpanString.setSpan(typefaceSpan, 0, feedTitle.length(), 0);
            } else {
                TypefaceSpan typefaceSpan = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_REGULAR));
                SpanString.setSpan(typefaceSpan, 0, feedTitle.length(), 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_article_label)), 0, feedTitle.length(), 0);
            }

            if (StringUtil.isNotNullOrEmptyString(postedIn) && StringUtil.isNotNullOrEmptyString(nameAndCommunity)) {
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), feedTitle.length(), feedTitle.length() + postedIn.length() + 1, 0);
                SpanString.setSpan(community, feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);
                TypefaceSpan typefaceSpan = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_MEDIUM));
                SpanString.setSpan(typefaceSpan, feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);
                SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), feedTitle.length() + postedIn.length() + 2, nameAndCommunity.length(), 0);
            }
            mTvFeedPollCardTitle.setMovementMethod(LinkMovementMethod.getInstance());
            mTvFeedPollCardTitle.setText(SpanString, TextView.BufferType.SPANNABLE);
            mTvFeedPollCardTitle.setSelected(true);

        }
    }

    //endregion

    @OnClick({R.id.tv_feed_poll_user_comment, R.id.li_poll_main_layout})
    public void repliesClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onUserPostClicked(mPollSolarObj);
        }
    }


    @Override
    public void onClick(View view) {

    }


    @OnClick(R.id.tv_feed_poll_user_share)
    public void tvShareClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onPostShared(mPollSolarObj);
        }
        ((SheroesApplication) ((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_COMMUNITY_POST, AppConstants.EMPTY_STRING);

    }

    @OnClick(R.id.tv_feed_poll_total_reactions)
    public void reactionClick() {
        if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onLikesCountClicked(mPollSolarObj.getEntityOrParticipantId());
        }
    }

    @OnClick(R.id.tv_feed_poll_card_title)
    public void onCommunityNameClicked() {
        ((FeedItemCallback) viewInterface).onCommunityTitleClicked(mPollSolarObj);
    }


}
