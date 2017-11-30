package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.EventSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.EventDetailPojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.EventSpeakerData;
import appliedlife.pvtltd.SHEROES.models.entities.home.EventSponsorData;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 16-06-2017.
 */

public class EventDetailHolder extends BaseViewHolder<EventDetailPojo> {
    private final String TAG = LogUtils.makeLogTag(EventDetailHolder.class);
    @Inject
    DateUtil mDateUtil;
    BaseHolderInterface viewInterface;
    private EventDetailPojo dataItem;
    @Bind(R.id.rv_event_sponsers_list)
    RecyclerView mEventSponserRecyclerView;
    @Bind(R.id.rv_event_speaker_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_event_detail_interested_btn)
    TextView mTvEventInterestedBtn;
    @Bind(R.id.tv_event_detail_month)
    TextView mTvEventDetailMonth;
    @Bind(R.id.tv_event_detail_day)
    TextView mTvEventDetailDay;
    @Bind(R.id.tv_event_detail_title)
    TextView mTvEventDetailTitle;
    @Bind(R.id.tv_event_detail_interested_people)
    TextView mTvEventDetailInterestedPeople;
    @Bind(R.id.tv_event_detail_going_count)
    TextView mTvEventDetailGoingCount;
    @Bind(R.id.tv_event_detail_interested_count)
    TextView mTvEventDetailInterestedCount;
    @Bind(R.id.tv_event_detail_going_btn)
    TextView mTvEventDetailGoingBtn;
    @Bind(R.id.tv_start_end_time)
    TextView mTvStartEndTime;
    @Bind(R.id.tv_event_address_title)
    TextView mTvEventAddressTitle;
    @Bind(R.id.tv_event_address)
    TextView mTvEventAddress;
    @Bind(R.id.tv_event_detail_description)
    TextView mTvEventDetailDescription;
    @Bind(R.id.tv_event_share_btn)
    TextView mTvEventShareBtn;
    @Bind(R.id.card_view_speaker)
    CardView mCardViewSpeaker;
    @Bind(R.id.card_view_sponsor)
    CardView mCardViewSponsor;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private GridLayoutManager mGridManager;
    Context mContext;
    private EventSolrObj mFeedDetail;

    public EventDetailHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public void bindData(EventDetailPojo item, Context context, int position) {
        this.dataItem = item;
        mContext = context;
        mFeedDetail = (EventSolrObj)dataItem.getFeedDetail();
        mTvEventInterestedBtn.setEnabled(true);
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getNameOrTitle())) {
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                mTvEventDetailTitle.setText(Html.fromHtml(mFeedDetail.getNameOrTitle(), 0)); // for 24 api and more
            } else {
                mTvEventDetailTitle.setText(Html.fromHtml(mFeedDetail.getNameOrTitle()));// or for older api
            }
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getDescription())) {
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                mTvEventDetailDescription.setText(Html.fromHtml(mFeedDetail.getDescription(), 0)); // for 24 api and more
            } else {
                mTvEventDetailDescription.setText(Html.fromHtml(mFeedDetail.getDescription()));// or for older api
            }
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getEventVenu())) {
            mTvEventAddressTitle.setText(mFeedDetail.getEventVenu());
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getStartDateForEvent())) {

            long time = mDateUtil.getTimeInMillis(mFeedDetail.getStartDateForEvent(), AppConstants.DATE_FORMAT);
            if (System.currentTimeMillis() == time) {
                stringBuilder.append(mContext.getString(R.string.ID_TODAY));
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(time);
                stringBuilder.append(calendar.get(Calendar.DAY_OF_MONTH));
                stringBuilder.append(AppConstants.DASH);
                stringBuilder.append(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
                stringBuilder.append(AppConstants.DASH);
                stringBuilder.append(calendar.get(Calendar.YEAR));
            }
            stringBuilder.append(AppConstants.SPACE);
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getDisplayTextStartHour())) {
            int hour = Integer.parseInt(mFeedDetail.getDisplayTextStartHour());
            if (hour < 10 && mFeedDetail.getDisplayTextStartHour().length() < 2) {
                stringBuilder.append("0").append(mFeedDetail.getDisplayTextStartHour());
            } else {
                stringBuilder.append(mFeedDetail.getDisplayTextStartHour());
            }
            stringBuilder.append(AppConstants.COLON);
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getDisplayTextStartMinute())) {
            stringBuilder.append(mFeedDetail.getDisplayTextStartMinute());
            int minut = Integer.parseInt(mFeedDetail.getDisplayTextStartMinute());
            if (minut < 10 && mFeedDetail.getDisplayTextStartMinute().length() < 2) {
                stringBuilder.append(mFeedDetail.getDisplayTextStartMinute());
            } else {
                stringBuilder.append(mFeedDetail.getDisplayTextStartMinute());
            }
            stringBuilder.append(AppConstants.HOURS);
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getDisplayTextEndHour())) {
            stringBuilder.append(AppConstants.DASH);
            int hour = Integer.parseInt(mFeedDetail.getDisplayTextEndHour());
            if (hour < 10 && mFeedDetail.getDisplayTextEndHour().length() < 2) {
                stringBuilder.append("0").append(mFeedDetail.getDisplayTextEndHour());
            } else {
                stringBuilder.append(mFeedDetail.getDisplayTextEndHour());
            }
            stringBuilder.append(AppConstants.COLON);
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getDisplayTextEndMinute())) {
            stringBuilder.append(mFeedDetail.getDisplayTextEndMinute());
            int minut = Integer.parseInt(mFeedDetail.getDisplayTextEndMinute());
            if (minut < 10 && mFeedDetail.getDisplayTextEndMinute().length() < 2) {
                stringBuilder.append(mFeedDetail.getDisplayTextEndMinute());
            } else {
                stringBuilder.append(mFeedDetail.getDisplayTextEndMinute());
            }
            stringBuilder.append(AppConstants.HOURS);
        }
        if (StringUtil.isNotNullOrEmptyString(stringBuilder.toString())) {
            mTvStartEndTime.setText(stringBuilder.toString());
        }
        setInterested();
        goingOnEvent();
        setEventDate();
        if (null != mFeedDetail && StringUtil.isNotEmptyCollection(mFeedDetail.getSpeakerId())) {
            mCardViewSpeaker.setVisibility(View.VISIBLE);
            List<EventSpeakerData> speakerList = new ArrayList<>();
            int size = mFeedDetail.getSpeakerId().size();
            for (int i = 0; i < size; i++) {
                EventSpeakerData eventSpeakerData = new EventSpeakerData();
                eventSpeakerData.setSpeakerName(mFeedDetail.getSpeakerName().get(i));
                eventSpeakerData.setSpeakerDescription(mFeedDetail.getSpeakerDescription().get(i));
                eventSpeakerData.setSpeakerDesignation(mFeedDetail.getSpeakerDesignation().get(i));
                eventSpeakerData.setSpeakerImageUrl(mFeedDetail.getSpeakerImageUrl().get(i));
                eventSpeakerData.setSpeakerId(mFeedDetail.getSpeakerId().get(i));
                speakerList.add(eventSpeakerData);
                speakerList.add(eventSpeakerData);
                speakerList.add(eventSpeakerData);
                speakerList.add(eventSpeakerData);
            }
            setSpeakerListItem(speakerList);
        } else {
            mCardViewSpeaker.setVisibility(View.GONE);
        }
        if (null != mFeedDetail && StringUtil.isNotEmptyCollection(mFeedDetail.getSpeakerId())) {
            mCardViewSponsor.setVisibility(View.VISIBLE);
            List<EventSponsorData> sponsorList = new ArrayList<>();
            int size = mFeedDetail.getSpeakerId().size();
            for (int i = 0; i < size; i++) {
                EventSponsorData eventSponsorData = new EventSponsorData();
                eventSponsorData.setSponsorName(mFeedDetail.getSponsorName().get(i));
                eventSponsorData.setSponsorDescription(mFeedDetail.getSpeakerDescription().get(i));
                eventSponsorData.setSponsorDesignation(mFeedDetail.getSpeakerDesignation().get(i));
                eventSponsorData.setSponsorImageUrl(mFeedDetail.getSpeakerImageUrl().get(i));
                eventSponsorData.setSponsorId(mFeedDetail.getSpeakerId().get(i));
                sponsorList.add(eventSponsorData);
                sponsorList.add(eventSponsorData);
                sponsorList.add(eventSponsorData);
                sponsorList.add(eventSponsorData);
            }
            setSponsersListItem(sponsorList);
        } else {
            mCardViewSponsor.setVisibility(View.GONE);
        }
    }

    private void setEventDate() {
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getStartDateForEvent())) {
            long time = mDateUtil.getTimeInMillis(mFeedDetail.getStartDateForEvent(), AppConstants.DATE_FORMAT);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            if (StringUtil.isNotNullOrEmptyString(month)) {
                mTvEventDetailMonth.setText(month);
            }
            if (StringUtil.isNotNullOrEmptyString(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))))
            {
                StringBuilder stringBuilder=new StringBuilder();
                if(calendar.get(Calendar.DAY_OF_MONTH)<10)
                {
                    stringBuilder.append("0").append(calendar.get(Calendar.DAY_OF_MONTH));
                    mTvEventDetailDay.setText(stringBuilder.toString());
                }else
                {
                    mTvEventDetailDay.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                }

            }

        }
    }

    private void setInterested() {
        if (mFeedDetail.getNoOfLikes() > 0) {
            if (mFeedDetail.getNoOfLikes() > 999) {
                int value = mFeedDetail.getNoOfLikes() / 1000;
                mTvEventDetailInterestedCount.setText(value + AppConstants.THOUSANDS + AppConstants.SPACE + mContext.getString(R.string.ID_PEOPLE_INTERESTED));
            } else {
                mTvEventDetailInterestedCount.setText(mFeedDetail.getNoOfLikes() + AppConstants.SPACE + mContext.getString(R.string.ID_PEOPLE_INTERESTED));
            }
            mTvEventDetailInterestedCount.setVisibility(View.VISIBLE);
        } else {
            mTvEventDetailInterestedCount.setVisibility(View.GONE);
        }
        switch (mFeedDetail.getReactionValue()) {
            case AppConstants.NO_REACTION_CONSTANT:
                mTvEventInterestedBtn.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
                mTvEventInterestedBtn.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                break;
            case AppConstants.EVENT_CONSTANT:
                mTvEventInterestedBtn.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                mTvEventInterestedBtn.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + mFeedDetail.getReactionValue());
        }
    }

    private void goingOnEvent() {
        if (mFeedDetail.getNoOfBookmarks() > 0) {
            mTvEventDetailGoingCount.setVisibility(View.VISIBLE);
            if (mFeedDetail.getNoOfBookmarks() > 999) {
                int value = mFeedDetail.getNoOfBookmarks() / 1000;
                mTvEventDetailGoingCount.setText(value + AppConstants.THOUSANDS + AppConstants.SPACE + mContext.getString(R.string.ID_TOTAL_GOING));
            } else {
                mTvEventDetailGoingCount.setText(mFeedDetail.getNoOfBookmarks() + AppConstants.SPACE + mContext.getString(R.string.ID_TOTAL_GOING));
            }
        } else {
            mTvEventDetailGoingCount.setVisibility(View.GONE);
        }
        if (mFeedDetail.isBookmarked()) {
            mTvEventDetailGoingBtn.setEnabled(false);
            mTvEventDetailGoingBtn.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            mTvEventDetailGoingBtn.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
        } else {
            mTvEventDetailGoingBtn.setEnabled(true);
            mTvEventDetailGoingBtn.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            mTvEventDetailGoingBtn.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
        }
    }

    private void setSpeakerListItem(List<EventSpeakerData> speakerList) {
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(mContext, (HomeActivity) mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setSheroesGenericListData(speakerList);
        mAdapter.notifyDataSetChanged();
    }

    private void setSponsersListItem(List<EventSponsorData> speakerList) {
        mGridManager = new GridLayoutManager(mContext, 2);
        mEventSponserRecyclerView.setLayoutManager(mGridManager);
        mAdapter = new GenericRecyclerViewAdapter(mContext, (HomeActivity) mContext);
        mEventSponserRecyclerView.setLayoutManager(mGridManager);
        mEventSponserRecyclerView.setAdapter(mAdapter);
        mAdapter.setSheroesGenericListData(speakerList);
        mAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.tv_event_detail_interested_btn)
    public void onInterestedClick() {
        mTvEventInterestedBtn.setEnabled(false);
        if (mFeedDetail.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            viewInterface.handleOnClick(mFeedDetail, mTvEventInterestedBtn);
        } else {
            viewInterface.handleOnClick(mFeedDetail, mTvEventInterestedBtn);
        }
        if (mFeedDetail.getReactionValue() != AppConstants.NO_REACTION_CONSTANT) {
            mFeedDetail.setReactionValue(AppConstants.NO_REACTION_CONSTANT);
            mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() - AppConstants.ONE_CONSTANT);
        } else {
            mFeedDetail.setReactionValue(AppConstants.EVENT_CONSTANT);
            mFeedDetail.setNoOfLikes(mFeedDetail.getNoOfLikes() + AppConstants.ONE_CONSTANT);
        }
        setInterested();
    }

    @OnClick(R.id.tv_event_detail_going_btn)
    public void onGoingClick() {
        mTvEventDetailGoingBtn.setEnabled(false);
        if (mFeedDetail.isBookmarked()) {
            viewInterface.handleOnClick(mFeedDetail, mTvEventDetailGoingBtn);
        } else {
            viewInterface.handleOnClick(mFeedDetail, mTvEventDetailGoingBtn);
        }
        if (!mFeedDetail.isBookmarked()) {
            mFeedDetail.setBookmarked(true);
        } else {
            mFeedDetail.setBookmarked(false);
        }
        goingOnEvent();
    }

    @OnClick(R.id.tv_event_share_btn)
    public void onShareClick() {
        viewInterface.handleOnClick(mFeedDetail, mTvEventShareBtn);
    }

    @Override
    public void onClick(View view) {


    }

    @Override
    public void viewRecycled() {

    }
}
