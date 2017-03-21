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
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 20/02/17.
 */

public class ProfileTravelClientFragment extends BaseFragment{

private final String TAG = LogUtils.makeLogTag(ProfileTravelClientFragment.class);
private final String SCREEN_NAME = "Profile_Travel_screen";
    private ProfileTravelClientFragmentListener profileTravelClientFragmentListener;

    @Bind(R.id.tv_setting_tittle)
    TextView mTv_setting_tittle;



@Override
public void onAttach(Context context) {
    super.onAttach(context);
    try {
        if (getActivity() instanceof ProfileTravelClientFragmentListener) {

            profileTravelClientFragmentListener = (ProfileTravelClientFragmentListener) getActivity();

        }
    } catch (Exception e) {


    }

}



@Nullable
@Override
public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_professional_travel_client, container, false);
        ButterKnife.bind(this, view);
        mTv_setting_tittle.setText(R.string.ID_TRAVEL_FLEXIBILITY);
       return view;
        }

@OnClick(R.id.iv_back_setting)
public void callBack()
{
    profileTravelClientFragmentListener.clintBack();
}

    public interface ProfileTravelClientFragmentListener {

        void onErrorOccurence();

        void clintBack();
    }



}
