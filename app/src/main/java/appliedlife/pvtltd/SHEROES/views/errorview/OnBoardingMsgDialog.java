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
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 29/01/18.
 */

public class OnBoardingMsgDialog extends BaseDialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.on_boarding_msg_layout, container, false);
        ButterKnife.bind(this, view);
       // getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        return view;
    }

    @OnClick(R.id.iv_close)
    public void closeClick() {
        dismissAllowingStateLoss();
        dismiss();
    }


}
