package appliedlife.pvtltd.SHEROES.basecomponents;

import java.util.List;

import appliedlife.pvtltd.SHEROES.datamanger.ImpressionDataSample;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

public interface ImpressionCallback extends BaseMvpView {

    void onNetworkCall();

    FeedDetail getListItemAtPos(int pos);

    String getScreenName();

    void storeInDatabase(List<ImpressionDataSample> impressionData);

}
