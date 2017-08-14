package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 01-02-2017.
 */

public class CommunityCardDetailHeader extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(CommunityCardDetailHeader.class);
    @Bind(R.id.card_community_detail)
    CardView cardCommunityDetail;
    @Bind(R.id.tv_community_name)
    TextView tvCommunityName;
    @Bind(R.id.tv_join_view_holder)
    TextView tvJoin;
    @Bind(R.id.tv_community_related)
    TextView tvCommunityRelated;
    @Bind(R.id.iv_communities_detail)
    ImageView iv_communities_detail;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    private Context mContext;
    @Inject
    Preference<LoginResponse> userPreference;


    public CommunityCardDetailHeader(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        this.viewInterface = baseHolderInterface;

    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        this.mContext = context;

        if (StringUtil.isNotNullOrEmptyString(dataItem.getThumbnailImageUrl())) {
            Glide.with(context).load(dataItem.getThumbnailImageUrl()).transform(new CircleTransform(mContext)).into(iv_communities_detail);
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())) {
            tvCommunityName.setText(dataItem.getNameOrTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCommunityType())) {
            tvCommunityRelated.setText(dataItem.getCommunityType());
        }
      /*  if (StringUtil.isNotNullOrEmptyString(dataItem.getScreenName())) {
            switch (dataItem.getScreenName()) {
                case AppConstants.ALL_SEARCH:
                    if (!dataItem.isMember() && !dataItem.isOwner() && !dataItem.isRequestPending()) {
                        tvJoin.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
                        tvJoin.setText(mContext.getString(R.string.ID_JOIN));
                        tvJoin.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                    } else {
                        tvJoin.setVisibility(View.GONE);
                    }
                    break;
                case AppConstants.FEATURE_FRAGMENT:
                    if (!dataItem.isMember() && !dataItem.isOwner() && !dataItem.isRequestPending()) {
                        tvJoin.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
                        tvJoin.setText(mContext.getString(R.string.ID_JOIN));
                        tvJoin.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                    } else {
                        tvJoin.setVisibility(View.GONE);
                    }
                    break;
                case AppConstants.MY_COMMUNITIES_FRAGMENT:
                    tvJoin.setVisibility(View.GONE);
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + dataItem.getScreenName());
            }
        }*/
        if (!dataItem.isMember() && !dataItem.isOwner() && !dataItem.isRequestPending()&&dataItem.isFeatured()) {
            tvJoin.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            tvJoin.setText(mContext.getString(R.string.ID_JOIN));
            tvJoin.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
            tvJoin.setVisibility(View.VISIBLE);
        } else {
            tvJoin.setVisibility(View.GONE);
        }

        if(dataItem != null && StringUtil.isNotNullOrEmptyString(dataItem.getId() )&&StringUtil.isNotNullOrEmptyString(dataItem.getListDescription()) && null != userPreference && userPreference.isSet() && null != userPreference.get() && userPreference.get().getUserSummary() !=null){
            ((SheroesApplication)((BaseActivity)mContext).getApplication()).trackEvent(AppConstants.IMPRESSIONS,AppConstants.COMMUNITY_POST_IMPRESSION, dataItem.getId() + AppConstants.DASH +userPreference.get().getUserSummary().getUserId() + AppConstants.DASH + dataItem.getListDescription() );
        }

    }

    @OnClick(R.id.card_community_detail)
    public void onBackClick() {
        viewInterface.handleOnClick(dataItem, cardCommunityDetail);
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

    }

    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    @OnClick(R.id.tv_join_view_holder)
    public void onJoinButtonClick() {
        viewInterface.handleOnClick(dataItem, tvJoin);
    }
}
