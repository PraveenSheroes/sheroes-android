
package appliedlife.pvtltd.SHEROES.models.entities.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrivilegesList {

    @SerializedName("home_town")
    @Expose
    private HomeTown homeTown;
    @SerializedName("relationship_status")
    @Expose
    private RelationshipStatus relationshipStatus;
    @SerializedName("dob")
    @Expose
    private Dob dob;
    @SerializedName("is_married")
    @Expose
    private IsMarried isMarried;

    public HomeTown getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(HomeTown homeTown) {
        this.homeTown = homeTown;
    }

    public RelationshipStatus getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setRelationshipStatus(RelationshipStatus relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public Dob getDob() {
        return dob;
    }

    public void setDob(Dob dob) {
        this.dob = dob;
    }

    public IsMarried getIsMarried() {
        return isMarried;
    }

    public void setIsMarried(IsMarried isMarried) {
        this.isMarried = isMarried;
    }

}
