package appliedlife.pvtltd.SHEROES.social;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;

import appliedlife.pvtltd.SHEROES.R;


public class CustomSocialDialog {

	private int theme = android.R.style.Theme_Holo_Light_Dialog_NoActionBar;
	public static final int LOGGING_IN_DIALOG = 1;
	private Context mContext;
	int id;

	public CustomSocialDialog(Context mContext, int id) {
		this.mContext = mContext;
		this.id = id;
	}

	public Dialog createCustomDialog() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			theme = android.R.style.Theme_Dialog;
		}
		Dialog dialog = null;
		try {
			switch (id) {
			case LOGGING_IN_DIALOG:
				dialog = new Dialog(mContext, theme);
				dialog.setContentView(R.layout.dialog_google_login_wait);
				dialog.setCanceledOnTouchOutside(false);
				break;
			default:
				break;
			}
			if (dialog != null) {
				dialog.show();
			}
			return dialog;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
