package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka
 * Setting_About_screen
 */

public class SettingAboutFragment extends BaseFragment implements SettingView {

    private final String TAG = LogUtils.makeLogTag(SettingAboutFragment.class);

    @Bind(R.id.iv_back_setting)
    ImageView miv_back_setting;
    @Bind(R.id.tv_setting_tittle)
    TextView mtv_setting_tittle;
    SettingView settingViewlistener;

    @Override
    public void onAttach(Context context){


        super.onAttach(context);
        try {
            if (getActivity() instanceof SettingView) {
                settingViewlistener = (SettingView) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_setting_about, container, false);
        ButterKnife.bind(this, view);
        mtv_setting_tittle.setText(R.string.ID_ABOUT);
        return view;
    }

    //Open setting_preferences_Activity
    @OnClick(R.id.iv_back_setting)
    public void onBackClick()
    {
        settingViewlistener.backListener(R.id.iv_back_setting);

    }


    @Override
    public void showNwError() {

    }

    @Override
    public void backListener(int id) {

    }

    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String s) {

    }
    public interface settingFragmentCallBack
    {
        void callBackSettingActivity(int id);
    }
}