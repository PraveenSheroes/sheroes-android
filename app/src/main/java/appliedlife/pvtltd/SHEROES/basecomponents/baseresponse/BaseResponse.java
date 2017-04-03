package appliedlife.pvtltd.SHEROES.basecomponents.baseresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public abstract class BaseResponse
{
       @SerializedName("fieldErrorMessageMap")
        @Expose
        private HashMap<String,String> fieldErrorMessageMap;
        @SerializedName("numFound")
        @Expose
        private int numFound;
        @SerializedName("start")
        @Expose
        private int start;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("screen_name")
        @Expose
        private String screenName;

        public HashMap<String, String> getFieldErrorMessageMap() {
            return fieldErrorMessageMap;
        }

        public void setFieldErrorMessageMap(HashMap<String, String> fieldErrorMessageMap) {
            this.fieldErrorMessageMap = fieldErrorMessageMap;
        }

        public int getNumFound() {
            return numFound;
        }

        public void setNumFound(int numFound) {
            this.numFound = numFound;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getScreenName() {
            return screenName;
        }

        public void setScreenName(String screenName) {
            this.screenName = screenName;
        }
    }