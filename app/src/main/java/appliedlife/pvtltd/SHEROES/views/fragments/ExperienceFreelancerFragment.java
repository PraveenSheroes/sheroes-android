package appliedlife.pvtltd.SHEROES.views.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;

/**
 * Created by Dilip.Chaudhary on 13/4/17.
 */
public class ExperienceFreelancerFragment extends BaseWorkExperienceFragment {
    private static final String SCREEN_LABEL = "Experience Freelancer Screen";
    private static final String TAG = "ExperienceFreelancerFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_experience_freelancer, container, false);
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}
