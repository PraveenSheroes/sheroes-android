package appliedlife.pvtltd.SHEROES.vernacular;

import android.content.Context;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

public class NoScrollEditText extends FontEditText{
    public NoScrollEditText(Context context) {
        super(context);
    }

    public NoScrollEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void scrollTo(int x, int y) {
        //do nothing
    }
}

