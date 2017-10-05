package appliedlife.pvtltd.SHEROES.basecomponents;

import java.util.Map;

import appliedlife.pvtltd.SHEROES.analytics.Event;

/**
 * Created by ujjwal on 28/09/17.
 */

public interface EventInterface {
    String getScreenName();

    void trackEvent(Event event, Map<String, Object> properties);
}
