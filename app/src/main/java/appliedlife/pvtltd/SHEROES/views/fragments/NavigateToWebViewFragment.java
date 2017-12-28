package appliedlife.pvtltd.SHEROES.views.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
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

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ravi on 21/11/17.
 * This class provide functionality to load the content in Web-View
 */

public class NavigateToWebViewFragment extends BaseFragment {

    private static final String SCREEN_LABEL = "WebUrl Screen";
    private static final String WEB_HTML = "Web Html";
    private static final String RELATIVE_PATH_ASSETS = "file:///android_asset/";
    private static final String TAG = NavigateToWebViewFragment.class.getName();

    @Bind(R.id.external_url_web_view)
    WebView webPagesView;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar pbWebView;
    @Inject
    Preference<LoginResponse> mUserPreference;
    private String currentSelectedItemName;

    public static NavigateToWebViewFragment newInstance(String webUrl, String webHtml, String menuName) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.WEB_URL_FRAGMENT, webUrl);
        bundle.putString(WEB_HTML, webHtml);
        bundle.putString(AppConstants.SELECTED_MENU_NAME, menuName);

        NavigateToWebViewFragment fragment = new NavigateToWebViewFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.fragment_web_url, container, false);
        ButterKnife.bind(this, view);

        if(getActivity() instanceof HomeActivity){
            ((HomeActivity)getActivity()).changeFragmentWithCommunities();
            ((HomeActivity)getActivity()).resetUiSelectedOptions();

        }
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
            String mWebHtml = getArguments().getString(WEB_HTML);
            currentSelectedItemName = getArguments().getString(AppConstants.SELECTED_MENU_NAME);
                if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get()) {
                    String token = mUserPreference.get().getToken();
                    if (StringUtil.isNotNullOrEmptyString(token) && mWebUrl != null) {
                        String webUrl = getResources().getString(R.string.PLACEHOLDER_WEBPAGE_URL, mWebUrl, token); //append token with url
                        LogUtils.info(TAG, "#######WebPage Url*****" + webUrl);
                        webPagesView.loadUrl(webUrl);
                    }

                    if (StringUtil.isNotNullOrEmptyString(token) && mWebHtml != null) {
                        webPagesView.loadDataWithBaseURL(RELATIVE_PATH_ASSETS, mWebHtml, "text/html", "UTF-8", null);
                    }
                }
        }

        webPagesView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pbWebView.bringToFront();
            }

            public void onPageFinished(WebView view, String url) {
                pbWebView.setVisibility(View.GONE);  //Hide progress bar
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) { //Handle hyperlinks here

                if (StringUtil.isNotNullOrEmptyString(url)) {
                    if(url.startsWith("whatsapp://")) { //Share on WhatsApp
                        CommonUtil.shareLinkToWhatsApp(getContext(), url);
                    } else if(url.startsWith("http://www.facebook.com") ) { //Share on Facebook
                        CommonUtil.shareLinkToFaceBook(getContext(), url);
                    } else if(url.startsWith("http://twitter.com")) { //share on twitter
                        CommonUtil.shareLinkToTwitter(getContext(), url);
                    } else {
                        webPagesView.loadUrl(url);
                    }
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

        webPagesView.setOnLongClickListener(new View.OnLongClickListener() { //To avoid default selection on long press
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webPagesView.setLongClickable(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DrawerViewHolder.selectedOptionName = currentSelectedItemName;
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
