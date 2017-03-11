package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.ProfessionalAddEducationActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sheroes on 06/03/17.
 */

public class ProfileOtherFragment extends BaseFragment {

    private final String TAG = LogUtils.makeLogTag(ProfileOtherFragment.class);
    private final String SCREEN_NAME = "Profile_Other_screen";
    ProfileView profileViewlistener;

    @Override
    public void onAttach(Context context) {

        try {
            if (getActivity() instanceof ProfileView) {
                profileViewlistener = (ProfileView) getActivity();
            }
        } catch (InstantiationException exception) {
        }
        super.onAttach(context);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_professional_other, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

    }

    @OnClick(R.id.fab_add_other)
    public  void fab_add_education_click()
    {
        profileViewlistener.callFragment(R.id.fab_add_other);


    }
    @OnClick(R.id.tv_edit_other_textline)

    public  void fab_edit_education_click()
    {
        profileViewlistener.callFragment(R.id.tv_edit_other_textline);


    }

    @OnClick(R.id.tv_profile_other_back)
    public  void Onback_Click()
    {

        profileViewlistener.backListener(R.id.tv_profile_other_back);

    }



}
