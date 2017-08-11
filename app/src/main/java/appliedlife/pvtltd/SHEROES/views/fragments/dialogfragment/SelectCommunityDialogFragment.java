package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListResponse;
import appliedlife.pvtltd.SHEROES.presenters.CreateCommunityPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ajit Kumar on 24-01-2017.
 */
public class SelectCommunityDialogFragment extends BaseDialogFragment implements CommunityView, BaseHolderInterface {
    private final String TAG = LogUtils.makeLogTag(SelectCommunityDialogFragment.class);
    @Inject
    CreateCommunityPresenter mCreateCommunityPresenter;
    @Bind(R.id.rv_home_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_dialog_progress_bar)
    ProgressBar mProgressBar;
    @Inject
    AppUtils mAppUtils;
    private MyDialogFragmentListener mHomeActivityIntractionListner;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    List<CommunityPostResponse> communityPostResponses;
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
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mCreateCommunityPresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mCreateCommunityPresenter.getSelectCommunityFromPresenter(mAppUtils.selectCommunityRequestBuilder());
        return v;
    }


    @Override
    public void getSelectedCommunityListSuccess(List<CommunityPostResponse> selected_community_response) {
        if(StringUtil.isNotEmptyCollection(selected_community_response)) {
            communityPostResponses=selected_community_response;
            mAdapter.setSheroesGenericListData(selected_community_response);
            mAdapter.setCallForRecycler(AppConstants.COMMUNITY_NAME_SUB_TYPE);
            mAdapter.notifyDataSetChanged();
            setDilogSize();
        }
    }
    @Override
    public void startProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.bringToFront();
        }
    }

    @Override
    public void stopProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.GONE);
        }
    }
    @Override
    public void getOwnerListSuccess(OwnerListResponse ownerListResponse) {

    }

    @Override
    public void createCommunitySuccess(BaseResponse baseResponse) {

    }

    @Override
    public void getOwnerListDeactivateSuccess(DeactivateOwnerResponse deactivateOwnerResponse) {

    }

    @Override
    public void postCreateCommunityOwner(CreateCommunityOwnerResponse createCommunityOwnerResponse) {

    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, CommunityEnum communityEnum) {

    }


    @Override
    public void startActivityFromHolder(Intent intent) {

    }


    @Override
    public void handleOnClick(BaseResponse sheroesListDataItem, View view) {
        if (sheroesListDataItem instanceof CommunityPostResponse) {
            CommunityPostResponse communityPostResponse = (CommunityPostResponse) sheroesListDataItem;
            mHomeActivityIntractionListner.onAddCommunityDetailSubmit(communityPostResponse);
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
    public void championProfile(BaseResponse baseResponse, int championValue) {

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.y = -20;
            params.x = 10;

            dialog.getWindow().setLayout(getScreenWidth() - 100, ViewGroup.LayoutParams.WRAP_CONTENT);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        }

    }
    private void setDilogSize()
    {
        Dialog dialog = getDialog();

        if (dialog != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.y = -20;
            params.x = 10;
            if(StringUtil.isNotEmptyCollection(communityPostResponses)) {
                if (communityPostResponses.size() < 9) {
                    dialog.getWindow().setLayout(getScreenWidth() - 100, ViewGroup.LayoutParams.WRAP_CONTENT);
                } else {
                    dialog.getWindow().setLayout(getScreenWidth() - 100, getScreenheight() - 400);

                }
            }
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        }
    }
    private int getScreenWidth()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width;
    }
    private int getScreenheight()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return height;
    }
    public interface MyDialogFragmentListener {
        void onAddCommunityDetailSubmit(CommunityPostResponse communityPostResponse);
    }
}