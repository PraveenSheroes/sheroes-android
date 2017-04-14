package appliedlife.pvtltd.SHEROES.views.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;

/**
 * Created by Dilip.Chaudhary on 13/4/17.
 */
public class ExperienceSelfEmployedFragment extends BaseWorkExperienceFragment {
    private static final String TAG = "ExperienceSelfEmployedFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self_employed, container, false);
        return view;
    }

}
