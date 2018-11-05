package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
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
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
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
    private BaseHolderInterface baseHolderInterface;
    private long mLoggedInUserId = -1;
    private Preference<LoginResponse> mUserPreference;

    //region Constructor
    public FollowerFollowingAdapter(Context context, BaseHolderInterface baseHolderInterface, Preference<LoginResponse> mUserPreference) {
        mContext = context;
        this.baseHolderInterface = baseHolderInterface;
        this.mUserPreference = mUserPreference;
    }

    //endregion
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_COMMUNITY) {
            return new FollowerFollowingAdapter.FollowedUserListItemViewHolder(mInflater.inflate(R.layout.followed_mentor_list_item, parent, false));
        } else {
            View view = mInflater.inflate(R.layout.feed_progress_bar_holder, parent, false);
            return new FeedProgressBarHolder(view, baseHolderInterface);
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

    // region Followers or Following List Item ViewHolder
    public class FollowedUserListItemViewHolder extends RecyclerView.ViewHolder {

        // region Butterknife Bindings
        @Bind(R.id.iv_mentor_full_view_icon)
        CircleImageView mentorIcon;

        @Bind(R.id.user_name)
        TextView mentorName;

        @Bind(R.id.expert_at)
        TextView expertAt;

        @Bind(R.id.iv_mentor_verified)
        ImageView verifiedChampionsIcon;

        @Bind(R.id.follower)
        TextView follower;

        @BindDimen(R.dimen.dp_size_65)
        int authorProfileSize;

        @Bind(R.id.follow_following_btn)
        Button followFollowingBtn;
        private UserSolrObj mMentor;
        private int position = -1;

        // endregion

        public FollowedUserListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bindData(final UserSolrObj mentor, final int position) {

            if (null != mentor) {
                this.mMentor = mentor;
                this.position = position;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((FollowerFollowingCallback) baseHolderInterface).onItemClick(mentor);
                    }
                });

                if (mentor.getEntityOrParticipantTypeId() == AppConstants.CHAMPION_TYPE_ID) {
                    verifiedChampionsIcon.setVisibility(View.VISIBLE);
                } else {
                    verifiedChampionsIcon.setVisibility(View.GONE);
                }

                if (mentor.getThumbnailImageUrl() != null) {  //mentor image icon
                    mentorIcon.setCircularImage(true);
                    String authorThumborUrl = CommonUtil.getThumborUri(mentor.getThumbnailImageUrl(), authorProfileSize, authorProfileSize);
                    mentorIcon.bindImage(authorThumborUrl);
                }

                if (mentor.getNameOrTitle() != null) {
                    mentorName.setText(mentor.getNameOrTitle());
                }

                if (mentor.getEntityOrParticipantTypeId() == AppConstants.CHAMPION_TYPE_ID) {
                    List<String> canHelpInArea = mentor.getCanHelpIns();
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
                    if (CommonUtil.isNotEmpty(mentor.getCityName())) {
                        expertAt.setText(mentor.getCityName());
                        expertAt.setVisibility(View.VISIBLE);
                    } else {
                        expertAt.setVisibility(View.GONE);
                    }
                }

                if (follower != null) {
                    String pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfFollowers, mentor.getSolrIgnoreNoOfMentorFollowers());
                    follower.setText(String.valueOf(changeNumberToNumericSuffix(mentor.getSolrIgnoreNoOfMentorFollowers()) + AppConstants.SPACE + pluralComments));
                }
                if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary()) {
                    mLoggedInUserId = mUserPreference.get().getUserSummary().getUserId();
                    if (mLoggedInUserId != mentor.getIdOfEntityOrParticipant()) {
                        if (mentor.isSolrIgnoreIsMentorFollowed()) {
                            showFollowing();
                        } else {
                            showFollow();
                        }
                    } else {
                        followFollowingBtn.setVisibility(View.GONE);
                    }
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
            if (position != -1 && followFollowingBtnText != null) {
                ((FollowerFollowingCallback) baseHolderInterface).onFollowFollowingClick(mMentor, position, followFollowingBtnText);
            }
        }
    }
    //endregion
}

