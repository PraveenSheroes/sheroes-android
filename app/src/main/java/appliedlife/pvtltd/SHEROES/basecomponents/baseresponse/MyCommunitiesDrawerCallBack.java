package appliedlife.pvtltd.SHEROES.basecomponents.baseresponse;

import android.view.View;

import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.UserContactDetail;

/**
 * Created by Praveen on 28/05/18.
 */

public interface MyCommunitiesDrawerCallBack {
    void onCommunityClicked(FeedDetail feedDetail, View view);
}
