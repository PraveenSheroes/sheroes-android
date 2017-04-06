package appliedlife.pvtltd.SHEROES.models.entities.home;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Praveen_Singh on 13-01-2017.
 */

public class FragmentOpen implements Parcelable {

    boolean isOpen;
    boolean reactionList;
    boolean commentList;
    boolean feedOpen;
    boolean articleFragment;
    boolean jobFragment;
    boolean communityOpen;
    boolean settingFragment;
    boolean bookmarkFragment;
    boolean isImageBlur;
    int openCommentReactionFragmentFor;
    boolean isOpenImageViewer;
    List<HomeSpinnerItem> homeSpinnerItemList;
    boolean isOwner;


    boolean isTellUsOpen;
    boolean isLookingForHowCanOpen;
    boolean isJobAtOpen;
    boolean isInterestOpen;
    boolean isWorkingExpOpen;
    boolean isOpenAboutFragment;
    boolean isInviteCommunityOwner;

    public FragmentOpen() {
    }


    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isReactionList() {
        return reactionList;
    }

    public void setReactionList(boolean reactionList) {
        this.reactionList = reactionList;
    }

    public boolean isCommentList() {
        return commentList;
    }

    public void setCommentList(boolean commentList) {
        this.commentList = commentList;
    }

    public boolean isFeedOpen() {
        return feedOpen;
    }

    public void setFeedOpen(boolean feedOpen) {
        this.feedOpen = feedOpen;
    }

    public boolean isArticleFragment() {
        return articleFragment;
    }

    public void setArticleFragment(boolean articleFragment) {
        this.articleFragment = articleFragment;
    }

    public boolean isJobFragment() {
        return jobFragment;
    }

    public void setJobFragment(boolean jobFragment) {
        this.jobFragment = jobFragment;
    }

    public boolean isCommunityOpen() {
        return communityOpen;
    }

    public void setCommunityOpen(boolean communityOpen) {
        this.communityOpen = communityOpen;
    }

    public boolean isSettingFragment() {
        return settingFragment;
    }

    public void setSettingFragment(boolean settingFragment) {
        this.settingFragment = settingFragment;
    }

    public boolean isBookmarkFragment() {
        return bookmarkFragment;
    }

    public void setBookmarkFragment(boolean bookmarkFragment) {
        this.bookmarkFragment = bookmarkFragment;
    }

    public boolean isImageBlur() {
        return isImageBlur;
    }

    public void setImageBlur(boolean imageBlur) {
        isImageBlur = imageBlur;
    }

    public int getOpenCommentReactionFragmentFor() {
        return openCommentReactionFragmentFor;
    }

    public void setOpenCommentReactionFragmentFor(int openCommentReactionFragmentFor) {
        this.openCommentReactionFragmentFor = openCommentReactionFragmentFor;
    }

    public boolean isOpenImageViewer() {
        return isOpenImageViewer;
    }

    public void setOpenImageViewer(boolean openImageViewer) {
        isOpenImageViewer = openImageViewer;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isOpen ? (byte) 1 : (byte) 0);
        dest.writeByte(this.reactionList ? (byte) 1 : (byte) 0);
        dest.writeByte(this.commentList ? (byte) 1 : (byte) 0);
        dest.writeByte(this.feedOpen ? (byte) 1 : (byte) 0);
        dest.writeByte(this.articleFragment ? (byte) 1 : (byte) 0);
        dest.writeByte(this.jobFragment ? (byte) 1 : (byte) 0);
        dest.writeByte(this.communityOpen ? (byte) 1 : (byte) 0);
        dest.writeByte(this.settingFragment ? (byte) 1 : (byte) 0);
        dest.writeByte(this.bookmarkFragment ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isImageBlur ? (byte) 1 : (byte) 0);
        dest.writeInt(this.openCommentReactionFragmentFor);
        dest.writeByte(this.isOpenImageViewer ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.homeSpinnerItemList);
        dest.writeByte(this.isOwner ? (byte) 1 : (byte) 0);
    }

    protected FragmentOpen(Parcel in) {
        this.isOpen = in.readByte() != 0;
        this.reactionList = in.readByte() != 0;
        this.commentList = in.readByte() != 0;
        this.feedOpen = in.readByte() != 0;
        this.articleFragment = in.readByte() != 0;
        this.jobFragment = in.readByte() != 0;
        this.communityOpen = in.readByte() != 0;
        this.settingFragment = in.readByte() != 0;
        this.bookmarkFragment = in.readByte() != 0;
        this.isImageBlur = in.readByte() != 0;
        this.openCommentReactionFragmentFor = in.readInt();
        this.isOpenImageViewer = in.readByte() != 0;
        this.homeSpinnerItemList = in.createTypedArrayList(HomeSpinnerItem.CREATOR);
        this.isOwner = in.readByte() != 0;
    }

    public static final Creator<FragmentOpen> CREATOR = new Creator<FragmentOpen>() {
        @Override
        public FragmentOpen createFromParcel(Parcel source) {
            return new FragmentOpen(source);
        }

        @Override
        public FragmentOpen[] newArray(int size) {
            return new FragmentOpen[size];
        }
    };

    public boolean isTellUsOpen() {
        return isTellUsOpen;
    }

    public void setTellUsOpen(boolean tellUsOpen) {
        isTellUsOpen = tellUsOpen;
    }

    public boolean isLookingForHowCanOpen() {
        return isLookingForHowCanOpen;
    }

    public void setLookingForHowCanOpen(boolean lookingForHowCanOpen) {
        isLookingForHowCanOpen = lookingForHowCanOpen;
    }

    public boolean isJobAtOpen() {
        return isJobAtOpen;
    }

    public void setJobAtOpen(boolean jobAtOpen) {
        isJobAtOpen = jobAtOpen;
    }

    public boolean isInterestOpen() {
        return isInterestOpen;
    }

    public void setInterestOpen(boolean interestOpen) {
        isInterestOpen = interestOpen;
    }

    public boolean isWorkingExpOpen() {
        return isWorkingExpOpen;
    }

    public void setWorkingExpOpen(boolean workingExpOpen) {
        isWorkingExpOpen = workingExpOpen;
    }

    public boolean isOpenAboutFragment() {
        return isOpenAboutFragment;
    }

    public void setOpenAboutFragment(boolean openAboutFragment) {
        isOpenAboutFragment = openAboutFragment;
    }

    public boolean isInviteCommunityOwner() {
        return isInviteCommunityOwner;
    }

    public void setInviteCommunityOwner(boolean inviteCommunityOwner) {
        isInviteCommunityOwner = inviteCommunityOwner;
    }
}
