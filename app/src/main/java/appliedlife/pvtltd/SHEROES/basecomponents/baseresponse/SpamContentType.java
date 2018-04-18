package appliedlife.pvtltd.SHEROES.basecomponents.baseresponse;

import android.support.annotation.StringRes;

import appliedlife.pvtltd.SHEROES.R;

/**
 * Created by ravi on 12/04/18.
 */

public enum SpamContentType {
    POST(R.string.spam_status_post), COMMENT(R.string.spam_status_comment), USER(R.string.spam_status_user);

    @StringRes
    int statusName;

    SpamContentType(@StringRes int statusName) {
        this.statusName = statusName;
    }

    @StringRes
    public int getStatusName() {
        return statusName;
    }
}
