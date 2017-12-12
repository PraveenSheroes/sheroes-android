package appliedlife.pvtltd.SHEROES.models.entities.profile;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by priyanka on 07/04/17.
 */
@Parcel(analyze = {MyProfileView.class, BaseResponse.class})
public class MyProfileView extends BaseResponse{

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
}
