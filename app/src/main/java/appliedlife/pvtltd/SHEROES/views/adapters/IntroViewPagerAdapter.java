package appliedlife.pvtltd.SHEROES.views.adapters;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;

import appliedlife.pvtltd.SHEROES.views.fragments.WelcomeScreen1Fragment;
import appliedlife.pvtltd.SHEROES.views.fragments.WelcomeScreen2Fragment;
import appliedlife.pvtltd.SHEROES.views.fragments.WelcomeScreen3Fragment;

/**
 * Created by priyanka on 08/03/17.
 */

public class IntroViewPagerAdapter extends FragmentPagerAdapter {


    public static int TOTAL_PAGE = 3;

    public IntroViewPagerAdapter(FragmentActivity activity, android.support.v4.app.FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        android.support.v4.app.Fragment fragment = new android.support.v4.app.Fragment();

        switch (position) {
            case 0:
                fragment = new WelcomeScreen1Fragment();
                break;
            case 1:
                fragment = new WelcomeScreen2Fragment();
                break;
            case 2:
                fragment = new WelcomeScreen3Fragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {

        return TOTAL_PAGE;
    }

}

