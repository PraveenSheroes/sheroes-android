package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.community.BadgeDetails;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LeaderBoardUserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.DATE_FORMAT;

/**
 * Created by ravi on 02/05/18.
 * Dialog to display the details of community badge
 */

public class BadgeDetailsDialogFragment extends BaseDialogFragment {

    //region private member variable
    public static String SCREEN_NAME = "Badge Details Dialog Screen";
    private static final String IS_LEADER_BOARD = "IS_LEADER_BOARD";
    private static final String LEADER_BOARD_DETAILS = "LeaderBoard_Details";
    private boolean isLeaderBoard = false;
    private LeaderBoardUserSolrObj mLeaderBoardUserSolrObj;
    private long mLoggedInUserId = -1;
    //endregion

    @Inject
    Preference<Configuration> mConfiguration;

    @Inject
    Preference<LoginResponse> mUserPreference;

    //region Bind view variables

    @Bind(R.id.header_container)
    FrameLayout headerContainer;

    @Bind(R.id.badge_icon)
    ImageView badgeIcon;

    @Bind(R.id.badge_title)
    TextView badgeTitle;

    @Bind(R.id.bade_won_date_label)
    TextView badgeWonPeriod;

    @Bind(R.id.badge_desc)
    TextView badgeDesc;

    @Bind(R.id.view_profile)
    TextView viewProfile;

    @Bind(R.id.show_leaderBoard)
    TextView showLeaderBoard;

    @Bind(R.id.ok)
    TextView okButton;

    @Bind(R.id.share)
    TextView shareButton;

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

            if (getArguments().getParcelable(LEADER_BOARD_DETAILS) != null) {
                Parcelable parcelable = getArguments().getParcelable(LEADER_BOARD_DETAILS);
                mLeaderBoardUserSolrObj = Parcels.unwrap(parcelable);
            }
        }

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            mLoggedInUserId = mUserPreference.get().getUserSummary().getUserId();
        }

        if (!isLeaderBoard) {
            showLeaderBoard.setVisibility(View.VISIBLE);
            viewProfile.setVisibility(View.GONE);
        } else {
            showLeaderBoard.setVisibility(View.GONE);
            viewProfile.setVisibility(View.VISIBLE);
        }

        //Enable share for own badge
        if(mLoggedInUserId == mLeaderBoardUserSolrObj.getUserSolrObj().getIdOfEntityOrParticipant()) {
            okButton.setVisibility(View.GONE);
            shareButton.setVisibility(View.VISIBLE);
        } else {
            okButton.setVisibility(View.VISIBLE);
            shareButton.setVisibility(View.GONE);
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

                String startDate = mLeaderBoardUserSolrObj.getSolrIgnoreStartDate();
                String endDate = mLeaderBoardUserSolrObj.getSolrIgnoreEndDate();

                //Start and end date for badge period.
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                Date startDateObj = DateUtil.parseDateFormat(startDate, DATE_FORMAT);
                Date endDateObj = DateUtil.parseDateFormat(endDate, DATE_FORMAT);

                String day = dayFormat.format(startDateObj);
                String endDateText = dateFormat.format(endDateObj);
                badgeWonPeriod.setText(getActivity().getResources().getString(R.string.badge_period_date_text, day, endDateText));

                String mutualCommunityText = getResources().getString(R.string.badge_desc, mLeaderBoardUserSolrObj.getUserSolrObj().getNameOrTitle(), mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().getCommunityName());
                badgeDesc.setText(mutualCommunityText);
            }
        }
        return view;
    }
    //endregion

    public static BadgeDetailsDialogFragment showDialog(Activity activity, LeaderBoardUserSolrObj leaderBoardUserSolrObj, String sourceScreen, boolean isLeaderBaord) {

        BadgeDetailsDialogFragment badgeDetailsDialogFragment = new BadgeDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_LEADER_BOARD, isLeaderBaord);
        Parcelable parcelable = Parcels.wrap(leaderBoardUserSolrObj);
        args.putParcelable(LEADER_BOARD_DETAILS, parcelable);
        args.putString(BaseActivity.SOURCE_SCREEN, sourceScreen);
        badgeDetailsDialogFragment.setArguments(args);
        badgeDetailsDialogFragment.setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, 0);
        badgeDetailsDialogFragment.show(activity.getFragmentManager(), SCREEN_NAME);

        return badgeDetailsDialogFragment;

    }

    //region onclick method
    @OnClick({R.id.cross, R.id.ok})
    protected void crossClick() {
        dismiss();
    }

    @OnClick(R.id.share)
    protected void shareCard() {
        shareBadgeCard();
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

    private void shareBadgeCard() {
        if (getActivity()==null && getActivity().isFinishing()) return;

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_badge_share, null, false);
        final FrameLayout cardContainer = view.findViewById(R.id.badge_card_container);
        final FrameLayout headerContainer = view.findViewById(R.id.header_container);
        final TextView badgeTitle = view.findViewById(R.id.badge_title);
        final TextView badgeDesc = view.findViewById(R.id.badge_desc);
        final ImageView badgeIcon = view.findViewById(R.id.card_badge_icon);

        final String badgeUrl = mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().getImageUrl();
        final String backgroundColor = mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().getPrimaryColor();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            badgeDesc.setText(Html.fromHtml(mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().getDescription(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            badgeDesc.setText(Html.fromHtml(mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().getDescription()));
        }
        BadgeDetails leaderBoardUser = mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails();
        badgeTitle.setText(leaderBoardUser.getName());
        headerContainer.setBackgroundColor(Color.parseColor(backgroundColor));

        if (StringUtil.isNotNullOrEmptyString(badgeUrl)) {
            Glide.with(getActivity())
                    .asBitmap()
                    .load(badgeUrl)
                    .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(getActivity())))
                    .into(new BitmapImageViewTarget(badgeIcon) {

                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            super.onResourceReady(resource, transition);
                            badgeIcon.setImageBitmap(resource);
                            Bitmap bitmap = CommonUtil.getViewBitmap(cardContainer);
                            Uri contentUri = CommonUtil.getContentUriFromBitmap(getActivity(), bitmap);
                            dismiss();
                            if (contentUri != null) {
                                //Analytics for Badge Shared
                                HashMap<String, Object> properties =
                                        new EventProperty.Builder()
                                                .id(String.valueOf(mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().getId()))
                                                .isBadgeActive(mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().isIsActive())
                                                .name(mUserPreference.get().getUserSummary().getFirstName())
                                                .build();
                                AnalyticsManager.trackEvent(Event.BADGE_SHARED, SCREEN_NAME, properties);

                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType(AppConstants.SHARE_MENU_TYPE);
                                String badgeShareMsg = new ConfigData().mBadgeShareMsg;
                                if (mConfiguration.isSet() && mConfiguration.get().configData != null) {
                                    badgeShareMsg = mConfiguration.get().configData.mBadgeShareMsg;
                                }
                                intent.putExtra(Intent.EXTRA_TEXT, badgeShareMsg + mLeaderBoardUserSolrObj.getUserSolrObj().getPostShortBranchUrls());
                                intent.putExtra(Intent.EXTRA_STREAM, contentUri);
                                intent.setType("image/*");
                                startActivity(Intent.createChooser(intent, AppConstants.SHARE));
                            }
                        }
                    });
        }
    }
}