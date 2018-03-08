package appliedlife.pvtltd.SHEROES.basecomponents;

import android.view.View;

import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.UserContactDetail;
import appliedlife.pvtltd.SHEROES.usertagging.suggestions.interfaces.Suggestible;

/**
 * Created by Praveen on 05/03/18.
 */

public interface UserTagCallback {
    void onSuggestedUserClicked(Suggestible suggestible, View view);
}
