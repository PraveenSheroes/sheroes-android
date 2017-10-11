package appliedlife.pvtltd.SHEROES.analytics;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;

/**
 * Created by ujjwal on 27/09/17.
 */
public enum SuperProperty {
    // NOTE: Capitalize the first letter
    USER_ID("User Id"),
    DATE_OF_BIRTH("Date Of Birth"),
    CREATED_DATE("Created Date"),
    MOBILE_NUMBER("Mobile Number"),
    NAME("Name"),
    EMAIL_ID("Email Id");

    private final String string;

    SuperProperty(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }


    public static class Builder {
        private JSONObject superProperties = new JSONObject();
        MixpanelAPI mixpanel = MixpanelHelper.getInstance(SheroesApplication.mContext);

        private static <T> boolean validateData(final SuperProperty property, final T value) {
            return property != null && value != null;
        }


        private <T> boolean put(final SuperProperty property, final T value) {
            if (validateData(property, value)) {
                try {
                    superProperties.put(property.getString(), value);
                    // Name is being tracked separately, so ignore name
                   /* if (!property.getString().equalsIgnoreCase(NAME.toString()) && !property.getString().equalsIgnoreCase(USERNAME.toString())) {
                        mixpanel.getPeople().set(property.getString(), value);

                    }*/

                } catch (JSONException ignored) {
                }
            }
            return value != null;
        }

        public Builder name(String value) {
            put(NAME, value);
            return this;
        }

        public Builder userId(String userId) {
            put(USER_ID, userId);
            return this;
        }

        public Builder dateOfBirth(String value){
            put(DATE_OF_BIRTH, value);
            return this;
        }

        public Builder createdDate(String value){
            put(CREATED_DATE, value);
            return this;
        }

        public Builder mobileNumber(String value){
            put(MOBILE_NUMBER, value);
            return this;
        }

        public Builder emailId(String value){
            put(EMAIL_ID, value);
            return this;
        }

        public JSONObject build() {
            return superProperties;
        }
    }
}
