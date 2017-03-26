package appliedlife.pvtltd.SHEROES.enums;

/**
 * Created by Praveen_Singh on 27-03-2017.
 */

public enum  CommunityEnum {
    SEARCH_COMMUNITY("search"), FEATURE_COMMUNITY("feature"),MY_COMMUNITY("my_community");
    String value;
    CommunityEnum(String value){
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
