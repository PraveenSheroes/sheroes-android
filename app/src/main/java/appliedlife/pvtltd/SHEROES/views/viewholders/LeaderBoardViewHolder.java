package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LeaderBoardUserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.BindInt;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 18/09/17.
 * LeaderBoard view holder
 */

public class LeaderBoardViewHolder extends BaseViewHolder<LeaderBoardUserSolrObj> {

    @Bind(R.id.top_header_container)
    LinearLayout topHeaderContainer;

    @Bind(R.id.about_leaderboard)
    TextView topHeaderView;

    @Bind(R.id.leader_board_users_container)
    RelativeLayout itemContainer;

    @Bind(R.id.top_user_title)
    TextView topUserTitle;

    @Bind(R.id.user_pic_icon)
    ImageView mProfilePic;

    @Bind(R.id.user_position)
    TextView mUserPosition;

    @Bind(R.id.badge_icon)
    ImageView badgeIcon;

    @Bind(R.id.follow_button)
    TextView mFollowButton;

    @Bind(R.id.name)
    TextView mName;

    @Bind(R.id.description)
    TextView mDescription;

    @BindDimen(R.dimen.dp_size_40)
    int mUserPicSize;

    @BindDimen(R.dimen.dp_size_40)
    int mBadgeIconSize;

    @BindDimen(R.dimen.leaderboard_position_user_pic_margin_start)
    int mUserPicMargin;

    @BindInt(R.integer.leader_board_selected_user_side_margin)
    int selectedRowSideMargin;

    @BindDimen(R.dimen.leaderboard_badge_user_pic_margin_left)
    int mMarginUserPicFromBadge;

    @BindInt(R.integer.leader_board_selected_user_bottom_margin)
    int selectedRowBottomMargin;

    @BindDimen(R.dimen.leaderboard_selected_row_radius)
    int mSelectedRowRadius;

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    Preference<AppConfiguration> mConfiguration;

    private long mLoggedInUserId = -1;
    private BaseHolderInterface viewInterface;
    private LeaderBoardUserSolrObj mLeaderBoardUserSolrObj;
    private ConfigData configData;

    public LeaderBoardViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;

        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            mLoggedInUserId = mUserPreference.get().getUserSummary().getUserId();
        }
    }

    @Override
    public void bindData(LeaderBoardUserSolrObj leaderBoardUserSolrObj, final Context context, int position) {

        if (leaderBoardUserSolrObj != null) {
            if (position == 0) {
                if (configData == null) {
                    configData = new ConfigData();
                }
                int leaderBoardTopUserCount = configData.leaderBoardTopUserCount;
                //Get the top user count in leader board
                if (mConfiguration.isSet() && mConfiguration.get().configData != null) {
                    leaderBoardTopUserCount = mConfiguration.get().configData.leaderBoardTopUserCount;
                }
                topUserTitle.setText(context.getResources().getString(R.string.leaderbaord_top_10_user, leaderBoardTopUserCount));
                topHeaderContainer.setVisibility(View.VISIBLE);
            } else {
                topHeaderContainer.setVisibility(View.GONE);
            }

            mLeaderBoardUserSolrObj = leaderBoardUserSolrObj;
            boolean isFollowed = false;
            mProfilePic.setOnClickListener(this);
            mName.setOnClickListener(this);
            topHeaderView.setOnClickListener(this);
            itemContainer.setOnClickListener(this);

            if (leaderBoardUserSolrObj.getSolrIgnoreBadgeDetails() != null && CommonUtil.isNotEmpty(leaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().getImageUrl())) {
                badgeIcon.setVisibility(View.VISIBLE);
                String badgeIconUrl = CommonUtil.getThumborUri(leaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().getImageUrl(), mBadgeIconSize, mBadgeIconSize);
                Glide.with(badgeIcon.getContext())
                        .load(badgeIconUrl)
                        .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(badgeIcon.getContext())))
                        .into(badgeIcon);
            } else {
                badgeIcon.setVisibility(View.GONE);
            }
            mName.setText(leaderBoardUserSolrObj.getUserSolrObj().getNameOrTitle());

            if (leaderBoardUserSolrObj.getUserSolrObj().getThumbnailImageUrl() != null && CommonUtil.isNotEmpty(leaderBoardUserSolrObj.getUserSolrObj().getThumbnailImageUrl())) {
                mProfilePic.setVisibility(View.VISIBLE);
                String userImage = CommonUtil.getThumborUri(leaderBoardUserSolrObj.getUserSolrObj().getThumbnailImageUrl(), mUserPicSize, mUserPicSize);
                Glide.with(mProfilePic.getContext())
                        .load(userImage)
                        .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(context)))
                        .into(mProfilePic);
            } else {
                mProfilePic.setVisibility(View.GONE);
            }

            String pluralLikes = context.getResources().getQuantityString(R.plurals.numberOfLikes, leaderBoardUserSolrObj.getSolrIgnoreNoOfLikesOnUserPost());
            String pluralComments = context.getResources().getQuantityString(R.plurals.numberOfComments, leaderBoardUserSolrObj.getSolrIgnoreNoOfCommentsOnUserPost());
            mDescription.setText(context.getResources().getString(R.string.leaderboard_user_like_comment, leaderBoardUserSolrObj.getSolrIgnoreNoOfLikesOnUserPost(), pluralLikes, leaderBoardUserSolrObj.getSolrIgnoreNoOfCommentsOnUserPost(), pluralComments));

            //Highlight user if in leaderBoard list
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            if (mLoggedInUserId != -1 && mLeaderBoardUserSolrObj.getUserSolrObj().getIdOfEntityOrParticipant() == mLoggedInUserId) {

                //If user is not in leaderBoard top users list , show its position in the end
                RelativeLayout.LayoutParams marginParams = new RelativeLayout.LayoutParams(
                        mUserPicSize, mUserPicSize);

                if (mLeaderBoardUserSolrObj.getLeaderBoardUserRankObj() != null && mLeaderBoardUserSolrObj.getLeaderBoardUserRankObj().getCommunityLeaderboardRank() != null) {
                    int userRank = mLeaderBoardUserSolrObj.getLeaderBoardUserRankObj().getCommunityLeaderboardRank();

                    itemContainer.setOnClickListener(null);
                    badgeIcon.setVisibility(View.GONE);
                    mUserPosition.setVisibility(View.VISIBLE);
                    marginParams.addRule(RelativeLayout.RIGHT_OF, mUserPosition.getId());
                    marginParams.setMargins(mUserPicMargin, 0, 0, 0);
                    if (configData == null) {
                        configData = new ConfigData();
                    }
                    int leaderBoardUserRankThreshold = configData.leaderBoardUserRankThreshold;
                    if (mConfiguration.isSet() && mConfiguration.get().configData != null) {
                        leaderBoardUserRankThreshold = mConfiguration.get().configData.leaderBoardUserRankThreshold;
                    }

                    if (userRank > leaderBoardUserRankThreshold) {
                        //Get the text that need to show when rank exceed threshold
                        String exceededRankText = configData.userRankExceedLimitText;
                        if (mConfiguration.isSet() && mConfiguration.get().configData != null) {
                            exceededRankText = mConfiguration.get().configData.userRankExceedLimitText;
                        }

                        if (StringUtil.isNotNullOrEmptyString(exceededRankText)) {
                            mUserPosition.setText(exceededRankText);
                        } else {
                            mUserPosition.setText(R.string.leaderboard_rank_unavailable);
                        }
                    } else {
                        mUserPosition.setText(String.valueOf(userRank));
                    }
                } else { //If user is in leaderBoard top users list
                    mUserPosition.setVisibility(View.GONE);
                    badgeIcon.setVisibility(View.VISIBLE);
                    itemContainer.setOnClickListener(this);
                    marginParams.setMargins(CommonUtil.convertDpToPixel(mMarginUserPicFromBadge, context), 0, 0, 0);
                }
                mProfilePic.setLayoutParams(marginParams);

                //Set background color and radius to row
                if (mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails() != null && StringUtil.isNotNullOrEmptyString(mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().getPrimaryColor())) {
                    PaintDrawable selectedRowDrawable = new PaintDrawable(Color.parseColor(mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().getPrimaryColor()));
                    selectedRowDrawable.setCornerRadius(mSelectedRowRadius);
                    itemContainer.setBackground(selectedRowDrawable);
                } else {
                    itemContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.white_color));
                }
                isFollowed = false;
                mFollowButton.setVisibility(View.GONE);
                mDescription.setTextColor(ContextCompat.getColor(context, R.color.white_color));
                mName.setTextColor(ContextCompat.getColor(context, R.color.white_color));
                layoutParams.setMargins(CommonUtil.convertDpToPixel(selectedRowSideMargin, context), 0, CommonUtil.convertDpToPixel(selectedRowSideMargin, context), CommonUtil.convertDpToPixel(selectedRowBottomMargin, context));
            } else {
                itemContainer.setBackgroundColor(Color.WHITE);
                itemContainer.setOnClickListener(this);
                mDescription.setTextColor(ContextCompat.getColor(context, R.color.leader_board_badge_sub_text));
                mName.setTextColor(ContextCompat.getColor(context, R.color.leaderboard_user));
                layoutParams.setMargins(0, 0, 0, 0);
                mUserPosition.setVisibility(View.GONE);
                mFollowButton.setVisibility(View.VISIBLE);
                isFollowed = mLeaderBoardUserSolrObj.getUserSolrObj() != null && mLeaderBoardUserSolrObj.getUserSolrObj().isSolrIgnoreIsUserFollowed();
            }

            followButtonVisibility(context, isFollowed);
            itemContainer.setLayoutParams(layoutParams);

            if (mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails() != null && mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().isActive()) {
                badgeIcon.setBackgroundResource(R.drawable.circular_background_yellow);
            } else {
                badgeIcon.setBackgroundResource(R.drawable.circular_background_grey);
            }
            mProfilePic.setBackgroundResource(R.drawable.circular_leaderbaord_user_icon_background_grey);
        }
    }

    //Follow/Following button in leaderboard
    private void followButtonVisibility(Context context, boolean isFollowed) {
        if (isFollowed) {
            mFollowButton.setTextColor(ContextCompat.getColor(context, R.color.white));
            mFollowButton.setText(context.getString(R.string.following_user));
            mFollowButton.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        } else {
            mFollowButton.setTextColor(ContextCompat.getColor(context, R.color.footer_icon_text));
            mFollowButton.setText(context.getString(R.string.follow_user));
            mFollowButton.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }
    }

    @Override
    public void viewRecycled() {
    }

    @Override
    public void onClick(View view) {
        if (viewInterface instanceof FeedItemCallback && mLeaderBoardUserSolrObj != null) {
            switch (view.getId()) {
                case R.id.name:
                case R.id.user_pic_icon:
                    ((FeedItemCallback) viewInterface).onLeaderBoardUserClick(mLeaderBoardUserSolrObj.getUserSolrObj().getIdOfEntityOrParticipant(), AppConstants.LEADERBOARD_SCREEN);
                    break;
                case R.id.leader_board_users_container:
                    ((FeedItemCallback) viewInterface).onLeaderBoardItemClick(mLeaderBoardUserSolrObj, AppConstants.LEADERBOARD_SCREEN);
                    break;
                case R.id.about_leaderboard:
                    ((FeedItemCallback) viewInterface).onLeaderBoardHeaderClick(mLeaderBoardUserSolrObj, AppConstants.LEADERBOARD_SCREEN);
                    break;
            }
        }
    }

    //region onClick methods
    @OnClick(R.id.follow_button)
    public void onFollowClicked() {
        ((FeedItemCallback) viewInterface).onFollowClicked(mLeaderBoardUserSolrObj.getUserSolrObj());
    }
}
