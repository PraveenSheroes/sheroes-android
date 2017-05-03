package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GetUserVisitingCardRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.VisitingCardActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;

/**
 * Created by priyanka on 14/03/17.
 */




public class ProfileVisitingCardView extends BaseFragment implements ProfileView{

    private final String TAG = LogUtils.makeLogTag(ProfileVisitingCardView.class);

    private final String SCREEN_NAME = "Profile_Visiting_card_screen";
    String dest_file_path = "visiting_card.pdf";
    int downloadedSize = 0, totalsize;
    String download_file_url = "http://www.princexml.com/howcome/2016/samples/magic6/magic.pdf";
    float per = 0;
    @Bind(R.id.tv_download_visiting_card)
    TextView mTv_download_visiting_card;
    @Bind(R.id.tv_edit_visiting_card)
    TextView mTv_edit_visiting_card;
    @Bind(R.id.tv_user_fullname)
    TextView mTv_user_fullname;
    @Bind(R.id.tv_designation)
    TextView mTv_designation;
    @Bind(R.id.tv_interesting_text1)
    TextView mTv_interesting_text1;
    @Bind(R.id.tv_interesting_text2)
    TextView mTv_interesting_text2;
    @Bind(R.id.tv_user_location)
    TextView mTvUserLocation;
    @Bind(R.id.tv_mobile_no)
    TextView mTv_mobile_no;
    @Bind(R.id.tv_email_text)
     TextView mTv_email_text;
    @Bind(R.id.tv_user_address)
     TextView mTv_user_address;



    ProfileEditVisitingCardResponse profileEditVisitingCardResponse=new ProfileEditVisitingCardResponse();




    ProfileView mProfileVisitingCardFragmentCallBack;
    @Inject
    ProfilePersenter mProfilePresenter;

    @Override
    public void onAttach(Context context) {


        super.onAttach(context);
        try {
            if (getActivity() instanceof ProfileView) {

                mProfileVisitingCardFragmentCallBack = (ProfileView) getActivity();

            }

        } catch (Exception e) {
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_profile_visiting_card, container, false);
        ButterKnife.bind(this, view);
        mProfilePresenter.attachView(this);

        mProfilePresenter.getEditVisitingCardDetailsAuthTokeInPresenter();


        return view;
    }

    void downloadAndOpenPDF() {
        new Thread(new Runnable() {
            public void run() {
                if(StringUtil.isNotNullOrEmptyString(profileEditVisitingCardResponse.getUrl()));
                {
                    Uri path = Uri.fromFile(downloadFile(profileEditVisitingCardResponse.getUrl()));
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(path, "application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    } catch (ActivityNotFoundException e) {


                        //mTv_loading.setError("PDF Reader application is not installed in your device");
                    }
                }
            }
        }).start();

    }

    File downloadFile(String dwnload_file_path) {
        File file = null;
        try {

            URL url = new URL(dwnload_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            // connect
            urlConnection.connect();

            // set the path where we want to save the file
            File SDCardRoot = Environment.getExternalStorageDirectory();
            // create a new file, to save the downloaded file
            file = new File(SDCardRoot, dest_file_path);

            FileOutputStream fileOutput = new FileOutputStream(file);

            // Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            // this is the total size of the file which we are
            // downloading
            totalsize = urlConnection.getContentLength();
            //setText("Starting PDF download...");

            // create a buffer...
            byte[] buffer = new byte[1024 * 1024];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                per = ((float) downloadedSize / totalsize) * 100;
              /*  setText("Total PDF File size  : "
                        + (totalsize / 1024)
                        + " KB\n\nDownloading PDF " + (int) per
                        + "% complete");*/
            }
            // close the output stream when complete //
            fileOutput.close();
           // setText("Download Complete. Open PDF Application installed in the device.");

        } catch (final MalformedURLException e) {
           /* setTextError("Some error occured. Press back and try again.",
                    Color.RED);*/
        } catch (final IOException e) {

            /*setTextError("Some error occured. Press back and try again.",
                    Color.RED);*/
        } catch (final Exception e) {
          /*  setTextError(
                    "Failed to download image. Please check your internet connection.",
                    Color.RED);*/
        }
        return file;
    }


    @OnClick(R.id.tv_edit_visiting_card)

  public void Edit_VisitingCard()

  {

     mProfileVisitingCardFragmentCallBack.visitingCardOpen(profileEditVisitingCardResponse);

/*  ProfileEditVisitingCardFragment profileVisitingCardView= new ProfileEditVisitingCardFragment();
      Bundle bundleProfileVisitingCardFragment = new Bundle();
      ButterKnife.bind(getActivity());

      profileVisitingCardView.setArguments(bundleProfileVisitingCardFragment);
      getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.top_to_bottom_exit)
              .replace(R.id.profile_container, profileVisitingCardView, ProfileVisitingCardView.class.getName()).addToBackStack(null).commitAllowingStateLoss();

*/
      /*
      Intent myIntent = new Intent(getActivity(), ProfileEditVisitingCardFragment.class);
      startActivity(myIntent);*/


  }



    @OnClick(R.id.tv_download_visiting_card)

    protected  void  ClickOnTexview()
    {

        GetUserVisitingCardRequest getUserVisitingCardRequest = new GetUserVisitingCardRequest();
        AppUtils appUtils = AppUtils.getInstance();

        getUserVisitingCardRequest.setAboutMe(profileEditVisitingCardResponse.getAboutMe());
        getUserVisitingCardRequest.setScreenName("string");
        getUserVisitingCardRequest.setCurrentLocation(profileEditVisitingCardResponse.getCurrentLocation());
        getUserVisitingCardRequest.setMobile(profileEditVisitingCardResponse.getMobile());
        getUserVisitingCardRequest.setFirstName(profileEditVisitingCardResponse.getFirstName());
        getUserVisitingCardRequest.setLastName(profileEditVisitingCardResponse.getLastName());
        getUserVisitingCardRequest.setCurrentDesignation(profileEditVisitingCardResponse.getCurrentDesignation());
        getUserVisitingCardRequest.setCurrentCompany(profileEditVisitingCardResponse.getCurrentCompany());
        getUserVisitingCardRequest.setEmailId(profileEditVisitingCardResponse.getEmailid());
        getUserVisitingCardRequest.setHeighestDegree(profileEditVisitingCardResponse.getHeighestDegree());
        getUserVisitingCardRequest.setSchool(profileEditVisitingCardResponse.getSchool());
        mProfilePresenter.getVisitingCardDetailsAuthTokeInPresenter(getUserVisitingCardRequest);
        downloadAndOpenPDF();

      /*Intent myIntent = new Intent(getActivity(), VisitingCardActivity.class);
      startActivity(myIntent);*/

    }

    @Override
    public void onBackPressed(int id) {

    }

    @Override
    public void visitingCardOpen(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {

    }

    @Override
    public void callFragment(int id) {

    }

    @Override
    public void getEducationResponse(BoardingDataResponse boardingDataResponse) {

    }



    @Override
    public void getPersonalBasicDetailsResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getprofiletracelflexibilityResponse(BoardingDataResponse boardingDataResponse) {

    }




    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getProfessionalBasicDetailsResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getProfessionalWorkLocationResponse(BoardingDataResponse boardingDataResponse) {

    }




    @Override
    public void getProfileVisitingCardResponse(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {

        switch (profileEditVisitingCardResponse.getStatus()) {
            case AppConstants.SUCCESS:
                mTv_user_fullname.setText(profileEditVisitingCardResponse.getFirstName()+" "+ profileEditVisitingCardResponse.getLastName());
                mTv_mobile_no.setText(profileEditVisitingCardResponse.getMobile());
                mTv_designation.setText(profileEditVisitingCardResponse.getCurrentDesignation());
                mTv_user_address.setText(profileEditVisitingCardResponse.getCurrentLocation());
                mTvUserLocation.setText(profileEditVisitingCardResponse.getCurrentLocation());
                mTv_email_text.setText(profileEditVisitingCardResponse.getEmailid());
                this.profileEditVisitingCardResponse=profileEditVisitingCardResponse;
                break;
            case AppConstants.FAILED:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(profileEditVisitingCardResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_JOIN_INVITE);
                break;
            default:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), ERROR_JOIN_INVITE);
        }

    }

    @Override
    public void getUserData(UserProfileResponse userProfileResponse) {

    }


    @Override
    public void getProfileListSuccess(GetTagData getAllData) {

    }

    @Override
    public void getProfileListSuccess(List<Doc> feedDetailList) {

    }
}
