package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Praveen_Singh on 19-03-2017.
 */
@Singleton
public class OnBoardingModel {
    private final String TAG = LogUtils.makeLogTag(OnBoardingModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;

    @Inject
    public OnBoardingModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson = gson;
    }

    public Observable<FeedResponsePojo> getFeedFromModel(FeedRequestPojo feedRequestPojo) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(feedRequestPojo));
        return sheroesAppServiceApi.getFeedFromApi(feedRequestPojo)
                .map(new Function<FeedResponsePojo, FeedResponsePojo>() {
                    @Override
                    public FeedResponsePojo apply(FeedResponsePojo feedResponsePojo) {
                        return feedResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<GetAllData> getOnBoardingFromModel(GetAllDataRequest getAllDataRequest) {
        return sheroesAppServiceApi.getOnBoardingSearchFromApi(getAllDataRequest)
                .map(new Function<GetAllData, GetAllData>() {
                    @Override
                    public GetAllData apply(GetAllData getAllData) {
                        return getAllData;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CommunityResponse> communityJoinFromModel(CommunityRequest communityRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(communityRequest));
        return sheroesAppServiceApi.getCommunityJoinResponse(communityRequest)
                .map(new Function<CommunityResponse, CommunityResponse>() {
                    @Override
                    public CommunityResponse apply(CommunityResponse communityResponse) {
                        return communityResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MemberListResponse> removeMember(RemoveMemberRequest removeMemberRequest) {
        LogUtils.error("Community Member list req: ", gson.toJson(removeMemberRequest));

        return sheroesAppServiceApi.removeMember(removeMemberRequest)
                .map(new Function<MemberListResponse, MemberListResponse>() {
                    @Override
                    public MemberListResponse apply(MemberListResponse memberListResponse) {
                        LogUtils.error("Community Member list res: ", gson.toJson(memberListResponse));
                        return memberListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ConfigurationResponse> getConfig() {
        return sheroesAppServiceApi.getConfig()
                .map(new Function<ConfigurationResponse, ConfigurationResponse>() {
                    @Override
                    public ConfigurationResponse apply(ConfigurationResponse config) {
                        return config;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
