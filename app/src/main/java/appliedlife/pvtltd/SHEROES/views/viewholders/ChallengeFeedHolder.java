package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeDataItem;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.ScrimUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ujjwal on 10/10/17.
 */

public class ChallengeFeedHolder extends BaseViewHolder<FeedDetail> {

    @Bind(R.id.feature_image)
    ImageView mFeatureImage;

    @Bind(R.id.scrim_view)
    View mScrimView;

    @Bind(R.id.contest_tag)
    TextView mContestTag;

    @Bind(R.id.title)
    TextView mTitle;

    @Bind(R.id.contest_end_text)
    TextView mContestEndText;

    @Bind(R.id.contest_status)
    TextView mContestStatus;

    @Bind(R.id.join_challenge_text)
    TextView mJoinChallengeText;

    @Bind(R.id.response_view_count)
    TextView mResponseViewCount;


    BaseHolderInterface viewInterface;
    private FeedDetail feedDetail;


    public ChallengeFeedHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    @Override
    public void bindData(FeedDetail feedDetail, Context context, int position) {
        this.feedDetail = feedDetail;

      /*  tvCanHelpAdd.setOnClickListener(this);
        tvCanHelpNumber.setText(dataItem.getType());
        List<CanHelpIn> canHelpIns=this.dataItem.getCanHelpIn();


        if(null !=dataItem.getCanHelpIn() && dataItem.getCanHelpIn().size() >0) {
            if (StringUtil.isNotNullOrEmptyString(canHelpIns.get(0).getName())) {
                mTv_interesting_text1.setVisibility(View.VISIBLE);
                mTv_interesting_text1.setText(canHelpIns.get(0).getName());

            }
            if(StringUtil.isNotNullOrEmptyString(canHelpIns.get(1).getName()) && dataItem.getCanHelpIn().size() >0)
            {
                mTv_interesting_text2.setVisibility(View.VISIBLE);
                mTv_interesting_text2.setText(canHelpIns.get(1).getName());

            }
        }*/
      mTitle.setText("Showcase your workspace. Share your workspace photo that motivate you to work daily.");
      mContestEndText.setText("Ends in: 4 Days");
      mContestStatus.setText("LIVE");
      mJoinChallengeText.setText("Join the Challenge");
      mResponseViewCount.setText("198 responses  ·  235 views");
        mScrimView.setBackground(ScrimUtil.makeCubicGradientScrimDrawable(
                0xaa000000, 8, Gravity.TOP));
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
/*
            case R.id.tv_add_can_help:

                viewInterface.handleOnClick(this.dataItem,tvCanHelpAdd);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + view.getId());*/
        }

    }
}

