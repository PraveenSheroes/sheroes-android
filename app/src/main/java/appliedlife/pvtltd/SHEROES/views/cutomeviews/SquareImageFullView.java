package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Praveen_Singh on 27-06-2017.
 */
@SuppressLint("AppCompatCustomView")
public class SquareImageFullView extends ImageView {
    public SquareImageFullView(Context context) {
        super(context);
    }

    public SquareImageFullView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageFullView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        setMeasuredDimension(width, height);
    }
}