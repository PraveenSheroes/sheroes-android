package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import appliedlife.pvtltd.SHEROES.models.entities.feed.ListOfFeed;
import appliedlife.pvtltd.SHEROES.models.entities.feed.TotalCoverImage;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.TouchImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageFullViewAdapter extends PagerAdapter implements View.OnClickListener {
    private final String TAG = LogUtils.makeLogTag(ImageFullViewAdapter.class);
    @Bind(R.id.iv_full_image)
    TouchImageView ivFullImage;
    @Bind(R.id.iv_full_image_back)
    Button ivFullImageBack;
    @Bind(R.id.tv_total_image)
    TextView tvTotalImage;
    private ListOfFeed mListOfFeed;
    private FragmentOpen mFragmentOpen;
    private Context mContext;
    private List<TotalCoverImage> mTotalCoverImages = new ArrayList<>();
    private LayoutInflater inflater;
    private HomeActivityIntraction mHomeActivityIntraction;

    public ImageFullViewAdapter(Context context, ListOfFeed listOfFeed, FragmentOpen fragmentOpen) {
        this.mContext = context;
        this.mListOfFeed = listOfFeed;
        this.mFragmentOpen = fragmentOpen;
        mTotalCoverImages = mListOfFeed.getTotalCoverImages();
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
        tvTotalImage.setText(String.valueOf(position+1)+AppConstants.BACK_SLASH+String.valueOf(mTotalCoverImages.size()));
        Glide.with(mContext)
                .load(mTotalCoverImages.get(position).getFeedDetailImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFullImage);
        ((ViewPager) container).addView(viewLayout);
        ivFullImageBack.setOnClickListener(this);
        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_full_image_back:
                mHomeActivityIntraction.onDialogDissmiss(mFragmentOpen);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

    public interface HomeActivityIntraction {
        void onDialogDissmiss(FragmentOpen isFragmentOpen);
    }
}
