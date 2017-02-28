package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 13-01-2017.
 */

public class HomeSpinnerFooterHolder extends BaseViewHolder<HomeSpinnerItem> {
    private final String TAG = LogUtils.makeLogTag(HomeSpinnerFooterHolder.class);
    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    @Bind(R.id.tv_done)
    TextView tvDone;
    BaseHolderInterface viewInterface;
    private HomeSpinnerItem dataItem;
    Context mContext;

    public HomeSpinnerFooterHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(HomeSpinnerItem item, Context context, int position) {
        mContext = context;
        this.dataItem = item;
        tvDone.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_done:
                if (viewInterface != null && StringUtil.isNotEmptyCollection(viewInterface.getListData())) {
                    tvDone.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
                    List<BaseResponse> mHomeSpinnerItemList = viewInterface.getListData();
                    if (StringUtil.isNotEmptyCollection(mHomeSpinnerItemList)) {
                        BaseResponse baseResponse = getAllSelectedData(mHomeSpinnerItemList);
                        viewInterface.handleOnClick(baseResponse, tvDone);
                    }

                }
                break;
            case R.id.tv_cancel:
                tvCancel.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
                List<BaseResponse> mHomeSpinnerItemList = viewInterface.getListData();
                BaseResponse baseResponse = mHomeSpinnerItemList.get(getAdapterPosition());
                HomeSpinnerItem homeSpinnerItem = (HomeSpinnerItem) baseResponse;
                boolean isDone = homeSpinnerItem.isDone();
                if (isDone) {
                    dataItem.setName(AppConstants.FEED_ARTICLE);
                } else {
                    dataItem.setName(AppConstants.EMPTY_STRING);
                }
                viewInterface.handleOnClick(dataItem, tvCancel);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

    private HomeSpinnerItem getAllSelectedData(List<BaseResponse> homeSpinnerItemList) {
        String addAllText = AppConstants.EMPTY_STRING;
        List<Integer>categoryList=new ArrayList<>();
        HomeSpinnerItem homeSpinnerItem = new HomeSpinnerItem();
        for (BaseResponse baseResponse : homeSpinnerItemList) {
            homeSpinnerItem = ((HomeSpinnerItem) baseResponse);
            if (homeSpinnerItem.isChecked()) {
                addAllText += homeSpinnerItem.getName() + AppConstants.COMMA;
                categoryList.add(homeSpinnerItem.getCategoryIdItem());
            }
        }
        if (StringUtil.isNotNullOrEmptyString(addAllText)) {
            homeSpinnerItem.setName(addAllText.substring(0, addAllText.length() - 1));
            homeSpinnerItem.setDone(true);
            homeSpinnerItem.setCategoryId(categoryList);
        }
        return homeSpinnerItem;
    }
}