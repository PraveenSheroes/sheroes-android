package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 24-05-2017.
 */

public class ChallengeUpdateProgressDialogFragment extends BaseDialogFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(ChallengeUpdateProgressDialogFragment.class);
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.checkbox_just_started)
    CheckBox checkBoxJustStarted;
    @Bind(R.id.checkbox_half_done)
    CheckBox checkBoxHalfDone;
    @Bind(R.id.checkbox_almost_there)
    CheckBox checkBoxAlmostThere;
    @Bind(R.id.checkbox_completed)
    CheckBox checkBoxCompleted;
    private String passedString;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.challenge_update_progress, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (null != getArguments()) {
            Bundle challenge = getArguments();
            passedString =challenge.getString(AppConstants.CHALLENGE_SUB_TYPE);
        }
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismissAllowingStateLoss();//dismiss dialog on back button press
                dismiss();
            }
        };
    }


    @OnClick(R.id.checkbox_just_started)
    public void onCheckJustStartedClick() {
        checkBoxJustStarted.isChecked();
    }

    @OnClick(R.id.checkbox_half_done)
    public void onCheckHalfDoneClick() {
        checkBoxHalfDone.isChecked();
    }

    @OnClick(R.id.checkbox_almost_there)
    public void onCheckAlmostThereClick() {
        checkBoxAlmostThere.isChecked();
    }

    @OnClick(R.id.checkbox_completed)
    public void onCheckCompletedClick() {
        checkBoxCompleted.isChecked();
    }

}
