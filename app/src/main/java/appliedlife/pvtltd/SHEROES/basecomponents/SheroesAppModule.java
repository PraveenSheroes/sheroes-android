package appliedlife.pvtltd.SHEROES.basecomponents;


import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.InstallUpdateForMoEngage;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.preferences.GsonPreferenceAdapter;
import appliedlife.pvtltd.SHEROES.utils.AnnotationExclusionStrategy;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_TAG;

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
                Request.Builder builder=original.newBuilder();
                Request request;
                if (null != userPreference && userPreference.isSet() && null != userPreference.get()) {
                     builder.header("Content-Type", "application/json")
                            .header("Authorization", userPreference.get().getToken());
                } else {
                     builder.header("Content-Type", "application/json");
                }

                if (!NetworkUtil.isConnected(mApplication)) {
                   // int maxAge =AppConstants.NO_REACTION_CONSTANT; // read from cache for 0 minute if connected
                    // builder.addHeader("Cache-Control", "public, max-age=" + maxAge);
                }else
                {
                   // int maxStale = AppConstants.SECONDS_IN_MIN * AppConstants.MINUTES_IN_HOUR * AppConstants.HOURS_IN_DAY * AppConstants.CACHE_VALID_DAYS; // tolerate 2-weeks stale
                    // builder.addHeader("Cache-Control","public, only-if-cached, max-stale=" + maxStale);
                }
                request=builder.build();

                okhttp3.Response response = chain.proceed(request);
                response.cacheResponse();
                // Customize or return the response
                return response;
            }
        };
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
        return rxSharedPreferences.getObject(AppConstants.SHEROES_AUTH_TOKEN, new GsonPreferenceAdapter<>(gson, LoginResponse.class));
    }


    @Singleton
    @Provides
    public Preference<CreateCommunityRequest> provideCommunityPref(RxSharedPreferences rxSharedPreferences, Gson gson) {
        return rxSharedPreferences.getObject(AppConstants.COMMUNITY_DETAIL, new GsonPreferenceAdapter<>(gson, CreateCommunityRequest.class));
    }


    @Singleton
    @Provides
    public Preference<MasterDataResponse> provideMasterDataUserPref(RxSharedPreferences rxSharedPreferences, Gson gson) {
        return rxSharedPreferences.getObject(AppConstants.MASTER_DATA, new GsonPreferenceAdapter<>(gson, MasterDataResponse.class));
    }
    @Singleton
    @Provides
    public Preference<InstallUpdateForMoEngage> provideInstallUpdatePref(RxSharedPreferences rxSharedPreferences, Gson gson) {
        return rxSharedPreferences.getObject(AppConstants.INSTALL_UPDATE, new GsonPreferenceAdapter<>(gson, InstallUpdateForMoEngage.class));
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
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(AppConstants.READ_TIME_OUT, TimeUnit.SECONDS)
               .connectTimeout(AppConstants.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .cache(cache)
                .build();
        return okHttpClient;
    }

    @Provides
    @Singleton
    Retrofit provideSheroesNetworkCall(OkHttpClient okHttpClient, Gson gson) {

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public SheroesAppServiceApi providesApiService(Retrofit retrofit) {
        return retrofit.create(SheroesAppServiceApi.class);
    }



}

