package appliedlife.pvtltd.SHEROES.analytics;

import java.util.Map;

/**
 * Created by Ujjwal on 27-09-2017.
 * Note:
 * 1. Always Capitalize Words
 * 2. Use Past Tense
 *
 */
public enum Event {

    // TODO - Add Global properties

    //region APP related events
    APP_LOGIN(AnalyticsEventType.APP, "Login"),
    //endregion

    //region post related events
    POST_LIKED(AnalyticsEventType.POST, "Liked"),
    POST_SHARED(AnalyticsEventType.POST, "Shared"),
    POST_BOOKMARKED(AnalyticsEventType.POST, "Bookmarked"),
    POST_EDITED(AnalyticsEventType.POST, "Edited"),
    POST_DELETED(AnalyticsEventType.POST, "Deleted"),
    POST_REPORTED(AnalyticsEventType.POST, "Reported"),
    POST_APPROVED(AnalyticsEventType.POST, "Approved"),
    POST_REJECTED(AnalyticsEventType.POST, "Rejected"),
    POST_CREATED(AnalyticsEventType.POST, "Created"),
    //endregion

    //region reply related events
    REPLY_CREATED(AnalyticsEventType.REPLY, "Created"),
    REPLY_EDITED(AnalyticsEventType.REPLY, "Edited"),
    REPLY_DELETED(AnalyticsEventType.REPLY, "Deleted"),
    REPLY_REPORTED(AnalyticsEventType.REPLY, "Reported"),
    //endregion

    //region job related events
    JOBS_CREATED(AnalyticsEventType.JOB, "Created"),
    JOBS_SEARCH(AnalyticsEventType.JOB, "Search Performed"),
    JOBS_SEARCH_RESULT_CLICKED(AnalyticsEventType.JOB, "Search Result Click"),
    JOBS_APPLIED(AnalyticsEventType.JOB, "Applied"),
    JOBS_BOOKMARKED(AnalyticsEventType.JOB, "Bookmarked"),
    JOBS_EDITED(AnalyticsEventType.JOB, "Edited"),
    JOBS_DELETED(AnalyticsEventType.JOB, "Deleted"),
    JOBS_SHARED(AnalyticsEventType.JOB, "Shared"),
    JOBS_RECOMMENDED(AnalyticsEventType.JOB, "Recommended"),

    //region Helpline message events
    HELPLINE_MESSAGE_CREATED(AnalyticsEventType.HELPLINE_MESSAGE, "Created"),
    //endregion

    //region Challenge related events
    CHALLENGE_ACCEPTED(AnalyticsEventType.CHALLENGE, "Accepted"),
    CHALLENGE_SHARED(AnalyticsEventType.CHALLENGE, "Shared"),
    CHALLENGE_COMPLETED(AnalyticsEventType.CHALLENGE, "Completed"),
    //endregion

    //region Community related events
    COMMUNITY_JOINED(AnalyticsEventType.COMMUNITY, "Joined"),
    COMMUNITY_LEFT(AnalyticsEventType.COMMUNITY, "Left"),
    COMMUNITY_SHARED(AnalyticsEventType.COMMUNITY, "Shared"),
    COMMUNITY_ADDED_OWNER(AnalyticsEventType.COMMUNITY, "Added Owner"),
    COMMUNITY_REMOVE_OWNER(AnalyticsEventType.COMMUNITY, "Removed Owner"),
    COMMUNITY_ADDED_MEMBER(AnalyticsEventType.COMMUNITY, "Added Member"),
    COMMUNITY_REMOVED_MEMBER(AnalyticsEventType.COMMUNITY, "Removed Member"),
    COMMUNITY_INVITE(AnalyticsEventType.COMMUNITY, "Invite Clicked"),
    //endregion

    //region Organization related event
    ORGANIZATION_UPVOTED(AnalyticsEventType.ORGANIZATION, "Upvoted"),    // region User related events
    USER_SIGNUP(AnalyticsEventType.USER, "Signed Up"),
    USER_ONBOARDED(AnalyticsEventType.APP, "Onboarded"),
    USER_LOG_OUT(AnalyticsEventType.USER, "Logout"),
    // endregion

    //region Search events
    SEARCH_RESULT_SELECTED(AnalyticsEventType.SEARCH, "Result Selected"),
    SEARCH_PERFORMED(AnalyticsEventType.SEARCH, "Performed"),
    SEARCH_CLEARED(AnalyticsEventType.SEARCH, "Cleared"),
    // endregion

    // region Push Notification related events
    PUSH_NOTIFICATION_SHOWN(AnalyticsEventType.PUSH_NOTIFICATION, "Shown"),
    PUSH_NOTIFICATION_CLICKED(AnalyticsEventType.PUSH_NOTIFICATION, "Clicked");
    // endregion

    public final AnalyticsEventType type;
    public String name;

    Event(AnalyticsEventType eventType, String eventName) {
        this.type = eventType;
        this.name = eventName;
    }

    public String getFullName(){
        return  type.name + " " + name;
    }

    public boolean trackEventToProvider(AnalyticsProvider analyticsProvider) {
        if (analyticsProvider == AnalyticsProvider.GOOGLE_ANALYTICS || analyticsProvider == AnalyticsProvider.MIXPANEL) {
            return true;
        }
        return false;
    }

    public void addProperties(Map<String, Object> properties) {
        type.addProperties(properties);
    }
    // endregion
}
