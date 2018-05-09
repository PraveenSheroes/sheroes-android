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
 * This class render the dashed progress bar
 */
public class DashProgressBar extends View {

    private Paint progressPaint;
    private float progress = 0.0f;
    private int barThickness;
    private Path path;
    private int maxDash = 0;
    private ProgressbarView mProgressBarListener;

    public DashProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.DashProgressBar, 0, 0);
        try {
            setBarThickness(typedArray.getDimensionPixelOffset(R.styleable.DashProgressBar_barThickness, 4));
            path = new Path();
            path.reset();

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
        float progressEndX = ((float) getWidth()) * (progress / (float)100);
        progressPaint.setStrokeWidth(barThickness);

        // draw the un-filled dashes
        float dashWidth = (float) getWidth() / (float) 8; //todo - add to config file
        float tempDashWidth = dashWidth - 10;
        PathEffect effects = new DashPathEffect(new float[]{tempDashWidth, 10, tempDashWidth, 10}, 0);

        int color = R.color.progress_unfilled;
        progressPaint.setColor(ContextCompat.getColor(getContext(), color));
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setPathEffect(effects);

        path.moveTo(0, halfHeight);
        path.lineTo(getWidth(), halfHeight);
        canvas.drawPath(path, progressPaint);

        // draw the filled portion of the bar
        path.reset();
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(barThickness);
        color = R.color.dark_green;
        progressPaint.setColor(ContextCompat.getColor(getContext(), color));
        progressPaint.setPathEffect(effects);
        path.moveTo(0, halfHeight);
        path.lineTo(progressEndX, halfHeight);

        canvas.drawPath(path, progressPaint);

       if(mProgressBarListener!=null) {
            mProgressBarListener.onViewRendered(tempDashWidth);
            mProgressBarListener = null;
        }

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
    }

    public int getMaxDash() {
        return maxDash;
    }

    public void setMaxDash(int maxDash) {
        this.maxDash = maxDash;
    }
}
