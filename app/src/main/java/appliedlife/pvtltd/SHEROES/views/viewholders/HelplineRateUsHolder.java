package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineChatDoc;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SHEROES-TECH on 23-05-2017.
 */

public class HelplineRateUsHolder extends BaseViewHolder<HelplineChatDoc> {

    private final String TAG = LogUtils.makeLogTag(HelplineRateUsHolder.class);

    @Bind(R.id.rate_us_screen)
    LinearLayout rateUsScreen;

    @Bind(R.id.rate_us)
    Button rateUs;

    @Bind(R.id.not_now)
    Button notNow;

    BaseHolderInterface viewInterface;
    private HelplineChatDoc dataItem;
    private Context mContext;

    public HelplineRateUsHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public void bindData(HelplineChatDoc helplineChatDoc, Context context, int position) {
        this.dataItem = helplineChatDoc;
        this.mContext = context;
        //Visibility when call comes
        //rateUsScreen.setVisibility(View.VISIBLE);
    }

    @Override
    public void viewRecycled() {
    }

    @Override
    public void onClick(View v) {
    }

    @OnClick(R.id.rate_us)
    public void RateUsClicked() {
        CommonUtil.openPlayStore(mContext, SheroesApplication.mContext.getPackageName());
        rateUsScreen.setVisibility(View.GONE);
        //dataItem.setNeedRating(false);
    }

    @OnClick(R.id.not_now)
    public void NotNowClicked() {
        rateUsScreen.setVisibility(View.GONE);
        //dataItem.setNeedRating(false);
    }
}
