package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
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

public class HomeHeaderViewHolder extends BaseViewHolder<FeedDetail> {
    @Bind(R.id.card_header_view)
    CardView rootLayout;
    BaseHolderInterface viewInterface;
    @Bind(R.id.iv_header_circle_icon)
    CircleImageView ivLoginUserPic;
    @Bind(R.id.header_msg)
    TextView headerMsg;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.ripple)
    RippleView rippleView;
    private Context context;
    @Inject
    Preference<LoginResponse> userPreference;
    private String mPhotoUrl;
    private String loggedInUser;
    private long userId;
    private FeedDetail dataItem;
    private View popupViewToolTip;
    private PopupWindow popupWindowTooTip;
    private boolean isToolTip;

    public HomeHeaderViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        if (CommonUtil.forGivenCountOnly(AppConstants.HEADER_PROFILE_SESSION_PREF, AppConstants.HEADER_SESSION) == AppConstants.HEADER_SESSION) {
            if (CommonUtil.ensureFirstTime(AppConstants.HEADER_PROFILE_PREF)) {
                isToolTip = true;
            } else {
                isToolTip = false;
            }
        } else {
            isToolTip = false;
        }
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        this.context = context;
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            if (StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getPhotoUrl())) {
                mPhotoUrl = userPreference.get().getUserSummary().getPhotoUrl();
            }
            String userName = userPreference.get().getUserSummary().getFirstName() + AppConstants.SPACE + userPreference.get().getUserSummary().getLastName();
            if (StringUtil.isNotNullOrEmptyString(userName)) {
                loggedInUser = userName;
            }
            userId = userPreference.get().getUserSummary().getUserId();
        }
        ivLoginUserPic.setCircularImage(true);
        ivLoginUserPic.bindImage(mPhotoUrl);
        if (StringUtil.isNotNullOrEmptyString(loggedInUser)) {
            String name = loggedInUser.substring(0, 1).toUpperCase() + loggedInUser.substring(1, loggedInUser.length());
            userName.setText(name);
        }
        headerMsg.setText(context.getString(R.string.ID_HEADER_TEXT));
        if (isToolTip) {
            toolTipForHeaderFeed(context);
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
                if(viewInterface instanceof FeedItemCallback){
                    ((FeedItemCallback)viewInterface).onAskQuestionClicked();
                }else {
                    viewInterface.handleOnClick(dataItem, headerMsg);
                }
            }
        });

    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View v) {

    }

    private void toolTipForHeaderFeed(Context context) {
        try {
            LayoutInflater inflater = null;
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.tooltip_arrow_up_side, null);
            FrameLayout.LayoutParams lps = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            lps.setMargins(CommonUtil.convertDpToPixel(25, context), CommonUtil.convertDpToPixel(60, context), 0, 0);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(CommonUtil.convertDpToPixel(25, context), CommonUtil.convertDpToPixel(18, context));
            imageParams.gravity = Gravity.START;
            imageParams.setMargins(CommonUtil.convertDpToPixel(10, context), 0, 0, 0);
            final LinearLayout llToolTipBg = view.findViewById(R.id.ll_tool_tip_bg);
            RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(CommonUtil.convertDpToPixel(300, context), LinearLayout.LayoutParams.WRAP_CONTENT);
            llParams.addRule(RelativeLayout.BELOW, R.id.iv_arrow);
            llToolTipBg.setLayoutParams(llParams);
            final ImageView ivArrow = view.findViewById(R.id.iv_arrow);
            RelativeLayout.LayoutParams arrowParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            arrowParams.setMargins(CommonUtil.convertDpToPixel(10, context), 0, 0, 0);//CommonUtil.convertDpToPixel(10, HomeActivity.this)
            ivArrow.setLayoutParams(arrowParams);
            TextView text = view.findViewById(R.id.title);
            text.setText(R.string.tool_tip_feed_header_profile);
            TextView gotIt = view.findViewById(R.id.got_it);
            gotIt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isToolTip = false;
                    rootLayout.removeView(view);
                }
            });
            rootLayout.addView(view, lps);
        } catch (IllegalArgumentException e) {
            Crashlytics.getInstance().core.logException(e);
        }
    }
}