package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 09-02-2017.
 */

public class OwnerRemoveDialog extends BaseDialogFragment {
    private boolean finishParent;
    private CloseListener mHomeActivityIntractionListner;
    private final String TAG = LogUtils.makeLogTag(SelectCommunityFragment.class);
    @Bind(R.id.tvcancel)
    TextView mTv_cancel;
    @Bind(R.id.tv_continue)
    TextView mTv_continue;

    public OwnerRemoveDialog(CommunitiesDetailActivity context) {
        try {
            if (context instanceof CloseListener) {
                mHomeActivityIntractionListner = (CloseListener)getActivity();
            }
        } catch (Fragment.InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.owner_remove_dialog, container, false);
        ButterKnife.bind(this, view);


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
        getDialog().cancel();
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
