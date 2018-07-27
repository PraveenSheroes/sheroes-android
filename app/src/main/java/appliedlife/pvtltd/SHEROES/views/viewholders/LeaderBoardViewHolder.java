package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.graphics.Color;
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
import appliedlife.pvtltd.SHEROES.models.entities.feed.LeaderBoardUserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

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

    @Bind(R.id.badge_icon)
    ImageView badgeIcon;

    @Bind(R.id.name)
    TextView mName;

    @Bind(R.id.description)
    TextView mDescription;

    @BindDimen(R.dimen.dp_size_40)
    int mUserPicSize;

    @Inject
    Preference<LoginResponse> mUserPreference;

    private int mNumCount = 10;
    private long mLoggedInUserId = -1;
    private float mPaddingLeftForSelectedRow = 14.0f;
    private BaseHolderInterface viewInterface;
    private LeaderBoardUserSolrObj mLeaderBoardUserSolrObj;

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
                topHeaderContainer.setVisibility(View.VISIBLE);
            } else {
                topHeaderContainer.setVisibility(View.GONE);
            }

            mLeaderBoardUserSolrObj = leaderBoardUserSolrObj;

            mProfilePic.setOnClickListener(this);
            mName.setOnClickListener(this);
            topHeaderView.setOnClickListener(this);
            itemContainer.setOnClickListener(this);

            if (leaderBoardUserSolrObj.getSolrIgnoreBadgeDetails()!=null && CommonUtil.isNotEmpty(leaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().getImageUrl())) {
                String trophyImageUrl = CommonUtil.getThumborUri(leaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().getImageUrl(), mUserPicSize, mUserPicSize);
                Glide.with(badgeIcon.getContext())
                        .load(trophyImageUrl)
                        .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(badgeIcon.getContext())))
                        .into(badgeIcon);
            }
            mName.setText(leaderBoardUserSolrObj.getUserSolrObj().getNameOrTitle());

            if (leaderBoardUserSolrObj.getUserSolrObj().getThumbnailImageUrl() != null && CommonUtil.isNotEmpty(leaderBoardUserSolrObj.getUserSolrObj().getThumbnailImageUrl())) {
                String userImage = CommonUtil.getThumborUri(leaderBoardUserSolrObj.getUserSolrObj().getThumbnailImageUrl(), mUserPicSize, mUserPicSize);
                Glide.with(mProfilePic.getContext())
                        .load(userImage)
                        .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(context)))
                        .into(mProfilePic);
            }

            topUserTitle.setText(context.getResources().getString(R.string.leaderbaord_top_10_user, mNumCount));
            String pluralLikes = context.getResources().getQuantityString(R.plurals.numberOfLikes, leaderBoardUserSolrObj.getSolrIgnoreNoOfLikesOnUserPost());
            String pluralComments = context.getResources().getQuantityString(R.plurals.numberOfComments, leaderBoardUserSolrObj.getSolrIgnoreNoOfCommentsOnUserPost());
            mDescription.setText(context.getResources().getString(R.string.leaderboard_user_like_comment, leaderBoardUserSolrObj.getSolrIgnoreNoOfLikesOnUserPost(), pluralLikes, leaderBoardUserSolrObj.getSolrIgnoreNoOfCommentsOnUserPost(), pluralComments));

            //Highlight user if in leaderBoard list
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            if (mLoggedInUserId != -1 && mLeaderBoardUserSolrObj.getUserSolrObj().getIdOfEntityOrParticipant() == mLoggedInUserId) {
                final String backgroundColor = mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().getPrimaryColor();
                itemContainer.setBackgroundColor(Color.parseColor(backgroundColor));
                mDescription.setTextColor(ContextCompat.getColor(context, R.color.white_color));
                mName.setTextColor(ContextCompat.getColor(context, R.color.white_color));
                itemContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.border_new_feature));
                layoutParams.setMargins(CommonUtil.convertDpToPixel(mPaddingLeftForSelectedRow, context), 0, CommonUtil.convertDpToPixel(mPaddingLeftForSelectedRow, context), 0);
            } else {
                itemContainer.setBackgroundColor(Color.WHITE);
                mDescription.setTextColor(ContextCompat.getColor(context, R.color.leader_board_badge_sub_text));
                mName.setTextColor(ContextCompat.getColor(context, R.color.gray_light));
                itemContainer.setBackground(null);
                layoutParams.setMargins(0, 0, 0, 0);
            }
            itemContainer.setLayoutParams(layoutParams);

            if(mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails()!=null) {
                if (mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().isActive()) {
                    badgeIcon.setBackgroundResource(R.drawable.circular_background_yellow);
                } else {
                    badgeIcon.setBackgroundResource(R.drawable.circular_background_grey);
                }
            }
            mProfilePic.setBackgroundResource(R.drawable.circular_background_grey);
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
                    ((FeedItemCallback) viewInterface).onLeaderBoardUserClick(mLeaderBoardUserSolrObj.getUserSolrObj().getIdOfEntityOrParticipant(), AppConstants.LEADER_BOARD_SCREEN);
                    break;
                case R.id.leader_board_users_container:
                    ((FeedItemCallback) viewInterface).onLeaderBoardItemClick(mLeaderBoardUserSolrObj, AppConstants.LEADER_BOARD_SCREEN);
                    break;
                case R.id.about_leaderboard:
                    ((FeedItemCallback) viewInterface).onLeaderBoardHeaderClick(mLeaderBoardUserSolrObj, AppConstants.LEADER_BOARD_SCREEN);
                    break;
            }
        }
    }

    public void totalNumberOfUsersInLeaderBoard(int numCount) {
        mNumCount = numCount;
    }

}
