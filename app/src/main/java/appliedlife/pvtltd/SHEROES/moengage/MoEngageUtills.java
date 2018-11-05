package appliedlife.pvtltd.SHEROES.moengage;

import android.content.Context;
import android.util.Base64;

import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;

/**
 * Created by Praveen_Singh on 17-05-2017.
 */

public class MoEngageUtills {
    private final String TAG = LogUtils.makeLogTag(MoEngageUtills.class);
    private static MoEngageUtills sInstance;

    public static synchronized MoEngageUtills getInstance() {
        if (sInstance == null) {
            sInstance = new MoEngageUtills();
        }
        return sInstance;
    }

    public void entityMoEngageAppVersion(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, int appVersion) {
        payloadBuilder.putAttrInt(MoEngageConstants.APP_VERSION, appVersion);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_APP_OPEN.value, payloadBuilder.build());
    }

    public void entityMoEngageLastOpen(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, Date lastOpen) {
        payloadBuilder.putAttrDate(MoEngageConstants.LAST_APP_OPEN, lastOpen);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_LAST_OPEN.value, payloadBuilder.build());
    }

    public void entityMoEngageDeeplink(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder) {
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_DEEP_LINK.value, payloadBuilder.build());
    }

    public void entityMoEngageUserAttribute(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, LoginResponse loginResponse) {
        if (null != loginResponse.getUserSummary() && loginResponse.getUserSummary().getUserId() > 0) {
            mMoEHelper.setUniqueId(loginResponse.getUserSummary().getUserId());

            // If you have first and last name separately
            if (StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getFirstName())) {
                mMoEHelper.setFirstName(loginResponse.getUserSummary().getFirstName());
            }
            if (StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getLastName())) {
                mMoEHelper.setLastName(loginResponse.getUserSummary().getLastName());
            }

            try {
                String userId = String.valueOf(loginResponse.getUserSummary().getUserId());
                byte[] data = new byte[0];

                data = userId.getBytes(AppConstants.UTF_8);
                String encodedUserId = Base64.encodeToString(data, Base64.DEFAULT);

                mMoEHelper.setUserAttribute("encoded_user_id", encodedUserId);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getEmailId())) {
                mMoEHelper.setEmail(loginResponse.getUserSummary().getEmailId());
            }
            if (null != loginResponse.getUserSummary().getUserBO()) {

                if (StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getUserBO().getDob())) {
                    mMoEHelper.setBirthDate(loginResponse.getUserSummary().getUserBO().getDob());
                }
                if (StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getUserBO().getGender())) {
                    mMoEHelper.setGender(loginResponse.getUserSummary().getUserBO().getGender());
                }
                if (StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getUserBO().getMobile())) {
                    mMoEHelper.setNumber(loginResponse.getUserSummary().getUserBO().getMobile());
                }
            }
        }
    }

    public void entityMoEngageLoggedIn(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, String loginSource) {
        payloadBuilder.putAttrString(MoEngageConstants.LOGGED_IN_USING, loginSource);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_LOGGED_IN.value, payloadBuilder.build());
    }

    public void entityMoEngageSignUp(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, String loginSource) {
        payloadBuilder.putAttrString(MoEngageConstants.SIGN_UP_USING, loginSource);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_SIGNED_UP.value, payloadBuilder.build());
    }

    public void entityMoEngageViewFeed(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpentFeed) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpentFeed);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_VIEWED_FEED.value, payloadBuilder.build());
    }


    public void entityMoEngageViewICCMember(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpentFeed) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpentFeed);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_VIEWED_ICC_MEMBERS.value, payloadBuilder.build());
    }

    public void entityMoEngageViewFAQ(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpentFeed) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpentFeed);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_VIEWED_FAQS.value, payloadBuilder.build());
    }

    public void entityMoEngageVerifyEmail(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpentFeed) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpentFeed);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_VERIFY_EMAIL.value, payloadBuilder.build());
    }

    public void entityMoEngageForgotPassword(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpentFeed) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpentFeed);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_FORGET_PWD.value, payloadBuilder.build());
    }

    private String getCardTag(FeedDetail feedDetail) {
        StringBuilder mergeTags = new StringBuilder();
        if (StringUtil.isNotEmptyCollection(feedDetail.getTags())) {
            List<String> tags = feedDetail.getTags();
            for (String tag : tags) {
                mergeTags.append(tag).append(AppConstants.COMMA);
            }
        }
        return mergeTags.toString();
    }

    public void entityMoEngageBookMarkData(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, FeedDetail feedDetail) {
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getSubType())) {
            String subType = feedDetail.getSubType();
            switch (subType) {
                case AppConstants.FEED_ARTICLE:

                    entityMoEngageBookmark(mMoEHelper, payloadBuilder, MoEngageConstants.ARTICLE, feedDetail.getEntityOrParticipantId(), feedDetail.getNameOrTitle(), MoEngageConstants.ARTICLE_CATEGORY, getCardTag(feedDetail), feedDetail.getAuthorName(), AppConstants.FEED_SCREEN, feedDetail.getItemPosition());
                    break;
                case AppConstants.FEED_COMMUNITY_POST:
                    entityMoEngageBookmark(mMoEHelper, payloadBuilder, MoEngageConstants.COMMUNITY_POST, feedDetail.getEntityOrParticipantId(), feedDetail.getNameOrTitle(), MoEngageConstants.COMMUNITY_POST_CATEGORY, getCardTag(feedDetail), feedDetail.getAuthorName(), AppConstants.FEED_SCREEN, feedDetail.getItemPosition());
                    break;
            }
        }
    }

    private void entityMoEngageBookmark(MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, String entityValue, long entityId, String entityTitle, String entityType, String entityTag, String entityCreator, String screenName, int position) {
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY, entityValue);
        payloadBuilder.putAttrLong(MoEngageConstants.ENTITY_ID, entityId);
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY_TITLE, entityTitle);
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY_TYPE, entityType);
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY_TAG, entityTag);
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY_CREATOR, entityCreator);
        payloadBuilder.putAttrString(MoEngageConstants.SCREEN_NAME, screenName);
        payloadBuilder.putAttrInt(MoEngageConstants.POSITION_OF_ENTITY, position);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_BOOKMARKED.value, payloadBuilder.build());
    }

    public void entityMoEngageReaction(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, FeedDetail feedDetail, int reactionValue, int position) {
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getSubType())) {
            String subType = feedDetail.getSubType();
            String reactionType = reactionValueName(reactionValue, context);
            switch (subType) {
                case AppConstants.FEED_ARTICLE:
                    entityMoEngageReaction(mMoEHelper, payloadBuilder, MoEngageConstants.ARTICLE, feedDetail.getEntityOrParticipantId(), feedDetail.getNameOrTitle(), MoEngageConstants.ARTICLE_CATEGORY, reactionType, getCardTag(feedDetail), feedDetail.getAuthorName(), AppConstants.FEED_SCREEN, position);
                    break;
                case AppConstants.FEED_COMMUNITY_POST:
                    entityMoEngageReaction(mMoEHelper, payloadBuilder, MoEngageConstants.COMMUNITY_POST.toLowerCase(), feedDetail.getEntityOrParticipantId(), feedDetail.getNameOrTitle(), MoEngageConstants.COMMUNITY_POST_CATEGORY, reactionType, getCardTag(feedDetail), feedDetail.getAuthorName(), AppConstants.FEED_SCREEN, position);
                    break;
            }
        }
    }

    private String reactionValueName(int reactionValue, Context context) {
        String reactionType = MoEngageConstants.LIKE_UN_DO;
        switch (reactionValue) {
            case AppConstants.NO_REACTION_CONSTANT:
                reactionType = MoEngageConstants.LIKE_UN_DO;
                break;
            case AppConstants.EMOJI_THIRD_REACTION_CONSTANT:
                reactionType = context.getString(R.string.ID_LIKE);
                break;
        }
        return reactionType;
    }

    private void entityMoEngageReaction(MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, String entityValue, long entityId, String entityTitle, String entityType, String reactionType, String entityTag, String entityCreator, String screenName, int position) {
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY, entityValue);
        payloadBuilder.putAttrLong(MoEngageConstants.ENTITY_ID, entityId);
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY_TITLE, entityTitle);
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY_TYPE, entityType);
        payloadBuilder.putAttrString(MoEngageConstants.REACTION_TYPE, reactionType);
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY_TAG, entityTag);
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY_CREATOR, entityCreator);
        payloadBuilder.putAttrString(MoEngageConstants.SCREEN_NAME, screenName);
        payloadBuilder.putAttrInt(MoEngageConstants.POSITION_OF_ENTITY, position);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_REACTED.value, payloadBuilder.build());
    }

    public void entityMoEngageCardShareVia(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, FeedDetail feedDetail, String shareType) {
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getSubType())) {
            String subType = feedDetail.getSubType();
            switch (subType) {
                case AppConstants.FEED_ARTICLE:
                    entityMoEngageShareCard(mMoEHelper, payloadBuilder, shareType, MoEngageConstants.ARTICLE, feedDetail.getEntityOrParticipantId(), feedDetail.getNameOrTitle(), MoEngageConstants.ARTICLE_CATEGORY, getCardTag(feedDetail), feedDetail.getAuthorName(), AppConstants.FEED_SCREEN, feedDetail.getItemPosition());
                    break;
                case AppConstants.FEED_COMMUNITY_POST:
                    entityMoEngageShareCard(mMoEHelper, payloadBuilder, shareType, MoEngageConstants.COMMUNITY_POST, feedDetail.getEntityOrParticipantId(), feedDetail.getNameOrTitle(), MoEngageConstants.COMMUNITY_POST_CATEGORY, getCardTag(feedDetail), feedDetail.getAuthorName(), AppConstants.FEED_SCREEN, feedDetail.getItemPosition());
                    break;
                case AppConstants.FEED_COMMUNITY:
                    entityMoEngageShareCard(mMoEHelper, payloadBuilder, shareType, MoEngageConstants.COMMUNITY, feedDetail.getEntityOrParticipantId(), feedDetail.getNameOrTitle(), MoEngageConstants.COMMUNITY_CATEGORY, getCardTag(feedDetail), feedDetail.getAuthorName(), AppConstants.FEED_SCREEN, feedDetail.getItemPosition());
                    break;
                case AppConstants.FEATURED_COMMUNITY:
                    entityMoEngageShareCard(mMoEHelper, payloadBuilder, shareType, MoEngageConstants.FEATURE_COMMUNITY, feedDetail.getEntityOrParticipantId(), feedDetail.getNameOrTitle(), MoEngageConstants.FEATURE_COMMUNITY_CATEGORY, getCardTag(feedDetail), feedDetail.getAuthorName(), AppConstants.FEED_SCREEN, feedDetail.getItemPosition());
                    break;
            }
        }
    }

    public void entityMoEngageShareCard(MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, String shareType, String entityValue, long entityId, String entityTitle, String entityType, String entityTag, String entityCreator, String screenName, int position) {
        payloadBuilder.putAttrString(MoEngageConstants.SHARE_TYPE, shareType);
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY, entityValue);
        payloadBuilder.putAttrLong(MoEngageConstants.ENTITY_ID, entityId);
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY_TITLE, entityTitle);
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY_TYPE, entityType);
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY_TAG, entityTag);
        payloadBuilder.putAttrString(MoEngageConstants.ENTITY_CREATOR, entityCreator);
        payloadBuilder.putAttrString(MoEngageConstants.SCREEN_NAME, screenName);
        payloadBuilder.putAttrInt(MoEngageConstants.POSITION_OF_ENTITY, position);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_SHARED_EXTERNALLY.value, payloadBuilder.build());
    }

    public void entityMoEngageArticleListing(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpent) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpent);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_ARTICLE_LISTING.value, payloadBuilder.build());
    }

    public void entityMoEngageNotification(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpent) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpent);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_VIEWD_NOTIFICATION.value, payloadBuilder.build());
    }


    public void entityMoEngagePushNotification(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, String type, String title, String campaign) {
        payloadBuilder.putAttrString(MoEngageConstants.NOTIFICATION_TYPE, type);
        payloadBuilder.putAttrString(MoEngageConstants.CAMPAIGN, campaign);
        payloadBuilder.putAttrString(MoEngageConstants.TITLE, title);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_PUSH_NOTIFICATAION_SHOWN.value, payloadBuilder.build());
    }

}
