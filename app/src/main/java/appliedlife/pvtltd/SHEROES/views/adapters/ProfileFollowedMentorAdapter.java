package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileCommunity;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.numericToThousand;

/**
 * Created by ravi on 01/01/18.
 *
 */

public class ProfileFollowedMentorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserSolrObj> communities;
    private final Context mContext;
    private final OnItemClicked onItemClick;
    private ProfilePresenterImpl profilePresenter;
    private boolean showMoreItem = false;
    public static final int INITIAL_ITEM_COUNT = 1;
    public int commentAdded = 0;

    //region Constructor
    public ProfileFollowedMentorAdapter(Context context, ProfilePresenterImpl profilePresenter, OnItemClicked onItemClick) {
        mContext = context;
        this.profilePresenter = profilePresenter;
        this.onItemClick = onItemClick;
    }

    //endregion
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        return new ProfileFollowedMentorAdapter.FollowedUserListItemViewHolder(mInflater.inflate(R.layout.followed_mentor_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProfileFollowedMentorAdapter.FollowedUserListItemViewHolder commentListItemViewHolder = (ProfileFollowedMentorAdapter.FollowedUserListItemViewHolder) holder;
        UserSolrObj mentorDetails = communities.get(position);
        commentListItemViewHolder.bindData(mentorDetails, position);
    }

    @Override
    public int getItemCount() {
        if (CommonUtil.isEmpty(communities)) {
            return 0;
        }
        return communities.size();
    }

    public void setData(List<UserSolrObj>  communities) {
        this.communities = communities;
        notifyDataSetChanged();
    }

    // region Comment List Item ViewHolder
    public class FollowedUserListItemViewHolder extends RecyclerView.ViewHolder {

        // region Butterknife Bindings
        @Bind(R.id.iv_mentor_full_view_icon)
        CircleImageView mentorIcon;

        @Bind(R.id.user_name)
        TextView mentorName;

        @Bind(R.id.expert_at)
        TextView expertAt;

        @Bind(R.id.follower)
        TextView follower;

        // endregion

        public FollowedUserListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bindData(final UserSolrObj mentor, final int position) {

            if (null != mentor) {

                if(mentor.getSubType().equalsIgnoreCase(AppConstants.FEED_PROGRESS_BAR)) {

                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick.onItemClick(mentor);
                    }
                });

                if (mentor.getThumbnailImageUrl() != null) {  //mentor image icon
                    mentorIcon.setCircularImage(true);
                    mentorIcon.setPlaceHolderId(R.drawable.default_img);
                    mentorIcon.setErrorPlaceHolderId(R.drawable.default_img);
                    mentorIcon.bindImage(mentor.getThumbnailImageUrl());
                }

                if (mentor.getNameOrTitle() != null) {
                    mentorName.setText(mentor.getNameOrTitle());
                }

                List<String> canHelpInArea = mentor.getCanHelpIns();
                if (StringUtil.isNotEmptyCollection(canHelpInArea)) {
                    StringBuilder expertFields = new StringBuilder();
                    for (int i = 0; i < canHelpInArea.size(); i++) {
                        if (i > 0) {
                            expertFields.append(AppConstants.COMMA);
                        }
                        expertFields.append(canHelpInArea.get(i));
                    }
                    expertAt.setText(expertFields.toString());
                }

                if(follower !=null) {
                    String pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfFollowers, mentor.getSolrIgnoreNoOfMentorFollowers());
                    follower.setText(String.valueOf(numericToThousand(mentor.getSolrIgnoreNoOfMentorFollowers()) + AppConstants.SPACE + pluralComments));
                }
            }
            }
        }
    //endregion

    public interface OnItemClicked {
        void onItemClick(UserSolrObj mentor);
    }
}

