package appliedlife.pvtltd.SHEROES.models.entities.poll;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.PollSolarObj;

@Parcel(analyze = {PollOptionModel.class})

public class PollOptionModel {

    @SerializedName("id")
    private long pollOptionId;

    @SerializedName("description")
    private String description;

    @SerializedName("is_active")
    private boolean isActive;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("solr_ignore_is_voted")
    private boolean isVoted;

    @SerializedName("solr_ignore_total_no_of_votes")
    private long totalNoOfVotes;

    @SerializedName("solr_ignore_total_no_of_votes_percent")
    private int totalNoOfVotesPercent;


    public long getPollOptionId() {
        return pollOptionId;
    }

    public void setPollOptionId(long pollOptionId) {
        this.pollOptionId = pollOptionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setVoted(boolean voted) {
        isVoted = voted;
    }

    public long getTotalNoOfVotes() {
        return totalNoOfVotes;
    }

    public void setTotalNoOfVotes(long totalNoOfVotes) {
        this.totalNoOfVotes = totalNoOfVotes;
    }

    public int getTotalNoOfVotesPercent() {
        return totalNoOfVotesPercent;
    }

    public void setTotalNoOfVotesPercent(int totalNoOfVotesPercent) {
        this.totalNoOfVotesPercent = totalNoOfVotesPercent;
    }
}
