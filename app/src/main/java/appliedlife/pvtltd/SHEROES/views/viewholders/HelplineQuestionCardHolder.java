package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineChatDoc;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.linkifyURLs;

/**
 * Created by SHEROES-TECH on 23-05-2017.
 */

public class HelplineQuestionCardHolder extends BaseViewHolder<HelplineChatDoc> {

    private final String TAG = LogUtils.makeLogTag(HelplineQuestionCardHolder.class);

    @Bind(R.id.question)
    TextView question;

    @Bind(R.id.time_question)
    TextView questionTime;

    BaseHolderInterface viewInterface;
    private HelplineChatDoc dataItem;

    public HelplineQuestionCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(HelplineChatDoc helplineChatDoc, Context context, int position) {
        this.dataItem = helplineChatDoc;
        if (StringUtil.isNotNullOrEmptyString(dataItem.getSearchText())) {
            question.setText(dataItem.getSearchText());
            linkifyURLs(question);

        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getFormatedDate())) {
            String date = dataItem.getFormatedDate().substring(0, 11);
            String time = dataItem.getFormatedDate().substring(12);
            questionTime.setText(time);
        }

    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View v) {

    }
}
