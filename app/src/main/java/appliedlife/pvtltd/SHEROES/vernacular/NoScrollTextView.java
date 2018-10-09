package appliedlife.pvtltd.SHEROES.vernacular;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Praveen on 07/11/17.
 */

public class NoScrollTextView extends FontTextView {

    public NoScrollTextView(Context context) {
        super(context);
    }

    public NoScrollTextView(Context context, AttributeSet set) {
        super(context, set);
    }

    public NoScrollTextView(Context context, AttributeSet set, int defaultStyle) {
        super(context, set, defaultStyle);
    }

    @Override
    public void scrollTo(int x, int y) {
        //do nothing
    }
}
