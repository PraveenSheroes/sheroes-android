package appliedlife.pvtltd.SHEROES.animation;

/**
 * Created by ujjwal on 19/12/17.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;

import appliedlife.pvtltd.SHEROES.R;

public class SnowFlakeView extends View {

    private boolean mAnimationDisabled = false;
    private SnowFlake[] mSnowFlakes;

    public SnowFlakeView(Context context) {
        super(context);
        init();
    }

    public SnowFlakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SnowFlakeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SnowFlakeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        int numFlakes = 100;
        mSnowFlakes = new SnowFlake[numFlakes];
        for (int i = 0; i < mSnowFlakes.length; i++) {
            mSnowFlakes[i] = new SnowFlake(getResources(), getHeight(), getWidth());
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        for (SnowFlake flake : mSnowFlakes) {
            flake.setScreenDimensions(getHeight(), getWidth());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mAnimationDisabled) {
            return;
        }

        for (SnowFlake flake : mSnowFlakes) {
            flake.onDraw(canvas, getHeight(), getWidth());
        }

        invalidate();
    }

    @VisibleForTesting
    public void disableAnimation() {
        mAnimationDisabled = true;
    }
}
