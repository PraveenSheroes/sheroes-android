package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SHEROES-TECH on 07-03-2017.
 */

public class UserHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(UserHolder.class);
    @Bind(R.id.iv_user)
    CircleImageView ivFeedUserCircleIcon;
    @Bind(R.id.tv_owner_title)
    TextView tvowOer_title;
    @Bind(R.id.tv_owner_city)
    TextView tv_owner_city;
    @Bind(R.id.tv_owner_add)
    TextView mTvownerclose;
    BaseHolderInterface viewInterface;
    private UserSolrObj userObj;
    Context mContext;

    public UserHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;

    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.userObj = (UserSolrObj) item;
        this.mContext = context;
        mTvownerclose.setOnClickListener(this);
        userObj.setItemPosition(position);
        allTextViewStringOperations(context);
    }

    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(userObj.getNameOrTitle())) {
            tvowOer_title.setText(userObj.getNameOrTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(userObj.getCityName())) {
            tv_owner_city.setText(userObj.getCityName());
        }
        String images = userObj.getImageUrl();

        ivFeedUserCircleIcon.setCircularImage(true);
        ivFeedUserCircleIcon.bindImage(images);
        if(userObj.isTrending())
        {
            mTvownerclose.setBackgroundResource(R.drawable.unselected_add_btn_shap);
            mTvownerclose.setTextColor(Color.WHITE);
            mTvownerclose.setText(R.string.ID_ADDED);
        }else
        {
            mTvownerclose.setBackgroundResource(R.drawable.select_purpose_btn_shap);
            mTvownerclose.setTextColor(Color.BLACK);
            mTvownerclose.setText(R.string.ID_ADD_HERE);
        }
    }



    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_owner_add:
                viewInterface.handleOnClick(userObj,view);
                break;
            default:
                LogUtils.error("", AppConstants.CASE_NOT_HANDLED + " " + "" + " " + id);
        }



    }

}