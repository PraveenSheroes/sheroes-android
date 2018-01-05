package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
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

public class ProfileFollowedMentorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserSolrObj> followedMentors;
    private final Context mContext;
    private final View.OnClickListener mOnDeleteClickListener;
    private ProfilePresenterImpl profilePresenter;
    private boolean showMoreItem = false;
    public static final int INITIAL_ITEM_COUNT = 1;
    public int commentAdded = 0;

    //region Constructor
    public ProfileFollowedMentorAdapter(Context context, ProfilePresenterImpl profilePresenter, View.OnClickListener onDeleteClickListener) {
        mContext = context;
        this.profilePresenter = profilePresenter;
        mOnDeleteClickListener = onDeleteClickListener;
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
        UserSolrObj mentorDetails = followedMentors.get(position);
        commentListItemViewHolder.bindData(mentorDetails, position);
    }

    @Override
    public int getItemCount() {
        if (CommonUtil.isEmpty(followedMentors)) {
            return 0;
        }
        return followedMentors.size();
    }


    public void setData(List<UserSolrObj>  followedMentors) {
        this.followedMentors = followedMentors;
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

        public void bindData(final UserSolrObj mentorDetails, final int position) {

            if(null!= mentorDetails) {

                if(mentorDetails.getThumbnailImageUrl()!=null) {  //mentor image icon
                    mentorIcon.setCircularImage(true);
                    mentorIcon.setPlaceHolderId(R.drawable.default_img);
                    mentorIcon.setErrorPlaceHolderId(R.drawable.default_img);
                    mentorIcon.bindImage(mentorDetails.getThumbnailImageUrl());
                }

                if(mentorDetails.getNameOrTitle()!=null) {
                    mentorName.setText(mentorDetails.getNameOrTitle());
                }

                List<String> canHelpInArea = mentorDetails.getCanHelpIns();
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
                    String pluralComments = mContext.getResources().getQuantityString(R.plurals.numberOfFollowers, mentorDetails.getSolrIgnoreNoOfMentorFollowers());
                    follower.setText(String.valueOf(numericToThousand(mentorDetails.getSolrIgnoreNoOfMentorFollowers()) + AppConstants.SPACE + pluralComments));
                }
            }
        }
    }
    //endregion
}
