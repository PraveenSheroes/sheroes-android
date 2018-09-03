package appliedlife.pvtltd.SHEROES.analytics;

import java.util.Map;

/**
 * Created by ujjwal on 27/09/17.
 */
public enum AnalyticsEventType {

    // region Types
    APP("App"),
    POST("Post"),
    GENDER_SELECTED("Gender Selected"),
    APP_REVIEW_CLICKED("App Review Clicked"),
    PROFILE("Profile"),
    BADGE("Badge"),
    REPLY("Reply"),
    JOB("Job"),
    HELPLINE_MESSAGE("Helpline Message "),
    CHALLENGE("Challenge"),
    COMMUNITY("Community"),
    ON_BOARDING("Onboarding"),
    WALKTHROUGH("Walkthrough"),
    FACEBOOK_PUBLISH("Facebook Publish"),
    ALLOWED_CONTACT_SYNC("Allowed Contact Sync"),
    DENIED_CONTACT_SYNC("Denied Contact Sync"),
    ORGANIZATION("Organization"),
    ARTICLE("Article"),
    CHAT("Chat"),
    NOTIFICATION_LIST("Notification List"),
    PUSH_NOTIFICATION("Push Notification"),
    USER("User"),
    SEARCH("Search"),
    IMAGE("Image"),
    LOOKING_FOR("Looking For"),
    LINK("Link"),
    PROMO_CARD("Promo Card"),
    POST_LOAD_MORE("Post Load More"),
    FRIEND("Friend"),
    IMAGE_CARD("Image Card"),
    STORY("Story");

    // endregion

    public final String name;

    AnalyticsEventType(String name) {
        this.name = name;
    }

    public void addProperties(Map<String, Object> properties) {
        // Default - do nothing
    }
}
