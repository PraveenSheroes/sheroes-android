package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.ButterKnife;

/**
 * Created by priyanka on 08/03/17.
 */

public class WelcomeScreenThirdFragment extends BaseFragment {
    private static final String SCREEN_LABEL = "Welcome Third Screen";
    private final String TAG = LogUtils.makeLogTag(WelcomeScreenThirdFragment.class);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.welcome_screen_third_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }
   /* @OnClick(R.id.tv_click_to_join)
    public void clickToJoin()
    {
        mHomeSearchActivityFragmentIntractionWithActivityListner.onSuccessResult(AppConstants.SUCCESS,null);
    }*/
}
