package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfessionalBasicDetailsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePreferredWorkLocationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTravelFlexibilityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryResponse;

/**
 * Created by priyanka on 07/03/17.
 */

public interface ProfileView extends BaseMvpView {



    void backListener(int id);
    void visitingCardOpen(ProfileEditVisitingCardResponse profileEditVisitingCardResponse);

    void callFragment(int id);
    void getEducationResponse(EducationResponse educationResponse);
    void getPersonalBasicDetailsResponse(PersonalBasicDetailsResponse personalBasicDetailsResponse);
    void getprofiletracelflexibilityResponse(ProfileTravelFlexibilityResponse profileTravelFlexibilityResponse);
    void getUserSummaryResponse(UserSummaryResponse userSummaryResponse);
    void getProfessionalBasicDetailsResponse(ProfessionalBasicDetailsResponse professionalBasicDetailsResponse);
    void getProfessionalWorkLocationResponse(ProfilePreferredWorkLocationResponse profilePreferredWorkLocationResponse);
    void getProfileVisitingCardResponse(ProfileEditVisitingCardResponse profileEditVisitingCardResponse);


}
