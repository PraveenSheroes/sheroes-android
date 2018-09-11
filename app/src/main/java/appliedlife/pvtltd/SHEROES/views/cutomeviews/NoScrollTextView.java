package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Praveen on 07/11/17.
 */

public class NoScrollTextView extends AppCompatTextView {
    public NoScrollTextView(Context context) {
        super(context);
        setFont();
    }

    public NoScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public NoScrollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    @Override
    public void scrollTo(int x, int y) {
        //do nothing
    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Hind-Regular.ttf");
        setTypeface(typeface); //function used to set font
    }
}
