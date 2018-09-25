package appliedlife.pvtltd.SHEROES.vernacular;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.util.Locale;

import appliedlife.pvtltd.SHEROES.utils.AppConstants;

public class TextViewFontRobotoMediumBold extends AppCompatTextView {
    public TextViewFontRobotoMediumBold(Context context) {
        super(context);
        setLanguageFont(context);
    }

    public TextViewFontRobotoMediumBold(Context context, AttributeSet set) {
        super(context, set);
        setLanguageFont(context);
    }

    public TextViewFontRobotoMediumBold(Context context, AttributeSet set, int defaultStyle) {
        super(context, set, defaultStyle);
        setLanguageFont(context);
    }

    private void setLanguageFont(Context context)
    {
        if (Locale.getDefault().getLanguage().equalsIgnoreCase(AppConstants.LANGUAGE_ENGLISH)) {
            Typeface typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL);
            setTypeface(typeface);
        }
    }
}
