package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.SHEModel;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQSRequest;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQSResponse;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMemberRequest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SHEView;
import rx.Subscriber;
import rx.Subscription;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_GET_FAQS;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_GET_ICC_MEMBERS;

/**
 * Created by SHEROES 005 on 02-Jun-17.
 */

public class SHEPresenter extends BasePresenter<SHEView> {

    private SheroesApplication mSheroesApplication;
    private SHEModel mSheModel;

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    public SHEPresenter(SHEModel sheModel, SheroesApplication mSheroesApplication, Preference<LoginResponse> mUserPreference) {
        this.mSheModel = sheModel;
        this.mSheroesApplication = mSheroesApplication;
        this.mUserPreference = mUserPreference;
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }

    public void onStop() {
        detachView();
    }

    public void getAllFAQS(FAQSRequest faqsRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_GET_FAQS);
            return;
        }
        Subscription subscription = mSheModel.getAllFAQS(faqsRequest).subscribe(new Subscriber<FAQSResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_SERVER_PROBLEM),ERROR_GET_FAQS);

            }

            @Override
            public void onNext(FAQSResponse faqsResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getAllFAQs(faqsResponse);
            }
        });
        registerSubscription(subscription);
    }

    public void getAllICCMembers(ICCMemberRequest iccMemberRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, ERROR_GET_ICC_MEMBERS);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mSheModel.getAllICCMembers(iccMemberRequest).subscribe(new Subscriber<ICCMemberListResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(mSheroesApplication.getString(R.string.ID_SERVER_PROBLEM),ERROR_GET_ICC_MEMBERS);

            }

            @Override
            public void onNext(ICCMemberListResponse iccMemberListResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getAllICCMembers(iccMemberListResponse);
            }
        });
        registerSubscription(subscription);
    }

}
