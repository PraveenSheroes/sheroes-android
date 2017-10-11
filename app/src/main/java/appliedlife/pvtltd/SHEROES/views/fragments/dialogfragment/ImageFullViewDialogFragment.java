package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
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

public class ImageFullViewDialogFragment extends BaseDialogFragment implements ViewPager.OnPageChangeListener {
    private static final String SCREEN_LABEL = "Image Full Screen";
    private final String TAG = LogUtils.makeLogTag(ImageFullViewDialogFragment.class);
    @Bind(R.id.vp_full_image_view)
    ViewPager viewPagerFullImageView;
    @Bind(R.id.tv_total_image)
    TextView tvTotalImage;
    private ImageFullViewAdapter mImageFullViewAdapter;
    private FeedDetail mFeedDetail;
    private FragmentOpen mFragmentOpen;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.fragment_image_full_view, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
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
        AnalyticsManager.trackScreenView(SCREEN_LABEL, new EventProperty.Builder().id(Long.toString(mFeedDetail.getEntityOrParticipantId())).build());
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        // safety check
        if (getDialog() == null) {
            return;
        }
        // set the animations to use on showing and hiding the dialog
        getDialog().getWindow().setWindowAnimations(R.style.dialog_imageview_animation_fade);
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
        dismiss();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth) {
            @Override
            public void onBackPressed() {
                dismissCommentDialog();
            }
        };
    }
}