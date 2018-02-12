package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
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
        String    shareText="You have completed the #"+mContest.tag+" challenge ";
            SpannableString spannableSecond = new SpannableString(shareText);
            if (StringUtil.isNotNullOrEmptyString(shareText)) {
                spannableSecond.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.email)), 22, shareText.length()-11, 0);
                spannableSecond.setSpan(new StyleSpan(Typeface.BOLD), 22, shareText.length()-11, 0);
                tvChallengeResponseText.setMovementMethod(LinkMovementMethod.getInstance());
                tvChallengeResponseText.setText(spannableSecond, TextView.BufferType.SPANNABLE);
                tvChallengeResponseText.setSelected(true);
            }
        }
        if(StringUtil.isNotNullOrEmptyString(mContest.thumbImage)) {
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
            String shareText="Yay! I just completed the #"+mContest.tag+" challenge on the SHEROES app. It is a women only app where you can share anything without hesitation. You should also take up this challenge on the app. Try here: "+mContest.shortUrl;
            ShareBottomSheetFragment.showDialog(ChallengeGratificationActivity.this, shareText, mContest.thumbImage, mContest.shortUrl, AppConstants.CHALLENGE_GRATIFICATION_SCREEN, true, mContest.shortUrl, false);
        }
    }
//TODO: Will work on Challenge gratification next part where this code will be use
    private Bitmap createShareImage() {
        View view;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.challenge_share_card_layout, null, false);

        LinearLayout userDetailContainer = view.findViewById(R.id.ll_sharable_card);
        ImageView profilePic = view.findViewById(R.id.iv_challenge_share_card);
        TextView shareText = view.findViewById(R.id.tv_challenge_share_text);
        TextView shareDesc = view.findViewById(R.id.tv_challenge_share_card);

        shareText.setText(mContest.title);
        Glide.with(profilePic.getContext())
                .load(mContest.thumbImage)
                .apply(new RequestOptions().placeholder(R.color.photo_placeholder))
                .into(profilePic);
        return CommonUtil.getViewBitmap(userDetailContainer);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
