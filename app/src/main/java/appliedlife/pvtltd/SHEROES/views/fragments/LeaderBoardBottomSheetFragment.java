package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ravi on 18/06/18
 */

public class LeaderBoardBottomSheetFragment extends BottomSheetDialogFragment {
    private static final String SCREEN_LABEL = "LeaderBoardBottomSheetFragment";

    // endregion

    //region Fragment LifeCycle Methods
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(getArguments()!=null){
            if(getDialog()!=null){
                getDialog().dismiss();
            }
        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View containerView = View.inflate(getContext(), R.layout.leaderboad_about_bottomsheet, null);
        dialog.setContentView(containerView);
        ButterKnife.bind(this, containerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    //endregion

    //region private methods
    //endregion

    //region Public Static methods
    public static LeaderBoardBottomSheetFragment showDialog(AppCompatActivity activity) {
        LeaderBoardBottomSheetFragment postBottomSheetFragment = new LeaderBoardBottomSheetFragment();
        postBottomSheetFragment.show(activity.getSupportFragmentManager(), SCREEN_LABEL);
        return postBottomSheetFragment;
    }
    //endregion
}
