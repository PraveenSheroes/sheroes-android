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
    ID("ID"),
    LIST("List"),
    NAME("Name"),
    SOURCE("Source"),
    TITLE("Title"),
    TYPE("Type"),
    URL("Url"),
    ACTION("Action"),
    POSITION_IN_LIST("Position in List"),
    POST_TYPE("Post Type"),
    POST_ID("Post Id"),
    BODY("Body"),
    COMPANY_ID("Company Id"),
    LOCATION("Location"),
    COMMUNITY_ID("Community Id"),
    COMMUNITY_NAME("Community Name"),
    KEYWORD("Keyword"),
    SHARED_TO("Share To"),
    ENTITY_ID("Entity Id"),
    ACTIVITY_NAME("Activity Name"),
    LOOKING_FOR_NAME("Looking For Name"),
    IS_MOENGAGE("Is Moengage");

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


        public Builder type(String value){
            put(TYPE, value);
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

        public Builder entityId(String value) {
            put(ENTITY_ID, value);
            return this;
        }

        public Builder communityName(String value) {
            put(COMMUNITY_NAME, value);
            return this;
        }

        public Builder isMonengage(boolean value) {
            put(IS_MOENGAGE, value);
            return this;
        }

        public Builder keyword(String value) {
            put(KEYWORD, value);
            return this;
        }

        public HashMap<String, Object> build(){
            return properties;
        }

        public Builder positionInList(Integer value){
            put(POSITION_IN_LIST, value);
            return this;
        }

        public Builder sharedTo(final String sharedTo){
            put(SHARED_TO, sharedTo);
            return this;
        }

        public Builder activityName(final String name){
            put(ACTIVITY_NAME, name);
            return this;
        }

        public Builder lookingForName(final String lookingForName){
            put(LOOKING_FOR_NAME, lookingForName);
            return this;
        }
    }
}
