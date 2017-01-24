package appliedlife.pvtltd.SHEROES.basecomponents.baserequest;

import android.os.Parcel;
import android.os.Parcelable;

public class TrackingParams implements Parcelable {

    private String trackingId;
    private String userIP;
    private String userEmailId;
    private String requestingServerIP;
    private String respondingServerIP;


    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getRequestingServerIP() {
        return requestingServerIP;
    }

    public void setRequestingServerIP(String requestingServerIP) {
        this.requestingServerIP = requestingServerIP;
    }

    public String getRespondingServerIP() {
        return respondingServerIP;
    }

    public void setRespondingServerIP(String respondingServerIP) {
        this.respondingServerIP = respondingServerIP;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.trackingId);
        dest.writeString(this.userIP);
        dest.writeString(this.userEmailId);
        dest.writeString(this.requestingServerIP);
        dest.writeString(this.respondingServerIP);
    }

    public TrackingParams() {
    }

    protected TrackingParams(Parcel in) {
        this.trackingId = in.readString();
        this.userIP = in.readString();
        this.userEmailId = in.readString();
        this.requestingServerIP = in.readString();
        this.respondingServerIP = in.readString();
    }

    public static final Parcelable.Creator<TrackingParams> CREATOR = new Parcelable.Creator<TrackingParams>() {
        @Override
        public TrackingParams createFromParcel(Parcel source) {
            return new TrackingParams(source);
        }

        @Override
        public TrackingParams[] newArray(int size) {
            return new TrackingParams[size];
        }
    };
}
