package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.MentorFollowerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.MentorInsightResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.presenters.MentorPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.MentorView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.changeNumberToNumericSuffix;

/**
 * Created by Praveen on 11/12/17.
 */

public class MentorInsightActivity extends BaseActivity implements MentorView {
    private static final String SCREEN_LABEL = "Mentor Insight Screen";
    //region binding view variables
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.title_toolbar)
    TextView titleToolbar;

    @Bind(R.id.iv_mentor_full_view_icon)
    CircleImageView ivMentorFullViewIcon;

    @Bind(R.id.tv_insight_name)
    TextView tvInsightName;

    @Bind(R.id.li_profile_visitor)
    LinearLayout liProfileVisitor;

    @Bind(R.id.tv_visitor_count)
    TextView tvVisitorCount;

    @Bind(R.id.tv_mentor_insight_follower_count)
    TextView tvMentorInsightFollowerCount;

    @Bind(R.id.tv_mentor_insight_follower)
    TextView tvMentorInsightFollower;

    @Bind(R.id.tv_mentor_insight_follower_date)
    TextView tvMentorInsightFollowerDate;

    @Bind(R.id.tv_mentor_insight_follower_date_lable)
    TextView tvMentorInsightFollowerDateLable;

    @Bind(R.id.view_mentor)
    View viewMentor;

    @Bind(R.id.tv_impression_count)
    TextView tvImpressionCount;

    @Bind(R.id.tv_impressions)
    TextView tvImpressions;

    @Bind(R.id.tv_mentor_insight_post_count)
    TextView tvMentorInsightPostCount;

    @Bind(R.id.tv_mentor_insight_post)
    TextView tvMentorInsightPost;

    @Bind(R.id.tv_mentor_insight_post_create_date)
    TextView tvMentorInsightPostCreateDate;

    @Bind(R.id.tv_mentor_insight_post_create_lable)
    TextView tvMentorInsightPostCreateLable;

    @Bind(R.id.li_user_operation)
    LinearLayout liUserOperation;

    @Bind(R.id.li_impression)
    LinearLayout liImpression;

    @Bind(R.id.rl_post)
    RelativeLayout rlPost;

    @Bind(R.id.rl_like)
    RelativeLayout rlLike;

    @Bind(R.id.tv_insight_like_count)
    TextView tvInsightLikeCount;

    @Bind(R.id.tv_insight_like)
    TextView tvInsightLike;

    @Bind(R.id.rl_comment)
    RelativeLayout rlComment;

    @Bind(R.id.tv_insight_comment_count)
    TextView tvInsightComentCount;

    @Bind(R.id.tv_insight_comment)
    TextView tvInsightComent;

    @Bind(R.id.rl_share)
    RelativeLayout rlShare;

    @Bind(R.id.tv_insight_share_count)
    TextView tvInsightShareCount;

    @Bind(R.id.tv_insight_share)
    TextView tvInsightShare;

    @Bind(R.id.tv_mentor_insight_answer_count)
    TextView tvMentorInsightAnswerCount;

    @Bind(R.id.tv_mentor_insight_answer)
    TextView tvMentorInsightAnswer;

    @Bind(R.id.tv_mentor_insight_answer_date)
    TextView tvMentorInsightAnswerDate;

    @Bind(R.id.tv_mentor_insight_answer_date_lable)
    TextView tvMentorInsightAnswerDateLable;

    @Bind(R.id.view_answer)
    View viewAnswer;

    @Bind(R.id.rl_follower)
    RelativeLayout rlFollower;


    @Bind(R.id.tv_mentor_insight_question_count)
    TextView tvMentorInsightQuestionCount;

    @Bind(R.id.tv_mentor_insight_question)
    TextView tvMentorInsightQuestion;

    @Bind(R.id.tv_mentor_insight_question_date)
    TextView tvMentorInsightQuestionDate;

    @Bind(R.id.tv_mentor_insight_question_date_lable)
    TextView tvMentorInsightQuestionDateLable;

    @Bind(R.id.view_question)
    View viewQuestion;

    @Bind(R.id.rl_question)
    RelativeLayout rlQuestion;

    @Bind(R.id.rl_answer)
    RelativeLayout rlAnswer;

    @Inject
    AppUtils mAppUtils;

    @Inject
    MentorPresenter mMentorPresenter;

    UserSolrObj mUserSolrObj;

    @Bind(R.id.tv_mentor_answering_question)
    TextView tvMentorAnsweringQuestion;

    @BindDimen(R.dimen.dp_size_150)
    int mentorProfileSize;

    @Bind(R.id.view_insight)
    View viewInsight;


    //endregion

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.mentor_insight_layout);
        mMentorPresenter.attachView(this);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        titleToolbar.setText(R.string.ID_INSIGHT);
        mMentorPresenter.getMentorInsightFromPresenter(new MentorFollowerRequest());
        ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_MENTOR_INSIGHT));

    }

    @Override
    public void showMentorInsightDetail(MentorInsightResponse mentorInsightResponse) {
        tvMentorAnsweringQuestion.setVisibility(View.VISIBLE);
        mentorInsightDetail(mentorInsightResponse);
    }

    private void mentorInsightDetail(MentorInsightResponse mentorInsightResponse) {

        mUserSolrObj = mentorInsightResponse.getUserDoc();
        if (StringUtil.isNotNullOrEmptyString(mUserSolrObj.getThumbnailImageUrl())) {
            ivMentorFullViewIcon.setCircularImage(true);
            String authorThumborUrl = CommonUtil.getThumborUri(mUserSolrObj.getThumbnailImageUrl(), mentorProfileSize, mentorProfileSize);
            ivMentorFullViewIcon.bindImage(authorThumborUrl);
        }
        if (StringUtil.isNotNullOrEmptyString(mUserSolrObj.getNameOrTitle())) {
            tvInsightName.setText(mUserSolrObj.getNameOrTitle());
        }
        if (mentorInsightResponse.getTotalProfileVisitorsIn7Days() > 0) {
            liProfileVisitor.setVisibility(View.VISIBLE);
            tvInsightName.setText(String.valueOf(mentorInsightResponse.getTotalProfileVisitorsIn7Days()));
        } else {
            liProfileVisitor.setVisibility(View.GONE);
        }
        if (mUserSolrObj.getSolrIgnoreNoOfMentorFollowers() > 0) {
            rlFollower.setVisibility(View.VISIBLE);
            String pluralComments = getResources().getQuantityString(R.plurals.numberOfFollowers, mUserSolrObj.getSolrIgnoreNoOfMentorFollowers());
            tvMentorInsightFollowerCount.setText(String.valueOf(changeNumberToNumericSuffix(mUserSolrObj.getSolrIgnoreNoOfMentorFollowers())));
            tvMentorInsightFollower.setText(pluralComments);
            if (mentorInsightResponse.getNoOfFollowers7Days() > 0) {
                String str = AppConstants.PLUS + mentorInsightResponse.getNoOfFollowers7Days();
                tvMentorInsightFollowerDate.setText(str);
                viewMentor.setVisibility(View.VISIBLE);
            } else {
                viewMentor.setVisibility(View.GONE);
                tvMentorInsightFollowerDate.setVisibility(View.GONE);
                tvMentorInsightFollowerDateLable.setVisibility(View.GONE);
            }
        } else {
            rlFollower.setVisibility(View.GONE);
        }
        if (mentorInsightResponse.getTotalNoOfImpressions() > 0) {
            liImpression.setVisibility(View.VISIBLE);
            String plural = getResources().getQuantityString(R.plurals.numberOfImpression, mentorInsightResponse.getTotalNoOfImpressions());
            tvImpressionCount.setText(String.valueOf(changeNumberToNumericSuffix(mentorInsightResponse.getTotalNoOfImpressions())));
            tvImpressions.setText(plural);
        } else {
            liImpression.setVisibility(View.GONE);
        }
        if (mUserSolrObj.getSolrIgnoreNoOfMentorPosts() > 0) {
            rlPost.setVisibility(View.VISIBLE);
            String plural = getResources().getQuantityString(R.plurals.numberOfPosts, mentorInsightResponse.getTotalNoOfPostCreated());
            tvMentorInsightPostCount.setText(String.valueOf(changeNumberToNumericSuffix(mUserSolrObj.getSolrIgnoreNoOfMentorPosts())));
            tvMentorInsightPost.setText(plural);

            if (mentorInsightResponse.getTotalNoOfPost7Days() > 0) {
                String post = AppConstants.PLUS + mentorInsightResponse.getTotalNoOfPost7Days();
                tvMentorInsightPostCreateDate.setText(post);
                viewInsight.setVisibility(View.VISIBLE);
                tvMentorInsightPostCreateDate.setVisibility(View.VISIBLE);
            } else {
                viewInsight.setVisibility(View.GONE);
                tvMentorInsightPostCreateDate.setVisibility(View.GONE);
                tvMentorInsightPostCreateLable.setVisibility(View.GONE);
            }
        } else {
            rlPost.setVisibility(View.GONE);
        }
        if (mentorInsightResponse.getTotalNoOfLikes() > 0) {
            rlLike.setVisibility(View.VISIBLE);
            String plural = getResources().getQuantityString(R.plurals.numberOfLikes, mentorInsightResponse.getTotalNoOfLikes());
            tvInsightLikeCount.setText(String.valueOf(changeNumberToNumericSuffix(mentorInsightResponse.getTotalNoOfLikes())));
            tvInsightLike.setText(plural);
        } else {
            rlLike.setVisibility(View.GONE);
        }
        if (mentorInsightResponse.getTotalNoOfCommentsOnUserPost() > 0) {
            rlComment.setVisibility(View.VISIBLE);
            String plural = getResources().getQuantityString(R.plurals.numberOfComments, mentorInsightResponse.getTotalNoOfCommentsOnUserPost());
            tvInsightComentCount.setText(String.valueOf(changeNumberToNumericSuffix(mentorInsightResponse.getTotalNoOfCommentsOnUserPost())));
            tvInsightComent.setText(plural);
        } else {
            rlComment.setVisibility(View.GONE);
        }
        if (mentorInsightResponse.getTotalNoOfQuestions() > 0) {
            rlQuestion.setVisibility(View.VISIBLE);
            String plural = getResources().getQuantityString(R.plurals.numberOfQuestions, mentorInsightResponse.getTotalNoOfQuestions());
            tvMentorInsightQuestionCount.setText(String.valueOf(changeNumberToNumericSuffix(mentorInsightResponse.getTotalNoOfCommentsOnUserPost())));
            tvMentorInsightQuestion.setText(plural);
            if (mentorInsightResponse.getTotalNoOfQuestions7Days() > 0) {
                String quest = AppConstants.PLUS + mentorInsightResponse.getTotalNoOfQuestions7Days();
                tvMentorInsightQuestionDate.setText(quest);
                viewQuestion.setVisibility(View.VISIBLE);
                tvMentorInsightQuestionDate.setVisibility(View.VISIBLE);
            } else {
                tvMentorInsightQuestionDate.setVisibility(View.GONE);
                tvMentorInsightQuestionDateLable.setVisibility(View.GONE);
                viewQuestion.setVisibility(View.GONE);
            }
        } else {
            rlQuestion.setVisibility(View.GONE);
        }
        if (mUserSolrObj.getSolrIgnoreNoOfMentorAnswers() > 0) {
            rlAnswer.setVisibility(View.VISIBLE);
            String plural = getResources().getQuantityString(R.plurals.numberOfAnswers, mUserSolrObj.getSolrIgnoreNoOfMentorAnswers());
            tvMentorInsightAnswerCount.setText(String.valueOf(changeNumberToNumericSuffix(mUserSolrObj.getSolrIgnoreNoOfMentorAnswers())));
            tvMentorInsightAnswer.setText(plural);
            if (mentorInsightResponse.getTotalNoOfAnswers7Days() > 0) {
                String quest = AppConstants.PLUS + mentorInsightResponse.getTotalNoOfAnswers7Days();
                tvMentorInsightAnswerDate.setText(quest);
                tvMentorInsightAnswerDate.setVisibility(View.VISIBLE);
                viewAnswer.setVisibility(View.VISIBLE);
            } else {
                viewAnswer.setVisibility(View.GONE);
                tvMentorInsightAnswerDate.setVisibility(View.GONE);
                tvMentorInsightAnswerDateLable.setVisibility(View.GONE);
            }
        } else {
            rlAnswer.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.tv_mentor_answering_question)
    public void mentorAnsweringQuestionClick() {
        ProfileActivity.navigateTo(this, mUserSolrObj, mUserSolrObj.getIdOfEntityOrParticipant(), true, AppConstants.ASKING_QUESTION_CALL, AppConstants.MENTOR_INSIGHT, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        int id = view.getId();
        if (baseResponse instanceof FeedDetail) {
            switch (id) {

                default:
            }
        }
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mMentorPresenter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        if (null != intent) {
            switch (requestCode) {
                case AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL:
                    break;
                case AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST:
                    finish();
                    Intent home = new Intent(this, HomeActivity.class);
                    startActivity(home);
                    break;
                default:
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void startProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.bringToFront();
        }
    }

    @Override
    public void stopProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        onShowErrorDialog(s, feedParticipationEnum);
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }

}

