package appliedlife.pvtltd.SHEROES.views.fragmentlistner;

import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

/**
 * Created by Praveen_Singh on 21-02-2017.
 */

public interface FragmentIntractionWithActivityListner {
    void onShowErrorDialog(String errorReason,FeedParticipationEnum feedParticipationEnum);
    
     void onSuccessResult(String result, FeedDetail feedDetail) ;
}
