package appliedlife.pvtltd.SHEROES.models.entities.miscellanous;

/**
 * Created by Praveen on 01/02/18.
 */

public class SocialPerson {
    public String name;
    public int id;
    public String iconUrl;

    public SocialPerson(String name, int id, String iconUrl) {
        this.name = name;
        this.id=id;
        this.iconUrl=iconUrl;
    }
}
