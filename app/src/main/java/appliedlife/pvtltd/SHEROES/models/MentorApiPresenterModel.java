package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.MentorFollowerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.MentorInsightResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
                .map(new Function<MentorInsightResponse, MentorInsightResponse>() {
                    @Override
                    public MentorInsightResponse apply(MentorInsightResponse mentorInsightResponse) {
                        return mentorInsightResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
