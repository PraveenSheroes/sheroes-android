package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.community.BadgeDetails;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LeaderBoardUserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ravi on 02/05/18.
 * Dialog to display the details of community badge
 */

public class BadgeDetailsDialogFragment extends BaseDialogFragment {

    //region private member variable
    public static String SCREEN_NAME = "Badge Dialog Screen";
    private static final String IS_LEADER_BOARD = "IS_LEADER_BOARD";
    private static final String LEADER_BOARD_DETAILS = "LeaderBoard_Details";
    private boolean isLeaderBoard = false;

    //endregion

    //region private member variable
    private LeaderBoardUserSolrObj mLeaderBoardUserSolrObj;
    //endregion

    @Inject
    Preference<Configuration> mConfiguration;

    //region Bind view variables

    @Bind(R.id.header_container)
    FrameLayout headerContainer;

    @Bind(R.id.badge_icon)
    ImageView badgeIcon;

    @Bind(R.id.badge_title)
    TextView badgeTitle;

    @Bind(R.id.badge_desc)
    TextView badgeDesc;

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

        if (getArguments() != null) {
            isLeaderBoard = getArguments().getBoolean(IS_LEADER_BOARD);
            SCREEN_NAME = getArguments().getString(BaseActivity.SOURCE_SCREEN);

            //Todo -handle with usersolor object when profile get badge
            if (getArguments().getParcelable(LEADER_BOARD_DETAILS) != null) {
                Parcelable parcelable = getArguments().getParcelable(LEADER_BOARD_DETAILS);
                mLeaderBoardUserSolrObj = Parcels.unwrap(parcelable);
            }
        }

        if (!isLeaderBoard) {
            showLeaderBoard.setVisibility(View.VISIBLE);
            viewProfile.setVisibility(View.GONE);
        } else {
            showLeaderBoard.setVisibility(View.GONE);
            viewProfile.setVisibility(View.VISIBLE);
        }

        if(mLeaderBoardUserSolrObj!=null && mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails()!=null) {
            BadgeDetails leaderBoardUser = mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails();
            headerContainer.setBackgroundColor(Color.parseColor(leaderBoardUser.getPrimaryColor()));

            badgeTitle.setText(leaderBoardUser.getName());
            if (CommonUtil.isNotEmpty(leaderBoardUser.getImageUrl())) {
                String trophyImageUrl = CommonUtil.getThumborUri(leaderBoardUser.getImageUrl(), 108 , 108);
                Glide.with(badgeIcon.getContext())
                        .load(trophyImageUrl)
                        .into(badgeIcon);
               // badgeIcon.setBackgroundResource(ContextCompat.getDrawable(getActivity(), R.drawble.circle));

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    badgeDesc.setText(Html.fromHtml(leaderBoardUser.getDescription(), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    badgeDesc.setText(Html.fromHtml(leaderBoardUser.getDescription()));
                }
            }
        }


        //Add analytics screen open Event
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

    public static BadgeDetailsDialogFragment showDialog(Activity activity, LeaderBoardUserSolrObj leaderBoardUserSolrObj, String sourceScreen, boolean isLeaderBaord) {

        BadgeDetailsDialogFragment badgeDetailsDialogFragment = new BadgeDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_LEADER_BOARD, isLeaderBaord);
        badgeDetailsDialogFragment.setArguments(args);
        Parcelable parcelable = Parcels.wrap(leaderBoardUserSolrObj);
        args.putParcelable(LEADER_BOARD_DETAILS, parcelable);
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
        ProfileActivity.navigateTo(getActivity(), mLeaderBoardUserSolrObj.getUserSolrObj().getIdOfEntityOrParticipant(), false, -1, SCREEN_NAME, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
    }

    @OnClick(R.id.show_leaderBoard)
    protected void showLeaderBaord() {
        dismiss();
        if(getActivity().isFinishing()) return;
        CommunityDetailActivity.navigateTo(getActivity(), mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().getCommunityId(), SCREEN_NAME, null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
    }
    //endregion
}