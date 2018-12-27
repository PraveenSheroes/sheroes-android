package appliedlife.pvtltd.SHEROES.utils;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: App constants stores all app messages.
 */
public class AppConstants {
    public static final String YOUTUBE_DEVELOPER_KEY = "AIzaSyBb6FQ0snY-RF9QjGV_RdGSgpWtclRMSvk";
    public static final String PLAY_STORE_ID_URL = "market://details?id=";
    public static final String PLAY_STORE_URL_PATH = "https://play.google.com/store/apps/details?id=";
    //\b((https?|Https?|ftp)?)(:?)(\/\/?|)(www)[-a-zA-Z0-9+&@#%?=~_|!:,.;/]*[-a-zA-Z0-9+&@#%=~_|]
    public static final String WEB_LINK_PATTERN_REGEX = "\\b(https?|Https?|ftp)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    // YouTube video id
    public static final String YOUTUBE_VIDEO_CODE = "youtu.be";
    public static final String MOBILE_YOUTUBE_VIDEO_CODE = "m.youtube";
    public static final int APP_INTRO = 34;
    public static final int ANDROID_SDK_24 = 24;
    public static final int READ_TIME_OUT = 180;
    public static final int CONNECTION_TIME_OUT = 60;
    public static final int FEED_FIRST_TIME = 10;
    public static final int CREATE_POST = 101;
    public static final int SECONDS_IN_MIN = 60;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int HOURS_IN_DAY = 24;
    public static final int CACHE_VALID_DAYS = 14;
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String SPACE = " ";
    public static final String SHEROES_AUTH_TOKEN = "sheroes_auth_token";
    public static final String ALL_COMMUNITY_LIST = "all community list";
    public static final String MASTER_DATA = "master_data";
    public static final String INSTALL_UPDATE = "install_update";
    public static final String SHEROES_PREFERENCE = "sheroes_preference";
    public static final String HTTP_401_UNAUTHORIZED_ERROR = "HTTP 401 ";
    public static final String HTTP_401_UNAUTHORIZED = "HTTP 401 Unauthorized";
    public static final String INAVLID_DATA = "INVALID_DATA";
    public static final String IS_DEACTIVATED = "IS_DEACTIVATED";
    public static final String ERROR = "error";
    public static final String EXTRA_IMAGE = "extraImage";
    public static final String CHECK_NETWORK_CONNECTION = "Oops! There is some connectivity issue. Please check your internet connection.";
    public static final String VIEW_NOT_ATTACHED_EXCEPTION = "Please call SheroesPresenter.attachView(BaseMvpView) before" + " requesting data to the SheroesPresenter";
    public static final String EXCEPTION_MUST_IMPLEMENT = "Exception while implement listner with in :";
    public static final String CASE_NOT_HANDLED = "Case not handled on : ";
    public static final String EMPTY_STRING = "";
    public static final String COMMA = ",";
    public static final String AT_THE_RATE_OF = "@";
    public static final String SHARED_PREFS = "SHARED_PREFS";
    public static final String ARTICLE_CATEGORY_SPINNER_FRAGMENT = "spinner_fragment";
    public static final String ANDROID_VIEW_PAGER = "android:switcher:";
    public static final String COLON = ":";
    public static final String DOTS = "...";
    public static final String DOT = " • ";
    public static final String USER_YOU_TUBE = "youtube.com/user";
    public static final String CHANNEL_YOU_TUBE = "youtube.com/channel";
    public static final String SHARE_MENU_TYPE = "text/plain";
    public static final String FOR_ALL = "All";
    public static final String FEED_SUB_TYPE = "F";
    public static final String USER_SUB_TYPE = "U";
    public static final String CAROUSEL_SUB_TYPE = "W";
    public static final String LEADER_SUB_TYPE = "LB";
    public static final String CHALLENGE_SUB_TYPE_NEW = "H";
    public static final String IMAGE_SUBTYPE = "I";
    public static final String FEED_SCREEN = "feed";
    public static final String COMMUNITIES_ONBOARDING_SCREEN = "community_onboarding";
    public static final String SOURCE_NAME = "android";
    public static final String UTF_8 = "UTF-8";
    public static final String USER = "USER";
    public static final String FEED_COMMUNITY = "C";
    public static final String FEED_ARTICLE = "A";
    public static final String FEED_COMMUNITY_POST = "P";
    public static final String FEATURED_COMMUNITY = "T";
    public static final String FEED_POLL = "PL";
    public static final String BOOKMARKS = "bookmarks";
    public static final String COMMENT_REACTION_FRAGMENT = "comment_reaction_fragment";
    public static final String HOME_FRAGMENT = "home";
    public static final String ON_BOARDING_COMMUNITIES = "on_boarding_communities";
    public static final String MENTOR_LISTING = "mentor_listing";
    public static final String BELL_NOTIFICATION_LISTING = "bell_notification_listing";
    public static final String PROFILE_COMMUNITY_LISTING = "profile_community_listing";
    public static final String PROFILE_CHAMPION = "profile_champion";
    public static final String SPAM_LIST_FRAGMENT = "spam_list_fragment";
    public static final String MASTER_SKILL = "master_data_skill";
    public static final int REQUEST_CODE_FOR_LOCATION = 1401;
    public static final int REQUEST_CODE_FOR_ARTICLE_DETAIL = 1001;
    public static final int PROFILE_NOTIFICATION_ID = -1;
    public static final int REQUEST_CODE_FOR_CHALLENGE_DETAIL = 1901;
    public static final int REQUEST_CODE_FOR_POST_DETAIL = 1902;
    public static final int REQUEST_CODE_FOR_COMMUNITY_DETAIL = 2001;
    public static final int REQUEST_CODE_FOR_PROFILE_DETAIL = 2003;
    public static final int REQUEST_CODE_FOR_FACEBOOK = 4001;
    public static final int REQUEST_CODE_FOR_COMMUNITY_POST = 5001;
    public static final int REQUEST_CODE_FOR_ADDRESS = 50011;
    public static final int REQUEST_CODE_FOR_GALLERY = 9001;
    public static final int REQUEST_CODE_FOR_CAMERA = 1101;
    public static final int RESULT_CODE_FOR_DEACTIVATION = 10007;
    public static final int RESULT_CODE_FOR_PROFILE_FOLLOWED = 10009;
    public static final int REQUEST_CODE_FOR_EDIT_PROFILE = 1002;
    public static final int REQUEST_CODE_FOR_COMMUNITY_LISTING = 1003;
    public static final int REQUEST_CODE_FOR_USER_LISTING = 1009;
    public static final int REQUEST_CODE_FOR_IMAGE_CROPPING = 1201;
    public static final int REQUEST_CODE_FOR_GOOGLE_PLUS = 1301;
    public static final int REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL = 1501;
    public static final int REQUEST_CODE_FOR_INVITE_FRIEND = 1601;
    public static final int REQUEST_CODE_FOR_SELF_PROFILE_DETAIL = 1502;
    public static final int REQUEST_CODE_FOR_USER_PROFILE_DETAIL = 1600;
    public static final int REQUEST_CODE_CHAMPION_TITLE = 1503;
    public static final int REQUEST_CODE_FOR_LAST_COMMENT_USER_DETAIL = 1505;
    public static final int REQUEST_CODE_FOR_LAST_COMMENT_FROM_ARTICLE = 1506;
    public static final int REQUEST_CODE_FOR_SEARCH = 1406;
    public static final String GROWTH_PUBLIC_PROFILE = "growth_public_profile";
    public static final String HELPLINE_FRAGMENT = "helpline";

    //Screen constants
    public static final String INVITE_FRIENDS = "Invite Friends";
    public static final String FEED_HEADER = "FEED_HEADER";
    public static final String LOG_OUT_URL = "/users/logout";
    public static final String DRAWER_NAVIGATION = "Drawer navigation";
    public static final String RIGHT_SWIPE_NAVIGATION = "Right Swipe Navigation";
    public static final String HELPLINE = "helpline";
    public static final String FEATURE_FRAGMENT = "feature";
    public static final String MY_COMMUNITIES_DRAWER = "my_communities_drawer";
    public static final String MY_COMMUNITIES_FRAGMENT = "my_communities";
    public static final String COMMUNITY_DEATIL_DRAWER = "community_detail_drawer";
    public static final String ARTICLE_FRAGMENT = "article";
    public static final String COMMUNITY_POST_FRAGMENT = "community_post_fragment";
    public static final String USER_COMMUNITY_POST_FRAGMENT = "user_community_post_fragment";
    public static final String QA_POST_FRAGMENT = "question_answer_fragment";
    public static final String INVITE_MEMBER = "invite_member";
    public static final String APP_INTRO_SUB_TYPE = "APP_INTRO_TYPE";
    public static final String ONCE_WELCOME = "ONCE_WELCOME";
    public static final String FEED_PROGRESS_BAR = "FEED_PROGRESS_BAR";
    public static final String HOME_FEED_HEADER = "HEADER";
    public static final String TYPE_EMPTY_VIEW = "empty_view";
    public static final String NO_COMMUNITIES = "NO_COMMUNITIES";
    public static final String COMMUNITIES_DETAIL = "community_detail";
    public static final String IMAGE = "Img";
    public static final String FOLLOWED_CHAMPION = "FOLLOWED_CHAMPIONS";
    public static final String FOLLOWED_CHAMPION_LABEL = "Followed Champions Screen";
    public static final String FOLLOWERS = "FOLLOWERS";
    public static final String FOLLOWING = "FOLLOWING";
    public static final String HIDE_SPLASH_THEME = "hide_splash_theme";
    public static final String GENDER_INPUT_DIALOG = "GenderInputDialog";
    public static final String SHARE = "Share";
    public static final String WEB_URL_FRAGMENT = "web_url";
    public static final String LEADERBOARD_SCREEN = "Leaderboard Screen";
    public static final String USER_FOLLOWED_DETAIL = "USER_FOLLOWED_DETAIL";
    public static final String EMAIL = "email";
    public static final String BACK_SLASH = "/";
    public static final String ARTICLE_DETAIL = "article_detail";
    public static final String MENTOR_DETAIL = "mentor_detail";
    public static final String COMMUNITY_DETAIL = "community_detail";
    public static final String CHALLENGE_GRATIFICATION = "challenge_gratification";
    public static final String SUCCESS = "SUCCESS";
    public static final String MARK_AS_SPAM = "Reported as spam";
    public static final String FACEBOOK_VERIFICATION = "Your account is not facebook verified please click below continue with facebook";
    public static final String FAILED = "FAILED";
    public static final String INVALID = "INVALID";
    public static final long SAVED_DAYS_TIME = 2246400000l;
    public static final int COMMENT_EDIT = 1;
    public static final int COMMENT_DELETE = 2;
    public static final int ONE_CONSTANT = 1;
    public static final int TWO_CONSTANT = 2;
    public static final int THREE_CONSTANT = 3;
    public static final int FOURTH_CONSTANT = 4;
    public static final int PAGE_SIZE = 10;
    public static final int PAGE_SIZE_FOR_COMMENTS = 25;
    public static final int PAGE_SIZE_CHAT = 20;
    public static final int INVITE_PAGE_SIZE = 200;
    public static final int NO_REACTION_CONSTANT = 0;
    public static final int HEART_REACTION_CONSTANT = 10;
    public static final int EMOJI_FIRST_REACTION_CONSTANT = 20;
    public static final int EMOJI_SECOND_REACTION_CONSTANT = 30;
    public static final int EMOJI_THIRD_REACTION_CONSTANT = 40;
    public static final int EMOJI_FOURTH_REACTION_CONSTANT = 50;
    public static final int SEARCH_CONSTANT_DELAY = 500;
    public static final int NOT_TIME = 50000;
    public static final String EQUAL_SIGN = "=";
    public static final String AND_SIGN = "&";

    //User id's eg: Admin,Champion etc
    public static final int CHAMPION_TYPE_ID = 7;
    public static final int ADMIN_TYPE_ID = 2;
    public static final int COMMUNITY_MODERATOR_TYPE = 13;
    public static final int USER_POST = 14;
    public static final int COMMUNITY_POST = 15;
    public static final int COMMUNITY_POLL_ADMIN = 18;
    public static final int SHEROES_EVENT_ID = 299;
    public static final String HOME_USER_NAME_PREF = "HOME_USERANAME";
    public static final String NOTIFICATION_SESSION_SHARE_PREF = "BELL_NOTIFICATION_SESSION";
    public static final String NOTIFICATION_SHARE_PREF = "BELL_NOTIFICATION";
    public static final String MALE_ERROR_SHARE_PREF = "MALE_USER_ERROR";
    public static final String GUIDELINE_SHARE_PREF = "GUIDELINE";
    public static final String NEW_TAG_FOR_RIGHT_SWIP = "NEW_TAG";
    public static final String APP_REVIEW_PLAY_STORE = "REVIEW_PLAY_STORE";
    public static final String FOLLOWER_SHARE_PREF = "FOLLOW_USER";
    public static final String PICTURE_SHARE_PREF = "PICTURE_SHARE_PREF";
    public static final String PICTURE_SHARE_SESSION_PREF = "PICTURE_SHARE_SESSION_PREF";
    public static final String INVITE_FRIEND_SESSION_PREF = "INVITE_FRIEND_SESSION_PREF";
    public static final String INVITE_FRIEND_PREF = "INVITE_FRIEND_PREF";
    public static final String ARTICLE_SHARE_SESSION_PREF = "ARTICLE_SHARE_SESSION_PREF";
    public static final String ARTICLE_SHARE_PREF = "ARTICLE_SHARE_PREF";
    public static final String HEADER_PROFILE_SESSION_PREF = "HEADER_PROFILE_SESSION_PREF";
    public static final String HEADER_PROFILE_PREF = "HEADER_PROFILE_PREF";
    public static final String PROFILE_OFFER_PREF = "PROFILE_OFFER_PREF";
    public static final String CONTACT_SYNC_TIME_PREF = "CONTACT_SYNC";
    public static final String IS_FROM_ADVERTISEMENT = "IS_FROM_ADVERTISEMENT";
    public static final String ADS_DEEP_LINK_URL = "ADS_DEEP_LINK";
    public static final String CREATE_FEED_POST = "CREATE_FEED_POST";

    /*Master data keys for pages*/
    public static final String MASTER_DATA_ARTICLE_KEY = "ARTICLE_CATEGORY";//article category drop down

    /*Get all data keys*/
    public static final String MASTER_DATA_POPULAR_CATEGORY = "POPULAR"; // on second map lavele key
    public static final String ERROR_OCCUR = "Some error occured. Press back and try again.";

    //Community
    public static final String NAV_DRAWER_FILE_NAME = "nav_items";
    public static final String NAV_DRAWER_SHE_FILE_NAME = "she_nav_items";
    public static final String SPAM_REASONS_FILE = "spam_reasons";
    public static final String DEACTIVATE_REASONS_FILE = "deactivate_user_reasons";
    public static final String OPEN_COMMUNITY = "open community for join";
    public static final String STRING = SOURCE_NAME;
    public static final String SUMMARY = "SUMMARY";
    public static final String USER_SUMMARY_SERVICE = "USER_SUMMARY_SERVICE";
    public static final String WEB_BROWSER_MASSAGE = "Please select a web browser";
    public static final String COMMUNITY_POST_URL_COM = "https://sheroes.com/community-post";
    public static final String COMMUNITY_POST_URL = "https://sheroes.in/community-post";
    public static final String INVITE_FRIEND_URL = "https://sheroes.in/invite-friends";
    public static final String INVITE_FRIEND_URL_COM = "https://sheroes.com/invite-friends";
    public static final String CHAMPION_URL = "https://sheroes.in/champions";
    public static final String CHAMPION_URL_COM = "https://sheroes.com/champions";
    public static final String ARTICLE_URL = "https://sheroes.in/articles";
    public static final String SEARCH_URL = "https://sheroes.in/search";
    public static final String SEARCH_URL_COM = "https://sheroes.com/search";
    public static final String ARTICLE_URL_COM = "https://sheroes.com/articles";
    public static final String ARTICLE_CATEGORY_URL_COM = "https://sheroes.com/articles/category";
    public static final String ARTICLE_CATEGORY_URL_IN = "https://sheroes.in/articles/category";
    public static final String STORIES_URL = "https://sheroes.in/stories";
    public static final String STORIES_URL_COM = "https://sheroes.com/stories";
    public static final String COMMUNITY_URL = "https://sheroes.in/communities";
    public static final String COMMUNITY_URL_COM = "https://sheroes.com/communities";
    public static final String POLL_URL_COM = "https://sheroes.com/communities/poll";
    public static final String USER_PROFILE_URL = "https://sheroes.in/users/";
    public static final String SELF_USER_PROFILE_URL = "https://sheroes.com/users/edit_profile";
    public static final String USER_PROFILE_URL_COM = "https://sheroes.com/users/";
    public static final String USER_URL = "https://sheroes.in/users";
    public static final String USER_URL_COM = "https://sheroes.com/users";
    public static final String CHALLENGE_URL = "https://sheroes.in/feed";
    public static final String WRITE_STORY_URL = "https://sheroes.in/write_story";
    public static final String WRITE_STORY_URL_COM = "https://sheroes.com/write_story";
    public static final String MY_STORY_URL = "https://sheroes.in/my_story";
    public static final String MY_STORY_URL_COM = "https://sheroes.com/my_story";
    public static final String CHALLENGE_NEW_URL = "https://sheroes.in/sheroes-challenge";
    public static final String CHALLENGE_NEW_URL_COM = "https://sheroes.com/sheroes-challenge";
    public static final String MY_CHALLENGE_NEW_URL = "https://sheroes.in/my-challenges";
    public static final String MY_CHALLENGE_NEW_URL_COM = "https://sheroes.com/my-challenges";
    public static final String COLLECTION_NEW_URL = "https://sheroes.in/app-collections";
    public static final String COLLECTION_NEW_URL_COM = "https://sheroes.com/app-collections";
    public static final String SELECT_LANGUAGE_URL_COM = "https://sheroes.com/language_selection";

    /* Create post deeplink */
    public static final String CREATE_POST_URL = "https://sheroes.in/create-post";
    public static final String CREATE_POST_URL_COM = "https://sheroes.com/create-post";
    public static final String ID_FOR_CREATE_POST_ENTITY = "entity_id";
    public static final String CREATE_POST_ENTITY_NAME = "entity_name";
    public static final String CREATE_POST_REQUEST_FOR = "create_post_request_for";
    public static final String IS_MY_ENTITY = "is_my_entity";
    public static final String PREFILL_TEXT = "prefill_text";
    public static final String IS_CHALLENGE_TYPE = "is_challenge_type";
    public static final String CHALLENGE_AUTHOR_TYPE = "challenge_author_type";
    public static final String CHALLENGE_URL_COM = "https://sheroes.com/feed";
    public static final String FAQ_URL = "https://sheroes.com/faq";
    public static final String FAQ_URL_COM = "https://sheroes.com/faq";
    public static final String ICC_MEMBERS_URL = "https://sheroes.in/icc-members";
    public static final String ICC_MEMBERS_URL_COM = "https://sheroes.com/icc-members";
    public static final String ARTICLE_ID = "article_id";
    public static final String ARTICLE_CATEGORY_ID = "article_category_id";
    public static final String CHAMPION_ID = "champion_id";
    public static final String IS_CHAMPION_ID = "is_mentor";
    public static final String IS_SELF_PROFILE = "is_self_profile";
    public static final String COMMUNITY_ID = "community_id";
    public static final String FROM_DEEPLINK = "from_deep_link";
    public static final String CHALLENGE_ID = "challenge_id";
    public static final String END_POINT_URL = "end_point_url";
    public static final String COLLECTION_VIEW_TYPE = "view_type";
    public static final String GRID_VIEW_TYPE = "grid";
    public static final String TOOLBAR_TITTE = "toolbar_title";
    public static final String SCREEN_NAME = "screen_name";
    public static final String SEARCH_TEXT = "search_text";
    public static final String SEARCH_CATEGORY = "search_category";
    public static final String NEXT_TOKEN = "next_token";

    //Clever tap notification keys
    public static final String CLEVER_TAP_IS_PRESENT = "wzrk_pn";
    public static final String CLEVER_TAP_TITLE = "nt";
    public static final String CLEVER_TAP_BODY = "nm";
    public static final String CLEVER_TAP_DEEP_LINK_URL = "wzrk_dl";
    public static final String MESSAGE = "message";
    public static final String NOTIFICATION_ID = "notification_id";
    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final String DEEP_LINK_URL = "deep_link_url";
    public static final String OPEN_IN_WEBVIEW = "open_in_webview";
    public static final String SHEROES = "SHEROES";
    public static final String TICKER = "ticker";
    public static final String TWITTER_SHARE = "com.twitter.android";
    public static final String TWITTER_SHARE_VIA_BROWSER = "https://twitter.com/intent/tweet?text=";
    public static final String FACEBOOK_SHARE = "com.facebook.katana";
    public static final String FACEBOOK_SHARE_VIA_BROSWER = "https://www.facebook.com/sharer/sharer.php?u=";
    public static final String JPG_FORMATE = ".jpg";
    public static final String HELPLINE_SUB_TYPE_QUESTION = "Q";
    public static final int REQ_CODE_SPEECH_INPUT = 100;
    public static final String GOOGLE_PLUS = "Google plus";
    public static final String HELPLINE_URL = "https://sheroes.in/helpline";
    public static final String HELPLINE_URL_COM = "https://sheroes.com/helpline";
    public static final String HELPLINE_CHAT = "helpline_chat";
    public static final String EMAIL_VERIFICATION = "email_verification";
    public static final String PROFILE_PIC_TYPE = "PROFILE_PHOTO";
    public static final String PROFILE_PIC_SUB_TYPE = "USER_PROFILE_PHOTO_SERVICE";
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 79;
    public static final String PUBLIC_PROFILE = "public profile";
    public static final long ASKED_QUESTION_TO_MENTOR = 3;
    public static final String orgUserType = "organisations_test_user";
    public static final String EXTERNAL_STORAGE_FOLDER_NAME = "Sheroes Care";
    public static final String PRIVATE_FOLDER_NAME = "Sheroes";
    public static final String webstyle = "@font-face {font-family: MyFont;src: url(\"fonts/RobotoSlab-Regular.ttf\")}body{font-family: MyFont !important;background-color:#fafafa ;font-size:0.987em ;font-weight:120;color: #3c3c3c ;line-height: 160%;} a{text-decoration:none!important;} strong {font-family: MyFont !important;font-size:0.987em ;}span {font-family: MyFont !important;font-size: 0.987em !important;}img{display: inline;height: auto !important;max-width: 100% ;}";
    public static final String javascriptcode = "<script type=\"text/javascript\" src=\"jquery.js\"></script> <script type=\"text/javascript\" src=\"jquerylazyload.js\"></script> <script type=\"text/javascript\" language=\"javascript\"> /*source: http://www.appelsiini.net/projects/lazyload*/ function initials() { loadVideo(); convertImages(); loadImages(); } function loadImages() { $(\"img.lazy\").lazyload({ effect: \"fadeIn\" }); $(\"img.lazy\").click(function() { image.openImageActivity($(this).attr(\"data-original\")); }); } function loadVideo() { var n, v = document.getElementsByClassName(\"youtube\"); var l = v.length; for (n = 0; n < l; n++) { var iframe = document.createElement(\"iframe\"); iframe.setAttribute(\"src\", \"https://www.youtube.com/embed/\" + v[n].dataset.id + \"?rel=0&fs=1\"); iframe.setAttribute(\"frameborder\", \"0\"); iframe.setAttribute(\"width\", \"100%\"); iframe.setAttribute(\"id\", \"player\"); iframe.setAttribute(\"height\", \"240\"); iframe.setAttribute(\"allowfullscreen\", \"1\"); while (v[n].firstChild) { v[n].removeChild(v[n].firstChild); } v[n].appendChild(iframe); } } function convertImages() { var n, v = document.getElementsByTagName(\"img\"); var l = v.length; for (n = 0; n < l; n++) { v[n].setAttribute(\"data-original\", v[n].src); v[n].setAttribute(\"src\", \"placeholder.png\"); v[n].setAttribute(\"class\", \"lazy\"); } } </script>";
    public static final String APP_NAME = "Sheroes";
    public static final String PLUS = "+";

    //Tags - for maintaining the Nav selection of current class
    public static final String NAV_ARTICLES = "Articles";
    public static final String NAV_CHALLENGE = "My Challenges";
    public static final String NAV_ASK_SHEROES = "Ask Sheroes";
    public static final String NAV_ICC_MEMBERS = "ICC Members";
    public static final String NAV_FAQ = "FAQ";
    public static final String SELECTED_MENU_NAME = "SELECTED_MENU_NAME";
    public static final String PROFILE_FOLLOWED_CHAMPION = "Profile Followed Champion";
    public static final String APP_CONFIGURATION = "APP_CONFIGURATION";
    public static final String APP_SNOW = "SNOW";
    public static final String APP_SHARE_OPTION = "SHARE_OPTION";
    public static final String FROM_PUSH_NOTIFICATION = "From Push Notification";
    public static final String IS_FROM_PUSH = "Is From Push";
    public static final String APP_SHARE_LINK = "https://shrs.me/m/3AICGHn1KJ";
    public static final int NOTIFICATION_SESSION = 4;
    public static final int ALBUM_SESSION = 2;
    public static final int ARTICLE_SESSION = 2;
    public static final int HEADER_SESSION = 3;
    public static final int INVITE_FRIEND_SESSION = 2;
    public static final String CHALLENGE_GRATIFICATION_OBJ = "challenge_gratification_obj";
    public static final String CHALLENGE_GRATIFICATION_SCREEN = "Challenge Completed Screen";
    public static final String WHATSAPP_ICON = "Whatsapp Icon";
    public static final String SHARE_CHOOSER = "Share Chooser";
    public static final String CONFIG_KEY = "appliedlife.pvtltd.SHEROES.sharedprefs.CONFIG_KEY";
    public static final String APP_INSTALLATION = "appliedlife.pvtltd.SHEROES.sharedprefs.APP_INSTALLATION";
    public static final String SHARE_TEXT = "share_text";
    public static final String SHARE_IMAGE = "share_image";
    public static final String SHARE_DEEP_LINK_URL = "share_deep_link_url";
    public static final String SHARE_DIALOG_TITLE = "share_dialog_title";
    public static final String IS_SHARE_DEEP_LINK = "is share deep link";
    public static final String SHARE_CHANNEL = "channel";
    public static final String LOGOUT_USER = "Logout_user";
    public static final String REFERRER = "Referrer";
    public static final String UTM_SOURCE = "utm_source";
    public static final String UTM_MEDIUM = "utm_medium";
    public static final String UTM_CAMPAIGN = "utm_campaign";
    public static final String UTM_CONTENT = "utm_content";
    public static final String UTM_TERM = "utm_term";
    public static final String THUMBOR_KEY = "ThumborKey";
    public static final double MAX_IMAGE_RATIO = 1.5;
    public static final String SET_ORDER_KEY = "set order key";
    public static final String FEED_CONFIG_VERSION = "feed config version";
    public static final String REFERRER_BRANCH_LINK_URL = "Referrer branch link url";
    public static final int MENTOR_USER_TYPE_FOR_TAGGING = 7;
    public static final String NEXT_DAY_DATE = "NEXT_DAY_DATE";
    public static final int USER_MENTION_HEADER = 1;
    public static final int USER_MENTION_NO_RESULT_FOUND = 0;
    public static final String ARTICLE_GUIDELINE = "<h3> Hello! Here are 7 things to keep in mind when writing a story on SHEROES </h3><p>1. We welcome you to share your experiences and expertise with the SHEROES community. If you think you can help the women on the SHEROES platform in some way or have a really interesting story to tell, do write a story on SHEROES.</p><p>2. Word length for a story should be between 200 words to 1000 words.</p><p>3. By submitting a story you grant us a license to re-use, edit, and modify the content, if need be.</p><p>4. Please ensure the content you submit is your original work.</p><p>5. Promotional or Spammy content in any form will not be accepted.</p><p>6. Email us at care@sheroes.in in case you have any further queries regarding submitting a story.</p><p>7. All the best!</p>";

    //Push provider name
    public static final String PUSH_PROVIDER_SHEROES = "Sheroes";
    public static final String PUSH_PROVIDER_CLEVER_TAP = "CleverTap";
    public static final String IMAGE_INITIAL_FILE_NAME = "file\"; filename=\"";
    public static final int BACK_SLASH_OCCURRENCE_IN_POST_LINK = 4; // https://sheroes.com/communities/carrer-community/MTI3NQ==/MTIx

    //Vernacular
    public static final String SELECT_LANGUAGE_SHARE_PREF = "SELECT_LANGUAGE";
    public static final String LANGUAGE_KEY = "language_key";
    public static final String LEADERBOARD_CAROUSEL_STREAM = "LeaderboardCarouselStream";
    public static final String STORY_DRAFT = "Draft";
    public static final int RECYCLER_SMOOTH_SCROLL_COUNT_SIZE = 15;
    public static final int TRENDING_TAB = 1;
    public static final int HELPLINE_TIME_START = 12;
    public static final int HELPLINE_DATE_START = 0;
    public static final int HELPLINE_DATE_END = 11;
    public static final int FIREBASE_MAX_PARAMETERS = 25;
    public static final String CLEVER_TAP_CHANNEL_ID = "sheroesRelatedChannelID";
    public static final String CLEVER_TAP_CHANNEL_NAME = "Related to you and your posts";
    public static final String CLEVER_TAP_CHANNEL_DESC = "Sheroes Channel Desc";
    public static final String WHATS_APP_URI = "com.whatsapp";
    public static final String UNFOLLOW = "UNFOLLOW";
    public static final String LOGGED_IN_USER = "LoggedInUser";
    public static final String CHAMPION_SUBTYPE = "C";

    // Stream name
    public static final String STORY_STREAM = "story_stream";
    public static final String POST_STREAM = "profile_post_stream";
    /* Stream URL's for posts,articles,stories etc */
    public static final String MY_FEED_POST_STREAM = "participant/feed/stream";
    public static final String TRENDING_POST_STREAM = "participant/feed/stream?setOrderKey=TrendingPosts";
    public static final String OTHER_USER_STORIES_STREAM = "participant/feed/stream?setOrderKey=UserStoryStream&userId=";
    public static final String USER_MY_STORIES_STREAM = "participant/feed/stream?setOrderKey=UserStoryStream&myStory=true";
    public static final String OTHER_USER_POST_STREAM = "participant/feed/stream?setOrderKey=ProfilePostStream&userId=";
    public static final String USER_MY_POST_STREAM = "participant/feed/stream?setOrderKey=ProfilePostStream";
    public static String PREVIOUS_SCREEN="";
    public static String SOURCE_ACTIVE_TAB = "";

    //Social A/c's
    public static final String FACEBOOK = "Facebook";
    public static final String GOOGLE = "Google";
    public static final String EMAIL_PREF = "Email";
}

