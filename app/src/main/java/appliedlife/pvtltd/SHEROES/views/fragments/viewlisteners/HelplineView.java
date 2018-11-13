package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineChatDoc;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadResponse;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionResponse;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostRatingResponse;

/**
 * Created by Deepak on 21-05-2017.
 */

public interface HelplineView extends BaseMvpView {

    void getHelpChatThreadSuccess(HelplineGetChatThreadResponse helplineGetChatThreadResponse);
    void getPostQuestionSuccess(HelplinePostQuestionResponse helplinePostQuestionResponse);
    void getPostRatingSuccess(HelplinePostRatingResponse helplinePostRatingResponse, HelplineChatDoc helplineChatDoc);
}
