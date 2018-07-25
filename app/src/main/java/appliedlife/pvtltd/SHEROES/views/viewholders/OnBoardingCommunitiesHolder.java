package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 28/12/17.
 */

public class OnBoardingCommunitiesHolder extends BaseViewHolder<CommunityFeedSolrObj> {
    private final String TAG = LogUtils.makeLogTag(OnBoardingCommunitiesHolder.class);
    @Bind(R.id.tv_boarding_communities)
    TextView tvCommunityName;
    @Bind(R.id.tv_boarding_communities_join)
    TextView tvJoin;
    BaseHolderInterface viewInterface;
    private CommunityFeedSolrObj communityFeedObj;
    private Context mContext;
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Bind(R.id.iv_boarding_circle_icon)
    CircleImageView ivBoardingCircleIcon;

    @BindDimen(R.dimen.dp_size_66)
    int authorProfilePicSize;

    public OnBoardingCommunitiesHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        this.viewInterface = baseHolderInterface;

    }

    @Override
    public void bindData(CommunityFeedSolrObj item, final Context context, int position) {
        this.communityFeedObj = item;
        this.mContext = context;
        if (StringUtil.isNotNullOrEmptyString(communityFeedObj.getThumbnailImageUrl())) {
            ivBoardingCircleIcon.setCircularImage(true);
            ivBoardingCircleIcon.setPlaceHolderId(R.drawable.vector_community_selected_icon);
            ivBoardingCircleIcon.setErrorPlaceHolderId(R.drawable.vector_community_selected_icon);
            String authorThumborUrl = CommonUtil.getThumborUri(communityFeedObj.getThumbnailImageUrl(), authorProfilePicSize, authorProfilePicSize);
            ivBoardingCircleIcon.bindImage(authorThumborUrl);
        }

        if (StringUtil.isNotNullOrEmptyString(communityFeedObj.getNameOrTitle())) {
            tvCommunityName.setText(communityFeedObj.getNameOrTitle());
        }
        joinCommunity();
    }

    private void joinCommunity() {
        if (!communityFeedObj.isMember() && !communityFeedObj.isOwner() && !communityFeedObj.isRequestPending()) {
            tvJoin.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            tvJoin.setText(mContext.getString(R.string.ID_JOIN));
            tvJoin.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        } else if (communityFeedObj.isOwner() || communityFeedObj.isMember()) {
            tvJoin.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            tvJoin.setText(mContext.getString(R.string.ID_JOINED));
            tvJoin.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        } else {
            tvJoin.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            tvJoin.setText(mContext.getString(R.string.ID_JOIN));
            tvJoin.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

    }

    @OnClick(R.id.tv_boarding_communities_join)
    public void onJoinButtonClick() {
        if (tvJoin.getText().toString().equalsIgnoreCase(mContext.getString(R.string.ID_JOINED))) {
            viewInterface.handleOnClick(communityFeedObj, tvJoin);
            communityFeedObj.setMember(false);
            communityFeedObj.setRequestPending(false);
        } else {
            viewInterface.handleOnClick(communityFeedObj, tvJoin);
            communityFeedObj.setMember(true);
            communityFeedObj.setRequestPending(false);
        }
        joinCommunity();
    }
}

