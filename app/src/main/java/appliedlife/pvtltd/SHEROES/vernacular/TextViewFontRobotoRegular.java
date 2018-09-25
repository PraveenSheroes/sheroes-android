package appliedlife.pvtltd.SHEROES.vernacular;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.util.Locale;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;

public class TextViewFontRobotoRegular extends AppCompatTextView {
    public TextViewFontRobotoRegular(Context context) {
        super(context);
        setLanguageFont(context);
    }

    public TextViewFontRobotoRegular(Context context, AttributeSet set) {
        super(context, set);
        setLanguageFont(context);
    }

    public TextViewFontRobotoRegular(Context context, AttributeSet set, int defaultStyle) {
        super(context, set, defaultStyle);
        setLanguageFont(context);
    }

    private void setLanguageFont(Context context)
    {
        if (Locale.getDefault().getLanguage().equalsIgnoreCase(AppConstants.LANGUAGE_ENGLISH)) {
            Typeface typeface = Typeface.create("sans-serif-regular", Typeface.NORMAL);
            setTypeface(typeface);
        }
    }
}
