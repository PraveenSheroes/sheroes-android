package appliedlife.pvtltd.SHEROES.models.entities.helpline;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Deepak on 22-05-2017.
 */

public class HelplineGetChatThreadResponse extends BaseResponse {

    @SerializedName("helpline_chat_docs")
    @Expose
    private List<HelplineChatDoc> helplineChatDocs = null;

    public List<HelplineChatDoc> getHelplineChatDocs() {
        return helplineChatDocs;
    }

    public void setHelplineChatDocs(List<HelplineChatDoc> helplineChatDocs) {
        this.helplineChatDocs = helplineChatDocs;
    }
}
