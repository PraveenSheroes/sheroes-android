package appliedlife.pvtltd.SHEROES.basecomponents;


import android.content.Context;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.preferences.GsonPreferenceAdapter;
import appliedlife.pvtltd.SHEROES.preferences.SessionUser;
import appliedlife.pvtltd.SHEROES.preferences.Token;
import appliedlife.pvtltd.SHEROES.utils.AnnotationExclusionStrategy;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
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

    public SheroesAppModule(File cacheFile,SheroesApplication application) {
        this.cacheFile = cacheFile;
        mApplication = application;
    }

    @Singleton
    @Provides
    public Interceptor provideInterceptor( final Preference<Token> userPreference) {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                // Customize the request
                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                     //   .removeHeader("Pragma")
                     //   .header("Cache-Control", String.format("max-age=%d", BuildConfig.CACHETIME))
                        .build();
                okhttp3.Response response = chain.proceed(request);
                response.cacheResponse();
                if (userPreference.isSet()&&null!=userPreference.get()) {
                       // LogUtils.info("sheroesapp", "--------" + userPreference.get().getAccessToken());
                      //  LogUtils.info("sheroesapp", "--------" + userPreference.get().getTokenTime());
                }
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
    public RxSharedPreferences provideRxSharedPreferences() {
        return RxSharedPreferences.create(mApplication.getSharedPreferences(AppConstants.SHEROES_PREFERENCE, Context.MODE_PRIVATE));
    }

    @Singleton
    @Provides
    public Preference<Token> provideTokenPref(RxSharedPreferences rxSharedPreferences, Gson gson) {
        return rxSharedPreferences.getObject(AppConstants.SHEROES_AUTH_TOKEN,new GsonPreferenceAdapter<>(gson, Token.class));
    }


    @Singleton
    @Provides
    public Preference<SessionUser> provideSessionUserPref(RxSharedPreferences rxSharedPreferences, Gson gson) {
        return rxSharedPreferences.getObject(AppConstants.SHEROES_USER_SESSION,new GsonPreferenceAdapter<>(gson, SessionUser.class));
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

