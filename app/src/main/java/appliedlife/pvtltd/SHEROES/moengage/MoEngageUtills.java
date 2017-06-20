package appliedlife.pvtltd.SHEROES.moengage;

import android.content.Context;

import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

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

    public void entityMoEngageAppOpened(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder) {
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_APP_OPEN.value, payloadBuilder.build());
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
                case AppConstants.FEED_JOB:
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(feedDetail.getNameOrTitle()).append(AppConstants.SPACE).append(feedDetail.getAuthorName()).append(feedDetail.getAuthorCityName());
                    entityMoEngageBookmark(mMoEHelper, payloadBuilder, MoEngageConstants.JOB, feedDetail.getEntityOrParticipantId(), stringBuilder.toString(), MoEngageConstants.OPPORTUNITY_TYPE, MoEngageConstants.FUNCTION_AREA_FOR_JOB, feedDetail.getCompanyEmailId(), AppConstants.FEED_SCREEN, feedDetail.getItemPosition());
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
            case AppConstants.HEART_REACTION_CONSTANT:
                reactionType = context.getString(R.string.ID_LOVE);
                break;
            case AppConstants.EMOJI_FIRST_REACTION_CONSTANT:
                reactionType = context.getString(R.string.ID_WISHTLE);
                break;
            case AppConstants.EMOJI_SECOND_REACTION_CONSTANT:
                reactionType = context.getString(R.string.ID_XOXO);
                break;
            case AppConstants.EMOJI_THIRD_REACTION_CONSTANT:
                reactionType = context.getString(R.string.ID_LIKE);
                break;
            case AppConstants.EMOJI_FOURTH_REACTION_CONSTANT:
                reactionType = context.getString(R.string.ID_FACE_PALM);
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

    public void entityMoEngageJoinedCommunity(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, String communityName, long communityId, boolean isClose, String communityTag, String screenName, int position) {
        payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_NAME, communityName);
        payloadBuilder.putAttrLong(MoEngageConstants.COMMUNITY_ID, communityId);
        if (isClose) {
            payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_PRIVACY, context.getString(R.string.ID_CLOSED));

        } else {
            payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_PRIVACY, context.getString(R.string.ID_OPEN_MO));
        }
        payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_TAG, communityTag);
        payloadBuilder.putAttrString(MoEngageConstants.SCREEN_NAME, screenName);
        payloadBuilder.putAttrInt(MoEngageConstants.POSITION_OF_ENTITY, position);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_JOINED_COMMUNITY.value, payloadBuilder.build());
    }

    public void entityMoEngageCardShareVia(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, FeedDetail feedDetail, String shareType) {
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getSubType())) {
            String subType = feedDetail.getSubType();
            switch (subType) {
                case AppConstants.FEED_ARTICLE:
                    entityMoEngageShareCard(mMoEHelper, payloadBuilder, shareType,MoEngageConstants.ARTICLE, feedDetail.getEntityOrParticipantId(), feedDetail.getNameOrTitle(), MoEngageConstants.ARTICLE_CATEGORY, getCardTag(feedDetail), feedDetail.getAuthorName(), AppConstants.FEED_SCREEN, feedDetail.getItemPosition());
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
                case AppConstants.FEED_JOB:
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(feedDetail.getNameOrTitle()).append(AppConstants.SPACE).append(feedDetail.getAuthorName()).append(feedDetail.getAuthorCityName());
                    entityMoEngageShareCard(mMoEHelper, payloadBuilder, shareType,MoEngageConstants.JOB, feedDetail.getEntityOrParticipantId(), stringBuilder.toString(), MoEngageConstants.OPPORTUNITY_TYPE, MoEngageConstants.FUNCTION_AREA_FOR_JOB, feedDetail.getCompanyEmailId(), AppConstants.FEED_SCREEN, feedDetail.getItemPosition());
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

    public void entityMoEngageAddedMember(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, String communityName, long communityId, boolean isClose, String communityTag, boolean isMember, int memberCount) {
        payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_NAME, communityName);
        payloadBuilder.putAttrLong(MoEngageConstants.COMMUNITY_ID, communityId);
        if (isClose) {
            payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_PRIVACY, context.getString(R.string.ID_CLOSED));

        } else {
            payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_PRIVACY, context.getString(R.string.ID_OPEN_MO));
        }
        payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_TAG, communityTag);
        if (isMember) {
            payloadBuilder.putAttrString(MoEngageConstants.MEMBER_TYPE, context.getString(R.string.ID_NORMAL_MEMBER));
        } else {
            payloadBuilder.putAttrString(MoEngageConstants.MEMBER_TYPE, context.getString(R.string.ID_OWNER_Mo));
        }
        payloadBuilder.putAttrInt(MoEngageConstants.NUMBER_OF_MEMBER_ADDED, memberCount);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_ADDED_COMMUNITY_MEMBER.value, payloadBuilder.build());
    }

    public void entityMoEngageLeaveCommunity(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, String communityName, long communityId, boolean isClose, String communityTag, boolean isMember) {
        payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_NAME, communityName);
        payloadBuilder.putAttrLong(MoEngageConstants.COMMUNITY_ID, communityId);
        if (isClose) {
            payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_PRIVACY, context.getString(R.string.ID_CLOSED));

        } else {
            payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_PRIVACY, context.getString(R.string.ID_OPEN_MO));
        }
        payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_TAG, communityTag);
        if (isMember) {
            payloadBuilder.putAttrString(MoEngageConstants.MEMBER_TYPE, context.getString(R.string.ID_NORMAL_MEMBER));
        } else {
            payloadBuilder.putAttrString(MoEngageConstants.MEMBER_TYPE, context.getString(R.string.ID_OWNER_Mo));
        }
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_LEFT_COMMUNITY.value, payloadBuilder.build());
    }

    public void entityMoEngageAppliedJob(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, String jobTitle, long jobId, String company, String jobLocation, String opportunityType, String opportunityFlexibility, String functionalArea, String companyArea, String opportunitySkills) {
        payloadBuilder.putAttrString(MoEngageConstants.JOB_TITLE, jobTitle);
        payloadBuilder.putAttrLong(MoEngageConstants.JOB_ID, jobId);
        payloadBuilder.putAttrString(MoEngageConstants.COMPANY, company);
        payloadBuilder.putAttrString(MoEngageConstants.JOB_LOCATION, jobLocation);
        payloadBuilder.putAttrString(MoEngageConstants.OPPORTUNITY_TYPE, opportunityType);
        payloadBuilder.putAttrString(MoEngageConstants.OPPORTUNITY_FLEXIBILITY, opportunityFlexibility);
        payloadBuilder.putAttrString(MoEngageConstants.FUNCTIONAL_AREA, functionalArea);
        payloadBuilder.putAttrString(MoEngageConstants.COMPANY_AREA, companyArea);
        payloadBuilder.putAttrString(MoEngageConstants.OPPORTUNITY_SKILLS, opportunitySkills);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_APPLIED_JOB.value, payloadBuilder.build());
    }

    public void entityMoEngageLogOut(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, String lastScreen) {
        payloadBuilder.putAttrString(MoEngageConstants.LAST_SCREEN, lastScreen);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_LOGOUT.value, payloadBuilder.build());
    }

    public void entityMoEngageFeatureCommunity(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpent) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpent);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_VIEW_FEATURE_LISTING.value, payloadBuilder.build());
    }

    public void entityMoEngageMyCommunity(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpent) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpent);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_VIEW_MY_COMMUNITY_LISTING.value, payloadBuilder.build());
    }

    public void entityMoEngageCommunityDetail(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpent, String communityName, long communityId, boolean isClose, String communityTag) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpent);
        payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_NAME, communityName);
        payloadBuilder.putAttrLong(MoEngageConstants.COMMUNITY_ID, communityId);
        if (isClose) {
            payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_PRIVACY, context.getString(R.string.ID_CLOSED));

        } else {
            payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_PRIVACY, context.getString(R.string.ID_OPEN_MO));
        }
        payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_TAG, communityTag);

        mMoEHelper.trackEvent(MoEngageEvent.EVENT_VIEW_COMMUNITY_DETAIL.value, payloadBuilder.build());
    }

    public void entityMoEngageAboutCommunity(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long communityId, long timeSpent) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpent);
        payloadBuilder.putAttrLong(MoEngageConstants.COMMUNITY_ID, communityId);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_ABOUT_COMMUNITY.value, payloadBuilder.build());
    }

    public void entityMoEngageJobListing(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpent) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpent);
        mMoEHelper.trackEvent(MoEngageEvent.EVNET_JOB_LISTING.value, payloadBuilder.build());
    }

    public void entityMoEngageJobDetail(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpent, String jobTitle, long jobId, String company, String jobLocation, String opportunityType, String opportunityFlexibility, String functionalArea, String companyArea, String opportunitySkills) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpent);
        payloadBuilder.putAttrString(MoEngageConstants.JOB_TITLE, jobTitle);
        payloadBuilder.putAttrLong(MoEngageConstants.JOB_ID, jobId);
        payloadBuilder.putAttrString(MoEngageConstants.COMPANY, company);
        payloadBuilder.putAttrString(MoEngageConstants.JOB_LOCATION, jobLocation);
        payloadBuilder.putAttrString(MoEngageConstants.OPPORTUNITY_TYPE, opportunityType);
        payloadBuilder.putAttrString(MoEngageConstants.OPPORTUNITY_FLEXIBILITY, opportunityFlexibility);
        payloadBuilder.putAttrString(MoEngageConstants.FUNCTIONAL_AREA, functionalArea);
        payloadBuilder.putAttrString(MoEngageConstants.COMPANY_AREA, companyArea);
        payloadBuilder.putAttrString(MoEngageConstants.OPPORTUNITY_SKILLS, opportunitySkills);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_JOB_DETAIL.value, payloadBuilder.build());
    }

    public void entityMoEngageJobFilter(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, String jobLocation, String opportunityType, String opportunityFlexibility, String expStart, String expEnd) {
        payloadBuilder.putAttrString(MoEngageConstants.JOB_LOCATION, jobLocation);
        payloadBuilder.putAttrString(MoEngageConstants.OPPORTUNITY_TYPE, opportunityType);
        payloadBuilder.putAttrString(MoEngageConstants.OPPORTUNITY_FLEXIBILITY, opportunityFlexibility);
        payloadBuilder.putAttrString(MoEngageConstants.EXPERIENCE_START, expStart);
        payloadBuilder.putAttrString(MoEngageConstants.EXPERIENCE_END, expEnd);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_JOB_FILTER.value, payloadBuilder.build());
    }

    public void entityMoEngageArticleListing(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpent) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpent);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_ARTICLE_LISTING.value, payloadBuilder.build());
    }

    public void entityMoEngageArticleDetail(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpent, String ArticleTitle, long articleId, String articleCategory, String articleTag, String articleAuther, int articlePercentRead) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpent);
        payloadBuilder.putAttrString(MoEngageConstants.ARTICLE_TITLE, ArticleTitle);
        payloadBuilder.putAttrLong(MoEngageConstants.ARTICLE_ID, articleId);
        payloadBuilder.putAttrString(MoEngageConstants.ARTICLE_CATEGORY, articleCategory);
        payloadBuilder.putAttrString(MoEngageConstants.ARTICLE_TAG, articleTag);
        payloadBuilder.putAttrString(MoEngageConstants.ARTICLE_AUTHER, articleAuther);
        payloadBuilder.putAttrInt(MoEngageConstants.ARTICLE_PERCENT_READ, articlePercentRead);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_ARTICLE_DETAIL.value, payloadBuilder.build());
    }

    public void entityMoEngageNotification(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpent) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpent);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_VIEWD_NOTIFICATION.value, payloadBuilder.build());
    }

    public void entityMoEngageViewAppliedJob(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpent) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpent);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_VIEWD_APPLIED_JOBS.value, payloadBuilder.build());
    }

    public void entityMoEngageViewMyProfile(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, long timeSpent, String profileSection) {
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpent);
        payloadBuilder.putAttrString(MoEngageConstants.PROFILE_SECTION, profileSection);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_VIEWD_MY_PROFILE.value, payloadBuilder.build());
    }

    public void entityMoEngagePushNotification(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, String type, String title, String campaign) {
        payloadBuilder.putAttrString(MoEngageConstants.NOTIFICATION_TYPE, type);
        payloadBuilder.putAttrString(MoEngageConstants.CAMPAIGN, campaign);
        payloadBuilder.putAttrString(MoEngageConstants.TITLE, title);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_PUSH_NOTIFICATAION_SHOWN.value, payloadBuilder.build());
    }

    public void entityMoEngageCreatePost(Context context, MoEHelper mMoEHelper, PayloadBuilder payloadBuilder, String communityName, long communityPostId, long communityId, boolean isClose, String communityTag, String screenName) {
        payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_NAME, communityName);
        payloadBuilder.putAttrLong(MoEngageConstants.COMMUNITY_POST_ID, communityPostId);
        payloadBuilder.putAttrLong(MoEngageConstants.COMMUNITY_ID, communityId);
        if (isClose) {
            payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_PRIVACY, context.getString(R.string.ID_CLOSED));

        } else {
            payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_PRIVACY, context.getString(R.string.ID_OPEN_MO));
        }
        payloadBuilder.putAttrString(MoEngageConstants.COMMUNITY_POST_TAG, communityTag);
        payloadBuilder.putAttrString(MoEngageConstants.SCREEN_NAME, screenName);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_CREATE_COMMUNITY_POST.value, payloadBuilder.build());
    }
}
