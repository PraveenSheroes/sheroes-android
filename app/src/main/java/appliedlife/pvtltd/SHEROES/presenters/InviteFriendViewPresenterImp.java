package appliedlife.pvtltd.SHEROES.presenters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.contactdetail.UserContactDetail;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IInviteFriendView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Praveen on 14/02/18.
 */

public class InviteFriendViewPresenterImp extends BasePresenter<IInviteFriendView> {
    SheroesAppServiceApi sheroesAppServiceApi;

    //region Constructor

    @Inject
    public InviteFriendViewPresenterImp(SheroesAppServiceApi sheroesAppServiceApi) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
    }

    //endregion

    public void getContactsFromMobile(final Context context) {

        getPhoneContacts(context).subscribe(new DisposableObserver<List<UserContactDetail>>() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
            }

            @Override
            public void onNext(List<UserContactDetail> userContactDetails) {
                getMvpView().showContacts(userContactDetails);
            }
        });

    }

    private Observable<List<UserContactDetail>> getPhoneContacts(final Context context) {
        return Observable.create(new ObservableOnSubscribe<List<UserContactDetail>>() {
            @Override
            public void subscribe(ObservableEmitter<List<UserContactDetail>> observableEmitter) throws Exception {
                List<UserContactDetail> userContactDetailsList = new ArrayList<>();
                UserContactDetail userContactDetail = null;
                try {
                    Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                    //Uri emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
                    Cursor cursor = context.getContentResolver().query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,}, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
                    if (cursor != null && cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        while (!cursor.isAfterLast()) {
                            String contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                          //  String emailID = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            userContactDetail = new UserContactDetail();
                            userContactDetail.setName(contactName);
                            userContactDetail.setPhoneNumber(contactNumber);
                            userContactDetailsList.add(userContactDetail);
                            cursor.moveToNext();
                        }
                        cursor.close();
                    }
                } catch (Exception e) {
                    Crashlytics.getInstance().core.logException(e);
                }
                observableEmitter.onNext(userContactDetailsList);
            }

        });
    }
}
