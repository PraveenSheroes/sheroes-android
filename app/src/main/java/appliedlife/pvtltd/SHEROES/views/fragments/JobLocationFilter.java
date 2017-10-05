package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.FilterList;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobLocationList;
import appliedlife.pvtltd.SHEROES.presenters.CommentReactionPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.RangeSeekBar;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.JobFilterActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 13-02-2017.
 */

public class JobLocationFilter extends BaseFragment {
    private static final String SCREEN_LABEL = "Job Search Location Screen";
    private final String TAG = LogUtils.makeLogTag(JobFilterFragment.class);
    @Inject
    CommentReactionPresenter mCommentReactionPresenter;
    @Bind(R.id.rv_filter_list)
    RecyclerView mRecyclerView;

    @Bind(R.id.et_search_edit_text)
    EditText mEt_search_edit_text;

    @Bind(R.id.tv_save_job_filter_location)
    TextView mTv_save_job_filter_location;

    @Bind(R.id.tv_location_header)
    LinearLayout mLnrtv_location_header;

    ImageView mTv_back_btn;

    List<JobLocationList> listFeelter = new ArrayList<JobLocationList>();

    private GenericRecyclerViewAdapter mAdapter;
    private HomeActivityJobLocationIntractionListner mHomeActivityIntractionListner;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof HomeActivityJobLocationIntractionListner) {
                mHomeActivityIntractionListner = (HomeActivityJobLocationIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.joblocationfilter, container, false);
        ButterKnife.bind(this, view);


// Get noticed while dragging
        mEt_search_edit_text.setHint("Search Location");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (JobFilterActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        setFilterValues();

        mAdapter.setSheroesGenericListData(listFeelter);
        mAdapter.notifyDataSetChanged();
        mTv_back_btn=(ImageView) mLnrtv_location_header.findViewById(R.id.tv_back_btn);
        mTv_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeActivityIntractionListner.onClickSaveLocationList();

            }
        });
        return view;
    }

    @OnClick(R.id.tv_save_job_filter_location)
    public void saveClick()
    {
        mHomeActivityIntractionListner.onClickSaveLocationList();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setFilterValues()
    {
        JobLocationList filterList=new JobLocationList();
        filterList.setId("1");
        filterList.setName("Delhi India");
        listFeelter.add(filterList);

        JobLocationList filterList1=new JobLocationList();
        filterList1.setId("2");
        filterList1.setName("Mumbai India");
        listFeelter.add(filterList1);

        JobLocationList filterList2=new JobLocationList();
        filterList2.setId("3");
        filterList2.setName("Bangluru India");
        listFeelter.add(filterList2);

        JobLocationList filterList3=new JobLocationList();
        filterList3.setId("1");
        filterList3.setName("Delhi India");
        listFeelter.add(filterList3);

        JobLocationList filterList4=new JobLocationList();
        filterList4.setId("2");
        filterList4.setName("Mumbai India");
        listFeelter.add(filterList4);

        JobLocationList filterList5=new JobLocationList();
        filterList5.setId("3");
        filterList5.setName("Bangluru India");
        listFeelter.add(filterList5);



    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    public interface HomeActivityJobLocationIntractionListner {
        void onErrorOccurence();
        void onDialogDissmiss(FragmentOpen isFragmentOpen);
        void onClickSaveLocationList();
    }

}