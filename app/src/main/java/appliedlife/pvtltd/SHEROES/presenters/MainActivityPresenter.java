package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer.NavMenuItem;
import appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer.NavigationDrawerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer.NavigationItems;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.MainActivityNavDrawerView;
import io.reactivex.Observable;


import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_NAV_DRAWER;

/**
 * Created by ravi on 01/12/17.
 */

public class MainActivityPresenter extends BasePresenter<MainActivityNavDrawerView> {
    SheroesApplication mSheroesApplication;
    private SheroesAppServiceApi sheroesAppServiceApi;

    @Inject
    public MainActivityPresenter(SheroesApplication mSheroesApplication, SheroesAppServiceApi sheroesAppServiceApi) {
        this.mSheroesApplication = mSheroesApplication;
        this.sheroesAppServiceApi=sheroesAppServiceApi;
    }


    //To get navigation drawer items
    public void getNavigationDrawerOptions(NavigationDrawerRequest navigationDrawerRequest) { //Request to get navigation drawer options
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().getNavigationDrawerItemsFailed();
            return;
        }

        getNavigationDrawerItemsResponseInModel(navigationDrawerRequest)
                .compose(this.<NavigationItems>bindToLifecycle())
                .subscribe(new DisposableObserver<NavigationItems>() {
            @Override
            public void onComplete() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                if(getMvpView()!=null) {
                     getMvpView().getNavigationDrawerItemsFailed();
                }

            }

            @Override
            public void onNext(NavigationItems navigationItems) {
                getMvpView().stopProgressBar();
                if (null != navigationItems) {
                    switch (navigationItems.getStatus())
                    {
                        case AppConstants.SUCCESS:
                            List<NavMenuItem> navMenuItems = navigationItems.getMenuItems();
                            Collections.sort(navMenuItems, new Comparator<NavMenuItem>() { //Sort based on display order
                                public int compare(NavMenuItem obj1, NavMenuItem obj2) {
                                    return obj1.getDisplayOrder().compareTo(obj2.getDisplayOrder());
                                }
                            });
                            getMvpView().getNavigationDrawerItemsSuccess(navMenuItems);
                            break;
                        case AppConstants.FAILED:
                            getMvpView().getNavigationDrawerItemsFailed();
                            break;
                        default:
                    }

                }
            }
        });

    }

    //To get the list of navigation drawer options
    private Observable<NavigationItems> getNavigationDrawerItemsResponseInModel(NavigationDrawerRequest navigationDrawerRequest) {
        return sheroesAppServiceApi.getNavigationDrawerItems(navigationDrawerRequest)
                .map(new Function<NavigationItems, NavigationItems>() {
                    @Override
                    public NavigationItems apply(NavigationItems navigationItems) {
                        return navigationItems;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
