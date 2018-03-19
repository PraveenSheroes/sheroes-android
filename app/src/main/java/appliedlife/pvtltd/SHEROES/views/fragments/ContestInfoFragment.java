package appliedlife.pvtltd.SHEROES.views.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.parceler.Parcels;


import java.util.HashMap;
import java.util.Map;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.ContestStatus;
import appliedlife.pvtltd.SHEROES.utils.VideoEnabledWebChromeClient;
import appliedlife.pvtltd.SHEROES.utils.WebViewClickListener;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.VideoEnabledWebView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ujjwal on 01/05/17.
 */

public class ContestInfoFragment extends BaseFragment {
    private static final String SCREEN_LABEL = "Challenge Info Fragment";
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

    @Bind(R.id.author_pic)
    ImageView mAuthorPic;

    @Bind(R.id.author)
    TextView mAuthorName;

    @Bind(R.id.response_views_counts)
    TextView mResponseCount;

    @Bind(R.id.contest_status)
    TextView mContestStatus;

    @Bind(R.id.live_dot)
    ImageView mLiveDot;
    //endregion

    private Contest mContest;
    private WebViewClickListener webViewClickListener = null;
    private boolean showFeatureImage = true;
    ValueAnimator mAlphaAnimator;
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

    @Override
    protected SheroesPresenter getPresenter() {
        return null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            AnalyticsManager.trackScreenView(getScreenName(), getExtraProperties());
        }
    }

    @Override
    protected Map<String, Object> getExtraProperties() {
        final EventProperty.Builder builder = new EventProperty.Builder();
        if (mContest != null) {
            builder.title(mContest.title)
                    .id(Integer.toString(mContest.remote_id));

        }
        HashMap<String, Object> properties = builder.build();
        return properties;
    }

    @OnClick({R.id.author_pic, R.id.author})
    public void authorNameClicked() {
        if(mContest.createdBy!= -1) {
            ProfileActivity.navigateTo(getActivity(), mContest.createdBy, mContest.isAuthorMentor, SCREEN_LABEL, null,  AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
        }
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    private void showContest(Contest contest) {
        if (contest != null) {
            mContest = contest;
            if (showFeatureImage) {
                showImage();
            } else {
                imageView.setVisibility(View.GONE);
            }
            showAuthorDetails();
            showDaysParticipantsInfo();
            showContestInfo();
            showContestStatus();
        }
    }

    private void showContestStatus() {
        ContestStatus contestStatus = CommonUtil.getContestStatus(mContest.getStartAt(), mContest.getEndAt());
        if (contestStatus == ContestStatus.ONGOING) {
            mContestStatus.setText(R.string.contest_status_ongoing);
            mLiveDot.setImageResource(R.drawable.vector_live_dot);
            mLiveDot.setVisibility(View.VISIBLE);
            animateLiveDot();
            mContestStatus.setVisibility(View.VISIBLE);
        } else {
            mContestStatus.setVisibility(View.GONE);
            mLiveDot.setVisibility(View.GONE);
        }
    }

    private void showAuthorDetails() {
        if (mContest == null) {
            return;
        }
        if (mContest != null && CommonUtil.isNotEmpty(mContest.authorImageUrl)) {
            Glide.with(this)
                    .load(mContest.authorImageUrl)
                    .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(getActivity())))
                    .into(mAuthorPic);
        }
        mAuthorName.setText(mContest.authorName);
        mResponseCount.setText(Integer.toString(mContest.submissionCount) + " " + getActivity().getResources().getQuantityString(R.plurals.numberOfResponses, mContest.submissionCount));
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

    //region private methods
    private void showDaysParticipantsInfo() {
        ContestStatus contestStatus = CommonUtil.getContestStatus(mContest.getStartAt(), mContest.getEndAt());

    }

    @SuppressLint("AddJavascriptInterface")
    private void showContestInfo() {
        mTitle.setText(mContest.title);
        if (CommonUtil.isNotEmpty(mContest.tag)) {
            String tag = "#" + mContest.tag;
            String tagText = tag + " " + "Challenge";
            final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(tagText);
            final ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.email));
            spannableStringBuilder.setSpan(foregroundColorSpan, 0, tag.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mContestTag.setText(spannableStringBuilder);
        } else {
            mContestTag.setText("Challenge");
        }
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
        if (getActivity() != null && !getActivity().isFinishing()) {
            int featureImageHeight = (CommonUtil.getWindowWidth(getActivity()) / 2);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, featureImageHeight);
            imageView.setLayoutParams(params);
            if (CommonUtil.isNotEmpty(mContest.thumbImage)) {
                String imageKitUrl = CommonUtil.getImgKitUri(mContest.thumbImage, CommonUtil.getWindowWidth(getActivity()), featureImageHeight);
                if (CommonUtil.isNotEmpty(imageKitUrl)) {
                    Glide.with(getActivity())
                            .load(imageKitUrl)
                            .into(imageView);
                }
            }
        } else {
            imageView.setImageResource(R.drawable.challenge_placeholder);
        }
    }

    public void setContest(Contest contest){
        mContest = contest;
        showContest(contest);
    }

    private void animateLiveDot(){
        mAlphaAnimator = ObjectAnimator.ofFloat(mLiveDot, "alpha", 0f, 1f);
        mAlphaAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mAlphaAnimator.setRepeatCount(Animation.INFINITE);
        mAlphaAnimator.setDuration(300);

        Interpolator mSelectedInterpolator = new FastOutSlowInInterpolator();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(mSelectedInterpolator);
        animatorSet.play(mAlphaAnimator);
        animatorSet.start();
    }

    //endregion

    //region static variable
    public static Fragment instance() {
        return new ContestInfoFragment();
    }
    //endregion

}
