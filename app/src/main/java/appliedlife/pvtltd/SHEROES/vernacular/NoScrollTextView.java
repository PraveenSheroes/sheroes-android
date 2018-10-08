package appliedlife.pvtltd.SHEROES.vernacular;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.vernacular.LanguageType;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;

/**
 * Created by Praveen on 07/11/17.
 */

public class NoScrollTextView extends AppCompatTextView {

    public NoScrollTextView(Context context) {
        super(context);
    }

    public NoScrollTextView(Context context, AttributeSet set) {
        super(context, set);
        setLanguageFont(context, set);
    }

    public NoScrollTextView(Context context, AttributeSet set, int defaultStyle) {
        super(context, set, defaultStyle);
        setLanguageFont(context, set);
    }

    private void setLanguageFont(Context context, AttributeSet attrs) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.NoScrollTextViewFont);
        CharSequence fontFamily = styledAttributes.getString(R.styleable.NoScrollTextViewFont_noScrollTextFontFamily);
        if (fontFamily != null) {
            if (!LocaleManager.getLanguage(context).equalsIgnoreCase(LanguageType.ENGLISH.toString())) {
                Typeface typeface;
                if (fontFamily.toString().equalsIgnoreCase("regular")) {
                    typeface = ResourcesCompat.getFont(context, R.font.noto_sans_regular);
                } else if (fontFamily.toString().equalsIgnoreCase("light")) {
                    typeface = ResourcesCompat.getFont(context, R.font.noto_sans_regular);
                } else if (fontFamily.toString().equalsIgnoreCase("medium")) {
                    typeface = ResourcesCompat.getFont(context, R.font.noto_sans_bold);
                } else if (fontFamily.toString().equalsIgnoreCase("bold")) {
                    typeface = ResourcesCompat.getFont(context, R.font.noto_sans_bold);
                } else {
                    typeface = ResourcesCompat.getFont(context, R.font.noto_sans_regular);
                }
                setTypeface(typeface);
            }
        }
        styledAttributes.recycle();
    }

    @Override
    public void scrollTo(int x, int y) {
        //do nothing
    }
}
