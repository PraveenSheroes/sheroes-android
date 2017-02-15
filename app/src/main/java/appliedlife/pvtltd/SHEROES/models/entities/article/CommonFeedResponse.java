package  appliedlife.pvtltd.SHEROES.models.entities.article;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

/**
 * Created by Praveen_Singh on 14-02-2017.
 */

public class CommonFeedResponse extends BaseResponse implements Parcelable {
    @SerializedName("jobList")
    private List<FeedDetail> jobList;
    @SerializedName("articleList")
    private List<FeedDetail> articleList;
    @SerializedName("communityList")
    private List<FeedDetail> communityList;
    @SerializedName("communityPostList")
    private List<FeedDetail> communityPostList;

    public List<FeedDetail> getJobList() {
        return jobList;
    }

    public void setJobList(List<FeedDetail> jobList) {
        this.jobList = jobList;
    }

    public List<FeedDetail> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<FeedDetail> articleList) {
        this.articleList = articleList;
    }

    public List<FeedDetail> getCommunityList() {
        return communityList;
    }

    public void setCommunityList(List<FeedDetail> communityList) {
        this.communityList = communityList;
    }

    public List<FeedDetail> getCommunityPostList() {
        return communityPostList;
    }

    public void setCommunityPostList(List<FeedDetail> communityPostList) {
        this.communityPostList = communityPostList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.jobList);
        dest.writeList(this.articleList);
        dest.writeList(this.communityList);
        dest.writeList(this.communityPostList);
    }

    public CommonFeedResponse() {
    }

    protected CommonFeedResponse(Parcel in) {
        this.jobList = new ArrayList<FeedDetail>();
        in.readList(this.jobList, FeedDetail.class.getClassLoader());
        this.articleList = new ArrayList<FeedDetail>();
        in.readList(this.articleList, FeedDetail.class.getClassLoader());
        this.communityList = new ArrayList<FeedDetail>();
        in.readList(this.communityList, FeedDetail.class.getClassLoader());
        this.communityPostList = new ArrayList<FeedDetail>();
        in.readList(this.communityPostList, FeedDetail.class.getClassLoader());
    }

    public static final Parcelable.Creator<CommonFeedResponse> CREATOR = new Parcelable.Creator<CommonFeedResponse>() {
        @Override
        public CommonFeedResponse createFromParcel(Parcel source) {
            return new CommonFeedResponse(source);
        }

        @Override
        public CommonFeedResponse[] newArray(int size) {
            return new CommonFeedResponse[size];
        }
    };
}
