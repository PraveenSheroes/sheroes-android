package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.vernacular.LanguageType;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;
import appliedlife.pvtltd.SHEROES.views.activities.WelcomeActivity;

public class SelectLanguageDialog extends BaseDialogFragment implements View.OnClickListener {
    private static final String SCREEN_LABEL = "Select Language Screen";
    //region Inject variables
    //endregion

    //region View variables
    private TextView tvContinue;
    private ImageView ivLangHind;
    private ImageView ivLangEng;
    //endregion

    //region member variables

    private boolean isLanguageSelected;
    //endregion

    //region overridden variables
    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_language_dialog, container, false);
        setCancelable(true);
        tvContinue = view.findViewById(R.id.tv_continue);
        ivLangHind = view.findViewById(R.id.iv_lang_hind);
        ivLangEng = view.findViewById(R.id.iv_lang_eng);
        FrameLayout flHindi = view.findViewById(R.id.fl_hindi);
        FrameLayout flEnglish = view.findViewById(R.id.fl_english);
        flHindi.setOnClickListener(this);
        flEnglish.setOnClickListener(this);
        tvContinue.setOnClickListener(this);
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
    private void onHindiClick() {
        tvContinue.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        ivLangHind.setVisibility(View.VISIBLE);
        ivLangEng.setVisibility(View.GONE);
        setNewLocale(LanguageType.HINDI.toString());
        isLanguageSelected = true;
    }

    private void onEnglishClick() {
        tvContinue.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        ivLangEng.setVisibility(View.VISIBLE);
        ivLangHind.setVisibility(View.GONE);
        setNewLocale(LanguageType.ENGLISH.toString());
        isLanguageSelected = true;
    }

    private void onContinueClick() {
        if (isLanguageSelected) {
            dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fl_hindi:
                onHindiClick();
                break;
            case R.id.fl_english:
                onEnglishClick();
                break;
            case R.id.tv_continue:
                onContinueClick();
                break;
        }
    }

    //endregion
}
