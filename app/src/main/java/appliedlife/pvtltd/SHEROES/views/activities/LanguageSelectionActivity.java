package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.os.Bundle;

import java.util.Locale;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LanguageSelectionActivity extends BaseActivity {
    private static final String SCREEN_LABEL = "Select Language Screen";
    //region Inject variables
    //endregion

    //region View variables

    //endregion

    //region member variables


    //endregion

    //region overridden variables
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.select_language_dialog);
        SheroesApplication.getAppComponent(this).inject(this);
        ButterKnife.bind(this);
        AnalyticsManager.trackScreenView(SCREEN_LABEL);
    }

    private void setNewLocale(String language) {
        LocaleManager.setNewLocale(this, language);
        CommonUtil.setPrefValue(AppConstants.SELECT_LANGUAGE_SHARE_PREF);
    }

    //region onclick methods
  /*  @OnClick(R.id.tv_hindi)
    public void onHindiLanguageClick() {
        setNewLocale(AppConstants.LANGUAGE_HINDI);
        Intent i = new Intent(this, WelcomeActivity.class);
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }*/

   /* @OnClick(R.id.tv_english)
    public void onEnglishLanguageClick() {
        setNewLocale(AppConstants.LANGUAGE_ENGLISH);
        Intent i = new Intent(this, WelcomeActivity.class);
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean shouldTrackScreen() {
        return true;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return null;
    }

}
