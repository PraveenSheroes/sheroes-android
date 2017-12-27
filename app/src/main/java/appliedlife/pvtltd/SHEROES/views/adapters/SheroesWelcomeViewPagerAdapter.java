package appliedlife.pvtltd.SHEROES.views.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import appliedlife.pvtltd.SHEROES.R;

/**
 * Created by priyanka on 08/03/17.
 */

public class SheroesWelcomeViewPagerAdapter extends PagerAdapter {
    ArrayList<Integer> nameOfScreen;
    public SheroesWelcomeViewPagerAdapter(ArrayList<Integer> nameOfScreen)
    {
        this.nameOfScreen=nameOfScreen;
    }
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.welcome_screen_first_fragment, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_welcome_screen);
        int screen=nameOfScreen.get(position);
        imageView.setImageResource(screen);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}

