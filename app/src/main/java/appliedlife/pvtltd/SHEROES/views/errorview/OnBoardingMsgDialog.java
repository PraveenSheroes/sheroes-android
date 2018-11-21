package appliedlife.pvtltd.SHEROES.views.errorview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 29/01/18.
 */

public class OnBoardingMsgDialog extends BaseDialogFragment {
    // region public and lifecycle methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.on_boarding_msg_layout, container, false);
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        ButterKnife.bind(this, view);
        setCancelable(true);
        return view;
    }

    @OnClick(R.id.iv_close)
    public void closeClick() {
        dismissAllowingStateLoss();
        dismiss();
    }
    //endregion
}
