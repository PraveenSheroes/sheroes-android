package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadResponse;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionResponse;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Deepak on 22-05-2017.
 */

public class HelplineModel {

    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;

    @Inject
    public HelplineModel(SheroesAppServiceApi sheroesAppServiceApi, Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }

    public Observable<HelplinePostQuestionResponse> postHelplineQuestion(HelplinePostQuestionRequest helplinePostQuestionRequest) {

        return sheroesAppServiceApi.postHelplineQuestion(helplinePostQuestionRequest)
                .map(new Function<HelplinePostQuestionResponse, HelplinePostQuestionResponse>() {

                    @Override
                    public HelplinePostQuestionResponse apply(HelplinePostQuestionResponse helplinePostQuestionResponse) {

                        return helplinePostQuestionResponse;
                    }
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<HelplineGetChatThreadResponse> getHelplineChatDetails(HelplineGetChatThreadRequest helplineGetChatThreadRequest) {

        return sheroesAppServiceApi.getHelplineChatDetails(helplineGetChatThreadRequest)
                .map(new Function<HelplineGetChatThreadResponse, HelplineGetChatThreadResponse>() {

                    @Override
                    public HelplineGetChatThreadResponse apply(HelplineGetChatThreadResponse helplineGetChatThreadResponse) {

                        return helplineGetChatThreadResponse;
                    }
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    
}
