package appliedlife.pvtltd.SHEROES.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ujjwal on 02/02/18.
 */

public class UserProfileCompactViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;

    // region ButterKnife Bindings
    @Bind(R.id.user_compact_card)
    CardView mUserCompactCard;

    @Bind(R.id.user_image)
    CircleImageView mImage;

    @Bind(R.id.bade_icon)
    ImageView mBadgeIcon;

    @Bind(R.id.post_count_title)
    TextView mPostCountTitle;

    @Bind(R.id.post_count)
    TextView mPostCount;

    @Bind(R.id.comments_count_title)
    TextView mCommentsCountTitle;

    @Bind(R.id.comments_count)
    TextView mCommentsCount;

    @Bind(R.id.follower_count_title)
    TextView mFollowerCountTitle;

    @Bind(R.id.follower_count)
    TextView mFollowerCount;

    @Bind(R.id.follow_button)
    TextView mFollowButton;

    @Bind(R.id.name)
    TextView mName;

    @Bind(R.id.location)
    TextView mLocation;

    @BindDimen(R.dimen.dp_size_80)
    int authorProfileSize;

    private BaseHolderInterface viewInterface;
    private UserSolrObj mUserSolrObj;

    // endregion

    public UserProfileCompactViewHolder(View itemView, Context context, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mContext = context;
        this.viewInterface = baseHolderInterface;
    }

    public void bindData(UserSolrObj userSolrObj, Context context) {
        mUserSolrObj = userSolrObj;
        if (CommonUtil.isNotEmpty(userSolrObj.getThumbnailImageUrl())) {
            mImage.setCircularImage(true);
            String authorThumborUrl = CommonUtil.getThumborUri(userSolrObj.getThumbnailImageUrl(), authorProfileSize, authorProfileSize);
            mImage.bindImage(authorThumborUrl);
        }
        String pluralPosts = context.getResources().getQuantityString(R.plurals.numberOfPosts, userSolrObj.getSolrIgnoreNoOfMentorPosts());
        mPostCount.setText(Integer.toString(userSolrObj.getSolrIgnoreNoOfMentorPosts()));
        mPostCountTitle.setText(pluralPosts);

        String pluralAnswers = context.getResources().getQuantityString(R.plurals.numberOfComments, userSolrObj.getUserCommentsCount());
        mCommentsCount.setText(Integer.toString(userSolrObj.getUserCommentsCount()));
        mCommentsCountTitle.setText(pluralAnswers);

        String pluralFollowers = context.getResources().getQuantityString(R.plurals.numberOfFollowers, userSolrObj.getUserFollowersCount());
        mFollowerCount.setText(Integer.toString(userSolrObj.getUserFollowersCount()));
        mFollowerCountTitle.setText(pluralFollowers);

        if (userSolrObj.isSolrIgnoreIsUserFollowed()) {
            mFollowButton.setTextColor(ContextCompat.getColor(context, R.color.white));
            mFollowButton.setText(context.getString(R.string.following_user));
            mFollowButton.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        } else {
            mFollowButton.setTextColor(ContextCompat.getColor(context, R.color.footer_icon_text));
            mFollowButton.setText(context.getString(R.string.follow_user));
            mFollowButton.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }

        mName.setText(userSolrObj.getNameOrTitle());

        if (CommonUtil.isNotEmpty(userSolrObj.getCityName())) {
            mLocation.setVisibility(View.VISIBLE);
            mLocation.setText(userSolrObj.getCityName());
        } else {
            mLocation.setVisibility(View.GONE);
        }

        showHideUserBadge(mBadgeIcon, mUserSolrObj.isSheBadgeActive(), mUserSolrObj.getProfileBadgeUrl());

    }

    //Show or hide the badge icon from user pic
    private void showHideUserBadge(ImageView userPic, boolean isBadgeShown, String badgeUrl) {
        if(isBadgeShown && !TextUtils.isEmpty(badgeUrl) && !TextUtils.isEmpty(badgeUrl)) {
            userPic.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(badgeUrl)
                    .into(userPic);
        } else {
            userPic.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.follow_button)
    public void onFollowClicked() {
        ((FeedItemCallback)viewInterface).onUserFollowedUnFollowed(mUserSolrObj);
    }

    @OnClick(R.id.user_compact_card)
    public void onUserCardClicked() {
        ((FeedItemCallback)viewInterface).onMentorProfileClicked(mUserSolrObj);
    }
}
