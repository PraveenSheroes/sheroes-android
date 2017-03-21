package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.models.entities.setting.FieldErrorMessageMap;

/**
 * Created by SHEROES-TECH on 07-03-2017.
 */

public class CreateCommunityOwnerResponse   {
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("fieldErrorMessageMap")
        @Expose
        private FieldErrorMessageMap fieldErrorMessageMap;
        @SerializedName("screen_name")
        @Expose
        private Object screenName;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public FieldErrorMessageMap getFieldErrorMessageMap() {
            return fieldErrorMessageMap;
        }

        public void setFieldErrorMessageMap(FieldErrorMessageMap fieldErrorMessageMap) {
            this.fieldErrorMessageMap = fieldErrorMessageMap;
        }

        public Object getScreenName() {
            return screenName;
        }

        public void setScreenName(Object screenName) {
            this.screenName = screenName;
        }
}
