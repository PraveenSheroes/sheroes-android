package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.PollTypeCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.post.PollOptionType;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RippleViewLinear;
import appliedlife.pvtltd.SHEROES.views.fragments.PostBottomSheetFragment;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PollTypesViewHolder extends BaseViewHolder<PollOptionType> {

    //region private variables
    private BaseHolderInterface viewInterface;
    private PollOptionType mPollOptionType;
    private Context mContext;
    private PostBottomSheetFragment mPostBottomSheetFragment;
    //endregion

    //region bind variables
    @Bind(R.id.poll_type_item)
    RippleViewLinear pollTypeContainer;

    @Bind(R.id.poll_type_icon)
    CircleImageView mPollTypeIcon;

    @Bind(R.id.poll_type_name)
    TextView mPollTypeName;

    @BindDimen(R.dimen.dp_size_50)
    int iconSize;
    //endregion

    //region constructor
    public PollTypesViewHolder(View itemView, BaseHolderInterface baseHolderInterface, PostBottomSheetFragment postBottomSheetFragment) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        this.mPostBottomSheetFragment = postBottomSheetFragment;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    //endregion

    //region adapter method
    @Override
    public void bindData(PollOptionType pollOptionType, Context context, int position) {
        mContext = context;
        mPollOptionType = pollOptionType;
        mPollTypeIcon.setImageResource(mPollOptionType.imgUrl);
        mPollTypeName.setText(mPollOptionType.title);
    }
    //endregion

    //region public method
    @Override
    public void viewRecycled() {
    }
    //endregion

    //region onclick method
    @Override
    public void onClick(View view) {
    }

    @OnClick(R.id.poll_type_item)
    public void pollItemClick() {
        pollTypeContainer.setOnRippleCompleteListener(new RippleViewLinear.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleViewLinear rippleView) {
                if (viewInterface instanceof PollTypeCallBack) {
                    mPostBottomSheetFragment.dismiss();
                    ((PollTypeCallBack) viewInterface).onPollTypeClicked(mPollOptionType.pollType);
                }
            }
        });

    }
    //endregion
}
