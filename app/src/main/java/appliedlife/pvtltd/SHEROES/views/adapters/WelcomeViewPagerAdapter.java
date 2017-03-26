package appliedlife.pvtltd.SHEROES.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import appliedlife.pvtltd.SHEROES.views.fragments.WelcomeScreenFirstFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.WelcomeScreenSecondFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.WelcomeScreenThirdFragment;

/**
 * Created by priyanka on 08/03/17.
 */

public class WelcomeViewPagerAdapter extends FragmentPagerAdapter {


    public static int TOTAL_PAGE = 3;

    public WelcomeViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    @Override
    public Fragment getItem(int position) {

       Fragment fragment = new android.support.v4.app.Fragment();

        switch (position) {
            case 0:
                fragment = new WelcomeScreenFirstFragment();
                break;
            case 1:
                fragment = new WelcomeScreenSecondFragment();
                break;
            case 2:
                fragment = new WelcomeScreenThirdFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {

        return TOTAL_PAGE;
    }

}

