package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 31/01/17.
 */

public class PreferencesDeactiveAccountDialogFragment extends BaseDialogFragment {
    private boolean finishParent;
    private NewCloseListener mHomeActivityIntractionListner;
    private final String TAG = LogUtils.makeLogTag(SettingPreferencesDeactiveAccountFragment.class);



    PreferencesDeactiveAccountDialogFragment(SettingPreferencesDeactiveAccountFragment context) {
        try {
            if (context instanceof NewCloseListener) {
                mHomeActivityIntractionListner = (NewCloseListener)context;
            }
        } catch (Fragment.InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_deactive_accountdialog, container, false);
        ButterKnife.bind(this, view);
        setCancelable(true);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }
    }


@OnClick(R.id.tv_dialog_close)
public void onBack()
{
    getDialog().cancel();
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
    public interface NewCloseListener {
        void onErrorOccurence();
        void onClose();
    }
}
