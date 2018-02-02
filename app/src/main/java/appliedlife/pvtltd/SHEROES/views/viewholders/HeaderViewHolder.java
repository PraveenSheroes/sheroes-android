package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RippleView;
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
    @Bind(R.id.header_msg)
    TextView headerMsg;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.ripple)
    RippleView rippleView;
    private  Context context;
    @Inject
    Preference<LoginResponse> userPreference;
    private String mPhotoUrl;
    private String loggedInUser;
    private long userId;
    private FeedDetail dataItem;
    private View popupViewToolTip;
    private PopupWindow popupWindowTooTip;

    public HeaderViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem=item;
        this.context=context;
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
        ivLoginUserPic.setCircularImage(true);
        ivLoginUserPic.bindImage(mPhotoUrl);
        if(StringUtil.isNotNullOrEmptyString(loggedInUser)) {
            String name = loggedInUser.substring(0, 1).toUpperCase() + loggedInUser.substring(1, loggedInUser.length());
            userName.setText(name);
        }
            headerMsg.setText(context.getString(R.string.ID_HEADER_TEXT));
        if (CommonUtil.fromNthTimeOnly(AppConstants.HEADER_PROFILE_SESSION_PREF, 3)) {
            if (CommonUtil.ensureFirstTime(AppConstants.HEADER_PROFILE_PREF)) {
                toolTipForHeaderFeed();
            }
        }
    }

    @OnClick(R.id.user_name)
    public void userNameClickForProfile() {
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                dataItem.setEntityOrParticipantId(userId);
                viewInterface.handleOnClick(dataItem, ivLoginUserPic);
            }
        });
    }
    @OnClick(R.id.iv_header_circle_icon)
    public void userImageClickForProfile() {
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                dataItem.setEntityOrParticipantId(userId);
                viewInterface.handleOnClick(dataItem, ivLoginUserPic);
            }
        });

    }
    @OnClick(R.id.header_msg)
    public void textClickForCreatePost() {
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                viewInterface.handleOnClick(dataItem, headerMsg);
            }
        });

    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View v) {

    }
    private void toolTipForHeaderFeed() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    popupViewToolTip = layoutInflater.inflate(R.layout.tooltip_arrow_nav, null);
                    popupWindowTooTip = new PopupWindow(popupViewToolTip, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindowTooTip.setOutsideTouchable(true);
                    popupWindowTooTip.showAsDropDown(ivLoginUserPic, 50, -20);
                    final TextView tvGotIt = (TextView) popupViewToolTip.findViewById(R.id.got_it);
                    final TextView tvTitle = (TextView) popupViewToolTip.findViewById(R.id.title);
                    tvTitle.setText(context.getString(R.string.ID_TOOL_TIP_FEED_HEADER_PROFILE));
                    tvGotIt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindowTooTip.dismiss();
                        }
                    });
                } catch (WindowManager.BadTokenException e) {
                    Crashlytics.getInstance().core.logException(e);
                }
            }
        }, 1000);


    }
}
