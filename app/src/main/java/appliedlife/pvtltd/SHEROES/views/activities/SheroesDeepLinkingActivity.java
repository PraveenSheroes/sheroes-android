package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import org.parceler.Parcels;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;

/**
 * Created by Ajit Kumar on 11-04-2017.
 */

public class SheroesDeepLinkingActivity extends BaseActivity {
    private static final String SCREEN_LABEL = "DeepLink Screen";
    public static final String OPEN_FRAGMENT = "Open Fragment";
    private Uri mData;
    private int indexOfFourthBackSlace;
    private MoEHelper mMoEHelper;
    private MoEngageUtills moEngageUtills;
    private PayloadBuilder payloadBuilder;
    private int mFromNotification;
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        moEngageUtills.entityMoEngageDeeplink(this, mMoEHelper, payloadBuilder);
        SheroesApplication.getAppComponent(this).inject(this);
    }

    private void logout() {
        ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.LOGGED_OUT_USER, AppConstants.EMPTY_STRING);
        Intent intent = new Intent(this, WelcomeActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (null != mMoEHelper) {
            mMoEHelper.onStart(this);
        }
        try {
            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
                callDeepLinkingData();
            } else {
                logout();
            }
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
            logout();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMoEHelper.onResume(this);
    }

    private void callDeepLinkingData() {
        String notificationId = "";
        String url = "";
        String deepLink = "";

        if (null != getIntent()) {
            Intent intent = getIntent();
            if (null != getIntent().getExtras()) {
                mFromNotification = getIntent().getExtras().getInt(AppConstants.FROM_PUSH_NOTIFICATION);
            }
            if (null != intent.getData()) {
                mData = intent.getData();
                deepLink = mData.toString();
                getDeeplinkUrlFromNotification(mData.toString(), intent);
            } else {
                if (null != intent.getExtras()) {
                    deepLink = intent.getExtras().getString(AppConstants.DEEP_LINK_URL);
                    notificationId = intent.getExtras().getString(AppConstants.NOTIFICATION_ID);
                    getDeeplinkUrlFromNotification(deepLink, intent);
                }
            }

        } else {
            homeActivityCall("");
        }
    }

    private void getDeeplinkUrlFromNotification(String urlOfSharedCard, Intent sourceIntent) {
        String baseUrl = "";
        int fullLength = 0;
        if (StringUtil.isNotNullOrEmptyString(urlOfSharedCard)) {
            try {
                if (StringUtil.isNotNullOrEmptyString(urlOfSharedCard)) {
                    if (urlOfSharedCard.contains(AppConstants.CHALLENGE_URL) || urlOfSharedCard.contains(AppConstants.CHALLENGE_URL_COM)) {
                        try {
                            Intent into = new Intent(SheroesDeepLinkingActivity.this, HomeActivity.class);
                            int indexOfFirstEqual = AppUtils.findNthIndexOf(urlOfSharedCard, "=", 1);
                            String challengeUrl = urlOfSharedCard.substring(indexOfFirstEqual + 1, urlOfSharedCard.length());
                            if (StringUtil.isNotNullOrEmptyString(challengeUrl)) {
                                String ChallengeId = challengeUrl;
                                byte[] challengeBytes = Base64.decode(ChallengeId, Base64.DEFAULT);
                                String newChallengeId = new String(challengeBytes, AppConstants.UTF_8);
                                into.putExtra(AppConstants.CHALLENGE_ID, Long.parseLong(newChallengeId));
                                into.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                into.putExtra(AppConstants.FROM_PUSH_NOTIFICATION,mFromNotification);
                                //    into.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(into);
                                finish();
                            }
                            if (mFromNotification > 0) {
                                ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.BELL_NOTIFICATION_TO_HOME_CHALLENGE, AppConstants.EMPTY_STRING);
                            } else {
                                ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.DEEP_LINK_TO_HOME_CHALLENGE, AppConstants.EMPTY_STRING);
                            }
                        } catch (Exception e) {
                            Crashlytics.getInstance().core.logException(e);
                            homeActivityCall("");
                        }
                    } else if (urlOfSharedCard.equalsIgnoreCase(AppConstants.CHALLENGE_NEW_URL) || urlOfSharedCard.equals(AppConstants.CHALLENGE_NEW_URL_COM) || urlOfSharedCard.equals(AppConstants.CHALLENGE_NEW_URL + "/") || urlOfSharedCard.equals(AppConstants.CHALLENGE_NEW_URL_COM + "/")) {
                        try {
                            ContestListActivity.navigateTo(SheroesDeepLinkingActivity.this, SCREEN_LABEL, null);
                            finish();
                        } catch (Exception e) {
                            Crashlytics.getInstance().core.logException(e);
                            homeActivityCall("");
                        }
                    } else if (urlOfSharedCard.contains(AppConstants.CHALLENGE_NEW_URL) || urlOfSharedCard.contains(AppConstants.CHALLENGE_NEW_URL_COM)) {
                        try {
                            Intent into = new Intent(SheroesDeepLinkingActivity.this, ContestActivity.class);
                            String challengeIdEncoded = Uri.parse(urlOfSharedCard).getLastPathSegment();
                            byte[] challengeBytes = Base64.decode(challengeIdEncoded, Base64.DEFAULT);
                            String challengeId = new String(challengeBytes, AppConstants.UTF_8);
                            try {
                                Integer.parseInt(challengeId);
                            } catch (Exception e) {
                                homeActivityCall("");
                                return;
                            }
                            if (CommonUtil.isNotEmpty(challengeId)) {
                                into.putExtra(Contest.CONTEST_ID, challengeId);
                                into.putExtra(AppConstants.FROM_PUSH_NOTIFICATION,mFromNotification);
                                startActivity(into);
                                finish();
                            }
                            if (mFromNotification > 0) {
                                ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.BELL_NOTIFICATION_TO_HOME_CHALLENGE, AppConstants.EMPTY_STRING);
                            } else {
                                ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.DEEP_LINK_TO_HOME_CHALLENGE, AppConstants.EMPTY_STRING);
                            }
                        } catch (Exception e) {
                            Crashlytics.getInstance().core.logException(e);
                            homeActivityCall("");
                        }
                    } else if (urlOfSharedCard.contains(AppConstants.HELPLINE_URL) || urlOfSharedCard.contains(AppConstants.HELPLINE_URL_COM)) {
                        Intent helplineIntent = new Intent(SheroesDeepLinkingActivity.this, HomeActivity.class);
                        //   helplineIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        helplineIntent.putExtra(AppConstants.HELPLINE_CHAT, AppConstants.HELPLINE_CHAT);
                        helplineIntent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        helplineIntent.putExtra(AppConstants.FROM_PUSH_NOTIFICATION,mFromNotification);
                        startActivity(helplineIntent);
                        finish();
                        if (mFromNotification > 0) {
                            ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.BELL_NOTIFICATION_TO_HELP_LINE, AppConstants.EMPTY_STRING);
                        } else {
                            ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.DEEP_LINK_TO_HELP_LINE, AppConstants.EMPTY_STRING);
                        }
                    } else if (urlOfSharedCard.equals(AppConstants.ARTICLE_URL) || urlOfSharedCard.equals(AppConstants.ARTICLE_URL_COM) || urlOfSharedCard.equals(AppConstants.ARTICLE_URL + "/") || urlOfSharedCard.equals(AppConstants.ARTICLE_URL_COM + "/")) {
                        homeActivityCall(ArticlesFragment.SCREEN_LABEL);
                    } else if (urlOfSharedCard.equals(AppConstants.JOB_URL) || urlOfSharedCard.equals(AppConstants.JOB_URL_COM) || urlOfSharedCard.equals(AppConstants.JOB_URL + "/") || urlOfSharedCard.equals(AppConstants.JOB_URL_COM + "/")) {
                        homeActivityCall(AppConstants.JOB_FRAGMENT);
                    } else if (urlOfSharedCard.equals(AppConstants.CHAMPION_URL) || urlOfSharedCard.equals(AppConstants.CHAMPION_URL_COM) || urlOfSharedCard.equals(AppConstants.CHAMPION_URL + "/") || urlOfSharedCard.equals(AppConstants.CHAMPION_URL_COM + "/")) {
                        homeActivityCall(AppConstants.CHAMPION_URL);

                    } else if (urlOfSharedCard.equals(AppConstants.FAQ_URL) || urlOfSharedCard.equals(AppConstants.FAQ_URL_COM)) {
                        homeActivityCall(AppConstants.FAQ_URL);
                    } else if (urlOfSharedCard.equals(AppConstants.ICC_MEMBERS_URL) || urlOfSharedCard.equals(AppConstants.ICC_MEMBERS_URL_COM)) {
                        homeActivityCall(AppConstants.ICC_MEMBERS_URL);
                    } else if (urlOfSharedCard.equals(AppConstants.COMMUNITY_URL) || urlOfSharedCard.equals(AppConstants.COMMUNITY_URL_COM) || urlOfSharedCard.equals(AppConstants.COMMUNITY_URL + "/") || urlOfSharedCard.equals(AppConstants.COMMUNITY_URL_COM + "/")) {
                        homeActivityCall("Community List");
                    } else if (urlOfSharedCard.equals(AppConstants.CHAMPION_URL) || urlOfSharedCard.equals(AppConstants.CHAMPION_URL_COM) || urlOfSharedCard.equals(AppConstants.CHAMPION_URL + "/") || urlOfSharedCard.equals(AppConstants.CHAMPION_URL_COM + "/")) {
                        homeActivityCall(AppConstants.CHAMPION_URL);
                    } else {
                        indexOfFourthBackSlace = AppUtils.findNthIndexOf(urlOfSharedCard, AppConstants.BACK_SLASH, 4);
                        if (indexOfFourthBackSlace > 0) {
                            baseUrl = urlOfSharedCard.substring(0, indexOfFourthBackSlace);
                            //When Fourth back slace not available
                            if (baseUrl.equalsIgnoreCase(AppConstants.EMPTY_STRING)) {
                                baseUrl = urlOfSharedCard;
                            } else {
                                if (baseUrl.equalsIgnoreCase(AppConstants.USER_URL) || baseUrl.equalsIgnoreCase(AppConstants.USER_URL_COM)) {
                                    baseUrl = urlOfSharedCard;
                                }
                            }
                        } else {
                            baseUrl = urlOfSharedCard;
                        }
                    }
                } else {
                    homeActivityCall("");
                }
            } catch (Exception e) {
                Crashlytics.getInstance().core.logException(e);
                homeActivityCall("");
            }
            fullLength = urlOfSharedCard.length();

            if (StringUtil.isNotNullOrEmptyString(baseUrl)) {
                callActivities(urlOfSharedCard, baseUrl, fullLength, sourceIntent);
            }

        } else {
            Toast.makeText(getApplicationContext(), AppConstants.INVALID_URL, Toast.LENGTH_SHORT).show();
        }
    }

    private void callActivities(String urlSharedViaSocial, String baseUrl, int fullLength, Intent sourceIntent) {
        String dataIdString = AppConstants.EMPTY_STRING;
        //In case of Article
        if (AppConstants.ARTICLE_URL.equalsIgnoreCase(baseUrl) || AppConstants.ARTICLE_URL_COM.equalsIgnoreCase(baseUrl) && AppConstants.ARTICLE_URL.length() < fullLength) {
            try {
                int sareid = urlSharedViaSocial.lastIndexOf(AppConstants.BACK_SLASH);
                String id = urlSharedViaSocial.substring(sareid + 1, fullLength);
                byte[] id1 = Base64.decode(id, Base64.DEFAULT);
                dataIdString = new String(id1, AppConstants.UTF_8);
                Intent articleDetail = new Intent(SheroesDeepLinkingActivity.this, ArticleActivity.class);
                articleDetail.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, mFromNotification);
                articleDetail.putExtra(AppConstants.ARTICLE_ID, Long.parseLong(dataIdString));
                articleDetail.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(articleDetail);
                finish();
                if (mFromNotification > 0) {
                    ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.BELL_NOTIFICATION_TO_ARTICLE, AppConstants.EMPTY_STRING);

                } else {
                    ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.DEEP_LINK_TO_ARTICLE, AppConstants.EMPTY_STRING);
                }
            } catch (Exception e) {
                Crashlytics.getInstance().core.logException(e);
                homeActivityCall("");
            }
        }
        //In case of communities
        else if (AppConstants.COMMUNITY_URL.equalsIgnoreCase(baseUrl) || AppConstants.COMMUNITY_URL_COM.equalsIgnoreCase(baseUrl) && AppConstants.COMMUNITY_URL.length() < fullLength) {
            try {
                String communityDetail = urlSharedViaSocial.substring(indexOfFourthBackSlace, urlSharedViaSocial.length());
                int countBackSlash = countBackSlash(communityDetail);
                if (countBackSlash > 2) {
                    String splitCommPostUrl[] = communityDetail.split(AppConstants.BACK_SLASH);
                    if (null != splitCommPostUrl && splitCommPostUrl.length > 0) {
                        if (StringUtil.isNotNullOrEmptyString(splitCommPostUrl[splitCommPostUrl.length - 1]) && StringUtil.isNotNullOrEmptyString(splitCommPostUrl[splitCommPostUrl.length - 2])) {
                            String postid = splitCommPostUrl[splitCommPostUrl.length - 1];
                            postid = postid.replace("=", AppConstants.EMPTY_STRING);
                            String communityId = splitCommPostUrl[splitCommPostUrl.length - 2];
                            communityId = communityId.replace("=", AppConstants.EMPTY_STRING);
                            byte[] communityPostBytes = Base64.decode(postid, Base64.DEFAULT);
                            dataIdString = new String(communityPostBytes, AppConstants.UTF_8);
                            byte[] communityBytes = Base64.decode(communityId, Base64.DEFAULT);
                            String newCommunityId = new String(communityBytes, AppConstants.UTF_8);
                            Intent postIntent = new Intent(SheroesDeepLinkingActivity.this, PostDetailActivity.class);
                            postIntent.putExtra(AppConstants.COMMUNITY_ID, Long.parseLong(newCommunityId));
                            postIntent.putExtra(UserPostSolrObj.USER_POST_ID, dataIdString);
                            postIntent.putExtra(AppConstants.FROM_DEEPLINK, true);
                            postIntent.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, mFromNotification);
                            postIntent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            startActivity(postIntent);
                        }
                    }
                } else {
                    Intent into = new Intent(SheroesDeepLinkingActivity.this, CommunityDetailActivity.class);
                    String tabKey = "";
                    if (sourceIntent != null && sourceIntent.getExtras() != null) {
                        tabKey = sourceIntent.getStringExtra(CommunityDetailActivity.TAB_KEY);
                    }
                    into.putExtra(CommunityDetailActivity.TAB_KEY, tabKey);
                    int indexOfSecondBackSlace = AppUtils.findNthIndexOf(communityDetail, AppConstants.BACK_SLASH, 2);
                    String communityId = communityDetail.substring(indexOfSecondBackSlace + 1, communityDetail.length());
                    byte[] communityBytes = Base64.decode(communityId, Base64.DEFAULT);
                    String newCommunityId = new String(communityBytes, AppConstants.UTF_8);
                    into.putExtra(AppConstants.COMMUNITY_ID, newCommunityId);
                    into.putExtra(AppConstants.FROM_PUSH_NOTIFICATION,mFromNotification);
                    into.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    startActivity(into);
                }
                //  into.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                finish();
                if (mFromNotification > 0) {
                    ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.BELL_NOTIFICATION_TO_COMMUNITY, AppConstants.EMPTY_STRING);

                } else {
                    ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.DEEP_LINK_TO_COMMUNITY, AppConstants.EMPTY_STRING);
                }
            } catch (Exception e) {
                Crashlytics.getInstance().core.logException(e);
                homeActivityCall("");

            }

        } else if (AppConstants.EVENT_URL.equalsIgnoreCase(baseUrl) || AppConstants.EVENT_URL_COM.equalsIgnoreCase(baseUrl) && AppConstants.EVENT_URL.length() < fullLength) {
            try {
                int sareid = urlSharedViaSocial.lastIndexOf(AppConstants.BACK_SLASH);
                String id = urlSharedViaSocial.substring(sareid + 1, fullLength);
                byte[] id1 = Base64.decode(id, Base64.DEFAULT);
                dataIdString = new String(id1, AppConstants.UTF_8);
                dataIdString = dataIdString.replaceAll("\\D+", "");
                Intent eventDetail = new Intent(SheroesDeepLinkingActivity.this, HomeActivity.class);
                eventDetail.putExtra(AppConstants.EVENT_ID, Long.parseLong(dataIdString));
                //  eventDetail.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                eventDetail.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                eventDetail.putExtra(AppConstants.FROM_PUSH_NOTIFICATION,mFromNotification);
                startActivity(eventDetail);
                finish();
                if (mFromNotification > 0) {
                    ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.BELL_NOTIFICATION_TO_EVENT, AppConstants.EMPTY_STRING);

                } else {
                    ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.DEEP_LINK_EVENT, AppConstants.EMPTY_STRING);
                }
            } catch (Exception e) {
                Crashlytics.getInstance().core.logException(e);
                homeActivityCall("");
            }
        } else if (AppConstants.CHAMPION_URL.equalsIgnoreCase(baseUrl) || AppConstants.CHAMPION_URL_COM.equalsIgnoreCase(baseUrl) && AppConstants.CHAMPION_URL.length() < fullLength) {
            try {
                int champId = urlSharedViaSocial.lastIndexOf(AppConstants.BACK_SLASH);
                String id = urlSharedViaSocial.substring(champId + 1, fullLength);
                byte[] id1 = Base64.decode(id, Base64.DEFAULT);
                dataIdString = new String(id1, AppConstants.UTF_8);
                ProfileActivity.navigateTo(this, Long.parseLong(dataIdString), true, mFromNotification, AppConstants.FROM_PUSH_NOTIFICATION, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                finish();
                if (mFromNotification > 0) {
                    ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.BELL_NOTIFICATION_TO_CHAMPION, AppConstants.EMPTY_STRING);

                } else {
                    ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.DEEP_LINK_CHAMPION, AppConstants.EMPTY_STRING);

                }
            } catch (Exception e) {
                Crashlytics.getInstance().core.logException(e);
                homeActivityCall("");
            }
        } else if ((AppConstants.SELF_USER_PROFILE_URL).equalsIgnoreCase(baseUrl)) {
            try {
                if (null != mUserPreference) {
                    long userId = mUserPreference.get().getUserSummary().getUserId();
                    int userType = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
                    boolean isMentor=false;
                    if (userType == AppConstants.MENTOR_TYPE_ID) {
                        isMentor = true;
                    }
                    ProfileActivity.navigateTo(this, userId, isMentor, mFromNotification, AppConstants.FROM_PUSH_NOTIFICATION, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                    finish();
                    if (mFromNotification > 0) {
                        ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.BELL_NOTIFICATION_TO_PROFILE, AppConstants.EMPTY_STRING);

                    } else {
                        ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.DEEP_LINK_PROFILE, AppConstants.EMPTY_STRING);

                    }
                }
            } catch (Exception e) {
                Crashlytics.getInstance().core.logException(e);
                homeActivityCall("");
            }
        }

        //In case of profile
        else if (baseUrl.contains(AppConstants.USER_PROFILE_URL) || baseUrl.contains(AppConstants.USER_PROFILE_URL_COM) && AppConstants.USER_PROFILE_URL.length() < fullLength) {
            try {
                indexOfFourthBackSlace = AppUtils.findNthIndexOf(baseUrl, AppConstants.BACK_SLASH, 5);
                int userId = urlSharedViaSocial.lastIndexOf(AppConstants.BACK_SLASH);
                String id = urlSharedViaSocial.substring(userId + 1, fullLength);
                byte[] id1 = Base64.decode(id, Base64.DEFAULT);
                dataIdString = new String(id1, AppConstants.UTF_8);
                boolean isChampion = false;
                ProfileActivity.navigateTo(this, Long.parseLong(dataIdString), isChampion, mFromNotification, AppConstants.FROM_PUSH_NOTIFICATION, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                finish();
                if (mFromNotification > 0) {
                    ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.BELL_NOTIFICATION_TO_PROFILE, AppConstants.EMPTY_STRING);

                } else {
                    ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.DEEP_LINK_PROFILE, AppConstants.EMPTY_STRING);

                }
            } catch (Exception e) {
                Crashlytics.getInstance().core.logException(e);
                homeActivityCall("");
            }

        } else if ((AppConstants.MY_CHALLENGE_NEW_URL).equalsIgnoreCase(baseUrl) || AppConstants.MY_CHALLENGE_NEW_URL_COM.equalsIgnoreCase(baseUrl)) {
            Intent into = new Intent(this, ContestListActivity.class);
            //   into.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            into.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            into.putExtra(AppConstants.FROM_PUSH_NOTIFICATION,mFromNotification);
            startActivity(into);
            finish();
            if (mFromNotification > 0) {
                ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.BELL_NOTIFICATION_TO_PROFILE, AppConstants.EMPTY_STRING);

            } else {
                ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.DEEP_LINK_PROFILE, AppConstants.EMPTY_STRING);

            }

        } else {
            Toast.makeText(SheroesDeepLinkingActivity.this, AppConstants.WEB_BROWSER_MASSAGE, Toast.LENGTH_SHORT).show();
            finish();
            if (mFromNotification > 0) {
                ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.BELL_NOTIFICATION_TO_WEB, AppConstants.EMPTY_STRING);

            } else {
                ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.DEEP_LINK_WEB, AppConstants.EMPTY_STRING);

            }
        }

    }

    private int countBackSlash(String url) {
        int counter = 0;
        for (int i = 0; i < url.length(); i++) {
            if (url.charAt(i) == '/') {
                counter++;
            }
        }
        return counter;
    }

    private void homeActivityCall(String fragmentName) {
        if (mFromNotification > 0) {
            ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.BELL_NOTIFICATION_TO_HOME, AppConstants.EMPTY_STRING);

        } else {
            ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DEEP_LINK, GoogleAnalyticsEventActions.DEEP_LINK_TO_HOME, AppConstants.EMPTY_STRING);

        }
        Intent into = new Intent(this, HomeActivity.class);
        // into.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        into.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        into.putExtra(AppConstants.FROM_PUSH_NOTIFICATION,mFromNotification);
        into.putExtra(OPEN_FRAGMENT, fragmentName);
        startActivity(into);
        finish();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}