package appliedlife.pvtltd.SHEROES.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ravi on 05/09/18.
 */

public class UserProfileCompactViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private final String TAG = LogUtils.makeLogTag(UserProfileCompactViewHolder.class);
    // region ButterKnife Bindings
    @Bind(R.id.user_compact_card)
    CardView mUserCompactCard;

    @Bind(R.id.user_image)
    ImageView mUserImage;

    @Bind(R.id.bade_icon)
    ImageView mBadgeIcon;

    @Bind(R.id.follow_button)
    TextView mFollowButton;

    @Bind(R.id.name)
    TextView mName;

    @Bind(R.id.desc)
    TextView mDesc;

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
            String userThumborUrl = CommonUtil.getThumborUri(userSolrObj.getThumbnailImageUrl(), authorProfileSize, authorProfileSize);
            Glide.with(mUserImage.getContext())
                    .load(userThumborUrl)
                    .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(mUserImage.getContext())))
                    .into(mUserImage);
        } else {
            mUserImage.setVisibility(View.GONE);
        }

        if(StringUtil.isNotNullOrEmptyString(userSolrObj.getNameOrTitle())) {
            mName.setText(userSolrObj.getNameOrTitle());
        } else {
            mName.setText(R.string.not_available);
        }

        if (userSolrObj.isSolrIgnoreIsUserFollowed()) {
            mFollowButton.setEnabled(false);
            mFollowButton.setTextColor(ContextCompat.getColor(context, R.color.white));
            mFollowButton.setText(context.getString(R.string.following_user));
            mFollowButton.setAlpha(0.3f);
            mFollowButton.setBackgroundResource(R.drawable.rectangle_grey_winner_dialog);
        } else {
            mFollowButton.setEnabled(true);
            mFollowButton.setAlpha(1.0f);
            mFollowButton.setTextColor(ContextCompat.getColor(context, R.color.footer_icon_text));
            mFollowButton.setText(context.getString(R.string.follow_user));
            mFollowButton.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }

        mName.setText(userSolrObj.getNameOrTitle());
        try {
            String description = mContext.getResources().getString(R.string.user_card_desc, CommonUtil.camelCaseString(userSolrObj.getmSolarIgnoreCommunityName().toLowerCase()));
            mDesc.setText(description);
        } catch (Exception e) {
            LogUtils.error(TAG, e);
        }
        CommonUtil.showHideUserBadge(mContext, false, mBadgeIcon, mUserSolrObj.isSheBadgeActive(), mUserSolrObj.getProfileBadgeUrl());
    }

    //region onClick methods
    @OnClick(R.id.follow_button)
    public void onFollowClicked() {
        ((FeedItemCallback) viewInterface).onFollowClicked(mUserSolrObj);
    }

    @OnClick(R.id.user_compact_card)
    public void onUserCardClicked() {
        ((FeedItemCallback) viewInterface).onMentorProfileClicked(mUserSolrObj);
    }
    //endregion
}
