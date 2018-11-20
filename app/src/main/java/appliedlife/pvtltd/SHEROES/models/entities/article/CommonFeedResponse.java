package  appliedlife.pvtltd.SHEROES.models.entities.article;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

/**
 * Created by Praveen_Singh on 14-02-2017.
 */
@Parcel(analyze = {CommonFeedResponse.class,BaseResponse.class})
public class CommonFeedResponse extends BaseResponse{
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
}
