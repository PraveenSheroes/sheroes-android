package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Praveen_Singh on 04-08-2017.
 */
@SuppressLint("AppCompatCustomView")
public class SquareImageGivenSize extends ImageView {
    public SquareImageGivenSize(Context context) {
        super(context);
    }

    public SquareImageGivenSize(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageGivenSize(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        setMeasuredDimension(width, 700);
    }
}
