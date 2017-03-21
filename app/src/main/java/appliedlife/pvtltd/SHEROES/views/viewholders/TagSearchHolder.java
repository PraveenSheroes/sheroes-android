package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 19-03-2017.
 */

public class TagSearchHolder extends BaseViewHolder<Doc> {

    @Bind(R.id.tv_tag_name)
    TextView tvTagrName;
    @Bind(R.id.rl_tag)
    RelativeLayout rl_tag;
    BaseHolderInterface viewInterface;
    private Doc dataItem;
    private Context mContext;
    public TagSearchHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }



    @Override
    public void bindData(Doc obj, Context context, int position) {
        this.dataItem = obj;
        tvTagrName.setOnClickListener(this);
        mContext = context;
        if (StringUtil.isNotNullOrEmptyString(dataItem.getTitle())) ;
        {
            tvTagrName.setText(dataItem.getTitle());
        }
    }

    @Override
    public void viewRecycled() {

    }

  /*  @OnClick(R.id.tv_tag_name)
    public void inviteOnclick() {
       // rl_tag.setBackgroundColor(R.color.grey2);

       *//* if(!dataItem.isLongPress()) {
            dataItem.setLongPress(true);
        }else
        {
            dataItem.setLongPress(false);

        }*//*
        //viewInterface.handleOnClick(dataItem, tvAddInvite);
    }*/

    @Override
    public void onClick(View view) {
       /* rl_tag.setBackgroundColor(ContextCompat.getColor(mContext, R.color.background_color_project_number));
        tvTagrName.setTextColor(ContextCompat.getColor(mContext, R.color.blue));*/
        tvTagrName.setText("");
        viewInterface.handleOnClick(dataItem,view);
    }
}
