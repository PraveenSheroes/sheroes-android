package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sheroes on 07/03/17.
 */

public class ProfessionalEditBasicDetailsFragment extends BaseFragment {
    private final String TAG = LogUtils.makeLogTag(ProfessionalEditBasicDetailsFragment.class);
    private final String SCREEN_NAME = "Professional_Edit_Basic_Details_screen";


    @Bind(R.id.tv_setting_tittle)
    TextView mTv_setting_tittle;
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
        View view = inflater.inflate(R.layout.fragment_professional_basic_details, container, false);
        ButterKnife.bind(this, view);
        mTv_setting_tittle.setText(R.string.ID_BASICDETAILS);
        return view;
    }

    @OnClick(R.id.iv_back_setting)

    public  void Onback_Click()
    {
        profileViewlistener.backListener(R.id.tv_profile_education_back);
    }
}
