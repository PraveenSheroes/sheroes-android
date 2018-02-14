package appliedlife.pvtltd.SHEROES.basecomponents;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Presenter which provide context for every view which is currenty present.
 */
public interface SheroesPresenter<V extends BaseMvpView> {

    void attachView(V mvpView);

    void detachView();

    void onCreate();

    void onDestroy();

    void onStart();

    void onStop();

    void onPause();

    void onResume();

    void onAttach();

    void onDetach();

}
