package appliedlife.pvtltd.SHEROES.views.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import androidx.viewpager.widget.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;

/**
 * Created by praveen on 08/03/17.
 */

public class SheroesWelcomeViewPagerAdapter extends PagerAdapter {
    private ArrayList<Integer> nameOfScreen;
    private Context context;

    public SheroesWelcomeViewPagerAdapter(ArrayList<Integer> nameOfScreen, Context context) {
        this.nameOfScreen = nameOfScreen;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.welcome_screen_first_fragment, container, false);
        ImageView imageView = itemView.findViewById(R.id.iv_welcome_screen);
        TextView textView = itemView.findViewById(R.id.tv_welcome_text);
        ArrayList<String> screenText = new ArrayList<>();
        screenText.add(context.getString(R.string.ID_WELCOME_FIRST));
        screenText.add(context.getString(R.string.ID_WELCOME_SECOND));
        screenText.add(context.getString(R.string.ID_WELCOME_THIRD));
        int screen = nameOfScreen.get(position);
        String text = screenText.get(position);
        imageView.setImageResource(screen);
        if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
            textView.setText(Html.fromHtml(text, 0)); // for 24 api and more
        } else {
            textView.setText(Html.fromHtml(text));// or for older api
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}

