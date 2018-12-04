package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ChallengeWinnerPopUpDialog extends BaseDialogFragment {
    public static final String SCREEN_LABEL = "Challenge Winner Rating Dialog";
    //region Inject
    @Inject
    Preference<AppConfiguration> mConfiguration;
    //endregion

    //region Bind view variables
    @Bind(R.id.tv_winner_dialog_title)
    TextView tvWinnerDialogTitle;

    @Bind(R.id.tv_winner_dialog_intro)
    TextView tvWinnerDialogIntro;

    @Bind(R.id.tv_winner_dialog_msg)
    TextView tvWinnerDialogMsg;

    @Bind(R.id.tv_cover_text_view)
    TextView tvCoverText;

    @Bind(R.id.tv_winner_dialog_button)
    TextView tvWinnerButton;

    @Bind(R.id.tv_copy)
    TextView tvCopy;
    //endregion

    //region private member variable
    private FeedDetail mFeedDetail;
    //endregion

    //region Fragment methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getActivity() == null) return null;
        View view = inflater.inflate(R.layout.challenge_winner_pop_up_dialog, container, false);
        ButterKnife.bind(this, view);
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        Parcelable parcelable = getArguments().getParcelable(CHALLENGE_WINNER);
        if (null != parcelable) {
            mFeedDetail = Parcels.unwrap(parcelable);
        }
        if (null != mFeedDetail) {
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getAuthorName())) {
                tvWinnerDialogTitle.setText(getString(R.string.congratulation) + " " + mFeedDetail.getAuthorName() + "!");
            }
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getDescription())) {
                tvCoverText.setText("\"" + mFeedDetail.getDescription() + "\"");
            }
            if (null != mConfiguration && mConfiguration.isSet() && mConfiguration.get().configData != null) {
                tvWinnerDialogIntro.setText(mConfiguration.get().configData.challengeWinnerDialogMassage);
            } else {
                tvWinnerDialogIntro.setText(new ConfigData().challengeWinnerDialogMassage);
            }
        }
        AnalyticsManager.trackScreenView(SCREEN_LABEL);
        return view;
    }
    //endregion

    //region onclick method
    @OnClick(R.id.iv_winner_dialog_close)
    protected void crossClick() {
        dismiss();
        CommonUtil.setPrefValue(AppConstants.APP_REVIEW_PLAY_STORE);
    }

    @OnClick(R.id.tv_copy)
    public void copyClick() {
        copyToClipboard(tvCoverText.getText().toString());
        tvWinnerButton.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        tvCopy.setText(getString(R.string.copied));
    }

    @OnClick(R.id.tv_winner_dialog_button)
    public void redirectToPlayStoreClick() {
        final String appPackageName = getApplicationContext().getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.PLAY_STORE_ID_URL + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.PLAY_STORE_URL_PATH + appPackageName)));
        }
        CommonUtil.setPrefValue(AppConstants.APP_REVIEW_PLAY_STORE);
        AnalyticsManager.trackEvent(Event.APP_REVIEW_CLICKED, SCREEN_LABEL, null);
    }
    //endregion

    //region Public methods

    public void copyToClipboard(String copyText) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            clipboard.setText(copyText);
        } else {
            ClipData clip = android.content.ClipData.newPlainText(getString(R.string.message), copyText);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(getApplicationContext(), getString(R.string.your_response_copied), Toast.LENGTH_SHORT).show();

    }
    //endregion


}
