package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.views.activities.LoginActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sheroes on 08/03/17.
 */

public class IntroOneFragment extends BaseFragment {

    public IntroOneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome_screen1, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @OnClick(R.id.tv_click_to_join)
    public void onJoinFbClick()
    {
        Intent login=new Intent(getActivity(), LoginActivity.class);
        startActivity(login);
    }
}
