package appliedlife.pvtltd.SHEROES.analytics;

import java.util.Map;

/**
 * Created by ujjwal on 27/09/17.
 */
public enum AnalyticsEventType {

    // region Types
    APP("App"),
    POST("Post"),
    REPLY("Reply"),
    JOB("Job"),
    HELPLINE_MESSAGE("Helpline Message "),
    CHALLENGE("Challenge"),
    COMMUNITY("Community"),


    ANSWER("Answer"),
    ADDRESS("Address"),
    ARTICLE("Article"),
    CHAT("Chat"),
    CHILD("Child"),
    DOCTOR("Doctor"),
    DOCUMENT("Document"),
    NOTIFICATION_LIST("Notification List"),
    PROMO("Promo"),
    PUSH_NOTIFICATION("Push Notification"),
    QUESTION("Question"),
    TIP("Tip"),
    USER("User"),
    VITAL("Vital"),
    LINK("Link"),
    IMAGE("Image"),
    VIDEO("Video"),
    SEARCH("Search"),
    ALBUM("Album"),
    SUBMISSION("Submission"),
    SHARE_CARD("Share Card"),
    CONTEST("Contest");
    // endregion

    public final String name;

    AnalyticsEventType(String name) {
        this.name = name;
    }

    public void addProperties(Map<String, Object> properties){
        // Default - do nothing
    }
}
