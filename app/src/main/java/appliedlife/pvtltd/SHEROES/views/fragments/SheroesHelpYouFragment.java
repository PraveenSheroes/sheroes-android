package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobLocationList;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.can_help;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.OnboardingActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Ajit Kumar on 22-02-2017.
 */

public class SheroesHelpYouFragment extends BaseFragment {
    @Bind(R.id.rv_how_can_sheroes_help_list)
    RecyclerView mRecyclerView;
    private GenericRecyclerViewAdapter mAdapter;
    List<can_help> listFeelter = new ArrayList<can_help>();

    private OnBoardingSheroesHelpActivityIntractionListner mOnboardingIntractionListner;
    private final String mTAG = LogUtils.makeLogTag(SheroesHelpYouFragment.class);
    View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof OnBoardingSheroesHelpActivityIntractionListner) {
                mOnboardingIntractionListner = (OnBoardingSheroesHelpActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(mTAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + mTAG + AppConstants.SPACE + exception.getMessage());
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        view = inflater.inflate(R.layout.how_can_sheroes_help_fragment, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (OnboardingActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        setFilterValues();
        mAdapter.setSheroesGenericListData(listFeelter);
        mAdapter.notifyDataSetChanged();

        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Fabric.with(getActivity(), new Crashlytics());
        return view;
    }
    public interface OnBoardingSheroesHelpActivityIntractionListner {
        void close();
        void onErrorOccurence();
        void callSheroesHelpYouTagPage();
    }
    private void setFilterValues()
    {
        can_help filterList=new can_help();
        filterList.setId("1");
        filterList.setName("HELP FOR A BUSINESS");
        filterList.setItem1("Grow my business");
        filterList.setItem2("Business Marketing");
        filterList.setItem3("Customer Review");
        filterList.setItem4("Balance Work & Life");
        filterList.setItem5("Child care tips");
        filterList.setItem6("Child care tips");
        filterList.setItem7("Child care tips");
        filterList.setItem8("Child care tips");
        filterList.setItem9("Child care tips");
        filterList.setItem10("Child care tips");
        filterList.setItem11("Child care tips");
        filterList.setItem12("tips");
        filterList.setItem13("care tips");
        filterList.setItem14("Child care tips");
        filterList.setItem15("Child");
        listFeelter.add(filterList);


    }

}
