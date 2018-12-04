package appliedlife.pvtltd.SHEROES.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.article.ArticleSubmissionRequest;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.community.BellNotificationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.ChallengePostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityTopPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRenderResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.WinnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MyCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostRatingRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCount;
import appliedlife.pvtltd.SHEROES.models.entities.imageUpload.UploadImageRequest;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer.NavigationDrawerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.poll.CreatePollRequest;
import appliedlife.pvtltd.SHEROES.models.entities.poll.DeletePollRequest;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollOptionRequestModel;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollType;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollVote;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.FollowersFollowingRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileUsersCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQSRequest;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.vernacular.LanguageUpdateRequest;
import appliedlife.pvtltd.SHEROES.usertagging.mentions.MentionSpan;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AppUtils {
    private static final String TAG = LogUtils.makeLogTag(AppUtils.class);
    private static AppUtils sInstance;

    public final Pattern URL_WITH_HTTP_ADDRESS_PATTERN = Pattern.compile("(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?");
    public final Pattern URL_START_WWW_ADDRESS_PATTERN = Pattern.compile("([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?");
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
            + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

    private String mDeviceId;

    private AppUtils() {
    }


    public static synchronized AppUtils getInstance() {
        if (sInstance == null) {
            sInstance = new AppUtils();
        }
        return sInstance;
    }

    /**
     * Use this function to get application mContext in any class
     *
     * @return Application mContext
     */
    public Context getApplicationContext() {
        return SheroesApplication.mContext;
    }


    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    public static int getWindowWidth(Context context) {
        int[] size = getWindowSize(context);
        return size[0];
    }

    public static int[] getWindowSize(Context context) {
        int screenWidth, screenHeight;
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Point point = new Point();
            display.getSize(point);
            screenWidth = point.x;
            screenHeight = point.y;
        } else {
            screenWidth = display.getWidth();
            screenHeight = display.getHeight();
        }

        return new int[]{screenWidth, screenHeight};
    }


    public static void hideKeyboard(View view, String TAG) {
        if (view == null) {
            return;
        }
        try {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            LogUtils.error(TAG, e);
        }
    }


    /**
     * The version number of this package, as specified by the manifest tag_item_ui_for_onboarding's
     * attribute.
     */
    public String getAppVersionName() {
        if (getPackageInfo(0) != null) {
            return getPackageInfo(0).versionName;
        } else {
            return "";
        }
    }

    public String getCloudMessaging() {
        if (getPackageInfo(0) != null) {
            return getPackageInfo(0).versionName;
        } else {
            return "";
        }
    }

    /**
     * Check for the internet availability.
     *
     * @return true if internet connection is available.
     */
    public boolean isNetworkAvailable() {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        boolean isNetworkAvailable = false;
        ConnectivityManager connMgr = (ConnectivityManager) SheroesApplication.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null) {
            isNetworkAvailable = networkInfo.isConnected();
            if (isNetworkAvailable) {
                LogUtils.info(TAG, "Network Type: " + networkInfo.getTypeName());
            } else {
                LogUtils.info(TAG, "Network State Reason: " + networkInfo.getReason());
            }
        }
        LogUtils.info(TAG, "Network Status: " + isNetworkAvailable);
        LogUtils.exit(TAG, LogUtils.getMethodName());
        return isNetworkAvailable;
    }

    /**
     * This function provides the unique device id(IMEI, MEID or ESN).
     *
     * @return device id string
     */
    public String getDeviceId() {
        try {
            if (mDeviceId == null) {
                mDeviceId = Settings.Secure.getString(SheroesApplication.mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } catch (Exception ex) {
            LogUtils.error(TAG, ex);
            return " ";
        }
        return mDeviceId;
    }


    /**
     * Retrieve overall information about an application package that is
     * installed on the system.
     *
     * @param flags Additional option flags. Use any combination to
     *              modify the data returned.
     * @return Returns a PackageInfo object containing information about the
     * package.
     */
    public PackageInfo getPackageInfo(int flags) {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        try {
            PackageManager packageManager = SheroesApplication.mContext.getPackageManager();
            return packageManager.getPackageInfo(SheroesApplication.mContext.getPackageName(), flags);
        } catch (PackageManager.NameNotFoundException ex) {
            LogUtils.error(TAG, ex.toString(), ex);
        } catch (Exception ex) {
            LogUtils.error(TAG, ex.toString(), ex);
        }
        LogUtils.exit(TAG, LogUtils.getMethodName());
        return null;
    }


    public boolean checkUrl(String url) {
        return URL_WITH_HTTP_ADDRESS_PATTERN.matcher(url).matches();
    }

    public boolean checkWWWUrl(String url) {
        return URL_START_WWW_ADDRESS_PATTERN.matcher(url).matches();
    }

    public boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public void email(String email, String subject, String text) {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(Intent.createChooser(intent, "Send email"));
        LogUtils.exit(TAG, LogUtils.getMethodName());
    }

    // Nikhil
    public static <T> T parseUsingGSONFromJSON(String is, String classPath) {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        T queryResult = null;

        if (is != null) {

            // Reader reader = new InputStreamReader(is);

            GsonBuilder gsonBuilder = new GsonBuilder();

            Gson gson = gsonBuilder.create();

            try {
                try {
                    queryResult = (T) gson.fromJson(is, Class.forName(classPath));
                    // queryResult = (T)
                    // gson.fromJson(reader,Class.forName(classPath));
                } catch (ClassNotFoundException e) {
                    LogUtils.error("G-PAR", " ClassNotFoundException " + e.toString(), e);
                }
            } catch (JsonSyntaxException e) {
                LogUtils.error("G-PAR", " JsonSyntaxException " + e.toString(), e);
                return null;
            } catch (JsonIOException e) {
                LogUtils.error("G-PAR", "JsonIOException " + e.toString(), e);
                return null;
            }
        }
        LogUtils.exit(TAG, LogUtils.getMethodName());
        return queryResult;
    }


    public float convertDpToPixel(float dp) {
        Resources resources = SheroesApplication.mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    /**
     * Request for Navigation drawer items
     */
    public NavigationDrawerRequest navigationOptionsRequestBuilder() {
        AppUtils appUtils = AppUtils.getInstance();
        NavigationDrawerRequest navigationDrawerRequest = new NavigationDrawerRequest();
        navigationDrawerRequest.setDisplayDefault(false);
        navigationDrawerRequest.setSource(AppConstants.SOURCE_NAME);
        navigationDrawerRequest.setAppVersion(appUtils.getAppVersionName());
        return navigationDrawerRequest;
    }

    public static String getStringContent(String fileName) {
        AssetManager assetManager = AppUtils.getInstance().getApplicationContext().getAssets();
        InputStream input;
        String content = "";

        try {
            input = assetManager.open(fileName);

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            content = new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * @return manufacturer of the device for tracking
     * if not available then Brand
     */
    public String getDeviceManufacturer() {
        String manufacturer = Build.MANUFACTURER;
        if (!StringUtil.isNotNullOrEmptyString(manufacturer)) {
            manufacturer = Build.BRAND;
        }
        return manufacturer;
    }

    /* Function to check if fragment UI is active*/
    public static boolean isFragmentUIActive(Fragment frag) {
        return frag != null && frag.getActivity() != null && frag.isAdded() && !frag.isDetached() && !frag.isRemoving();
    }

    /* Function to check if support fragment UI is active*/
    public static boolean isFragmentUIActive(androidx.fragment.app.Fragment frag) {
        return frag != null && frag.getActivity() != null && frag.isAdded() && !frag.isDetached() && !frag.isRemoving();
    }


    public static int findNthIndexOf(String str, String needle, int occurence) throws IndexOutOfBoundsException {
        int index = 0;
        Pattern p = Pattern.compile(needle, Pattern.MULTILINE);
        Matcher m = p.matcher(str);
        while (m.find()) {
            if (--occurence == 0) {
                index = m.start();
                break;
            }
        }
        return index;
    }

    public static LoginRequest loginRequestBuilder() {
        LoginRequest loginRequest = new LoginRequest();
        AppUtils appUtils = AppUtils.getInstance();
        //TODO:: check real data
        loginRequest.setAppVersion(appUtils.getAppVersionName());
        loginRequest.setAdvertisementid(appUtils.getDeviceManufacturer());
        loginRequest.setDeviceid(appUtils.getDeviceId());
        loginRequest.setDevicetype(AppConstants.SOURCE_NAME);
        return loginRequest;
    }

    public static RemoveMemberRequest removeMemberRequestBuilder(Long communityId, Long userid) {
        RemoveMemberRequest removeMemberRequest = new RemoveMemberRequest();
        AppUtils appUtils = AppUtils.getInstance();
        removeMemberRequest.setCommunityId(communityId);
        removeMemberRequest.setUserId(userid);
        removeMemberRequest.setDeviceUniqueId(appUtils.getDeviceId());
        removeMemberRequest.setCommunityId(communityId);
        removeMemberRequest.setAppVersion(appUtils.getAppVersionName());
        removeMemberRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        return removeMemberRequest;
    }

    public static FeedRequestPojo userCommunityPostRequestBuilder(String typeOfFeed, int pageNo, long communityId) {
        FeedRequestPojo feedRequestPojo = makeFeedRequest(typeOfFeed, pageNo);
        feedRequestPojo.setCommunityId(communityId);
        return feedRequestPojo;
    }

    public FeedRequestPojo userCommunityDetailRequestBuilder(String typeOfFeed, int pageNo, long communityId) {
        FeedRequestPojo feedRequestPojo = makeFeedRequest(typeOfFeed, pageNo);
        feedRequestPojo.setIdForFeedDetail(communityId);
        return feedRequestPojo;
    }

    public ProfileUsersCommunityRequest userCommunitiesRequestBuilder(int pageNo, long userId) {
        ProfileUsersCommunityRequest profileUsersCommunityRequest = new ProfileUsersCommunityRequest();
        profileUsersCommunityRequest.setAppVersion(getAppVersionName());
        profileUsersCommunityRequest.setDeviceUniqueId(getDeviceId());
        profileUsersCommunityRequest.setCloudMessagingId(getCloudMessaging());
        profileUsersCommunityRequest.setPageNo(pageNo);
        profileUsersCommunityRequest.setPageSize(AppConstants.PAGE_SIZE);
        profileUsersCommunityRequest.setUserId(userId);
        return profileUsersCommunityRequest;
    }

    public static NotificationReadCount notificationReadCountRequestBuilder(String screenName) {
        AppUtils appUtils = AppUtils.getInstance();
        NotificationReadCount notificationReadCount = new NotificationReadCount();
        notificationReadCount.setAppVersion(appUtils.getAppVersionName());
        notificationReadCount.setDeviceUniqueId(appUtils.getDeviceId());
        notificationReadCount.setLastScreenName(screenName);
        notificationReadCount.setScreenName(screenName);
        return notificationReadCount;
    }

    /**
     * Request for feed api
     */
    public FeedRequestPojo feedRequestBuilder(String typeOfFeed, int pageNo) {
        FeedRequestPojo feedRequestPojo = makeFeedRequest(typeOfFeed, pageNo);
        return feedRequestPojo;
    }

    public static MyCommunityRequest myCommunityRequestBuilder(String typeOfFeed, int pageNo) {
        AppUtils appUtils = AppUtils.getInstance();
        MyCommunityRequest myCommunityRequest = new MyCommunityRequest();
        myCommunityRequest.setAppVersion(appUtils.getAppVersionName());
        myCommunityRequest.setSource(AppConstants.SOURCE_NAME);
        myCommunityRequest.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        myCommunityRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        myCommunityRequest.setPageNo(pageNo);
        myCommunityRequest.setPageSize(AppConstants.PAGE_SIZE);
        myCommunityRequest.setSubType(typeOfFeed);
        return myCommunityRequest;
    }

    public static MyCommunityRequest myCommunityRequestBuilder(String typeOfFeed, int pageNo, int pageSize) {
        AppUtils appUtils = AppUtils.getInstance();
        MyCommunityRequest myCommunityRequest = new MyCommunityRequest();
        myCommunityRequest.setAppVersion(appUtils.getAppVersionName());
        myCommunityRequest.setSource(AppConstants.SOURCE_NAME);
        myCommunityRequest.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        myCommunityRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        myCommunityRequest.setPageNo(pageNo);
        myCommunityRequest.setPageSize(pageSize);
        myCommunityRequest.setSubType(typeOfFeed);
        return myCommunityRequest;
    }

    public PublicProfileListRequest pubicProfileRequestBuilder(int pageNo) {
        AppUtils appUtils = AppUtils.getInstance();
        PublicProfileListRequest publicProfileListRequest = new PublicProfileListRequest();
        publicProfileListRequest.setAppVersion(appUtils.getAppVersionName());
        publicProfileListRequest.setPageNo(pageNo);
        publicProfileListRequest.setPageSize(AppConstants.PAGE_SIZE);
        return publicProfileListRequest;
    }

    public FollowersFollowingRequest followerFollowingRequest(int pageNo, long userId, String followerFollowingType) {
        switch (followerFollowingType) {
            case AppConstants.FOLLOWED_CHAMPION:
                return followerFollowingRequestBuilder(pageNo, userId, false, false);
            case AppConstants.FOLLOWERS:
                return followerFollowingRequestBuilder(pageNo, userId, false, true);
            case AppConstants.FOLLOWING:
                return followerFollowingRequestBuilder(pageNo, userId, true, true);
            default:
                return null;
        }
    }

    private FollowersFollowingRequest followerFollowingRequestBuilder(int pageNo, long userId, boolean is_user, boolean is_listing) {
        AppUtils appUtils = AppUtils.getInstance();
        FollowersFollowingRequest followersFollowingRequest = new FollowersFollowingRequest();
        followersFollowingRequest.setPageNo(pageNo);
        followersFollowingRequest.setAppVersion(appUtils.getAppVersionName());
        followersFollowingRequest.setUserId(userId);
        followersFollowingRequest.setIsListing(is_listing);
        followersFollowingRequest.setIsUser(is_user);
        followersFollowingRequest.setPageSize(AppConstants.PAGE_SIZE);
        return followersFollowingRequest;
    }

    public FeedRequestPojo articleCategoryRequestBuilder(String typeOfFeed, int pageNo, List<Long> categoryIds) {
        FeedRequestPojo feedRequestPojo = makeFeedRequest(typeOfFeed, pageNo);
        feedRequestPojo.setCategoryIds(categoryIds);
        return feedRequestPojo;
    }

    public FeedRequestPojo winnerPostRequestBuilder(String typeOfFeed, Long userId, Long challengeId) {
        FeedRequestPojo feedRequestPojo = makeFeedRequest(typeOfFeed, 1);
        feedRequestPojo.setAutherId(userId);
        feedRequestPojo.setSourceEntityId(challengeId);
        return feedRequestPojo;
    }

    public FeedRequestPojo feedDetailRequestBuilder(String typeOfFeed, int pageNo, long idForDetail) {
        FeedRequestPojo feedRequestPojo = makeFeedRequest(typeOfFeed, pageNo);
        feedRequestPojo.setIdForFeedDetail(idForDetail);
        return feedRequestPojo;
    }

    public WinnerRequest winnerRequestBuilder(String contestId) {
        AppUtils appUtils = AppUtils.getInstance();
        WinnerRequest winnerRequest = new WinnerRequest();
        winnerRequest.setAppVersion(appUtils.getAppVersionName());
        winnerRequest.setDeviceUniqueId(appUtils.getDeviceId());
        winnerRequest.setScreenName("Feed");
        winnerRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        winnerRequest.challengeId = contestId;
        return winnerRequest;
    }

    public static FeedRequestPojo makeFeedRequest(String typeOfFeed, int pageNo) {
        AppUtils appUtils = AppUtils.getInstance();
        FeedRequestPojo feedRequestPojo = new FeedRequestPojo();
        feedRequestPojo.setAppVersion(appUtils.getAppVersionName());
        feedRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        feedRequestPojo.setScreenName("Feed");
        //TODO:: change rquest data
        feedRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        feedRequestPojo.setPageNo(pageNo);
        feedRequestPojo.setPageSize(AppConstants.PAGE_SIZE);
        feedRequestPojo.setSubType(typeOfFeed);
        return feedRequestPojo;
    }

    public static FeedRequestPojo makeFeedChallengeListRequest(String typeOfFeed, int pageNo) {
        AppUtils appUtils = AppUtils.getInstance();
        FeedRequestPojo feedRequestPojo = new FeedRequestPojo();
        feedRequestPojo.setAppVersion(appUtils.getAppVersionName());
        feedRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        feedRequestPojo.setScreenName("Feed");
        feedRequestPojo.setOnlyActive(true);
        feedRequestPojo.setAcceptedOrActive(true);
        //TODO:: change rquest data
        feedRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        feedRequestPojo.setPageNo(pageNo);
        feedRequestPojo.setPageSize(AppConstants.PAGE_SIZE);
        feedRequestPojo.setSubType(typeOfFeed);
        return feedRequestPojo;
    }


    public static DeleteCommunityPostRequest deleteCommunityPostRequest(long idOfEntityParticipant) {
        AppUtils appUtils = AppUtils.getInstance();
        DeleteCommunityPostRequest deleteCommunityPostRequest = new DeleteCommunityPostRequest();
        deleteCommunityPostRequest.setAppVersion(appUtils.getAppVersionName());
        deleteCommunityPostRequest.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        deleteCommunityPostRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        deleteCommunityPostRequest.setIdOfEntityOrParticipant(idOfEntityParticipant);
        return deleteCommunityPostRequest;
    }

    public static FeedRequestPojo getBookMarks(int pageNo) {
        AppUtils appUtils = AppUtils.getInstance();
        FeedRequestPojo feedRequestPojo = new FeedRequestPojo();
        feedRequestPojo.setAppVersion(appUtils.getAppVersionName());
        feedRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        feedRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        feedRequestPojo.setPageNo(pageNo);
        feedRequestPojo.setPageSize(AppConstants.PAGE_SIZE);
        return feedRequestPojo;
    }

    public UserSummaryRequest getUserProfileRequestBuilder(String subType, String type, String imageUrl) {
        AppUtils appUtils = AppUtils.getInstance();
        UserSummaryRequest userSummaryRequest = new UserSummaryRequest();
        userSummaryRequest.setAppVersion(appUtils.getAppVersionName());
        userSummaryRequest.setSource(AppConstants.SOURCE_NAME);
        userSummaryRequest.setType(type);
        userSummaryRequest.setSubType(subType);
        userSummaryRequest.setImageString(imageUrl);
        return userSummaryRequest;
    }

    public SearchUserDataRequest searchUserDataRequest(String query, Long communityId, Long postEntityId, Long postUserAuthorId, String context) {
        AppUtils appUtils = AppUtils.getInstance();
        SearchUserDataRequest searchUserDataRequest = new SearchUserDataRequest();
        searchUserDataRequest.setSearchNameOfUserForTagging(query);
        searchUserDataRequest.setCommunityId(communityId);
        searchUserDataRequest.setAppVersion(appUtils.getAppVersionName());
        searchUserDataRequest.setDeviceUniqueId(appUtils.getDeviceId());
        searchUserDataRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        searchUserDataRequest.setPostAuthorUserId(postUserAuthorId);
        searchUserDataRequest.setPostEntityId(postEntityId);
        searchUserDataRequest.setUserMentionContext(context);
        return searchUserDataRequest;
    }

    /**
     * Request for feed api
     */
    public static GetAllDataRequest onBoardingSearchRequestBuilder(String queryName, String masterDataTypeSkill) {
        AppUtils appUtils = AppUtils.getInstance();
        GetAllDataRequest getAllDataRequest = new GetAllDataRequest();
        getAllDataRequest.setAppVersion(appUtils.getAppVersionName());
        getAllDataRequest.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        getAllDataRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        getAllDataRequest.setQ(queryName);
        getAllDataRequest.setMasterDataType(masterDataTypeSkill);
        //TODO:: change rquest data
        getAllDataRequest.setSource(AppConstants.SOURCE_NAME);
        return getAllDataRequest;
    }

    /**
     * Request for feed api
     */
    public FeedRequestPojo searchRequestBuilder(String typeOfFeed, String queryName, int pageNo, String screenName, Long communityId, int pageSize) {
        AppUtils appUtils = AppUtils.getInstance();
        FeedRequestPojo feedRequestPojo = new FeedRequestPojo();
        feedRequestPojo.setAppVersion(appUtils.getAppVersionName());
        feedRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        feedRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        feedRequestPojo.setPageNo(pageNo);
        feedRequestPojo.setPageSize(pageSize);
        feedRequestPojo.setSubType(typeOfFeed);
        feedRequestPojo.setQuestion(queryName);
        feedRequestPojo.setCommunityId(communityId);
        feedRequestPojo.setScreenName(screenName);
        return feedRequestPojo;
    }

    /**
     * Request for feed api
     */
    public LikeRequestPojo likeRequestBuilder(long entityId, int reactionValue) {
        AppUtils appUtils = AppUtils.getInstance();
        LikeRequestPojo likeRequestPojo = new LikeRequestPojo();
        likeRequestPojo.setAppVersion(appUtils.getAppVersionName());
        likeRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        likeRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        likeRequestPojo.setEntityId(entityId);
        likeRequestPojo.setReactionValue(reactionValue);
        return likeRequestPojo;
    }

    public PollVote pollVoteRequestBuilder(Long pollId, Long pollOptionId) {
        AppUtils appUtils = AppUtils.getInstance();
        PollVote likeRequestPojo = new PollVote();
        likeRequestPojo.setAppVersion(appUtils.getAppVersionName());
        likeRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        likeRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        likeRequestPojo.setPollId(pollId);
        likeRequestPojo.setPollOptionId(pollOptionId);
        return likeRequestPojo;
    }

    public LikeRequestPojo likeRequestBuilder(long entityId, int reactionValue, long commentId) {
        AppUtils appUtils = AppUtils.getInstance();
        LikeRequestPojo likeRequestPojo = new LikeRequestPojo();
        likeRequestPojo.setAppVersion(appUtils.getAppVersionName());
        likeRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        likeRequestPojo.commentId = commentId;
        //TODO:: change rquest data
        likeRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        likeRequestPojo.setEntityId(entityId);
        likeRequestPojo.setReactionValue(reactionValue);
        return likeRequestPojo;
    }

    /**
     * Request for feed api
     */
    public LikeRequestPojo unLikeRequestBuilder(long entityId) {
        AppUtils appUtils = AppUtils.getInstance();
        LikeRequestPojo likeRequestPojo = new LikeRequestPojo();
        likeRequestPojo.setAppVersion(appUtils.getAppVersionName());
        likeRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        likeRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        likeRequestPojo.setEntityId(entityId);
        return likeRequestPojo;
    }

    public LikeRequestPojo unLikeRequestBuilder(long entityId, long commentId) {
        AppUtils appUtils = AppUtils.getInstance();
        LikeRequestPojo likeRequestPojo = new LikeRequestPojo();
        likeRequestPojo.setAppVersion(appUtils.getAppVersionName());
        likeRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        likeRequestPojo.commentId = commentId;
        //TODO:: change rquest data
        likeRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        likeRequestPojo.setEntityId(entityId);
        return likeRequestPojo;
    }

    public static CommentReactionRequestPojo getCommentRequestBuilder(long entityId, int pageNo) {
        AppUtils appUtils = AppUtils.getInstance();
        CommentReactionRequestPojo commentReactionRequestPojo = new CommentReactionRequestPojo();
        commentReactionRequestPojo.setAppVersion(appUtils.getAppVersionName());
        commentReactionRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        commentReactionRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        commentReactionRequestPojo.setPageNo(pageNo);
        //Page size for comment list
        commentReactionRequestPojo.setPageSize(AppConstants.PAGE_SIZE_FOR_COMMENTS);
        commentReactionRequestPojo.setEntityId(entityId);
        return commentReactionRequestPojo;
    }

    public static CommentReactionRequestPojo getCommentRequestBuilder(long entityId, int pageNo, int pageSize) {
        AppUtils appUtils = AppUtils.getInstance();
        CommentReactionRequestPojo commentReactionRequestPojo = new CommentReactionRequestPojo();
        commentReactionRequestPojo.setAppVersion(appUtils.getAppVersionName());
        commentReactionRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        commentReactionRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        commentReactionRequestPojo.setPageNo(pageNo);
        //Page size for comment list
        commentReactionRequestPojo.setPageSize(pageSize);
        commentReactionRequestPojo.setEntityId(entityId);
        return commentReactionRequestPojo;
    }

    public BellNotificationRequest getBellNotificationRequest() {
        AppUtils appUtils = AppUtils.getInstance();
        BellNotificationRequest bellNotificationRequest = new BellNotificationRequest();
        bellNotificationRequest.setAppVersion(appUtils.getAppVersionName());
        bellNotificationRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        bellNotificationRequest.setDeviceUniqueId(appUtils.getDeviceId());
        return bellNotificationRequest;
    }

    public static CommunityPostCreateRequest schedulePost(Long communityId, String createType, String description, Long mIdForEditPost, LinkRenderResponse linkRenderResponse, boolean hasPermission, String accessToken, String mDateTime, boolean hasMention, List<MentionSpan> userMentionList) {
        CommunityPostCreateRequest communityPostCreateRequest = createCommunityPostRequestBuilder(communityId, createType, description, mIdForEditPost, linkRenderResponse, hasPermission, accessToken, hasMention, userMentionList);
        communityPostCreateRequest.setSchedulePost(mDateTime);
        return communityPostCreateRequest;
    }

    public static Map createCommunityImagePostRequest(List<String> filePath) {
        LinkedHashMap<String, RequestBody> map = new LinkedHashMap<>();
        for (int i = 0; i < filePath.size(); i++) {
            File file = new File(filePath.get(i));
            RequestBody fileBody = RequestBody.create(MediaType.parse(FileUtil.getMimeType(filePath.get(i))), file);
            map.put(AppConstants.IMAGE_INITIAL_FILE_NAME + file.getName(), fileBody);
        }
        return map;
    }

    public static CommunityPostCreateRequest createCommunityPostRequestBuilder(Long communityId, String createType, String description, Long mIdForEditPost, LinkRenderResponse linkRenderResponse, boolean hasPermission, String accessToken, boolean hasMention, List<MentionSpan> userMentionList) {
        AppUtils appUtils = AppUtils.getInstance();
        CommunityPostCreateRequest communityPostCreateRequest = new CommunityPostCreateRequest();
        communityPostCreateRequest.setAppVersion(appUtils.getAppVersionName());
        communityPostCreateRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        communityPostCreateRequest.setDeviceUniqueId(appUtils.getDeviceId());
        communityPostCreateRequest.setCommunityId(communityId);
        communityPostCreateRequest.setCreatorType(createType);
        communityPostCreateRequest.setDescription(description);
        communityPostCreateRequest.setPostToFacebook(hasPermission);
        communityPostCreateRequest.setUserFbAccessToken(accessToken);
        communityPostCreateRequest.setId(mIdForEditPost);
        if (null != linkRenderResponse) {
            communityPostCreateRequest.setOgTitleS(linkRenderResponse.getOgTitleS());
            communityPostCreateRequest.setOgDescriptionS(linkRenderResponse.getOgDescriptionS());
            communityPostCreateRequest.setOgImageUrlS(linkRenderResponse.getOgImageUrlS());
            communityPostCreateRequest.setOgVideoLinkB(linkRenderResponse.isOgVideoLinkB());
            communityPostCreateRequest.setOgRequestedUrlS(linkRenderResponse.getOgRequestedUrlS());
        } else {
            communityPostCreateRequest.setOgTitleS(AppConstants.EMPTY_STRING);
            communityPostCreateRequest.setOgDescriptionS(AppConstants.EMPTY_STRING);
            communityPostCreateRequest.setOgImageUrlS(AppConstants.EMPTY_STRING);
            communityPostCreateRequest.setOgVideoLinkB(false);
            communityPostCreateRequest.setOgRequestedUrlS(AppConstants.EMPTY_STRING);
        }
        /*User tagging fields*/
        if (StringUtil.isNotEmptyCollection(userMentionList)) {
            communityPostCreateRequest.setHasMentions(true);
            communityPostCreateRequest.setUserMentionList(userMentionList);
        } else {
            communityPostCreateRequest.setHasMentions(false);
            communityPostCreateRequest.setUserMentionList(null);
        }
        return communityPostCreateRequest;
    }

    public CreatePollRequest createPollRequestBuilder(Long communityId, String createType, PollType pollType, String description, List<PollOptionRequestModel> pollOptionModelList, String startAt, String endAt) {
        AppUtils appUtils = AppUtils.getInstance();
        CreatePollRequest createPollRequest = new CreatePollRequest();
        createPollRequest.setAppVersion(appUtils.getAppVersionName());
        createPollRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        createPollRequest.setDeviceUniqueId(appUtils.getDeviceId());
        createPollRequest.setCommunityId(communityId);
        createPollRequest.setPollCreatorType(createType);
        createPollRequest.setPollType(pollType);
        createPollRequest.setDescription(description);
        createPollRequest.setPollOptions(pollOptionModelList);
        createPollRequest.setStartsAt(startAt);
        createPollRequest.setEndsAt(endAt);
        return createPollRequest;
    }
    public DeletePollRequest deletePollRequestBuilder(Long pollId) {
        AppUtils appUtils = AppUtils.getInstance();
        DeletePollRequest deletePollRequest = new DeletePollRequest();
        deletePollRequest.setAppVersion(appUtils.getAppVersionName());
        deletePollRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        deletePollRequest.setDeviceUniqueId(appUtils.getDeviceId());
        deletePollRequest.setPollId(pollId);

        return deletePollRequest;
    }
    public static ChallengePostCreateRequest createChallengePostRequestBuilder(String createType, int challengeId, String sourceType, String description, List<String> imag, LinkRenderResponse linkRenderResponse, boolean hasMention, List<MentionSpan> userMentionList) {
        AppUtils appUtils = AppUtils.getInstance();
        ChallengePostCreateRequest challengePostCreateRequest = new ChallengePostCreateRequest();
        challengePostCreateRequest.setAppVersion(appUtils.getAppVersionName());
        challengePostCreateRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        challengePostCreateRequest.setDeviceUniqueId(appUtils.getDeviceId());
        challengePostCreateRequest.setSourceEntityId(challengeId);
        challengePostCreateRequest.setmChallengeId(challengeId);
        challengePostCreateRequest.setmSourceType(sourceType);
        challengePostCreateRequest.setCommunityId(0L);
        challengePostCreateRequest.setAccepted(true);
        challengePostCreateRequest.setUpdated(true);
        challengePostCreateRequest.setActive(true);
        challengePostCreateRequest.setmCompletionPercent(100);
        challengePostCreateRequest.setCreatorType(createType);
        challengePostCreateRequest.setDescription(description);
        challengePostCreateRequest.setImages(imag);
        if (null != linkRenderResponse) {
            challengePostCreateRequest.setOgTitleS(linkRenderResponse.getOgTitleS());
            challengePostCreateRequest.setOgDescriptionS(linkRenderResponse.getOgDescriptionS());
            challengePostCreateRequest.setOgImageUrlS(linkRenderResponse.getOgImageUrlS());
            challengePostCreateRequest.setOgVideoLinkB(linkRenderResponse.isOgVideoLinkB());
            challengePostCreateRequest.setOgRequestedUrlS(linkRenderResponse.getOgRequestedUrlS());
        } else {
            challengePostCreateRequest.setOgTitleS(AppConstants.EMPTY_STRING);
            challengePostCreateRequest.setOgDescriptionS(AppConstants.EMPTY_STRING);
            challengePostCreateRequest.setOgImageUrlS(AppConstants.EMPTY_STRING);
            challengePostCreateRequest.setOgVideoLinkB(false);
            challengePostCreateRequest.setOgRequestedUrlS(AppConstants.EMPTY_STRING);
        }
        /*User tagging fields*/
        if (StringUtil.isNotEmptyCollection(userMentionList)) {
            challengePostCreateRequest.setHasMentions(true);
            challengePostCreateRequest.setUserMentionList(userMentionList);
        } else {
            challengePostCreateRequest.setHasMentions(false);
            challengePostCreateRequest.setUserMentionList(null);
        }
        return challengePostCreateRequest;
    }

    public LinkRequest linkRequestBuilder(String linkData) {
        AppUtils appUtils = AppUtils.getInstance();
        LinkRequest linkRequest = new LinkRequest();
        linkRequest.setAppVersion(appUtils.getAppVersionName());
        linkRequest.setSource(AppConstants.SOURCE_NAME);
        linkRequest.setLinkUrl(linkData);
        return linkRequest;
    }

    public static CommunityPostCreateRequest editCommunityPostRequestBuilder(Long communityId, String createType, String description, Long mIdForEditPost, List<Long> deletedImageId, LinkRenderResponse linkRenderResponse, boolean hasMention, List<MentionSpan> userMentionList) {
        AppUtils appUtils = AppUtils.getInstance();
        CommunityPostCreateRequest communityPostCreateRequest = new CommunityPostCreateRequest();
        communityPostCreateRequest.setAppVersion(appUtils.getAppVersionName());
        communityPostCreateRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        communityPostCreateRequest.setDeviceUniqueId(appUtils.getDeviceId());
        communityPostCreateRequest.setCommunityId(communityId);
        communityPostCreateRequest.setCreatorType(createType);
        communityPostCreateRequest.setDescription(description);
        communityPostCreateRequest.setId(mIdForEditPost);
        communityPostCreateRequest.setDeleteImagesIds(deletedImageId);
        if (null != linkRenderResponse) {
            communityPostCreateRequest.setOgTitleS(linkRenderResponse.getOgTitleS());
            communityPostCreateRequest.setOgDescriptionS(linkRenderResponse.getOgDescriptionS());
            communityPostCreateRequest.setOgImageUrlS(linkRenderResponse.getOgImageUrlS());
            communityPostCreateRequest.setOgVideoLinkB(linkRenderResponse.isOgVideoLinkB());
            communityPostCreateRequest.setOgRequestedUrlS(linkRenderResponse.getOgRequestedUrlS());
        } else {
            communityPostCreateRequest.setOgTitleS(AppConstants.EMPTY_STRING);
            communityPostCreateRequest.setOgDescriptionS(AppConstants.EMPTY_STRING);
            communityPostCreateRequest.setOgImageUrlS(AppConstants.EMPTY_STRING);
            communityPostCreateRequest.setOgVideoLinkB(false);
            communityPostCreateRequest.setOgRequestedUrlS(AppConstants.EMPTY_STRING);
        }
        /*User tagging fields*/
        if (StringUtil.isNotEmptyCollection(userMentionList)) {
            communityPostCreateRequest.setHasMentions(true);
            communityPostCreateRequest.setUserMentionList(userMentionList);
        } else {
            communityPostCreateRequest.setHasMentions(false);
            communityPostCreateRequest.setUserMentionList(null);
        }
        return communityPostCreateRequest;
    }

    public static CommunityTopPostRequest topCommunityPostRequestBuilder(Long communityId, String createType, String description, long id, boolean topPost) {
        AppUtils appUtils = AppUtils.getInstance();
        CommunityTopPostRequest communityTopPostRequest = new CommunityTopPostRequest();
        communityTopPostRequest.setAppVersion(appUtils.getAppVersionName());
        communityTopPostRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        communityTopPostRequest.setDeviceUniqueId(appUtils.getDeviceId());
        communityTopPostRequest.setCommunityId(communityId);
        communityTopPostRequest.setCreatorType(createType);
        communityTopPostRequest.setDescription(description);
        communityTopPostRequest.setTopPost(topPost);
        communityTopPostRequest.setId(id);
        return communityTopPostRequest;
    }

    public ApproveSpamPostRequest spamPostApprovedRequestBuilder(FeedDetail feedDetail, boolean isActive, boolean isSpam, boolean isApproved) {
        AppUtils appUtils = AppUtils.getInstance();
        ApproveSpamPostRequest approveSpamPostRequest = new ApproveSpamPostRequest();
        approveSpamPostRequest.setAppVersion(appUtils.getAppVersionName());
        approveSpamPostRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        approveSpamPostRequest.setDeviceUniqueId(appUtils.getDeviceId());
        approveSpamPostRequest.setApproved(isApproved);
        approveSpamPostRequest.setId(feedDetail.getIdOfEntityOrParticipant());
        approveSpamPostRequest.setActive(isActive);
        approveSpamPostRequest.setSpam(isSpam);
        return approveSpamPostRequest;
    }

    public ApproveSpamPostRequest spamCommentApprovedRequestBuilder(Comment comment, boolean isActive, boolean isSpam, boolean isApproved) {
        AppUtils appUtils = AppUtils.getInstance();
        ApproveSpamPostRequest approveSpamPostRequest = new ApproveSpamPostRequest();
        approveSpamPostRequest.setAppVersion(appUtils.getAppVersionName());
        approveSpamPostRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        approveSpamPostRequest.setDeviceUniqueId(appUtils.getDeviceId());
        approveSpamPostRequest.setApproved(isApproved);
        approveSpamPostRequest.setId(comment.getCommentsId());
        approveSpamPostRequest.setActive(isActive);
        approveSpamPostRequest.setSpam(isSpam);
        return approveSpamPostRequest;
    }

    public BookmarkRequestPojo bookMarkRequestBuilder(long entityId) {
        AppUtils appUtils = AppUtils.getInstance();
        BookmarkRequestPojo bookmarkRequestPojo = new BookmarkRequestPojo();
        bookmarkRequestPojo.setAppVersion(appUtils.getAppVersionName());
        bookmarkRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        bookmarkRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        bookmarkRequestPojo.setEntityId(entityId);
        return bookmarkRequestPojo;
    }


    public static CommentReactionRequestPojo postCommentRequestBuilder(long entityId, String userComment, boolean isAnonymous, boolean hasMention, List<MentionSpan> mentionSpanList) {
        AppUtils appUtils = AppUtils.getInstance();
        CommentReactionRequestPojo commentReactionRequestPojo = new CommentReactionRequestPojo();
        commentReactionRequestPojo.setAppVersion(appUtils.getAppVersionName());
        commentReactionRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        commentReactionRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        commentReactionRequestPojo.setUserComment(userComment);
        commentReactionRequestPojo.setIsAnonymous(isAnonymous);
        commentReactionRequestPojo.setEntityId(entityId);
        /*User mention*/
        if (StringUtil.isNotEmptyCollection(mentionSpanList)) {
            commentReactionRequestPojo.setHasMentions(true);
            commentReactionRequestPojo.setUserMentionList(mentionSpanList);
        } else {
            commentReactionRequestPojo.setHasMentions(false);
            commentReactionRequestPojo.setUserMentionList(null);
        }
        return commentReactionRequestPojo;
    }


    public static CommentReactionRequestPojo editCommentRequestBuilder(long entityId, String userComment, boolean isAnonymous, boolean isActive, long participationId, boolean hasMention, List<MentionSpan> mentionSpanList) {
        AppUtils appUtils = AppUtils.getInstance();
        CommentReactionRequestPojo commentReactionRequestPojo = new CommentReactionRequestPojo();
        commentReactionRequestPojo.setAppVersion(appUtils.getAppVersionName());
        commentReactionRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        commentReactionRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        commentReactionRequestPojo.setUserComment(userComment);
        commentReactionRequestPojo.setIsAnonymous(isAnonymous);
        commentReactionRequestPojo.setIsActive(isActive);
        commentReactionRequestPojo.setEntityId(entityId);
        commentReactionRequestPojo.setParticipationId(participationId);
        /*User mention*/
        if (StringUtil.isNotEmptyCollection(mentionSpanList)) {
            commentReactionRequestPojo.setHasMentions(true);
            commentReactionRequestPojo.setUserMentionList(mentionSpanList);
        } else {
            commentReactionRequestPojo.setHasMentions(false);
            commentReactionRequestPojo.setUserMentionList(null);
        }
        return commentReactionRequestPojo;
    }


    public static CommunityRequest communityRequestBuilder(List<Long> userId, long idOfEntityParticipant, String reasonToJoin) {
        AppUtils appUtils = AppUtils.getInstance();
        CommunityRequest communityRequest = new CommunityRequest();
        communityRequest.setUserId(userId);
        communityRequest.setCommunityId(idOfEntityParticipant);
        communityRequest.setAppVersion(appUtils.getAppVersionName());
        communityRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        communityRequest.setDeviceUniqueId(appUtils.getDeviceId());
        communityRequest.setLastScreenName(AppConstants.COMMUNITY_DETAIL);
        communityRequest.setScreenName(AppConstants.COMMUNITY_DETAIL);
        communityRequest.setReasonToJoin(reasonToJoin);
        return communityRequest;
    }


    public static HelplinePostQuestionRequest helplineQuestionBuilder(String chatQueryText) {
        HelplinePostQuestionRequest helplinePostQuestionRequest = new HelplinePostQuestionRequest();
        helplinePostQuestionRequest.setQuestion(chatQueryText);
        helplinePostQuestionRequest.setSource(AppConstants.SOURCE_NAME);
        return helplinePostQuestionRequest;
    }


    public static HelplineGetChatThreadRequest helplineGetChatThreadRequestBuilder(int pageNo) {
        HelplineGetChatThreadRequest helplineGetChatThreadRequest = new HelplineGetChatThreadRequest();
        helplineGetChatThreadRequest.setPageNo(pageNo);
        helplineGetChatThreadRequest.setPageSize(AppConstants.PAGE_SIZE_CHAT);
        return helplineGetChatThreadRequest;
    }

    public static FAQSRequest sheFAQSRequestBuilder() {
        FAQSRequest faqsRequest = new FAQSRequest();
        return faqsRequest;
    }

    public static ICCMemberRequest sheICCMemberListRequestBuilder() {
        ICCMemberRequest iccMemberRequest = new ICCMemberRequest();
        return iccMemberRequest;
    }

    public static void openChromeTab(Activity activity, Uri url) {
        CustomTabsIntent customTabsIntent =
                new CustomTabsIntent.Builder()
                        .setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                        .setShowTitle(true)
                        .enableUrlBarHiding()
                        .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            customTabsIntent.intent.putExtra(Intent.EXTRA_REFERRER,
                    Uri.parse(Intent.URI_ANDROID_APP_SCHEME + "//" + activity.getPackageName()));
        }
        customTabsIntent.launchUrl(activity, url);
    }

    public static void openChromeTabForce(Activity activity, Uri url) {
        String PACKAGE_NAME = "com.android.chrome";
        CustomTabsIntent customTabsIntent =
                new CustomTabsIntent.Builder()
                        .setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                        .setShowTitle(true)
                        .enableUrlBarHiding()
                        .build();

        PackageManager packageManager = activity.getPackageManager();
        List<ApplicationInfo> resolveInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);


        for (ApplicationInfo applicationInfo : resolveInfoList) {
            String packageName = applicationInfo.packageName;
            if (TextUtils.equals(packageName, PACKAGE_NAME)) {
                customTabsIntent.intent.setPackage(PACKAGE_NAME);
                break;
            }
        }
        customTabsIntent.launchUrl(activity, url);
    }

    public static boolean matchesWebsiteURLPattern(String sentence) {
        final Pattern pattern = Pattern.compile(AppConstants.WEB_LINK_PATTERN_REGEX,
                Pattern.MULTILINE | Pattern.DOTALL);
        Matcher m = pattern.matcher(sentence);
        return m.find();
    }

    public ArticleSubmissionRequest articleDraftAddEditRequest(Long articleId, String articleTitle, String articleBody, List<Long> tagList, List<Long> deletedArticleTagList, ArticleSolrObj articleSolrObj, String coverImageUrl, boolean isPublish) {
        AppUtils appUtils = AppUtils.getInstance();
        ArticleSubmissionRequest articleSubmissionRequest = new ArticleSubmissionRequest();
        articleSubmissionRequest.setAppVersion(appUtils.getAppVersionName());
        articleSubmissionRequest.setDeviceUniqueId(appUtils.getDeviceId());
        articleSubmissionRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        if (null != articleId) {
            articleSubmissionRequest.articleId = articleId;
            if (StringUtil.isNotEmptyCollection(articleSolrObj.getTag_ids())) {
                for (long oldArticleTag : articleSolrObj.getTag_ids()) {
                    boolean isMatch = false;
                    for (long deletedArticleTag : deletedArticleTagList) {
                        if (oldArticleTag == deletedArticleTag) {
                            isMatch = true;
                            break;
                        } else {
                            isMatch = false;
                        }
                    }
                    if (isMatch) {
                        deletedArticleTagList.add(oldArticleTag);
                    }
                }
                articleSubmissionRequest.deletedTagIds = deletedArticleTagList;
            }
        }
        articleSubmissionRequest.isPublish = isPublish;
        articleSubmissionRequest.storyTitle = articleTitle;
        articleSubmissionRequest.storyContent = articleBody;
        articleSubmissionRequest.tagIds = tagList;
        articleSubmissionRequest.coverImageUrl = coverImageUrl;
        return articleSubmissionRequest;
    }

    public ArticleSubmissionRequest articleDeleteRequest(ArticleSolrObj articleSolrObj) {
        AppUtils appUtils = AppUtils.getInstance();
        ArticleSubmissionRequest articleSubmissionRequest = new ArticleSubmissionRequest();
        articleSubmissionRequest.setAppVersion(appUtils.getAppVersionName());
        articleSubmissionRequest.setDeviceUniqueId(appUtils.getDeviceId());
        articleSubmissionRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        articleSubmissionRequest.articleId = articleSolrObj.getIdOfEntityOrParticipant();
        articleSubmissionRequest.storyTitle = articleSolrObj.getNameOrTitle();
        articleSubmissionRequest.storyContent = articleSolrObj.getDescription();

        return articleSubmissionRequest;
    }
    public UploadImageRequest uploadImageRequestBuilder(String  encodedImage) {
        UploadImageRequest uploadImageRequest = new UploadImageRequest();
        uploadImageRequest.images = new ArrayList<>();
        uploadImageRequest.images.add(encodedImage);
        return uploadImageRequest;
    }

    public LanguageUpdateRequest updateSelectedLanguageRequestBuilder(String language, Long userId) {
        LanguageUpdateRequest languageUpdateRequest = new LanguageUpdateRequest();
        languageUpdateRequest.language = language;
        languageUpdateRequest.userId = userId;
        return languageUpdateRequest;
    }

    public HelplinePostRatingRequest helpLinePostRatingRequestBuilder(boolean isRating, int answerId) {
        HelplinePostRatingRequest helplinePostRatingRequest = new HelplinePostRatingRequest();
        helplinePostRatingRequest.setRating(isRating);
        helplinePostRatingRequest.setAnswerId(answerId);
        return helplinePostRatingRequest;
    }
}
