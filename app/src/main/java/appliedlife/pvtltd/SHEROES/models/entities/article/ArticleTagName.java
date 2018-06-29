package appliedlife.pvtltd.SHEROES.models.entities.article;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Praveen on 14/06/18.
 */
@Parcel(analyze = {ArticleTagName.class})
public class ArticleTagName {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String tagName;
    @SerializedName("is_active")
    private boolean isActive;
    @SerializedName("is_other")
    private boolean isOther;
    @SerializedName("crdt")
    private long crdt;


    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean getOther() {
        return isOther;
    }

    public void setOther(boolean other) {
        isOther = other;
    }

    public long getCrdt() {
        return crdt;
    }

    public void setCrdt(long crdt) {
        this.crdt = crdt;
    }
}
