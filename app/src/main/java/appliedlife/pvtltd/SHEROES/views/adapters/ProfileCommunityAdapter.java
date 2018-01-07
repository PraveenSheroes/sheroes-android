package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
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
 */

public class ProfileCommunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProfileCommunity> communities;
    private final Context mContext;
    private final OnItemClicked onCommunityClickListener;
    private ProfilePresenterImpl profilePresenter;
    private boolean showMoreItem = false;
    public static final int INITIAL_ITEM_COUNT = 1;
    public int commentAdded = 0;

    //region Constructor
    public ProfileCommunityAdapter(Context context, ProfilePresenterImpl profilePresenter, OnItemClicked onClickListener) {
        mContext = context;
        this.profilePresenter = profilePresenter;
        onCommunityClickListener = onClickListener;
    }

    //endregion
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        return new ProfileCommunityAdapter.FollowedUserListItemViewHolder(mInflater.inflate(R.layout.profile_communities_listing, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProfileCommunityAdapter.FollowedUserListItemViewHolder commentListItemViewHolder = (ProfileCommunityAdapter.FollowedUserListItemViewHolder) holder;
        ProfileCommunity mentorDetails = communities.get(position);
        commentListItemViewHolder.bindData(mentorDetails, position);
    }

    @Override
    public int getItemCount() {
        if (CommonUtil.isEmpty(communities)) {
            return 0;
        }
        return communities.size();
    }


    public void setData(List<ProfileCommunity> communities) {
        this.communities = communities;
        notifyDataSetChanged();
    }

    // region Comment List Item ViewHolder
    public class FollowedUserListItemViewHolder extends RecyclerView.ViewHolder {

        // region Butterknife Bindings
        @Bind(R.id.iv_mentor_full_view_icon)
        CircleImageView mentorIcon;

        @Bind(R.id.community_name)
        TextView communityName;

        @Bind(R.id.expert_at)
        TextView communityType;

        @Bind(R.id.member)
        TextView member;

        @Bind(R.id.header_title)
        TextView headderTitle;

        @Bind(R.id.community_subtitle)
        TextView community_subtitle;

        // endregion

        public FollowedUserListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final ProfileCommunity profileCommunity, final int position) {

            if (null != profileCommunity) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCommunityClickListener.onItemClick(profileCommunity);
                    }
                });

                if (headderTitle.getVisibility() != View.VISIBLE && (profileCommunity.isShowHeader() || position == 0)) {
                    headderTitle.setVisibility(View.VISIBLE);
                } else{
                    headderTitle.setVisibility(View.GONE);
                }

                if (profileCommunity.isMutualCommunityFirstItem()) {
                    community_subtitle.setVisibility(View.VISIBLE);
                    community_subtitle.setText(profileCommunity.getMutualCommunityCount() + " Mutual Communities");
                } else if (profileCommunity.isOtherCommunityFirstItem()) {
                    community_subtitle.setVisibility(View.VISIBLE);
                    String label = profileCommunity.getMutualCommunityCount() == 0 ? "Communities" : "Other Communities";
                    if(label.equalsIgnoreCase("Other Communities")) {
                        headderTitle.setVisibility(View.GONE);
                    }
                    community_subtitle.setText(label);
                } else {
                    community_subtitle.setVisibility(View.GONE);
                }

                if (profileCommunity.getThumbnailImageUrl() != null) {  //mentor image icon
                    mentorIcon.setCircularImage(true);
                    mentorIcon.setPlaceHolderId(R.drawable.default_img);
                    mentorIcon.setErrorPlaceHolderId(R.drawable.default_img);
                    mentorIcon.bindImage(profileCommunity.getThumbnailImageUrl());
                }

                if (profileCommunity.getNameOrTitle() != null) {
                    communityName.setText(profileCommunity.getNameOrTitle());
                }

                List<String> canHelpInArea = profileCommunity.getTags();
                if (StringUtil.isNotEmptyCollection(canHelpInArea)) {
                    StringBuilder expertFields = new StringBuilder();
                    for (int i = 0; i < canHelpInArea.size(); i++) {
                        if (i > 0) {
                            expertFields.append(AppConstants.COMMA);
                        }
                        expertFields.append(canHelpInArea.get(i));
                    }
                    communityType.setText(expertFields.toString());
                }

                String pluralMember = mContext.getResources().getQuantityString(R.plurals.numberOfMembers, profileCommunity.getNoOfMembers());
                member.setText(String.valueOf(numericToThousand(profileCommunity.getNoOfMembers()) + AppConstants.SPACE + pluralMember));
            }
        }
    }
    //endregion

    public interface OnItemClicked {
        void onItemClick(ProfileCommunity profileCommunity);
    }

}
