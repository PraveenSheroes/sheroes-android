package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineChatDoc;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.linkifyURLs;

/**
 * Created by SHEROES-TECH on 23-05-2017.
 */

public class HelplineAnswerCardHolder extends BaseViewHolder<HelplineChatDoc> {

    private final String TAG = LogUtils.makeLogTag(HelplineAnswerCardHolder.class);

    @Bind(R.id.answer)
    TextView answer;

    @Bind(R.id.time_answer)
    TextView answerTime;

    @Bind(R.id.iv_mentor_full_view_icon)
    CircleImageView counselorImage;

    @BindDimen(R.dimen.counselor_image_size)
    int counselorImageSize;

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
        if (StringUtil.isNotNullOrEmptyString(dataItem.getSearchText())) {
            answer.setText(dataItem.getSearchText());
            linkifyURLs(answer);
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getFormatedDate())) {
            String date = dataItem.getFormatedDate().substring(0, 11);
            String time = dataItem.getFormatedDate().substring(12);
            answerTime.setText(time);
        }
        if (helplineChatDoc.getThumbnailImageUrl() != null) {  //mentor image icon
            counselorImage.setCircularImage(true);
            String authorThumborUrl = CommonUtil.getThumborUri(helplineChatDoc.getThumbnailImageUrl(), counselorImageSize, counselorImageSize);
            counselorImage.bindImage(authorThumborUrl);
        }
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View v) {

    }
}
