package appliedlife.pvtltd.SHEROES.models;

import android.util.Log;

import com.f2prateek.rx.preferences.Preference;
import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobApplyRequest;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobApplyResponse;
import appliedlife.pvtltd.SHEROES.preferences.SessionUser;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by SHEROES-TECH on 11-03-2017.
 */

public class JobModel {
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    Preference<SessionUser> userPreference;
    @Inject
    public JobModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }
    public rx.Observable<JobApplyResponse> getJobApply(JobApplyRequest jobApplyRequest){

        LogUtils.error("Job Apply req: ",gson.toJson(jobApplyRequest));
        return sheroesAppServiceApi.getJobApply(jobApplyRequest)
                .map(new Func1<JobApplyResponse, JobApplyResponse>() {
                    @Override
                    public JobApplyResponse call(JobApplyResponse jobApplyResponse) {
                        Log.e("Job Apply res: ",gson.toJson(jobApplyResponse));
                        return jobApplyResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
