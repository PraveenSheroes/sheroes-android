package appliedlife.pvtltd.SHEROES.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.errorview.NetworkAndApiErrorDialog;

public class ErrorUtil {
    //region member variables
    private static ErrorUtil mInstance;
    private boolean mIsDestroyed = false;
    //endregion


    public static synchronized ErrorUtil getInstance() {
        if (mInstance == null) {
            mInstance = new ErrorUtil();
        }
        return mInstance;
    }


    private ErrorUtil() {
    }

    public void showErrorDialogOnUserAction(Context context, boolean finishParentOnBackOrTryagain, boolean isCancellable, String errorMessage, String isDeactivated) {
        NetworkAndApiErrorDialog fragment = (NetworkAndApiErrorDialog) ((Activity) context).getFragmentManager().findFragmentByTag(NetworkAndApiErrorDialog.class.getName());
        if (fragment == null) {
            fragment = new NetworkAndApiErrorDialog();
            Bundle b = new Bundle();
            b.putBoolean(BaseDialogFragment.DISMISS_PARENT_ON_OK_OR_BACK, finishParentOnBackOrTryagain);
            b.putBoolean(BaseDialogFragment.IS_CANCELABLE, isCancellable);
            b.putString(BaseDialogFragment.ERROR_MESSAGE, errorMessage);
            b.putString(BaseDialogFragment.USER_DEACTIVATED, isDeactivated);
            fragment.setArguments(b);
        }
        if (!fragment.isVisible() && !fragment.isAdded() && !((Activity) context).isFinishing() && !mIsDestroyed) {
            fragment.show(((Activity) context).getFragmentManager(), NetworkAndApiErrorDialog.class.getName());
        }
    }

    public void onShowErrorDialog(Context context, String errorReason, FeedParticipationEnum feedParticipationEnum) {
        if (context == null)
            return;
        if (StringUtil.isNotNullOrEmptyString(errorReason)) {
            switch (errorReason) {
                case AppConstants.CHECK_NETWORK_CONNECTION:
                    showErrorDialogOnUserAction(context, true, false, context.getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION), "");
                    break;
                case AppConstants.MARK_AS_SPAM:
                    showErrorDialogOnUserAction(context, true, false, errorReason, "");
                    break;
                case AppConstants.HTTP_401_UNAUTHORIZED_ERROR:
                case AppConstants.HTTP_401_UNAUTHORIZED:
                    showErrorDialogOnUserAction(context, true, false, context.getString(R.string.IDS_UN_AUTHORIZE), "");
                    break;
                default: {
                    showErrorDialogOnUserAction(context, true, false, context.getString(R.string.ID_GENERIC_ERROR), "");
                }
            }
        } else {
            showErrorDialogOnUserAction(context, true, false, context.getString(R.string.ID_GENERIC_ERROR), "");
        }
    }
}
