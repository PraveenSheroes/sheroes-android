package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.community.ApproveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMember;
import appliedlife.pvtltd.SHEROES.models.entities.community.RequestedListResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by SHEROES-TECH on 08-02-2017.
 */

public class RequestedListModel {
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    @Inject
    public RequestedListModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }

    public rx.Observable<RequestedListResponse> getMemberList(MemberRequest memberRequest){
        LogUtils.error("Community Requested req: ",gson.toJson(memberRequest));

        return sheroesAppServiceApi.getRequestList(memberRequest)
                .map(new Func1<RequestedListResponse, RequestedListResponse>() {
                    @Override
                    public RequestedListResponse call(RequestedListResponse memberListResponse) {
                        LogUtils.error("Community Requested res: ",gson.toJson(memberListResponse));

                        return memberListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public rx.Observable<MemberListResponse> removePandingMember(RemoveMember removeMember){
        LogUtils.error("Community Panding Member Reject req: ",gson.toJson(removeMember));

        return sheroesAppServiceApi.removePandingMember(removeMember)
                .map(new Func1<MemberListResponse, MemberListResponse>() {
                    @Override
                    public MemberListResponse call(MemberListResponse memberListResponse) {
                        LogUtils.error("Community Panding Member Reject res: ",gson.toJson(memberListResponse));

                        return memberListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public rx.Observable<MemberListResponse> approvePandingMember(ApproveMemberRequest approveMemberRequest){
        LogUtils.error("Community Panding Member Reject req: ",gson.toJson(approveMemberRequest));

        return sheroesAppServiceApi.approvePandingMember(approveMemberRequest)
                .map(new Func1<MemberListResponse, MemberListResponse>() {
                    @Override
                    public MemberListResponse call(MemberListResponse memberListResponse) {
                        LogUtils.error("Community Panding Member Reject res: ",gson.toJson(memberListResponse));

                        return memberListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
