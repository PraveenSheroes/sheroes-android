package appliedlife.pvtltd.SHEROES.views.cutomeviews;

/**
 * Created by Praveen on 25/10/17.
 */
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;

public class ViewMoreLayout {
    public static void doResizeTextView(final TextView tv, final int maxLine, final String expandText, final boolean viewMore,final String list) {
        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    int value = lineEndIndex - expandText.length() + 1;

                    String text=tv.getText().toString();
                    if(value<0) {
                        value=0;
                       // text = tv.getText().subSequence(0, value) + " " + expandText;
                    }/*else
                    {
                        if(value>12) {
                            text = tv.getText().subSequence(0, value - 12) + " " + expandText;
                        }
                    }*/
                    text = tv.getText().subSequence(0, value) + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                        viewMore,list), TextView.BufferType.SPANNABLE);

                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                        int value = lineEndIndex - expandText.length() + 1;
                    String text=tv.getText().toString();
                    if(value<0) {
                        value=0;
                       // text = tv.getText().subSequence(0, value) + " " + expandText;
                    }/*else
                    {
                        if(value>12) {
                            text = tv.getText().subSequence(0, value - 12) + " " + expandText;
                        }
                    }*/
                    text = tv.getText().subSequence(0, value) + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                        viewMore,list), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text=tv.getText().toString();
                    if(lineEndIndex<0) {
                        lineEndIndex=0;
                       // text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    }/*else
                    {
                        if(lineEndIndex>12) {
                            text = tv.getText().subSequence(0, lineEndIndex - 12) + " " + expandText;
                        }
                    }*/
                    text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                        viewMore,list), TextView.BufferType.SPANNABLE);
                    }

            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore,final  String list) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
             ssb.setSpan(new MySpannable(false)  {

                @Override
                public void onClick(View widget) {

                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(list, TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        doResizeTextView(tv, -1, SheroesApplication.mContext.getString(R.string.ID_LESS_MENTOR), false,list);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(list, TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        doResizeTextView(tv, 4, SheroesApplication.mContext.getString(R.string.ID_VIEW_MORE_MENTOR), true,list);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }
}
