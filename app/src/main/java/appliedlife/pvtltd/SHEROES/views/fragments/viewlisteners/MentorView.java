package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.MentorInsightResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

/**
 * Created by Praveen on 14/12/17.
 */

public interface MentorView extends BaseMvpView {
    void showMentorInsightDetail(MentorInsightResponse mentorInsightResponse);
}
