package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
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
 * Created by Praveen_Singh on 25-03-2017.
 */

public class GetAllDataBoardingSearchHolder extends BaseViewHolder<GetAllDataDocument> {
    private final String TAG = LogUtils.makeLogTag(GetAllDataBoardingSearchHolder.class);
    BaseHolderInterface viewInterface;
    private GetAllDataDocument dataItem;
    private Context mContext;
    @Bind(R.id.li_city_name_layout)
    LinearLayout liCityNameLayout;
    @Bind(R.id.tv_city_name)
    TextView tvCityName;
    public GetAllDataBoardingSearchHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
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
    @OnClick(R.id.li_city_name_layout)
    public void cityItemOnClick() {
        viewInterface.handleOnClick(dataItem, liCityNameLayout);
    }

    @Override
    public void onClick(View view) {

    }


}