package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 03-04-2017.
 */

public class FacebookErrorDialog extends BaseDialogFragment {
    private static final String SCREEN_LABEL = "Male User Message Screen";
    public static final String GENDER_SHARE_LINK = "https://shrs.me/xtap573vXM";
    //region View variables
    @Bind(R.id.iv_close)
    ImageView mTvCacel;

    @Bind(R.id.tv_user_name_male_error)
    TextView tvUserNameMaleError;

    @Bind(R.id.tv_description_male_error)
    TextView tvDescriptionMaleError;
    //endregion

    //region member variables
    private int callFor = 0;

    private String message, mUserName;
    //endregion

    //region overridden variables
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.facebook_error_dialog, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            callFor = getArguments().getInt(AppConstants.FACEBOOK_VERIFICATION);
            message = getArguments().getString(AppConstants.SHEROES_AUTH_TOKEN);
            mUserName = getArguments().getString(BaseDialogFragment.USER_NAME);
        }
        if (StringUtil.isNotNullOrEmptyString(message)) {

            if (StringUtil.isNotNullOrEmptyString(mUserName)) {
                tvUserNameMaleError.setVisibility(View.VISIBLE);
                tvUserNameMaleError.setText(mUserName);

            } /*else {
                SpannableString spannableString = new SpannableString(message);
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.feed_article_label)), 0, message.length(), 0);
                tvDescriptionMaleError.setMovementMethod(LinkMovementMethod.getInstance());
                tvDescriptionMaleError.setText(spannableString, TextView.BufferType.SPANNABLE);
                tvDescriptionMaleError.setSelected(true);
            }*/
            SpannableString spannableString = new SpannableString(message);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 7, 0);
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.feed_article_label)), 33, 47, 0);
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.feed_article_label)), 69, message.length(), 0);
            tvDescriptionMaleError.setMovementMethod(LinkMovementMethod.getInstance());
            tvDescriptionMaleError.setText(spannableString, TextView.BufferType.SPANNABLE);
            tvDescriptionMaleError.setSelected(true);
        }
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(SCREEN_LABEL);
        AnalyticsManager.trackScreenView(SCREEN_LABEL);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // safety check
        if (getDialog() == null) {
            return;
        }
        // set the animations to use on showing and hiding the dialog
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth) {
            @Override
            public void onBackPressed() {
                tryAgainClick();
            }
        };
    }
    //endregion

    //region onclick methods
    @OnClick(R.id.iv_close)
    public void tryAgainClick() {
        if (callFor == AppConstants.NO_REACTION_CONSTANT) {

        } else {
            dismiss();
        }
    }

    @OnClick(R.id.tv_share_sheroes_app)
    public void tvShareSheroesAppClick() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.setPackage(AppConstants.WHATS_APP);
        intent.putExtra(Intent.EXTRA_TEXT, AppConstants.SHARED_EXTRA_SUBJECT + GENDER_SHARE_LINK);
        startActivity(intent);
    }
    //endregion

}
