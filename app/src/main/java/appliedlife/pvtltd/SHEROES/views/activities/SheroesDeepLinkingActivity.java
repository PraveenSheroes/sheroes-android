package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFormatException;
import android.util.Base64;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import androidx.core.app.ActivityCompat;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchFragment;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.REQUEST_CODE_FOR_INVITE_FRIEND;

/**
 * Created by Ajit Kumar on 11-04-2017.
 */

public class SheroesDeepLinkingActivity extends BaseActivity {
    // region constants
    private static final String SCREEN_LABEL = "DeepLink Screen";
    public static final String OPEN_FRAGMENT = "Open Fragment";
    public static final int COMMUNITY_DEEP_LINK_URL_BACK_SLASH = 4;
    // endregion

    // region Inject
    @Inject
    Preference<LoginResponse> mUserPreference;
    // endregion

    // region member variables
    private Uri mData;
    private int mIndexOfBackSlaceInPostDeeplink;
    private int mFromNotification;
    private String mSource;
    private Intent mIntent;
    private String mSearchText, mSearchCategory, mNextToken;
    // endregion

    // region public and lifecycle methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
    }

    private void logout() {
        Intent intent = new Intent(this, LanguageSelectionActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary()) {
                callDeepLinkingData();
            } else {
                logout();
            }
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
            logout();
        }

    }
    // endregion

    // region protected methods
    @Override
    protected SheroesPresenter getPresenter() {
        return null;
    }

    private void callDeepLinkingData() throws ParcelFormatException {
        String deepLink = "";
        if (null != getIntent()) {
            Intent intent = getIntent();
            mIntent = intent;
            if (null != getIntent().getExtras()) {
                mFromNotification = getIntent().getExtras().getInt(AppConstants.FROM_PUSH_NOTIFICATION);
                mSource = getIntent().getExtras().getString(BaseActivity.SOURCE_SCREEN);
            }
            if (null != intent.getData()) {
                mData = intent.getData();
                String trimUrl = CommonUtil.trimBranchIdQuery(mData.toString());
                getDeeplinkUrlFromNotification(trimUrl, intent);
            } else {

                if (intent.getType() != null && (Intent.ACTION_SEND.equals(intent.getAction()) || Intent.ACTION_SEND_MULTIPLE.equals(intent.getAction()))) {
                    openPostActivity(intent);
                } else if (null != intent.getExtras()) {
                    deepLink = intent.getExtras().getString(AppConstants.DEEP_LINK_URL);
                    String trimUrl = CommonUtil.trimBranchIdQuery(deepLink.toString());
                    getDeeplinkUrlFromNotification(trimUrl, intent);
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
                    if (urlOfSharedCard.contains(AppConstants.FEED_URL) || urlOfSharedCard.contains(AppConstants.FEED_URL_COM)) {
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
                                into.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, mFromNotification);
                                into.putExtra(BaseActivity.SOURCE_SCREEN, mSource);
                                //    into.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                addShareLink(sourceIntent, into);
                                startActivity(into);
                                finish();
                            }
                        } catch (Exception e) {
                            Crashlytics.getInstance().core.logException(e);
                            homeActivityCall("");
                        }
                    } else if (urlOfSharedCard.equalsIgnoreCase(AppConstants.CHALLENGE_NEW_URL) || urlOfSharedCard.equals(AppConstants.CHALLENGE_NEW_URL_COM) || urlOfSharedCard.equals(AppConstants.CHALLENGE_NEW_URL + "/") || urlOfSharedCard.equals(AppConstants.CHALLENGE_NEW_URL_COM + "/")) {
                        try {
                            ContestListActivity.navigateTo(SheroesDeepLinkingActivity.this, SCREEN_LABEL, null);
                            Intent intent = new Intent(SheroesDeepLinkingActivity.this, ContestListActivity.class);
                            addShareLink(sourceIntent, intent);
                            ActivityCompat.startActivity(SheroesDeepLinkingActivity.this, intent, null);
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
                                into.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, mFromNotification);
                                into.putExtra(BaseActivity.SOURCE_SCREEN, mSource);
                                addShareLink(sourceIntent, into);
                                startActivity(into);
                                finish();
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
                        helplineIntent.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, mFromNotification);
                        helplineIntent.putExtra(BaseActivity.SOURCE_SCREEN, mSource);
                        addShareLink(sourceIntent, helplineIntent);
                        startActivity(helplineIntent);
                        finish();
                    } else if (urlOfSharedCard.equals(AppConstants.WRITE_STORY_URL) || urlOfSharedCard.equals(AppConstants.WRITE_STORY_URL_COM)) {
                        homeActivityCall(AppConstants.WRITE_STORY_URL);
                    } else if (urlOfSharedCard.equals(AppConstants.SELECT_LANGUAGE_URL_COM) || urlOfSharedCard.equals(AppConstants.SELECT_LANGUAGE_URL_COM)) {
                        homeActivityCall(AppConstants.SELECT_LANGUAGE_URL_COM);
                    } else if (urlOfSharedCard.equals(AppConstants.MY_STORY_URL) || urlOfSharedCard.equals(AppConstants.MY_STORY_URL_COM)) {
                        try {
                            showUserProfile(true, sourceIntent);
                        } catch (Exception e) {
                            Crashlytics.getInstance().core.logException(e);
                            homeActivityCall("");
                        }
                    } else if (urlOfSharedCard.equals(AppConstants.ARTICLE_URL) || urlOfSharedCard.equals(AppConstants.ARTICLE_URL_COM) || urlOfSharedCard.equals(AppConstants.ARTICLE_URL + "/") || urlOfSharedCard.equals(AppConstants.ARTICLE_URL_COM + "/")) {
                        homeActivityCall(ArticlesFragment.SCREEN_LABEL);
                    } else if (urlOfSharedCard.startsWith(AppConstants.ARTICLE_CATEGORY_URL_COM) || urlOfSharedCard.startsWith(AppConstants.ARTICLE_CATEGORY_URL_IN)) {
                        homeActivityCallForArticleCategory(urlOfSharedCard);
                    } else if (urlOfSharedCard.equals(AppConstants.CHAMPION_URL) || urlOfSharedCard.equals(AppConstants.CHAMPION_URL_COM) || urlOfSharedCard.equals(AppConstants.CHAMPION_URL + "/") || urlOfSharedCard.equals(AppConstants.CHAMPION_URL_COM + "/")) {
                        homeActivityCall(AppConstants.CHAMPION_URL);
                    } else if (urlOfSharedCard.equals(AppConstants.FAQ_URL) || urlOfSharedCard.equals(AppConstants.FAQ_URL_COM)) {
                        homeActivityCall(AppConstants.FAQ_URL);
                    } else if (urlOfSharedCard.equals(AppConstants.ICC_MEMBERS_URL) || urlOfSharedCard.equals(AppConstants.ICC_MEMBERS_URL_COM)) {
                        homeActivityCall(AppConstants.ICC_MEMBERS_URL);
                    } else if (urlOfSharedCard.equals(AppConstants.COMMUNITY_URL) || urlOfSharedCard.equals(AppConstants.COMMUNITY_URL_COM) || urlOfSharedCard.equals(AppConstants.COMMUNITY_URL + "/") || urlOfSharedCard.equals(AppConstants.COMMUNITY_URL_COM + "/")) {
                        homeActivityCall(AppConstants.COMMUNITY_URL);
                    } else if (urlOfSharedCard.equals(AppConstants.CHAMPION_URL) || urlOfSharedCard.equals(AppConstants.CHAMPION_URL_COM) || urlOfSharedCard.equals(AppConstants.CHAMPION_URL + "/") || urlOfSharedCard.equals(AppConstants.CHAMPION_URL_COM + "/")) {
                        homeActivityCall(AppConstants.CHAMPION_URL);
                    } else if (urlOfSharedCard.contains(AppConstants.SEARCH_URL) || urlOfSharedCard.contains(AppConstants.SEARCH_URL_COM) || urlOfSharedCard.contains(AppConstants.SEARCH_URL + "/") || urlOfSharedCard.contains(AppConstants.SEARCH_URL_COM + "/")) {
                        mSearchText = sourceIntent.getData().getQueryParameter(AppConstants.SEARCH_TEXT);
                        mSearchCategory = sourceIntent.getData().getQueryParameter(AppConstants.SEARCH_CATEGORY);
                        mNextToken = sourceIntent.getData().getQueryParameter(AppConstants.NEXT_TOKEN);
                        homeActivityCall(SearchFragment.SCREEN_LABEL);
                    } else {
                        mIndexOfBackSlaceInPostDeeplink = AppUtils.findNthIndexOf(urlOfSharedCard, AppConstants.BACK_SLASH, AppConstants.BACK_SLASH_OCCURRENCE_IN_POST_LINK);
                        if (mIndexOfBackSlaceInPostDeeplink > 0) {
                            baseUrl = urlOfSharedCard.substring(0, mIndexOfBackSlaceInPostDeeplink);
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
                openEndPointActivities(urlOfSharedCard, baseUrl, fullLength, sourceIntent);
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.invalid_url, Toast.LENGTH_SHORT).show();
        }
    }

    private void addShareLink(Intent sourceIntent, Intent destinationIntent) {
        if (sourceIntent.getExtras().getBoolean(AppConstants.IS_SHARE_DEEP_LINK)) {
            sourceIntent.getExtras().getString(AppConstants.SHARE_DEEP_LINK_URL);
            destinationIntent.putExtra(AppConstants.SHARE_DEEP_LINK_URL, sourceIntent.getExtras().getString(AppConstants.SHARE_DEEP_LINK_URL));
            destinationIntent.putExtra(AppConstants.SHARE_TEXT, sourceIntent.getExtras().getString(AppConstants.SHARE_TEXT));
            destinationIntent.putExtra(AppConstants.SHARE_DIALOG_TITLE, sourceIntent.getExtras().getString(AppConstants.SHARE_DIALOG_TITLE));
            destinationIntent.putExtra(AppConstants.SHARE_IMAGE, sourceIntent.getExtras().getString(AppConstants.SHARE_IMAGE));
            destinationIntent.putExtra(AppConstants.IS_SHARE_DEEP_LINK, sourceIntent.getExtras().getBoolean(AppConstants.IS_SHARE_DEEP_LINK));
            destinationIntent.putExtra(AppConstants.SHARE_CHANNEL, sourceIntent.getExtras().getString(AppConstants.SHARE_CHANNEL));
        }
    }

    private void addSearchQuery(Intent destinationIntent, String fragmentName) {
        if (SearchFragment.SCREEN_LABEL.equalsIgnoreCase(fragmentName)) {
            destinationIntent.putExtra(AppConstants.SEARCH_TEXT, mSearchText);
            destinationIntent.putExtra(AppConstants.SEARCH_CATEGORY, mSearchCategory);
            destinationIntent.putExtra(AppConstants.NEXT_TOKEN, mNextToken);
        }
    }

    private void openEndPointActivities(String urlSharedViaSocial, String baseUrl, int fullLength, Intent sourceIntent) {
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
                articleDetail.putExtra(BaseActivity.SOURCE_SCREEN, mSource);
                articleDetail.putExtra(AppConstants.ARTICLE_ID, Long.parseLong(dataIdString));
                articleDetail.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                addShareLink(mIntent, articleDetail);
                startActivity(articleDetail);
                finish();
            } catch (Exception e) {
                Crashlytics.getInstance().core.logException(e);
                homeActivityCall("");
            }
        } else if (AppConstants.STORIES_URL.equalsIgnoreCase(baseUrl) || AppConstants.STORIES_URL_COM.equalsIgnoreCase(baseUrl) && AppConstants.STORIES_URL.length() < fullLength) {
            try {
                int sareid = urlSharedViaSocial.lastIndexOf(AppConstants.BACK_SLASH);
                String id = urlSharedViaSocial.substring(sareid + 1, fullLength);
                byte[] id1 = Base64.decode(id, Base64.DEFAULT);
                dataIdString = new String(id1, AppConstants.UTF_8);
                Intent articleDetail = new Intent(SheroesDeepLinkingActivity.this, ArticleActivity.class);
                articleDetail.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, mFromNotification);
                articleDetail.putExtra(BaseActivity.SOURCE_SCREEN, mSource);
                articleDetail.putExtra(BaseActivity.USER_STORY, true);
                articleDetail.putExtra(AppConstants.ARTICLE_ID, Long.parseLong(dataIdString));
                articleDetail.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                addShareLink(mIntent, articleDetail);
                startActivity(articleDetail);
                finish();
            } catch (Exception e) {
                Crashlytics.getInstance().core.logException(e);
                homeActivityCall("");
            }
        }
        //In case of communities
        else if (AppConstants.COMMUNITY_URL.equalsIgnoreCase(baseUrl) || AppConstants.COMMUNITY_URL_COM.equalsIgnoreCase(baseUrl) && AppConstants.COMMUNITY_URL.length() < fullLength) {
            openCommunityPostPollDeepLink(urlSharedViaSocial, baseUrl, sourceIntent);
        } else if (AppConstants.CHAMPION_URL.equalsIgnoreCase(baseUrl) || AppConstants.CHAMPION_URL_COM.equalsIgnoreCase(baseUrl) && AppConstants.CHAMPION_URL.length() < fullLength) {
            try {
                int champId = urlSharedViaSocial.lastIndexOf(AppConstants.BACK_SLASH);
                String id = urlSharedViaSocial.substring(champId + 1, fullLength);
                byte[] id1 = Base64.decode(id, Base64.DEFAULT);
                dataIdString = new String(id1, AppConstants.UTF_8);

                Intent intent = new Intent(SheroesDeepLinkingActivity.this, ProfileActivity.class);
                intent.putExtra(AppConstants.CHAMPION_ID, Long.parseLong(dataIdString));
                // intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
                intent.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, mFromNotification);
                intent.putExtra(BaseActivity.SOURCE_SCREEN, mSource);
                intent.putExtra(AppConstants.IS_CHAMPION_ID, true);
                addShareLink(sourceIntent, intent);
                ActivityCompat.startActivityForResult(SheroesDeepLinkingActivity.this, intent, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL, null);
                finish();
            } catch (Exception e) {
                Crashlytics.getInstance().core.logException(e);
                homeActivityCall("");
            }
        } else if ((AppConstants.SELF_USER_PROFILE_URL).equalsIgnoreCase(baseUrl)) {
            try {
                showUserProfile(false, sourceIntent);
            } catch (Exception e) {
                Crashlytics.getInstance().core.logException(e);
                homeActivityCall("");
            }
        }

        //In case of profile
        else if (baseUrl.contains(AppConstants.USER_PROFILE_URL) || baseUrl.contains(AppConstants.USER_PROFILE_URL_COM) && AppConstants.USER_PROFILE_URL.length() < fullLength) {
            try {
                mIndexOfBackSlaceInPostDeeplink = AppUtils.findNthIndexOf(baseUrl, AppConstants.BACK_SLASH, 5);
                int userId = urlSharedViaSocial.lastIndexOf(AppConstants.BACK_SLASH);
                String id = urlSharedViaSocial.substring(userId + 1, fullLength);
                byte[] id1 = Base64.decode(id, Base64.DEFAULT);
                dataIdString = new String(id1, AppConstants.UTF_8);
                boolean isChampion = false;
                Intent intent = new Intent(SheroesDeepLinkingActivity.this, ProfileActivity.class);
                intent.putExtra(AppConstants.CHAMPION_ID, Long.parseLong(dataIdString));
                // intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
                intent.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, mFromNotification);
                intent.putExtra(BaseActivity.SOURCE_SCREEN, mSource);
                intent.putExtra(AppConstants.IS_CHAMPION_ID, AppConstants.FROM_PUSH_NOTIFICATION);
                addShareLink(sourceIntent, intent);
                ActivityCompat.startActivityForResult(SheroesDeepLinkingActivity.this, intent, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL, null);

                //ProfileActivity.navigateTo(this, Long.parseLong(dataIdString), isChampion, mFromNotification, AppConstants.FROM_PUSH_NOTIFICATION, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
                finish();
            } catch (Exception e) {
                Crashlytics.getInstance().core.logException(e);
                homeActivityCall("");
            }

        } else if ((AppConstants.MY_CHALLENGE_NEW_URL).equalsIgnoreCase(baseUrl) || AppConstants.MY_CHALLENGE_NEW_URL_COM.equalsIgnoreCase(baseUrl)) {
            Intent into = new Intent(this, ContestListActivity.class);
            //   into.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            into.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            into.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, mFromNotification);
            into.putExtra(BaseActivity.SOURCE_SCREEN, mSource);
            addShareLink(sourceIntent, into);
            startActivity(into);
            finish();
        } else if ((AppConstants.INVITE_FRIEND_URL).equalsIgnoreCase(baseUrl) || AppConstants.INVITE_FRIEND_URL_COM.equalsIgnoreCase(baseUrl)) {
            AllContactActivity.navigateTo(this, mFromNotification, null, null, REQUEST_CODE_FOR_INVITE_FRIEND);
            finish();
        } else {
            Toast.makeText(SheroesDeepLinkingActivity.this, AppConstants.WEB_BROWSER_MASSAGE, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void showUserProfile(boolean isWriteStory, Intent sourceIntent) {
        if (null != mUserPreference) {
            long userId = mUserPreference.get().getUserSummary().getUserId();
            int userType = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
            boolean isMentor = false;
            if (userType == AppConstants.CHAMPION_TYPE_ID) {
                isMentor = true;
            }
            Intent intent = new Intent(SheroesDeepLinkingActivity.this, ProfileActivity.class);
            intent.putExtra(AppConstants.CHAMPION_ID, userId);
            intent.putExtra(AppConstants.IS_CHAMPION_ID, isMentor);
            intent.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, mFromNotification);
            intent.putExtra(BaseActivity.STORIES_TAB, isWriteStory);
            intent.putExtra(BaseActivity.SOURCE_SCREEN, mSource);
            addShareLink(sourceIntent, intent);
            ActivityCompat.startActivityForResult(SheroesDeepLinkingActivity.this, intent, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL, null);
            finish();
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

    //Launch community post activity
    private void openPostActivity(Intent intent) {
        if (intent.getExtras() != null) {
            Intent into = new Intent(this, CommunityPostActivity.class);
            into.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            into.putExtras(intent.getExtras());
            into.setAction(intent.getAction());
            into.setType(intent.getType());
            addShareLink(mIntent, into);
            startActivity(into);
            finish();
        }
    }

    //Article category id
    private void homeActivityCallForArticleCategory(String articleCategoryUrl) {
        try {
            int articleCategoryId = articleCategoryUrl.lastIndexOf(AppConstants.BACK_SLASH);
            String id = articleCategoryUrl.substring(articleCategoryId + 1, articleCategoryUrl.length());
            byte[] id1 = Base64.decode(id, Base64.DEFAULT);
            String dataIdString = new String(id1, AppConstants.UTF_8);
            Intent articleDetail = new Intent(SheroesDeepLinkingActivity.this, HomeActivity.class);
            articleDetail.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, mFromNotification);
            articleDetail.putExtra(BaseActivity.SOURCE_SCREEN, mSource);
            articleDetail.putExtra(AppConstants.ARTICLE_CATEGORY_ID, Long.parseLong(dataIdString));
            articleDetail.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            articleDetail.putExtra(OPEN_FRAGMENT, ArticlesFragment.SCREEN_LABEL);
            addShareLink(mIntent, articleDetail);
            startActivity(articleDetail);
            finish();
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
            homeActivityCall("");
        }
    }

    private void openCommunityPostPollDeepLink(String urlSharedViaSocial, String baseUrl, Intent sourceIntent) {
        try {
            String communityDetail = urlSharedViaSocial.substring(mIndexOfBackSlaceInPostDeeplink, urlSharedViaSocial.length());
            int countBackSlash = countBackSlash(communityDetail);
            if (countBackSlash > 2) {
                String communityId = "", postId = "", typeOfFeed = AppConstants.COMMUNITY_POST_URL_COM;
                String splitCommPostUrl[] = communityDetail.split(AppConstants.BACK_SLASH);
                if (splitCommPostUrl.length <= COMMUNITY_DEEP_LINK_URL_BACK_SLASH && StringUtil.isNotNullOrEmptyString(splitCommPostUrl[splitCommPostUrl.length - 1]) && StringUtil.isNotNullOrEmptyString(splitCommPostUrl[splitCommPostUrl.length - 2])) {
                    //https://sheroes.com/communities/carrer-community/MTI3NQ==/MTIx
                    /* If deepLink consists only post id after community name/id(eg: mcarrer-community/MTI3NQ==/MTIx)
                     * Url's syntax were defined when we first time launched app.
                     * We can change Url's structure after discussion but for now we have to follow this structure.*/
                    postId = splitCommPostUrl[splitCommPostUrl.length - 1];
                    postId = postId.replace("=", AppConstants.EMPTY_STRING);
                    communityId = splitCommPostUrl[splitCommPostUrl.length - 2];
                    communityId = communityId.replace("=", AppConstants.EMPTY_STRING);
                    byte[] communityPostBytes = Base64.decode(postId, Base64.DEFAULT);
                    postId = new String(communityPostBytes, AppConstants.UTF_8);
                    byte[] communityBytes = Base64.decode(communityId, Base64.DEFAULT);
                    communityId = new String(communityBytes, AppConstants.UTF_8);
                    typeOfFeed = AppConstants.COMMUNITY_POST_URL_COM;
                } else if (splitCommPostUrl.length > COMMUNITY_DEEP_LINK_URL_BACK_SLASH && StringUtil.isNotNullOrEmptyString(splitCommPostUrl[splitCommPostUrl.length - 1]) && StringUtil.isNotNullOrEmptyString(splitCommPostUrl[splitCommPostUrl.length - 3])) {
                    //https://sheroes.com/communities/my-ferst-community/Mjgw/polls/Mjc=
                    /* If deepLink consists poll or any other type in url after community name/id(eg: my-ferst-community/Mjgw/polls) */
                    postId = splitCommPostUrl[splitCommPostUrl.length - 1];
                    postId = postId.replace("=", AppConstants.EMPTY_STRING);
                    communityId = splitCommPostUrl[splitCommPostUrl.length - 3];
                    communityId = communityId.replace("=", AppConstants.EMPTY_STRING);
                    byte[] communityPostBytes = Base64.decode(postId, Base64.DEFAULT);
                    postId = new String(communityPostBytes, AppConstants.UTF_8);
                    byte[] communityBytes = Base64.decode(communityId, Base64.DEFAULT);
                    communityId = new String(communityBytes, AppConstants.UTF_8);
                    typeOfFeed = AppConstants.POLL_URL_COM;
                }
                if (StringUtil.isNotNullOrEmptyString(communityId) && StringUtil.isNotNullOrEmptyString(postId)) {
                    Intent postIntent = new Intent(SheroesDeepLinkingActivity.this, PostDetailActivity.class);
                    postIntent.putExtra(AppConstants.COMMUNITY_ID, Long.parseLong(communityId));
                    postIntent.putExtra(FeedDetail.FEED_OBJ_ID, postId);
                    postIntent.putExtra(BaseActivity.KEY_FOR_DEEPLINK_DETAIL, typeOfFeed);
                    postIntent.putExtra(AppConstants.FROM_DEEPLINK, true);
                    postIntent.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, mFromNotification);
                    postIntent.putExtra(BaseActivity.SOURCE_SCREEN, mSource);
                    postIntent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    addShareLink(sourceIntent, postIntent);
                    startActivity(postIntent);
                } else {
                    homeActivityCall("");
                }
            } else {
                Intent into = new Intent(SheroesDeepLinkingActivity.this, CommunityDetailActivity.class);
                String tabKey = "";
                boolean isFromAdvertisement = false;
                if (sourceIntent != null && sourceIntent.getExtras() != null) {
                    tabKey = sourceIntent.getStringExtra(CommunityDetailActivity.TAB_KEY);
                    isFromAdvertisement = sourceIntent.getBooleanExtra(AppConstants.IS_FROM_ADVERTISEMENT, false);
                }
                into.putExtra(AppConstants.IS_FROM_ADVERTISEMENT, isFromAdvertisement);
                into.putExtra(CommunityDetailActivity.TAB_KEY, tabKey);

                int indexOfSecondBackSlace = AppUtils.findNthIndexOf(communityDetail, AppConstants.BACK_SLASH, 2);
                String communityId = communityDetail.substring(indexOfSecondBackSlace + 1, communityDetail.length());
                byte[] communityBytes = Base64.decode(communityId, Base64.DEFAULT);
                String newCommunityId = new String(communityBytes, AppConstants.UTF_8);
                into.putExtra(AppConstants.COMMUNITY_ID, newCommunityId);
                into.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, mFromNotification);
                into.putExtra(BaseActivity.SOURCE_SCREEN, mSource);
                into.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                addShareLink(sourceIntent, into);
                startActivity(into);
            }
            finish();
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
            homeActivityCall("");
        }
    }

    private void homeActivityCall(String fragmentName) {
        HomeFragment.PREVIOUS_SCREEN = SCREEN_LABEL;

        Intent into = new Intent(this, HomeActivity.class);
        into.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        into.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, mFromNotification);
        into.putExtra(BaseActivity.SOURCE_SCREEN, mSource);
        into.putExtra(OPEN_FRAGMENT, fragmentName);
        addSearchQuery(into, fragmentName);
        addShareLink(mIntent, into);
        startActivity(into);
        finish();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    protected void onPause() {
        HomeFragment.PREVIOUS_SCREEN = SCREEN_LABEL;
        super.onPause();
    }
}