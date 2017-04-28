package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityTags;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ajit Kumar on 07-02-2017.
 */

public class TagsHolder extends BaseViewHolder<CommunityTags> {
    @Bind(R.id.tv_tags)
    TextView tvTags;

    @Bind(R.id.tv_tags1)
    TextView tvTags1;

    @Bind(R.id.tv_tags2)
    TextView tvTags2;

    @Bind(R.id.tv_tags3)
    TextView tvTags3;

    @Bind(R.id.tv_tags4)
    TextView tvTags4;
    BaseHolderInterface viewInterface;
    private CommunityTags dataItem;
    private int position=0;
     static int mcount=0;
    private final String TAG = LogUtils.makeLogTag(TagsHolder.class);


    public TagsHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
    }

    @Override
    public void bindData(CommunityTags obj, Context context, int position) {
        this.dataItem = obj;
        mcount=0;
        itemView.setOnClickListener(this);
        tvTags.setOnClickListener(this);
        tvTags1.setOnClickListener(this);
        tvTags2.setOnClickListener(this);
        tvTags3.setOnClickListener(this);
        tvTags4.setOnClickListener(this);
        tvTags.setText("text" + dataItem.getId());
        if (dataItem.getName().length() > 10 && dataItem.getName().length() < 15) {
            tvTags1.setVisibility(View.VISIBLE);
            tvTags2.setVisibility(View.VISIBLE);
            tvTags3.setVisibility(View.VISIBLE);
            tvTags1.setText(dataItem.getName());

            tvTags2.setText("text" + dataItem.getId());
            tvTags3.setText("text" + dataItem.getId());

        } else if (dataItem.getName().length() > 15 && dataItem.getName().length() < 20) {
            tvTags1.setVisibility(View.VISIBLE);
            tvTags2.setVisibility(View.VISIBLE);
            tvTags1.setText(dataItem.getName());
            tvTags2.setText("text" + dataItem.getId());


        } else if (dataItem.getName().length() > 20 && dataItem.getName().length() < 25) {
            tvTags1.setVisibility(View.VISIBLE);
            tvTags1.setText(dataItem.getName());
        } else if (dataItem.getName().length() > 25) {
            tvTags.setText(dataItem.getName().substring(0, 25));

            tvTags1.setVisibility(View.GONE);
            tvTags2.setVisibility(View.GONE);
            tvTags3.setVisibility(View.GONE);
            tvTags4.setVisibility(View.GONE);


        } else if (dataItem.getName().length() < 10) {
            tvTags1.setVisibility(View.VISIBLE);
            tvTags2.setVisibility(View.VISIBLE);
            tvTags3.setVisibility(View.VISIBLE);
            tvTags4.setVisibility(View.VISIBLE);
            tvTags1.setText(dataItem.getName());

            tvTags2.setText("text" + dataItem.getId());
            tvTags3.setText(dataItem.getName());

            tvTags4.setText("text" + dataItem.getId());

        }


        String images = dataItem.getBackground();


    }


    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        mcount++;

if(mcount<=3)
{
        switch (id) {
            case R.id.tv_tags:
                    tvTags.setBackgroundResource(R.drawable.select_tag_shap);
                    tvTags.setTextColor((Color.parseColor("#3949ab")));
                //tvTags.Typeface.createFromAsset(this, R.string.ID_ROBOTO_MEDIUM));
                tvTags.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));

                LogUtils.error(TAG, ""+mcount);

                break;
            case R.id.tv_tags1:
                tvTags1.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
                    tvTags1.setBackgroundResource(R.drawable.selected_tag_shap);
                    tvTags1.setTextColor((Color.parseColor("#3949ab")));
                LogUtils.error(TAG, ""+mcount);
                break;
            case R.id.tv_tags2:
                tvTags2.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
                    tvTags2.setBackgroundResource(R.drawable.selected_tag_shap);
                    tvTags2.setTextColor((Color.parseColor("#3949ab")));
                LogUtils.error(TAG, ""+mcount);
                break;
            case R.id.tv_tags3:
                tvTags3.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
                    tvTags3.setBackgroundResource(R.drawable.selected_tag_shap);
                    tvTags3.setTextColor((Color.parseColor("#3949ab")));
                LogUtils.error(TAG, ""+mcount);
                break;
            case R.id.tv_tags4:
                    tvTags4.setBackgroundResource(R.drawable.selected_tag_shap);
                    tvTags4.setTextColor((Color.parseColor("#3949ab")));
                LogUtils.error(TAG, ""+mcount);

                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
            HashMap<String, Object> map = new HashMap<String, Object>();
            //   map.put("collection name",dataItem.getTitle());
            map.put("collection id", dataItem.getId());
//    map.put("collection type",dataItem.getType());
            viewInterface.handleOnClick(this.dataItem, view);
            //createCommunityViewInterface.closeDialog("communityDialog");

    }
    }
}
