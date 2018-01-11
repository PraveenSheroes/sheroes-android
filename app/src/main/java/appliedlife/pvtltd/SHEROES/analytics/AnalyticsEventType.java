package appliedlife.pvtltd.SHEROES.analytics;

import java.util.Map;

/**
 * Created by ujjwal on 27/09/17.
 */
public enum AnalyticsEventType {

    // region Types
    APP("App"),
    POST("Post"),
    PROFILE("Profile"),
    REPLY("Reply"),
    JOB("Job"),
    HELPLINE_MESSAGE("Helpline Message "),
    CHALLENGE("Challenge"),
    COMMUNITY("Community"),
    ORGANIZATION("Organization"),
    ARTICLE("Article"),
    CHAT("Chat"),
    NOTIFICATION_LIST("Notification List"),
    PUSH_NOTIFICATION("Push Notification"),
    USER("User"),
    SEARCH("Search"),
    IMAGE("Image"),
    LOOKING_FOR("Looking For"),
    PROMO_CARD("Promo Card");

    // endregion

    public final String name;

    AnalyticsEventType(String name) {
        this.name = name;
    }

    public void addProperties(Map<String, Object> properties) {
        // Default - do nothing
    }
}
