package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.TouchImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageFullViewAdapter extends PagerAdapter {
    private final String TAG = LogUtils.makeLogTag(ImageFullViewAdapter.class);
    @Bind(R.id.iv_full_image)
    TouchImageView ivFullImage;
    private FeedDetail mFeedDetail;
    private FragmentOpen mFragmentOpen;
    private Context mContext;
    private List<String> mTotalCoverImages = new ArrayList<>();
    private LayoutInflater inflater;
    public TextView tvTotalImage;
    private HomeActivityIntraction mHomeActivityIntraction;
    public ImageFullViewAdapter(Context context, FeedDetail feedDetail, FragmentOpen fragmentOpen,HomeActivityIntraction mHomeActivityIntraction) {
        this.mContext = context;
        this.mFeedDetail = feedDetail;
        this.mFragmentOpen = fragmentOpen;
        this.mHomeActivityIntraction=mHomeActivityIntraction;
        mTotalCoverImages = mFeedDetail.getImageUrls();
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
            int pos=position;
            mHomeActivityIntraction.onSetText(pos);
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
        void onSetText(int value);
    }
}
