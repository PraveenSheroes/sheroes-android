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

/**
 * Created by Ravi on 01-05-2018.
 * This class render the dashed progress bar
 */
public class DashProgressBar extends View {

    private Paint progressPaint;
    private int progress;
    private int barThickness;
    private Path path;
    private PathEffect effects;


    public DashProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        progressPaint = new Paint();

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.DashProgressBar, 0, 0);
        try {
            setBarThickness(typedArray.getDimensionPixelOffset(R.styleable.DashProgressBar_barThickness, 4));

            path = new Path();
            path.reset();

            //Dashed line effects - 120 solid line 10 space
            effects = new DashPathEffect(new float[]{120, 10, 120, 10}, 0);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int halfHeight = getHeight() / 2;
        int progressEndX = (int) (getWidth() * progress / 100f);

        // draw the filled portion of the bar
        progressPaint.reset();
        progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        progressPaint.setStrokeWidth(barThickness);
        int color = R.color.dark_green;
        progressPaint.setPathEffect(effects);

        // horizontal
        progressPaint.setColor(ContextCompat.getColor(getContext(), color));
        path.moveTo(0, halfHeight);
        path.lineTo(progressEndX, halfHeight);
        canvas.drawPath(path, progressPaint);

        path.reset();
        color = R.color.progress_unfilled;
        progressPaint.setColor(ContextCompat.getColor(getContext(), color));
        progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        progressPaint.setPathEffect(effects);

        path.moveTo(progressEndX, halfHeight);
        path.lineTo(getWidth(), halfHeight);
        canvas.drawPath(path, progressPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public void setProgress(final int progress, boolean animate) {
        if (animate) {
            ValueAnimator barAnimator = ValueAnimator.ofFloat(0, 1);

            barAnimator.setDuration(700);

            // reset progress without animating
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
            this.progress = progress;
            postInvalidate();
        }
    }

    public void setBarThickness(int barThickness) {
        this.barThickness = barThickness;
        postInvalidate();
        invalidate();
    }
}
