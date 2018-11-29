package appliedlife.pvtltd.SHEROES.presenters;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Address;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IAddressView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_COMMENT_REACTION;

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
        updateAddress(address)
                .compose(this.<BaseResponse>bindToLifecycle())
                .subscribe(new DisposableObserver<BaseResponse>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                 getMvpView().showError(e.getMessage(), ERROR_COMMENT_REACTION);
            }

            @Override
            public void onNext(BaseResponse baseResponse) {
                getMvpView().stopProgressBar();
                if (baseResponse.getStatus().equalsIgnoreCase(AppConstants.SUCCESS)) {
                    getMvpView().finishActivity();
                }
            }
        });
    }

    public Observable<BaseResponse> updateAddress(Address address) {
        return sheroesAppServiceApi.updateAddress(address)
                .map(new Function<BaseResponse, BaseResponse>() {
                    @Override
                    public BaseResponse apply(BaseResponse baseResponse) {
                        return baseResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //endregion
}
