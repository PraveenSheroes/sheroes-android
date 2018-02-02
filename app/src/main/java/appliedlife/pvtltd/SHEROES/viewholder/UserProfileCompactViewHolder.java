package appliedlife.pvtltd.SHEROES.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
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

    @Bind(R.id.bio)
    TextView mBio;

    // endregion

    public UserProfileCompactViewHolder(View itemView, Context context, View.OnClickListener mOnItemClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mContext = context;
        itemView.setOnClickListener(mOnItemClickListener);
    }

    public void bindData(UserSolrObj userSolrObj, Context context) {
        if (CommonUtil.isNotEmpty(userSolrObj.getThumbnailImageUrl())) {
            mImage.setCircularImage(true);
            mImage.bindImage(userSolrObj.getThumbnailImageUrl());
        }
        String pluralPosts = context.getResources().getQuantityString(R.plurals.numberOfPosts, userSolrObj.getSolrIgnoreNoOfMentorPosts());
        mPostCount.setText(userSolrObj.getSolrIgnoreNoOfMentorPosts());
        mPostCountTitle.setText(pluralPosts);

        String pluralAnswers = context.getResources().getQuantityString(R.plurals.numberOfAnswers, userSolrObj.getSolrIgnoreNoOfMentorAnswers());
        mCommentsCount.setText(userSolrObj.getSolrIgnoreNoOfMentorAnswers());
        mCommentsCountTitle.setText(pluralAnswers);

        String pluralFollowers = context.getResources().getQuantityString(R.plurals.numberOfFollowers, userSolrObj.getSolrIgnoreNoOfMentorFollowers());
        mFollowerCount.setText(userSolrObj.getSolrIgnoreNoOfMentorFollowers());
        mFollowerCountTitle.setText(pluralFollowers);

        if (userSolrObj.isSolrIgnoreIsMentorFollowed() || userSolrObj.isSolrIgnoreIsUserFollowed()) {
            mFollowButton.setTextColor(ContextCompat.getColor(context, R.color.white));
            mFollowButton.setText(context.getString(R.string.ID_GROWTH_BUDDIES_FOLLOWING));
            mFollowButton.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        } else {
            mFollowButton.setTextColor(ContextCompat.getColor(context, R.color.footer_icon_text));
            mFollowButton.setText(context.getString(R.string.ID_GROWTH_BUDDIES_FOLLOW));
            mFollowButton.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }

        mName.setText(userSolrObj.getNameOrTitle());

        if (CommonUtil.isNotEmpty(userSolrObj.getCityName())) {
            mLocation.setVisibility(View.VISIBLE);
            mLocation.setText(userSolrObj.getCityName());
        } else {
            mLocation.setVisibility(View.GONE);
        }

        if(CommonUtil.isNotEmpty(userSolrObj.getDescription())){
            mBio.setVisibility(View.VISIBLE);
            mBio.setText(userSolrObj.getDescription());
        }else {
            mBio.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.follow_button)
    public void onFollowClicked() {

    }
}
