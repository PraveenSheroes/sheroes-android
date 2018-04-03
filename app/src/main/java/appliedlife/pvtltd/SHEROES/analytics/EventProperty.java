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
    SOURCE_URL("Source Url"),
    ACTION("Action"),
    POSITION_IN_LIST("Position in List"),
    POST_TYPE("Post Type"),
    POST_ID("Post Id"),
    BODY("Body"),
    COMPANY_ID("Company Id"),
    LOCATION("Location"),
    COMMUNITY_ID("Community Id"),
    CHALLENGE_ID("Challenge Id"),
    COMMUNITY_NAME("Community Name"),
    KEYWORD("Keyword"),
    SHARED_TO("Share To"),
    ENTITY_ID("Entity Id"),
    ACTIVITY_NAME("Activity Name"),
    LOOKING_FOR_NAME("Looking For Name"),
    TAB_TITLE("Tab Title"),
    TAB_KEY("Tab Key"),
    SOURCE_SCREEN_ID("Source Screen Id"),
    SOURCE_TAB_KEY("Source Tab Key"),
    SOURCE_TAB_TITLE("Source Tab Title"),
    IS_MOENGAGE("Is Moengage"),
    POSITION_IN_SEQUENCE("Position In Sequence"),
    IS_MENTOR("Is Mentor"),
    IS_OWN_PROFILE("Is Own Profile"),
    DESCRIPTION("Description"),
    COMMUNITY_CATEGORY("Community Category"),
    POSITION_IN_CAROUSEL("Position In Carousel"),
    IS_CHECKED("Is Checked"),
    COLLECTION_NAME("Collection Name"),
    STREAM_TYPE("Stream Type"),
    SOURCE_COLLECTION_NAME("Source Collection Name"),
    POSITION_OF_CAROUSEL("Position Of Carousel"),
    POST_COMMENT_ID("Post / Comment ID"),
    TAGGED_IN("Tagged In"),
    TAGGED_USER_ID("Tagged User ID");

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
        public Builder postCommentId(String value){
            put(POST_COMMENT_ID, value);
            return this;
        }
        public Builder taggedUserId(String value){
            put(TAGGED_USER_ID, value);
            return this;
        }
        public Builder taggedIn(String value){
            put(TAGGED_IN, value);
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

        public Builder sourceUrl(String url) {
            put(SOURCE_URL, url);
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

        public Builder challengeId(String value) {
            put(CHALLENGE_ID, value);
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

        public Builder communityCategory(String value) {
            put(COMMUNITY_CATEGORY, value);
            return this;
        }

        public Builder positionInCarousel(String value) {
            put(POSITION_IN_CAROUSEL, value);
            return this;
        }

        public Builder positionOfCarousel(String value) {
            put(POSITION_OF_CAROUSEL, value);
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
        public Builder tabTitle(final String tabTitle){
            put(TAB_TITLE, tabTitle);
            return this;
        }
        public Builder tabKey(final String tabKey){
            put(TAB_KEY, tabKey);
            return this;
        }
        public Builder sourceScreenId(final String sourceScreenId){
            put(SOURCE_SCREEN_ID, sourceScreenId);
            return this;
        }
        public Builder sourceTabKey(final String sourceTabKey){
            put(SOURCE_TAB_KEY, sourceTabKey);
            return this;
        }
        public Builder sourceTabTitle(final String sourceTabTitle){
            put(SOURCE_TAB_TITLE, sourceTabTitle);
            return this;
        }

        public Builder positionInSequence(final String position){
            put(POSITION_IN_SEQUENCE, position);
            return this;
        }

        public Builder description(final String description){
            put(DESCRIPTION, description);
            return this;
        }

        public Builder isMentor(final boolean isMentor){
            put(IS_MENTOR, isMentor);
            return this;
        }

        public Builder isChecked(final boolean isChecked){
            put(IS_CHECKED, isChecked);
            return this;
        }

        public Builder isOwnProfile(final boolean isOwnProfile){
            put(IS_OWN_PROFILE, isOwnProfile);
            return this;
        }

        public Builder collectionName(String value){
            put(COLLECTION_NAME, value);
            return this;
        }

        public Builder sourceCollectionName(String value){
            put(SOURCE_COLLECTION_NAME, value);
            return this;
        }

        public Builder streamType(String value){
            put(STREAM_TYPE, value);
            return this;
        }

    }
}
