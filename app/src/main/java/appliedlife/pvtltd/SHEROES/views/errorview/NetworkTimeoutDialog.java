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
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * DialogFragment displayed when timeout in request occurs .
 */
public class NetworkTimeoutDialog extends BaseDialogFragment {
    @Bind(R.id.tv_no_conn_desc)
    TextView mTvNoConnDesc;
    private boolean finishParent;
    private boolean isCancellable;
    private String errorMessage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_network_timeout, container, false);
        ButterKnife.bind(this,view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        finishParent = getArguments().getBoolean(DISMISS_PARENT_ON_OK_OR_BACK);
        isCancellable = getArguments().getBoolean(IS_CANCELABLE);
        errorMessage = getArguments().getString(ERROR_MESSAGE);
        if(StringUtil.isNotNullOrEmptyString(errorMessage)) {
            mTvNoConnDesc.setText(errorMessage);
        }
        setCancelable(isCancellable);
        return view;
    }

    @OnClick(R.id.tv_try_again)
    public void tryAgainClick()
    {
        dismissAllowingStateLoss();
        if (finishParent) {
            dismiss();
            //  getActivity().finish();
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
                    dismiss();
                }
            }
        };
    }

}
