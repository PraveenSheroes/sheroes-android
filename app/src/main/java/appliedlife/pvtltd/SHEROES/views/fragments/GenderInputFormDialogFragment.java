package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.WelcomeActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 09/05/18.
 */

public class GenderInputFormDialogFragment extends BaseDialogFragment {
    private static final String SCREEN_LABEL = "Gender Input Form Screen";
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
    @Bind(R.id.li_male_error)
    LinearLayout liMaleError;
    @Bind(R.id.rl_gender_input_form)
    RelativeLayout rlGenderInputForm;
    private String mUserName, mPersonnelEmailId;
    private boolean isMaleSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gender_input_form_fragment_layout, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, v);
        if (null != getArguments()) {
            mUserName = getArguments().getString(USER_NAME);
            mPersonnelEmailId = getArguments().getString(EMAIL_ID);
        }
        liMaleError.setVisibility(View.GONE);
        rlGenderInputForm.setVisibility(View.VISIBLE);
        tvGenderSelectFinish.setEnabled(false);
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(SCREEN_LABEL);
        AnalyticsManager.trackScreenView(SCREEN_LABEL);
        return v;
    }

    @OnClick(R.id.iv_male)
    public void maleImageClick() {
        isMaleSelected=true;
        tvGenderSelectFinish.setBackgroundResource(R.drawable.rectangle_boarding_active);
        ivMale.setImageResource(R.drawable.vector_male_active);
        tvMan.setTextColor(ContextCompat.getColor(getActivity(), R.color.footer_icon_text));
        tvGenderSelectFinish.setEnabled(true);

    }

    @OnClick(R.id.iv_female)
    public void femaleImageClick() {
        isMaleSelected=false;
        tvGenderSelectFinish.setEnabled(true);
        tvGenderSelectFinish.setBackgroundResource(R.drawable.rectangle_boarding_active);
        ivFemale.setImageResource(R.drawable.vector_female_active);
        tvWomen.setTextColor(ContextCompat.getColor(getActivity(), R.color.footer_icon_text));
    }

    @OnClick(R.id.tv_gender_select_finish)
    public void getStartedClick() {
        if(isMaleSelected) {
            liMaleError.setVisibility(View.VISIBLE);
            rlGenderInputForm.setVisibility(View.GONE);
        }else
        {
            if (StringUtil.isNotNullOrEmptyString(mPersonnelEmailId)) {
                ((WelcomeActivity) getActivity()).getTokenFromGoogleAuth(mPersonnelEmailId);
                dismiss();
            }
        }
    }

    @OnClick(R.id.tv_share_sheroes_app)
    public void tvShareSheroesAppClick() {
        dismiss();
    }
    @OnClick(R.id.iv_close)
    public void ivCloseClick() {
        dismiss();
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

}
