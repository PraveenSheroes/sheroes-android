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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

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
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
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
 * Dialog to display the details of profile or community badge
 */

public class BadgeDetailsDialogFragment extends BaseDialogFragment {

    //region private member variable
    public static String SCREEN_NAME = "Badge Details Dialog Screen";
    private static String DAY_DATE_FORMATTER = "dd";
    private static String DAY_MONTH_YEAR_DATE_FORMATTER = "dd MMM yyyy";
    private static final String IS_LEADER_BOARD = "IS_LEADER_BOARD";
    private static final String BADGE_DETAILS = "Badge_Details";
    private static final String USER_DETAILS = "user_Details";
    private static final int BADGE_ICON_SIZE = 108;
    private boolean isLeaderBoard = false;
    private BadgeDetails mBadgeDetails;
    private UserSolrObj mUserSolrObj;
    private long mLoggedInUserId = -1;
    private String previousScreenName;
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

    @Bind(R.id.bade_won_count_label)
    TextView badgeWonCounterText;

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

            if (getArguments().getParcelable(BADGE_DETAILS) != null) {
                Parcelable parcelable = getArguments().getParcelable(BADGE_DETAILS);
                mBadgeDetails = Parcels.unwrap(parcelable);
            }

            if (getArguments().getParcelable(USER_DETAILS) != null) {
                Parcelable parcelable = getArguments().getParcelable(USER_DETAILS);
                mUserSolrObj = Parcels.unwrap(parcelable);
            }

            if (getArguments().getString(BaseActivity.SOURCE_SCREEN) != null) {
                previousScreenName = getArguments().getString(BaseActivity.SOURCE_SCREEN);
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
        if (mLoggedInUserId == mUserSolrObj.getIdOfEntityOrParticipant()) {
            okButton.setVisibility(View.GONE);
            shareButton.setVisibility(View.VISIBLE);
        } else {
            okButton.setVisibility(View.VISIBLE);
            shareButton.setVisibility(View.GONE);
        }

        if (mBadgeDetails != null) {
            BadgeDetails leaderBoardUser = mBadgeDetails;
            headerContainer.setBackgroundColor(Color.parseColor(leaderBoardUser.getPrimaryColor()));

            badgeTitle.setText(leaderBoardUser.getName());
            badgeTitle.setTextColor(Color.parseColor(leaderBoardUser.getSecondaryColor()));
            if (CommonUtil.isNotEmpty(leaderBoardUser.getImageUrl())) {
                String trophyImageUrl = CommonUtil.getThumborUri(leaderBoardUser.getImageUrl(), BADGE_ICON_SIZE, BADGE_ICON_SIZE);
                Glide.with(badgeIcon.getContext())
                        .load(trophyImageUrl)
                        .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(getActivity())))
                        .into(badgeIcon);
            }

            String startDate = mBadgeDetails.getSolrIgnoreStartDate();
            String endDate = mBadgeDetails.getSolrIgnoreEndDate();

            //Start and end date for badge period.
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dayFormat = new SimpleDateFormat(DAY_DATE_FORMATTER);
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat(DAY_MONTH_YEAR_DATE_FORMATTER);
            Date startDateObj = DateUtil.parseDateFormat(startDate, DATE_FORMAT);
            Date endDateObj = DateUtil.parseDateFormat(endDate, DATE_FORMAT);

            String day = dayFormat.format(startDateObj);
            String endDateText = dateFormat.format(endDateObj);

            //For profile if badge is inactive show message "Won last on" other "Won Latest on" and hide o
            if (!isLeaderBoard) {
                if (mBadgeDetails.isActive()) {
                    showLeaderBoard.setVisibility(View.VISIBLE);
                    badgeWonPeriod.setText(getActivity().getResources().getString(R.string.badge_period_date_text, day, endDateText));
                } else {
                    showLeaderBoard.setVisibility(View.GONE);
                    badgeWonPeriod.setText(getActivity().getResources().getString(R.string.badge_inactive_period_date_text, day, endDateText));
                }
            } else {
                showLeaderBoard.setVisibility(View.GONE);
                badgeWonPeriod.setText(getActivity().getResources().getString(R.string.badge_period_date_text, day, endDateText));
            }

            String mutualCommunityText = getResources().getString(R.string.badge_desc, CommonUtil.camelCaseString(mUserSolrObj.getNameOrTitle().trim().toLowerCase()), CommonUtil.camelCaseString(mBadgeDetails.getCommunityName().toLowerCase()));
            badgeDesc.setText(mutualCommunityText);


            //check for how many times user have won the badge
            if (!isLeaderBoard) {
                int badgeWonCount = mBadgeDetails.getBadgeCount();
                if (badgeWonCount > 0) {
                    String badgeWonTimePlurals = getResources().getQuantityString(R.plurals.badgeWonTime, badgeWonCount);
                    badgeWonCounterText.setText(getString(R.string.badge_won_times_label, badgeWonCount, badgeWonTimePlurals));
                    badgeWonCounterText.setVisibility(View.VISIBLE);
                } else {
                    badgeWonCounterText.setVisibility(View.GONE);
                }
            } else {
                badgeWonCounterText.setVisibility(View.GONE);
            }

            //Analytics
            if (mBadgeDetails != null && previousScreenName != null) {
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(String.valueOf(mBadgeDetails.getId()))
                                .communityId(String.valueOf(mBadgeDetails.getCommunityId()))
                                .isBadgeActive(mBadgeDetails.isActive())
                                .build();
                AnalyticsManager.trackEvent(Event.BADGE_CLICKED, previousScreenName, properties);
            }
        }
        return view;
    }
    //endregion

    public static BadgeDetailsDialogFragment showDialog(Activity activity, UserSolrObj userSolrObj, BadgeDetails badgeDetails, String sourceScreen, boolean isLeaderBaord) {

        BadgeDetailsDialogFragment badgeDetailsDialogFragment = new BadgeDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_LEADER_BOARD, isLeaderBaord);
        Parcelable parcelable = Parcels.wrap(badgeDetails);
        args.putParcelable(BADGE_DETAILS, parcelable);
        Parcelable userObjParcelable = Parcels.wrap(userSolrObj);
        args.putParcelable(USER_DETAILS, userObjParcelable);
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
        ProfileActivity.navigateTo(getActivity(), mUserSolrObj.getIdOfEntityOrParticipant(), false, -1, SCREEN_NAME, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
    }

    @OnClick(R.id.show_leaderBoard)
    protected void showLeaderBaord() {
        dismiss();
        if (getActivity().isFinishing()) return;
        CommunityDetailActivity.navigateTo(getActivity(), mBadgeDetails.getCommunityId(), SCREEN_NAME, null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
    }
    //endregion

    private void shareBadgeCard() {
        if (getActivity() == null && getActivity().isFinishing()) return;

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_badge_share, null, false);
        final FrameLayout cardContainer = view.findViewById(R.id.badge_card_container);
        final FrameLayout headerContainer = view.findViewById(R.id.header_container);
        final TextView badgeTitle = view.findViewById(R.id.badge_title);
        final TextView badgeDesc = view.findViewById(R.id.badge_desc);
        final ImageView badgeIcon = view.findViewById(R.id.card_badge_icon);

        final String badgeUrl = mBadgeDetails.getImageUrl();
        final String backgroundColor = mBadgeDetails.getPrimaryColor();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            badgeDesc.setText(Html.fromHtml(mBadgeDetails.getDescription(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            badgeDesc.setText(Html.fromHtml(mBadgeDetails.getDescription()));
        }
        badgeTitle.setText(mBadgeDetails.getName());
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
                                                .id(String.valueOf(mBadgeDetails.getId()))
                                                .communityId(String.valueOf(mBadgeDetails.getCommunityId()))
                                                .isBadgeActive(mBadgeDetails.isActive())
                                                .name(mUserPreference.get().getUserSummary().getFirstName())
                                                .build();
                                AnalyticsManager.trackEvent(Event.BADGE_SHARED, SCREEN_NAME, properties);

                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType(AppConstants.SHARE_MENU_TYPE);
                                String badgeShareMsg = new ConfigData().mBadgeShareMsg;
                                if (mConfiguration.isSet() && mConfiguration.get().configData != null) {
                                    badgeShareMsg = mConfiguration.get().configData.mBadgeShareMsg;
                                }
                                intent.putExtra(Intent.EXTRA_TEXT, badgeShareMsg + "\n" + mUserSolrObj.getPostShortBranchUrls());
                                intent.putExtra(Intent.EXTRA_STREAM, contentUri);
                                intent.setType("image/*");
                                startActivity(Intent.createChooser(intent, AppConstants.SHARE));
                            }
                        }
                    });
        }
    }
}