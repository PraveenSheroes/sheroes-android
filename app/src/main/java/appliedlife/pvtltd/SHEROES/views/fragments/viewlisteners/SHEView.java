package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQSResponse;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMemberListResponse;

/**
 * Created by SHEROES 005 on 02-Jun-17.
 */

public interface SHEView  extends BaseMvpView {
    void getAllFAQs(FAQSResponse faqsResponse);
    void getAllICCMembers(ICCMemberListResponse iccMemberListResponse);
}
