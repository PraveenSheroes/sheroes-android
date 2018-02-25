package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.ContactDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
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
    @Bind(R.id.ll_suggested_contact)
    LinearLayout llSuggestedContact;
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
        this.mContext = context;
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

    @OnClick({R.id.ll_suggested_contact, R.id.iv_suggested_contact_card_circle_icon, R.id.tv_suggested_contact_name})
    public void suggestedCardClick() {
        mPostDetailCallback.onSuggestedContactClicked(mUseSolarObj, llSuggestedContact);
    }

    @OnClick(R.id.btn_follow_friend)
    public void inviteFriendClicked() {
        if (mUseSolarObj.isSolrIgnoreIsUserFollowed()) {
            unFollowConfirmation();
        } else {
            btnFollowFriend.setEnabled(false);
            mPostDetailCallback.onSuggestedContactClicked(mUseSolarObj, btnFollowFriend);
            mUseSolarObj.setSolrIgnoreIsUserFollowed(true);
            setFollowUnFollow();
        }
    }
    protected final void unFollowConfirmation() {
          final  Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.unfollow_confirmation_dialog);

            CircleImageView circleImageView = (CircleImageView) dialog.findViewById(R.id.user_img_icon);
            if (StringUtil.isNotNullOrEmptyString(mUseSolarObj.getThumbnailImageUrl())) {
                circleImageView.setCircularImage(true);
                circleImageView.bindImage(mUseSolarObj.getThumbnailImageUrl());
            }

            TextView text = (TextView) dialog.findViewById(R.id.title);
            text.setText("Unfollow " + mUseSolarObj.getNameOrTitle());

            TextView dialogButton = (TextView) dialog.findViewById(R.id.cancel);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            TextView unFollowButton = (TextView) dialog.findViewById(R.id.unfollow);
            unFollowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnFollowFriend.setEnabled(false);
                    mPostDetailCallback.onSuggestedContactClicked(mUseSolarObj, btnFollowFriend);
                    mUseSolarObj.setSolrIgnoreIsUserFollowed(false);
                    setFollowUnFollow();
                    dialog.dismiss();
                }
            });

            dialog.show();
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
