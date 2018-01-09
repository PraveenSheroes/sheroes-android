package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;

import org.parceler.Parcels;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.MentorInsightActivity;
import appliedlife.pvtltd.SHEROES.views.activities.MentorUserProfileActvity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.numericToThousand;

/**
 * Created by Praveen on 24/11/17.
 */

public class MentorCard extends BaseViewHolder<UserSolrObj> {
    private final String TAG = LogUtils.makeLogTag(MentorCard.class);
    @Inject
    DateUtil mDateUtil;
    BaseHolderInterface viewInterface;
    private UserSolrObj dataItem;
    Context mContext;
    @Bind(R.id.li_mentor)
    LinearLayout liMentor;
    @Bind(R.id.card_view_mentor)
    CardView cvMentor;
    @Bind(R.id.li_can_help)
    LinearLayout liCanHelpSecond;
    @Bind(R.id.tv_mentor_ask_question)
    TextView tvMentorAskQuestion;
    @Bind(R.id.tv_mentor_follow)
    TextView tvMentorFollow;
    @Bind(R.id.tv_feed_mentor_card_title)
    TextView tvFeedMentorCardTitle;
    @Bind(R.id.tv_feed_mentor_follower)
    TextView tvFeedMentorFollower;
    @Bind(R.id.tv_feed_mentor_answered)
    TextView tvFeedMentorAnswered;
    @Bind(R.id.tv_feed_mentor_status)
    TextView tvFeedMentorStatus;
    @Bind(R.id.tv_feed_mentor_first_profession)
    TextView tvFeedMentorFirstProfession;
    @Bind(R.id.tv_feed_mentor_second_profession)
    TextView tvFeedMentorSecondProfession;
    @Bind(R.id.tv_feed_mentor_third_profession)
    TextView tvFeedMentorThirdProfession;
    @Bind(R.id.li_proffessions)
    LinearLayout liProffessions;
    @Bind(R.id.iv_feed_mentor_card_circle_icon)
    CircleImageView ivFeedMentorCardCircleIcon;
    @Inject
    Preference<LoginResponse> mUserPreference;

    public MentorCard(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(UserSolrObj item, Context context, int position) {
        this.dataItem = item;
        mContext = context;
        dataItem.setItemPosition(position);
        tvMentorFollow.setEnabled(true);
        LogUtils.info(TAG, "########Metor data " + item.isSuggested());
        if (item.isSuggested()) {
            int width = AppUtils.getWindowWidth(mContext);
            FrameLayout.LayoutParams liHolderLayout = (FrameLayout.LayoutParams) liMentor.getLayoutParams();
            liHolderLayout.width = (width - 120);
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 2);
            cvMentor.setLayoutParams(params);
            cvMentor.setCardElevation(0);
            cvMentor.setRadius(0);
        }
        setAllData();
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            if (mUserPreference.get().getUserSummary().getUserId() == dataItem.getEntityOrParticipantId()) {
                tvMentorFollow.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
                tvMentorFollow.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                tvMentorFollow.setText(mContext.getString(R.string.ID_EDIT_PROFILE));
                tvMentorAskQuestion.setText(mContext.getString(R.string.ID_SEE_INSIGHT));
            } else {
                setFollowUnFollow();
            }
        } else {
            setFollowUnFollow();
        }


    }

    private void setAllData() {
        if (StringUtil.isNotNullOrEmptyString(dataItem.getThumbnailImageUrl())) {
            ivFeedMentorCardCircleIcon.setCircularImage(true);
            ivFeedMentorCardCircleIcon.bindImage(dataItem.getThumbnailImageUrl());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())) {
            tvFeedMentorCardTitle.setText(dataItem.getNameOrTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getSolrIgnoreMentorStatus())) {
            if (dataItem.getSolrIgnoreMentorStatus().equalsIgnoreCase(AppConstants.AVAILABLE)) {
                tvFeedMentorStatus.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.green_circle), null, null, null);
                tvFeedMentorStatus.setTextColor(ContextCompat.getColor(mContext, R.color.mentor_green));
            } else {
                tvFeedMentorStatus.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.red_circle), null, null, null);
                tvFeedMentorStatus.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            }
            tvFeedMentorStatus.setText(dataItem.getSolrIgnoreMentorStatus());
            tvFeedMentorStatus.setVisibility(View.VISIBLE);
        } else {
            tvFeedMentorStatus.setVisibility(View.INVISIBLE);
        }
        if (dataItem.getSolrIgnoreNoOfMentorFollowers() > 0) {
            String pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfFollowers, dataItem.getSolrIgnoreNoOfMentorFollowers());
            tvFeedMentorFollower.setText(String.valueOf(numericToThousand(dataItem.getSolrIgnoreNoOfMentorFollowers()) + AppConstants.SPACE + pluralComments));
            tvFeedMentorFollower.setVisibility(View.VISIBLE);
        } else {
            tvFeedMentorFollower.setVisibility(View.INVISIBLE);
        }
        if (dataItem.getSolrIgnoreNoOfMentorAnswers() > 0) {
            StringBuilder answers = new StringBuilder();
            answers.append(numericToThousand(dataItem.getSolrIgnoreNoOfMentorAnswers()) + AppConstants.SPACE + mContext.getString(R.string.ID_ANSWERED_QUESTIONS));
            tvFeedMentorAnswered.setText(answers);
            tvFeedMentorAnswered.setVisibility(View.VISIBLE);
        } else {
            tvFeedMentorAnswered.setVisibility(View.INVISIBLE);
        }
        if (StringUtil.isNotEmptyCollection(dataItem.getCanHelpIns())) {
            liProffessions.setVisibility(View.VISIBLE);
            tvFeedMentorFirstProfession.setText(dataItem.getCanHelpIns().get(0));
            if (dataItem.getCanHelpIns().size() > 1) {
                tvFeedMentorSecondProfession.setText(dataItem.getCanHelpIns().get(1));
                liCanHelpSecond.setVisibility(View.VISIBLE);
            } else {
                liCanHelpSecond.setVisibility(View.GONE);
            }
        } else {
            liProffessions.setVisibility(View.INVISIBLE);
        }
    }

    private void setFollowUnFollow() {
        tvMentorAskQuestion.setText(mContext.getString(R.string.ID_ASK_QUESTION));
        if (dataItem.isSolrIgnoreIsMentorFollowed()) {
            tvMentorFollow.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            tvMentorFollow.setText(mContext.getString(R.string.ID_GROWTH_BUDDIES_FOLLOWING));
            tvMentorFollow.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        } else {
            tvMentorFollow.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            tvMentorFollow.setText(mContext.getString(R.string.ID_GROWTH_BUDDIES_FOLLOW));
            tvMentorFollow.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }
    }

    @Override
    public void onClick(View view) {


    }

    @OnClick(R.id.tv_mentor_follow)
    public void mentorFollowClick() {
        if (tvMentorFollow.getText().toString().equalsIgnoreCase(mContext.getString(R.string.ID_EDIT_PROFILE))) {
            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
                String profile = mUserPreference.get().getUserSummary().getPhotoUrl();
                Intent intent = new Intent(mContext, MentorUserProfileActvity.class);
                Bundle bundle = new Bundle();
                Parcelable parcelableFeedDetail = Parcels.wrap(dataItem);
                bundle.putParcelable(AppConstants.MENTOR_DETAIL, parcelableFeedDetail);
                Parcelable parcelableMentor = Parcels.wrap(dataItem);
                bundle.putParcelable(AppConstants.GROWTH_PUBLIC_PROFILE, parcelableMentor);
                intent.putExtra(AppConstants.IS_MENTOR_ID, true); 
                intent.putExtras(bundle);
                intent.putExtra(AppConstants.EXTRA_IMAGE, profile);
                mContext.startActivity(intent);
            }
        } else {
            tvMentorFollow.setEnabled(false);
            viewInterface.handleOnClick(dataItem, tvMentorFollow);
            if (dataItem.isSolrIgnoreIsMentorFollowed()) {
                dataItem.setSolrIgnoreIsMentorFollowed(false);
            } else {
                dataItem.setSolrIgnoreIsMentorFollowed(true);
            }
            setFollowUnFollow();
        }

    }

    @OnClick(R.id.tv_mentor_ask_question)
    public void mentorAskQuestionClick() {
        if (tvMentorAskQuestion.getText().toString().equalsIgnoreCase(mContext.getString(R.string.ID_SEE_INSIGHT))) {
            Intent intent = new Intent(mContext, MentorInsightActivity.class);
            mContext.startActivity(intent);
        } else {
            viewInterface.handleOnClick(dataItem, tvMentorAskQuestion);
        }
    }

    @OnClick(R.id.li_mentor)
    public void mentorCardClick() {
        viewInterface.handleOnClick(dataItem, liMentor);
    }

    @Override
    public void viewRecycled() {

    }
}



