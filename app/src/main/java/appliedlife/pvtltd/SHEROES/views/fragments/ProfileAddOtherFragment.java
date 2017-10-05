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
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sheroes on 07/03/17.
 */

public class ProfileAddOtherFragment extends BaseFragment {
    private static final String SCREEN_LABEL = "Profile Add Other Screen";
    @Bind(R.id.tv_setting_tittle)
    TextView mTv_setting_tittle;
    @Bind(R.id.iv_back_setting)
    ImageView mIv_back_setting;
    ProfileView profileViewlistener;
    @Bind(R.id.tv_add_other_text)
    TextView mTv_add_other_text;
    @Bind(R.id.btn_save_addinational_activities)
    TextView mBtn_save_addinational_activities;
    int flag=0;




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
        View view = inflater.inflate(R.layout.fragment_professional_add_edit_other, container, false);
        ButterKnife.bind(this, view);
        mTv_setting_tittle.setText(R.string.ID_OTHER);
        return view;

    }


    @OnClick(R.id.iv_back_setting)

    public  void Onback_Click()
    {

      //  profileViewlistener.onBackPressed(R.id.tv_profile_education_back);



    }

    @OnClick(R.id.tv_add_other_text)



    public  void On_add_other_textfield_Click() {

        if (flag == 0) {

            mBtn_save_addinational_activities.setBackgroundColor(getResources().getColor(R.color.red));
            flag=1;

        } else
        {

            mBtn_save_addinational_activities.setBackgroundColor(getResources().getColor(R.color.search_tab_unselected_text));
            flag=0;
        }

    }


    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}
