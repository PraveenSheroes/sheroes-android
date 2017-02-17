package appliedlife.pvtltd.SHEROES.models.entities.home;

/**
 * Created by Praveen_Singh on 17-02-2017.
 */

public class FragmentListRefreshData {
    int pageNo;
    String callFromFragment;
    String idFeedDetail;

    public FragmentListRefreshData(int pageNo, String callFromFragment, String idFeedDetail) {
        this.pageNo = pageNo;
        this.callFromFragment = callFromFragment;
        this.idFeedDetail = idFeedDetail;
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
}
