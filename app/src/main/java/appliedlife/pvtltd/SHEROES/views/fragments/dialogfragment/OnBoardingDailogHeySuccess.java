package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 26-03-2017.
 */

public class OnBoardingDailogHeySuccess extends BaseDialogFragment {

    @Bind(R.id.tv_name)
    TextView mTvName;
    @Inject
    Preference<LoginResponse> userPreference;
    @Bind(R.id.sv_welcome_sheroes_boardinng)
    ScrollView mScrollView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.on_boarding_dailog_hey_success, container, false);
        ButterKnife.bind(this, view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getFirstName())) {
            mTvName.setText(userPreference.get().getUserSummary().getFirstName());
        }
        mScrollView.post(new Runnable() {
            public void run() {
                mScrollView.fullScroll(mScrollView.FOCUS_DOWN);
            }
        });
        mScrollView.scrollTo(0, mScrollView.getBottom() + 1);

        setCancelable(false);
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_ONBOARDING_LETS_START_TALKING));
        return view;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth);
    }

    @OnClick(R.id.iv_hey_success_next)
    public void onHeySuccessNext() {
        dismiss();
    }

}
