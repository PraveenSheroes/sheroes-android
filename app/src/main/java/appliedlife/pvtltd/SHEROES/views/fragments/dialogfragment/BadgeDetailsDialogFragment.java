package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.ProgressbarView;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.EditUserProfileActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.DashProgressBar;
import appliedlife.pvtltd.SHEROES.views.fragments.LikeListBottomSheetFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ravi on 02/05/18.
 * Dialog to display the details of community badge
 */

public class BadgeDetailsDialogFragment extends BaseDialogFragment {

    //region private member variable
    public static final String SCREEN_NAME = "Badge Dialog";
    private static final String IS_LEADER_BOARD = "IS_LEADERBOARD";
    private boolean isLeaderBoard = false;
    //endregion

    //region private member variable
    //endregion

    @Inject
    Preference<Configuration> mConfiguration;

    //region Bind view variables

    @Bind(R.id.view_profile)
    TextView viewProfile;

    @Bind(R.id.show_leaderBoard)
    TextView showLeaderBoard;

    //endregion

    //region Fragment methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (getActivity() == null) return null;

        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.community_badge_detail, container, false);
        ButterKnife.bind(this, view);

        //Add analytics screen open Event
            if (getArguments() != null) {
                isLeaderBoard = getArguments().getBoolean(IS_LEADER_BOARD);
            }

        if (!isLeaderBoard) {
            showLeaderBoard.setVisibility(View.VISIBLE);
            viewProfile.setVisibility(View.GONE);
        } else {
            showLeaderBoard.setVisibility(View.GONE);
            viewProfile.setVisibility(View.VISIBLE);
        }

          /*      String strengthLevel = userLevel(mUserSolrObj).name();
                String sourceScreenName = getArguments().getString(AppConstants.SOURCE_NAME);
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .isMentor(mUserSolrObj.isAuthorMentor())
                                .profileStrength(strengthLevel)
                                .build();
                AnalyticsManager.trackScreenView(SCREEN_NAME, sourceScreenName, properties);
            }
        }*/

        return view;
    }
    //endregion

    public static BadgeDetailsDialogFragment showDialog(Activity activity, String sourceScreen, boolean isLeaderBaord) {

        BadgeDetailsDialogFragment badgeDetailsDialogFragment = new BadgeDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_LEADER_BOARD, isLeaderBaord);
        badgeDetailsDialogFragment.setArguments(args);
        args.putString(BaseActivity.SOURCE_SCREEN, sourceScreen);
        badgeDetailsDialogFragment.setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, 0);
        badgeDetailsDialogFragment.show(activity.getFragmentManager(), SCREEN_NAME);

        return badgeDetailsDialogFragment;

    }

    //region onclick method
    @OnClick(R.id.cross)
    protected void crossClick() {
        dismiss();
    }

    @OnClick(R.id.view_profile)
    protected void openUserProfile() {
        dismiss();
        ProfileActivity.navigateTo(getActivity(), 995047, false, -1, SCREEN_NAME, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
    }


    @OnClick(R.id.show_leaderBoard)
    protected void showLeaderBaord() {
        dismiss();
        //CommonUtil
        ProfileActivity.navigateTo(getActivity(), 995047, false, -1, SCREEN_NAME, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
    }

}