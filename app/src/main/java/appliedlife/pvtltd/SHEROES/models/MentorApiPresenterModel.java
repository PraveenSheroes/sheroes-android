package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.MentorFollowerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.MentorInsightResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Praveen on 14/12/17.
 */

public class MentorApiPresenterModel {
    private final String TAG = LogUtils.makeLogTag(HomeModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;

    @Inject
    public MentorApiPresenterModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson = gson;
    }

    public Observable<MentorInsightResponse> getMentorInsightFromModel(MentorFollowerRequest mentorFollowerRequest) {
        LogUtils.info(TAG, "*******************" + new Gson().toJson(mentorFollowerRequest));
        return sheroesAppServiceApi.getMentorInsightFromApi(mentorFollowerRequest)
                .map(new Func1<MentorInsightResponse, MentorInsightResponse>() {
                    @Override
                    public MentorInsightResponse call(MentorInsightResponse mentorInsightResponse) {
                        return mentorInsightResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
