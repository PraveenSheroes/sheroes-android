package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.numericToThousand;

/**
 * Created by ravi on 01/01/18.
 * Adapter for community listing on user or champion profile
 */

public class ProfileCommunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CommunityFeedSolrObj> communities;
    private final Context mContext;
    private final OnItemClicked onCommunityClickListener;
    private boolean isOwnProfile;

    //region Constructor
    public ProfileCommunityAdapter(Context context, boolean isSelfProfile, OnItemClicked onClickListener) {
        mContext = context;
        isOwnProfile = isSelfProfile;
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
        CommunityFeedSolrObj communityFeedSolrObj = communities.get(position);
        commentListItemViewHolder.bindData(communityFeedSolrObj);
    }

    @Override
    public int getItemCount() {
        if (CommonUtil.isEmpty(communities)) {
            return 0;
        }
        return communities.size();
    }


    public void setData(List<CommunityFeedSolrObj> communities) {
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
        TextView headerTitle;

        @Bind(R.id.title_header_container)
        LinearLayout titleHeaderContainer;

        @Bind(R.id.community_subtitle)
        TextView community_subtitle;

        @Bind(R.id.subtitle_header_container)
        LinearLayout subTitleHeaderContainer;

        @BindDimen(R.dimen.dp_size_65)
        int authorProfileSize;

        // endregion

        FollowedUserListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final CommunityFeedSolrObj profileCommunity) {

            if (null != profileCommunity) {

                LinearLayout linearLayout = ButterKnife.findById(itemView, R.id.community_row_item);

                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCommunityClickListener.onItemClick(profileCommunity);
                    }
                });

                if (profileCommunity.isShowHeader() && getAdapterPosition() ==0) {
                    titleHeaderContainer.setVisibility(View.VISIBLE);
                } else{
                    titleHeaderContainer.setVisibility(View.GONE);
                }

                if (profileCommunity.isMutualCommunityFirstItem() && getAdapterPosition() ==0) {
                    subTitleHeaderContainer.setVisibility(View.VISIBLE);
                    community_subtitle.setText(String.format(Locale.US, "%d Mutual Communities", profileCommunity.getMutualCommunityCount()));
                } else if (profileCommunity.isOtherCommunityFirstItem()) {
                    String label = isOwnProfile ? "My Communities" : "Communities";
                    community_subtitle.setText(label);
                    subTitleHeaderContainer.setVisibility(View.VISIBLE);
                } else {
                    subTitleHeaderContainer.setVisibility(View.GONE);
                }

                if (profileCommunity.getThumbnailImageUrl() != null) {  //mentor image icon
                    mentorIcon.setCircularImage(true);
                    String authorThumborUrl = CommonUtil.getThumborUri(profileCommunity.getThumbnailImageUrl(), authorProfileSize, authorProfileSize);
                    mentorIcon.bindImage(authorThumborUrl);
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
                            expertFields.append(AppConstants.SPACE);
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
        void onItemClick(CommunityFeedSolrObj profileCommunity);
    }

}
