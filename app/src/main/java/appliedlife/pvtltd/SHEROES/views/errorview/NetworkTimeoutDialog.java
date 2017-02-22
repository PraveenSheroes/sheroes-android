package appliedlife.pvtltd.SHEROES.views.errorview;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;


/**
 * DialogFragment displayed when timeout in request occurs .
 */
public class NetworkTimeoutDialog extends BaseDialogFragment implements View.OnClickListener {
    private TextView tryAgain;
    private boolean finishParent;
    public static final String DISMISS_PARENT_ON_OK_OR_BACK = "DISMISS_PARENT_ON_OK_OR_BACK";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_network_timeout, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        tryAgain = (TextView) view.findViewById(R.id.tv_try_again);
        tryAgain.setOnClickListener(this);
        finishParent = getArguments().getBoolean(DISMISS_PARENT_ON_OK_OR_BACK, false);
        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == tryAgain.getId()) {
            dismissAllowingStateLoss();
            if (finishParent) {
              getActivity().finish();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismissAllowingStateLoss();//dismiss dialog on back button press
                if (finishParent) {
                    getActivity().finish();
                }
            }
        };
    }

}
