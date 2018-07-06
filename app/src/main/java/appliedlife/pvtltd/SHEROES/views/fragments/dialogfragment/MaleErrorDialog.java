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

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 03-04-2017.
 */

public class MaleErrorDialog extends BaseDialogFragment {
    private static final String SCREEN_LABEL = "Male User Message Screen";
    //region Inject variables

    @Inject
    Preference<Configuration> mConfiguration;
    //endregion

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

    private String mSharedText;
    //endregion

    //region overridden variables
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.male_error_dialog, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            callFor = getArguments().getInt(AppConstants.FACEBOOK_VERIFICATION);
            message = getArguments().getString(AppConstants.SHEROES_AUTH_TOKEN);
            mUserName = getArguments().getString(BaseDialogFragment.USER_NAME);
        }
        if (StringUtil.isNotNullOrEmptyString(message)) {
            try {
                if (StringUtil.isNotNullOrEmptyString(mUserName)) {
                    tvUserNameMaleError.setVisibility(View.VISIBLE);
                    tvUserNameMaleError.setText(mUserName);

                }
                if (message.length() > 69) {
                    SpannableString spannableString = new SpannableString(message);
                    spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 7, 0);
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.feed_article_label)), 33, 47, 0);
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.feed_article_label)), 69, message.length(), 0);
                    tvDescriptionMaleError.setMovementMethod(LinkMovementMethod.getInstance());
                    tvDescriptionMaleError.setText(spannableString, TextView.BufferType.SPANNABLE);
                    tvDescriptionMaleError.setSelected(true);
                }
            } catch (Exception e) {
                Crashlytics.getInstance().core.logException(e);
            }

        }
        if (mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configData != null) {
            mSharedText = mConfiguration.get().configData.mMaleErrorText;
        } else {
            mSharedText = new ConfigData().mMaleErrorText;
        }
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        CommonUtil.setPrefValue(AppConstants.MALE_ERROR_SHARE_PREF);
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
        dismiss();
        getActivity().finish();
    }

    @OnClick(R.id.tv_share_sheroes_app)
    public void tvShareSheroesAppClick() {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType(AppConstants.SHARE_MENU_TYPE);
            intent.setPackage(AppConstants.WHATS_APP);
            intent.putExtra(Intent.EXTRA_TEXT, mSharedText);
            startActivity(intent);
            HashMap<String, Object> properties = new EventProperty.Builder().build();
            properties.put(EventProperty.SHARED_TO.getString(), AppConstants.WHATSAPP_ICON);
            AnalyticsManager.trackEvent(Event.POST_SHARED, SCREEN_LABEL, properties);
        } catch (Exception e) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType(AppConstants.SHARE_MENU_TYPE);
            intent.putExtra(Intent.EXTRA_TEXT, mSharedText);
            startActivity(Intent.createChooser(intent, AppConstants.SHARE));
            HashMap<String, Object> properties = new EventProperty.Builder().build();
            properties.put(EventProperty.SHARED_TO.getString(), AppConstants.SHARE_CHOOSER);
            AnalyticsManager.trackEvent(Event.POST_SHARED, SCREEN_LABEL, properties);
        }
    }
    //endregion

}
