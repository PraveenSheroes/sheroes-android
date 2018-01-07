package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 06/11/17.
 */

public class HeaderViewHolder extends BaseViewHolder<FeedDetail> {
    BaseHolderInterface viewInterface;
    @Bind(R.id.iv_header_circle_icon)
    CircleImageView ivLoginUserPic;
    @Bind(R.id.card_header_view)
    CardView cardHeaderView;
    @Bind(R.id.tv_header_name)
    TextView tvHeaderName;
    @Bind(R.id.user_name)
    TextView userName;

    @Inject
    Preference<LoginResponse> userPreference;
    private String mPhotoUrl;
    private String loggedInUser;
    private long userId;
    private FeedDetail dataItem;
    public HeaderViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            if (StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getPhotoUrl())) {
                mPhotoUrl = userPreference.get().getUserSummary().getPhotoUrl();
            }
            String userName = userPreference.get().getUserSummary().getFirstName() +AppConstants.SPACE + userPreference.get().getUserSummary().getLastName();
            if (StringUtil.isNotNullOrEmptyString(userName) ) {
                loggedInUser = userName;
            }

            userId = userPreference.get().getUserSummary().getUserId();
        }
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem=item;
        ivLoginUserPic.setCircularImage(true);
        ivLoginUserPic.bindImage(mPhotoUrl);
        if(StringUtil.isNotNullOrEmptyString(loggedInUser)) {
            String name = loggedInUser.substring(0, 1).toUpperCase() + loggedInUser.substring(1, loggedInUser.length());
            tvHeaderName.setText(context.getString(R.string.ID_HEADER_TEXT));
            userName.setText(name);
        }else {
            tvHeaderName.setText(context.getString(R.string.ID_HEADER_TEXT));
        }
    }

    @OnClick(R.id.user_name)
    public void userNameClickForProfile() {
        dataItem.setEntityOrParticipantId(userId);
        viewInterface.handleOnClick(dataItem, ivLoginUserPic);
    }

    @OnClick(R.id.iv_header_circle_icon)
    public void userImageClickForProfile() {
        dataItem.setEntityOrParticipantId(userId);
        viewInterface.handleOnClick(dataItem, ivLoginUserPic);
    }
    @OnClick(R.id.tv_header_name)
    public void textClickForCreatePost() {
        viewInterface.handleOnClick(dataItem, cardHeaderView);
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View v) {

    }
}
