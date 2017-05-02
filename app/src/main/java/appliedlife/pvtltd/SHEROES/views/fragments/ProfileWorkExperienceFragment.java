package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.ProfessionalWorkExperienceActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 01/03/17.
 */

public class ProfileWorkExperienceFragment  extends BaseFragment {

    private final String TAG = LogUtils.makeLogTag(ProfileWorkExperienceFragment.class);
    private final String SCREEN_NAME = "Profile_work_experience_screen";
    ProfileView profileViewlistener;
    @Bind(R.id.a1_profile_workexperiences)
    AppBarLayout ma1ProfileWorkexperiences;
    @Bind(R.id.tv_workexperience)
    TextView mtvWorkexperience;


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
        View view = inflater.inflate(R.layout.fragment_professional_workexperience, container, false);
        ButterKnife.bind(this, view);




        ma1ProfileWorkexperiences.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mtvWorkexperience.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    mtvWorkexperience.setVisibility(View.GONE);
                    isShow = false;
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

    }

    @OnClick(R.id.fab_add_other_work_experience)

    public  void fabon_click()
    {
        Intent intent = new Intent(getActivity(), ProfessionalWorkExperienceActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.tv_profile_workexperience_back)
    public  void Onback_Click()
    {

        profileViewlistener.onBackPressed(R.id.tv_profile_workexperience_back);

    }

}
