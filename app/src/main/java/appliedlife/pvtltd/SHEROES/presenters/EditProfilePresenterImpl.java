package appliedlife.pvtltd.SHEROES.presenters;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences.Preference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.ProfileModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.AllCommunitiesResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IEditProfileView;
import rx.Subscriber;
import rx.Subscription;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_AUTH_TOKEN;

/**
 * Created by ravi on 01/01/18.
 * Presenter for Edit profile Activity
 */

public class EditProfilePresenterImpl extends BasePresenter<IEditProfileView> {

    private final String TAG = LogUtils.makeLogTag(HomePresenter.class);
    private ProfileModel profileModel;

    SheroesApplication mSheroesApplication;

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    Preference<AllCommunitiesResponse> mAllCommunities;

    @Inject
    public EditProfilePresenterImpl(ProfileModel profileModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference) {
        this.profileModel = profileModel;
        this.mSheroesApplication = sheroesApplication;
        this.mUserPreference = userPreference;
    }

    // Update User Basic Details
    public void getPersonalBasicDetailsAuthTokeInPresenter(PersonalBasicDetailsRequest personalBasicDetailsRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }

        getMvpView().startProgressBar();
        Subscription subscription = profileModel.getPersonalBasicDetailsAuthTokenFromModel(personalBasicDetailsRequest).subscribe(new Subscriber<BoardingDataResponse>() {
            @Override
            public void onCompleted() {


            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(BoardingDataResponse boardingDataResponse) {
                getMvpView().stopProgressBar();
                if (null != boardingDataResponse) {
                    if(boardingDataResponse.getStatus().equalsIgnoreCase(AppConstants.FAILED))  {
                        if(boardingDataResponse.getFieldErrorMessageMap().containsKey(AppConstants.INAVLID_DATA)) {
                            String errorMessage = boardingDataResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA);
                            getMvpView().errorMessage(errorMessage);
                        }
                    } else if(boardingDataResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                        getMvpView().getPersonalBasicDetailsResponse(boardingDataResponse);
                    }
                }
            }
        });
        registerSubscription(subscription);
    }

    //Update - About me
    public void getUserSummaryDetailsAuthTokeInPresenter(UserSummaryRequest userSummaryRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_AUTH_TOKEN);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = profileModel.getPersonalUserSummaryDetailsAuthTokenFromModel(userSummaryRequest).subscribe(new Subscriber<BoardingDataResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_GENERIC_ERROR), ERROR_AUTH_TOKEN);
            }

            @Override
            public void onNext(BoardingDataResponse boardingDataResponse) {
                getMvpView().stopProgressBar();
                if (null != boardingDataResponse) {
                    getMvpView().getUserSummaryResponse(boardingDataResponse);
                }
            }
        });
        registerSubscription(subscription);
    }

    public Bitmap decodeFile(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 512;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public String setImageOnHolder(Bitmap photo) {
        byte[] buffer = new byte[4096];
        if (null != photo) {
            buffer = getBytesFromBitmap(photo);
            if (null != buffer) {
                return Base64.encodeToString(buffer, Base64.DEFAULT);
            }
        }
        return null;
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }
}
