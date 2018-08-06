package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.PollTypeCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.post.PollType;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RippleViewLinear;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PollTypesViewHolder extends BaseViewHolder<PollType> {

    //region private variables
    private BaseHolderInterface viewInterface;
    private PollType mPollType;
    private Context mContext;
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
    public PollTypesViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    //endregion

    //region adapter method
    @Override
    public void bindData(PollType pollType, Context context, int position) {
        mContext = context;
        mPollType = pollType;

        if (CommonUtil.isNotEmpty(mPollType.imgUrl)) {
            String imageKitUrl = CommonUtil.getThumborUri(mPollType.imgUrl, iconSize, iconSize);
            if (CommonUtil.isNotEmpty(imageKitUrl)) {
                Glide.with(mContext)
                        .load(imageKitUrl)
                        .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(mContext)))
                        .into(mPollTypeIcon);
            }
        } else {
            mPollTypeIcon.setImageResource(R.drawable.ic_image_holder);
        }

        mPollTypeName.setText(mPollType.title);
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
                    ((PollTypeCallBack) viewInterface).onPollTypeClicked(mPollType);
                }
            }
        });

    }
    //endregion
}
