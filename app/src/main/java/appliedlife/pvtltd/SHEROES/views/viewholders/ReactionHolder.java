package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.comment.ReactionList;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 24-01-2017.
 */
public class ReactionHolder  extends BaseViewHolder<ReactionList> {
    private final String TAG = LogUtils.makeLogTag(ReactionHolder.class);
    @Bind(R.id.li_reaction)
    LinearLayout liReaction;
    @Bind(R.id.iv_reaction_profile_pic)
    CircleImageView ivReactionProfilePic;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_user_location)
    TextView tvUserLoaction;
    BaseHolderInterface viewInterface;
    private ReactionList dataItem;
    public ReactionHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ReactionList item, final Context context, int position) {
        this.dataItem = item;
        tvUserName.setText(item.getName());
        tvUserLoaction.setText(item.getName());
        String images = dataItem.getProfilePicUrl();
        ivReactionProfilePic.setCircularImage(true);
        ivReactionProfilePic.bindImage(images);
    }

    @Override
    public void viewRecycled() {

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