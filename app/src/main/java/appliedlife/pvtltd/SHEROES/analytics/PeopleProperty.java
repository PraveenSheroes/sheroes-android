package appliedlife.pvtltd.SHEROES.analytics;

/**
 * Created by ujjwal on 27/09/17.
 */
public enum PeopleProperty {
    // NOTE: Capitalize the first letter
    CURRENT_STATUS("Current Status"),
    SKILLS("Skills"),
    INTEREST("Interest"),
    WORK_EXPERIENCE("Work Experience"),
    FEED_CONFIG("FeedConfigVersion"),
    NAME("Name"),
    PHOTO("Photo"),
    IDENTITY("Identity"),
    GENDER("Gender"),
    EMAIL("Email"),
    PHONE_NO("Phone"),
    LOCATION("Location"),
    LATITUDE("Latitude"),
    LONGITUDE("Longitude"),
    CREATED_DATE("Created"),
    CLEVER_TAP_EMAIL("MSG-email"),
    CLEVER_TAP_PUSH("MSG-push"),
    CLEVER_TAP_SMS("MSG-sms");

    private final String string;

    PeopleProperty(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
