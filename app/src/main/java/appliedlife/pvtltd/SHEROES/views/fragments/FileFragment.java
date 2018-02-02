package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.TouchImageViewNew;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by avinash on 9/2/15.
 */
public class FileFragment extends Fragment {

    //region Butterknife bindings
    @Bind(R.id.imgDisplay)
    TouchImageViewNew mImgDisplay;

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    @Bind(R.id.footer)
    TextView footerView;
    //endregion

    private boolean isStatusBarVisible;
    public CommonUtil.Callback mCallBack;

    //region Fragment Lifecycle methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String imageUrl = getArguments().getString("imageUrl");
        String footer = getArguments().getString("footer");

        inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View fileFragmentLayout = inflater.inflate(R.layout.layout_pinchable_image, container, false);
        ButterKnife.bind(this, fileFragmentLayout);

/*
        Canvas canvas = new Canvas();
        int height = canvas.getMaximumBitmapHeight() / 8;
        int width = canvas.getMaximumBitmapWidth() / 8;
*/

/*        try {
            imageUrl = SheroesThumbor.getInstance().buildImage(URLEncoder.encode(imageUrl, "UTF-8"))
                    .resize(width, height)
                    .fitIn(ThumborUrlBuilder.FitInStyle.NORMAL)
                    .filter(ThumborUrlBuilder.noUpscale(), ThumborUrlBuilder.format(ThumborUrlBuilder.ImageFormat.WEBP))
                    .toUrl();
        } catch (UnsupportedEncodingException e) {
            Crashlytics.getInstance().core.logException(e);
        }*/

        progressBar.setVisibility(View.VISIBLE);
        Glide.with(getActivity())
                .load(imageUrl)
                .apply(new RequestOptions().override(CommonUtil.getWindowWidth(container.getContext()), CommonUtil.getWindowHeight(container.getContext())))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(mImgDisplay);
        if (CommonUtil.isNotEmpty(footer)) {
            footerView.setVisibility(View.VISIBLE);
            footerView.setText(footer);
        } else {
            footerView.setVisibility(View.GONE);
        }
        isStatusBarVisible = true; //initially status bar is visible
        return fileFragmentLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mImgDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final View decorView = getActivity().getWindow().getDecorView();
                if (isStatusBarVisible) {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
                    toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                    footerView.animate().translationY(footerView.getHeight()).setInterpolator(new AccelerateInterpolator()).start();
                    if (mCallBack != null) {
                        mCallBack.callBack(isStatusBarVisible);
                    }

                } else {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                    toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                    footerView.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                    if (mCallBack != null) {
                        mCallBack.callBack(isStatusBarVisible);
                    }
                }
                isStatusBarVisible = !isStatusBarVisible;
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Do nothing
        } else {
            if (mImgDisplay != null) {
                mImgDisplay.resetZoom();
            }
        }
    }
    //endregion

    //region Public methods
    public static FileFragment newInstance(String imageUrl, String footer) {
        FileFragment fileFragment = new FileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("imageUrl", imageUrl);
        if (CommonUtil.isNotEmpty(footer)) {
            bundle.putString("footer", footer);
        }
        fileFragment.setArguments(bundle);

        return fileFragment;
    }
    //endregion
}
