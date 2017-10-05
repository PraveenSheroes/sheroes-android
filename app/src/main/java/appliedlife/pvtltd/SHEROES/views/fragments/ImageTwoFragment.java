package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;

/**
 * Created by sheroes on 08/03/17.
 */

public class ImageTwoFragment extends BaseFragment {
    private static final String SCREEN_LABEL = "Image Two Screen";
    public ImageTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.welcome_screen_second_fragment, container, false);
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}
