package appliedlife.pvtltd.SHEROES.models.entities.article;

import org.parceler.Parcel;

/**
 * Created by Praveen on 14/06/18.
 */
@Parcel(analyze = {ArticleTagName.class})
public class ArticleTagName {
    private String tagName;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
