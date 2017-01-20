package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CustiomActionBarToggle extends ActionBarDrawerToggle {

    public interface DrawerStateListener {
        void onDrawerOpened();

        void onDrawerClosed();
    }

    private DrawerStateListener stateListener;

    public CustiomActionBarToggle(Activity activity,
                                  DrawerLayout drawerLayout,
                                  @StringRes int openDrawerContentDescRes,
                                  @StringRes int closeDrawerContentDescRes, DrawerStateListener stateListener) {
        super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.stateListener = stateListener;
    }

    public CustiomActionBarToggle(Activity activity, DrawerLayout drawerLayout,
                                  Toolbar toolbar, @StringRes int openDrawerContentDescRes,
                                  @StringRes int closeDrawerContentDescRes, DrawerStateListener stateListener) {
        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.stateListener = stateListener;
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        if (stateListener != null)
            stateListener.onDrawerOpened();
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        if (stateListener != null)
            stateListener.onDrawerClosed();
    }
}
