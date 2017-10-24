package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 24-01-2017.
 */
public class ReactionHolder extends BaseViewHolder<CommentReactionDoc> {
    private final String TAG = LogUtils.makeLogTag(ReactionHolder.class);
    @Bind(R.id.li_reaction)
    LinearLayout liReaction;
    @Bind(R.id.iv_reaction_profile_pic_verified)
    ImageView ivReactionProfilePicVerified;
    @Bind(R.id.iv_reaction_profile_pic)
    CircleImageView ivReactionProfilePic;
    @Bind(R.id.tv_user_name_reaction)
    TextView tvUserName;
    @Bind(R.id.tv_user_location)
    TextView tvUserLoaction;
    @Bind(R.id.iv_user_reaction_emoji)
    ImageView ivUserReactionEmoji;

    BaseHolderInterface viewInterface;
    private CommentReactionDoc dataItem;

    public ReactionHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(CommentReactionDoc item, final Context context, int position) {
        this.dataItem = item;
        if (StringUtil.isNotNullOrEmptyString(item.getParticipantName())) {
            tvUserName.setText(item.getParticipantName());
        }
        if (StringUtil.isNotNullOrEmptyString(item.getCity())) {
            tvUserLoaction.setText(item.getCity());
        }
        if (StringUtil.isNotNullOrEmptyString(item.getParticipantImageUrl())) {
            String images = dataItem.getParticipantImageUrl();
            ivReactionProfilePic.setCircularImage(true);
            ivReactionProfilePic.bindImage(images);

        }
        if (dataItem.isVerifiedMentor()) {
            ivReactionProfilePicVerified.setVisibility(View.VISIBLE);
        } else {
            ivReactionProfilePicVerified.setVisibility(View.GONE);
        }
        switch (item.getLikeValue()) {
            case AppConstants.HEART_REACTION_CONSTANT:
                ivUserReactionEmoji.setImageResource(R.drawable.ic_heart_active);
                break;
            case AppConstants.EMOJI_FIRST_REACTION_CONSTANT:
                break;
            case AppConstants.EMOJI_SECOND_REACTION_CONSTANT:
                break;
            case AppConstants.EMOJI_THIRD_REACTION_CONSTANT:
                break;
            case AppConstants.EMOJI_FOURTH_REACTION_CONSTANT:
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + "  " + TAG + " " + item.getLikeValue());
        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.li_user_reaction_with_name)
    public void onReactionWithNameClick() {
        if (dataItem.isVerifiedMentor()) {
            viewInterface.championProfile(dataItem, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.li_reaction:
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}