package appliedlife.pvtltd.SHEROES.basecomponents;


import android.content.Context;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.preferences.GsonPreferenceAdapter;
import appliedlife.pvtltd.SHEROES.preferences.SessionUser;
import appliedlife.pvtltd.SHEROES.utils.AnnotationExclusionStrategy;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
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
                Request request;
                if (null != userPreference && userPreference.isSet() && null != userPreference.get()) {
                    request = original.newBuilder().header("Content-Type", "application/json")
                            .header("Authorization", userPreference.get().getToken())
                            .build();
                } else {
                    request = original.newBuilder().header("Content-Type", "application/json")
                            .build();
                }
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
    public Preference<SessionUser> provideSessionUserPref(RxSharedPreferences rxSharedPreferences, Gson gson) {
        return rxSharedPreferences.getObject(AppConstants.SHEROES_USER_SESSION, new GsonPreferenceAdapter<>(gson, SessionUser.class));
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Interceptor interceptor) {
        Cache cache = null;
        try {
            cache = new Cache(cacheFile, CACHE_SIZE);
        } catch (Exception e) {
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

    @Singleton
    @Provides
    public StorIOSQLite getStorIOSQLite(SheroesSqliteOpenHelper openHelper) {
        return SheroesSqliteOpenHelper.getStorIOSQLite(openHelper);
    }

}

