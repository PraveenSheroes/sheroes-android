package appliedlife.pvtltd.SHEROES.basecomponents;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Base class for all common methods in the views and presenter.
 * this interface will be implemented in all child classes.
 */
public interface BaseMvpView {
    void startProgressBar();
    void stopProgressBar();
    void startNextScreen();
    void showError(String s);
}
