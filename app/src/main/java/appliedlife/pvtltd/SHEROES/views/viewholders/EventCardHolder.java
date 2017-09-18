package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.f2prateek.rx.preferences.Preference;

import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 18/09/17.
 */

public class EventCardHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(EventCardHolder.class);
    private static final String LEFT_HTML_VEIW_TAG_FOR_COLOR = "<font color='#f2403c'>";
    private static final String RIGHT_HTML_VIEW_TAG_FOR_COLOR = "</font>";
    private static final String LEFT_POSTED = "<font color='#8a8d8e'>";
    private static final String RIGHT_POSTED = "</font>";
    BaseHolderInterface viewInterface;
    @Inject
    DateUtil mDateUtil;
    //Event handling
    @Bind(R.id.li_event_card_main_layout)
    LinearLayout liEventCardMainLayout;
    @Bind(R.id.li_feed_event_images)
    LinearLayout liFeedEventImages;
    @Bind(R.id.tv_event_interested_btn)
    TextView tvEventInterestedBtn;
    @Bind(R.id.tv_event_going_btn)
    TextView tvEventGoingBtn;
    @Bind(R.id.tv_event_share_btn)
    TextView tvEventShareBtn;
    @Bind(R.id.tv_event_interested_people)
    TextView tvEventInterestedPeople;
    @Bind(R.id.tv_event_title)
    TextView tvEventTitle;
    @Bind(R.id.tv_event_month)
    TextView tvEventMonth;
    @Bind(R.id.tv_event_day)
    TextView tvEventDay;
    @Bind(R.id.iv_feed_event_icon)
    CircleImageView ivFeedEventIcon;
    @Bind(R.id.tv_feed_event_card_title)
    TextView tvFeedEventCardTitle;
    @Bind(R.id.tv_feed_event_time)
    TextView tvFeedEventTime;

    @Inject
    Preference<LoginResponse> userPreference;
    private FeedDetail dataItem;
    private Context mContext;
    private long mUserId;
    private int mAdminId;
    public EventCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            mUserId = userPreference.get().getUserSummary().getUserId();
            if(null != userPreference.get().getUserSummary().getUserBO()) {
                mAdminId = userPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
        }
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        mContext = context;
        dataItem.setItemPosition(position);
        eventPostUI(mUserId);
    }
    private void eventPostUI(long userId)
    {
        liEventCardMainLayout.setVisibility(View.VISIBLE);
        tvEventInterestedBtn.setEnabled(true);
        setInterested();
        goingOnEvent();
        if (!dataItem.isTrending()) {
            imageSetOnEventBackground();

        }
        if(dataItem != null &&StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle()) ){
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(AppConstants.IMPRESSIONS, AppConstants.EVENT_IMPRESSION, dataItem.getIdOfEntityOrParticipant() + AppConstants.DASH + userId + AppConstants.DASH + dataItem.getNameOrTitle());
        }
    }
    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void imageSetOnEventBackground() {
        liFeedEventImages.removeAllViews();
        liFeedEventImages.removeAllViewsInLayout();
        if (StringUtil.isNotNullOrEmptyString(dataItem.getAuthorName())) {
            StringBuilder posted = new StringBuilder();
            String feedTitle = mContext.getString(R.string.ID_APP_NAME);
            String feedCommunityName = mContext.getString(R.string.ID_EVENT);
            posted.append(feedTitle).append(AppConstants.SPACE).append(LEFT_POSTED).append(mContext.getString(R.string.ID_POSTED_AN)).append(RIGHT_POSTED).append(AppConstants.SPACE);
            posted.append(LEFT_HTML_VEIW_TAG_FOR_COLOR).append(feedCommunityName).append(RIGHT_HTML_VIEW_TAG_FOR_COLOR);
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedEventCardTitle.setText(Html.fromHtml(posted.toString(), 0)); // for 24 api and more
            } else {
                tvFeedEventCardTitle.setText(Html.fromHtml(posted.toString()));// or for older api
            }
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(dataItem.getCreatedDate(), AppConstants.DATE_FORMAT);
            tvFeedEventTime.setText(mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate));
        }

        String authorImageUrl = dataItem.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(authorImageUrl)) {
            ivFeedEventIcon.setCircularImage(true);
            ivFeedEventIcon.bindImage(authorImageUrl);
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getStartDateForEvent())) {
            long time = mDateUtil.getTimeInMillis(dataItem.getStartDateForEvent(), AppConstants.DATE_FORMAT);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            if (StringUtil.isNotNullOrEmptyString(month)) {
                tvEventMonth.setText(month);
            }
            if (StringUtil.isNotNullOrEmptyString(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)))) {
                StringBuilder stringBuilder = new StringBuilder();
                if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
                    stringBuilder.append("0").append(calendar.get(Calendar.DAY_OF_MONTH));
                    tvEventDay.setText(stringBuilder.toString());
                } else {
                    tvEventDay.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                }

            }

        }

        if (StringUtil.isNotNullOrEmptyString(dataItem.getListDescription())) {
            tvEventTitle.setText(dataItem.getListDescription());
        }
        if (StringUtil.isNotEmptyCollection(dataItem.getImageUrls())) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.challenge_image, null);
            ImageView ivEventImage = (ImageView) child.findViewById(R.id.iv_feed_challenge);
            LinearLayout liImageText = (LinearLayout) child.findViewById(R.id.li_image_text);
            liImageText.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(dataItem.getImageUrls().get(0))
                    .into(ivEventImage);
            liFeedEventImages.addView(child);
        }
    }
    private void setInterested() {
        if (dataItem.getNoOfLikes() > 0) {
            tvEventInterestedPeople.setText(dataItem.getNoOfLikes() + AppConstants.SPACE + mContext.getString(R.string.ID_PEOPLE_INTERESTED));
            tvEventInterestedPeople.setVisibility(View.VISIBLE);
        } else {
            tvEventInterestedPeople.setVisibility(View.GONE);
        }
        switch (dataItem.getReactionValue()) {
            case AppConstants.NO_REACTION_CONSTANT:
                tvEventInterestedBtn.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
                tvEventInterestedBtn.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                break;
            case AppConstants.EVENT_CONSTANT:
                tvEventInterestedBtn.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                tvEventInterestedBtn.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + dataItem.getReactionValue());
        }
    }

    private void goingOnEvent() {
        if (dataItem.isBookmarked()) {
            tvEventGoingBtn.setEnabled(false);
            tvEventGoingBtn.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            tvEventGoingBtn.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        } else {
            tvEventGoingBtn.setEnabled(true);
            tvEventGoingBtn.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            tvEventGoingBtn.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }
    }
    private void interestedPress() {
        tvEventInterestedBtn.setEnabled(false);
        dataItem.setTrending(true);
        dataItem.setLongPress(false);
        if (dataItem.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.NO_REACTION_CONSTANT, getAdapterPosition());
        } else {
            viewInterface.userCommentLikeRequest(dataItem, AppConstants.EVENT_CONSTANT, getAdapterPosition());
        }
        if (dataItem.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            dataItem.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
            dataItem.setNoOfLikes(dataItem.getNoOfLikes() - AppConstants.ONE_CONSTANT);
            tvEventInterestedBtn.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            tvEventInterestedBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_feed_commnity_join, 0, 0, 0);
        } else {
            dataItem.setReactionValue(AppConstants.EVENT_CONSTANT);
            dataItem.setNoOfLikes(dataItem.getNoOfLikes() + AppConstants.ONE_CONSTANT);
            tvEventInterestedBtn.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            tvEventInterestedBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rectangle_feed_community_joined_active, 0, 0, 0);
        }
        setInterested();
    }
    @OnClick(R.id.li_feed_event_images)
    public void eventImageClick() {
        viewInterface.handleOnClick(dataItem, liEventCardMainLayout);
        //  viewInterface.dataOperationOnClick(dataItem);
    }
    @OnClick(R.id.li_event_card_main_layout)
    public void onEventClick() {
        viewInterface.handleOnClick(dataItem, liEventCardMainLayout);
    }

    @OnClick(R.id.tv_event_interested_btn)
    public void onEventInterestedClick() {
        interestedPress();
    }

    @OnClick(R.id.tv_event_going_btn)
    public void onEventGoingClick() {
        dataItem.setTrending(true);
        tvEventGoingBtn.setEnabled(false);
        if (!dataItem.isBookmarked()) {
            viewInterface.handleOnClick(dataItem, tvEventGoingBtn);
        }
        if (!dataItem.isBookmarked()) {
            dataItem.setBookmarked(true);
        }
        goingOnEvent();
    }

    @OnClick(R.id.tv_event_share_btn)
    public void onEventShareClick() {
        viewInterface.handleOnClick(dataItem, tvEventShareBtn);
    }
    @Override
    public void viewRecycled() {

    }
    @Override
    public void onClick(View view) {

    }


}
