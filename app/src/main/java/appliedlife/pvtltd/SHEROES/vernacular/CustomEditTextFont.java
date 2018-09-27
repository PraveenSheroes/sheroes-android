package appliedlife.pvtltd.SHEROES.vernacular;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import java.util.Locale;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;

public class CustomEditTextFont extends AppCompatEditText {
    public CustomEditTextFont(Context context) {
        super(context);
    }

    public CustomEditTextFont(Context context, AttributeSet set) {
        super(context, set);
        setLanguageFont(context, set);
    }

    public CustomEditTextFont(Context context, AttributeSet set, int defaultStyle) {
        super(context, set, defaultStyle);
        setLanguageFont(context, set);
    }

    private void setLanguageFont(Context context, AttributeSet attrs) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.CustomEditTextFont);
        CharSequence fontFamily = styledAttributes.getString(R.styleable.CustomEditTextFont_customEditTextFontFamily);
        if (fontFamily != null) {
            if (!LocaleManager.getLanguage(context).equalsIgnoreCase(AppConstants.LANGUAGE_ENGLISH)) {
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


