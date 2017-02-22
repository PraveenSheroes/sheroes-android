package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * Created by Praveen_Singh on 07-02-2017.
 */

public class ImageFullViewFragment extends BaseFragment {
    private final String TAG = LogUtils.makeLogTag(ImageFullViewFragment.class);
    @Bind(R.id.vp_full_image_view)
    ViewPager viewPagerFullImageView;
    private ImageFullViewAdapter mImageFullViewAdapter;
    private FeedDetail mFeedDetail;
    private FragmentOpen mFragmentOpen;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_image_full_view, container, false);
        ButterKnife.bind(this, view);
        if(null!=getArguments()) {
            mFeedDetail = getArguments().getParcelable(AppConstants.IMAGE_FULL_VIEW);
            mFragmentOpen = getArguments().getParcelable(AppConstants.FRAGMENT_FLAG_CHECK);
        }
        mImageFullViewAdapter = new ImageFullViewAdapter(getActivity(), mFeedDetail,mFragmentOpen);
        viewPagerFullImageView.setAdapter(mImageFullViewAdapter);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}