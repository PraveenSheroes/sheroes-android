package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
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
 * Created by priyanka on 20/02/17.
 */

public class ProfileTravelClientFragment extends BaseFragment {

private final String TAG = LogUtils.makeLogTag(ProfileTravelClientFragment.class);
private final String SCREEN_NAME = "Profile_Travel_screen";



  public static ProfileTravelClientFragment createInstance(int itemsCount) {

          ProfileTravelClientFragment profiletravelFragment = new ProfileTravelClientFragment();

      return profiletravelFragment;
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
