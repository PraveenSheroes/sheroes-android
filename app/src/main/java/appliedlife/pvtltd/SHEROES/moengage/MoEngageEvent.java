package appliedlife.pvtltd.SHEROES.moengage;

/**
 * Created by Praveen_Singh on 16-05-2017.
 */

public enum  MoEngageEvent {
    EVENT_APP_OPEN("opened app"),
    EVENT_SIGNED_UP("signed up"),
    EVENT_LOGGED_IN("logged in"),
    EVENT_STARTED_ON_BOARDING("started on boarding"),
    EVENT_VIEW_FIRST_MESSAGE_SCREEN("viewed onboarding- first message screen"),
    EVENT_VIEW_CURRENT_STATUS("viewed onboarding- current status, current city and mobile"),
    EVENT_VIEW_HOW_CAN_LOOKING_FOR("viewed onboarding- looking for"),
    EVENT_COMPLETED_ON_BOARDING("completed onboarding"),
    EVENT_VIEWED_FEED("viewed feed"),
    EVENT_REACTED("reacted"),
    EVENT_REPLIED("replied"),
    EVENT_BOOKMARKED("bookmarked"),
    EVENT_CREATE_COMMUNITY_POST("created a community post"),
    EVENT_SHARED_EXTERNALLY("shared externally"),
    EVENT_JOINED_COMMUNITY("joined a community"),
    EVENT_ADDED_COMMUNITY_MEMBER("added community members"),
    EVENT_LEFT_COMMUNITY("left a community"),
    EVENT_APPLIED_JOB("applied to a job"),
    EVENT_LOGOUT("logged out of the app"),
    EVENT_VIEW_FEATURE_LISTING("viewed community listing- featured"),
    EVENT_VIEW_MY_COMMUNITY_LISTING("viewed community listing- my communities"),
    EVENT_VIEW_COMMUNITY_DETAIL("viewed community view- post listing"),
    EVENT_ABOUT_COMMUNITY("viewed community view- about community"),
    EVNET_JOB_LISTING("viewed job listing"),
    EVENT_JOB_DETAIL("viewed job details"),
    EVENT_JOB_FILTER("used filter on jobs"),
    EVENT_ARTICLE_LISTING("viewed article listing"),
    EVENT_ARTICLE_DETAIL("viewed article details"),
    EVENT_VIEWD_NOTIFICATION("viewed notifications"),
    EVENT_VIEWD_APPLIED_JOBS("viewed applied jobs"),
    EVENT_VIEWD_MY_PROFILE("viewed my profile"),
    EVENT_PUSH_NOTIFICATAION_SHOWN("push notification shown"),
    EVENT_VIEWED_FAQS("viewed SHE FAQS"),
    EVENT_VIEWED_ICC_MEMBERS("viewed ICC Members");
    public final String value;
    MoEngageEvent(String pValue) {
        value = pValue;
    }
}
