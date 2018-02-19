package appliedlife.pvtltd.SHEROES.basecomponents;

import android.view.View;

import appliedlife.pvtltd.SHEROES.models.entities.contactdetail.UserContactDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;

/**
 * Created by Praveen on 14/02/18.
 */

public interface ContactDetailCallBack {
    void onContactClicked(UserContactDetail contactDetail,View view);
    void onSuggestedContactClicked(UserSolrObj userSolrObj, View view);
}
