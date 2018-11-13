package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.views.activities.LoginActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ResetPasswordSuccessFragment extends BaseFragment {
    private static final String SCREEN_LABEL = "Reset Password Success Screen";
    @Bind(R.id.reset_pwd_success_text)
    TextView tvResetPwd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_reset_password_success, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if(bundle!=null && bundle.getString(AppConstants.EMAIL)!=null){
            int index = bundle.getString(AppConstants.EMAIL).indexOf(AppConstants.AT_THE_RATE_OF);
            if(index < bundle.getString(AppConstants.EMAIL).length()){
                String domain = bundle.getString(AppConstants.EMAIL).substring(index);
                tvResetPwd.setText(getString(R.string.ID_RESET_PASSWORD_SUCCESS_TEXT_PART_1, domain) + getString(R.string.ID_RESET_PASSWORD_SUCCESS_TEXT_PART_2));
            }
        }

        return view;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.back_login)
    public void onLoginLinkClick(){
        ((LoginActivity)getActivity()).renderLoginFragmentView();
    }

    @OnClick(R.id.iv_login_back)
    public void backOnClick() {
        ((LoginActivity)getActivity()).renderLoginFragmentView();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

}
