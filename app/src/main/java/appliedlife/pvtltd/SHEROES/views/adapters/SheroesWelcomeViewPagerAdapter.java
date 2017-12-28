package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;

/**
 * Created by praveen on 08/03/17.
 */

public class SheroesWelcomeViewPagerAdapter extends PagerAdapter {
    private ArrayList<Integer> nameOfScreen;
    private Context context;
    public SheroesWelcomeViewPagerAdapter(ArrayList<Integer> nameOfScreen,Context context)
    {
        this.nameOfScreen=nameOfScreen;
        this.context=context;
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

