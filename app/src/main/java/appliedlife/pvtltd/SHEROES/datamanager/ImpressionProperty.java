package appliedlife.pvtltd.SHEROES.datamanager;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * Impression properties enum
 */
@Deprecated
public enum ImpressionProperty {

    USER_ID("userId"),
    IP_ADDRESS("ipAddress"),
    DEVICE_ID("deviceId"),
    APP_VERSION("appVersion"),
    CLIENT_ID("clientId"),
    USER_AGENT("userAgent"),
    GTID("gtid"),
    POST_ID("postId"),
    POSITION("position"),
    EVENT("event"),
    ENGAGEMENT_TIME("engagementTime"),
    TIMESTAMP("timestamp"),
    FEED_CONFIG_VERSION("feedConfigVersion"),
    SET_ORDER_KEY("setOrderKey"),
    CONFIG_TYPE("configType"),
    CONFIG_VERSION("configVersion"),
    SCREEN_NAME("screenName"),
    SOURCE("source"),
    SOURCE_COLLECTION("sourceCollection"),
    SOURCE_URL("sourceURL"),
    POST_TYPE("postType"),
    POSITION_IN_LIST("positionInList");

    private final String string;

    ImpressionProperty(String string) {
        if (TextUtils.isEmpty(string)) {
            throw new IllegalStateException("ImpressionProperty name not initialized!");
        }

        this.string = string;
    }

    public String getString() {
        return string;
    }

    public static class Builder {
        private HashMap<String, Object> properties = new HashMap<>();

        private static <T> boolean validateData(final ImpressionProperty property, final T value) {
            return property != null && value != null;
        }

        private <T> boolean put(final ImpressionProperty property, final T value) {
            if (validateData(property, value)) {
                properties.put(property.getString(), value);
            }
            return value != null;
        }

        public ImpressionProperty.Builder userId(String value) {
            put(USER_ID, value);
            return this;
        }

        public ImpressionProperty.Builder clientId(String value) {
            put(CLIENT_ID, value);
            return this;
        }

        public ImpressionProperty.Builder postId(String value) {
            put(POST_ID, value);
            return this;
        }

        public ImpressionProperty.Builder positionInList(String value) {
            put(POSITION_IN_LIST, value);
            return this;
        }

        public ImpressionProperty.Builder engagementTime(String value) {
            put(ENGAGEMENT_TIME, value);
            return this;
        }

        public ImpressionProperty.Builder timestamp(String value) {
            put(TIMESTAMP, value);
            return this;
        }

        public ImpressionProperty.Builder screenName(String value) {
            put(SCREEN_NAME, value);
            return this;
        }

        public ImpressionProperty.Builder eventType(String value) {
            put(EVENT, value);
            return this;
        }

        public ImpressionProperty.Builder IPAddress(String value) {
            put(IP_ADDRESS, value);
            return this;
        }

        public ImpressionProperty.Builder deviceId(String value) {
            put(DEVICE_ID, value);
            return this;
        }

        public ImpressionProperty.Builder gtid(String value) {
            put(GTID, value);
            return this;
        }

        public ImpressionProperty.Builder userAgent(String value) {
            put(USER_AGENT, value);
            return this;
        }

        public ImpressionProperty.Builder source(String value) {
            put(SOURCE, value);
            return this;
        }

        public ImpressionProperty.Builder sourceCollection(String value) {
            put(SOURCE_COLLECTION, value);
            return this;
        }

        public ImpressionProperty.Builder sourceUrl(String value) {
            put(SOURCE_URL, value);
            return this;
        }

        public ImpressionProperty.Builder postType(String value) {
            put(POST_TYPE, value);
            return this;
        }

        public ImpressionProperty.Builder appVersion(String value) {
            put(APP_VERSION, value);
            return this;
        }

        public ImpressionProperty.Builder feedConfigVersion(String value) {
            put(FEED_CONFIG_VERSION, value);
            return this;
        }
    }

}
