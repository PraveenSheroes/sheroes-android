package appliedlife.pvtltd.SHEROES.models.entities.helpline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Deepak on 22-05-2017.
 */

public class HelplinePostQuestionRequest  extends BaseRequest{

    @SerializedName("question")
    @Expose
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
