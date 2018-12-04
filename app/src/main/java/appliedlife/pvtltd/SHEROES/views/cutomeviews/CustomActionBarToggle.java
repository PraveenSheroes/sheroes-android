package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.app.Activity;
import androidx.annotation.StringRes;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

public class CustomActionBarToggle extends ActionBarDrawerToggle {

    public interface DrawerStateListener {
        void onDrawerOpened();

        void onDrawerClosed();
    }

    private DrawerStateListener stateListener;

    public CustomActionBarToggle(Activity activity,
                                 DrawerLayout drawerLayout,
                                 @StringRes int openDrawerContentDescRes,
                                 @StringRes int closeDrawerContentDescRes, DrawerStateListener stateListener) {
        super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.stateListener = stateListener;
    }

    public CustomActionBarToggle(Activity activity, DrawerLayout drawerLayout,
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
