package appliedlife.pvtltd.SHEROES.models.entities.home;

/**
 * Created by Praveen_Singh on 17-02-2017.
 */

public class FragmentListRefreshData {
    int pageNo;
    String callFromFragment;
    String idFeedDetail;
    boolean isReactionList;
    long enitityOrParticpantid;
    int swipeToRefresh;
    long communityId;
    public FragmentListRefreshData() {

    }
    public FragmentListRefreshData(int pageNo, String callFromFragment, String idFeedDetail) {
        this.pageNo = pageNo;
        this.callFromFragment = callFromFragment;
        this.idFeedDetail = idFeedDetail;
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

    public String getIdFeedDetail() {
        return idFeedDetail;
    }

    public void setIdFeedDetail(String idFeedDetail) {
        this.idFeedDetail = idFeedDetail;
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
}
