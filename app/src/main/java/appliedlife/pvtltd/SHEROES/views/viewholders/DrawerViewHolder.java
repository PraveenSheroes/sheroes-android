package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.DrawerItems;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 05-01-2017.
 */

public class DrawerViewHolder extends BaseViewHolder<DrawerItems> {
    private final String TAG = LogUtils.makeLogTag(DrawerViewHolder.class);
    BaseHolderInterface viewInterface;
    private DrawerItems dataItem;
    @Bind(R.id.tv_drawer_item)
    TextView tvDrawerItem;
    @Bind(R.id.tv_drawer_image)
    TextView tvDrawerImage;
    @Bind(R.id.ll_drawer_item)
    LinearLayout llDrawerItem;
    public DrawerViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(DrawerItems item, Context context, int position) {
        this.dataItem = item;
        llDrawerItem.setOnClickListener(this);
        int drawerItemId = item.getId();
        switch (drawerItemId) {
            case 1:
                tvDrawerImage.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_profile), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_PROFILE));
                break;
            case 2:
                tvDrawerImage.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_article), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_ARTICLE));
                break;
            case 3:
                tvDrawerImage.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_job_navigation), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_JOBS));
                break;
            case 4:
                tvDrawerImage.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_bookmark), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_BOOKMARK));
                break;
            case 5:
                tvDrawerImage.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_setting), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_SETTING));
                break;
            case 6:
                tvDrawerImage.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_ask_sheroes), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_ASK_SHEROES));
                break;
            case 7:
                tvDrawerImage.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_helpline), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_ASK_SHEROES));
                break;
            case 8:
                tvDrawerImage.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_icc_members), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_ICC_MEMBERS));
                break;
            case 9:
                tvDrawerImage.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_faq), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_FAQS));
                break;
            case 10:
                tvDrawerImage.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_feed), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_FEED));
                break;
            case AppConstants.ELEVENTH_CONSTANT:
                tvDrawerImage.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_logout), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_LOGOUT));
                tvDrawerItem.setTextColor(ContextCompat.getColor(context, R.color.blue));
                tvDrawerItem.setTextSize(16.0f);
                break;
            case 12:
                tvDrawerImage.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_invite_friend), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_INVITE_WOMEN_FRIEND));
                tvDrawerItem.setTextColor(ContextCompat.getColor(context, R.color.ask_sheroes));
                break;
            case 13:
                tvDrawerImage.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context,R.drawable.ic_invite_friend), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_INVITE_REFERRAL_FRIEND));
                tvDrawerItem.setTextColor(ContextCompat.getColor(context, R.color.ask_sheroes));
                break;
            case 14:
                tvDrawerImage.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context,R.drawable.ic_champions), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_GROWTH_BUDDIES));
                break;
            case 15:
                tvDrawerImage.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context,R.drawable.ic_mychallenge), null, null, null);
                tvDrawerItem.setText(context.getString(R.string.ID_CHALLENGED));
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE+ TAG +  AppConstants.SPACE + drawerItemId);
        }

    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ll_drawer_item:
                viewInterface.handleOnClick(dataItem, view);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}