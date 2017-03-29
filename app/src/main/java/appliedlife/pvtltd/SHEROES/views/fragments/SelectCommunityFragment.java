package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityList;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostCreateResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.Docs;
import appliedlife.pvtltd.SHEROES.models.entities.community.Member;
import appliedlife.pvtltd.SHEROES.models.entities.community.SelectCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.presenters.CreateCommunityPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ajit Kumar on 24-01-2017.
 */
public class SelectCommunityFragment extends BaseDialogFragment implements CommunityView, BaseHolderInterface, HomeView {
    @Inject
    CreateCommunityPresenter mHomePresenter;
    @Bind(R.id.rv_home_list)
    RecyclerView mRecyclerView;
    @Inject
    AppUtils mAppUtils;
    private FragmentListRefreshData mFragmentListRefreshData;

    private MyDialogFragmentListener mHomeActivityIntractionListner;
    private final String TAG = LogUtils.makeLogTag(SelectCommunityFragment.class);
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    public void setListener(MyDialogFragmentListener listener) {
        this.mHomeActivityIntractionListner =  listener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View v = inflater.inflate(R.layout.community_list, container, false);
        ButterKnife.bind(this, v);
        mHomePresenter.attachView(this);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        SelectCommunityRequest selectCommunityRequest=new SelectCommunityRequest();
        selectCommunityRequest.setAppVersion("string");
        selectCommunityRequest.setCloudMessagingId("string");
        selectCommunityRequest.setDeviceUniqueId("string");
        selectCommunityRequest.setMasterDataType("skill");
        mHomePresenter.getSelectCommunityFromPresenter(selectCommunityRequest);

        return v;
    }


    @Override
    public void getityCommunityListSuccess(List<CommunityList> data) {
        mAdapter.setSheroesGenericListData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getSelectedCommunityListSuccess(List<Docs> selected_community_response) {
        mAdapter.setSheroesGenericListData( selected_community_response);
        mAdapter.setCallForRecycler(AppConstants.COMMUNITY_NAME_SUB_TYPE);
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void getOwnerListSuccess(List<Member> ownerListResponse) {

    }

    @Override
    public void postCreateCommunitySuccess(CreateCommunityResponse createCommunityResponse) {

    }

    @Override
    public void addPostCreateCommunitySuccess(CommunityPostCreateResponse createCommunityResponse) {

    }

    @Override
    public void getOwnerListDeactivateSuccess(DeactivateOwnerResponse deactivateOwnerResponse) {

    }

    @Override
    public void postCreateCommunityOwnerSuccess(CreateCommunityOwnerResponse createCommunityOwnerResponse) {

    }

    @Override
    public void showNwError() {

    }


    @Override
    public void startActivityFromHolder(Intent intent) {

    }


    @Override
    public void handleOnClick(BaseResponse sheroesListDataItem, View view) {
        if (sheroesListDataItem instanceof Docs) {
            Docs docs = (Docs) sheroesListDataItem;
            //  getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());
            mHomeActivityIntractionListner.onAddCommunityDetailSubmit(docs);
            //  getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());
           // mHomeActivityIntractionListner.onAddFriendSubmit(communityList.getName(),communityList.getBackground());
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
    public List getListData() {
        return null;
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {

    }


    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {

    }


    @Override
    public void getTagListSuccess(List<Doc> feedDetailList) {

    }

    @Override
    public void getSuccessForAllResponse(String success, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void getDB(List<RecentSearchData> recentSearchDatas) {

    }


    public interface MyDialogFragmentListener {
        void onErrorOccurence();

        void onAddCommunityDetailSubmit(Docs docs);
    }
}