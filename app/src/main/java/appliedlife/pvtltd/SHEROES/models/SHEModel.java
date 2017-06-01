package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQSRequest;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQSResponse;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMemberRequest;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by SHEROES 005 on 02-Jun-17.
 */

public class SHEModel {
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;

    @Inject
    public SHEModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }

    public Observable<FAQSResponse> getAllFAQS(FAQSRequest faqsRequest){
        return sheroesAppServiceApi.getAllSHEFAQS(faqsRequest)
                .map(new Func1<FAQSResponse, FAQSResponse>() {
                    @Override
                    public FAQSResponse call(FAQSResponse faqsResponse) {
                        return faqsResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ICCMemberListResponse> getAllICCMembers(ICCMemberRequest iccMemberRequest){
        return sheroesAppServiceApi.getAllSHEICCMemberList(iccMemberRequest)
                .map(new Func1<ICCMemberListResponse, ICCMemberListResponse>() {
                    @Override
                    public ICCMemberListResponse call(ICCMemberListResponse iccMemberListResponse) {
                        return iccMemberListResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
