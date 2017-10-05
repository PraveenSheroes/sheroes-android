package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.ImageFullViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 07-02-2017.
 */

public class ImageFullViewFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private static final String SCREEN_LABEL = "Image Full Screen";
    private final String TAG = LogUtils.makeLogTag(ImageFullViewFragment.class);
    @Bind(R.id.vp_full_image_view)
    ViewPager viewPagerFullImageView;
    @Bind(R.id.tv_total_image)
    TextView tvTotalImage;
    private ImageFullViewAdapter mImageFullViewAdapter;
    private FeedDetail mFeedDetail;
    private FragmentOpen mFragmentOpen;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_image_full_view, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            mFeedDetail = getArguments().getParcelable(AppConstants.IMAGE_FULL_VIEW);
            mFragmentOpen = getArguments().getParcelable(AppConstants.FRAGMENT_FLAG_CHECK);
        }
        mImageFullViewAdapter = new ImageFullViewAdapter(getActivity(), mFeedDetail, mFragmentOpen);
        viewPagerFullImageView.setAdapter(mImageFullViewAdapter);
        viewPagerFullImageView.setCurrentItem(mFeedDetail.getItemPosition());
        viewPagerFullImageView.addOnPageChangeListener(this);
        setIndex(viewPagerFullImageView.getCurrentItem());
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Nothing to do here
    }

    @Override
    public void onPageSelected(int position) {
        setIndex(position);
    }

    private void setIndex(int position) {
        tvTotalImage.setText(String.valueOf(position + AppConstants.ONE_CONSTANT) + AppConstants.BACK_SLASH + String.valueOf(mFeedDetail.getImageUrls() != null ? mFeedDetail.getImageUrls().size() : 0));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //Nothing to do here
    }
    @OnClick(R.id.tv_full_image_back)
    public void dismissCommentDialog() {
      //  (getActivity()).onBackPressed();
        (getActivity()).getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}