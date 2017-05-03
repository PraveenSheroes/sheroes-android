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
    private String iteam1;
    private UserDetails userDetails;
    private List<EducationEntity> educationEntity;
    private List<ExprienceEntity> exprienceEntity;
    private List<ProjectEntity> projectEntity;
    private List<GoodAtSkill> goodAtSkill;
    private List<OpportunityType> opportunityType;
    private List<InterestType> interestType;
    private AboutMe aboutMe;
    private List<CanHelpIn> canHelpIn;
    private ClientSideLocation clientSideLocation;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIteam1() {
        return iteam1;
    }

    public void setIteam1(String iteam1) {
        this.iteam1 = iteam1;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public List<EducationEntity> getEducationEntity() {
        return educationEntity;
    }

    public void setEducationEntity(List<EducationEntity> educationEntity) {
        this.educationEntity = educationEntity;
    }

    public List<ExprienceEntity> getExprienceEntity() {
        return exprienceEntity;
    }

    public void setExprienceEntity(List<ExprienceEntity> exprienceEntity) {
        this.exprienceEntity = exprienceEntity;
    }

    public List<ProjectEntity> getProjectEntity() {
        return projectEntity;
    }

    public void setProjectEntity(List<ProjectEntity> projectEntity) {
        this.projectEntity = projectEntity;
    }

    public List<GoodAtSkill> getGoodAtSkill() {
        return goodAtSkill;
    }

    public void setGoodAtSkill(List<GoodAtSkill> goodAtSkill) {
        this.goodAtSkill = goodAtSkill;
    }

    public List<OpportunityType> getOpportunityType() {
        return opportunityType;
    }

    public void setOpportunityType(List<OpportunityType> opportunityType) {
        this.opportunityType = opportunityType;
    }

    public List<InterestType> getInterestType() {
        return interestType;
    }

    public void setInterestType(List<InterestType> interestType) {
        this.interestType = interestType;
    }

    public AboutMe getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(AboutMe aboutMe) {
        this.aboutMe = aboutMe;
    }

    public List<CanHelpIn> getCanHelpIn() {
        return canHelpIn;
    }

    public void setCanHelpIn(List<CanHelpIn> canHelpIn) {
        this.canHelpIn = canHelpIn;
    }

    public ClientSideLocation getClientSideLocation() {
        return clientSideLocation;
    }

    public void setClientSideLocation(ClientSideLocation clientSideLocation) {
        this.clientSideLocation = clientSideLocation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.type);
        dest.writeString(this.iteam1);
        dest.writeParcelable(this.userDetails, flags);
        dest.writeList(this.educationEntity);
        dest.writeTypedList(this.exprienceEntity);
        dest.writeTypedList(this.projectEntity);
        dest.writeList(this.goodAtSkill);
        dest.writeList(this.opportunityType);
        dest.writeList(this.interestType);
        dest.writeParcelable(this.aboutMe, flags);
        dest.writeList(this.canHelpIn);
        dest.writeParcelable(this.clientSideLocation, flags);
    }

    public MyProfileView() {
    }

    protected MyProfileView(Parcel in) {
        super(in);
        this.type = in.readString();
        this.iteam1 = in.readString();
        this.userDetails = in.readParcelable(UserDetails.class.getClassLoader());
        this.educationEntity = new ArrayList<EducationEntity>();
        in.readList(this.educationEntity, EducationEntity.class.getClassLoader());
        this.exprienceEntity = in.createTypedArrayList(ExprienceEntity.CREATOR);
        this.projectEntity = in.createTypedArrayList(ProjectEntity.CREATOR);
        this.goodAtSkill = new ArrayList<GoodAtSkill>();
        in.readList(this.goodAtSkill, GoodAtSkill.class.getClassLoader());
        this.opportunityType = new ArrayList<OpportunityType>();
        in.readList(this.opportunityType, OpportunityType.class.getClassLoader());
        this.interestType = new ArrayList<InterestType>();
        in.readList(this.interestType, InterestType.class.getClassLoader());
        this.aboutMe = in.readParcelable(AboutMe.class.getClassLoader());
        this.canHelpIn = new ArrayList<CanHelpIn>();
        in.readList(this.canHelpIn, CanHelpIn.class.getClassLoader());
        this.clientSideLocation = in.readParcelable(ClientSideLocation.class.getClassLoader());
    }

    public static final Creator<MyProfileView> CREATOR = new Creator<MyProfileView>() {
        @Override
        public MyProfileView createFromParcel(Parcel source) {
            return new MyProfileView(source);
        }

        @Override
        public MyProfileView[] newArray(int size) {
            return new MyProfileView[size];
        }
    };
}
