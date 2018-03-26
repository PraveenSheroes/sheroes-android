package appliedlife.pvtltd.SHEROES.utils;


import android.support.annotation.StringRes;

import appliedlife.pvtltd.SHEROES.R;


/**
 * Created by Ujjwal on 02/05/17.
 */

public enum ArticleStatus {
    SUBMITTED(R.string.article_status_submitted),
    DRAFT(R.string.article_status_draft),
    UNDER_REVIEW(R.string.article_status_under_review),
    SCHEDULED(R.string.article_status_scheduled),
    REJECTED(R.string.article_status_rejected),
    PUBLISHED(R.string.article_status_published);

    @StringRes
    int statusName;

    ArticleStatus(@StringRes int statusName) {
        this.statusName = statusName;
    }

    @StringRes
    public int getStatusName() {
        return statusName;
    }
}