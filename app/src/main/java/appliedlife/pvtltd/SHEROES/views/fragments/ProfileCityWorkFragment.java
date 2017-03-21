package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 01/03/17.
 */

public class ProfileCityWorkFragment extends BaseFragment {

    private final String TAG = LogUtils.makeLogTag(ProfileCityWorkFragment.class);
    private ProfileWorkLocationFragmentListener profileWorkLocationFragmentListener;
    private final String SCREEN_NAME = "Profile_City_Work_screen";
    @Bind(R.id.tv_setting_tittle)
    TextView mTv_setting_tittle;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        try {
            if (getActivity() instanceof
                    ProfileWorkLocationFragmentListener) {

                profileWorkLocationFragmentListener = (ProfileCityWorkFragment.ProfileWorkLocationFragmentListener) getActivity();

            }
        } catch (Exception e) {


        }


    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_professional_choose_city, container, false);
        ButterKnife.bind(this, view);
        mTv_setting_tittle.setText(R.string.ID_PREFERRED_WORK_LOCATION);

        return view;
    }

    @OnClick(R.id.iv_back_setting)
    public void callBack()
    {
        profileWorkLocationFragmentListener.locationBack();
    }

    public interface ProfileWorkLocationFragmentListener {

        void onErrorOccurence();

        void locationBack();
    }

}
