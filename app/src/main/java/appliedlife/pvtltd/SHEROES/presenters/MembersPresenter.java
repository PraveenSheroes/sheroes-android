package appliedlife.pvtltd.SHEROES.presenters;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.MemberListModel;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.AllMembersView;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Ajit Kumar on 03-02-2017.
 */

public class MembersPresenter extends BasePresenter<AllMembersView> {
    private final String TAG = LogUtils.makeLogTag(SearchModulePresenter.class);
    MemberListModel mCommentReactionModel;
    SheroesApplication mSheroesApplication;
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    public MembersPresenter(MemberListModel memberListModel, SheroesApplication mSheroesApplication, Preference<LoginResponse> mUserPreference) {
        this.mCommentReactionModel = memberListModel;
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


    public void getAllMembers(MemberRequest memberRequest) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showNwError();
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mCommentReactionModel.getMemberList(memberRequest).subscribe(new Subscriber<MemberListResponse>() {
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
            public void onNext(MemberListResponse memberListResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getAllMembers(memberListResponse.getMembers());
            }
        });
        registerSubscription(subscription);

    }
    public void onStop() {
        detachView();
    }
}