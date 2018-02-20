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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsEventType;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.WelcomeActivity;

/**
 * Created by praveen on 08/03/17.
 */

public class SheroesWelcomeViewPagerAdapter extends PagerAdapter {
    private ArrayList<Integer> nameOfScreen;
    private ArrayList<String> screenText;
    private Context context;
    public SheroesWelcomeViewPagerAdapter(ArrayList<Integer> nameOfScreen,Context context,ArrayList<String>screenText)
    {
        this.nameOfScreen=nameOfScreen;
        this.screenText=screenText;
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
        TextView textView = (TextView) itemView.findViewById(R.id.tv_welcome_text);
        int screen=nameOfScreen.get(position);
        String text=screenText.get(position);
        imageView.setImageResource(screen);
        switch (position) {
            case AppConstants.NO_REACTION_CONSTANT:
                SpannableString spannableString = new SpannableString(text);
                if (StringUtil.isNotNullOrEmptyString(text)) {
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.feed_article_label)), 32, 61, 0);
                    spannableString.setSpan(new StyleSpan(Typeface.BOLD), 32, 61, 0);
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                    textView.setText(spannableString, TextView.BufferType.SPANNABLE);
                    textView.setSelected(true);
                }
                break;
            case AppConstants.ONE_CONSTANT:
                ((SheroesApplication) context.getApplicationContext()).trackScreenView(context.getString(R.string.ID_SECOND_WELCOME));
                SpannableString spannableSecond = new SpannableString(text);
                if (StringUtil.isNotNullOrEmptyString(text)) {
                    spannableSecond.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.feed_article_label)), 5, 26, 0);
                    spannableSecond.setSpan(new StyleSpan(Typeface.BOLD), 5, 26, 0);
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                    textView.setText(spannableSecond, TextView.BufferType.SPANNABLE);
                    textView.setSelected(true);
                }
                break;
            case AppConstants.TWO_CONSTANT:
                ((SheroesApplication) context.getApplicationContext()).trackScreenView(context.getString(R.string.ID_THIRD_WELCOME));
                SpannableString spannableThird = new SpannableString(text);
                if (StringUtil.isNotNullOrEmptyString(text)) {
                    spannableThird.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.feed_article_label)), 0, 18, 0);
                    spannableThird.setSpan(new StyleSpan(Typeface.BOLD), 0, 18, 0);
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                    textView.setText(spannableThird, TextView.BufferType.SPANNABLE);
                    textView.setSelected(true);
                }
                break;
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

