package appliedlife.pvtltd.SHEROES.models.entities.miscellanous;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Praveen_Singh on 28-07-2017.
 */

public class LatLongWithLocation implements Parcelable {

    private Long entityOrParticipantId;
    private Double latitude;
    private Double longitude;
    private String cityName;
    private String locality;
    private String state;
    private String description;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LatLongWithLocation() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEntityOrParticipantId() {
        return entityOrParticipantId;
    }

    public void setEntityOrParticipantId(Long entityOrParticipantId) {
        this.entityOrParticipantId = entityOrParticipantId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.entityOrParticipantId);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeString(this.cityName);
        dest.writeString(this.locality);
        dest.writeString(this.state);
        dest.writeString(this.description);
    }

    protected LatLongWithLocation(Parcel in) {
        this.entityOrParticipantId = (Long) in.readValue(Long.class.getClassLoader());
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.cityName = in.readString();
        this.locality = in.readString();
        this.state = in.readString();
        this.description = in.readString();
    }

    public static final Creator<LatLongWithLocation> CREATOR = new Creator<LatLongWithLocation>() {
        @Override
        public LatLongWithLocation createFromParcel(Parcel source) {
            return new LatLongWithLocation(source);
        }

        @Override
        public LatLongWithLocation[] newArray(int size) {
            return new LatLongWithLocation[size];
        }
    };
}

