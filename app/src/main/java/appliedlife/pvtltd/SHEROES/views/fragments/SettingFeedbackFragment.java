package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka.
 */


public class SettingFeedbackFragment extends BaseFragment implements SettingView{

    private final String TAG = LogUtils.makeLogTag(SettingFeedbackFragment.class);
    private final String SCREEN_NAME = "Setting_feedback_page";


    @Bind(R.id.tv_skip)
    TextView Mtextviewskip;
    @Bind(R.id.sc1)
    ScrollView Msc1;
    @Bind(R.id.et_write_comment)
    EditText Met_write_comment;
    @Bind(R.id.dialog_ratingbar)
    RatingBar Mdialog_ratingbar;
    @Bind(R.id.iv_back_setting)
    ImageView miv_back_setting;
    @Bind(R.id.tv_setting_tittle)
    TextView mtv_setting_tittle;
    settingFragmentCallBack msettingFragmentCallBack;
    SettingView settingViewlistener;

    @Override
    public void onAttach(Context context){


        super.onAttach(context);
        try {
            if (getActivity() instanceof SettingView) {
                settingViewlistener = (SettingView) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_setting_feedback, container, false);
        ButterKnife.bind(this, view);
        mtv_setting_tittle.setText(R.string.ID_FEEDBACK);



        //on tuch star...
        Mdialog_ratingbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float touchPositionX = event.getX();
                    float width = Mdialog_ratingbar.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    int stars = (int)starsf + 1;
                    Mdialog_ratingbar.setRating(stars);
                    Toast.makeText(getActivity(), String.valueOf(Mdialog_ratingbar.getRating()), Toast.LENGTH_SHORT).show();
                    v.setPressed(false);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setPressed(false);
                }

                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setPressed(false);
                }
                Mtextviewskip.setVisibility(View.GONE);
                Msc1.setBackgroundColor(Color.WHITE);
                Met_write_comment.setVisibility(View.VISIBLE);


                return true;
            }});
        return view;
    }

    @OnClick(R.id.iv_back_setting)

    public void onBackClick()
    {

        settingViewlistener.backListener(R.id.iv_back_setting);
    }


    @Override
    public void showNwError() {

    }

    @Override
    public void backListener(int id) {

    }

    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String s) {

    }

    public interface settingFragmentCallBack
    {
        void callBackSettingActivity(int id);
    }

}
