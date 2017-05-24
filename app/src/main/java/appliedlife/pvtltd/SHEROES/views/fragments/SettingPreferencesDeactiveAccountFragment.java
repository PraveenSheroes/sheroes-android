package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingChangeUserPreferenseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingDeActivateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingDeActivateResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackResponce;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.UserpreferenseResponse;
import appliedlife.pvtltd.SHEROES.presenters.SettingFeedbackPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.SettingPreferencesActivity;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.PreferencesDeactiveAccountDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingFeedbackView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by priyanka
 * Setting_Preferences_DeactiveAccount_Screen
 */


public class SettingPreferencesDeactiveAccountFragment extends BaseFragment implements SettingFeedbackView {

    private final String TAG = LogUtils.makeLogTag(SettingPreferencesDeactiveAccountFragment.class);

    @Bind(R.id.deactive_text1)
    TextView mtvdeactive_text1;
    /*  @Bind(R.id.deactive_check_box1)
      CheckBox mdeactive_check_box1;*/
    @Bind(R.id.deactive_text1a)
    TextView mTvDeactiveText1a;
    @Bind(R.id.deactive_check_box1a)
    CheckBox mDeactiveCheckBox1a;
    @Bind(R.id.deactive_text1b)
    TextView mtvdeactive_text1b;
    @Bind(R.id.deactive_check_box1b)
    CheckBox mDeactiveCheckBox1b;
    @Bind(R.id.deactive_text1c)
    TextView mTvDeactiveText1c;
    @Bind(R.id.deactive_check_box1c)
    CheckBox mDeactiveCheckBox1c;
    @Bind(R.id.deactive_text1d)
    TextView mTvDeactiveText1d;
    @Bind(R.id.deactive_check_box1d)
    CheckBox mDeactiveCheckBox1d;
    @Bind(R.id.et_edit_text_reson)
    EditText mEditTextReson;
    @Bind(R.id.ID_tvreason)
    TextView tv_reson;
    @Bind(R.id.preferences_deactiveaccount_button)
    Button mPreferencesDeactiveaccountButton;
    @Bind(R.id.tv_setting_tittle)
    TextView mTvSettingTittle;
    @Bind(R.id.tv_setting_tittle1)
    TextView mTvSettingTittle1;
    @Bind(R.id.iv_back_setting)
    ImageView mIvBackSetting;
    @Bind(R.id.progress_bar_first_load)
    ProgressBar mProgressBarFirstLoad;
    String mDeactivateReson_value;
    @Bind(R.id.tv_reson_line)
    TextView mTvResonLine;

    int flag = 0;
    String value;
    @Inject
    SettingFeedbackPresenter mSettingFeedbackPresenter;
    private FragmentIntractionWithActivityListner mHomeSearchActivityFragmentIntractionWithActivityListner;


    public SettingPreferences_DeactiveAccounActivitytLisIntractionListener settingPreferences_deactiveAccounActivitytLisIntractionListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (mActivity instanceof FragmentIntractionWithActivityListner) {
                mHomeSearchActivityFragmentIntractionWithActivityListner = (FragmentIntractionWithActivityListner) getActivity();
            }
        } catch (Fragment.InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_setting_preferences_deactiveaccount, container, false);
        ButterKnife.bind(this, view);
        mSettingFeedbackPresenter.attachView(this);
        mProgressBarFirstLoad.setVisibility(View.GONE);

        mTvSettingTittle.setText(R.string.ID_DEACTIVEACCOUNT);
        mTvSettingTittle1.setText(R.string.ID_PREFERENCES);
        mTvSettingTittle.setTextSize(14);
        mTvSettingTittle1.setTextSize(12);

        mTvResonLine.setVisibility(View.GONE);
        //Open setting_preferences_Activity

        mIvBackSetting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                ((SettingPreferencesActivity)getActivity()).getSupportFragmentManager().popBackStack();

             /*   Intent intent = new Intent(getActivity(), SettingPreferencesActivity.class);
                startActivity(intent);*/


            }
        });
       /* miv_back_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), SettingPreferencesActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });*/

        return view;
    }
    //click on deactive_text2

    @OnClick(R.id.deactive_text1a)

    public void ondeactivetext1aclick() {

        setcolorontextview(mTvDeactiveText1a, mDeactiveCheckBox1a);
        value = mTvDeactiveText1a.getText().toString();

        mDeactiveCheckBox1a.setChecked(true);
        mDeactiveCheckBox1b.setChecked(false);
        mDeactiveCheckBox1c.setChecked(false);
        mDeactiveCheckBox1d.setChecked(false);
        mEditTextReson.setVisibility(View.GONE);
        mTvResonLine.setVisibility(View.GONE);
        tv_reson.setVisibility(View.GONE);


        mPreferencesDeactiveaccountButton.setEnabled(true);
        mTvDeactiveText1a.setTextColor(getResources().getColor(R.color.blue));
        mtvdeactive_text1.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mtvdeactive_text1b.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1c.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1d.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));

    }


    //click on deactive_check2

    @OnClick(R.id.deactive_check_box1a)

    public void ondeactivecheck1aclick() {


        setcolorontextview(mTvDeactiveText1a, mDeactiveCheckBox1a);

        mDeactiveCheckBox1a.setChecked(true);
        mDeactiveCheckBox1b.setChecked(false);
        mDeactiveCheckBox1c.setChecked(false);
        mDeactiveCheckBox1d.setChecked(false);
        mEditTextReson.setVisibility(View.GONE);
        mTvResonLine.setVisibility(View.GONE);
        tv_reson.setVisibility(View.GONE);

        mPreferencesDeactiveaccountButton.setEnabled(true);
        mTvDeactiveText1a.setTextColor(getResources().getColor(R.color.blue));
        mtvdeactive_text1.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mtvdeactive_text1b.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1c.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1d.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));


    }


    //click on deactive_text3

    @OnClick(R.id.deactive_text1b)

    public void ondeactivetext1bclick() {

        setcolorontextview(mtvdeactive_text1b, mDeactiveCheckBox1b);
        value = mtvdeactive_text1b.getText().toString();
        mDeactiveCheckBox1b.setChecked(true);
        mDeactiveCheckBox1a.setChecked(false);
        mDeactiveCheckBox1c.setChecked(false);
        mDeactiveCheckBox1d.setChecked(false);
        mTvResonLine.setVisibility(View.GONE);
        tv_reson.setVisibility(View.GONE);

        mEditTextReson.setVisibility(View.GONE);
        mPreferencesDeactiveaccountButton.setEnabled(true);
        mtvdeactive_text1b.setTextColor(getResources().getColor(R.color.blue));
        mtvdeactive_text1.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1a.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1c.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1d.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));


    }

    //click on deactive_check_3

    @OnClick(R.id.deactive_check_box1b)

    public void ondeactivecheck3bclick() {

        setcolorontextview(mtvdeactive_text1b, mDeactiveCheckBox1b);
        mDeactiveCheckBox1b.setChecked(true);
        mDeactiveCheckBox1a.setChecked(false);
        mDeactiveCheckBox1c.setChecked(false);
        mDeactiveCheckBox1d.setChecked(false);
        mEditTextReson.setVisibility(View.GONE);
        mTvResonLine.setVisibility(View.GONE);
        tv_reson.setVisibility(View.GONE);

        mPreferencesDeactiveaccountButton.setEnabled(true);
        mtvdeactive_text1b.setTextColor(getResources().getColor(R.color.blue));
        mtvdeactive_text1.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1a.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1c.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1d.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));


    }


    //click on deactive_text4

    @OnClick(R.id.deactive_text1c)

    public void ondeactivetext1cclick() {

        setcolorontextview(mTvDeactiveText1c, mDeactiveCheckBox1c);
        value = mTvDeactiveText1c.getText().toString();

        mDeactiveCheckBox1c.setChecked(true);
        mDeactiveCheckBox1a.setChecked(false);
        mDeactiveCheckBox1b.setChecked(false);
        mDeactiveCheckBox1d.setChecked(false);
        mEditTextReson.setVisibility(View.GONE);
        mTvResonLine.setVisibility(View.GONE);
        tv_reson.setVisibility(View.GONE);

        mPreferencesDeactiveaccountButton.setEnabled(true);
        mTvDeactiveText1c.setTextColor(getResources().getColor(R.color.blue));
        mtvdeactive_text1.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1a.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mtvdeactive_text1b.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1d.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));


    }

    //click on deactive_check4
    @OnClick(R.id.deactive_check_box1c)

    public void ondeactivecheck4cclick() {

        setcolorontextview(mTvDeactiveText1c, mDeactiveCheckBox1c);
        mDeactiveCheckBox1c.setChecked(true);
        mDeactiveCheckBox1a.setChecked(false);
        mDeactiveCheckBox1b.setChecked(false);
        mDeactiveCheckBox1d.setChecked(false);
        mEditTextReson.setVisibility(View.GONE);
        mTvResonLine.setVisibility(View.GONE);
        tv_reson.setVisibility(View.GONE);

        mPreferencesDeactiveaccountButton.setEnabled(true);
        mTvDeactiveText1c.setTextColor(getResources().getColor(R.color.blue));
        mtvdeactive_text1.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1a.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mtvdeactive_text1b.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1d.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));

    }

    //click on deactive_text5

    @OnClick(R.id.deactive_text1d)

    public void ondeactivetext1dclick() {

        setcolorontextview(mTvDeactiveText1d, mDeactiveCheckBox1d);
        value = mTvDeactiveText1d.getText().toString();
        mDeactiveCheckBox1d.setChecked(true);
        mDeactiveCheckBox1a.setChecked(false);
        mDeactiveCheckBox1b.setChecked(false);
        mDeactiveCheckBox1c.setChecked(false);
        mEditTextReson.setVisibility(View.VISIBLE);
        mTvResonLine.setVisibility(View.VISIBLE);
        tv_reson.setVisibility(View.GONE);

        mPreferencesDeactiveaccountButton.setEnabled(true);
        mTvDeactiveText1d.setTextColor(getResources().getColor(R.color.blue));
        mtvdeactive_text1.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1a.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mtvdeactive_text1b.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1c.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));


    }


    //click on deactive_checkbox_5

    @OnClick(R.id.deactive_check_box1d)

    public void ondeactivecheck5click() {
        mTvResonLine.setVisibility(View.VISIBLE);

        setcolorontextview(mTvDeactiveText1d, mDeactiveCheckBox1d);


        mDeactiveCheckBox1a.setChecked(false);
        mDeactiveCheckBox1b.setChecked(false);
        mDeactiveCheckBox1c.setChecked(false);
        mDeactiveCheckBox1d.setChecked(true);
        mEditTextReson.setVisibility(View.VISIBLE);
        tv_reson.setVisibility(View.GONE);
        mTvResonLine.setVisibility(View.VISIBLE);



        mPreferencesDeactiveaccountButton.setEnabled(true);
        mTvDeactiveText1d.setTextColor(getResources().getColor(R.color.blue));
        mtvdeactive_text1.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1a.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mtvdeactive_text1b.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
        mTvDeactiveText1c.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));


    }

    //function for change text color and show checkbox

    public void setcolorontextview(TextView textview, CheckBox checkbox)

    {
        if (flag == 0)

        {
            textview.setTextColor(getResources().getColor(R.color.search_tab_text));
            mPreferencesDeactiveaccountButton.setBackgroundColor(getResources().getColor(R.color.red));
            mPreferencesDeactiveaccountButton.setEnabled(true);


            checkbox.setChecked(true);

            if (textview.getText().equals(getResources().getString(R.string.ID_OTHER))) {
                mEditTextReson.setVisibility(View.VISIBLE);
                mPreferencesDeactiveaccountButton.setBackgroundColor(getResources().getColor(R.color.red));
                mPreferencesDeactiveaccountButton.setEnabled(true);
                mTvResonLine.setVisibility(View.VISIBLE);

            }

            flag = 1;
        } else

        {
            textview.setTextColor(getResources().getColor(R.color.black));
            checkbox.setChecked(false);
            if (textview.getText().equals(getResources().getString(R.string.ID_OTHER))) {
                mEditTextReson.setVisibility(View.GONE);
                tv_reson.setVisibility(View.GONE);
                mTvResonLine.setVisibility(View.GONE);



            }
            flag = 0;
        }


    }


    @OnClick(R.id.preferences_deactiveaccount_button)


    public void deactivebuttonclick() {


        if (value.equals("Other")) {

            mDeactivateReson_value = mEditTextReson.getText().toString();

            if (StringUtil.isNotNullOrEmptyString(mDeactivateReson_value) && mDeactivateReson_value.length() > 10) {

                tv_reson.setVisibility(View.GONE);
                SettingDeActivateRequest deActivateRequest = new SettingDeActivateRequest();
                deActivateRequest.setAppVersion("string");
                deActivateRequest.setCloudMessagingId("string");
                deActivateRequest.setDeviceUniqueId("string");
                deActivateRequest.setReasonForInactive(mDeactivateReson_value);
                deActivateRequest.setLastScreenName("string");
                deActivateRequest.setScreenName("string");
                mSettingFeedbackPresenter.getUserDeactiveAuthTokeInPresenter(deActivateRequest);
                PreferencesDeactiveAccountDialogFragment newFragment = new PreferencesDeactiveAccountDialogFragment();
                newFragment.setListener(this);
                newFragment.show(getActivity().getFragmentManager(), "dialog");

                 /*  Intent i = new Intent(getActivity(), PreferencesDeactiveAccountDialogFragment.class);
                   startActivity(i);
*/
              /*  Intent i = new Intent(getActivity(), PreferencesDeactiveAccountDialogFragment.class);
                startActivity(i);*/


            } else {

                tv_reson.setVisibility(View.VISIBLE);
                mTvResonLine.setBackgroundColor(getResources().getColor(R.color.red));
            }



        } else {
            SettingDeActivateRequest deActivateRequest = new SettingDeActivateRequest();
            deActivateRequest.setAppVersion("string");
            deActivateRequest.setCloudMessagingId("string");
            deActivateRequest.setDeviceUniqueId("string");
            deActivateRequest.setReasonForInactive(value);
            deActivateRequest.setLastScreenName("string");
            deActivateRequest.setScreenName("string");
            mSettingFeedbackPresenter.getUserDeactiveAuthTokeInPresenter(deActivateRequest);
            PreferencesDeactiveAccountDialogFragment newFragment = new PreferencesDeactiveAccountDialogFragment();
            newFragment.setListener(this);
            newFragment.show(getActivity().getFragmentManager(), "dialog");


        }


    }

    @Override
    public void getFeedbackResponse(SettingFeedbackResponce feedbackResponce) {

    }

    @Override
    public void getUserRatingResponse(SettingRatingResponse ratingResponse) {

    }

    @Override
    public void getUserDeactiveResponse(SettingDeActivateResponse deActivateResponse) {

        mProgressBarFirstLoad.setVisibility(View.GONE);


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
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {

        mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(errorMsg, feedParticipationEnum);
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


    interface SettingPreferences_DeactiveAccounActivitytLisIntractionListener {

        void onErrorOccurence();
    }


}
