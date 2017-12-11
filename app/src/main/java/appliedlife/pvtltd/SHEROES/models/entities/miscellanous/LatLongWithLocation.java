package appliedlife.pvtltd.SHEROES.models.entities.miscellanous;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;

/**
 * Created by Praveen_Singh on 28-07-2017.
 */
@Parcel(analyze = {LatLongWithLocation.class})
public class LatLongWithLocation {

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
}

