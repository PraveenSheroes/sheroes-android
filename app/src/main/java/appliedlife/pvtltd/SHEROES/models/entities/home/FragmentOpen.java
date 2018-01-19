package appliedlife.pvtltd.SHEROES.models.entities.home;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Praveen_Singh on 13-01-2017.
 */
@Parcel(analyze = {FragmentOpen.class})
public class FragmentOpen {




    private boolean communityOpen;

    private List<HomeSpinnerItem> homeSpinnerItemList;

    private boolean isOwner;
    private boolean isICCMemberListFragment;
    private boolean isFAQSFragment;
    private boolean isFeedFragment;


    public FragmentOpen() {
    }



    public boolean isCommunityOpen() {
        return communityOpen;
    }

    public void setCommunityOpen(boolean communityOpen) {
        this.communityOpen = communityOpen;
    }


    public List<HomeSpinnerItem> getHomeSpinnerItemList() {
        return homeSpinnerItemList;
    }

    public void setHomeSpinnerItemList(List<HomeSpinnerItem> homeSpinnerItemList) {
        this.homeSpinnerItemList = homeSpinnerItemList;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }


    public boolean isICCMemberListFragment() {
        return isICCMemberListFragment;
    }

    public void setICCMemberListFragment(boolean ICCMemberListFragment) {
        isICCMemberListFragment = ICCMemberListFragment;
    }

    public boolean isFAQSFragment() {
        return isFAQSFragment;
    }

    public void setFAQSFragment(boolean FAQSFragment) {
        isFAQSFragment = FAQSFragment;
    }

    public boolean isFeedFragment() {
        return isFeedFragment;
    }

    public void setFeedFragment(boolean feedFragment) {
        isFeedFragment = feedFragment;
    }

}
