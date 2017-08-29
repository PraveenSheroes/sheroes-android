package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.publicprofile.MentorDetailItem;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 03-08-2017.
 */

public class GrowthBuddiesHolder extends BaseViewHolder<MentorDetailItem> {
    private final String TAG = LogUtils.makeLogTag(EventSponsorHolder.class);
    @Inject
    DateUtil mDateUtil;
    BaseHolderInterface viewInterface;
    private MentorDetailItem dataItem;
    Context mContext;
    @Bind(R.id.iv_growth_buddies_icon)
    RoundedImageView ivGrowthBuddiesIcon;
    @Bind(R.id.tv_growth_buddies_name)
    TextView tvGrowthBuddiesName;
    @Bind(R.id.tv_growth_buddies_profession)
    TextView tvGrowthBuddiesProfession;
    @Bind(R.id.li_growth_buddies_layout)
    LinearLayout liGrowthBuddieslayout;
    @Bind(R.id.tv_growth_buddies_follow)
    TextView tvGrowthBuddiesFollow;
    @Inject
    Preference<LoginResponse> userPreference;

    public GrowthBuddiesHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(MentorDetailItem item, Context context, int position) {
        this.dataItem = item;
        mContext = context;
        dataItem.setItemPosition(position);
        tvGrowthBuddiesFollow.setEnabled(true);
        if (StringUtil.isNotNullOrEmptyString(dataItem.getMentorImageUrl())) {
            Glide.with(mContext)
                    .load(dataItem.getMentorImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivGrowthBuddiesIcon);
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getMentorName())) {
            tvGrowthBuddiesName.setText(dataItem.getMentorName());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getExpertiesIn())) {
            tvGrowthBuddiesProfession.setText(dataItem.getExpertiesIn());
        }
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            if (userPreference.get().getUserSummary().getUserId() == dataItem.getEntityOrParticipantId()) {
                tvGrowthBuddiesFollow.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
                tvGrowthBuddiesFollow.setText(mContext.getString(R.string.ID_EDIT_PROFILE));
                tvGrowthBuddiesFollow.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
            } else {
                isFollowUnfollow();
            }
        }

    }

    @OnClick(R.id.li_growth_buddies_layout)
    public void onGrowthBuddiesItemClick() {
        viewInterface.handleOnClick(dataItem, liGrowthBuddieslayout);
    }

    @OnClick(R.id.tv_growth_buddies_name)
    public void onGrowthBuddiesNameItemClick() {
        viewInterface.handleOnClick(dataItem, liGrowthBuddieslayout);
    }

    @OnClick(R.id.tv_growth_buddies_follow)
    public void onGrowthBuddiesFollowUnfollow() {
        if (tvGrowthBuddiesFollow.getText().toString().equalsIgnoreCase(mContext.getString(R.string.ID_EDIT_PROFILE))) {
            String profile = userPreference.get().getUserSummary().getPhotoUrl();
            Intent intent = new Intent(mContext, ProfileActicity.class);
            intent.putExtra(AppConstants.EXTRA_IMAGE, profile);
            mContext.startActivity(intent);
        } else {
            tvGrowthBuddiesFollow.setEnabled(false);
            viewInterface.handleOnClick(dataItem, tvGrowthBuddiesFollow);
            if (!dataItem.isFollowed()) {
                dataItem.setFollowed(true);
            } else {
                dataItem.setFollowed(false);
            }
            isFollowUnfollow();
        }
    }

    private void isFollowUnfollow() {
        if (dataItem.isFollowed()) {
            tvGrowthBuddiesFollow.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            tvGrowthBuddiesFollow.setText(mContext.getString(R.string.ID_GROWTH_BUDDIES_FOLLOWING));
            tvGrowthBuddiesFollow.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        } else {
            tvGrowthBuddiesFollow.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            tvGrowthBuddiesFollow.setText(mContext.getString(R.string.ID_GROWTH_BUDDIES_UNFOLLOW));
            tvGrowthBuddiesFollow.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }
    }

    @Override
    public void onClick(View view) {


    }

    @Override
    public void viewRecycled() {

    }
}



