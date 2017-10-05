package appliedlife.pvtltd.SHEROES.views.fragments;

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
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka
 * Setting_About_screen
 */

public class SettingAboutFragment extends BaseFragment {
    private static final String SCREEN_LABEL = "Setting About Screen";
    private final String TAG = LogUtils.makeLogTag(SettingAboutFragment.class);

    @Bind(R.id.iv_back_setting)
    ImageView mIvBackSetting;
    @Bind(R.id.tv_setting_tittle)
    TextView mTvSettingTittle;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_setting_about, container, false);
        ButterKnife.bind(this, view);
        mTvSettingTittle.setText(R.string.ID_ABOUT);
        return view;
    }

    //Open setting_preferences_Activity
    @OnClick(R.id.iv_back_setting)
    public void onBackClick() {
        ((HomeActivity) getActivity()).getSupportFragmentManager().popBackStack();

    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}
