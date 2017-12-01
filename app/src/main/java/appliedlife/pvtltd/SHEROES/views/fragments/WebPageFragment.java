package appliedlife.pvtltd.SHEROES.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ravi on 21/11/17.
 * This class provide functionality to load the content in Web-View
 */

public class WebPageFragment extends BaseFragment {

    private static final String SCREEN_LABEL = "WebUrl Screen";
    private static final String TAG = WebPageFragment.class.getName();

    @Bind(R.id.external_url_web_view)
    WebView webPagesView;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar pbWebView;
    @Inject
    Preference<LoginResponse> mUserPreference;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.fragment_web_url, container, false);
        ButterKnife.bind(this, view);

        pbWebView.setVisibility(View.VISIBLE);
        webPagesView.getSettings().setJavaScriptEnabled(true);
        webPagesView.setVerticalScrollBarEnabled(false);
        webPagesView.setFocusable(true);
        webPagesView.requestFocusFromTouch();

        WebSettings webSettings = webPagesView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        if (null != getArguments()) {
            String mWebUrl = getArguments().getString(AppConstants.WEB_URL_FRAGMENT);
                if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get()) {
                    String token = mUserPreference.get().getToken();
                    if (StringUtil.isNotNullOrEmptyString(token) && mWebUrl != null) {
                        String localUrl = mWebUrl+ "?auth=" + token + "&addHeader=false&addFooter=false";
                        LogUtils.info(TAG, "#######WebPage Url*****" + localUrl);
                        webPagesView.loadUrl(localUrl);
                    }
                }
        }

        webPagesView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                pbWebView.setVisibility(View.GONE);  //Hide progress bar
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) { //Handle hyperlinks here
                if (url != null) {
                    LogUtils.info(TAG, "link url::" + url);
                    webPagesView.loadUrl(url);
                    return true;
                } else {
                    return false;
                }
            }
        });

        webPagesView.setOnKeyListener(new View.OnKeyListener(){
            public boolean onKey(View v, int keyCode, KeyEvent event) { //handle back press
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && webPagesView.canGoBack()) {
                    webPagesView.goBack();
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        pbWebView.setVisibility(View.GONE);
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

}
