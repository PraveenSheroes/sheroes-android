package appliedlife.pvtltd.SHEROES.views.fragments;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.WelcomeActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 09/05/18.
 */

public class GenderInputFormDialogFragment extends BaseDialogFragment {
    private static final String SCREEN_LABEL = "Input Gender Screen";
    //region View variables
    @Bind(R.id.tv_user_name)
    TextView tvUserName;

    @Bind(R.id.tv_msg)
    TextView tvMsg;

    @Bind(R.id.tv_gender_select_finish)
    TextView tvGenderSelectFinish;

    @Bind(R.id.iv_male)
    ImageView ivMale;

    @Bind(R.id.iv_female)
    ImageView ivFemale;

    @Bind(R.id.tv_man)
    TextView tvMan;

    @Bind(R.id.tv_women)
    TextView tvWomen;
    //endregion

    //region member variables
    private String mUserName, mPersonnelEmailId;

    private boolean isMaleSelected;
    //endregion

    //region overridden methods
    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gender_input_form_fragment_layout, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, v);
        if (null != getArguments()) {
            mUserName = getArguments().getString(USER_NAME);
            mPersonnelEmailId = getArguments().getString(EMAIL_ID);
        }
        if (StringUtil.isNotNullOrEmptyString(mUserName)) {
            tvUserName.setText(getString(R.string.hi,mUserName));
        }
        if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
            tvMsg.setText(Html.fromHtml(getString(R.string.sheroes_msg), 0)); // for 24 api and more
        } else {
            tvMsg.setText(Html.fromHtml(getString(R.string.sheroes_msg)));// or for older api
        }
        ivFemale.setImageResource(R.drawable.vector_female);
        ivMale.setImageResource(R.drawable.vector_male);
        tvGenderSelectFinish.setEnabled(false);
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(SCREEN_LABEL);
        AnalyticsManager.trackScreenView(SCREEN_LABEL);
        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth) {
            @Override
            public void onBackPressed() {
                dismiss();
            }
        };
    }
    //endregion

    //region onclick methods
    @OnClick(R.id.li_male_layout)
    public void maleImageClick() {
        isMaleSelected = true;
        tvGenderSelectFinish.setBackgroundResource(R.drawable.rectangle_boarding_active);
        ivMale.setImageResource(R.drawable.vector_male_active);
        tvMan.setTextColor(ContextCompat.getColor(getActivity(), R.color.footer_icon_text));
        ivFemale.setImageResource(R.drawable.vector_female);
        tvWomen.setTextColor(ContextCompat.getColor(getActivity(), R.color.comment_text));
        tvGenderSelectFinish.setEnabled(true);

    }

    @OnClick(R.id.li_female_layout)
    public void femaleImageClick() {
        isMaleSelected = false;
        tvGenderSelectFinish.setEnabled(true);
        tvGenderSelectFinish.setBackgroundResource(R.drawable.rectangle_boarding_active);
        ivFemale.setImageResource(R.drawable.vector_female_active);
        tvWomen.setTextColor(ContextCompat.getColor(getActivity(), R.color.footer_icon_text));
        ivMale.setImageResource(R.drawable.vector_male);
        tvMan.setTextColor(ContextCompat.getColor(getActivity(), R.color.comment_text));
    }

    @OnClick(R.id.tv_gender_select_finish)
    public void getStartedClick() {
        String gender = "Male";
        if (isMaleSelected) {
            gender = "Male";
            String description = getString(R.string.sheroes_gender_error);
            ((WelcomeActivity) getActivity()).showMaleError(mUserName);
            dismiss();
        } else {
            if (StringUtil.isNotNullOrEmptyString(mPersonnelEmailId)) {
                gender = "Female";
                ((WelcomeActivity) getActivity()).getTokenFromGoogleAuth(mPersonnelEmailId);
                dismiss();
            }
        }
        final HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .gender(gender)
                        .build();
        properties.put(EventProperty.SOURCE.getString(), SCREEN_LABEL);

        AnalyticsManager.trackEvent(Event.GENDER_SELECTED, SCREEN_LABEL, properties);
    }
    //endregion
}
