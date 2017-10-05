package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingChangeUserPreferenseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingDeActivateResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackResponce;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.UserpreferenseResponse;
import appliedlife.pvtltd.SHEROES.presenters.SettingFeedbackPresenter;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.Feedback_ThankyouActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingFeedbackView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka.
 */


public class SettingFeedbackFragment extends BaseFragment implements SettingFeedbackView {
    private static final String SCREEN_LABEL = "Setting FeedBack Screen";
    private final String TAG = LogUtils.makeLogTag(SettingFeedbackFragment.class);
    private final String SCREEN_NAME = "Setting_feedback_page";

    @Bind(R.id.tv_skip)
    TextView Mtextviewskip;
    @Bind(R.id.sc1)
    ScrollView Msc1;
    @Bind(R.id.et_write_comment)
    EditText mEtWriteComment;
    @Bind(R.id.dialog_ratingbar)
    RatingBar MdialogRatingbar;
    @Bind(R.id.iv_back_setting)
    ImageView mIvBackSetting;
    @Bind(R.id.tv_setting_tittle)
    TextView mTvSettingTittle;
    settingFragmentCallBack msettingFragmentCallBack;
    @Bind(R.id.preferences_deactiveaccount_button)
    Button mPreferencesDeactiveaccountButton;
    String mFeebackvalue;
    int mStars;
    @Inject
    SettingFeedbackPresenter mSettingFeedbackPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_setting_feedback, container, false);
        ButterKnife.bind(this, view);
        mTvSettingTittle.setText(R.string.ID_FEEDBACK);
        mSettingFeedbackPresenter.attachView(this);

        //on tuch star...
        MdialogRatingbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mPreferencesDeactiveaccountButton.setEnabled(true);
                    mEtWriteComment.setCursorVisible(true);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                    mPreferencesDeactiveaccountButton.setBackgroundColor(getResources().getColor(R.color.red));
                    mTvSettingTittle.setVisibility(View.VISIBLE);
                    float touchPositionX = event.getX();
                    float width = MdialogRatingbar.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    mStars = (int) starsf + 1;
                    MdialogRatingbar.setRating(mStars);
                    userRating_Value(mStars);
                    // Toast.makeText(getActivity(), String.valueOf(Mdialog_ratingbar.getRating()), Toast.LENGTH_SHORT).show();
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
                mEtWriteComment.setVisibility(View.VISIBLE);


                return true;
            }
        });
        return view;
    }


    private void userRating_Value(int starvalue) {

        //Store values at the time of the User_rating btn attempt.

        SettingRatingRequest ratingRequest = new SettingRatingRequest();
        ratingRequest.setAppVersion("string");
        ratingRequest.setCloudMessagingId("string");
        ratingRequest.setDeviceUniqueId("string");
        ratingRequest.setRating(starvalue);
        ratingRequest.setLastScreenName("string");
        ratingRequest.setScreenName("string");
        mSettingFeedbackPresenter.getUserRatingAuthTokeInPresenter(ratingRequest);

        Toast.makeText(getActivity(),"Thankyou for Rating us", Toast.LENGTH_LONG).show();


    }

    private void userfeedback() {

        // Store values at the time of the User_feedback btn attempt.
        SettingFeedbackRequest feedbackRequest = new SettingFeedbackRequest();
        feedbackRequest.setAppVersion("string");
        feedbackRequest.setCloudMessagingId("string");
        feedbackRequest.setComment(mFeebackvalue);
        feedbackRequest.setDeviceUniqueId("string");
        feedbackRequest.setLastScreenName("string");
        feedbackRequest.setScreenName("string");
        mSettingFeedbackPresenter.getFeedbackAuthTokeInPresenter(feedbackRequest);
       }

    @OnClick(R.id.preferences_deactiveaccount_button)


    public void onSubmitPress() {

        mFeebackvalue = mEtWriteComment.getText().toString();

        if (StringUtil.isNotNullOrEmptyString(mFeebackvalue)) {




            userfeedback();
            Intent intent = new Intent(getActivity(), Feedback_ThankyouActivity.class);
            startActivity(intent);

        } else {

            //Toast.makeText(getActivity(),"Your message is too short", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getActivity(), Feedback_ThankyouActivity.class);
            startActivity(intent);



        }


    }

    @OnClick(R.id.iv_back_setting)
    public void onBackClick() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        ((HomeActivity)getActivity()).getSupportFragmentManager().popBackStack();

    }

    @Override
    public void getFeedbackResponse(SettingFeedbackResponce feedbackResponce) {


    }

    @Override
    public void getUserRatingResponse(SettingRatingResponse ratingResponse) {


    }

    @Override
    public void getUserDeactiveResponse(SettingDeActivateResponse deActivateResponse) {

    }

    @Override
    public void getUserPreferenceResponse(UserpreferenseResponse userpreferenseResponse) {

    }

    @Override
    public void getUserChangePreferenceResponse(SettingChangeUserPreferenseResponse settingChangeUserPreferenseResponse) {

    }

    @Override
    public void showNwError() {

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
    public String getScreenName() {
        return SCREEN_LABEL;
    }


    public interface settingFragmentCallBack {


        void callBackSettingActivity(int id);


    }

}
