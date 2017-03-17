package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.RequestedList;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.presenters.RequestedPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.RequestedView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 07-02-2017.
 */

public class CommunityRequestedFragment extends BaseFragment implements RequestedView {
    private final String TAG = LogUtils.makeLogTag(CommentReactionFragment.class);
    @Inject
    RequestedPresenter requestedPresenter;
    @Bind(R.id.rv_comment_reaction_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_comment_reaction_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.fmCommunityMembersClose)
    FrameLayout fmCommunityMembersClose;
    private FragmentOpen mFragmentOpen;
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    private GenericRecyclerViewAdapter mAdapter;
    private RequestHomeActivityIntractionListner mHomeActivityIntractionListner;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof RequestHomeActivityIntractionListner) {
                mHomeActivityIntractionListner = (RequestHomeActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        ButterKnife.bind(this, view);
        requestedPresenter.attachView(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunitiesDetailActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);




        return view;
    }
    @OnClick(R.id.fmCommunityMembersClose)
    public void communityClosePress()
    {
        mHomeActivityIntractionListner.closeRequestFragment();
    }
   /* @Override
    public void getAllCommentsAndReactions(List<CommentsList> data) {
        if(mAdapter!=null) {
            mTvUserCommentHeaderText.setText(getString(R.string.ID_REPLIES)+getString(R.string.ID_OPEN_BRACKET)+String.valueOf(data.size())+getString(R.string.ID_CLOSE_BRACKET));
            mAdapter.setSheroesGenericListData(data);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getAllReactions(List<ReactionList> data) {
        if(mAdapter!=null) {
            mTvUserCommentHeaderText.setText(getString(R.string.ID_REACTION)+getString(R.string.ID_OPEN_BRACKET)+String.valueOf(data.size())+getString(R.string.ID_CLOSE_BRACKET));
            mAdapter.setSheroesGenericListData(data);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showNwError() {
        mHomeActivityIntractionListner.onErrorOccurence();
    }


    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();
    }
*/

    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requestedPresenter.detachView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void getAllRequest(List<RequestedList> data) {
        mAdapter.setSheroesGenericListData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNwError() {

    }


    @Override
    public void startNextScreen() {

    }


    public interface RequestHomeActivityIntractionListner {
        void onErrorOccurence();
        void closeRequestFragment();
        void onClickReactionList(FragmentOpen isFragmentOpen);
    }
   /* @OnClick(R.id.tv_user_comment_header_text)
    public void dismissCommentDialog()
    {
        mHomeActivityIntractionListner.onDialogDismissWithListRefresh(mFragmentOpen);
    }
    @OnClick(R.id.fl_comment_reaction)
    public void openReactionList()
    {
        mFragmentOpen.setReactionList(true);
        mFragmentOpen.setCommentList(false);
        mHomeActivityIntractionListner.onClickReactionList(mFragmentOpen);
    }*/
}