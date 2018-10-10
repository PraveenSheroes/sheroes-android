package appliedlife.pvtltd.SHEROES.basecomponents;


import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.AppInstallation;
import appliedlife.pvtltd.SHEROES.models.entities.community.AllCommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.EventSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ImageSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LeaderBoardUserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.OrganizationFeedObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.PollSolarObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.InstallUpdateForMoEngage;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.preferences.GsonConverter;
import appliedlife.pvtltd.SHEROES.utils.AnnotationExclusionStrategy;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static appliedlife.pvtltd.SHEROES.utils.CommonUtil.getDeviceName;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: All app level modules for network calls.
 * required newtork connection for app vua
 */
@Module
public class SheroesAppModule {

    File cacheFile;
    SheroesApplication mApplication;
    final static long CACHE_SIZE = 10 * 1024 * 1024;

    private static final String[] DATE_FORMATS = new String[]{
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd"
    };

    @Provides
    @Singleton
    SheroesApplication providesApplication() {
        return mApplication;
    }

    public SheroesAppModule(File cacheFile, SheroesApplication application) {
        this.cacheFile = cacheFile;
        mApplication = application;
    }

    @Singleton
    @Provides
    public Interceptor provideInterceptor(final Preference<LoginResponse> userPreference) {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                Request request;
                if (null != userPreference && userPreference.isSet()) {
                    builder.header("Content-Type", "application/json")
                            .header("Authorization", userPreference.get().getToken());
                } else {
                    builder.header("Content-Type", "application/json");
                }
                builder.header("X-Language", CommonUtil.getPrefStringValue(AppConstants.LANGUAGE_KEY));
                builder.header("user-agent", getUserAgent(SheroesApplication.mContext));
                builder.header("X-app-version-code", getAppVersionCode(SheroesApplication.mContext));
                if (NetworkUtil.isConnected(mApplication)) {
                     int maxAge =0; // read from cache for 0 minute if connected
                     builder.addHeader("Cache-Control", "public, max-age=" + maxAge);
                } else {
                     int maxStale = AppConstants.SECONDS_IN_MIN * AppConstants.MINUTES_IN_HOUR * AppConstants.HOURS_IN_DAY * AppConstants.CACHE_VALID_DAYS; // tolerate 2-weeks stale
                     builder.addHeader("Cache-Control","public, only-if-cached, max-stale=" + maxStale);
                }
                request = builder.build();

                okhttp3.Response response = chain.proceed(request);
                response.cacheResponse();
                // Customize or return the response
                return response;
            }
        };
    }

    private static Gson initGSONSerializers() {

        final RuntimeTypeAdapterFactory<FeedDetail> typeFactory = RuntimeTypeAdapterFactory
                .of(FeedDetail.class, "sub_type")
                .registerSubtype(ArticleSolrObj.class, AppConstants.FEED_ARTICLE)
                .registerSubtype(UserSolrObj.class, AppConstants.USER_SUB_TYPE)
                .registerSubtype(CommunityFeedSolrObj.class, AppConstants.FEED_COMMUNITY)
                .registerSubtype(UserPostSolrObj.class, AppConstants.FEED_COMMUNITY_POST)
                .registerSubtype(EventSolrObj.class, AppConstants.FEED_EVENT)
                .registerSubtype(OrganizationFeedObj.class, AppConstants.ORGANIZATION_SUB_TYPE)
                .registerSubtype(ChallengeSolrObj.class, AppConstants.CHALLENGE_SUB_TYPE_NEW)
                .registerSubtype(CarouselDataObj.class, AppConstants.CAROUSEL_SUB_TYPE)
                .registerSubtype(LeaderBoardUserSolrObj.class, AppConstants.LEADER_SUB_TYPE)
                .registerSubtype(ImageSolrObj.class, AppConstants.IMAGE_SUBTYPE)
                .registerSubtype(PollSolarObj.class, AppConstants.FEED_POLL);

        return new GsonBuilder()
                .setDateFormat(DATE_FORMATS[0])
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .registerTypeAdapterFactory(typeFactory)
                .create();
    }

    private static class DateDeserializer implements JsonSerializer<Date>, JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement jsonElement, Type typeOF,
                                JsonDeserializationContext context) throws JsonParseException {
            if (TextUtils.isEmpty(jsonElement.getAsString())) {
                return null;
            }

            for (String format : DATE_FORMATS) {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    return simpleDateFormat.parse(jsonElement.getAsString());
                } catch (ParseException ignored) {
                }
            }
            Crashlytics.getInstance().core.logException(new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
                    + "\". Supported formats: " + Arrays.toString(DATE_FORMATS)));
            return null;
        }

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == null) {
                return JsonNull.INSTANCE;
            }

            return new JsonPrimitive(DateUtil.toDateOnlyString(src));
        }
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setExclusionStrategies(new AnnotationExclusionStrategy())
                .create();
    }

    @Singleton
    @Provides
    public DateUtil provideDateUtil() {
        return DateUtil.getInstance();
    }

    @Singleton
    @Provides
    public AppUtils provideAppUtil() {
        return AppUtils.getInstance();
    }

    @Singleton
    @Provides
    public RxSharedPreferences provideRxSharedPreferences() {
        return RxSharedPreferences.create(mApplication.getSharedPreferences(AppConstants.SHEROES_PREFERENCE, Context.MODE_PRIVATE));
    }

    @Singleton
    @Provides
    public Preference<LoginResponse> provideTokenPref(RxSharedPreferences rxSharedPreferences, Gson gson) {
        return rxSharedPreferences.getObject(AppConstants.SHEROES_AUTH_TOKEN, new LoginResponse(), new GsonConverter<>(gson, LoginResponse.class));
    }

    @Singleton
    @Provides
    public Preference<AllCommunitiesResponse> provideAllCommunities(RxSharedPreferences rxSharedPreferences, Gson gson) {
        return rxSharedPreferences.getObject(AppConstants.ALL_COMMUNITY_LIST, new AllCommunitiesResponse(), new GsonConverter<>(gson, AllCommunitiesResponse.class));
    }

    @Singleton
    @Provides
    public Preference<AppConfiguration> provideConfiguration(RxSharedPreferences rxSharedPreferences, Gson gson) {
        return rxSharedPreferences.getObject(AppConstants.CONFIG_KEY, new AppConfiguration(), new GsonConverter<>(gson, AppConfiguration.class));
    }

    @Singleton
    @Provides
    public Preference<AppInstallation> provideAppInstallation(RxSharedPreferences rxSharedPreferences, Gson gson) {
        return rxSharedPreferences.getObject(AppConstants.APP_INSTALLATION, new AppInstallation(), new GsonConverter<>(gson, AppInstallation.class));
    }


    @Singleton
    @Provides
    public Preference<MasterDataResponse> provideMasterDataUserPref(RxSharedPreferences rxSharedPreferences, Gson gson) {
        return rxSharedPreferences.getObject(AppConstants.MASTER_DATA, new MasterDataResponse(), new GsonConverter<>(gson, MasterDataResponse.class));
    }

    @Singleton
    @Provides
    public Preference<InstallUpdateForMoEngage> provideInstallUpdatePref(RxSharedPreferences rxSharedPreferences, Gson gson) {
        return rxSharedPreferences.getObject(AppConstants.INSTALL_UPDATE, new InstallUpdateForMoEngage(), new GsonConverter<>(gson, InstallUpdateForMoEngage.class));
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Interceptor interceptor) {
        Cache cache = null;
        try {
            cache = new Cache(cacheFile, CACHE_SIZE);
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
            e.printStackTrace();
        }
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.readTimeout(AppConstants.READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(AppConstants.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .cache(cache);

        if (BuildConfig.DEBUG) {
           okHttpClientBuilder.addNetworkInterceptor(new StethoInterceptor());
        }

        return okHttpClientBuilder.build();
    }


    public static Gson ensureGson() {
        if (sGson == null) {
            sGson = initGSONSerializers();
        }
        return sGson;
    }

    private static Gson sGson;

    @Provides
    @Singleton
    Retrofit provideSheroesNetworkCall(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(ensureGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public SheroesAppServiceApi providesApiService(Retrofit retrofit) {
        return retrofit.create(SheroesAppServiceApi.class);
    }

    private static String getUserAgent(Context mContext) {
        String userAgent = AppConstants.APP_NAME;
        try {
            String packageName = mContext.getPackageName();
            String version = mContext.getPackageManager().getPackageInfo(packageName, 0).versionName;
            String deviceModel = getDeviceName();
            String androidVersion = android.os.Build.VERSION.RELEASE;

            userAgent = userAgent + "/" + version + "/" + "Android" + "/" + androidVersion + "/" + deviceModel;
        } catch (PackageManager.NameNotFoundException e) {
            // Log.e(TAG, "Unable to find self by package name", e);
        }

        return userAgent;
    }

    private static String getAppVersionCode(Context mContext) {
        String version = "0";
        try {
            String packageName = mContext.getPackageName();
            version = Integer.toString(mContext.getPackageManager().getPackageInfo(packageName, 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            // Log.e(TAG, "Unable to find self by package name", e);
        }

        return version;
    }

}

