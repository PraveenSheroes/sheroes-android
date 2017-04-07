package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.FaceBookOpenActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 03-04-2017.
 */

public class FacebookErrorDialog extends BaseDialogFragment {
    @Bind(R.id.tv_ok)
    TextView mTvCacel;
    @Bind(R.id.tv_message)
    TextView mTvMessage;
    int callFor = 0;
    String message;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.facebook_error_dialog, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            callFor = getArguments().getInt(AppConstants.FACEBOOK_VERIFICATION);
            message = getArguments().getString(AppConstants.SHEROES_AUTH_TOKEN);
        }
        if(StringUtil.isNotNullOrEmptyString(message)) {
            mTvMessage.setText(message);
        }
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        return view;
    }

    @OnClick(R.id.tv_ok)
    public void tryAgainClick() {
        if (callFor == AppConstants.NO_REACTION_CONSTANT) {
            ((FaceBookOpenActivity) getActivity()).backToWelcome();
        } else {
            dismiss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth);
    }


}
