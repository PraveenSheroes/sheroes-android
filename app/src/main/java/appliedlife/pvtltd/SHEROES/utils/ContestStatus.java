package appliedlife.pvtltd.SHEROES.utils;


import androidx.annotation.StringRes;

import appliedlife.pvtltd.SHEROES.R;

/**
 * Created by Ujjwal on 02/05/17.
 */

public enum ContestStatus {
    UPCOMING(R.string.contest_status_upcoming),
    ONGOING(R.string.contest_status_ongoing),
    COMPLETED(R.string.contest_status_completed);

    @StringRes
    int statusName;

    ContestStatus(@StringRes int statusName) {
        this.statusName = statusName;
    }

    @StringRes
    public int getStatusName() {
        return statusName;
    }
}