package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.os.Build;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.basecomponents.TextExpandCollapseCallback;

/**
 * This class provide functionality to expand and collapsed the text view with see more or see less.
 *
 * @author ravi
 */
public class ExpandedCollapseTextView {

    public static final String VIEW_MORE_TEXT = "View More";
    private static final String VIEW_LESS_TEXT = "View Less";
    private static ExpandedCollapseTextView expandedCollapseTextView;

    public static ExpandedCollapseTextView getInstance() {
        if (expandedCollapseTextView == null) {
            expandedCollapseTextView = new ExpandedCollapseTextView();
        }
        return expandedCollapseTextView;
    }

    public void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore, final TextExpandCollapseCallback textExpandCollapseCallback) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }

        tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int totalLine = tv.getLayout().getLineCount();
                if (totalLine > 1) {
                    if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                        int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);

                        String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                        viewMore, textExpandCollapseCallback), TextView.BufferType.SPANNABLE);
                    } else {
                        int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                        String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                        viewMore, textExpandCollapseCallback), TextView.BufferType.SPANNABLE);
                    }
                }

                ViewTreeObserver viewTreeObserver = tv.getViewTreeObserver();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this);
                } else {
                    viewTreeObserver.removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    private SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                     final int maxLine, final String spanableText, final boolean viewMore, final TextExpandCollapseCallback textExpandCollapseCallback) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {

            ssb.setSpan(new MySpannable(false) {
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, VIEW_LESS_TEXT, false, textExpandCollapseCallback);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 1, VIEW_MORE_TEXT, true, textExpandCollapseCallback);
                    }
                    textExpandCollapseCallback.onTextResize(viewMore);
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;
    }
}