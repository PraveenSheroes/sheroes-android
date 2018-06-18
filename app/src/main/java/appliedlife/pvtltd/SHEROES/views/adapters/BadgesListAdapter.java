package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.profile.BadgeDetails;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ravi on 01/01/18.
 * Adapter for community listing on user or champion profile
 */

public class BadgesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BadgeDetails> badges;
    private final Context mContext;
    private final OnItemClicked onBadgeClickListener;

    //region Constructor
    public BadgesListAdapter(Context context, OnItemClicked onBadgeClickListener) {
        mContext = context;
           this.onBadgeClickListener = onBadgeClickListener;
    }

    //endregion
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        return new BadgesListAdapter.FollowedUserListItemViewHolder(mInflater.inflate(R.layout.badge_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BadgesListAdapter.FollowedUserListItemViewHolder commentListItemViewHolder = (BadgesListAdapter.FollowedUserListItemViewHolder) holder;
        BadgeDetails communityFeedSolrObj = badges.get(position);
        commentListItemViewHolder.bindData(communityFeedSolrObj);
    }

    @Override
    public int getItemCount() {
        if (CommonUtil.isEmpty(badges)) {
            return 0;
        }
        return badges.size();
    }


    public void setData(List<BadgeDetails> badgeDetailsList) {
        this.badges = badgeDetailsList;
        notifyDataSetChanged();
    }

    // region Comment List Item ViewHolder
    public class FollowedUserListItemViewHolder extends RecyclerView.ViewHolder {

        // region Butterknife Bindings

        @Bind(R.id.badge_title)
        TextView badgeTitle;

        @Bind(R.id.bade_won_date)
        TextView BadgeWonDate;

      /*  @Bind(R.id.iv_mentor_full_view_icon)
        CircleImageView mentorIcon;

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
        int authorProfileSize;*/

        // endregion

        FollowedUserListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final BadgeDetails badgeDetails) {

            if (null != badgeDetails) {

                // LinearLayout linearLayout = ButterKnife.findById(itemView, R.id.community_row_item);

                badgeTitle.setText(badgeDetails.getName());

                badgeTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBadgeClickListener.onItemClick(badgeDetails);
                    }
                });

                /*if (profileCommunity.isShowHeader() && getAdapterPosition() ==0) {
                    titleHeaderContainer.setVisibility(View.VISIBLE);
                } else{
                    titleHeaderContainer.setVisibility(View.GONE);
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
            }*/
                //}
            }
            //endregion
        }
    }

    public interface OnItemClicked {
        void onItemClick(BadgeDetails badgeDetails);
    }
}
