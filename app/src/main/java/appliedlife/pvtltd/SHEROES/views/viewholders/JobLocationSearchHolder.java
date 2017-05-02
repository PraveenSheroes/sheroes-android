package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 02-05-2017.
 */

public class JobLocationSearchHolder extends BaseViewHolder<GetAllDataDocument> {
    private final String TAG = LogUtils.makeLogTag(JobLocationSearchHolder.class);
    BaseHolderInterface viewInterface;
    private GetAllDataDocument dataItem;
    private Context mContext;
    @Bind(R.id.tv_job_loc_name)
    TextView tvCityName;
    @Bind(R.id.iv_location)
    ImageView ivLocation;
    @Bind(R.id.li_job_item)
    LinearLayout liJobItem;
    public JobLocationSearchHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
    }

    @Override
    public void bindData(GetAllDataDocument item, final Context context, int position) {
        dataItem = item;
        mContext = context;
        if(null!=dataItem&& StringUtil.isNotNullOrEmptyString(dataItem.getTitle()))
        {
            tvCityName.setText(dataItem.getTitle());
        }
    }

    @Override
    public void viewRecycled() {

    }
    @Override
    public void onClick(View view) {

    }
    @OnClick(R.id.li_job_item)
    public void onListItemClick()
    {
        if(dataItem.isChecked())
        {
            ivLocation.setBackground(ContextCompat.getDrawable(mContext, R.color.fully_transparent));
            tvCityName.setTextColor(ContextCompat.getColor(mContext, R.color.black_current));
            viewInterface.handleOnClick(dataItem, liJobItem);
            dataItem.setChecked(false);

        }else
        {
            ivLocation.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_chek_box));
            tvCityName.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
            viewInterface.handleOnClick(dataItem, liJobItem);
            dataItem.setChecked(true);
        }
    }

}