package appliedlife.pvtltd.SHEROES.analytics.Impression;

import java.util.List;

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

}
