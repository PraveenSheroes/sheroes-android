package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.HelplineViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineChatDoc;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.linkifyURLs;

/**
 * Created by SHEROES-TECH on 23-05-2017.
 */

public class HelplineQuestionCardHolder extends HelplineViewHolder<HelplineChatDoc> {

    private final String TAG = LogUtils.makeLogTag(HelplineQuestionCardHolder.class);

    @Bind(R.id.question)
    TextView question;

    @Bind(R.id.time_question)
    TextView questionTime;

    @Bind(R.id.date_stamp)
    TextView dateStamp;

    BaseHolderInterface viewInterface;
    private HelplineChatDoc dataItem;

    public HelplineQuestionCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(HelplineChatDoc helplineChatDoc, Context context, int position, HelplineChatDoc prevObj) {
        this.dataItem = helplineChatDoc;
        if (StringUtil.isNotNullOrEmptyString(dataItem.getSearchText())) {
            question.setText(dataItem.getSearchText());
            linkifyURLs(question);
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getFormatedDate())) {
            String time = dataItem.getFormatedDate().substring(AppConstants.HELPLINE_TIME_START);
            questionTime.setText(time);
            setDate(prevObj, helplineChatDoc, position);
        }
    }

    @Override
    public void bindData(Object obj, Context context, int position) {
    }

    @Override
    public void viewRecycled() {
    }

    @Override
    public void onClick(View v) {
    }

    private void setDate(HelplineChatDoc prevObj, HelplineChatDoc helplineChatDoc, int position) {
        String prevDate = null;
        String currDate = helplineChatDoc.getFormatedDate().substring(AppConstants.HELPLINE_DATE_START, AppConstants.HELPLINE_DATE_END);

        if (prevObj != null) {
            prevDate = prevObj.getFormatedDate().substring(AppConstants.HELPLINE_DATE_START, AppConstants.HELPLINE_DATE_END);
        }

        if (prevObj != null) {
            if (prevDate.equals(currDate)) {
                dateStamp.setVisibility(View.GONE);
            } else {
                dateStamp.setText(currDate);
                dateStamp.setVisibility(View.VISIBLE);
            }
        } else {
            dateStamp.setText(currDate);
            dateStamp.setVisibility(View.VISIBLE);
        }
    }
}
