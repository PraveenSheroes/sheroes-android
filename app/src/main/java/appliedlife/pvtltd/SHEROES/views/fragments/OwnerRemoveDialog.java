package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.Member;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerList;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListResponse;
import appliedlife.pvtltd.SHEROES.presenters.OwnerPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;

/**
 * Created by Ajit Kumar on 09-02-2017.
 */

public class OwnerRemoveDialog extends BaseDialogFragment implements CommunityView {
    private final String TAG = LogUtils.makeLogTag(OwnerRemoveDialog.class);
    @Inject
    OwnerPresenter mOwnerPresenter;
    @Bind(R.id.tvcancel)
    TextView mTv_cancel;
    @Bind(R.id.tv_continue)
    TextView mTv_continue;
    Member members;
    OwnerList mOwner;
    Long community_id;
    Context context;
    @Bind(R.id.tv_owner_name)
    TextView tv_owner_name;
    @Inject
    AppUtils mAppUtils;
    boolean isOwner;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.owner_remove_dialog, container, false);
        ButterKnife.bind(this, view);
        mOwnerPresenter.attachView(this);
        if (null != getArguments()) {
            isOwner = getArguments().getBoolean(AppConstants.COMMUNITY_POST_FRAGMENT);
            if (isOwner) {
                mOwner = getArguments().getParcelable(AppConstants.OWNER_SUB_TYPE);
                if (null != mOwner) {
                    tv_owner_name.setText(mOwner.getName());
                }
            } else {
                members = getArguments().getParcelable(AppConstants.MEMBER);
                community_id = getArguments().getLong(AppConstants.COMMUNITY_DETAIL);
                if (null != members) {
                    tv_owner_name.setText(members.getCommunityUserFirstName());
                }
            }

        }

        setCancelable(true);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }
    }

    @OnClick(R.id.tvcancel)
    public void cancelClick() {
        getDialog().cancel();
    }

    @OnClick(R.id.tv_continue)
    public void continueClick() {
        DeactivateOwnerRequest deactivateOwnerRequest = mAppUtils.deActivateOwnerRequestBuilder(community_id);
        if (null != members.getUsersId()) {
            deactivateOwnerRequest.setUserId(members.getUsersId());
        }
        mOwnerPresenter.getCommunityOwnerDeactive(deactivateOwnerRequest);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismiss();
            }
        };
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
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {

    }


    @Override
    public void getSelectedCommunityListSuccess(List<CommunityPostResponse> selected_community_response) {

    }

    @Override
    public void getOwnerListSuccess(OwnerListResponse ownerListResponse) {

    }

    @Override
    public void createCommunitySuccess(CreateCommunityResponse createCommunityResponse) {

    }

    @Override
    public void getOwnerListDeactivateSuccess(DeactivateOwnerResponse deactivateOwnerResp) {
        switch (deactivateOwnerResp.getStatus()) {
            case AppConstants.SUCCESS:
                ((CommunitiesDetailActivity) getActivity()).onOwnerClose();
                getDialog().cancel();
                break;
            case AppConstants.FAILED:
                getDialog().cancel();
                ((CommunitiesDetailActivity)getActivity()).onShowErrorDialog(deactivateOwnerResp.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_JOIN_INVITE);
                break;
            default:
                getDialog().cancel();
                ((CommunitiesDetailActivity)getActivity()).onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), ERROR_JOIN_INVITE);
        }
    }

    @Override
    public void postCreateCommunityOwner(CreateCommunityOwnerResponse createCommunityOwnerResponse) {

    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, CommunityEnum communityEnum) {

    }

}
