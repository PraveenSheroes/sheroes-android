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
 * Created by Praveen_Singh on 16-04-2017.
 */

public class WelcomeScreenFourthFragment  extends BaseFragment {
    private final String TAG = LogUtils.makeLogTag(WelcomeScreenThirdFragment.class);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.welcome_screen_fourth_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
  /*  @OnClick(R.id.tv_click_to_join)
    public void clickToJoin()
    {
        mHomeSearchActivityFragmentIntractionWithActivityListner.onSuccessResult(AppConstants.SUCCESS,null);
    }*/
}