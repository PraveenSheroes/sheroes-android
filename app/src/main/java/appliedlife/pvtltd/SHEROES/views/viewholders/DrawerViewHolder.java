package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer.NavMenuItem;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RippleView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 05-01-2017.
 */

public class DrawerViewHolder extends BaseViewHolder<NavMenuItem> {

    private final String TAG = LogUtils.makeLogTag(DrawerViewHolder.class);

    BaseHolderInterface viewInterface;
    private NavMenuItem dataItem;
    public static String selectedOptionName;
    private boolean isUserProfile;

    @Bind(R.id.tv_drawer_item)
    TextView tvDrawerItem;

    @Bind(R.id.tv_drawer_image)
    AppCompatImageView tvDrawerImage;

    @Bind(R.id.ll_drawer_item)
    RippleView rippleView;

    @Bind(R.id.new_feature)
    TextView newFeature;

    @Inject
    Preference<LoginResponse> mUserPreference;

    public DrawerViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(NavMenuItem item, final Context context, final int position) {
        this.dataItem = item;

        rippleView.setTag(item.getMenuName());

        String itemName = dataItem.getMenuName();
        tvDrawerItem.setText(itemName);

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && null != mUserPreference.get().getUserSummary().getUserBO()) {
                if (mUserPreference.get().getUserSummary().getUserBO().getUserTypeId() != AppConstants.MENTOR_TYPE_ID) {
                    isUserProfile = true;
                }
        }

        if(itemName.equalsIgnoreCase("Profile") && isUserProfile) { //Show new feature only to users
            newFeature.setVisibility(View.VISIBLE);
        } else {
            newFeature.setVisibility(View.GONE);
        }

        String iconUrl = dataItem.getMenuItemIconUrl();   //default icon
        if(StringUtil.isNotNullOrEmptyString(iconUrl)) {
            setImageBackground(context, iconUrl);
        }

        if (null != selectedOptionName && selectedOptionName.equalsIgnoreCase(itemName)) {
            String iconSelectedUrl = dataItem.getMenuItemIconUrlSelected();  //Selected icon
            if (StringUtil.isNotNullOrEmptyString(iconSelectedUrl)) {
                setImageBackground(context, iconSelectedUrl);
            }
        }
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                if(null!= rippleView.getTag()) {
                    selectedOptionName = (String) rippleView.getTag();
                } else{
                    selectedOptionName = null;
                }
                viewInterface.handleOnClick(dataItem, rippleView);
            }
        });
    }

    //set the image icon and cache it
    private void setImageBackground(Context context, String url) {
        if (StringUtil.isNotNullOrEmptyString(url)) {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(tvDrawerImage);
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

                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}