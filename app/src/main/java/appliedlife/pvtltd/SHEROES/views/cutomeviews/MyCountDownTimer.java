package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeDataItem;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;

/**
 * Created by Praveen_Singh on 30-05-2017.
 */

public class MyCountDownTimer extends CountDownTimer {
    TextView textView;
    BaseHolderInterface viewInterface;
    Context context;
    ChallengeDataItem dataItem;
    public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView textView, BaseHolderInterface viewInterface, Context context,ChallengeDataItem dataItem) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.viewInterface=viewInterface;
        this.context=context;
        this.dataItem=dataItem;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        dateSetWithTimerSecond(millisUntilFinished);
    }

    @Override
    public void onFinish() {
        textView.setText(context.getString(R.string.ID_COMPLETED));
    }


    private void dateSetWithTimerSecond(long millisUntilFinished) {
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
        textView.setText(stringBuilder.toString());
    }
}