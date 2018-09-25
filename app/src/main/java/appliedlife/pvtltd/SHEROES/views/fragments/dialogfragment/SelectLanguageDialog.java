package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;
import appliedlife.pvtltd.SHEROES.views.activities.WelcomeActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectLanguageDialog extends BaseDialogFragment {
    private static final String SCREEN_LABEL = "Select Language Screen";
    //region Inject variables
    //endregion

    //region View variables

    //endregion

    //region member variables


    //endregion

    //region overridden variables
    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_language_dialog, container, false);
        ButterKnife.bind(this, view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        AnalyticsManager.trackScreenView(SCREEN_LABEL);
        return view;
    }

    private void setNewLocale(String language) {
        LocaleManager.setNewLocale(getActivity(), language);
        CommonUtil.setPrefValue(AppConstants.SELECT_LANGUAGE_SHARE_PREF);
        dismiss();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth) {
            @Override
            public void onBackPressed() {
                getActivity().finish();
            }
        };
    }
    //endregion

    //region onclick methods
    @OnClick(R.id.tv_hindi)
    public void onHindiLanguageClick() {
        setNewLocale(AppConstants.LANGUAGE_HINDI);
        Intent i = new Intent(getActivity(), WelcomeActivity.class);
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @OnClick(R.id.tv_english)
    public void onEnglishLanguageClick() {
        setNewLocale(AppConstants.LANGUAGE_ENGLISH);
        Intent i = new Intent(getActivity(), WelcomeActivity.class);
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    //endregion
}
