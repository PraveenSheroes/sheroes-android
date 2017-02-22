package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.TouchImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageFullViewAdapter extends PagerAdapter {
    private final String TAG = LogUtils.makeLogTag(ImageFullViewAdapter.class);
    @Bind(R.id.iv_full_image)
    TouchImageView ivFullImage;
    @Bind(R.id.iv_full_image_back)
    Button ivFullImageBack;
    @Bind(R.id.tv_total_image)
    TextView tvTotalImage;
    private FeedDetail mFeedDetail;
    private FragmentOpen mFragmentOpen;
    private Context mContext;
    private List<String> mTotalCoverImages = new ArrayList<>();
    private LayoutInflater inflater;
    private HomeActivityIntraction mHomeActivityIntraction;

    public ImageFullViewAdapter(Context context, FeedDetail feedDetail, FragmentOpen fragmentOpen) {
        this.mContext = context;
        this.mFeedDetail = feedDetail;
        this.mFragmentOpen = fragmentOpen;
        mTotalCoverImages = mFeedDetail.getImageUrls();
        mHomeActivityIntraction = (HomeActivityIntraction) context;
    }

    @Override
    public int getCount() {
        return this.mTotalCoverImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container, false);
        ButterKnife.bind(this, viewLayout);
        if (StringUtil.isNotEmptyCollection(mTotalCoverImages)) {
            tvTotalImage.setText(String.valueOf(position + 1) + AppConstants.BACK_SLASH + String.valueOf(mTotalCoverImages.size()));
            Glide.with(mContext)
                    .load(mTotalCoverImages.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFullImage);
        }
        (container).addView(viewLayout);
        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((RelativeLayout) object);

    }

    public interface HomeActivityIntraction {
        void onDialogDissmiss(FragmentOpen isFragmentOpen);
    }

    @OnClick(R.id.iv_full_image_back)
    public void dismissCommentDialog() {
        mHomeActivityIntraction.onDialogDissmiss(mFragmentOpen);
    }
}
