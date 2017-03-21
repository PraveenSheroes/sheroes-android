package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityList;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.Member;
import appliedlife.pvtltd.SHEROES.presenters.OwnerPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 09-02-2017.
 */

public class OwnerRemoveDialog extends BaseDialogFragment implements CommunityView {
    @Inject
    OwnerPresenter mOwnerPresenter;
    private boolean finishParent;
    private CloseListener mHomeActivityIntractionListner;
    private final String TAG = LogUtils.makeLogTag(SelectCommunityFragment.class);
    @Bind(R.id.tvcancel)
    TextView mTv_cancel;
    @Bind(R.id.tv_continue)
    TextView mTv_continue;
    Member members;
    Long community_id;
    Context context;

    public OwnerRemoveDialog(CommunitiesDetailActivity context) {
        try {
            this.context=context;
            if (context instanceof CommunitiesDetailActivity) {
                mHomeActivityIntractionListner = (CloseListener)getActivity();
            }
        } catch (Fragment.InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(context).inject(this);

        if(null!=getArguments()) {
            members=getArguments().getParcelable(AppConstants.MEMBER);
            community_id=getArguments().getLong(AppConstants.COMMUNITY_DETAIL);
        }
        View view = inflater.inflate(R.layout.owner_remove_dialog, container, false);

        ButterKnife.bind(this, view);
        mOwnerPresenter.attachView(this);

        //  finishParent = getArguments().getBoolean(DISMISS_PARENT_ON_OK_OR_BACK, false);
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
    public void cancelClick()
    {
        getDialog().cancel();
    }
    @OnClick(R.id.tv_continue)
    public void continueClick()
    {
        DeactivateOwnerRequest deactivateOwnerRequest=new DeactivateOwnerRequest();
        deactivateOwnerRequest.setScreenName("String");
        deactivateOwnerRequest.setLastScreenName("String");
        deactivateOwnerRequest.setDeviceUniqueId("String");
        deactivateOwnerRequest.setCommunityId(community_id);
        deactivateOwnerRequest.setAppVersion("5.0");
        deactivateOwnerRequest.setCloudMessagingId("String");
        deactivateOwnerRequest.setUserId(members.getCommunityUserParticipantId());
        mOwnerPresenter.getCommunityOwnerDeactive(deactivateOwnerRequest);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismissAllowingStateLoss();//dismiss dialog on back button press
                if (finishParent) {
                    getActivity().finish();
                }
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
    public void showError(String s, int errorFor) {

    }



    @Override
    public void getityCommunityListSuccess(List<CommunityList> data) {

    }

    @Override
    public void getOwnerListSuccess(List<Member> ownerListResponse) {

    }

    @Override
    public void postCreateCommunitySuccess(CreateCommunityResponse createCommunityResponse) {

    }

    @Override
    public void getOwnerListDeactivateSuccess(DeactivateOwnerResponse deactivateOwnerResp)
    {
        Toast.makeText(getActivity(),deactivateOwnerResp.getStatus(),Toast.LENGTH_LONG).show();
        getDialog().cancel();

    }

    @Override
    public void postCreateCommunityOwnerSuccess(CreateCommunityOwnerResponse createCommunityOwnerResponse) {

    }

    @Override
    public void showNwError() {
        getDialog().cancel();

    }

    /* @OnClick(R.id.tvcancel)
     public void cancelClick()
     {
         getDialog().cancel();
         // mHomeActivityIntractionListner.onClose();
     }
     @OnClick(R.id.tv_continue)
     public void continueClick()
     {
         getDialog().cancel();
     }
     public interface CloseListener {
         void onErrorOccurence();
         void onClose();
     }*/
    public interface CloseListener {
        void onErrorOccurence();
        void onClose();
    }
}
