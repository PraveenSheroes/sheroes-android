package appliedlife.pvtltd.SHEROES.basecomponents;

import java.util.List;

import appliedlife.pvtltd.SHEROES.datamanger.ImpressionData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserEventsContainer;

/**
 * Impression Callback
 * @author ravi
 */
public interface ImpressionPresenterCallback {

    /**
     * Store impression in local database
     * @param impressionData impression
     */
    void storeInDatabase(ImpressionData impressionData);

    /**
     * Store the impression in Database
     * @param impressionDataList impressions
     */
    void storeImpressions(List<ImpressionData> impressionDataList);

    /**
     * Send the impression to Server
     * @param userEventsContainer impressions
     */
    void sendImpression(UserEventsContainer userEventsContainer);

}
