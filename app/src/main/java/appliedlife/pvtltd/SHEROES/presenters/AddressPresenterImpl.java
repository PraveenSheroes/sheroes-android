package appliedlife.pvtltd.SHEROES.presenters;


import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.post.Address;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IAddressView;

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
      /*  if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, null);
            return;
        }
        //  getMvpView().startProgressBar();
        Subscription subscription = addAddressFromModel(commentReactionRequestPojo).subscribe(new Subscriber<CommentAddDelete>() {
            @Override
            public void onCompleted() {
                // getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                //   getMvpView().stopProgressBar();
                getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_UNABLE_TO_EDIT_DELETE), ERROR_COMMENT_REACTION);
            }

            @Override
            public void onNext(CommentAddDelete commentResponsePojo) {
                //  getMvpView().stopProgressBar();

            }
        });
        registerSubscription(subscription);*/
    }
    //endregion
}
