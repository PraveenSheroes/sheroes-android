package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by deepakpoptani on 06/09/17.
 */

public class GenericWebViewFragment extends BaseDialogFragment {
    private static final String SCREEN_LABEL = "Web View Screen";

    @Bind(R.id.generic_web_view)
    WebView webView;


    @Bind(R.id.tv_generic_frag_title)
    TextView tvTitleGeneric;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.generic_web_view_fragment, container, false);
        ButterKnife.bind(this, view);
        if(null!=getArguments()) {
            String mWebUrl = getArguments().getString(AppConstants.WEB_URL);
            String mWebTitle = getArguments().getString(AppConstants.WEB_TITLE);
            tvTitleGeneric.setText(mWebTitle);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(mWebUrl);
        }
        AnalyticsManager.trackScreenView(SCREEN_LABEL);
       return view;
    }


    @OnClick(R.id.iv_generic_frament_back)
    public void backClick()
    {
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth) {
            @Override
            public void onBackPressed() {
                backClick();
            }
        };
    }


}



