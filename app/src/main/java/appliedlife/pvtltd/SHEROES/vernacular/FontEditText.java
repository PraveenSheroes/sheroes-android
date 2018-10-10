package appliedlife.pvtltd.SHEROES.vernacular;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import appliedlife.pvtltd.SHEROES.R;

public class FontEditText extends AppCompatEditText {
    public FontEditText(Context context) {
        super(context);
    }

    public FontEditText(Context context, AttributeSet set) {
        super(context, set);
        //setLanguageFont(context, set);
    }

    public FontEditText(Context context, AttributeSet set, int defaultStyle) {
        super(context, set, defaultStyle);
       // setLanguageFont(context, set);
    }

    private void setLanguageFont(Context context, AttributeSet attrs) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.FontEditText);
        CharSequence fontFamily = styledAttributes.getString(R.styleable.FontEditText_customEditTextFontFamily);
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
}


