package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.FollowerFollowingCallback;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedProgressBarHolder;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.changeNumberToNumericSuffix;

/**
 * Created by ravi on 01/01/18.
 */

public class FollowerFollowingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_COMMUNITY = 0;
    private static final int TYPE_SHOW_MORE = 1;
    private List<UserSolrObj> communities;
    private final Context mContext;
    private BaseHolderInterface baseHolderInterface;

    //region Constructor
    public FollowerFollowingAdapter(Context context, BaseHolderInterface baseHolderInterface) {
        mContext = context;
        this.baseHolderInterface = baseHolderInterface;
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

        if (CommonUtil.isEmpty(communities)) return;

        if (holder.getItemViewType() == TYPE_COMMUNITY) {
            FollowerFollowingAdapter.FollowedUserListItemViewHolder commentListItemViewHolder = (FollowerFollowingAdapter.FollowedUserListItemViewHolder) holder;
            UserSolrObj mentorDetails = communities.get(position);
            commentListItemViewHolder.bindData(mentorDetails, position);
        } else {
            FeedProgressBarHolder loaderViewHolder = ((FeedProgressBarHolder) holder);
            loaderViewHolder.bindData(communities.get(position), mContext, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return !CommonUtil.isEmpty(communities) && communities.get(position).getSubType() != null && communities.get(position).getSubType().equals(AppConstants.FEED_PROGRESS_BAR) ? TYPE_SHOW_MORE : TYPE_COMMUNITY;
    }

    @Override
    public int getItemCount() {
        return CommonUtil.isEmpty(communities) ? 0 : communities.size();
    }

    public void setData(List<UserSolrObj> communities) {
        this.communities = communities;
        notifyDataSetChanged();
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

        // endregion

        public FollowedUserListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bindData(final UserSolrObj mentor, final int position) {

            if (null != mentor) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((FollowerFollowingCallback)baseHolderInterface).onItemClick(mentor);
                    }
                });

                if(mentor.getEntityOrParticipantTypeId() ==  AppConstants.CHAMPION_TYPE_ID) {
                    verifiedChampionsIcon.setVisibility(View.VISIBLE);
                } else{
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

                if(mentor.getEntityOrParticipantTypeId() ==  AppConstants.CHAMPION_TYPE_ID ) {
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
                } else{
                    if (CommonUtil.isNotEmpty(mentor.getCityName())) {
                        expertAt.setText(mentor.getCityName());
                    } else {
                        expertAt.setVisibility(View.GONE);
                    }
                }

                if (follower != null) {
                    String pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfFollowers, mentor.getFollowerCount());
                    follower.setText(String.valueOf(changeNumberToNumericSuffix(mentor.getFollowerCount()) + AppConstants.SPACE + pluralComments));
                }
            }
        }
    }
    //endregion
}

