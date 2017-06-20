package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.os.Parcel;
import android.text.TextPaint;
import android.text.style.URLSpan;

/**
 * Created by Praveen_Singh on 22-06-2017.
 */

public class URLSpanNoUnderline extends URLSpan {
    public URLSpanNoUnderline(String url) {
        super(url);
    }
    @Override public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    protected URLSpanNoUnderline(Parcel in) {
        super(in);
    }

    public static final Creator<URLSpanNoUnderline> CREATOR = new Creator<URLSpanNoUnderline>() {
        @Override
        public URLSpanNoUnderline createFromParcel(Parcel source) {
            return new URLSpanNoUnderline(source);
        }

        @Override
        public URLSpanNoUnderline[] newArray(int size) {
            return new URLSpanNoUnderline[size];
        }
    };
}