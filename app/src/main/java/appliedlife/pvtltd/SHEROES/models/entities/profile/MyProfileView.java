package appliedlife.pvtltd.SHEROES.models.entities.profile;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by priyanka on 07/04/17.
 */

public class MyProfileView extends BaseResponse implements Parcelable {

    private String type;
    private UserDetails userDetails;
    private ArrayList<EducationEntity> educationEntity;
    private ExprienceEntity exprienceEntity;
    private ProjectEntity projectEntity;
    private ArrayList<GoodAtSkill> goodAtSkill;
    private ArrayList<OpportunityType> opportunityType;
    private ArrayList<InterestType> interestType;
    private AboutMe aboutMe;
    private ArrayList<CanHelpIn> canHelpIn;
    private ClientSideLocation clientSideLocation;


    public MyProfileView(Parcel in) {
        type = in.readString();
    }

    public static final Creator<MyProfileView> CREATOR = new Creator<MyProfileView>() {
        @Override
        public MyProfileView createFromParcel(Parcel in) {
            return new MyProfileView(in);
        }

        @Override
        public MyProfileView[] newArray(int size) {
            return new MyProfileView[size];
        }
    };

    public MyProfileView() {

    }

    public ArrayList<CanHelpIn> getCanHelpIn() {
        return canHelpIn;
    }

    public void setCanHelpIn(ArrayList<CanHelpIn> canHelpIn) {
        this.canHelpIn = canHelpIn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public ArrayList<EducationEntity> getEducationEntity() {
        return educationEntity;
    }

    public void setEducationEntity(ArrayList<EducationEntity> educationEntity) {
        this.educationEntity = educationEntity;
    }

    public ExprienceEntity getExprienceEntity() {
        return exprienceEntity;
    }

    public void setExprienceEntity(ExprienceEntity exprienceEntity) {
        this.exprienceEntity = exprienceEntity;
    }

    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    public void setProjectEntity(ProjectEntity projectEntity) {
        this.projectEntity = projectEntity;
    }

    public ArrayList<GoodAtSkill> getGoodAtSkill() {
        return goodAtSkill;
    }

    public void setGoodAtSkill(ArrayList<GoodAtSkill> goodAtSkill) {
        this.goodAtSkill = goodAtSkill;
    }

    public ArrayList<OpportunityType> getOpportunityType() {
        return opportunityType;
    }

    public void setOpportunityType(ArrayList<OpportunityType> opportunityType) {
        this.opportunityType = opportunityType;
    }

    public ArrayList<InterestType> getInterestType() {
        return interestType;
    }

    public void setInterestType(ArrayList<InterestType> interestType) {
        this.interestType = interestType;
    }

    public AboutMe getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(AboutMe aboutMe) {
        this.aboutMe = aboutMe;
    }

    public void setClientSideLocation(ClientSideLocation clientSideLocation) {
        this.clientSideLocation = clientSideLocation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
    }
}
