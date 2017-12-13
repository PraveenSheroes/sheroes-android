package appliedlife.pvtltd.SHEROES.presenters;


import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentAddDelete;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.post.Address;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IAddressView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ujjwal on 04/05/17.
 */

public class AddressPresenterImpl extends BasePresenter<IAddressView> {
    SheroesAppServiceApi sheroesAppServiceApi;

    //region Constructor

    @Inject
    public AddressPresenterImpl(SheroesAppServiceApi sheroesAppServiceApi) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
    }

    //region presenter methods
    public void postAddress(final Address address) {
        AppUtils appUtils = AppUtils.getInstance();
        address.setAppVersion(appUtils.getAppVersionName());
        address.setDeviceUniqueId(appUtils.getDeviceId());
        address.setCloudMessagingId(appUtils.getCloudMessaging());

        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, null);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = updateAddress(address).subscribe(new Subscriber<BaseResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                // getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_UNABLE_TO_EDIT_DELETE), ERROR_COMMENT_REACTION);
            }

            @Override
            public void onNext(BaseResponse baseResponse) {
                getMvpView().stopProgressBar();
                if (baseResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                    getMvpView().finishActivity();
                }

            }
        });
        registerSubscription(subscription);
    }

    public Observable<BaseResponse> updateAddress(Address address) {
        return sheroesAppServiceApi.updateAddress(address)
                .map(new Func1<BaseResponse, BaseResponse>() {
                    @Override
                    public BaseResponse call(BaseResponse baseResponse) {
                        return baseResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //endregion
}
