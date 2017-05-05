package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;

/**
 * Created by Praveen_Singh on 17-04-2017.
 */

public class ArticleTextView { public static void doResizeTextView(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

    if (tv.getTag() == null) {
        tv.setTag(tv.getText());
    }
    ViewTreeObserver vto = tv.getViewTreeObserver();
    vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            final String LEFT_HTML_VEIW_TAG_FOR_COLOR = "<font color='#50e3c2'>";
            final String RIGHT_HTML_VIEW_TAG_FOR_COLOR = "</font>";
            ViewTreeObserver obs = tv.getViewTreeObserver();
            obs.removeGlobalOnLayoutListener(this);
            if (maxLine == 0) {
                int lineEndIndex = tv.getLayout().getLineEnd(0);
                String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                tv.setText(text);
                LogUtils.info("Hi","**************maxline*****"+text);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                String data=LEFT_HTML_VEIW_TAG_FOR_COLOR+text.substring(text.length()-10,text.length())+RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                text=text.substring(0,text.length()-20)+ AppConstants.DOTS;
                tv.setText(Html.fromHtml(text+data));
            } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {

                int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                String data=LEFT_HTML_VEIW_TAG_FOR_COLOR+text.substring(text.length()-10,text.length())+RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                LogUtils.info("Hi","**************maxline >0*****"+text+data);
                //  tv.setText(text+data);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                text=text.substring(0,text.length()-20)+ AppConstants.DOTS;
                tv.setText(Html.fromHtml(text+data));
            } else {
                int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                String data=LEFT_HTML_VEIW_TAG_FOR_COLOR+text.substring(text.length()-10,text.length())+RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                LogUtils.info("Hi","**************else*****"+text+data);
                // tv.setText(text+data);
                text=text.substring(0,text.length()-20)+ AppConstants.DOTS;
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setText(Html.fromHtml(text+data));
            }
        }
    });

}

}