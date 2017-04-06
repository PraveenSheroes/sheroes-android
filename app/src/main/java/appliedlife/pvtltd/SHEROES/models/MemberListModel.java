package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ajit Kumar on 03-02-2017.
 */

public class MemberListModel {
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    @Inject
    public MemberListModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }

    public rx.Observable<MemberListResponse> getMemberList(MemberRequest memberRequest){
        LogUtils.error("Community Member list req: ",gson.toJson(memberRequest));

        return sheroesAppServiceApi.getMemberList(memberRequest)
                .map(new Func1<MemberListResponse, MemberListResponse>() {
                    @Override
                    public MemberListResponse call(MemberListResponse memberListResponse) {
                        LogUtils.error("Community Member list res: ",gson.toJson(memberListResponse));

                        return memberListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public rx.Observable<MemberListResponse> removeMember(RemoveMemberRequest removeMemberRequest){
        LogUtils.error("Community Member list req: ",gson.toJson(removeMemberRequest));

        return sheroesAppServiceApi.removeMember(removeMemberRequest)
                .map(new Func1<MemberListResponse, MemberListResponse>() {
                    @Override
                    public MemberListResponse call(MemberListResponse memberListResponse) {
                        LogUtils.error("Community Member list res: ",gson.toJson(memberListResponse));

                        return memberListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
