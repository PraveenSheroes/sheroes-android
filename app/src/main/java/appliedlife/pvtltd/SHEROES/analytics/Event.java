package appliedlife.pvtltd.SHEROES.analytics;

import java.util.Map;

import appliedlife.pvtltd.SHEROES.utils.CommonUtil;

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
    ARTICLE_LIKED(AnalyticsEventType.ARTICLE, "Liked"),
    ARTICLE_UNLIKED(AnalyticsEventType.ARTICLE, "UnLiked"),
    POST_LIKED(AnalyticsEventType.POST, "Liked"),
    POST_UNLIKED(AnalyticsEventType.POST, "UnLiked"),
    STORY_SHARED(AnalyticsEventType.STORY, "Shared"){
        @Override
        public boolean trackEventToProvider(AnalyticsProvider analyticsProvider) {
            return analyticsProvider == AnalyticsProvider.FACEBOOK ||
                    analyticsProvider == AnalyticsProvider.MIXPANEL ||
                    analyticsProvider == AnalyticsProvider.APPSFLYER;
        }
    },
    POST_SHARED(AnalyticsEventType.POST, "Shared"){
        @Override
        public boolean trackEventToProvider(AnalyticsProvider analyticsProvider) {
            return analyticsProvider == AnalyticsProvider.FACEBOOK ||
                    analyticsProvider == AnalyticsProvider.MIXPANEL ||
                    analyticsProvider == AnalyticsProvider.APPSFLYER;
        }
    },
    ARTICLE_SHARED(AnalyticsEventType.ARTICLE, "Shared"){
        @Override
        public boolean trackEventToProvider(AnalyticsProvider analyticsProvider) {
            return analyticsProvider == AnalyticsProvider.FACEBOOK ||
                    analyticsProvider == AnalyticsProvider.MIXPANEL ||
                    analyticsProvider == AnalyticsProvider.APPSFLYER;
        }
    },
    POST_SHARED_CLICKED(AnalyticsEventType.POST, "Shared Clicked"){
        @Override
        public boolean trackEventToProvider(AnalyticsProvider analyticsProvider) {
            return analyticsProvider == AnalyticsProvider.FACEBOOK ||
                    analyticsProvider == AnalyticsProvider.MIXPANEL ||
                    analyticsProvider == AnalyticsProvider.APPSFLYER;
        }
    },
    PROFILE_SHARED(AnalyticsEventType.PROFILE, "Shared"),
    POST_BOOKMARKED(AnalyticsEventType.POST, "Bookmarked"),
    POST_UNBOOKMARKED(AnalyticsEventType.POST, "UnBookmarked"),
    POST_EDITED(AnalyticsEventType.POST, "Edited"),
    POST_DELETED(AnalyticsEventType.POST, "Deleted"),
    POST_REPORTED(AnalyticsEventType.POST, "Reported"),
    POST_APPROVED(AnalyticsEventType.POST, "Approved"),
    POST_REJECTED(AnalyticsEventType.POST, "Rejected"),
    POST_CREATED(AnalyticsEventType.POST, "Created"){
        @Override
        public boolean trackEventToProvider(AnalyticsProvider analyticsProvider) {
            return analyticsProvider == AnalyticsProvider.FACEBOOK ||
                    analyticsProvider == AnalyticsProvider.MIXPANEL ||
                    analyticsProvider == AnalyticsProvider.APPSFLYER;
        }
    },
    POST_TOP_POST(AnalyticsEventType.POST, "Marked Top Post"),
    //endregion

    //region reply related events
    REPLY_CREATED(AnalyticsEventType.REPLY, "Created"){
        @Override
        public boolean trackEventToProvider(AnalyticsProvider analyticsProvider) {
            return analyticsProvider == AnalyticsProvider.FACEBOOK ||
                    analyticsProvider == AnalyticsProvider.MIXPANEL ||
                    analyticsProvider == AnalyticsProvider.APPSFLYER;
        }
    },
    REPLY_EDITED(AnalyticsEventType.REPLY, "Edited"),
    REPLY_DELETED(AnalyticsEventType.REPLY, "Deleted"),
    REPLY_REPORTED(AnalyticsEventType.REPLY, "Reported"),
    REPLY_LIKED(AnalyticsEventType.REPLY, "Liked"){
        @Override
        public boolean trackEventToProvider(AnalyticsProvider analyticsProvider) {
            return analyticsProvider == AnalyticsProvider.FACEBOOK ||
                    analyticsProvider == AnalyticsProvider.MIXPANEL ||
                    analyticsProvider == AnalyticsProvider.APPSFLYER;
        }
    },
    REPLY_UNLIKED(AnalyticsEventType.REPLY, "Unliked"),
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
    HELPLINE_MESSAGE_CREATED(AnalyticsEventType.HELPLINE_MESSAGE, "Created"){
        @Override
        public boolean trackEventToProvider(AnalyticsProvider analyticsProvider) {
            return analyticsProvider == AnalyticsProvider.FACEBOOK ||
                    analyticsProvider == AnalyticsProvider.MIXPANEL ||
                    analyticsProvider == AnalyticsProvider.APPSFLYER;
        }
    },
    //endregion

    //region Challenge related events
    CHALLENGE_ACCEPTED(AnalyticsEventType.CHALLENGE, "Accepted"),
    CHALLENGE_SHARED(AnalyticsEventType.CHALLENGE, "Shared"){
        @Override
        public boolean trackEventToProvider(AnalyticsProvider analyticsProvider) {
            return analyticsProvider == AnalyticsProvider.FACEBOOK ||
                    analyticsProvider == AnalyticsProvider.MIXPANEL ||
                    analyticsProvider == AnalyticsProvider.APPSFLYER;
        }
    },
    CHALLENGE_SHARED_CLICKED(AnalyticsEventType.CHALLENGE, "Shared Clicked"),
    CHALLENGE_COMPLETED(AnalyticsEventType.CHALLENGE, "Completed"),
    CHALLENGE_SUBMIT_CLICKED(AnalyticsEventType.CHALLENGE, "Submit Clicked"),
    SEND_ADDRESS_CLICKED(AnalyticsEventType.CHALLENGE, "Send Address Clicked"),
    //endregion

    //region Community related events
    COMMUNITY_JOINED(AnalyticsEventType.COMMUNITY, "Joined"),
    COMMUNITY_LEFT(AnalyticsEventType.COMMUNITY, "Left"),
    COMMUNITY_SHARED(AnalyticsEventType.COMMUNITY, "Shared"),
    COMMUNITY_ADDED_OWNER(AnalyticsEventType.COMMUNITY, "Added Owner"),
    COMMUNITY_REMOVE_OWNER(AnalyticsEventType.COMMUNITY, "Removed Owner"),
    COMMUNITY_ADDED_MEMBER(AnalyticsEventType.COMMUNITY, "Added Member"),
    COMMUNITY_REMOVED_MEMBER(AnalyticsEventType.COMMUNITY, "Removed Member"),
    COMMUNITY_INVITE_CLICKED(AnalyticsEventType.COMMUNITY, "Invite Clicked"),
    COMMUNITY_INVITE(AnalyticsEventType.COMMUNITY, "Invite"),
    APP_INVITE_CLICKED(AnalyticsEventType.APP, " Invite Clicked"),
    APP_INVITE(AnalyticsEventType.APP, " Invite"),
    FRIEND_INVITED(AnalyticsEventType.FRIEND, "Invited"),
    FRIEND_SEARCH(AnalyticsEventType.FRIEND, "Searched"),

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
    PUSH_NOTIFICATION_CLICKED(AnalyticsEventType.PUSH_NOTIFICATION, "Clicked"),
    // endregion

    //region Image related events
    LINK_SHARED(AnalyticsEventType.LINK, "Shared"),
    IMAGE_SHARED(AnalyticsEventType.IMAGE, "Shared"),
    IMAGE_COPY_LINK(AnalyticsEventType.IMAGE, "Copy Link"),
    LOOKING_FOR(AnalyticsEventType.LOOKING_FOR, "Clicked"),
    PROMO_CARD(AnalyticsEventType.PROMO_CARD, "Clicked"),
    IMAGE_CARD(AnalyticsEventType.IMAGE_CARD, "Clicked"),
    // endregion

    PROFILE_FOLLOWED(AnalyticsEventType.PROFILE, "Followed"),
    PROFILE_UNFOLLOWED(AnalyticsEventType.PROFILE, "UnFollowed"),
    PROFILE_EDITED(AnalyticsEventType.PROFILE, "Edited"){
        @Override
        public boolean trackEventToProvider(AnalyticsProvider analyticsProvider) {
            return analyticsProvider == AnalyticsProvider.FACEBOOK ||
                    analyticsProvider == AnalyticsProvider.MIXPANEL ||
                    analyticsProvider == AnalyticsProvider.APPSFLYER;
        }
    },
    PROFILE_PIC_EDIT_CLICKED(AnalyticsEventType.PROFILE, "Picture Edit Clicked"),
    PROFILE_REPORTED(AnalyticsEventType.PROFILE, "Reported"),
    PROFILE_DEACTIVATE(AnalyticsEventType.PROFILE, "Deactivated"),
    //region Image related events
    ONBOARDING_COMPLETED(AnalyticsEventType.ON_BOARDING, " Completed"),
    USER_INTRO_TUTORIAL(AnalyticsEventType.USER, "Intro Tutorial"),
    WALKTHROUGH_STARTED(AnalyticsEventType.WALKTHROUGH, " Started"),
    WALKTHROUGH_COMPLETED(AnalyticsEventType.WALKTHROUGH, " Completed"),
    // endregion

    //region leaderBoard
    BADGE_CLICKED(AnalyticsEventType.BADGE, "Clicked"),
    BADGE_SHARED(AnalyticsEventType.BADGE, "Shared"),
    //endregion

    //region onboarding
    ONBOARDING_SKIPPED(AnalyticsEventType.ON_BOARDING, "Skipped"),
    //end region

    //region Contact events
    CONTACT_SYNC_ALLOWED(AnalyticsEventType.ALLOWED_CONTACT_SYNC, ""),
    CONTACT_SYNC_DENIED(AnalyticsEventType.DENIED_CONTACT_SYNC, ""),
    //endregion

    APP_UPDATE_YES(AnalyticsEventType.APP, "Update") {
        @Override
        public void addProperties(Map<String, Object> properties) {
            super.addProperties(properties);
            properties.put(EventProperty.ACTION.getString(), "Yes");
            properties.put(EventProperty.CURRENT_VERSION.getString(), CommonUtil.getCurrentAppVersion());
        }
    },
    APP_UPDATE_NO(AnalyticsEventType.APP, "Update") {
        @Override
        public void addProperties(Map<String, Object> properties) {
            super.addProperties(properties);
            properties.put(EventProperty.ACTION.getString(), "No");
            properties.put(EventProperty.CURRENT_VERSION.getString(), CommonUtil.getCurrentAppVersion());
        }
    },

    POST_LOAD_MORE_CLICKED(AnalyticsEventType.POST_LOAD_MORE, "Clicked"),
    //region publish related events
    FACEBOOK_PUBLISHED_CLICKED(AnalyticsEventType.FACEBOOK_PUBLISH, "Clicked"),
    FACEBOOK_PUBLISHED(AnalyticsEventType.FACEBOOK_PUBLISH, ""),
    USER_TAGGED(AnalyticsEventType.USER, "Tagged"),
    STORY_DRAFT_SAVED(AnalyticsEventType.STORY, "Draft Saved"),
    STORY_CREATED(AnalyticsEventType.STORY, "Created"),
    STORY_BOOKMARKED(AnalyticsEventType.STORY, "Bookmarked"),
    STORY_UN_BOOKMARKED(AnalyticsEventType.STORY, "UnBookmarked"),
    STORY_LIKED(AnalyticsEventType.STORY, "Liked"),
    STORY_UN_LIKED(AnalyticsEventType.STORY, "UnLiked"),
    STORY_REPLY_CREATED(AnalyticsEventType.STORY, "Reply Created"),
    GENDER_SELECTED(AnalyticsEventType.GENDER_SELECTED, "");
    //endregion

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
        return analyticsProvider == AnalyticsProvider.GOOGLE_ANALYTICS || analyticsProvider == AnalyticsProvider.MIXPANEL || analyticsProvider == AnalyticsProvider.APPSFLYER;
    }

    public void addProperties(Map<String, Object> properties) {
        type.addProperties(properties);
    }
    // endregion
}
