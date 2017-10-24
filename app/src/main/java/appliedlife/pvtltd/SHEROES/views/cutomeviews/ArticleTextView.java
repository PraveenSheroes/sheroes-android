package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.utils.AppConstants;

/**
 * Created by Praveen_Singh on 17-04-2017.
 */

public class ArticleTextView {
    public static void doResizeTextView(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final String LEFT_HTML_VEIW_TAG_FOR_COLOR = "<font color='#2793e7'>";
                final String RIGHT_HTML_VIEW_TAG_FOR_COLOR = "</font>";
                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    if(lineEndIndex>=expandText.length()) {
                        String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        if (text.length() > 10) {
                            String data = LEFT_HTML_VEIW_TAG_FOR_COLOR + text.substring(text.length() - 10, text.length()) + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                            if (text.length() > 20) {
                                text = text.substring(0, text.length() - 20) + AppConstants.DOTS;
                                tv.setText(Html.fromHtml(text + data));
                            }
                        }
                    }
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {

                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    if(lineEndIndex>=expandText.length()) {
                        String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                        if (text.length() > 10) {
                            String data = LEFT_HTML_VEIW_TAG_FOR_COLOR + text.substring(text.length() - 10, text.length()) + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                            //  tv.setText(text+data);
                            tv.setMovementMethod(LinkMovementMethod.getInstance());
                            if (text.length() > 20) {
                                text = text.substring(0, text.length() - 20) + AppConstants.DOTS;
                                tv.setText(Html.fromHtml(text + data));
                            }
                        }
                    }
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    if (text.length() > 10) {
                        String data = LEFT_HTML_VEIW_TAG_FOR_COLOR + text.substring(text.length() - 10, text.length()) + RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                        // tv.setText(text+data);
                        if (text.length() > 20) {
                            text = text.substring(0, text.length() - 20) + AppConstants.DOTS;

                            tv.setMovementMethod(LinkMovementMethod.getInstance());
                            tv.setText(Html.fromHtml(text + data));
                        }
                    }
                }
            }
        });

    }

}