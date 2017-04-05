package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.FilterList;
import appliedlife.pvtltd.SHEROES.presenters.CommentReactionPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.RangeSeekBar;
import appliedlife.pvtltd.SHEROES.views.activities.JobFilterActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 10-02-2017.
 */

public class JobFilterFragment extends BaseFragment {
    private final String TAG = LogUtils.makeLogTag(JobFilterFragment.class);
    @Inject
    CommentReactionPresenter mCommentReactionPresenter;
    @Bind(R.id.rv_filter_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_community_title)
    TextView mTv_community_title;
    @Bind(R.id.tv_filter_exp)
    TextView tvExp;

    List<FilterList> listFeelter = new ArrayList<FilterList>();

    private GenericRecyclerViewAdapter mAdapter;
    private HomeActivityJobFilterIntractionListner mHomeActivityIntractionListner;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof HomeActivityJobFilterIntractionListner) {
                mHomeActivityIntractionListner = (HomeActivityJobFilterIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.filterfragment, container, false);
        ButterKnife.bind(this, view);
        RangeSeekBar<Integer> seekBar = (RangeSeekBar<Integer>)  view.findViewById(R.id.rangeSeekbar);
        seekBar.setRangeValues(0, 25);
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                //Now you have the minValue and maxValue of your RangeSeekbar
                LogUtils.info("Range",minValue+"-"+maxValue);
                tvExp.setText(minValue+"-"+maxValue+" Years");
               // Toast.makeText(getActivity(), minValue + "-" + maxValue, Toast.LENGTH_LONG).show();
            }
        });

// Get noticed while dragging
        seekBar.setNotifyWhileDragging(true);

        mTv_community_title.setText("FILTER");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (JobFilterActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        listFeelter = new ArrayList<FilterList>();
        setFilterValues();

        mAdapter.setSheroesGenericListData(listFeelter);
        mAdapter.notifyDataSetChanged();

        return view;
    }
    @OnClick(R.id.tv_save_job_filter)
    public void saveClick()
    {
        mHomeActivityIntractionListner.onBackPressJobFilter();

    }
    @OnClick(R.id.tv_close_community)
    public void backBtnClick()
    {
        mHomeActivityIntractionListner.onBackPressJobFilter();
    }

    @OnClick(R.id.tv_opportunity_type_lable)
    public void opportunityType()
    {
    }
    @OnClick(R.id.tv_loaction_label)
    public void locationJob()
    {
    }
    @OnClick(R.id.tv_functional_area_lable)
    public void functionalArea()
    {
    }
    @OnClick(R.id.tv_filter_exp_label)
    public void filterExperience()
    {
    }

    private void setFilterValues()
    {
        FilterList filterList=new FilterList();
        filterList.setId("1");
        filterList.setName("OPPORTUNITY TYPE");
        filterList.setDescription("PART TIME-OFFICE JOBS, BUSINESS OPPORTUNITIES");
        listFeelter.add(filterList);

        FilterList filterList1=new FilterList();
        filterList1.setId("2");
        filterList1.setName("LOCATION");
        filterList1.setDescription("NEW DELHI, BANGLURU");
        listFeelter.add(filterList1);

        FilterList filterList2=new FilterList();
        filterList2.setId("3");
        filterList2.setName("FUNCTIONAL AREA");
        filterList2.setDescription("TECHNOLOGY, INTERNET");
        listFeelter.add(filterList2);



    }

    public interface HomeActivityJobFilterIntractionListner {
        void onErrorOccurence();
        void onBackPressJobFilter();
    }

}