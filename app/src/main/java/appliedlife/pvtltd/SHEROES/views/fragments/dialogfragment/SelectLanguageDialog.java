package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;
import appliedlife.pvtltd.SHEROES.views.activities.WelcomeActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectLanguageDialog extends BaseDialogFragment {
    private static final String SCREEN_LABEL = "Select Language Screen";
    //region Inject variables
    //endregion

    //region View variables

    //endregion

    //region member variables
    @Bind(R.id.tv_continue)
    TextView tvContinue;
    @Bind(R.id.iv_lang_hind)
    ImageView ivLangHind;
    @Bind(R.id.iv_lang_eng)
    ImageView ivLangEng;


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
        LocaleManager.setNewLocale(getActivity().getBaseContext(), language);
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
        ivLangHind.setVisibility(View.VISIBLE);
        ivLangEng.setVisibility(View.GONE);
        tvContinue.setBackgroundResource(R.drawable.rectangle_boarding_active);
        setNewLocale(AppConstants.LANGUAGE_HINDI);
    }

    @OnClick(R.id.tv_english)
    public void onEnglishLanguageClick() {
        ivLangEng.setVisibility(View.VISIBLE);
        ivLangHind.setVisibility(View.GONE);
        tvContinue.setBackgroundResource(R.drawable.rectangle_boarding_active);
        setNewLocale(AppConstants.LANGUAGE_ENGLISH);
    }

    @OnClick(R.id.tv_continue)
    public void onContinueClick() {
        if (getActivity() instanceof WelcomeActivity) {
            ((WelcomeActivity) getActivity()).updateLangTextOnUi();
        }
        dismiss();
    }

    //endregion
}
