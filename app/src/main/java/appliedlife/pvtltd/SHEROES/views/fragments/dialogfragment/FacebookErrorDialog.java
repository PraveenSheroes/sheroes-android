package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
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
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 03-04-2017.
 */

public class FacebookErrorDialog extends BaseDialogFragment {
    @Bind(R.id.tv_ok)
    TextView mTvCacel;
    @Bind(R.id.tv_message)
    TextView mTvMessage;
    @Bind(R.id.iv_women_error)
    ImageView mIvWomenError;
    @Bind(R.id.iv_hey_success_next)
    ImageView mIvHeySuccessNext;
    int callFor = 0;
    String message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.facebook_error_dialog, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            callFor = getArguments().getInt(AppConstants.FACEBOOK_VERIFICATION);
            message = getArguments().getString(AppConstants.SHEROES_AUTH_TOKEN);
        }
        if (StringUtil.isNotNullOrEmptyString(message)) {
            if (message.equalsIgnoreCase(getString(R.string.ID_WOMEN_ERROR))) {
                mIvHeySuccessNext.setVisibility(View.GONE);
                mIvWomenError.setVisibility(View.VISIBLE);
            } else {
                mIvHeySuccessNext.setVisibility(View.VISIBLE);
                mIvWomenError.setVisibility(View.GONE);
            }
            message=message.substring(6,message.length());
            SpannableString spannableString = new SpannableString(message);
            if (StringUtil.isNotNullOrEmptyString(message)) {
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.feed_article_label)), 13, 32, 0);
                spannableString.setSpan(new StyleSpan(Typeface.BOLD), 13, 32, 0);
                mTvMessage.setMovementMethod(LinkMovementMethod.getInstance());
                mTvMessage.setText(spannableString, TextView.BufferType.SPANNABLE);
                mTvMessage.setSelected(true);
            }
        }
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        return view;
    }

    @OnClick(R.id.tv_ok)
    public void tryAgainClick() {
        if (callFor == AppConstants.NO_REACTION_CONSTANT) {

        } else {
            dismiss();
        }
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


}
