package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer.NavMenuItem;
import appliedlife.pvtltd.SHEROES.svg.SvgSoftwareLayerSetter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
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
    private static final int IMAGE_SIZE = 22;

    BaseHolderInterface viewInterface;
    private NavMenuItem dataItem;
    public static String selectedOptionName;

    @Bind(R.id.tv_drawer_item)
    TextView tvDrawerItem;

    @Bind(R.id.tv_drawer_image)
    ImageView tvDrawerImage;

    @Bind(R.id.ll_drawer_item)
    RippleView rippleView;

    @Bind(R.id.new_feature)
    TextView newFeature;

    @Inject
    Preference<LoginResponse> mUserPreference;

    private RequestBuilder<PictureDrawable> requestBuilder;

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

        String iconUrl = dataItem.getMenuItemIconUrl();
        String iconUrlSvg = dataItem.getMenuItemIconUrlSvg(); //default icon
        if (CommonUtil.isNotEmpty(iconUrlSvg)) {
            setImageBackgroundSvg(context, iconUrlSvg);
        } else {
            if (StringUtil.isNotNullOrEmptyString(iconUrl)) {
                setImageBackground(context, iconUrl);
            }
        }

        if (null != selectedOptionName && selectedOptionName.equalsIgnoreCase(itemName)) {
            String iconSelectedUrlSvg = dataItem.getMenuItemIconUrlSelectedSvg();  //Selected icon
            String iconSelectedUrl = dataItem.getMenuItemIconUrlSelected();  //Selected icon
            if (CommonUtil.isNotEmpty(iconSelectedUrlSvg)) {
                setImageBackgroundSvg(context, iconSelectedUrlSvg);
            } else {
                if (StringUtil.isNotNullOrEmptyString(iconSelectedUrl)) {
                    setImageBackground(context, iconSelectedUrl);
                }
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
        String imageKitUrl = CommonUtil.getThumborUri(url, CommonUtil.convertDpToPixel(IMAGE_SIZE, context), CommonUtil.convertDpToPixel(IMAGE_SIZE, context));
        if (CommonUtil.isNotEmpty(imageKitUrl)) {
            Glide.with(context)
                    .load(imageKitUrl)
                    .into(tvDrawerImage);
        }
    }

    private void setImageBackgroundSvg(Context context, String url) {
        requestBuilder = Glide.with(context)
                .as(PictureDrawable.class)
                .listener(new SvgSoftwareLayerSetter());
        requestBuilder.load(url).into(tvDrawerImage);
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