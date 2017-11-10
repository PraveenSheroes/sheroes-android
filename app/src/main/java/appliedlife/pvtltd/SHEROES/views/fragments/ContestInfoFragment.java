package appliedlife.pvtltd.SHEROES.views.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.parceler.Parcels;


import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.ContestStatus;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.VideoEnabledWebChromeClient;
import appliedlife.pvtltd.SHEROES.utils.WebViewClickListener;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.VideoEnabledWebView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ujjwal on 01/05/17.
 */

public class ContestInfoFragment extends BaseFragment {
    private static final String SCREEN_LABEL = "Contest Info Fragment";
    private static final String RELATIVE_PATH_ASSETS = "file:///android_asset/";

    //region view variable
    @Bind(R.id.root)
    RelativeLayout rootLayout;

    @Bind(R.id.contest_tag)
    TextView mContestTag;

    @Bind(R.id.title)
    TextView mTitle;

    @Bind(R.id.image)
    ImageView imageView;

    @Bind(R.id.text)
    VideoEnabledWebView webViewText;

    @Bind(R.id.video_layout)
    RelativeLayout videoLayout;

    /*@Bind(R.id.days_count)
    TextView mDaysCount;*/

    /*@Bind(R.id.days_text)
    TextView mDaysText;*/

    /*@Bind(R.id.participants_count)
    TextView mParticipantsCount;

    @Bind(R.id.participants_text)
    TextView mParticipantsText;*/
    //endregion

    private Contest mContest;
    private WebViewClickListener webViewClickListener = null;
    private boolean showFeatureImage = true;
    //endregion

    //region fragment lifecycle
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contest_info, container, false);
        ButterKnife.bind(this, view);
        Parcelable parcelable = null;
        if (getArguments() != null) {
            showFeatureImage = getArguments().getBoolean("SHOW_FEATURE_IMAGE", true);
            if (getArguments().getParcelable(Contest.CONTEST_OBJ) != null) {
                parcelable = getArguments().getParcelable(Contest.CONTEST_OBJ);
            }
        } else {
            if (getActivity().getIntent() != null) {
                parcelable = getActivity().getIntent().getParcelableExtra(Contest.CONTEST_OBJ);
            }
        }
        if (parcelable != null) {
            mContest = Parcels.unwrap(parcelable);
            showContest(mContest);
        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
      //  AddoDocBus.getInstance().unregister(this);
    }

    private void showContest(Contest contest) {
        if (contest != null) {
            mContest = contest;
            if (showFeatureImage) {
                showImage();
            } else {
                imageView.setVisibility(View.GONE);
            }

            showDaysParticipantsInfo();
            showContestInfo();
        }
    }

    @Override
    public void onDestroy() {
        if (webViewText != null) {
            webViewText.destroy();
        }
        super.onDestroy();
    }


    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    /*@Override
    protected IPresenter getPresenter() {
        return null;
    }*/
    //endregion

    //region private methods
    private void showDaysParticipantsInfo() {
        ContestStatus contestStatus = CommonUtil.getContestStatus(mContest.startAt, mContest.endAt);
       /* mDaysText.setText(mContest.getDaysText());
        mDaysCount.setText(mContest.getDaysCount());
        mParticipantsText.setText(mContest.getParticipantsText(getContext()));
        mParticipantsCount.setText(mContest.getParticipantsCount());
        if (contestStatus == ContestStatus.ONGOING) {
            mDaysCount.setText(DateUtil.contestDate(mContest.endAt));
            mDaysText.setText(R.string.endsOn);
            mParticipantsCount.setText(String.format(Locale.getDefault(), "%d", mContest.submissionCount));
            mParticipantsText.setText(this.getResources().getQuantityString(R.plurals.numberOfResponses, mContest.submissionCount));
        } else if (contestStatus == ContestStatus.UPCOMING) {
            mDaysCount.setText(DateUtil.contestDate(mContest.startAt));
            mDaysText.setText(R.string.starts_at);
            mParticipantsCount.setText(String.format(Locale.getDefault(), "%d", mContest.likesCount));
            mParticipantsText.setText(R.string.joined_by);
        } else if (contestStatus == ContestStatus.COMPLETED) {
            mDaysCount.setText(DateUtil.contestDate(mContest.endAt));
            mDaysText.setText(R.string.completed);
            mParticipantsCount.setText(String.format(Locale.getDefault(), "%d", mContest.submissionCount));
            mParticipantsText.setText(this.getResources().getQuantityString(R.plurals.numberOfResponses, mContest.submissionCount));
        }*/
    }

    @SuppressLint("AddJavascriptInterface")
    private void showContestInfo() {
        mTitle.setText(mContest.title);
        mContestTag.setText(mContest.tag);
        VideoEnabledWebChromeClient webChromeClient = new VideoEnabledWebChromeClient(rootLayout, videoLayout, null, webViewText);
        webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback() {
            @Override
            public void toggledFullscreen(boolean fullscreen) {
                // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
                if (fullscreen) {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    WindowManager.LayoutParams attrs = getActivity().getWindow().getAttributes();
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getActivity().getWindow().setAttributes(attrs);
                    getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                } else {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    WindowManager.LayoutParams attrs = getActivity().getWindow().getAttributes();
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getActivity().getWindow().setAttributes(attrs);
                    getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                }
            }
        });
        final String webViewStyle = getStyleFromConfig();
        String htmlData = mContest.body;
        htmlData = "<style>" + webViewStyle + " </style> <body> " + getJavaScriptFromConfig() + htmlData + " </body>";
        webViewText.getSettings().setJavaScriptEnabled(true);
        webViewText.setWebChromeClient(webChromeClient);
        webViewText.setVerticalScrollBarEnabled(false);
        webViewText.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                webViewText.loadUrl("javascript:initials()");
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null) {
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } else {
                    return false;
                }
            }
        });

        webViewText.setFocusable(false);
        WebSettings webSettings = webViewText.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        if (webViewClickListener == null) {
            webViewClickListener = new WebViewClickListener(getActivity());
            webViewText.addJavascriptInterface(webViewClickListener, "image");
            webViewText.addJavascriptInterface(webViewClickListener, "video");
        }
        webViewText.loadDataWithBaseURL(RELATIVE_PATH_ASSETS, htmlData, "text/html", "UTF-8", null);
    }

    private String getStyleFromConfig() {
        String styleFromConfig;
        styleFromConfig = AppConstants.webstyle;
        return styleFromConfig;
    }


    private String getJavaScriptFromConfig() {
        String javaScriptFromConfig;
        javaScriptFromConfig = AppConstants.javascriptcode;
        return javaScriptFromConfig;
    }

    private void showImage() {
       /* imageView.setVisibility(View.VISIBLE);
        String imageUri = mContest.featureImage;
        if (mContest.featureImage != null) {
            try {
                imageUri = BabygogoThumbor.getInstance()
                        .buildImage(URLEncoder.encode(mContest.featureImage, "UTF-8"))
                        .resize(CommonUtil.getWindowWidth(getActivity()), 0)
                        .filter(ThumborUrlBuilder.format(ThumborUrlBuilder.ImageFormat.WEBP))
                        .toUrl();
            } catch (UnsupportedEncodingException e) {
                Crashlytics.getInstance().core.logException(e);
            }
            final String finalImageUri = imageUri;

            Glide.with(getActivity())
                    .load(finalImageUri)
                    .asBitmap()
                    .into(imageView);
        }*/
    }

    //endregion

    //region static variable
    public static Fragment instance() {
        return new ContestInfoFragment();
    }
    //endregion

}
