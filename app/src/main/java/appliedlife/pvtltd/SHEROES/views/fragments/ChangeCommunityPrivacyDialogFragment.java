package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 18-01-2017.
 */

public class ChangeCommunityPrivacyDialogFragment extends BaseDialogFragment {
    private final String TAG = LogUtils.makeLogTag(ChangeCommunityPrivacyDialogFragment.class);
    private boolean closeOpen;
    @Bind(R.id.tv_change_community_cancel)
    TextView tv_cance;
    @Bind(R.id.tv_change_community_continue)
    TextView tvContinue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        closeOpen = getArguments().getBoolean(DISMISS_PARENT_ON_OK_OR_BACK);
        if (closeOpen) {
            view = inflater.inflate(R.layout.close_change_community_privacy, container, false);
        } else {
            view = inflater.inflate(R.layout.open_change_community_privacy, container, false);
        }
        ButterKnife.bind(this, view);
        setCancelable(false);
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismissAllowingStateLoss();//dismiss dialog on back button press
                if (closeOpen) {
                    getActivity().finish();
                }
            }
        };
    }

    @OnClick(R.id.tv_change_community_cancel)
    public void cancelClick() {
        getDialog().cancel();
        // mHomeActivityIntractionListner.onClose();
    }

    @OnClick(R.id.tv_change_community_continue)
    public void continueClick() {
        getDialog().cancel();
    }

}
