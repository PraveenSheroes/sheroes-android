package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class ProfileAboutMeFragment extends BaseFragment{

        private final String TAG = LogUtils.makeLogTag(ProfessionalEditBasicDetailsFragment.class);
        private final String SCREEN_NAME = "Profile_About_Me_screen";


        @Bind(R.id.tv_about_me_tittle)
        TextView mTv_about_me_tittle;
        @Bind(R.id.et_write_about_me)
        TextView mEt_write_about_me;
        @Bind(R.id.btn_save_about_me_details)
        Button mBtn_save_about_me_details;
        ProfileView profileViewlistener;
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
            View view = inflater.inflate(R.layout.fragment_personal_aboutme, container, false);
            ButterKnife.bind(this, view);
           mTv_about_me_tittle.setText(R.string.ID_ABOUT_ME);
            return view;
        }



    @OnClick(R.id.et_write_about_me)

    public  void OnEdit_Text_Click()
    {

        if (flag == 0) {

            mBtn_save_about_me_details.setBackgroundColor(getResources().getColor(R.color.red));
            flag=1;

        } else

        {
            mBtn_save_about_me_details.setBackgroundColor(getResources().getColor(R.color.search_tab_unselected_text));
            flag=0;
        }

    }

     @OnClick(R.id.tv_about_me_back)

       public  void Onback_Click()
        {
            profileViewlistener.backListener(R.id.tv_about_me_back);


        }
}