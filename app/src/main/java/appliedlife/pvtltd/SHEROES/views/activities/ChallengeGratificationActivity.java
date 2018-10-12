package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.parceler.Parcels;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 07/02/18.
 */

public class ChallengeGratificationActivity extends BaseActivity {
    private static final String SCREEN_LABEL = "Challenge Completed Screen";
    //region View variables
    @Bind(R.id.tv_challenge_response_text_header)
    TextView tvChallengeResponseTextHeader;
    @Bind(R.id.iv_challenge_response)
    ImageView tvChallengeResponse;
    @Bind(R.id.tv_challenge_response_text)
    TextView tvChallengeResponseText;
    @Bind(R.id.tv_gratification)
    TextView tvGratification;
    @Bind(R.id.title_toolbar)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar mToolbarView;
    private Contest mContest;
    //endregion

    //region Activity method
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_challenge_graticfication_layout);
        ButterKnife.bind(this);
        init();
        setupToolbar();
    }

    private void init() {
        if (null != getIntent() && null != getIntent().getExtras()) {
            mContest = Parcels.unwrap(getIntent().getParcelableExtra(AppConstants.CHALLENGE_GRATIFICATION));
            String shareText = getString(R.string.challenge_completed_text, mContest.tag);
            SpannableString spannableSecond = new SpannableString(shareText);
            if (StringUtil.isNotNullOrEmptyString(shareText)) {
                int firstIndex = shareText.indexOf(mContest.tag);
                spannableSecond.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.email)), firstIndex, firstIndex + mContest.tag.length(), 0);
                spannableSecond.setSpan(new StyleSpan(Typeface.BOLD), firstIndex, firstIndex + mContest.tag.length(), 0);
                tvChallengeResponseText.setMovementMethod(LinkMovementMethod.getInstance());
                tvChallengeResponseText.setText(spannableSecond, TextView.BufferType.SPANNABLE);
                tvChallengeResponseText.setSelected(true);
            }
        }
        if (StringUtil.isNotNullOrEmptyString(mContest.thumbImage)) {
            Glide.with(this)
                    .asBitmap()
                    .load(mContest.thumbImage)
                    .apply(new RequestOptions().placeholder(R.color.photo_placeholder))
                    .into(tvChallengeResponse);
        }

    }

    private void setupToolbar() {
        setSupportActionBar(mToolbarView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbarTitle.setText(getString(R.string.challenge_completed));
    }

    public static void navigateTo(Activity fromActivity, Contest contest, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ChallengeGratificationActivity.class);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, SCREEN_LABEL);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        Bundle bundle = new Bundle();
        Parcelable parcelable = Parcels.wrap(contest);
        bundle.putParcelable(AppConstants.CHALLENGE_GRATIFICATION, parcelable);
        intent.putExtras(bundle);
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    @OnClick(R.id.ll_share)
    public void shareClick() {
        if (mContest != null) {
            String shareText = getString(R.string.challenge_gratification_share_text,mContest.tag,mContest.shortUrl);
            HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .challengeId(Integer.toString(mContest.remote_id))
                            .url(shareText)
                            .build();
            AnalyticsManager.trackEvent(Event.CHALLENGE_SHARED, AppConstants.CHALLENGE_GRATIFICATION_SCREEN, properties);
            ShareBottomSheetFragment.showDialog(ChallengeGratificationActivity.this, shareText, mContest.thumbImage, mContest.shortUrl, AppConstants.CHALLENGE_GRATIFICATION_SCREEN, true, mContest.shortUrl, false, Event.CHALLENGE_SHARED, properties);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    protected boolean trackScreenTime() {
        return true;
    }
    //endregion
}
