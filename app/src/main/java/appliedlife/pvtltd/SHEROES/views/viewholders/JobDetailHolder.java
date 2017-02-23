package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobDetailPojo;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleDetailPojo;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.JobDetailActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ajit Kumar on 20-02-2017.
 */

public class JobDetailHolder extends BaseViewHolder<JobDetailPojo> {
    @Bind(R.id.tv_job_des)
    TextView mTv_job_des;
    BaseHolderInterface viewInterface;
    private JobDetailPojo dataItem;
    private FeedDetail mFeedDetail;
    private Context mContext;
    public JobDetailHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
    }

    @Override
    public void bindData(JobDetailPojo obj, Context context, int position) {
        dataItem = obj;
        mFeedDetail = dataItem.getFeedDetail();
        if (null != mFeedDetail) {

            String description = mFeedDetail.getListDescription();
            if (StringUtil.isNotNullOrEmptyString(description)) {
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    mTv_job_des.setText(Html.fromHtml(description, 0)); // for 24 api and more
                } else {
                    mTv_job_des.setText(Html.fromHtml(description));// or for older api
                }
            }
        }
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View v) {

    }
}
