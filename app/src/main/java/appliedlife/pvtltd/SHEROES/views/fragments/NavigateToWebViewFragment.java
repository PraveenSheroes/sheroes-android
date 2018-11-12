package appliedlife.pvtltd.SHEROES.views.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.f2prateek.rx.preferences2.Preference;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostRatingResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;
import appliedlife.pvtltd.SHEROES.views.activities.BranchDeepLink;
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
    public static final String HAS_FOOTER = "Has Footer";
    public static final String FORCE_CUSTOM_TAB = "Force Custom Tab";

    @Bind(R.id.main_container)
    RelativeLayout mMainContainer;

    @Bind(R.id.external_url_web_view)
    WebView webPagesView;

    @Bind(R.id.pb_login_progress_bar)
    ProgressBar pbWebView;

    @Inject
    Preference<LoginResponse> mUserPreference;
    private String currentSelectedItemName;
    private boolean mForceCustomTab;

    public static NavigateToWebViewFragment newInstance(String webUrl, String webHtml, String menuName, boolean hasFooter) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.WEB_URL_FRAGMENT, webUrl);
        bundle.putString(WEB_HTML, webHtml);
        bundle.putString(AppConstants.SELECTED_MENU_NAME, menuName);
        bundle.putBoolean(HAS_FOOTER, hasFooter);
        NavigateToWebViewFragment fragment = new NavigateToWebViewFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    public static NavigateToWebViewFragment newInstance(String webUrl, String webHtml, String menuName, boolean hasFooter, boolean forceCustomTab) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.WEB_URL_FRAGMENT, webUrl);
        bundle.putString(WEB_HTML, webHtml);
        bundle.putString(AppConstants.SELECTED_MENU_NAME, menuName);
        bundle.putBoolean(HAS_FOOTER, hasFooter);
        bundle.putBoolean(FORCE_CUSTOM_TAB, forceCustomTab);
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
        LocaleManager.setLocale(getContext());
        pbWebView.setVisibility(View.VISIBLE);
        webPagesView.getSettings().setJavaScriptEnabled(true);
        webPagesView.setVerticalScrollBarEnabled(false);
        webPagesView.setFocusable(true);
        webPagesView.requestFocusFromTouch();
        webPagesView.setHapticFeedbackEnabled(false);


        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        WebSettings webSettings = webPagesView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);


        if (null != getArguments()) {
            String mWebUrl = getArguments().getString(AppConstants.WEB_URL_FRAGMENT);
            String mWebHtml = getArguments().getString(WEB_HTML);
            boolean hasFooter = getArguments().getBoolean(HAS_FOOTER, false);
            mForceCustomTab = getArguments().getBoolean(FORCE_CUSTOM_TAB, false);
            if (!hasFooter) {
                mMainContainer.setPadding(0, 0, 0, 0);
            } else {
                mMainContainer.setPadding(0, 0, 0, CommonUtil.navHeight(getActivity()));
            }
            currentSelectedItemName = getArguments().getString(AppConstants.SELECTED_MENU_NAME);
            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get()) {
                String token = mUserPreference.get().getToken();
                if (StringUtil.isNotNullOrEmptyString(token) && mWebUrl != null) {
                    webPagesView.loadUrl(mWebUrl, getCustomHeaders(mWebUrl));
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
                if (mForceCustomTab) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }

                if (getContext()!=null && StringUtil.isNotNullOrEmptyString(url)) {
                    if (url.startsWith("whatsapp://")) { //Share on WhatsApp
                        Uri uri = Uri.parse(url);
                        Set<String> args = uri.getQueryParameterNames();
                        if(args!=null && uri.getQueryParameter("text")!=null) {
                            CommonUtil.shareLinkToWhatsApp(getContext(), uri.getQueryParameter("text"));
                        }
                    } else if (url.startsWith("http://www.facebook.com")) { //Share on Facebook
                        CommonUtil.shareLinkToFaceBook(getContext(), url);
                    } else if (url.startsWith("http://twitter.com")) { //share on twitter
                        Uri uri = Uri.parse(url);
                        Set<String> args = uri.getQueryParameterNames();
                        if(args!=null && uri.getQueryParameter("status")!=null) {
                            CommonUtil.shareLinkToTwitter(getContext(), uri.getQueryParameter("status"));
                        }
                    } else if (CommonUtil.isBranchLink(Uri.parse(url))) {
                        if (StringUtil.isNotNullOrEmptyString(url)) {
                            Uri uri = Uri.parse(url);
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), BranchDeepLink.class);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    } else {
                        webPagesView.loadUrl(url, getCustomHeaders(url));
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });

        webPagesView.setOnKeyListener(new View.OnKeyListener() {
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
    protected SheroesPresenter getPresenter() {
        return null;
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

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    private Map<String, String> getCustomHeaders(String url) {
        String token = "", userId = "";
        if (null != mUserPreference && mUserPreference.isSet()) {
            token = mUserPreference.get().getToken();
            userId = String.valueOf(mUserPreference.get().getUserSummary().getUserId());
        }
        if (CommonUtil.isNotEmpty(url) && (CommonUtil.isSheroesValidLink(Uri.parse(url)) || url.contains("52.71.218.71"))) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", token);
            headers.put("userId", userId);
            return headers;
        } else {
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        webPagesView.destroy();


    }

    @Override
    public void getPostRatingSuccess(HelplinePostRatingResponse helplinePostRatingResponse) {

    }
}
