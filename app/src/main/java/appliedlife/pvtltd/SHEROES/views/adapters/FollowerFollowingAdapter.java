package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.FollowerFollowingCallback;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedProgressBarHolder;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.changeNumberToNumericSuffix;

/**
 * Created by ravi on 01/01/18.
 */

public class FollowerFollowingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_COMMUNITY = 0;
    private static final int TYPE_SHOW_MORE = 1;
    private List<UserSolrObj> mChampionUsersList;
    private final Context mContext;
    private BaseHolderInterface mBaseHolderInterface;
    private Preference<LoginResponse> mUserPreference;

    //region Constructor
    public FollowerFollowingAdapter(Context context, BaseHolderInterface baseHolderInterface, Preference<LoginResponse> userPreference) {
        mContext = context;
        this.mBaseHolderInterface = baseHolderInterface;
        this.mUserPreference = userPreference;
    }

    //endregion
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_COMMUNITY) {
            return new FollowerFollowingAdapter.FollowedUserListItemViewHolder(mInflater.inflate(R.layout.adapter_champion_list_item, parent, false));
        } else {
            View view = mInflater.inflate(R.layout.feed_progress_bar_holder, parent, false);
            return new FeedProgressBarHolder(view, mBaseHolderInterface);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (CommonUtil.isEmpty(mChampionUsersList)) return;
        if (holder.getItemViewType() == TYPE_COMMUNITY) {
            FollowerFollowingAdapter.FollowedUserListItemViewHolder commentListItemViewHolder = (FollowerFollowingAdapter.FollowedUserListItemViewHolder) holder;
            UserSolrObj mentorDetails = mChampionUsersList.get(position);
            commentListItemViewHolder.bindData(mentorDetails, position);
        } else {
            FeedProgressBarHolder loaderViewHolder = ((FeedProgressBarHolder) holder);
            loaderViewHolder.bindData(mChampionUsersList.get(position), mContext, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return !CommonUtil.isEmpty(mChampionUsersList) && mChampionUsersList.get(position).getSubType() != null && mChampionUsersList.get(position).getSubType().equals(AppConstants.FEED_PROGRESS_BAR) ? TYPE_SHOW_MORE : TYPE_COMMUNITY;
    }

    @Override
    public int getItemCount() {
        return CommonUtil.isEmpty(mChampionUsersList) ? 0 : mChampionUsersList.size();
    }

    public void setData(List<UserSolrObj> communities) {
        this.mChampionUsersList = communities;
        notifyDataSetChanged();
    }

    public void setData(int position, UserSolrObj feedDetail) {
        mChampionUsersList.set(position, feedDetail);
        notifyItemChanged(position);
    }

    public void updateData(UserSolrObj userSolrObj, int position) {
        if(mChampionUsersList.size()> position) {
            mChampionUsersList.set(position, userSolrObj);
        }
    }

    public void findPositionAndUpdateItem(UserSolrObj updateUserObj, long id) {
        if (CommonUtil.isEmpty(mChampionUsersList)) {
            return;
        }

        for (int i = 0; i < mChampionUsersList.size(); ++i) {
            FeedDetail feedDetail = mChampionUsersList.get(i);
            if (feedDetail != null && feedDetail.getIdOfEntityOrParticipant() == id) {
                setData(i, updateUserObj);
                break;
            }
        }
    }

    // region Followers or Following List Item ViewHolder
    public class FollowedUserListItemViewHolder extends RecyclerView.ViewHolder {

        // region Butterknife Bindings
        @Bind(R.id.iv_profile_full_view_icon)
        CircleImageView mentorIcon;

        @Bind(R.id.user_name)
        TextView mentorName;

        @Bind(R.id.expert_at)
        TextView expertAt;

        @Bind(R.id.iv_champion_verified)
        ImageView verifiedChampionsIcon;

        @Bind(R.id.follower)
        TextView follower;

        @BindDimen(R.dimen.dp_size_65)
        int authorProfileSize;

        @Bind(R.id.follow_following_btn)
        Button followFollowingBtn;

        private UserSolrObj championObj;
        private long loggedInUserId = -1;

        // endregion

        public FollowedUserListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary()) {
                loggedInUserId = mUserPreference.get().getUserSummary().getUserId();
            }
        }

        public void bindData(final UserSolrObj championObj, final int position) {
            if (null != championObj) {
                this.championObj = championObj;
                championObj.setItemPosition(position);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((FollowerFollowingCallback) mBaseHolderInterface).onItemClick(championObj);
                    }
                });
                if (championObj.getEntityOrParticipantTypeId() == AppConstants.CHAMPION_TYPE_ID) {
                    verifiedChampionsIcon.setVisibility(View.VISIBLE);
                } else {
                    verifiedChampionsIcon.setVisibility(View.GONE);
                }
                if (championObj.getThumbnailImageUrl() != null) {  //mentor image icon
                    mentorIcon.setCircularImage(true);
                    String authorThumborUrl = CommonUtil.getThumborUri(championObj.getThumbnailImageUrl(), authorProfileSize, authorProfileSize);
                    mentorIcon.bindImage(authorThumborUrl);
                }
                if (championObj.getNameOrTitle() != null) {
                    mentorName.setText(championObj.getNameOrTitle());
                }
                if (championObj.getEntityOrParticipantTypeId() == AppConstants.CHAMPION_TYPE_ID) {
                    List<String> canHelpInArea = championObj.getCanHelpIns();
                    if (StringUtil.isNotEmptyCollection(canHelpInArea)) {
                        StringBuilder expertFields = new StringBuilder();
                        for (int i = 0; i < canHelpInArea.size(); i++) {
                            if (i > 0) {
                                expertFields.append(AppConstants.COMMA);
                            }
                            expertFields.append(canHelpInArea.get(i));
                        }
                        expertAt.setVisibility(View.VISIBLE);
                        expertAt.setText(expertFields.toString());
                    } else {
                        expertAt.setVisibility(View.GONE);
                    }
                } else {
                    if (CommonUtil.isNotEmpty(championObj.getCityName())) {
                        expertAt.setText(championObj.getCityName());
                        expertAt.setVisibility(View.VISIBLE);
                    } else {
                        expertAt.setVisibility(View.GONE);
                    }
                }
                if (follower != null) {
                    String pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfFollowers, championObj.getFollowerCount());
                    follower.setText(String.valueOf(changeNumberToNumericSuffix(championObj.getFollowerCount()) + AppConstants.SPACE + pluralComments));
                }
                if (loggedInUserId != championObj.getIdOfEntityOrParticipant()) {
                    followFollowingBtn.setVisibility(View.VISIBLE);

                    if (championObj.isSolrIgnoreIsMentorFollowed() || championObj.isSolrIgnoreIsUserFollowed()) {
                        showFollowing();
                    } else {
                        showFollow();
                    }
                } else {
                    followFollowingBtn.setVisibility(View.GONE);
                }
            }
        }

        //Follow button
        private void showFollow() {
            followFollowingBtn.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            followFollowingBtn.setText(mContext.getString(R.string.follow_user));
            followFollowingBtn.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }

        //Following button
        private void showFollowing() {
            followFollowingBtn.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            followFollowingBtn.setText(mContext.getString(R.string.following_user));
            followFollowingBtn.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        }

        @OnClick(R.id.follow_following_btn)
        public void onFollowFollowingClick() {
            String followFollowingBtnText = followFollowingBtn.getText().toString();
            ((FollowerFollowingCallback) mBaseHolderInterface).onFollowFollowingClick(championObj, followFollowingBtnText);

            if (!championObj.isSolrIgnoreIsMentorFollowed() || !championObj.isSolrIgnoreIsUserFollowed()) {
                championObj.setSolrIgnoreIsMentorFollowed(true);
                championObj.setSolrIgnoreIsUserFollowed(true);
                showFollowing();
            }
        }
    }
    //endregion
}

