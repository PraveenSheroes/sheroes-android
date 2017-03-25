package appliedlife.pvtltd.SHEROES.basecomponents;

import android.app.DialogFragment;
import android.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;


/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Base Dialog class handling fadein and fadeout animations
 */
public class BaseDialogFragment extends DialogFragment implements BaseMvpView {
    private final String TAG = LogUtils.makeLogTag(BaseDialogFragment.class);
    public static final String DISMISS_PARENT_ON_OK_OR_BACK = "DISMISS_PARENT_ON_OK_OR_BACK";
    public static final String IS_CANCELABLE = "is_cancelable";
    public static final String ERROR_MESSAGE = "error_msg";
    @Override
    public void onStart() {
        super.onStart();
        // safety check
        if (getDialog() == null) {
            return;
        }
        getDialog().getWindow().setWindowAnimations(R.style.dialog_animation_fade);
    }

    /**
     * @param manager
     * @param tag     TO avoid illegal state exception.
     */
    @Override
    public void show(FragmentManager manager, String tag) {
        manager.beginTransaction().add(this, tag).commitAllowingStateLoss();
    }





    @Override
    public void dismiss() {
        this.dismissAllowingStateLoss();
    }


    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String s, int errorFor) {

    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }
}
