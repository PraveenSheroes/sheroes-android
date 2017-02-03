package appliedlife.pvtltd.SHEROES.models.entities.home;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Praveen_Singh on 13-01-2017.
 */

public class FragmentOpen implements Parcelable {

    boolean isOpen;
    boolean reactionList;
    boolean commentList;
    boolean feedOpen;
    boolean articleFragment;
    boolean communityOpen;
    boolean settingFragment;
    boolean isImageBlur;


    public FragmentOpen(boolean isOpen, boolean reactionList, boolean commentList, boolean feedOpen, boolean articleFragment,boolean settingFragment, boolean communityOpen) {
        this.isOpen = isOpen;
        this.reactionList = reactionList;
        this.commentList = commentList;
        this.feedOpen = feedOpen;
        this.articleFragment = articleFragment;
        this.communityOpen = communityOpen;
        this.settingFragment=settingFragment;

        this.isImageBlur = isImageBlur;
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

    public void setSettingFragment(boolean settingFragment) {


        this.settingFragment = settingFragment;
    }

    public boolean isCommunityOpen() {
        return communityOpen;
    }

    public void setCommunityOpen(boolean communityOpen) {
        this.communityOpen = communityOpen;
    }

    public boolean isImageBlur() {
        return isImageBlur;
    }

    public void setImageBlur(boolean imageBlur) {
        isImageBlur = imageBlur;
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
        dest.writeByte(this.communityOpen ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isImageBlur ? (byte) 1 : (byte) 0);
    }

    protected FragmentOpen(Parcel in) {
        this.isOpen = in.readByte() != 0;
        this.reactionList = in.readByte() != 0;
        this.commentList = in.readByte() != 0;
        this.feedOpen = in.readByte() != 0;
        this.articleFragment = in.readByte() != 0;
        this.communityOpen = in.readByte() != 0;
        this.isImageBlur = in.readByte() != 0;
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
}
