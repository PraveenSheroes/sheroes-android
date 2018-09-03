package appliedlife.pvtltd.SHEROES.models.entities.home;

import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;

/**
 * Created by Praveen_Singh on 17-02-2017.
 */

public class FragmentListRefreshData {
    private int pageNo;
    private String callFromFragment;
    private long idFeedDetail;
    private boolean isReactionList;
    private  long enitityOrParticpantid;
    private int swipeToRefresh;
    private long communityId;
    private List<Long> categoryIdList;
    private String searchStringName;
    private String callForNameUser;
    private String postedDate;
    private int challengePosition=-1;
    private FeedRequestPojo feedRequestPojo;
    private String subType;
    private boolean isSelfProfile;
    private long mentorUserId;
    private boolean isChallenge;
    private boolean isAnonymous;
    public FragmentListRefreshData() {

    }
    public FragmentListRefreshData(int pageNo, String callFromFragment, long idFeedDetail) {
        this.pageNo = pageNo;
        this.callFromFragment = callFromFragment;
        this.idFeedDetail = idFeedDetail;
    }

    public FragmentListRefreshData(int pageNo, String callFromFragment, long idFeedDetail, boolean isChallenge) {
        this.pageNo = pageNo;
        this.callFromFragment = callFromFragment;
        this.idFeedDetail = idFeedDetail;
        this.isChallenge = isChallenge;
    }

    public FragmentListRefreshData(int pageNo, String callFromFragment, long idFeedDetail,String searchString) {
        this.pageNo = pageNo;
        this.callFromFragment = callFromFragment;
        this.idFeedDetail = idFeedDetail;
        this.searchStringName=searchString;
    }
    public FragmentListRefreshData(int pageNo, String callFromFragment, boolean isReactionList, long enitityOrParticpantid) {
        this.pageNo = pageNo;
        this.callFromFragment = callFromFragment;
        this.isReactionList = isReactionList;
        this.enitityOrParticpantid = enitityOrParticpantid;
    }
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getCallFromFragment() {
        return callFromFragment;
    }

    public void setCallFromFragment(String callFromFragment) {
        this.callFromFragment = callFromFragment;
    }


    public boolean isReactionList() {
        return isReactionList;
    }

    public void setReactionList(boolean reactionList) {
        isReactionList = reactionList;
    }

    public long getEnitityOrParticpantid() {
        return enitityOrParticpantid;
    }

    public void setEnitityOrParticpantid(long enitityOrParticpantid) {
        this.enitityOrParticpantid = enitityOrParticpantid;
    }

    public int getSwipeToRefresh() {
        return swipeToRefresh;
    }

    public void setSwipeToRefresh(int swipeToRefresh) {
        this.swipeToRefresh = swipeToRefresh;
    }

    public long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(long communityId) {
        this.communityId = communityId;
    }

    public List<Long> getCategoryIdList() {
        return categoryIdList;
    }

    public void setCategoryIdList(List<Long> categoryIdList) {
        this.categoryIdList = categoryIdList;
    }

    public long getIdFeedDetail() {
        return idFeedDetail;
    }

    public void setIdFeedDetail(long idFeedDetail) {
        this.idFeedDetail = idFeedDetail;
    }

    public String getSearchStringName() {
        return searchStringName;
    }

    public void setSearchStringName(String searchStringName) {
        this.searchStringName = searchStringName;
    }

    public String getCallForNameUser() {
        return callForNameUser;
    }

    public void setCallForNameUser(String callForNameUser) {
        this.callForNameUser = callForNameUser;
    }
    public boolean isChallenge() {
        return isChallenge;
    }

    public void setChallenge(boolean challenge) {
        isChallenge = challenge;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public int getChallengePosition() {
        return challengePosition;
    }

    public void setChallengePosition(int challengePosition) {
        this.challengePosition = challengePosition;
    }

    public FeedRequestPojo getFeedRequestPojo() {
        return feedRequestPojo;
    }

    public void setFeedRequestPojo(FeedRequestPojo feedRequestPojo) {
        this.feedRequestPojo = feedRequestPojo;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public boolean isSelfProfile() {
        return isSelfProfile;
    }

    public void setSelfProfile(boolean selfProfile) {
        isSelfProfile = selfProfile;
    }

    public long getMentorUserId() {
        return mentorUserId;
    }

    public void setMentorUserId(long mentorUserId) {
        this.mentorUserId = mentorUserId;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }
}
