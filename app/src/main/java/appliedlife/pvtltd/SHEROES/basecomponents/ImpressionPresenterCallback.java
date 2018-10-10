package appliedlife.pvtltd.SHEROES.basecomponents;

import java.util.List;

import appliedlife.pvtltd.SHEROES.datamanager.Impression;
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
    void storeInDatabase(Impression impressionData);

    /**
     * Store the impression in Database
     * @param impressionDataList impressions
     */
    void storeImpressions(List<Impression> impressionDataList);

    /**
     * Send the impression to Server
     * @param userEventsContainer impressions
     */
    void sendImpression(UserEventsContainer userEventsContainer);

}
