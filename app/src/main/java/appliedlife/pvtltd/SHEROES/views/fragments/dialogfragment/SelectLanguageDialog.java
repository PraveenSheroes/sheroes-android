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
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.vernacular.LanguageType;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectLanguageDialog extends BaseDialogFragment {
    private static final String SCREEN_LABEL = "Language Setting Screen";

    //region View variables
    @Bind(R.id.tv_continue)
    TextView tvContinue;
    @Bind(R.id.iv_lang_hind)
    ImageView ivLangHind;
    @Bind(R.id.iv_lang_eng)
    ImageView ivLangEng;
    @Bind(R.id.fl_hindi)
    FrameLayout flHindi;
    @Bind(R.id.fl_english)
    FrameLayout flEnglish;
    @Bind(R.id.iv_language_cross)
    ImageView ivLanguageCross;
    //endregion

    //region member variables
    private boolean isLanguageSelected;
    //endregion

    //region overridden variables
    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_language_dialog, container, false);
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        ButterKnife.bind(this, view);
        setCancelable(true);
        ivLanguageCross.setVisibility(View.VISIBLE);
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
                onCrossClick();
            }
        };
    }
    //endregion

    //region onclick methods
    @OnClick(R.id.fl_hindi)
    public void onHindiClick() {
        tvContinue.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        ivLangHind.setVisibility(View.VISIBLE);
        ivLangEng.setVisibility(View.GONE);
        setNewLocale(LanguageType.HINDI.toString());
        isLanguageSelected = true;
    }

    @OnClick(R.id.fl_english)
    public void onEnglishClick() {
        tvContinue.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        ivLangEng.setVisibility(View.VISIBLE);
        ivLangHind.setVisibility(View.GONE);
        setNewLocale(LanguageType.ENGLISH.toString());
        isLanguageSelected = true;
    }

    @OnClick(R.id.tv_continue)
    public void onContinueClick() {
        if (isLanguageSelected) {
            if (getActivity() instanceof HomeActivity) {
                ((HomeActivity) getActivity()).refreshHomeViews();
            }
            dismiss();
        }
    }

    @OnClick(R.id.iv_language_cross)
    public void onCrossClick() {
        dismiss();
    }
    //endregion
}
