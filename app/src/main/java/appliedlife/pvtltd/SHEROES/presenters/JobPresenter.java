package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.JobModel;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobApplyRequest;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobApplyResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.JobView;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Ajit Kumar on 11-03-2017.
 */

public class JobPresenter extends BasePresenter<JobView> {
    private final String TAG = LogUtils.makeLogTag(SearchModulePresenter.class);
    JobModel jobModel;
    SheroesApplication mSheroesApplication;
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    public JobPresenter(JobModel jobModel, SheroesApplication mSheroesApplication, Preference<LoginResponse> mUserPreference) {
        this.jobModel = jobModel;
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


    public void getJobApply(final JobApplyRequest jobApplyRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = jobModel.getJobApply(jobApplyRequest).subscribe(new Subscriber<JobApplyResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().showError(AppConstants.ERROR_APP_CLOSE,0);
                getMvpView().showNwError();
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(JobApplyResponse jobApplyResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getJobApplySuccess(jobApplyResponse);
            }
        });
        registerSubscription(subscription);

    }
    public void onStop() {
        detachView();
    }
}