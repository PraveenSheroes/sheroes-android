package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.models.entities.setting.FieldErrorMessageMap;

/**
 * Created by priyanka on 19/03/17.
 */

public class EducationResponse {
        @SerializedName("subType")
        @Expose
        private String subType;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("fieldErrorMessageMap")
        @Expose
        private FieldErrorMessageMap fieldErrorMessageMap;
        @SerializedName("educationBO")
        @Expose
        private Object educationBO;
        @SerializedName("screen_name")
        @Expose
        private Object screenName;

        public String getSubType() {
            return subType;
        }

        public void setSubType(String subType) {
            this.subType = subType;
        }

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

        public Object getEducationBO() {
            return educationBO;
        }

        public void setEducationBO(Object educationBO) {
            this.educationBO = educationBO;
        }

        public Object getScreenName() {
            return screenName;
        }

        public void setScreenName(Object screenName) {
            this.screenName = screenName;
        }

}
