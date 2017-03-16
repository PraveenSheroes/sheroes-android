package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityList;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerList;
import appliedlife.pvtltd.SHEROES.presenters.CommunityListPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SHEROES-TECH on 24-01-2017.
 */
public class SelectCommunityFragment extends DialogFragment implements CommunityView, BaseHolderInterface {
    @Inject
    CommunityListPresenter mcommunityListPresenter;
    @Bind(R.id.rv_home_list)
    RecyclerView mRecyclerView;

    private MyDialogFragmentListener mHomeActivityIntractionListner;
    private final String TAG = LogUtils.makeLogTag(SelectCommunityFragment.class);
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    SelectCommunityFragment(CreateCommunityPostFragment context) {
        try {
            if (context instanceof MyDialogFragmentListener) {
                mHomeActivityIntractionListner = (MyDialogFragmentListener) context;
            }
        } catch (Fragment.InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View v = inflater.inflate(R.layout.community_list, container, false);
        ButterKnife.bind(this, v);
        mcommunityListPresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }


    @Override
    public void getityCommunityListSuccess(List<CommunityList> data) {
        mAdapter.setSheroesGenericListData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getOwnerListSuccess(List<OwnerList> data) {

    }

    @Override
    public void showNwError() {

    }


    @Override
    public void startActivityFromHolder(Intent intent) {

    }


    @Override
    public void handleOnClick(BaseResponse sheroesListDataItem, View view) {
        if (sheroesListDataItem instanceof CommunityList) {
            CommunityList communityList = (CommunityList) sheroesListDataItem;
            //  getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());
            mHomeActivityIntractionListner.onAddFriendSubmit(communityList.getName(), communityList.getBackground());
        }
        getActivity().getFragmentManager().popBackStack();

        getDialog().cancel();


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
    public List getListData() {
        return null;
    }

    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String s) {

    }

    public interface MyDialogFragmentListener {
        void onErrorOccurence();

        void onAddFriendSubmit(String communitynm, String image);
    }
}