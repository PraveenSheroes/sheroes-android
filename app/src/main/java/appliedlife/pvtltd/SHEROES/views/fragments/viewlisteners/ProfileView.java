package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;

/**
 * Created by priyanka on 07/03/17.
 */

public interface ProfileView extends BaseMvpView {



    void backListener(int id);
    void visitingCardOpen(ProfileEditVisitingCardResponse profileEditVisitingCardResponse);
    void callFragment(int id);
    void getEducationResponse(BoardingDataResponse boardingDataResponse);
    void getPersonalBasicDetailsResponse(BoardingDataResponse boardingDataResponse);
    void getprofiletracelflexibilityResponse(BoardingDataResponse boardingDataResponse);
    void getUserSummaryResponse(BoardingDataResponse boardingDataResponse);
    void getProfessionalBasicDetailsResponse(BoardingDataResponse boardingDataResponse);
    void getProfessionalWorkLocationResponse(BoardingDataResponse boardingDataResponse);
    void getProfileVisitingCardResponse(ProfileEditVisitingCardResponse profileEditVisitingCardResponse);
    // void getUserDetail(UserProfileResponse userProfileResponse);

    void getUserData(UserProfileResponse userProfileResponse);

    void getProfileListSuccess(GetTagData getAllData);

    void getProfileListSuccess(List<Doc> feedDetailList);






}
