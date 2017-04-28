package appliedlife.pvtltd.SHEROES.models.entities.sharemail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 28-04-2017.
 */

public class ShareViaMail extends BaseRequest {
    @SerializedName("community_id")
    @Expose
    private Long communityId;
    @SerializedName("email_ids")
    @Expose
    private String emailId;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("community_deep_link_url")
    @Expose
    private String deepLinkUrl;


    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDeepLinkUrl() {
        return deepLinkUrl;
    }

    public void setDeepLinkUrl(String deepLinkUrl) {
        this.deepLinkUrl = deepLinkUrl;
    }
}
