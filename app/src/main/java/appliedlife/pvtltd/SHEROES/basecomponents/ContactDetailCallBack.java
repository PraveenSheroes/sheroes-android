package appliedlife.pvtltd.SHEROES.basecomponents;

import android.view.View;

import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.UserContactDetail;

/**
 * Created by Praveen on 14/02/18.
 */

public interface ContactDetailCallBack {
    void onContactClicked(UserContactDetail contactDetail, View view);

    void onSuggestedContactClicked(UserSolrObj userSolrObj, View view);

    void showMsgOnSearch(String string);
}
