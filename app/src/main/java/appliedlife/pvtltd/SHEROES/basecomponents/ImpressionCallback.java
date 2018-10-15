package appliedlife.pvtltd.SHEROES.basecomponents;

import java.util.List;

import appliedlife.pvtltd.SHEROES.datamanager.ImpressionData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

public interface ImpressionCallback extends BaseMvpView {

    void onNetworkCall();

    FeedDetail getListItemAtPos(int pos);

    String getScreenName();

    void storeInDatabase(List<ImpressionData> impressionData);

    void showToast(String message);

}
