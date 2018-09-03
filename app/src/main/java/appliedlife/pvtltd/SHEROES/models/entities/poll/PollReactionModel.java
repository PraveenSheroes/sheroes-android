package appliedlife.pvtltd.SHEROES.models.entities.poll;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.models.entities.feed.PollSolarObj;

@Parcel(analyze = {PollReactionModel.class})
public class PollReactionModel {
    @SerializedName("id")
    private long id;

    @SerializedName("created_on")
    private String crdt;

    @SerializedName("created_by")
    private long createdBy;

    @SerializedName("last_modified_on")
    private String lastModifiedOn;

    @SerializedName("is_active")
    private boolean isActive;

    @SerializedName("poll_id")
    private long poll_id;

    @SerializedName("poll_doc")
    private PollSolarObj pollSolrObj;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCrdt() {
        return crdt;
    }

    public void setCrdt(String crdt) {
        this.crdt = crdt;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getPoll_id() {
        return poll_id;
    }

    public void setPoll_id(long poll_id) {
        this.poll_id = poll_id;
    }

    public PollSolarObj getPollSolrObj() {
        return pollSolrObj;
    }

    public void setPollSolrObj(PollSolarObj pollSolrObj) {
        this.pollSolrObj = pollSolrObj;
    }
}
