package appliedlife.pvtltd.SHEROES.models.entities.usertagging;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Praveen on 01/02/18.
 */

public class UserTaggingPerson {
    @SerializedName("id_of_entity_or_participant")
    @Expose
    public int idOfEntityOrParticipant;
    @SerializedName("entity_or_participant_id")
    @Expose
    public int entityOrParticipantId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("solr_ignore_deep_link_url")
    @Expose
    public String solrIgnoreDeepLinkUrl;
    @SerializedName("author_image_url")
    @Expose
    public String authorImageUrl;

    public int getIdOfEntityOrParticipant() {
        return idOfEntityOrParticipant;
    }

    public void setIdOfEntityOrParticipant(int idOfEntityOrParticipant) {
        this.idOfEntityOrParticipant = idOfEntityOrParticipant;
    }

    public int getEntityOrParticipantId() {
        return entityOrParticipantId;
    }

    public void setEntityOrParticipantId(int entityOrParticipantId) {
        this.entityOrParticipantId = entityOrParticipantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSolrIgnoreDeepLinkUrl() {
        return solrIgnoreDeepLinkUrl;
    }

    public void setSolrIgnoreDeepLinkUrl(String solrIgnoreDeepLinkUrl) {
        this.solrIgnoreDeepLinkUrl = solrIgnoreDeepLinkUrl;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
    }
}
