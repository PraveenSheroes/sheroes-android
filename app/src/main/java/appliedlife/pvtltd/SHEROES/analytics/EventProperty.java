package appliedlife.pvtltd.SHEROES.analytics;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ujjwal on 27/09/17.
 */
public enum EventProperty {
    // NOTE:
    // 1. Capitalize the first letter
    // 2. Keep the list alphabetical
    AUTH_PROVIDER("AUTH_PROVIDER"),
    IS_NEW_USER("Is New User"),
    ACTION_TRIGGER("Action Triggered"),
    EVENT_TYPE("EventType"),
    ID("ID"),
    LABEL("Label"),
    LIST("List"),
    LOGIN_SOURCE("Login Source"),
    NAME("Name"),
    NEW_USER("New User"),
    PHONE_NUMBER("Mobile"),
    READING_TIME("Reading Time"),
    READ_PERCENTAGE("Read Percentage"),
    SOURCE("Source"),
    TITLE("Title"),
    TYPE("Type"),
    SHARED_TO("Shared To"),
    URL("Url"),
    ACTION("Action"),
    TOPIC("Topic"),
    CURRENT_VERSION("Current Version"),
    IS_PLAYED("Is Played"),
    POSITION_IN_LIST("Position in List"),
    POST_TYPE("Post Type"),
    POST_ID("Post Id"),
    BODY("Body"),
    COMPANY_ID("Company Id"),
    LOCATION("Location"),
    NOTIFICATION_ID("Notification ID"),
    COMMUNITY_ID("Community Id"),
    COMMUNITY_NAME("Community Name");

    private final String string;

    EventProperty(String string) {
        if(TextUtils.isEmpty(string)){
            throw new IllegalStateException("EventProperty name not initialized!");
        }

        this.string = string;
    }

    public String getString() {
        return string;
    }

    public static class Builder{
        private HashMap<String, Object> properties = new HashMap<>();

        private static<T> boolean validateData(final EventProperty property, final T value) {
            return property != null && value != null;
        }

        private<T> boolean put(final EventProperty property, final T value){
            if(validateData(property, value)){
                properties.put(property.getString(), value);
            }
            return value != null;
        }

        public Builder action(String value){
            put(ACTION, value);
            return this;
        }

        public Builder eventType(String value){
            put(EVENT_TYPE, value);
            return this;
        }

        public Builder id(String value){
            put(ID, value);
            return this;
        }



        public Builder name(String value){
            put(NAME, value);
            return this;
        }

        public Builder title(String value){
            put(TITLE, value);
            return this;
        }

        public Builder postType(String value){
            put(POST_TYPE, value);
            return this;
        }
        public Builder postId(String value){
            put(POST_ID, value);
            return this;
        }

        public Builder body(String value){
            put(BODY, value);
            return this;
        }

        public Builder readingTime(int value){
            put(READING_TIME, value);
            return this;
        }

        public Builder readPercentage(Long value){
            put(READ_PERCENTAGE, value);
            return this;
        }

        public Builder newUser(Boolean value){
            put(NEW_USER, value);
            return this;
        }

        public Builder type(String value){
            put(TYPE, value);
            return this;
        }

        public Builder actionTrigger(final String actionTrigger){
            put(ACTION_TRIGGER, actionTrigger);
            return this;
        }
        public Builder linkPhoneNumber(final String linkPhoneNumber){
            put(PHONE_NUMBER, linkPhoneNumber);
            return this;
        }

        public Builder isPlayed(Boolean value){
            put(IS_PLAYED, value);
            return this;
        }

        public Builder authProvider(final String source){
            put(AUTH_PROVIDER, source);
            return this;
        }

        public Builder isNewUser(boolean source){
            put(IS_NEW_USER, source);
            return this;
        }
        public Builder loginSource(final String source){
            put(LOGIN_SOURCE, source);
            return this;
        }

        public Builder sharedTo(final String sharedTo){
            put(SHARED_TO, sharedTo);
            return this;
        }

        public Builder topic(final String topic){
            put(TOPIC, topic);
            return this;
        }

        public Builder list(String listName) {
            put(LIST, listName);
            return this;
        }

        public Builder url(String url) {
            put(URL, url);
            return this;
        }
        public Builder companyId(String value) {
            put(COMPANY_ID, value);
            return this;
        }

        public Builder location(String value) {
            put(LOCATION, value);
            return this;
        }

        public Builder communityId(String value) {
            put(COMMUNITY_ID, value);
            return this;
        }

        public Builder communityName(String value) {
            put(COMMUNITY_NAME, value);
            return this;
        }

        public Builder notificationId(String value) {
            put(NOTIFICATION_ID, value);
            return this;
        }

        public HashMap<String, Object> build(){
            return properties;
        }

        public Builder positionInList(Integer value){
            put(POSITION_IN_LIST, value);
            return this;
        }
    }
}
