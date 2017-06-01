package appliedlife.pvtltd.SHEROES.models.entities.she;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by SHEROES 005 on 30-May-17.
 */

public class FAQS extends BaseResponse{

    @SerializedName("question")
    @Expose
    public String question;

    @SerializedName("answer")
    @Expose
    public String answer;

    @SerializedName("item_selected")
    @Expose
    public boolean itemSelected;

    @SerializedName("item_position")
    @Expose
    public int itemPosition;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public boolean getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(boolean itemSelected) {
        this.itemSelected = itemSelected;
    }
}
