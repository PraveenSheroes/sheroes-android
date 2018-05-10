package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.ProgressbarView;

/**
 * Created by Ravi on 01-05-2018.
 * This class render the dashed mProgress bar
 */
public class DashProgressBar extends View {

    private static final int SPACE_WIDTH = 10;
    private float mProgress = 0.0f;
    private int mBarThickness;
    private int mTotalDash = 0;
    private Path mPath;
    private Paint mPaint;
    private ProgressbarView mProgressBarListener;

    public DashProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.DashProgressBar, 0, 0);
        try {
            setmBarThickness(typedArray.getDimensionPixelOffset(R.styleable.DashProgressBar_barThickness, 4));
            mPath = new Path();
            mPath.reset();

        } finally {
            typedArray.recycle();
        }
    }

    public void setListener(ProgressbarView progressbarViewRenderingCompleted) {
        this.mProgressBarListener = progressbarViewRenderingCompleted;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int halfHeight = getHeight() / 2;
        float progressEndX = ((float) getWidth()) * (mProgress / (float) 100);
        mPaint.setStrokeWidth(mBarThickness);

        // draw the un-filled dashes
        float dashWidth = (float) getWidth() / (float) getTotalDash();
        float dashWidthWithoutSpace = dashWidth - SPACE_WIDTH;
        PathEffect effects = new DashPathEffect(new float[]{dashWidthWithoutSpace, SPACE_WIDTH, dashWidthWithoutSpace, SPACE_WIDTH}, 0);

        int color = R.color.progress_unfilled;
        mPaint.setColor(ContextCompat.getColor(getContext(), color));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setPathEffect(effects);

        mPath.moveTo(0, halfHeight);
        mPath.lineTo(getWidth(), halfHeight);
        canvas.drawPath(mPath, mPaint);

        // draw the filled portion of the bar
        mPath.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBarThickness);
        color = R.color.dark_green;
        mPaint.setColor(ContextCompat.getColor(getContext(), color));
        mPaint.setPathEffect(effects);
        mPath.moveTo(0, halfHeight);
        mPath.lineTo(progressEndX, halfHeight);

        canvas.drawPath(mPath, mPaint);

        if (mProgressBarListener != null) {
            mProgressBarListener.onViewRendered(dashWidthWithoutSpace);
            mProgressBarListener = null;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public void setProgress(final float progress, boolean animate) {
        if (animate) {
            ValueAnimator barAnimator = ValueAnimator.ofFloat(0, 1);

            barAnimator.setDuration(700);

            // reset mProgress without animating
            setProgress(0, false);

            barAnimator.setInterpolator(new DecelerateInterpolator());

            barAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float interpolation = (float) animation.getAnimatedValue();
                    setProgress((int) (interpolation * progress), false);
                }
            });

            if (!barAnimator.isStarted()) {
                barAnimator.start();
            }
        } else {
            this.mProgress = progress;
            postInvalidate();
        }
    }

    public void setmBarThickness(int mBarThickness) {
        this.mBarThickness = mBarThickness;
        postInvalidate();
    }

    public int getTotalDash() {
        return mTotalDash;
    }

    public void setTotalDash(int mTotalDash) {
        this.mTotalDash = mTotalDash;
    }
}
