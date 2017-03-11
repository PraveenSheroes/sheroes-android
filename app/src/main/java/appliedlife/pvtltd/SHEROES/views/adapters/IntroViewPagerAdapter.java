package appliedlife.pvtltd.SHEROES.views.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;

import appliedlife.pvtltd.SHEROES.views.fragments.IntroOneFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ImageThreeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ImageTwoFragment;

/**
 * Created by sheroes on 08/03/17.
 */

public class IntroViewPagerAdapter extends FragmentPagerAdapter {
    private Context _context;

    public static int totalPage = 3;



    public IntroViewPagerAdapter(FragmentActivity activity, android.support.v4.app.FragmentManager fragmentManager) {
        super(fragmentManager);


    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        android.support.v4.app.Fragment f = new android.support.v4.app.Fragment();
        switch (position) {
            case 0:
                f = new IntroOneFragment();
                break;
            case 1:
                f = new ImageTwoFragment();
                break;
            case 2:
                f = new ImageThreeFragment();
                break;
        }
        return f;
    }

    @Override
    public int getCount() {
        return totalPage;
    }

}

