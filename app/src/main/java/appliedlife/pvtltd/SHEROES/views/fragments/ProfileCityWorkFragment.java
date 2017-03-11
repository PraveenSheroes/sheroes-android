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
    private final String SCREEN_NAME = "Profile_City_Work_screen";

    public static ProfileCityWorkFragment createInstance(int itemsCount) {

        ProfileCityWorkFragment profilecityworkFragment = new ProfileCityWorkFragment();

        return profilecityworkFragment;
    }


    @Override
    public void onAttach(Context context) {


        super.onAttach(context);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_professional_travel_client, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

    }

}
