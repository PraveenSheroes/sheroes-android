package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;

/**
 * Created by Praveen_Singh on 15-04-2017.
 */

public class ResizableCustomView {
    private final String TAG = LogUtils.makeLogTag(ResizableCustomView.class);
    public static void doResizeTextView(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

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
                    text=text.substring(0,text.length()-10)+ AppConstants.DOTS;
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(text+data), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {

                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    String data=LEFT_HTML_VEIW_TAG_FOR_COLOR+text.substring(text.length()-10,text.length())+RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                    LogUtils.info("Hi","**************maxline >0*****"+text+data);
                    //  tv.setText(text+data);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    text=text.substring(0,text.length()-10)+ AppConstants.DOTS;
                    tv.setText(addClickablePartTextViewResizable(Html.fromHtml(text+data), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    String data=LEFT_HTML_VEIW_TAG_FOR_COLOR+text.substring(text.length()-10,text.length())+RIGHT_HTML_VIEW_TAG_FOR_COLOR;
                    LogUtils.info("Hi","**************else*****"+text+data);
                   // tv.setText(text+data);
                    text=text.substring(0,text.length()-10);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(addClickablePartTextViewResizable(Html.fromHtml(text+data), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);
        if (str.contains(spanableText)) {
            ssb.setSpan(new MySpannable(false){

                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        doResizeTextView(tv, -1, AppConstants.VIEW_LESS, false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        doResizeTextView(tv, 2, AppConstants.VIEW_MORE, true);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }
}
