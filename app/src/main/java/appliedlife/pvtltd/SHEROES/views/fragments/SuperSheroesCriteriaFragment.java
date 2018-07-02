package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LeaderBoardUserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ravi on 18/06/18
 * Description in bottom sheet that how to become the super sheroes
 */

public class SuperSheroesCriteriaFragment extends BottomSheetDialogFragment {

    private static final String SCREEN_LABEL = "SuperSHEROES Criteria Screen";
    private static final String LEADER_BOARD_DETAILS = "LeaderBoard_Details";
    private LeaderBoardUserSolrObj mLeaderBoardUserSolrObj;
    private String previousScreenName;

    @Bind(R.id.content)
    TextView contentText;

    @Inject
    Preference<Configuration> mConfiguration;

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
        SheroesApplication.getAppComponent(getActivity()).inject(this);

        ConfigData configData = new ConfigData();
        String howToBeSuperSheroesContent = configData.superSheroesCriteriaMsg;
        if (mConfiguration.isSet() && mConfiguration.get().configData.superSheroesCriteriaMsg != null) {
            howToBeSuperSheroesContent = mConfiguration.get().configData.superSheroesCriteriaMsg;
        }
        if(CommonUtil.isNotEmpty(howToBeSuperSheroesContent)) {
            contentText.setText(howToBeSuperSheroesContent);
        }

        if (getArguments()!=null ) {
            if(getArguments().getParcelable(LEADER_BOARD_DETAILS) != null) {
                Parcelable parcelable = getArguments().getParcelable(LEADER_BOARD_DETAILS);
                mLeaderBoardUserSolrObj = Parcels.unwrap(parcelable);
            }

            if(getArguments().getString(AppConstants.SCREEN_NAME) !=null) {
                previousScreenName = getArguments().getString(AppConstants.SCREEN_NAME);
            }
        }

        if(mLeaderBoardUserSolrObj!=null && previousScreenName!=null) {
            HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .communityId(String.valueOf(mLeaderBoardUserSolrObj.getSolrIgnoreBadgeDetails().getCommunityId()))
                            .build();
            AnalyticsManager.trackScreenView(SCREEN_LABEL, previousScreenName,  properties);
        }

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


    //region Public Static methods
    public static SuperSheroesCriteriaFragment showDialog(AppCompatActivity activity, LeaderBoardUserSolrObj leaderBoardUserSolrObj, String screenName) {
        SuperSheroesCriteriaFragment postBottomSheetFragment = new SuperSheroesCriteriaFragment();

        Bundle args = new Bundle();
        Parcelable parcelable = Parcels.wrap(leaderBoardUserSolrObj);
        args.putParcelable(LEADER_BOARD_DETAILS, parcelable);
        args.putString(AppConstants.SCREEN_NAME, screenName);
        postBottomSheetFragment.setArguments(args);
        postBottomSheetFragment.show(activity.getSupportFragmentManager(), SCREEN_LABEL);
        return postBottomSheetFragment;
    }
    //endregion
}
