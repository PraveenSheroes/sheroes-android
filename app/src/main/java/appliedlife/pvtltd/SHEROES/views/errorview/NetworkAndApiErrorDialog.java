package appliedlife.pvtltd.SHEROES.views.errorview;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * DialogFragment displayed when timeout in request occurs .
 */
public class NetworkAndApiErrorDialog extends BaseDialogFragment {
    @Bind(R.id.tv_no_conn_desc)
    TextView mTvNoConnDesc;
    @Bind(R.id.tv_try_again)
    TextView mTvTryAgain;
    @Bind(R.id.li_user_deactivate)
    LinearLayout mliUserDeactivate;
    @Bind(R.id.rl_error_msg)
    RelativeLayout mRlErrorMsg;
    @Bind(R.id.tv_user_deactivate_text)
    TextView mTvUserDeactivateText;
    @Bind(R.id.tv_care_sheroes)
    TextView mTvCareSheroes;
    private boolean finishParent;
    private boolean isCancellable;
    private String errorMessage;
    private String isUserDeActivated;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_network_timeout, container, false);
        ButterKnife.bind(this, view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        finishParent = getArguments().getBoolean(DISMISS_PARENT_ON_OK_OR_BACK);
        isCancellable = getArguments().getBoolean(IS_CANCELABLE);
        isUserDeActivated = getArguments().getString(USER_DEACTIVATED);
        errorMessage = getArguments().getString(ERROR_MESSAGE);
        if (StringUtil.isNotNullOrEmptyString(isUserDeActivated) && isUserDeActivated.equalsIgnoreCase("true")) {
            mliUserDeactivate.setVisibility(View.VISIBLE);
            mRlErrorMsg.setVisibility(View.GONE);
            if (StringUtil.isNotNullOrEmptyString(errorMessage)) {
                mTvUserDeactivateText.setText(errorMessage);
            }
            SpannableString content = new SpannableString(getString(R.string.care_sheroes));
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            mTvCareSheroes.setText(content);
        } else {
            mliUserDeactivate.setVisibility(View.GONE);
            mRlErrorMsg.setVisibility(View.VISIBLE);
            if (AppConstants.MARK_AS_SPAM.equalsIgnoreCase(errorMessage) || AppConstants.FACEBOOK_VERIFICATION.equalsIgnoreCase(errorMessage)) {
                mTvTryAgain.setText(getString(R.string.ID_DONE));
            } else {
                mTvTryAgain.setText(getString(R.string.IDS_STR_TRY_AGAIN_TEXT));
            }
            if (StringUtil.isNotNullOrEmptyString(errorMessage)) {
                mTvNoConnDesc.setText(errorMessage);
            }
        }
        setCancelable(isCancellable);
        return view;
    }

    @OnClick(R.id.iv_close)
    public void closeDialog() {
        dismissAllowingStateLoss();
        dismiss();
    }

    @OnClick(R.id.tv_care_sheroes)
    public void careSheroes() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", getString(R.string.care_sheroes), null));
        startActivity(Intent.createChooser(emailIntent, null));
    }

    @OnClick(R.id.tv_try_again)
    public void tryAgainClick() {
        dismissAllowingStateLoss();
        if (finishParent) {
            dismiss();
        } else {
            dismiss();
            getActivity().recreate();
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
                dismissAllowingStateLoss();
                if (finishParent) {
                    dismiss();
                } else {
                    dismiss();
                    getActivity().recreate();
                }
            }
        };
    }

}
