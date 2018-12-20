package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.IHashTagCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ImageSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LeaderBoardUserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.PollSolarObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollOptionModel;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.SearchPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.FeedAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.HashTagsAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ISearchView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment.TRENDING_FEED_SCREEN_LABEL;

public class HashTagFragment extends BaseFragment implements ISearchView, BaseHolderInterface, IHashTagCallBack {
    @Inject
    SearchPresenter mSearchPresenter;

    View view;
    @Bind(R.id.rv_hashtags)
    RecyclerView hashTagsView;
    private HashTagsAdapter hashTagsAdapter;
    private List<FeedDetail> hashTagDetails;
    private List<String> hashTagsList;
    private FeedAdapter feedAdapter;
    @Bind(R.id.ll_loader)
    LinearLayout loaderLayout;
    @Bind(R.id.fl_container)
    FrameLayout containerLayout;
    @Bind(R.id.no_internet)
    CardView noInternet;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        view = inflater.inflate(R.layout.fragment_hashtag, container, false);
        ButterKnife.bind(this, view);
        mSearchPresenter.attachView(this);

        hashTagsList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hashTagsView.setLayoutManager(linearLayoutManager);

        hashTagsAdapter = new HashTagsAdapter(getActivity(), this, hashTagsList);
        hashTagsView.setAdapter(hashTagsAdapter);

        callHashTagApi();

        return view;

    }

    @Override
    protected SheroesPresenter getPresenter() {
        return null;
    }

    @Override
    public String getScreenName() {
        return null;
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {

    }

    @Override
    public void showNotificationList(BelNotificationListResponse bellNotificationResponse) {

    }

    @Override
    public void getNotificationReadCountSuccess(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void onConfigFetched() {

    }

    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {

    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {

    }

    @Override
    public void setListData(BaseResponse data, boolean flag) {

    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {

    }

    @Override
    public void navigateToProfileView(BaseResponse baseResponse, int mValue) {

    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {

    }


    @Override
    public void onHashTagClicked(String query) {
        SearchFragment searchFragment = (SearchFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fl_article_card_view);
        searchFragment.onHashTagClicked(query);

//        filterFeed(query);
    }

    public void filterFeed(String query) {
        loaderLayout.setVisibility(View.GONE);
        hashTagsView.setVisibility(View.GONE);
        containerLayout.setVisibility(View.VISIBLE);

        FeedFragment feedFragment = new FeedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.END_POINT_URL, "");
        bundle.putBoolean(FeedFragment.IS_HOME_FEED, false);
        bundle.putString(AppConstants.SCREEN_NAME, TRENDING_FEED_SCREEN_LABEL);
        feedFragment.setArguments(bundle);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        feedFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fl_container, feedFragment);
        fragmentTransaction.commit();
        feedFragment.paramsToFilterFeed(true, query, "hashtags");
    }

    @Override
    public void showEmptyScreen(String s) {

    }

    @OnClick({R.id.tv_retry_for_internet})
    public void onRetryClick() {
        noInternet.setVisibility(View.GONE);
        hashTagsView.setVisibility(View.VISIBLE);
        containerLayout.setVisibility(View.GONE);
        loaderLayout.setVisibility(View.VISIBLE);
        callHashTagApi();
    }

    @OnClick({R.id.tv_goto_setting})
    public void onSettingClick() {
        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

    @Override
    public void onHashTagsResponse(List<String> hashtagList) {
        noInternet.setVisibility(View.GONE);
        hashTagsAdapter.refreshList(hashtagList);
        containerLayout.setVisibility(View.GONE);
        loaderLayout.setVisibility(View.GONE);
        hashTagsView.setVisibility(View.VISIBLE);
    }

    public void callHashTagApi() {
        noInternet.setVisibility(View.GONE);
        hashTagsView.setVisibility(View.GONE);
        loaderLayout.setVisibility(View.VISIBLE);
        containerLayout.setVisibility(View.GONE);
        mSearchPresenter.getTrendingHashtags();
    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        if (StringUtil.isNotNullOrEmptyString(errorMsg) && errorMsg.equalsIgnoreCase(AppConstants.CHECK_NETWORK_CONNECTION)) {
            noInternet.setVisibility(View.VISIBLE);
            hashTagsView.setVisibility(View.GONE);
            containerLayout.setVisibility(View.GONE);
            loaderLayout.setVisibility(View.GONE);
        } else {
            super.showError(errorMsg, feedParticipationEnum);
        }
    }
}
