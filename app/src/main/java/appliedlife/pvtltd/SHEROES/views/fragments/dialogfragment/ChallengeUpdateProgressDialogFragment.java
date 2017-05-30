package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeDataItem;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 24-05-2017.
 */

public class ChallengeUpdateProgressDialogFragment extends BaseDialogFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(ChallengeUpdateProgressDialogFragment.class);
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.checkbox_just_started)
    ImageView checkBoxJustStarted;
    @Bind(R.id.checkbox_half_done)
    ImageView checkBoxHalfDone;
    @Bind(R.id.checkbox_almost_there)
    ImageView checkBoxAlmostThere;
    @Bind(R.id.checkbox_completed)
    ImageView checkBoxCompleted;
    Context mContext;
    ChallengeDataItem mChallengeDataItem;
    boolean isHalfDone, isAlmostDone, isComplete;
    @Bind(R.id.li_half_done)
    LinearLayout liHalfDone;
    @Bind(R.id.li_almost_done)
    LinearLayout liAlmostDone;
    @Bind(R.id.li_completed)
    LinearLayout liCompleted;
    int position;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.challenge_update_progress, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (null != getArguments()) {
            Bundle challenge = getArguments();
            mChallengeDataItem = challenge.getParcelable(AppConstants.CHALLENGE_SUB_TYPE);
        }
        ButterKnife.bind(this, view);
        checkBoxJustStarted.setBackgroundResource(R.drawable.ic_challenge_update_checked);
        checkBoxJustStarted.setAlpha(0.5f);
        checkBoxHalfDone.setTag(AppConstants.NO_REACTION_CONSTANT);
        checkBoxAlmostThere.setTag(AppConstants.NO_REACTION_CONSTANT);
        checkBoxCompleted.setTag(AppConstants.NO_REACTION_CONSTANT);
        if (mChallengeDataItem.getCompletionPercent() == AppConstants.HALF_DONE) {
            isHalfDone = true;
            checkBoxHalfDone.setBackgroundResource(R.drawable.ic_challenge_update_checked);
            checkBoxHalfDone.setAlpha(0.5f);
            checkBoxHalfDone.setEnabled(false);
            checkBoxHalfDone.setTag(AppConstants.ONE_CONSTANT);
            liHalfDone.setEnabled(false);
            position = 1;
        } else if (mChallengeDataItem.getCompletionPercent() == AppConstants.ALMOST_DONE) {
            isAlmostDone = true;
            checkBoxAlmostThere.setBackgroundResource(R.drawable.ic_challenge_update_checked);
            checkBoxAlmostThere.setAlpha(0.5f);
            checkBoxAlmostThere.setEnabled(false);
            checkBoxAlmostThere.setTag(AppConstants.ONE_CONSTANT);
            liAlmostDone.setEnabled(false);


            isHalfDone = true;
            checkBoxHalfDone.setBackgroundResource(R.drawable.ic_challenge_update_checked);
            checkBoxHalfDone.setAlpha(0.5f);
            checkBoxHalfDone.setEnabled(false);
            checkBoxHalfDone.setTag(AppConstants.ONE_CONSTANT);
            liHalfDone.setEnabled(false);
            position = 2;
        } else if (mChallengeDataItem.getCompletionPercent() == AppConstants.COMPLETE) {
            isComplete = true;
            checkBoxCompleted.setBackgroundResource(R.drawable.ic_challenge_update_checked);
            checkBoxCompleted.setAlpha(0.5f);
            checkBoxCompleted.setEnabled(false);
            checkBoxCompleted.setTag(AppConstants.ONE_CONSTANT);
            liCompleted.setEnabled(false);

            isAlmostDone = true;
            checkBoxAlmostThere.setBackgroundResource(R.drawable.ic_challenge_update_checked);
            checkBoxAlmostThere.setAlpha(0.5f);
            checkBoxAlmostThere.setEnabled(false);
            checkBoxAlmostThere.setTag(AppConstants.ONE_CONSTANT);
            liAlmostDone.setEnabled(false);


            isHalfDone = true;
            checkBoxHalfDone.setBackgroundResource(R.drawable.ic_challenge_update_checked);
            checkBoxHalfDone.setAlpha(0.5f);
            checkBoxHalfDone.setEnabled(false);
            checkBoxHalfDone.setTag(AppConstants.ONE_CONSTANT);
            liHalfDone.setEnabled(false);
            position = 3;
        }
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismissAllowingStateLoss();//dismiss dialog on back button press
                dismiss();
            }
        };
    }

    @OnClick(R.id.tv_update_progress_after_status)
    public void onUpdateStatusClick() {
        if ((int) checkBoxHalfDone.getTag() == AppConstants.TWO_CONSTANT) {
            ((HomeActivity) getActivity()).updateChallengeDataWithStatus(mChallengeDataItem, AppConstants.HALF_DONE, AppConstants.EMPTY_STRING, AppConstants.EMPTY_STRING);
        } else if ((int) checkBoxAlmostThere.getTag() == AppConstants.TWO_CONSTANT) {
            ((HomeActivity) getActivity()).updateChallengeDataWithStatus(mChallengeDataItem, AppConstants.ALMOST_DONE, AppConstants.EMPTY_STRING, AppConstants.EMPTY_STRING);
        } else if ((int) checkBoxCompleted.getTag() == AppConstants.TWO_CONSTANT) {
            ((HomeActivity) getActivity()).updateChallengeDataWithStatus(mChallengeDataItem, AppConstants.COMPLETE, AppConstants.EMPTY_STRING, AppConstants.EMPTY_STRING);
        }
        dismiss();
    }


    @OnClick(R.id.li_half_done)
    public void onCheckHalfDoneClick() {
        if (isHalfDone) {
            isHalfDone = false;
            checkBoxHalfDone.setBackgroundResource(R.drawable.ic_challenge_update_unchecked);
            checkBoxHalfDone.setTag(AppConstants.NO_REACTION_CONSTANT);
        } else {
            isHalfDone = true;
            checkBoxHalfDone.setBackgroundResource(R.drawable.ic_challenge_update_checked);
            checkBoxHalfDone.setTag(AppConstants.TWO_CONSTANT);
            checkBoxAlmostThere.setTag(AppConstants.NO_REACTION_CONSTANT);
            checkBoxCompleted.setTag(AppConstants.NO_REACTION_CONSTANT);
        }
        isAlmostDone = false;
        isComplete = false;
        checkBoxAlmostThere.setBackgroundResource(R.drawable.ic_challenge_update_unchecked);
        checkBoxCompleted.setBackgroundResource(R.drawable.ic_challenge_update_unchecked);
        if (position == 1) {
            checkBoxHalfDone.setBackgroundResource(R.drawable.ic_challenge_update_checked);
        } else if (position == 2) {
            checkBoxAlmostThere.setBackgroundResource(R.drawable.ic_challenge_update_checked);
        } else if (position == 3) {
            checkBoxCompleted.setBackgroundResource(R.drawable.ic_challenge_update_checked);
        }
    }

    @OnClick(R.id.li_almost_done)
    public void onCheckAlmostThereClick() {
        if (isAlmostDone) {
            isAlmostDone = false;
            checkBoxAlmostThere.setBackgroundResource(R.drawable.ic_challenge_update_unchecked);
            checkBoxAlmostThere.setTag(AppConstants.NO_REACTION_CONSTANT);

        } else {
            isAlmostDone = true;
            checkBoxAlmostThere.setBackgroundResource(R.drawable.ic_challenge_update_checked);
            checkBoxAlmostThere.setTag(AppConstants.TWO_CONSTANT);
            checkBoxHalfDone.setTag(AppConstants.NO_REACTION_CONSTANT);
            checkBoxCompleted.setTag(AppConstants.NO_REACTION_CONSTANT);
        }
        isHalfDone = false;
        isComplete = false;
        checkBoxHalfDone.setBackgroundResource(R.drawable.ic_challenge_update_unchecked);
        checkBoxCompleted.setBackgroundResource(R.drawable.ic_challenge_update_unchecked);
        if (position == 1) {
            checkBoxHalfDone.setBackgroundResource(R.drawable.ic_challenge_update_checked);
        } else if (position == 2) {
            checkBoxHalfDone.setBackgroundResource(R.drawable.ic_challenge_update_checked);
            checkBoxAlmostThere.setBackgroundResource(R.drawable.ic_challenge_update_checked);
        } else if (position == 3) {
            checkBoxCompleted.setBackgroundResource(R.drawable.ic_challenge_update_checked);
        }
    }

    @OnClick(R.id.li_completed)
    public void onCheckCompletedClick() {
        if (isComplete) {
            isComplete = false;
            checkBoxCompleted.setBackgroundResource(R.drawable.ic_challenge_update_unchecked);
            checkBoxCompleted.setTag(AppConstants.NO_REACTION_CONSTANT);
        } else {
            isComplete = true;
            checkBoxCompleted.setBackgroundResource(R.drawable.ic_challenge_update_checked);
            checkBoxCompleted.setTag(AppConstants.TWO_CONSTANT);
            checkBoxHalfDone.setTag(AppConstants.NO_REACTION_CONSTANT);
            checkBoxAlmostThere.setTag(AppConstants.NO_REACTION_CONSTANT);
        }
        isHalfDone = false;
        isAlmostDone = false;
        checkBoxHalfDone.setBackgroundResource(R.drawable.ic_challenge_update_unchecked);
        checkBoxAlmostThere.setBackgroundResource(R.drawable.ic_challenge_update_unchecked);
        if (position == 1) {
            checkBoxHalfDone.setBackgroundResource(R.drawable.ic_challenge_update_checked);
        } else if (position == 2) {
            checkBoxHalfDone.setBackgroundResource(R.drawable.ic_challenge_update_checked);
            checkBoxAlmostThere.setBackgroundResource(R.drawable.ic_challenge_update_checked);
        } else if (position == 3) {
            checkBoxHalfDone.setBackgroundResource(R.drawable.ic_challenge_update_checked);
            checkBoxAlmostThere.setBackgroundResource(R.drawable.ic_challenge_update_checked);
            checkBoxCompleted.setBackgroundResource(R.drawable.ic_challenge_update_checked);
        }
    }

}
