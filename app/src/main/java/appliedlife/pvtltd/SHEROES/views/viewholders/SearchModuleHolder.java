package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
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

/**
 * Created by Praveen_Singh on 18-01-2017.
 */

public class SearchModuleHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(SearchModuleHolder.class);
    @Bind(R.id.tv_search_list_header_text)
    TextView mTvHeaderText;
    @Bind(R.id.tv_search_list_label_text)
    TextView mTvLabelText;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    private int position;


    public SearchModuleHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail item, Context context, int position) {
        this.dataItem = item;
        if(StringUtil.isNotNullOrEmptyString(item.getSubType())) {
            switch (item.getSubType())
            {
                case AppConstants.FEED_ARTICLE:
                    mTvHeaderText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_feed_article_top_left,0,0,0);
                    break;
                case AppConstants.FEED_COMMUNITY:
                    mTvHeaderText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_group_icon,0,0,0);
                    break;
                case AppConstants.FEED_COMMUNITY_POST:
                    mTvHeaderText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_group_icon,0,0,0);
                    break;
                case AppConstants.FEED_JOB:
                    mTvHeaderText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_proffesion_icon,0,0,0);
                    break;
                default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + item.getSubType());
            }

        }
            mTvHeaderText.setText(item.getAuthorName());
            mTvLabelText.setText(item.getAuthorCityName());

    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        viewInterface.handleOnClick(this.dataItem,view);
        int id = view.getId();
        switch (id) {
          //  case R.id.iv_dashboard:
          //      break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}
