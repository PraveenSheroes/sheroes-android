package appliedlife.pvtltd.SHEROES.models.entities.login;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Deepak on 30-05-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupRequest extends BaseRequest {

        @SerializedName("advertisementid")
        @Expose
        private String advertisementid;
        @SerializedName("deviceid")
        @Expose
        private String deviceid;
        @SerializedName("devicetype")
        @Expose
        private String devicetype;
        @SerializedName("email_id")
        @Expose
        private String emailId;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("gcmorapnsid")
        @Expose
        private String gcmorapnsid;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("os")
        @Expose
        private String os;
        @SerializedName("os_version")
        @Expose
        private String osVersion;
        @SerializedName("password")
        @Expose
        private String password;

        public String getAdvertisementid() {
            return advertisementid;
        }

        public void setAdvertisementid(String advertisementid) {
            this.advertisementid = advertisementid;
        }
        public String getDeviceid() {
            return deviceid;
        }

        public void setDeviceid(String deviceid) {
            this.deviceid = deviceid;
        }

        public String getDevicetype() {
            return devicetype;
        }

        public void setDevicetype(String devicetype) {
            this.devicetype = devicetype;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getGcmorapnsid() {
            return gcmorapnsid;
        }

        public void setGcmorapnsid(String gcmorapnsid) {
            this.gcmorapnsid = gcmorapnsid;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public void setOsVersion(String osVersion) {
            this.osVersion = osVersion;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }