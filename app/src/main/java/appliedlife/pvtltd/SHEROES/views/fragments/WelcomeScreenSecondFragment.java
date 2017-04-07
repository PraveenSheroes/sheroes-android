package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 08/03/17.
 */

public class WelcomeScreenSecondFragment extends BaseFragment {
    private final String TAG = LogUtils.makeLogTag(WelcomeScreenSecondFragment.class);
    private FragmentIntractionWithActivityListner mHomeSearchActivityFragmentIntractionWithActivityListner;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            mActivity = getActivity();
        }
        try {
            if (mActivity instanceof FragmentIntractionWithActivityListner) {
                mHomeSearchActivityFragmentIntractionWithActivityListner = (FragmentIntractionWithActivityListner) getActivity();
            }
        } catch (Fragment.InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
       View view= inflater.inflate(R.layout.welcome_screen_second_fragment, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
    @OnClick(R.id.tv_click_to_join)
    public void clickToJoin()
    {
        mHomeSearchActivityFragmentIntractionWithActivityListner.onSuccessResult(AppConstants.SUCCESS,null);
    }
}