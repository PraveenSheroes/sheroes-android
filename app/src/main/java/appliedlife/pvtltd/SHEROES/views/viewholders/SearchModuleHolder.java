package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 18-01-2017.
 */

public class SearchModuleHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(SearchModuleHolder.class);
    @Bind(R.id.tv_search_list_header_text)
    TextView mTvHeaderText;
    @Bind(R.id.tv_search_list_label_text)
    TextView mTvLabelText;
    @Bind(R.id.rl_search_module_list)
    RelativeLayout rlSearchModuleList;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    private int position;


    public SearchModuleHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail item, Context context, int position) {
        this.dataItem = item;
        if (null != dataItem && StringUtil.isNotNullOrEmptyString(dataItem.getSubType())) {

            switch (item.getSubType()) {
                case AppConstants.FEED_ARTICLE:
                    mTvHeaderText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_feed_article_top_left, 0, 0, 0);
                    mTvHeaderText.setText(dataItem.getNameOrTitle());
                    rlSearchModuleList.setTag(AppConstants.FEED_ARTICLE);
                    break;
                case AppConstants.FEED_COMMUNITY:
                    mTvHeaderText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_group_icon, 0, 0, 0);
                    mTvHeaderText.setText(item.getNameOrTitle());
                    mTvLabelText.setText(item.getCommunityType());
                    mTvLabelText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_lock, 0);
                    rlSearchModuleList.setTag(AppConstants.FEED_COMMUNITY);
                    break;
                case AppConstants.FEED_COMMUNITY_POST:
                    mTvHeaderText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_group_icon, 0, 0, 0);
                    mTvHeaderText.setText(item.getNameOrTitle());
                    mTvLabelText.setText(item.getCommunityType());
                    rlSearchModuleList.setTag(AppConstants.FEED_COMMUNITY_POST);
                    break;
                case AppConstants.FEED_JOB:
                    mTvHeaderText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_proffesion_icon, 0, 0, 0);
                    mTvHeaderText.setText(item.getNameOrTitle());
                    mTvLabelText.setText(item.getAuthorCityName());
                    rlSearchModuleList.setTag(AppConstants.FEED_JOB);
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + item.getSubType());
            }

        }


    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.rl_search_module_list)
    public void onSearchModuleClick() {
        dataItem.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(dataItem, rlSearchModuleList);
    }

    @Override
    public void onClick(View view) {

    }

}
