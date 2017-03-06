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
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
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
    private final String TAG = LogUtils.makeLogTag(SheroesHelpYouFragment.class);
    @Bind(R.id.rv_how_can_sheroes_help_list)
    RecyclerView mRecyclerView;
    private GenericRecyclerViewAdapter mAdapter;
    List<OnBoardingData> listFeelter = new ArrayList<OnBoardingData>();

    private OnBoardingSheroesHelpActivityIntractionListner mOnboardingIntractionListner;
    View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof OnBoardingSheroesHelpActivityIntractionListner) {
                mOnboardingIntractionListner = (OnBoardingSheroesHelpActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
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
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        OnBoardingData filterList=new OnBoardingData();
        filterList.setName("JobAt");
        List<String> jobAtList=new ArrayList<>();
        jobAtList.add("Proffestional carrier");
        jobAtList.add("Contact data");
        jobAtList.add("Typing data");
        filterList.setBoardingDataList(jobAtList);

        OnBoardingData filterList1=new OnBoardingData();
        filterList1.setName("Contact Info");
        List<String> jobAtList1=new ArrayList<>();
        jobAtList1.add("Proffestional");
        jobAtList1.add("Second data planning");
        jobAtList1.add("Finding data");
        filterList.setBoardingDataList(jobAtList1);


        listFeelter.add(filterList);


    }

}
