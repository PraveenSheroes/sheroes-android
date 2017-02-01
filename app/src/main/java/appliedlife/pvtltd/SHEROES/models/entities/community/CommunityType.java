package appliedlife.pvtltd.SHEROES.models.entities.community;

/**
 * Created by Ajit Kumar on 17-01-2017.
 */

public class CommunityType {
    private boolean selected;
    private String items;


    public CommunityType(String items) {

        this.items = items;

    }

    public String getItems() {

        return items;
    }

    public void setItemName(String name) {

        this.items = name;
    }
    public boolean getSelected() {
        return selected;
    }

    public boolean setSelected(Boolean selected) {
        return this.selected = selected;
    }
}
