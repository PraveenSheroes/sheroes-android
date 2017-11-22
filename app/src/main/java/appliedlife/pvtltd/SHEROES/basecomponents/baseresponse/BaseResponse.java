package appliedlife.pvtltd.SHEROES.basecomponents.baseresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;

public class BaseResponse implements Parcelable {
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


    public BaseResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.fieldErrorMessageMap);
        dest.writeInt(this.numFound);
        dest.writeInt(this.start);
        dest.writeString(this.status);
        dest.writeString(this.screenName);
    }

    protected BaseResponse(Parcel in) {
        this.fieldErrorMessageMap = (HashMap<String, String>) in.readSerializable();
        this.numFound = in.readInt();
        this.start = in.readInt();
        this.status = in.readString();
        this.screenName = in.readString();
    }

}