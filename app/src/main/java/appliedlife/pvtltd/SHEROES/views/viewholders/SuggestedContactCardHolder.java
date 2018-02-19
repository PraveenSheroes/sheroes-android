package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.ContactDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 19/02/18.
 */

public class SuggestedContactCardHolder extends BaseViewHolder<UserSolrObj> {
    @Bind(R.id.tv_suggested_contact_name)
    TextView tvContactName;
    @Bind(R.id.btn_follow_friend)
    Button btnFollowFriend;
    @Bind(R.id.iv_suggested_contact_card_circle_icon)
    CircleImageView ivSuggestedContactCardCircleIcon;
    private ContactDetailCallBack mPostDetailCallback;
    private UserSolrObj mUseSolarObj;
    private Context mContext;

    public SuggestedContactCardHolder(View itemView, ContactDetailCallBack postDetailCallBack) {
        super(itemView);
        this.mPostDetailCallback = postDetailCallBack;
        ButterKnife.bind(this, itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);

    }

    @Override
    public void bindData(UserSolrObj userSolrObj, Context context, int position) {
        this.mContext=context;
        this.mUseSolarObj = userSolrObj;
        btnFollowFriend.setEnabled(true);
        mUseSolarObj.setItemPosition(position);
        if (StringUtil.isNotNullOrEmptyString(userSolrObj.getThumbnailImageUrl())) {
            ivSuggestedContactCardCircleIcon.setCircularImage(true);
            ivSuggestedContactCardCircleIcon.bindImage(userSolrObj.getThumbnailImageUrl());
        }
        if (StringUtil.isNotNullOrEmptyString(mUseSolarObj.getNameOrTitle())) {
            String str = mUseSolarObj.getNameOrTitle();
            tvContactName.setText(str);
        }
        setFollowUnFollow();
    }

    @OnClick(R.id.btn_follow_friend)
    public void inviteFriendClicked() {
        btnFollowFriend.setEnabled(false);
        mPostDetailCallback.onSuggestedContactClicked(mUseSolarObj, btnFollowFriend);
        if (mUseSolarObj.isSolrIgnoreIsUserFollowed()) {
            mUseSolarObj.setSolrIgnoreIsUserFollowed(false);
        } else {
            mUseSolarObj.setSolrIgnoreIsUserFollowed(true);
        }
        setFollowUnFollow();

    }
    private void setFollowUnFollow() {
        if (mUseSolarObj.isSolrIgnoreIsUserFollowed()) {
            btnFollowFriend.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            btnFollowFriend.setText(mContext.getString(R.string.following_user));
            btnFollowFriend.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        } else {
            btnFollowFriend.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            btnFollowFriend.setText(mContext.getString(R.string.follow_user));
            btnFollowFriend.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }
    }
    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {

    }
}
