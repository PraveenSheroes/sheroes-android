package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserFollowedMentorsResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.ProfileFollowedMentorAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.EmptyRecyclerView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileNewView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ravi on 02/01/18.
 */

public class ProfileCommunityListFragment extends BaseFragment implements ProfileNewView {

    private static final String SCREEN_LABEL = "Community List Screen";

    @Bind(R.id.rv_mentor_list)
    EmptyRecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;
    private ProfileFollowedMentorAdapter mAdapter;

    @Inject
    ProfilePresenterImpl profilePresenter;

    @Inject
    AppUtils mAppUtils;

    public static UserProfileTabFragment createInstance(long userId) {
        UserProfileTabFragment userProfileTabFragment = new UserProfileTabFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("USERID", userId); //todo -profile- change here
        userProfileTabFragment.setArguments(bundle);
        return userProfileTabFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.followed_mentor_listing_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    private void mentorSearchInListPagination() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // mRecyclerView.setEmptyViewWithImage(emptyView, R.string.empty_challenge_text, R.drawable.vector_emoty_challenge, R.string.empty_challenge_sub_text);

        mAdapter = new ProfileFollowedMentorAdapter(getContext(), profilePresenter, this);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mAdapter);

        profilePresenter.getFollowedMentors(mAppUtils.followedMentorRequestBuilder(1));
    }

    @Override
    public void getFollowedMentors(UserFollowedMentorsResponse profileFeedResponsePojo) {

        List<UserSolrObj> feedDetailList = profileFeedResponsePojo.getFeedDetails();
        mAdapter.setData(feedDetailList);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void getUsersFollowerCount(BaseResponse userFollowerOrFollowingCountResponse) {

    }

    @Override
    public void getUsersFollowingCount(BaseResponse userFollowerOrFollowingCountResponse) {

    }

    @Override
    public void getUsersPostCount(int totalPost) {

    }

    @Override
    public void getUsersCommunities(FeedResponsePojo userCommunities) {

    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}
