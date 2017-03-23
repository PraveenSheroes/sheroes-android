package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
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

public class ImageFullViewFragment extends BaseFragment implements ImageFullViewAdapter.HomeActivityIntraction  {
    private final String TAG = LogUtils.makeLogTag(ImageFullViewFragment.class);
    @Bind(R.id.vp_full_image_view)
    ViewPager viewPagerFullImageView;
    @Bind(R.id.tv_total_image)
    public  TextView tvTotalImage;
    private ImageFullViewAdapter mImageFullViewAdapter;
    private FeedDetail mFeedDetail;
    private FragmentOpen mFragmentOpen;
    private HomeActivityIntraction mHomeActivityIntraction;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof HomeActivityIntraction) {
                mHomeActivityIntraction = (HomeActivityIntraction) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_image_full_view, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            mFeedDetail = getArguments().getParcelable(AppConstants.IMAGE_FULL_VIEW);
            mFragmentOpen = getArguments().getParcelable(AppConstants.FRAGMENT_FLAG_CHECK);
        }
        mImageFullViewAdapter = new ImageFullViewAdapter(getActivity(), mFeedDetail, mFragmentOpen,this);
        viewPagerFullImageView.setAdapter(mImageFullViewAdapter);

        return view;
    }

    @OnClick(R.id.iv_full_image_back)
    public void dismissCommentDialog() {
        mHomeActivityIntraction.onDialogDissmiss(mFragmentOpen);
    }

    @Override
    public void onSetText(int value) {
        if(value==3)
        {
            tvTotalImage.setVisibility(View.GONE);
        }
        else
        {
            tvTotalImage.setVisibility(View.VISIBLE);
        }
    }

    public interface HomeActivityIntraction {
        void onDialogDissmiss(FragmentOpen isFragmentOpen);
    }

}