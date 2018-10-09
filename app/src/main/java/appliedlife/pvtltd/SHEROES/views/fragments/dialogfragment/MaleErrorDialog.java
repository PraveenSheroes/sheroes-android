package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
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

    @Bind(R.id.tv_care_id)
    TextView tvCareId;
    //endregion

    //region member variables
    private int callFor = 0;

    private String mUserName;

    private String mSharedText, mErrorText;
    //endregion

    //region overridden variables
    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.male_error_dialog, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            callFor = getArguments().getInt(AppConstants.FACEBOOK_VERIFICATION);
            mUserName = getArguments().getString(BaseDialogFragment.USER_NAME);
        }
        if (mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configData != null) {
            mSharedText = mConfiguration.get().configData.mMaleShareText;
            mErrorText = mConfiguration.get().configData.mMaleErrorText;
        } else {
            mSharedText = new ConfigData().mMaleShareText;
            mErrorText = new ConfigData().mMaleErrorText;
        }
        try {
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvDescriptionMaleError.setText(Html.fromHtml(mErrorText, 0)); // for 24 api and more
            } else {
                tvDescriptionMaleError.setText(Html.fromHtml(mErrorText));// or for older api
            }
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
        }
        String msg = getString(R.string.care_msg);
        SpannableString spannableString = new SpannableString(msg);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.view_more)), 11, msg.length(), 0);
        tvCareId.setMovementMethod(LinkMovementMethod.getInstance());
        tvCareId.setText(spannableString, TextView.BufferType.SPANNABLE);
        tvCareId.setSelected(true);
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
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, mSharedText);
        HashMap<String, Object> properties = new EventProperty.Builder().build();
        try {
            intent.setPackage(AppConstants.WHATS_APP);
            startActivity(intent);
            properties.put(EventProperty.SHARED_TO.getString(), AppConstants.WHATSAPP_ICON);
        } catch (Exception e) {
            startActivity(Intent.createChooser(intent, AppConstants.SHARE));
            properties.put(EventProperty.SHARED_TO.getString(), AppConstants.SHARE_CHOOSER);
        } finally {
            AnalyticsManager.trackEvent(Event.APP_SHARED, SCREEN_LABEL, properties);
        }
    }

    @OnClick(R.id.tv_care_id)
    public void onCareSupportClick() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", getString(R.string.care_sheroes), null));
        startActivity(Intent.createChooser(emailIntent, null));
    }
    //endregion

}
