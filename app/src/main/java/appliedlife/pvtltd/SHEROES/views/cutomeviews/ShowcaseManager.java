package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.app.Activity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.models.entities.feed.Tutorial;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;

public class ShowcaseManager {

    private final Activity activity;
    private ShowcaseView showcaseView;
    private ViewTarget target;
    private HashMap<String, Tutorial> tutorial = new HashMap<>();
    private FloatingActionButton floatActionBtn;
    private ImageView tvHome;
    private ImageView tvCommunities;
    private TextView tvDrawerNavigation;
    private RecyclerView recyclerView;
    private String mUserName;

    public ShowcaseManager(Activity activity) {
        this.activity = activity;
    }

    public ShowcaseManager(Activity activity, FloatingActionButton floatActionBtn, ImageView tvHome, ImageView tvCommunities, TextView tvDrawerNavigation, RecyclerView recyclerView, String userName) {
        this.activity = activity;
        this.floatActionBtn = floatActionBtn;
        this.tvHome = tvHome;
        this.tvCommunities = tvCommunities;
        this.tvDrawerNavigation = tvDrawerNavigation;
        this.recyclerView = recyclerView;
        this.mUserName = userName;
        tvDrawerNavigation.setEnabled(false);
        floatActionBtn.setEnabled(false);
        tvHome.setEnabled(false);
        tvCommunities.setEnabled(false);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    //region showcase First in MainActivity
    public void showFirstMainActivityShowcase() {

        if (showcaseView != null)
            ((ViewGroup) showcaseView.getParent()).removeView(showcaseView);
        mUserName = activity.getString(R.string.ID_SHOW_CASE_FEED_TITLE,mUserName);
        showcaseView = new ShowcaseView.Builder(activity)
                .withMaterialShowcase()
                .setTarget(new ViewTarget(tvHome))
                .setStyle(R.style.CustomShowcaseTheme)
                .setContentTitle(mUserName)
                .setContentText(activity.getString(R.string.ID_SHOW_CASE_FEED_DEC))
                .replaceEndButton(R.layout.showcase_btn_layout)
                .setShowcaseEventListener(
                        new SimpleShowcaseEventListener() {
                            @Override
                            public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                HashMap<String, Object> properties = new EventProperty.Builder().build();
                                AnalyticsManager.trackEvent(Event.WALKTHROUGH_STARTED, "", properties);
                                showSecondMainActivityShowcase();
                            }
                        }
                )
                .build();
        showcaseView.setButtonText(activity.getString(R.string.ID_NEXT));
        showcaseView.setButtonPosition(getButtonLayoutParams());
        // showcaseView.setDetailTextAlignment(Layout.Alignment.ALIGN_CENTER);
        // showcaseView.setTitleTextAlignment(Layout.Alignment.ALIGN_CENTER);
        // showcaseView.forceTextPosition(ShowcaseView.ABOVE);
    }
    //endregion


    //region showcase Second in MainActivity
    private void showSecondMainActivityShowcase() {
        if (showcaseView != null)
            ((ViewGroup) showcaseView.getParent()).removeView(showcaseView);
        showcaseView = new ShowcaseView.Builder(activity)
                .withMaterialShowcase()
                .setTarget(new ViewTarget(tvCommunities))
                .setStyle(R.style.CustomShowcaseTheme)
                .setContentTitle(activity.getString(R.string.ID_SHOW_CASE_COMMUNITIES_TITLE))
                .setContentText(activity.getString(R.string.ID_SHOW_CASE_COMMUNITIES_DEC))
                .replaceEndButton(R.layout.showcase_btn_layout)
                .setShowcaseEventListener(
                        new SimpleShowcaseEventListener() {
                            @Override
                            public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                showThirdMainActivityShowCase();
                            }
                        }
                )
                .build();
        showcaseView.setButtonText(activity.getString(R.string.ID_NEXT));
        showcaseView.setButtonPosition(getButtonLayoutParams());
        // showcaseView.setDetailTextAlignment(Layout.Alignment.ALIGN_CENTER);
        //  showcaseView.setTitleTextAlignment(Layout.Alignment.ALIGN_CENTER);
    }
    //endregion

    //region showcase Third in MainActivity
    private void showThirdMainActivityShowCase() {
        if (showcaseView != null)
            ((ViewGroup) showcaseView.getParent()).removeView(showcaseView);
        showcaseView = new ShowcaseView.Builder(activity)
                .withMaterialShowcase()
                .setTarget(new ViewTarget(floatActionBtn))
                .setStyle(R.style.CustomShowcaseTheme)
                .setContentTitle(activity.getString(R.string.ID_SHOW_CASE_CREATE_POST_TITLE))
                .setContentText(activity.getString(R.string.ID_SHOW_CASE_CREATE_POST_DESC))
                .replaceEndButton(R.layout.showcase_btn_layout)
                .setShowcaseEventListener(
                        new SimpleShowcaseEventListener() {
                            @Override
                            public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                floatActionBtn.setEnabled(true);
                                tvHome.setEnabled(true);
                                tvCommunities.setEnabled(true);
                                tvDrawerNavigation.setEnabled(true);
                                recyclerView.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        return false;
                                    }
                                });
                                HashMap<String, Object> properties = new EventProperty.Builder().build();
                                AnalyticsManager.trackEvent(Event.WALKTHROUGH_COMPLETED, "", properties);
                            }
                        }
                )
                .build();
        showcaseView.setButtonText(activity.getString(R.string.ID_GOT));
        showcaseView.setButtonPosition(getButtonLayoutParams());
        // showcaseView.setDetailTextAlignment(Layout.Alignment.ALIGN_CENTER);
        // showcaseView.setTitleTextAlignment(Layout.Alignment.ALIGN_CENTER);
    }
    //endregion

    //region private helper methods
    private RelativeLayout.LayoutParams getButtonLayoutParams() {
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        lps.setMargins(0, 0, 0, CommonUtil.convertDpToPixel(100, activity));
        return lps;
    }


    //endregion

    //region public helper methods

    //endregion
}
