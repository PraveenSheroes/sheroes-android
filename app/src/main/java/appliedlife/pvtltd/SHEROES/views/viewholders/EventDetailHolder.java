package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
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
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private GridLayoutManager mGridManager;
    Context mContext;
    private FeedDetail mFeedDetail;

    public EventDetailHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(EventDetailPojo item, Context context, int position) {
        this.dataItem = item;
        mContext = context;
        mFeedDetail = dataItem.getFeedDetail();
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getNameOrTitle())) {
            mTvEventDetailTitle.setText(mFeedDetail.getNameOrTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getDescription())) {
            mTvEventDetailDescription.setText(mFeedDetail.getDescription());
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getEventVenu())) {
            mTvEventAddress.setText(mFeedDetail.getEventVenu());
        }
        StringBuilder stringBuilder=new StringBuilder();
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getStartDate())) {
           stringBuilder.append(mFeedDetail.getStartDate());
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getDisplayTextStartHour())) {
            stringBuilder.append(mFeedDetail.getDisplayTextStartHour()).append(AppConstants.COLON);
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getDisplayTextStartMinute())) {
            stringBuilder.append(mFeedDetail.getDisplayTextStartMinute()).append(AppConstants.HOURS);
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getDisplayTextEndHour())) {
            stringBuilder.append(AppConstants.DASH).append(mFeedDetail.getDisplayTextEndHour()).append(AppConstants.COLON);
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getDisplayTextEndMinute())) {
            stringBuilder.append(mFeedDetail.getDisplayTextEndMinute()).append(AppConstants.HOURS);
        }
        if(StringUtil.isNotNullOrEmptyString(stringBuilder.toString())) {
            mTvStartEndTime.setText(stringBuilder.toString());
        }
        setInterested();
        goingOnEvent();
        String postedDateOnly = mFeedDetail.getPostingDateOnly();
        if (StringUtil.isNotNullOrEmptyString(postedDateOnly)) {
            String[] splitDate = postedDateOnly.split(AppConstants.SPACE);
            if (splitDate.length>1) {
                if (StringUtil.isNotNullOrEmptyString(splitDate[1])) {
                    mTvEventDetailMonth.setText(splitDate[1]);
                }
                if (StringUtil.isNotNullOrEmptyString(splitDate[0])) {
                    mTvEventDetailDay.setText(splitDate[0]);
                }
            }
        }
        if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getDescription())) {
            mTvEventDetailTitle.setText(mFeedDetail.getDescription());
        }
        if (null != mFeedDetail && StringUtil.isNotEmptyCollection(mFeedDetail.getSpeakerId())) {
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
        }
        if (null != mFeedDetail && StringUtil.isNotEmptyCollection(mFeedDetail.getSpeakerId())) {
            List<EventSponsorData> sponsorList = new ArrayList<>();
            int size = mFeedDetail.getSpeakerId().size();
            for (int i = 0; i < size; i++) {
                EventSponsorData eventSponsorData = new EventSponsorData();
                eventSponsorData.setSponsorName(mFeedDetail.getSponsorName().get(i));
                eventSponsorData.setSponsorDescription(mFeedDetail.getSpeakerDescription().get(i));
                eventSponsorData.setSponsorDesignation(mFeedDetail.getSpeakerDesignation().get(i));
                // eventSponsorData.setSponsorImageUrl(mFeedDetail.getSpeakerImageUrl().get(i));
                eventSponsorData.setSponsorImageUrl("https://a1.cdn-hotels.com/cos/production48/d1785/10fa68a0-ac68-11e4-99a1-d89d672bd508.jpg");
                eventSponsorData.setSponsorId(mFeedDetail.getSpeakerId().get(i));
                sponsorList.add(eventSponsorData);
                sponsorList.add(eventSponsorData);
                sponsorList.add(eventSponsorData);
                sponsorList.add(eventSponsorData);
            }
            setSponsersListItem(sponsorList);
        }
    }

    private void setInterested() {
        if (mFeedDetail.getNoOfLikes() > 0) {
            if (mFeedDetail.getNoOfLikes() > 999) {
                int value = mFeedDetail.getNoOfLikes() / 1000;
                mTvEventInterestedBtn.setText(value + AppConstants.THOUSANDS + AppConstants.SPACE + mContext.getString(R.string.ID_PEOPLE_INTERESTED));
            } else {
                mTvEventDetailGoingCount.setText(String.valueOf(mFeedDetail.getNoOfLikes()));
            }
            mTvEventInterestedBtn.setVisibility(View.VISIBLE);
        } else {
            mTvEventInterestedBtn.setVisibility(View.GONE);
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
                mTvEventDetailGoingCount.setText(String.valueOf(mFeedDetail.getNoOfBookmarks()));
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
        viewInterface.handleOnClick(mFeedDetail, mTvEventInterestedBtn);
    }

    @OnClick(R.id.tv_event_detail_going_btn)
    public void onGoingClick() {
        viewInterface.handleOnClick(mFeedDetail, mTvEventInterestedBtn);
    }
    @Override
    public void onClick(View view) {


    }

    @Override
    public void viewRecycled() {

    }
}
