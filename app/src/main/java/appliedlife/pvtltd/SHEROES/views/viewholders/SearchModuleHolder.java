package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleRequest;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 18-01-2017.
 */

public class SearchModuleHolder extends BaseViewHolder<ArticleRequest> {
    @Bind(R.id.tv_search_list_header_text)
    TextView mTvHeaderText;
    @Bind(R.id.tv_search_list_label_text)
    TextView mTvLabelText;
    BaseHolderInterface viewInterface;
    private ArticleRequest dataItem;
    private int position;


    public SearchModuleHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ArticleRequest item, Context context, int position) {
        this.dataItem = item;
        if(StringUtil.isNotNullOrEmptyString(item.getName())) {
            if(item.getDescription().equalsIgnoreCase("Connect"))
            {
                mTvHeaderText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_proffesion_icon,0,0,0);
            }
            mTvHeaderText.setText(item.getName());
            mTvLabelText.setText(item.getDescription());
        }
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        viewInterface.handleOnClick(this.dataItem,view);
    }

}
