package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
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
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 07/02/18.
 */

public class ChallengeGratificationActivity extends BaseActivity {
    private static final String SCREEN_LABEL = "Challenge Gratification Activity";
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
    private FeedDetail mFeedDetail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.challenge_graticfication_layout);
        ButterKnife.bind(this);
        init();
        setupToolbar();
    }

    private void init() {
        if (null != getIntent() && null != getIntent().getExtras()) {
            mFeedDetail = Parcels.unwrap(getIntent().getParcelableExtra(AppConstants.CHALLENGE_GRATIFICATION));
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbarView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbarTitle.setText(getString(R.string.ID_CHALLENGE_COMPLETED));
    }

    public static void navigateTo(Activity fromActivity, FeedDetail feedDetail, String sourceScreen, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, ProfileActivity.class);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        Bundle bundle = new Bundle();
        Parcelable parcelable = Parcels.wrap(feedDetail);
        bundle.putParcelable(AppConstants.CHALLENGE_GRATIFICATION, parcelable);
        intent.putExtras(bundle);
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    @OnClick(R.id.tv_mentor_answering_question)
    public void shareClick() {
        if (mFeedDetail != null) {
            Bitmap bitmap = createShareImage();
            ShareBottomSheetFragment.showDialog(this, mFeedDetail, bitmap, SCREEN_LABEL);
        }
    }

    private Bitmap createShareImage() {
        View view;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.challenge_share_card_layout, null, false);

        LinearLayout userDetailContainer = view.findViewById(R.id.ll_sharable_card);
        ImageView profilePic = view.findViewById(R.id.iv_challenge_share_card);
        TextView shareText = view.findViewById(R.id.tv_challenge_share_text);
        TextView shareDesc = view.findViewById(R.id.tv_challenge_share_card);

        shareText.setText(mFeedDetail.getNameOrTitle());
        Glide.with(profilePic.getContext())
                .load(mFeedDetail.getImageUrl())
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
}
