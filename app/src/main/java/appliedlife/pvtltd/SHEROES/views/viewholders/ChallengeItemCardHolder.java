package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.communities.ChallengeDataItem;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 03-02-2017.
 */

public class ChallengeItemCardHolder extends BaseViewHolder<ChallengeDataItem> {
    private final String TAG = LogUtils.makeLogTag(ChallengeItemCardHolder.class);
    BaseHolderInterface viewInterface;
    private ChallengeDataItem dataItem;
    @Bind(R.id.iv_challenge_icon)
    CircleImageView ivChallengeIcon;
    @Bind(R.id.tv_timer_count_challenge)
    TextView tvTimerCountChallenge;
    @Bind(R.id.tv_accept_challenge)
    TextView tvAcceptChallenge;
    @Bind(R.id.tv_update_progress)
    TextView tvUpdateProgress;

    Context mContext;

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
        String imageUrl = item.getImageUrl();
        ivChallengeIcon.setCircularImage(true);
        ivChallengeIcon.bindImage(imageUrl);
        reverseTimer(120, tvTimerCountChallenge);
    }

    private void reverseTimer(int seconds, final TextView tv) {

        new CountDownTimer(seconds * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int totalCount = (int) millisUntilFinished / 1000;
                int hour = (int) totalCount / 3600;
                int minutes = (int) (totalCount % 3600) / 60;
                int seconds = (int) (totalCount % 3600) % 60;
                StringBuilder stringBuilder = new StringBuilder();
                if (hour < 10) {
                    stringBuilder.append(AppConstants.NO_REACTION_CONSTANT).append(hour).append(AppConstants.COLON);
                } else {
                    stringBuilder.append(hour).append(AppConstants.COLON);
                }
                if (minutes < 10) {
                    stringBuilder.append(AppConstants.NO_REACTION_CONSTANT).append(minutes).append(AppConstants.COLON);
                } else {
                    stringBuilder.append(minutes).append(AppConstants.COLON);
                }
                if (seconds < 10) {
                    stringBuilder.append(AppConstants.NO_REACTION_CONSTANT).append(seconds);
                } else {
                    stringBuilder.append(seconds);
                }
                tv.setText(stringBuilder.toString());
            }

            public void onFinish() {
                tv.setText(mContext.getString(R.string.ID_COMPLETED));
            }
        }.start();
    }

    @OnClick(R.id.tv_update_progress)
    public void updateProgressClick() {
        viewInterface.handleOnClick(dataItem, tvUpdateProgress);
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
    }

}
