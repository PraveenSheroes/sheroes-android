package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.IHashTagCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.SearchPresenter;
import appliedlife.pvtltd.SHEROES.views.adapters.FeedAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.HashTagsAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class HashTagFragment extends BaseFragment implements BaseHolderInterface, IHashTagCallBack {
    View view;
    @Bind(R.id.rv_hashtags)RecyclerView hashTagsView;
    @Bind(R.id.rv_hashtag_details)RecyclerView hashTagDetailsView;
    private HashTagsAdapter hashTagsAdapter;
    private List<FeedDetail> hashTagDetails ;
    private List<String> hashTagsList;
    private FeedAdapter feedAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hashtag, container, false);
        ButterKnife.bind(this, view);


        hashTagsList = new ArrayList<>();

        hashTagsList.add("#start ups");
        hashTagsList.add("hello");
        hashTagsList.add("#culture");
        hashTagsList.add("#beauty");
        hashTagsList.add("#health");
        hashTagsList.add("#style");
        hashTagsList.add("#cooking");
        hashTagsList.add("#relationships");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hashTagsView.setLayoutManager(linearLayoutManager);

        hashTagsAdapter = new HashTagsAdapter(getActivity(), this, hashTagsList);
        hashTagsView.setAdapter(hashTagsAdapter);


        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        hashTagDetailsView.setLayoutManager(manager);

        feedAdapter = new FeedAdapter(getContext(), this);
        hashTagDetailsView.setAdapter(feedAdapter);

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

//    @Override
//    public void onHashTagClicked() {
//        hashTagsView.setVisibility(View.GONE);
//        mSearchPresenter.searchQuery(mETSearch.getText().toString(), mSearchCategory);
//
//        hashTagDetailsView.setVisibility(View.VISIBLE);
//
//        hashtagTxt.setVisibility(View.VISIBLE);
//    }

    public void showAllHashTags(ArrayList<FeedDetail> feedDetails) {
        hashTagsView.setVisibility(View.GONE);
        feedAdapter.setData(feedDetails);
        feedAdapter.notifyDataSetChanged();
        hashTagDetailsView.setVisibility(View.VISIBLE);

//        hashTagsAdapter.refreshList(feedDetails);

    }

    public void populateTrendingHashTags(){
        hashTagsView.setVisibility(View.VISIBLE);
        hashTagDetailsView.setVisibility(View.GONE);
//        hashTagsAdapter.refreshList(hashTagsList);
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
    }
}
