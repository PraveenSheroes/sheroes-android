package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Praveen_Singh on 23-03-2017.
 */

@SuppressLint("AppCompatCustomView")
public class BoardingSquareImageView extends ImageView {
    public BoardingSquareImageView(Context context) {
        super(context);
    }

    public BoardingSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BoardingSquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}
