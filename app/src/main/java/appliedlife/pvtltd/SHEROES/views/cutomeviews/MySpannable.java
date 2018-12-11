package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import androidx.core.content.ContextCompat;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;

/**
 * Created by Praveen_Singh on 15-04-2017.
 */

public class MySpannable  extends ClickableSpan {

    private boolean isUnderline = true;
    /**
     * Constructor
     */
    public MySpannable(boolean isUnderline) {
        this.isUnderline = isUnderline;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(isUnderline);
        ds.setColor(ContextCompat.getColor(SheroesApplication.mContext, R.color.view_more));


    }

    @Override
    public void onClick(View widget) {

    }
}
