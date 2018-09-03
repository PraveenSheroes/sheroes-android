package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.community.BadgeDetails;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.views.adapters.BadgeClosetAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.BadgeDetailsDialogFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.views.activities.MentorsUserListingActivity.CHAMPION_SUBTYPE;

/**
 * Created by ravi on 03/01/18.
 * Badge Closet
 */

public class BadgeClosetActivity extends BaseActivity {

    //region constant declaration
    public static final String SCREEN_LABEL = "Badge Closet Screen";
    private static final String TITLE_NAME = "Badget Closet";
    public static final String BADGE_CLOSET_LIST = "Badge_closet_list";
    public static final String USER_DETAILS = "User_details";
    //endregion

    //region binding view variables
    @Bind(R.id.title_toolbar)
    TextView titleName;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.badges_list)
    RecyclerView mRecyclerView;
    //endregion

    //region private variables
    private List<BadgeDetails> badgeDetailsList;
    private UserSolrObj mUserSolrObj;
    //endregion

    //region activity life cycle method
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_badge_closet);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            if (getIntent().getParcelableExtra(BADGE_CLOSET_LIST) != null) {
                Parcelable parcelable = getIntent().getParcelableExtra(BADGE_CLOSET_LIST);
                if (parcelable != null) {
                    badgeDetailsList = Parcels.unwrap(parcelable);
                }

                if (getIntent().getParcelableExtra(USER_DETAILS) != null) {
                    Parcelable userDetailsParcelable = getIntent().getParcelableExtra(USER_DETAILS);
                    if (parcelable != null) {
                        mUserSolrObj = Parcels.unwrap(userDetailsParcelable);
                    }
                }
            }
        }

        setupToolbarItemsColor();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        BadgeClosetAdapter mAdapter = new BadgeClosetAdapter(this, badgeDetailsList, new BadgeClosetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BadgeDetails badgeDetails) {
                if(mUserSolrObj!=null) {
                    BadgeDetailsDialogFragment.showDialog(BadgeClosetActivity.this, mUserSolrObj, badgeDetails, SCREEN_LABEL, false);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
    //endregion

    private void setupToolbarItemsColor() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        titleName.setText(TITLE_NAME);
    }
    //endregion

    //region override method
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
    public boolean shouldTrackScreen() {
        return true;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
    //endregion

    //region static methods
    public static void navigateTo(Activity fromActivity, List<BadgeDetails> badgeDetails, UserSolrObj userSolrObj, String sourceScreen) {
        Intent intent = new Intent(fromActivity, BadgeClosetActivity.class);
        Parcelable parcelable = Parcels.wrap(badgeDetails);
        intent.putExtra(BADGE_CLOSET_LIST, parcelable);
        Parcelable userDetailsParcelable = Parcels.wrap(userSolrObj);
        intent.putExtra(USER_DETAILS, userDetailsParcelable);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        ActivityCompat.startActivity(fromActivity, intent, null);
    }
    //endregion
}
