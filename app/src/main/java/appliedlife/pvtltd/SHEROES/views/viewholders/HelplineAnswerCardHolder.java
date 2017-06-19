package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineChatDoc;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SHEROES-TECH on 23-05-2017.
 */

public class HelplineAnswerCardHolder extends BaseViewHolder<HelplineChatDoc> {

    private final String TAG = LogUtils.makeLogTag(HelplineAnswerCardHolder.class);

    @Bind(R.id.answer)
    TextView answer;

    @Bind(R.id.time_answer)
    TextView answerTime;

    BaseHolderInterface viewInterface;
    private HelplineChatDoc dataItem;

    public HelplineAnswerCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public void bindData(HelplineChatDoc helplineChatDoc, Context context, int position) {
        this.dataItem = helplineChatDoc;
        if(StringUtil.isNotNullOrEmptyString(dataItem.getSearchText())){
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                answer.setText(Html.fromHtml(dataItem.getSearchText(), 0).toString());
            } else {
                answer.setText(Html.fromHtml(dataItem.getSearchText()).toString());
            }
        }
        if(StringUtil.isNotNullOrEmptyString(dataItem.getFormatedDate())) {
            answerTime.setText(dataItem.getFormatedDate());
        }
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View v) {

    }
}
