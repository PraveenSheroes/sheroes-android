package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by SHEROES-TECH on 15-02-2017.
 */

public class ProfileViewList extends BaseResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tag_item_ui_for_onboarding")
    @Expose
    private String tag;

    @SerializedName("item1")
    @Expose
    private String item1;

    @SerializedName("item2")
    @Expose
    private String item2;

    @SerializedName("item3")
    @Expose
    private String item3;

    @SerializedName("item4")
    @Expose
    private String item4;

    @SerializedName("item5")
    @Expose
    private String item5;

    @SerializedName("item6")
    @Expose
    private String item6;

    @SerializedName("item7")
    @Expose
    private String item7;

    @SerializedName("item8")
    @Expose
    private String item8;

    @SerializedName("item9")
    @Expose
    private String item9;

    @SerializedName("item10")
    @Expose
    private String item10;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }
    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }
    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }
    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getItem5() {
        return item5;
    }

    public void setItem5(String item5) {
        this.item5 = item5;
    }

    public String getItem6() {
        return item6;
    }

    public void setItem6(String item6) {
        this.item6 = item6;
    }

    public String getItem7() {
        return item7;
    }

    public void setItem7(String item7) {
        this.item7 = item7;
    }

    public String getItem8() {
        return item8;
    }

    public void setItem8(String item8) {
        this.item8 = item8;
    }

    public String getItem9() {
        return item9;
    }

    public void setItem9(String item9) {
        this.item9 = item9;
    }

    public String getItem10() {
        return item10;
    }

    public void setItem10(String item10) {
        this.item10 = item10;
    }
}
