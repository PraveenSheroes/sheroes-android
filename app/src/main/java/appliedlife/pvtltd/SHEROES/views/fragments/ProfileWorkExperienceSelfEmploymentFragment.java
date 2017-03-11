package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.ButterKnife;

/**
 * Created by sheroes on 08/03/17.
 */

public class ProfileWorkExperienceSelfEmploymentFragment extends BaseFragment{

    private final String TAG = LogUtils.makeLogTag(ProfileWorkExperienceSelfEmploymentFragment.class);
    private final String SCREEN_NAME = "Profile_work_exp_self_empolyment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_workexperience_selfempolyment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


}
