package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.NavigateToWebViewFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen on 22/06/18.
 */

public class WebViewActivity extends BaseActivity {
    private static final String SCREEN_LABEL = " Web View Activity";
    private static final String SCREEN_URL = "screen_url";
    private static final String MENU_ITEM_NAME = "menu_item";
    //region View variables
    @Bind(R.id.toolbar)
    Toolbar mToolbarView;
    @Bind(R.id.title_toolbar)
    TextView toolbarTitle;
    @Bind(R.id.appbar_layout)
    AppBarLayout mAppBarLayout;

    //region Member variables
    private int mFromNotification;
    private String url, menuItem;
    //endregion

    //region Activity method
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        initViews();
        openWebUrlFragment();
    }
    //endregion

    //region private methods
    private void initViews() {
        if (null != getIntent() && null != getIntent().getExtras()) {
            mFromNotification = getIntent().getExtras().getInt(AppConstants.FROM_PUSH_NOTIFICATION);
            url = getIntent().getExtras().getString(SCREEN_URL);
            menuItem = getIntent().getExtras().getString(MENU_ITEM_NAME);
        }
        setPagerAndLayouts();
        setupToolbar();
        invalidateOptionsMenu();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbarView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
        upArrow.mutate();
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbarTitle.setText(menuItem);
    }

    private void setPagerAndLayouts() {
        ViewCompat.setTransitionName(mAppBarLayout, SCREEN_LABEL);
        supportPostponeEnterTransition();
        setSupportActionBar(mToolbarView);

    }

    public void openWebUrlFragment() { //To open the web-pages in app

        NavigateToWebViewFragment navigateToWebViewFragment = NavigateToWebViewFragment.newInstance(url, null, menuItem, true);
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(NavigateToWebViewFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.beginTransaction().replace(R.id.fl_web_view, navigateToWebViewFragment, NavigateToWebViewFragment.class.getName()).addToBackStack(NavigateToWebViewFragment.class.getName()).commitAllowingStateLoss();
    }
    //endregion

    //region Public methods

    @Override
    public void onBackPressed() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        } else {
            if (mFromNotification > 0) {
                TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(upIntent)
                        .startActivities();
            }
        }
        finish();
    }

    @Override
    public String getScreenName() {
        return menuItem + SCREEN_LABEL;
    }

    @Override
    protected boolean trackScreenTime() {
        return true;
    }

    //endregion

    //region Static methods

    public static void navigateTo(Activity fromActivity, String sourceScreen, HashMap<String, Object> properties, String url, String menuItem) {
        Intent intent = new Intent(fromActivity, WebViewActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        intent.putExtra(WebViewActivity.SCREEN_URL, url);
        intent.putExtra(WebViewActivity.MENU_ITEM_NAME, menuItem);
        intent.putExtras(bundle);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivity(fromActivity, intent, null);
    }

    //endregion
    @Override
    protected SheroesPresenter getPresenter() {
        return null;
    }
}
