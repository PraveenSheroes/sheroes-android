package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeDataItem;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.MyCountDownTimer;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 03-02-2017.
 */

public class ChallengeItemCardHolder extends BaseViewHolder<ChallengeDataItem>{
    private final String TAG = LogUtils.makeLogTag(ChallengeItemCardHolder.class);
    BaseHolderInterface viewInterface;
    private ChallengeDataItem dataItem;
    @Bind(R.id.iv_challenge_icon)
    CircleImageView ivChallengeIcon;
    @Bind(R.id.tv_timer_count_challenge)
    TextView tvTimerCountChallenge;
    @Bind(R.id.tv_accept_challenge)
    TextView tvAcceptChallenge;
    @Bind(R.id.tv_challenge_name)
    TextView tvChallengeName;
    @Bind(R.id.tv_user_name)
    TextView tvAutherName;
    @Bind(R.id.tv_city_name)
    TextView tvCityName;
    @Bind(R.id.tv_after_city_name)
    TextView tvAfterCityName;
    @Bind(R.id.tv_count_in_country)
    TextView tvCountInCountry;
    @Bind(R.id.tv_after_count_in_country)
    TextView tvAfterCountInCountry;
    @Bind(R.id.tv_country_name)
    TextView tvCountryName;
    @Bind(R.id.tv_after_country_name)
    TextView tvAfterCountryName;
    @Bind(R.id.tv_count_in_city)
    TextView tvCountInCity;
    @Bind(R.id.tv_after_count_in_city)
    TextView tvAfterCountInCity;
    @Bind(R.id.li_total_count)
    LinearLayout liTotalCount;
    @Bind(R.id.li_after_accept_total_count)
    LinearLayout liAfterAcceptTotalCount;
    @Bind(R.id.view_line)
    View viewLine;
    @Bind(R.id.view_line_after)
    View viewLineAfter;
    @Bind(R.id.fl_total_count_city)
    FrameLayout flTotalCountCity;
    @Bind(R.id.fl_after_total_count_city)
    FrameLayout flAfterTotalCountCity;
    @Bind(R.id.fl_total_count_country)
    FrameLayout flTotalCountCountry;
    @Bind(R.id.fl_after_total_count_country)
    FrameLayout flAfterTotalCountCountry;
    @Bind(R.id.li_before_accept)
    LinearLayout liBeforeAccept;
    @Bind(R.id.li_after_accept)
    LinearLayout liAfterAccept;
    @Bind(R.id.tv_complete_share)
    TextView tvCompleteShare;
    @Bind(R.id.tv_timer_count_challenge_update_status)
    TextView tvTimerCountChallengeUpdateStatus;
    @Bind(R.id.tv_challenge_name_update_status)
    TextView tvChallengeNameUpdateStatus;
    @Bind(R.id.tv_update_progress)
    TextView tvUpdateProgress;

    @Bind(R.id.iv_just_started)
    ImageView ivJustStarted;
    @Bind(R.id.iv_half_done)
    ImageView ivhalfDone;
    @Bind(R.id.iv_almost_there)
    ImageView ivAlmostThere;
    @Bind(R.id.iv_completed)
    ImageView ivCompleted;

    @Bind(R.id.iv_just_started_circle)
    ImageView ivJustStartedCircle;
    @Bind(R.id.iv_half_done_circle)
    ImageView ivhalfDoneCircle;
    @Bind(R.id.iv_almost_there_circle)
    ImageView ivAlmostThereCircle;
    @Bind(R.id.iv_completed_circle)
    ImageView ivCompletedCircle;

    @Bind(R.id.view_just_started)
    View viewJustStarted;
    @Bind(R.id.view_half_done)
    View viewhalfDone;
    @Bind(R.id.view_complete)
    View viewComplete;

    @Bind(R.id.tv_just_started)
    TextView tvJustStarted;
    @Bind(R.id.tv_half_done)
    TextView tvHalfDone;
    @Bind(R.id.tv_almost_there)
    TextView tvAlmostThere;
    @Bind(R.id.tv_completed)
    TextView tvCompleted;
    @Bind(R.id.iv_fb_share)
    ImageView ivFbShare;
    @Bind(R.id.tv_share_progress)
    TextView tvShareProgress;

    @Bind(R.id.li_update_share)
    LinearLayout liUpdateShare;

    @Bind(R.id.tv_after_challenge_accepted)
    TextView tvAfterChallengeAccepted;
    Context mContext;
    MyCountDownTimer myCountDownTimer;
    public ChallengeItemCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ChallengeDataItem item, final Context context, int position) {
        this.dataItem = item;
        mContext = context;
        dataItem.setItemPosition(position);

        if (dataItem.is_accepted()) {
            liBeforeAccept.setVisibility(View.GONE);
            liAfterAccept.setVisibility(View.VISIBLE);
            if (StringUtil.isNotNullOrEmptyString(dataItem.getChallengeName())) {
                tvChallengeNameUpdateStatus.setText(dataItem.getChallengeName());
            }
            if (dataItem.getTotalPeopleAccepted() < 1 && dataItem.getTotalPeopleAcceptedDelhi() < 1) {
                viewLineAfter.setVisibility(View.INVISIBLE);
                liAfterAcceptTotalCount.setVisibility(View.INVISIBLE);
            } else {
                liAfterAcceptTotalCount.setVisibility(View.VISIBLE);
                viewLineAfter.setVisibility(View.VISIBLE);
            }
            if (dataItem.getTotalPeopleAccepted() > AppConstants.NO_REACTION_CONSTANT) {
                tvAfterCountryName.setVisibility(View.VISIBLE);
                flAfterTotalCountCountry.setVisibility(View.VISIBLE);
                String str;
                if (dataItem.getTotalPeopleAccepted() >= 1000) {
                    int total = dataItem.getTotalPeopleAccepted() / 1000;
                    str = total + AppConstants.THOUSANDS;
                    tvAfterCountInCountry.setText(str);
                } else {
                    str = dataItem.getTotalPeopleAccepted() + AppConstants.EMPTY_STRING;
                    tvAfterCountInCountry.setText(str);
                }
            } else {
                tvAfterCountryName.setVisibility(View.INVISIBLE);
                flAfterTotalCountCountry.setVisibility(View.INVISIBLE);
            }
            if (dataItem.getTotalPeopleAcceptedDelhi() > AppConstants.NO_REACTION_CONSTANT) {
                tvAfterCityName.setVisibility(View.VISIBLE);
                flAfterTotalCountCity.setVisibility(View.VISIBLE);
                String str;
                if (dataItem.getTotalPeopleAcceptedDelhi() >= 1000) {
                    int total = dataItem.getTotalPeopleAccepted() / 1000;
                    str = total + AppConstants.THOUSANDS;
                    tvAfterCountInCity.setText(str);
                } else {
                    str = dataItem.getTotalPeopleAcceptedDelhi() + AppConstants.EMPTY_STRING;
                    tvAfterCountInCity.setText(str);
                }
            } else {
                tvAfterCityName.setVisibility(View.INVISIBLE);
                flAfterTotalCountCity.setVisibility(View.INVISIBLE);
            }
            if (dataItem.getChallengeDuration() > AppConstants.NO_REACTION_CONSTANT) {
                if(null!=myCountDownTimer)
                {
                    myCountDownTimer.cancel();
                }
                myCountDownTimer = new MyCountDownTimer(dataItem.getChallengeDuration()*1000, 1000,tvTimerCountChallengeUpdateStatus,viewInterface,context,dataItem);
                myCountDownTimer.start();
            }
            if (dataItem.getCompletionPercent() == AppConstants.HALF_DONE) {
                ivhalfDone.setAlpha(1.0f);
                ivhalfDoneCircle.setAlpha(1.0f);
                viewJustStarted.setAlpha(1.0f);
                tvHalfDone.setAlpha(1.0f);

            } else if (dataItem.getCompletionPercent() == AppConstants.ALMOST_DONE) {
                ivhalfDone.setAlpha(1.0f);
                ivhalfDoneCircle.setAlpha(1.0f);
                viewJustStarted.setAlpha(1.0f);
                tvHalfDone.setAlpha(1.0f);

                ivAlmostThere.setAlpha(1.0f);
                ivAlmostThereCircle.setAlpha(1.0f);
                viewhalfDone.setAlpha(1.0f);
                tvAlmostThere.setAlpha(1.0f);

            } else if (dataItem.getCompletionPercent() == AppConstants.COMPLETE) {
                ivhalfDone.setAlpha(1.0f);
                ivhalfDoneCircle.setAlpha(1.0f);
                viewJustStarted.setAlpha(1.0f);
                tvHalfDone.setAlpha(1.0f);

                ivAlmostThere.setAlpha(1.0f);
                ivAlmostThereCircle.setAlpha(1.0f);
                viewhalfDone.setAlpha(1.0f);
                tvAlmostThere.setAlpha(1.0f);


                ivCompleted.setBackgroundResource(R.drawable.ic_completed_select);
                ivCompletedCircle.setAlpha(1.0f);
                viewComplete.setAlpha(1.0f);
                tvCompleted.setAlpha(1.0f);
                tvAfterChallengeAccepted.setText(mContext.getString(R.string.ID_CHALLENGE_COMPLETED));
                liUpdateShare.setVisibility(View.GONE);
                tvCompleteShare.setVisibility(View.VISIBLE);
            }
        } else {
            liBeforeAccept.setVisibility(View.VISIBLE);
            liAfterAccept.setVisibility(View.GONE);
            if (dataItem.getChallengeDuration() > AppConstants.NO_REACTION_CONSTANT) {
                if(null!=myCountDownTimer)
                {
                    myCountDownTimer.cancel();
                }
                myCountDownTimer = new MyCountDownTimer(dataItem.getChallengeDuration()*1000, 1000,tvTimerCountChallenge,viewInterface,context,dataItem);
                myCountDownTimer.start();
            }
            if (StringUtil.isNotNullOrEmptyString(dataItem.getChallengeName())) {
                tvChallengeName.setText(dataItem.getChallengeName());
            }
            if (dataItem.getTotalPeopleAccepted() < 1 && dataItem.getTotalPeopleAcceptedDelhi() < 1) {
                liTotalCount.setVisibility(View.INVISIBLE);
                viewLine.setVisibility(View.INVISIBLE);
            } else {
                liTotalCount.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.VISIBLE);
            }
            if (dataItem.getTotalPeopleAccepted() > AppConstants.NO_REACTION_CONSTANT) {
                tvCountryName.setVisibility(View.VISIBLE);
                flTotalCountCountry.setVisibility(View.VISIBLE);
                String str;
                if (dataItem.getTotalPeopleAccepted() >= 1000) {
                    int total = dataItem.getTotalPeopleAccepted() / 1000;
                    str = total + AppConstants.THOUSANDS;
                    tvCountInCountry.setText(str);
                } else {
                    str = dataItem.getTotalPeopleAccepted() + AppConstants.EMPTY_STRING;
                    tvCountInCountry.setText(str);
                }
            } else {
                tvCountryName.setVisibility(View.INVISIBLE);
                flTotalCountCountry.setVisibility(View.INVISIBLE);
            }
            if (dataItem.getTotalPeopleAcceptedDelhi() > AppConstants.NO_REACTION_CONSTANT) {
                tvCityName.setVisibility(View.VISIBLE);
                flTotalCountCity.setVisibility(View.VISIBLE);
                String str;
                if (dataItem.getTotalPeopleAcceptedDelhi() >= 1000) {
                    int total = dataItem.getTotalPeopleAccepted() / 1000;
                    str = total + AppConstants.THOUSANDS;
                    tvCountInCity.setText(str);
                } else {
                    str = dataItem.getTotalPeopleAcceptedDelhi() + AppConstants.EMPTY_STRING;
                    tvCountInCity.setText(str);
                }
            } else {
                tvCityName.setVisibility(View.INVISIBLE);
                flTotalCountCity.setVisibility(View.INVISIBLE);
            }
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getAuthorImgUrl())) {
            String imageUrl = dataItem.getAuthorImgUrl();
            ivChallengeIcon.setCircularImage(true);
            ivChallengeIcon.bindImage(imageUrl);
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getAuthorName())) {
            tvAutherName.setText(dataItem.getAuthorName());
        }
    }
    @OnClick(R.id.tv_complete_share)
    public void completeShareClick() {
        viewInterface.handleOnClick(dataItem, tvShareProgress);
    }

    @OnClick(R.id.tv_update_progress)
    public void updateProgressClick() {
        viewInterface.handleOnClick(dataItem, tvUpdateProgress);
    }

    @OnClick(R.id.tv_accept_challenge)
    public void acceptChallengeClick() {
        viewInterface.handleOnClick(dataItem, tvAcceptChallenge);
    }

    @OnClick(R.id.iv_fb_share)
    public void shareFacebook() {
        viewInterface.handleOnClick(dataItem, ivFbShare);
    }

    @OnClick(R.id.li_challenge_friend)
    public void challengeFriend() {
        viewInterface.handleOnClick(dataItem, ivFbShare);
    }

    @OnClick(R.id.tv_share_progress)
    public void shareProgress() {
        viewInterface.handleOnClick(dataItem, tvShareProgress);
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
    }


}
