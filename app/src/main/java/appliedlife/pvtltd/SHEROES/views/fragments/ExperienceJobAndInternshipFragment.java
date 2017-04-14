package appliedlife.pvtltd.SHEROES.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;

/**
 * Created by Dilip.Chaudhary on 13/4/17.
 */
public class ExperienceJobAndInternshipFragment extends BaseWorkExperienceFragment {
    private static final String TAG = "ExperienceJobAndInternshipFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_experience_job_and_internship, container, false);
    }

}
