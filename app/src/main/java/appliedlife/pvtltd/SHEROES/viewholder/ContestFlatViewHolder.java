package appliedlife.pvtltd.SHEROES.viewholder;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.ContestListCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.ContestStatus;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RippleView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ujjwal on 28/04/17.
 */

public class ContestFlatViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;

    // region ButterKnife Bindings
    @Bind(R.id.ripple_challenge)
    RippleView mRippleChallenge;

    @Bind(R.id.image)
    ImageView mImage;

    @Bind(R.id.title_contest)
    TextView mTitle;

    @Bind(R.id.contest_status)
    TextView mContestStatus;

    @Bind(R.id.contest_participants)
    TextView mContestParticipants;

    @BindDimen(R.dimen.dp_size_32)
    public int mImageMargin;

    private ContestListCallBack mContestListCallBack;

    private Contest mContest;
    // endregion

    public ContestFlatViewHolder(View itemView, Context context,ContestListCallBack contestListCallBack) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        this.mContext = context;
        this.mContestListCallBack=contestListCallBack;
    }

    public void bindData(Contest contest) {
        this.mContest=contest;
        mTitle.setText(contest.title);
        int featureImageHeight = ((CommonUtil.getWindowWidth(mContext)) - mImageMargin)/2;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, featureImageHeight);
        mImage.setLayoutParams(params);
        if (CommonUtil.isNotEmpty(contest.thumbImage)) {
            String contestImage = CommonUtil.getThumborUri(contest.thumbImage, CommonUtil.getWindowWidth(mContext) - mImageMargin, featureImageHeight);
            Glide.with(mContext)
                    .load(contestImage)
                    .into(mImage);
        } else {
            mImage.setImageResource(R.drawable.challenge_placeholder);
        }
        ContestStatus contestStatus = CommonUtil.getContestStatus(contest.getStartAt(), contest.getEndAt());
        if(contestStatus==ContestStatus.ONGOING){
            mContestParticipants.setText(Integer.toString(contest.submissionCount) + " " + mContext.getResources().getQuantityString(R.plurals.numberOfResponses, contest.submissionCount));
            mContestStatus.setText(R.string.contest_status_ongoing);
            mContestStatus.setTextColor(mContext.getResources().getColor(R.color.email));
            mContestStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_live_dot, 0, 0, 0);
            mContestStatus.setVisibility(View.VISIBLE);
        }
        if(contestStatus==ContestStatus.UPCOMING){
            mContestParticipants.setText(Integer.toString(contest.likesCount) + " " + mContext.getResources().getString(R.string.joined));
            mContestStatus.setText(mContext.getString(R.string.contest_status_upcoming));
            /*mContestStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_date_range_white_16dp, 0, 0, 0);
            mContestStatus.setBackgroundResource(R.drawable.background_upcoming_contest);*/
            mContestStatus.setVisibility(View.VISIBLE);
        }
        if(contestStatus==ContestStatus.COMPLETED){
            mContestParticipants.setText(Integer.toString(contest.submissionCount));
            mContestParticipants.setText(Integer.toString(contest.submissionCount) + " " + mContext.getResources().getQuantityString(R.plurals.numberOfResponses, contest.submissionCount));
            mContestStatus.setText(mContext.getString(R.string.contest_status_completed));
            mContestStatus.setTextColor(mContext.getResources().getColor(R.color.light_green));
            mContestStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vector_contest_completed, 0, 0, 0);
            mContestStatus.setVisibility(View.VISIBLE);
        }
    }
    @OnClick( R.id.ripple_challenge)
    public void challengeClicked() {
        mRippleChallenge.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                mContestListCallBack.onContactClicked(mContest, mRippleChallenge);
            }
        });
    }
}
