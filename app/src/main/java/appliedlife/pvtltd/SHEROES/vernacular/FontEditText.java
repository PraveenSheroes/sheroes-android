package appliedlife.pvtltd.SHEROES.vernacular;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;

import java.util.Map;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;

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
        if (fontFamily == null) return;

        if (!LocaleManager.getLanguage(context).equalsIgnoreCase(LanguageType.ENGLISH.toString())) {
            Typeface typeface = null;
            Map<String, Integer> fontMapItems = CommonUtil.initFonts();
            for (Map.Entry<String, Integer> fontMap : fontMapItems.entrySet()) {
                if (fontFamily.toString().equalsIgnoreCase(fontMap.getKey())) {
                    typeface = ResourcesCompat.getFont(context, fontMapItems.get(fontMap.getKey()));
                    break;
                }
            }
            if (typeface == null) {
                typeface = ResourcesCompat.getFont(context, R.font.noto_sans_regular);
            }
            setTypeface(typeface);
        }
        styledAttributes.recycle();
    }
}


