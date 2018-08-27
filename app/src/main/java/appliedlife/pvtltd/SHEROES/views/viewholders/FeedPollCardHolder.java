package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.f2prateek.rx.preferences2.Preference;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.PostDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.feed.PollSolarObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollOptionModel;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView;
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

    @Bind(R.id.tv_feed_poll_ends_in)
    TextView mTvFeedPollEndsIn;

    @Bind(R.id.tv_feed_poll_user_menu)
    TextView mTvFeedPollUserMenu;

    @Bind(R.id.rl_feed_poll_no_reaction_comments)
    RelativeLayout rlFeedPollNoReactionComment;

    @Bind(R.id.tv_feed_poll_total_reactions_count)
    TextView mTvFeedPollTotalReactionCount;

    @Bind(R.id.tv_feed_poll_total_replies)
    TextView mTvFeedPollTotalReplies;

    @BindDimen(R.dimen.option_poll_margintop)
    int mPollMarginTop;

    @BindDimen(R.dimen.option_poll_margin_left_right)
    int mPollMarginLeftRight;


    @BindDimen(R.dimen.author_pic_size)
    int authorPicSize;

    @BindDimen(R.dimen.author_pic_size_for_image)
    int authorPicSizeFourty;
    //endregion

    private BaseHolderInterface viewInterface;
    private PostDetailCallBack mPostDetailCallBack;
    private PollSolarObj mPollSolarObj;
    private Context mContext;
    private boolean isWhatappShareOption = false;
    private LinearLayout liImagePollRow;
    private ConstraintLayout clImagePollLeftContainer, clImagePollRightContainer;
    private ProgressBar pbPollPercentLeft, pbPollPercentRight;
    private TextView tvPollPercentNumberLeft, tvPollPercentNumberRight, tvImagePollNameLeft, tvImagePollNameRight;
    private RoundedImageView ivFeedImagePollLeft, ivFeedImagePollRight;

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
        mLiTypeOfPollView.removeAllViews();
        mLiTypeOfPollView.removeAllViewsInLayout();
        switch (mPollSolarObj.getPollType()) {
            case TEXT:
                if (mPollSolarObj.isShowResults() || mPollSolarObj.isRespondedOnPoll()) {
                    addTextPollResultViews();
                } else {
                    addTextPollInputViews();
                }
                break;
            case IMAGE:
                addImagePollViews();
                imagePollViewResultView();
                break;
        }
        showPollUiFieldsWithData();
    }

    private void imagePollViewWithoutRespond() {
        pbPollPercentLeft.setVisibility(View.GONE);
        tvPollPercentNumberLeft.setVisibility(View.GONE);
        tvImagePollNameLeft.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        tvImagePollNameLeft.setBackgroundResource(R.drawable.rectangle_image_poll_bottom_border);
        tvImagePollNameLeft.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));


        pbPollPercentRight.setVisibility(View.GONE);
        tvPollPercentNumberRight.setVisibility(View.GONE);
        tvImagePollNameRight.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        tvImagePollNameRight.setBackgroundResource(R.drawable.rectangle_image_poll_bottom_border);
        tvImagePollNameRight.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
    }

    private void imagePollViewResultView() {
        List<PollOptionModel> pollOptionModelList = mPollSolarObj.getPollOptions();
        if (StringUtil.isNotEmptyCollection(pollOptionModelList) && pollOptionModelList.size() > 1) {
            int width = CommonUtil.getWindowWidth(mContext);
            int imageHeight = CommonUtil.getWindowHeight(mContext);
            if (StringUtil.isNotNullOrEmptyString(pollOptionModelList.get(0).getImageUrl())) {
                String firstImageUrl = CommonUtil.getThumborUriWithFit(pollOptionModelList.get(0).getImageUrl(), width, imageHeight);
                Glide.with(mContext)
                        .load(firstImageUrl)
                        .apply(new RequestOptions().placeholder(R.color.photo_placeholder))
                        .into(ivFeedImagePollLeft);
            } else {
                ivFeedImagePollLeft.setBackgroundResource(R.color.photo_placeholder);
            }
            tvImagePollNameLeft.setBackgroundResource(R.drawable.rectangle_image_poll_bottom_border);
            tvImagePollNameLeft.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            tvImagePollNameLeft.setText(pollOptionModelList.get(0).getDescription());

            pbPollPercentLeft.setProgress(pollOptionModelList.get(0).getTotalNoOfVotesPercent());
            tvPollPercentNumberLeft.setText(pollOptionModelList.get(0).getTotalNoOfVotesPercent() + mContext.getString(R.string.percent_vote));

            if (pollOptionModelList.get(0).isVoted()) {
                tvImagePollNameLeft.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(mContext, R.drawable.vector_poll_percent_verified), null);
            } else {
                tvImagePollNameLeft.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }


            // Image poll right option
            if (StringUtil.isNotNullOrEmptyString(pollOptionModelList.get(1).getImageUrl())) {
                String secondImageUrl = CommonUtil.getThumborUriWithFit(pollOptionModelList.get(1).getImageUrl(), width, imageHeight);
                Glide.with(mContext)
                        .load(secondImageUrl)
                        .apply(new RequestOptions().placeholder(R.color.photo_placeholder))
                        .into(ivFeedImagePollRight);
            } else {
                ivFeedImagePollRight.setBackgroundResource(R.color.photo_placeholder);
            }
            tvImagePollNameRight.setText(pollOptionModelList.get(1).getDescription());
            tvImagePollNameRight.setBackgroundResource(R.drawable.rectangle_image_poll_bottom_border);
            tvImagePollNameRight.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));

            pbPollPercentRight.setProgress(pollOptionModelList.get(1).getTotalNoOfVotesPercent());
            tvPollPercentNumberRight.setText(pollOptionModelList.get(1).getTotalNoOfVotesPercent() + mContext.getString(R.string.percent_vote));

            if (pollOptionModelList.get(1).isVoted()) {
                tvImagePollNameRight.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(mContext, R.drawable.vector_poll_percent_verified), null);
            } else {
                tvImagePollNameRight.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }

        } else {
            imagePollViewWithoutRespond();
        }
        if (mPollSolarObj.isShowResults() || mPollSolarObj.isRespondedOnPoll()) {
            clImagePollLeftContainer.setEnabled(false);
            clImagePollRightContainer.setEnabled(false);
            pbPollPercentLeft.setVisibility(View.VISIBLE);
            tvPollPercentNumberLeft.setVisibility(View.VISIBLE);
            pbPollPercentRight.setVisibility(View.VISIBLE);
            tvPollPercentNumberRight.setVisibility(View.VISIBLE);
        } else {
            clImagePollLeftContainer.setEnabled(true);
            clImagePollRightContainer.setEnabled(true);
            pbPollPercentLeft.setVisibility(View.GONE);
            tvPollPercentNumberLeft.setVisibility(View.GONE);
            pbPollPercentRight.setVisibility(View.GONE);
            tvPollPercentNumberRight.setVisibility(View.GONE);
        }
    }

    @Override
    public void viewRecycled() {

    }
    //region private methods

    private void addTextPollInputViews() {
        for (int i = 0; i < mPollSolarObj.getPollOptions().size(); i++) {
            final TextView textPollInputLayout = (TextView) LayoutInflater.from(mContext).inflate(R.layout.feed_poll_card_textpoll_input_layout, null);
            final PollOptionModel pollOptionModel = mPollSolarObj.getPollOptions().get(i);
            textPollInputLayout.setText(pollOptionModel.getDescription());
            textPollInputLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textPollInputLayout.setBackgroundResource(R.drawable.rectangle_text_poll_active);
                    textPollInputLayout.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    mPollSolarObj.setTotalNumberOfResponsesOnPoll(mPollSolarObj.getTotalNumberOfResponsesOnPoll() + AppConstants.ONE_CONSTANT);
                    if (viewInterface != null) {
                        ((FeedItemCallback) viewInterface).onPollVote(mPollSolarObj, pollOptionModel);
                    } else if (mPostDetailCallBack != null) {
                        mPostDetailCallBack.onPollVote(mPollSolarObj, pollOptionModel);
                    }
                }
            });


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
            params.setMargins(mPollMarginLeftRight, mPollMarginTop, mPollMarginLeftRight, mPollMarginTop);
            textPollInputLayout.setLayoutParams(params);
            mLiTypeOfPollView.addView(textPollInputLayout);

        }

    }

    private void addTextPollResultViews() {
        List<PollOptionModel> pollOptionModelList = mPollSolarObj.getPollOptions();
        if (StringUtil.isNotEmptyCollection(pollOptionModelList)) {
            for (PollOptionModel pollOptionModel : pollOptionModelList) {
                final View pollLayout = LayoutInflater.from(mContext).inflate(R.layout.feed_poll_card_textpoll_result_layout, null);
                final LinearLayout liImageRatingRow = pollLayout.findViewById(R.id.li_feed_text_poll_row);
                ProgressBar pbPollPercent = pollLayout.findViewById(R.id.pb_poll_percent);
                TextView tvPollPercentNumber = pollLayout.findViewById(R.id.tv_poll_percent_number);
                TextView tvTextPollDesc = pollLayout.findViewById(R.id.tv_text_poll_desc);
                ImageView ivPercentVerified = pollLayout.findViewById(R.id.iv_percent_verified);
                pbPollPercent.setProgress(pollOptionModel.getTotalNoOfVotesPercent());
                tvPollPercentNumber.setText(pollOptionModel.getTotalNoOfVotesPercent() + "%");
                tvTextPollDesc.setText(pollOptionModel.getDescription());
                if (pollOptionModel.isVoted()) {
                    ivPercentVerified.setVisibility(View.VISIBLE);
                } else {
                    ivPercentVerified.setVisibility(View.GONE);
                }
                pbPollPercent.setVisibility(View.VISIBLE);
                tvPollPercentNumber.setVisibility(View.VISIBLE);
                mLiTypeOfPollView.setOnClickListener(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
                params.setMargins(mPollMarginLeftRight, mPollMarginTop, mPollMarginLeftRight, mPollMarginTop);
                liImageRatingRow.setLayoutParams(params);
                mLiTypeOfPollView.addView(liImageRatingRow);
            }
        }
    }

    private void addImagePollViews() {
        final View pollLayout = LayoutInflater.from(mContext).inflate(R.layout.feed_poll_card_imagepoll_layout, null);
        liImagePollRow = pollLayout.findViewById(R.id.li_feed_imagepoll_row);
        clImagePollLeftContainer = pollLayout.findViewById(R.id.cl_imagepoll_left_container);
        clImagePollRightContainer = pollLayout.findViewById(R.id.cl_imagepoll_right_container);
        clImagePollLeftContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvImagePollNameLeft.setBackgroundResource(R.drawable.rectangle_image_poll_bottom_border_active);
                tvImagePollNameLeft.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                tvImagePollNameRight.setBackgroundResource(R.drawable.rectangle_image_poll_bottom_border);
                tvImagePollNameRight.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
                PollOptionModel pollOptionModel = mPollSolarObj.getPollOptions().get(0);
                mPollSolarObj.setTotalNumberOfResponsesOnPoll(mPollSolarObj.getTotalNumberOfResponsesOnPoll() + AppConstants.ONE_CONSTANT);
                if (viewInterface != null) {
                    ((FeedItemCallback) viewInterface).onPollVote(mPollSolarObj, pollOptionModel);
                } else if (mPostDetailCallBack != null) {
                    mPostDetailCallBack.onPollVote(mPollSolarObj, pollOptionModel);
                }
            }
        });
        clImagePollRightContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvImagePollNameRight.setBackgroundResource(R.drawable.rectangle_image_poll_bottom_border_active);
                tvImagePollNameRight.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                tvImagePollNameLeft.setBackgroundResource(R.drawable.rectangle_image_poll_bottom_border);
                tvImagePollNameLeft.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
                PollOptionModel pollOptionModel = mPollSolarObj.getPollOptions().get(1);
                mPollSolarObj.setTotalNumberOfResponsesOnPoll(mPollSolarObj.getTotalNumberOfResponsesOnPoll() + AppConstants.ONE_CONSTANT);
                if (viewInterface != null) {
                    ((FeedItemCallback) viewInterface).onPollVote(mPollSolarObj, pollOptionModel);
                } else if (mPostDetailCallBack != null) {
                    mPostDetailCallBack.onPollVote(mPollSolarObj, pollOptionModel);
                }
            }
        });

        pbPollPercentLeft = pollLayout.findViewById(R.id.pb_imagepoll_percent_left);
        pbPollPercentRight = pollLayout.findViewById(R.id.pb_imagepoll_percent_right);

        tvPollPercentNumberLeft = pollLayout.findViewById(R.id.tv_imagepoll_percent_count_left);
        tvPollPercentNumberRight = pollLayout.findViewById(R.id.tv_imagepoll_percent_count_right);

        tvImagePollNameLeft = pollLayout.findViewById(R.id.tv_imagepoll_name_left);
        tvImagePollNameRight = pollLayout.findViewById(R.id.tv_imagepoll_name_right);

        ivFeedImagePollLeft = pollLayout.findViewById(R.id.iv_feed_image_poll_left);
        ivFeedImagePollRight = pollLayout.findViewById(R.id.iv_feed_image_poll_right);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
        params.setMargins(mPollMarginLeftRight, mPollMarginTop, mPollMarginLeftRight, mPollMarginTop);
        liImagePollRow.setLayoutParams(params);
        mLiTypeOfPollView.addView(liImagePollRow);
    }


    /**
     * Show post Ui content with data
     * Different Ui formats like Spam,Video and Image rendering etc.
     * Only Normal and link rendering views are handled.
     */
    private void showPollUiFieldsWithData() {
        mLiPollMainLayout.setVisibility(View.VISIBLE);
        mTvFeedPollUserReaction.setTag(true);
        mPollSolarObj.setLastReactionValue(mPollSolarObj.getReactionValue());
        populatePostedPollText();
        pollDataContentRendering();
        likeCommentOps();
        if (mPollSolarObj.getTotalNumberOfResponsesOnPoll() > 0) {
            String pluralVotes = mContext.getResources().getQuantityString(R.plurals.numberOfVotes, (int) mPollSolarObj.getTotalNumberOfResponsesOnPoll());
            mTvFeedPollTotalVotes.setText(String.valueOf(mPollSolarObj.getTotalNumberOfResponsesOnPoll() + AppConstants.SPACE + pluralVotes));
            mTvFeedPollTotalVotes.setVisibility(View.VISIBLE);
        } else {
            mTvFeedPollTotalVotes.setVisibility(View.GONE);
        }
        if (StringUtil.isNotNullOrEmptyString(mPollSolarObj.getEndsAt())) {
            long endDateTime = mDateUtil.getTimeInMillis(mPollSolarObj.getEndsAt(), AppConstants.DATE_FORMAT);
            String endsIn = mDateUtil.getDifferenceInTime(endDateTime, System.currentTimeMillis());
            if (StringUtil.isNotNullOrEmptyString(endsIn)) {
                endsIn = mContext.getString(R.string.ends_in) + " " + endsIn;
                mTvFeedPollEndsIn.setVisibility(View.VISIBLE);
                mTvFeedPollEndsIn.setText(endsIn);
            } else {
                mTvFeedPollEndsIn.setVisibility(View.GONE);
            }
        } else {
            mTvFeedPollEndsIn.setVisibility(View.GONE);
        }

    }


    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void pollDataContentRendering() {
        String authorImageUrl = mPollSolarObj.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(authorImageUrl)) {
            mIvFeedPollCircleIcon.setCircularImage(true);
            String authorThumborUrl = CommonUtil.getThumborUri(authorImageUrl, authorPicSizeFourty, authorPicSizeFourty);
            mIvFeedPollCircleIcon.bindImage(authorThumborUrl);
        } else {
            mIvFeedPollCircleIcon.setBackgroundResource(R.drawable.default_img);
        }

        if (mPollSolarObj.isAuthorMentor()) {
            mIvFeedPollCircleIconVerified.setVisibility(View.VISIBLE);
        } else {
            mIvFeedPollCircleIconVerified.setVisibility(View.GONE);
        }

        if (StringUtil.isNotNullOrEmptyString(mPollSolarObj.getAuthorName())) {
            StringBuilder completeText = new StringBuilder();
            String feedTitle;
            String feedCommunityName;
            String createdIn;
            if (mPollSolarObj.getEntityOrParticipantTypeId() == 18) {
                feedTitle = mPollSolarObj.getPollCommunityName();
                feedCommunityName = "";
                createdIn = mContext.getString(R.string.created_poll);
            } else {
                feedTitle = mPollSolarObj.getAuthorName();
                feedCommunityName = mPollSolarObj.getPollCommunityName();
                createdIn = mContext.getString(R.string.created_poll) + " in";
            }
            completeText.append(feedTitle).append(AppConstants.SPACE).append(createdIn).append(AppConstants.SPACE);
            completeText.append(feedCommunityName);
            clickOnUserAndCommunityName(completeText.toString(), feedTitle, createdIn, false);

            mTvFeedPollCardTitle.setVisibility(View.VISIBLE);
        } else {
            mTvFeedPollCardTitle.setVisibility(View.INVISIBLE);
        }
        if (StringUtil.isNotNullOrEmptyString(mPollSolarObj.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(mPollSolarObj.getCreatedDate(), AppConstants.DATE_FORMAT);
            String time = mDateUtil.getDifferenceInTime(createdDate,System.currentTimeMillis());
            if (StringUtil.isNotNullOrEmptyString(time)) {
                mTvFeedCommunityPostTime.setText(time);
                mTvFeedCommunityPostTime.setVisibility(View.VISIBLE);
            } else {
                mTvFeedCommunityPostTime.setVisibility(View.GONE);
            }
            mTvFeedCommunityPostTime.setVisibility(View.VISIBLE);
        } else {
            mTvFeedCommunityPostTime.setVisibility(View.GONE);
        }
    }

    private void likeCommentOps() {
        if (mPollSolarObj.getNoOfLikes() < AppConstants.ONE_CONSTANT && mPollSolarObj.getNoOfComments() < AppConstants.ONE_CONSTANT) {
            mTvFeedPollUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
            rlFeedPollNoReactionComment.setVisibility(View.GONE);
            mLineSeparate.setVisibility(View.GONE);
        }else
        {
            mLineSeparate.setVisibility(View.VISIBLE);
            rlFeedPollNoReactionComment.setVisibility(View.VISIBLE);
        }
        switch (mPollSolarObj.getNoOfLikes()) {
            case AppConstants.NO_REACTION_CONSTANT:
                if (mPollSolarObj.getNoOfComments() > AppConstants.NO_REACTION_CONSTANT) {
                    rlFeedPollNoReactionComment.setVisibility(View.VISIBLE);
                } else {
                    rlFeedPollNoReactionComment.setVisibility(View.GONE);
                }
                userLike();
                break;
            case AppConstants.ONE_CONSTANT:
                rlFeedPollNoReactionComment.setVisibility(View.VISIBLE);
                userLike();
                break;
            default:
                rlFeedPollNoReactionComment.setVisibility(View.VISIBLE);
                userLike();
        }
        switch (mPollSolarObj.getNoOfComments()) {
            case AppConstants.NO_REACTION_CONSTANT:
                if (mPollSolarObj.getNoOfLikes() > AppConstants.NO_REACTION_CONSTANT) {
                    rlFeedPollNoReactionComment.setVisibility(View.VISIBLE);
                    mTvFeedPollTotalReactionCount.setVisibility(View.VISIBLE);
                    mTvFeedPollTotalReaction.setVisibility(View.VISIBLE);
                    mTvFeedPollTotalReplies.setVisibility(View.INVISIBLE);
                } else {
                    rlFeedPollNoReactionComment.setVisibility(View.GONE);
                }
                break;
            case AppConstants.ONE_CONSTANT:
                mTvFeedPollTotalReplies.setVisibility(View.VISIBLE);
                break;
            default:
                mTvFeedPollTotalReplies.setVisibility(View.VISIBLE);
        }
        if (mPollSolarObj.getNoOfLikes() > 0) {
            String pluralLikes = mContext.getResources().getQuantityString(R.plurals.numberOfLikes, mPollSolarObj.getNoOfLikes());
            mTvFeedPollTotalReactionCount.setText(String.valueOf(mPollSolarObj.getNoOfLikes() + AppConstants.SPACE + pluralLikes));
            mTvFeedPollTotalReactionCount.setVisibility(View.VISIBLE);
            mTvFeedPollTotalReaction.setVisibility(View.VISIBLE);
        } else {
            mTvFeedPollTotalReactionCount.setVisibility(View.INVISIBLE);
            mTvFeedPollTotalReaction.setVisibility(View.INVISIBLE);
        }
        if (mPollSolarObj.getNoOfComments() > 0) {
            String pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfComments, mPollSolarObj.getNoOfComments());
            mTvFeedPollTotalReplies.setText(String.valueOf(mPollSolarObj.getNoOfComments() + AppConstants.SPACE + pluralComments));
            mTvFeedPollTotalReplies.setVisibility(View.VISIBLE);
        } else {
            mTvFeedPollTotalReplies.setVisibility(View.INVISIBLE);
        }
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
        final String listDescription = mPollSolarObj.getDescription();
        if (StringUtil.isNotNullOrEmptyString(listDescription)) {
            mTvFeedPollDescription.setVisibility(View.VISIBLE);
            mTvFeedPollDescription.setText(hashTagColorInString(listDescription), TextView.BufferType.SPANNABLE);
            linkifyURLs(mTvFeedPollDescription);
        } else {
            mTvFeedPollDescription.setVisibility(View.GONE);
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

    private void clickOnUserAndCommunityName(String nameAndCommunity, String feedTitle, String createdIn, boolean isCommunityName) {

        SpannableString SpanString = new SpannableString(nameAndCommunity);

        ClickableSpan authorTitle = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                profileImageClick();
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        ClickableSpan createdInClick = new ClickableSpan() {
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
                if (viewInterface != null) {
                    ((FeedItemCallback) viewInterface).onCommunityTitleClicked(mPollSolarObj);
                } else if (mPostDetailCallBack != null) {
                    mPostDetailCallBack.onCommunityTitleClicked(mPollSolarObj);
                }
            }

            @Override
            public void updateDrawState(final TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        };
        if (StringUtil.isNotNullOrEmptyString(feedTitle)) {
            SpanString.setSpan(authorTitle, 0, feedTitle.length(), 0);
            TypefaceSpan typefaceSpanAuthor = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_MEDIUM));
            SpanString.setSpan(typefaceSpanAuthor, 0, feedTitle.length(), 0);
            SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), 0, feedTitle.length(), 0);
            if (isCommunityName) {
                if (StringUtil.isNotNullOrEmptyString(createdIn) && StringUtil.isNotNullOrEmptyString(nameAndCommunity)) {
                    SpanString.setSpan(createdInClick, feedTitle.length(), feedTitle.length() + createdIn.length() + 3, 0);
                    SpanString.setSpan(community, feedTitle.length() + createdIn.length() + 2, nameAndCommunity.length(), 0);
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), feedTitle.length(), feedTitle.length() + createdIn.length() + 3, 0);
                    TypefaceSpan typefaceSpan = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_REGULAR));
                    SpanString.setSpan(typefaceSpan, feedTitle.length(), feedTitle.length() + createdIn.length() + 3, 0);
                    TypefaceSpan typefaceSpanCommunity = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_MEDIUM));
                    SpanString.setSpan(typefaceSpanCommunity, feedTitle.length() + createdIn.length() + 2, nameAndCommunity.length(), 0);
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), feedTitle.length() + createdIn.length() + 2, nameAndCommunity.length(), 0);
                }
            } else {
                if (StringUtil.isNotNullOrEmptyString(createdIn) && StringUtil.isNotNullOrEmptyString(nameAndCommunity)) {
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), feedTitle.length(), feedTitle.length() + createdIn.length() + 1, 0);
                    SpanString.setSpan(community, feedTitle.length() + createdIn.length() + 2, nameAndCommunity.length(), 0);
                    TypefaceSpan typefaceSpanCommunity = new TypefaceSpan(mContext.getResources().getString(R.string.ID_ROBOTO_MEDIUM));
                    SpanString.setSpan(typefaceSpanCommunity, feedTitle.length() + createdIn.length() + 2, nameAndCommunity.length(), 0);
                    SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.feed_title)), feedTitle.length() + createdIn.length() + 2, nameAndCommunity.length(), 0);
                }
            }

            mTvFeedPollCardTitle.setMovementMethod(LinkMovementMethod.getInstance());
            mTvFeedPollCardTitle.setText(SpanString, TextView.BufferType.SPANNABLE);
            mTvFeedPollCardTitle.setSelected(true);
        }
    }

    //endregion

    @OnClick({R.id.tv_feed_poll_user_comment, R.id.li_poll_main_layout})
    public void repliesClick() {
        if (viewInterface != null) {
            ((FeedItemCallback) viewInterface).onUserPostClicked(mPollSolarObj);
        } else if (mPostDetailCallBack != null) {
            mPostDetailCallBack.onCommentButtonClicked();
        }
    }

    @OnClick({R.id.iv_feed_poll_circle_icon})
    public void profileImageClick() {
        if (viewInterface != null) {
            if (mPollSolarObj.getEntityOrParticipantTypeId() == 18) {
                ((FeedItemCallback) viewInterface).onCommunityTitleClicked(mPollSolarObj);
            } else {
                ((FeedItemCallback) viewInterface).onChampionProfileClicked(mPollSolarObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
            }
        } else if (mPostDetailCallBack != null) {
            if (mPollSolarObj.getEntityOrParticipantTypeId() == 18) {
                mPostDetailCallBack.onCommunityTitleClicked(mPollSolarObj);
            } else {
                mPostDetailCallBack.onChampionProfileClicked(mPollSolarObj, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
            }
        }
    }

    @OnClick(R.id.tv_feed_poll_user_reaction)
    public void onUserReactionClick() {
        if (viewInterface != null) {
            if (mPollSolarObj.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
                mPollSolarObj.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                mPollSolarObj.setNoOfLikes(mPollSolarObj.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                mTvFeedPollUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                ((FeedItemCallback) viewInterface).onPollUnLiked(mPollSolarObj);
            } else {
                mPollSolarObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                mPollSolarObj.setNoOfLikes(mPollSolarObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                mTvFeedPollUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
                ((FeedItemCallback) viewInterface).onPollLiked(mPollSolarObj);
            }
            likeCommentOps();
        } else if (mPostDetailCallBack != null) {
            if (mPollSolarObj.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
                mPollSolarObj.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
                mPollSolarObj.setNoOfLikes(mPollSolarObj.getNoOfLikes() - AppConstants.ONE_CONSTANT);
                mTvFeedPollUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_in_active, 0, 0, 0);
                mPostDetailCallBack.onPollUnLikeClicked(mPollSolarObj);
            } else {
                mPollSolarObj.setReactionValue(AppConstants.HEART_REACTION_CONSTANT);
                mPollSolarObj.setNoOfLikes(mPollSolarObj.getNoOfLikes() + AppConstants.ONE_CONSTANT);
                mTvFeedPollUserReaction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_active, 0, 0, 0);
                mPostDetailCallBack.onPollLikeClicked(mPollSolarObj);
            }
            likeCommentOps();
        }
    }


    @OnClick(R.id.tv_feed_poll_user_share)
    public void tvShareClick() {
        if (viewInterface != null) {
            ((FeedItemCallback) viewInterface).onPostShared(mPollSolarObj);
        } else if (mPostDetailCallBack != null) {
            mPostDetailCallBack.onShareButtonClicked(mPollSolarObj, mTvFeedPollUserShare);
        }
    }

    @OnClick({R.id.tv_feed_poll_total_reactions_count})
    public void reactionClick() {
        if (viewInterface != null) {
            ((FeedItemCallback) viewInterface).onLikesCountClicked(mPollSolarObj.getEntityOrParticipantId());
        } else if (mPostDetailCallBack != null) {
            mPostDetailCallBack.onLikeCountClicked(mPollSolarObj);
        }
    }

    @OnClick(R.id.tv_feed_poll_user_menu)
    public void userPollMenuClick() {
        if (viewInterface != null) {
            ((FeedItemCallback) viewInterface).onPollMenuClicked(mPollSolarObj, mTvFeedPollUserMenu);
        } else if (mPostDetailCallBack != null) {
            mPostDetailCallBack.onPollMenuClicked(mPollSolarObj, mTvFeedPollUserMenu);
        }
    }

    @Override
    public void onClick(View view) {

    }

}
