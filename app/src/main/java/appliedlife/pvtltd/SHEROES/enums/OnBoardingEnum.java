package appliedlife.pvtltd.SHEROES.enums;

/**
 * Created by Praveen_Singh on 25-03-2017.
 */

public enum  OnBoardingEnum {
    LOCATION("location"), INTEREST_SEARCH("interest_search"),JOB_AT_SEARCH("job_at_search");
     String value;
    OnBoardingEnum(String value){
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
