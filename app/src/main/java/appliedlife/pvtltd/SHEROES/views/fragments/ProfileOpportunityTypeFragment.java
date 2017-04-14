package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GoodAt;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 26/03/17.
 */

public class ProfileOpportunityTypeFragment extends BaseFragment implements BaseHolderInterface {


    @Bind(R.id.rv_how_can_sheroes_help_list)
    RecyclerView mRecyclerView;
    @Inject
    Preference<MasterDataResponse> mUserPreference;
    @Bind(R.id.tv_selected_skill1)
    TextView mSkill1;
    @Bind(R.id.tv_selected_skill2)
    TextView mSkill2;
    @Bind(R.id.tv_selected_skill3)
    TextView mSkill3;
    @Bind(R.id.tv_selected_skill4)
    TextView mSkill4;
    @Bind(R.id.tv_selected_skill5)
    TextView mSkill5;
    @Bind(R.id.tv_selected_skill6)
    TextView mSkill6;
    @Bind(R.id.tv_selected_skill7)
    TextView mSkill7;
    @Bind(R.id.tv_about_me_tittle)
    TextView mCollapeTitleTxt;
    @Bind(R.id.al_community_open_about)
    AppBarLayout mAppBarLayout;
    String skill1, skill2;
    int mCount = 1;
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    String[] mSkills = new String[4];
    long[] mSkillsId = new long[4];
    private GenericRecyclerViewAdapter mAdapter;
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    HashMap<String, HashMap<String, ArrayList<LabelValue>>> data = new HashMap<>();
    private List<GoodAt> listFeelter = new ArrayList<GoodAt>();
    List<String> jobAtList = new ArrayList<>();
    private ProfileOpportunityTypeListiner profileOpportunityTypeListiner;
    private HashMap<String,Long> oppertunity=new HashMap<String,Long>();
    long optid;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof ProfileOpportunityTypeListiner) {


                profileOpportunityTypeListiner = (ProfileOpportunityTypeListiner) getActivity();
            }
        } catch (InstantiationException exception) {
            //LogUtils.error(mSkill, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + mSkill + AppConstants.SPACE + exception.getMessage());
        }
    }


    public void submitOppartunityType(String goodAtId, String goodAt)
    {

        setProfileOpportunityTypeText(""+oppertunity.get(goodAt),goodAt);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.profile_looking_for_fragment, container, false);
        ButterKnife.bind(this, view);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mCollapeTitleTxt.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    mCollapeTitleTxt.setVisibility(View.GONE);
                    isShow = false;
                }
            }
        });

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getData() ) {
            data= mUserPreference.get().getData();
            LogUtils.error("Master Data",data+"");
            HashMap<String, ArrayList<LabelValue>> hashMap=data.get(AppConstants.MASTER_DATA_OPPORTUNITY_KEY);
            List<LabelValue> labelValueArrayList = hashMap.get(AppConstants.MASTER_DATA_OPRTUNITY);
            GoodAt filterList = new GoodAt();
            filterList.setName("Popular Tag");
            List<String> jobAtList = new ArrayList<>();

            for(int i=0;i<labelValueArrayList.size();i++)
            {
                String abc=labelValueArrayList.get(i).getLabel();
                oppertunity.put(abc,labelValueArrayList.get(i).getValue());
                jobAtList.add(abc);
            }
            filterList.setBoardingDataList(jobAtList);
            listFeelter.add(filterList);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (ProfileActicity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setSheroesGenericListData(listFeelter);
        if (StringUtil.isNotEmptyCollection(setFilterValues())) {
            // mAdapter.setSheroesGenericListData(setFilterValues());
            // mAdapter.notifyDataSetChanged();
            // }
            mRecyclerView.addOnScrollListener(new HidingScrollListener(mRecyclerView, manager, new FragmentListRefreshData()) {
                @Override
                public void onHide() {
                    // ((ProfileActicity) getActivity()).mLiStripForAddItem.setVisibility(View.VISIBLE);
                }

                @Override
                public void onShow() {
                    //((OnBoardingActivity) getActivity()).mLiStripForAddItem.setVisibility(View.GONE);

                }

                @Override
                public void dismissReactions() {

                }
            });


        }
        return view;

    }
    private List<OnBoardingData> setFilterValues() {

        if (null != mMasterDataResult && null != mMasterDataResult.get(AppConstants.MASTER_DATA_OPPORTUNITY_KEY)) {
            {
                HashMap<String, ArrayList<LabelValue>> hashMap = mMasterDataResult.get(AppConstants.MASTER_DATA_OPPORTUNITY_KEY);

                List<OnBoardingData> listBoardingList = new ArrayList<>();
                Set<String> lookingForSet = hashMap.keySet();
                for (String lookingFor : lookingForSet) {
                    OnBoardingData boardingData = new OnBoardingData();
                    boardingData.setCategory(lookingFor);
                    boardingData.setBoardingDataList(hashMap.get(lookingFor));
                    listBoardingList.add(boardingData);
                }
                return listBoardingList;
            }
        }
        return null;
    }


    @Override
    public void startActivityFromHolder(Intent intent) {

    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {

    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {

    }

    @Override
    public void setListData(BaseResponse data, boolean flag) {

    }

    @Override
    public List getListData() {
        return null;
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {

    }





    @OnClick(R.id.tv_looking_back)
    public void OnclickLookingBack()
    {

        profileOpportunityTypeListiner.OnLookinBack();

    }


    public interface ProfileOpportunityTypeListiner {
     void OnLookinBack();


    }

    public void setProfileOpportunityTypeText(String id,String value)
    {
        if (mCount <= 3) {
                String skill = mSkills[mCount] = value;
                long skillId = mSkillsId[mCount] =Long.parseLong(id);


            if (mCount == 2) {

                if (mSkills[mCount].equals(mSkills[mCount - 1])) {
                    mCount = mCount - 1;
                    Toast.makeText(getActivity(), "Already Selected Please Select Another One", Toast.LENGTH_LONG).show();
                } else {
                    //  mVindecator1.setBackgroundColor((getResources().getColor(R.color.popular_tag_color)));

                    if (mSkills[mCount - 1].length() > 25) {
                        mSkill4.setVisibility(View.VISIBLE);
                        mSkill4.setText(mSkills[mCount]);
                    } else if (mSkills[mCount - 1].length() < 25) {
                        if (mSkills[mCount].length() > 25) {
                            mSkill4.setVisibility(View.VISIBLE);

                            mSkill4.setText(mSkills[mCount]);
                        } else {
                            mSkill2.setVisibility(View.VISIBLE);

                            mSkill2.setText(mSkills[mCount]);
                        }
                    }
                }
            } else if (mCount == 3) {
                skill1 = mSkills[mCount];
                skill2 = mSkills[mCount - 2];

                if ((mSkills[mCount].equals(mSkills[mCount - 1])) || (skill1.equals(skill2))) {
                    mCount = mCount - 1;
                    Toast.makeText(getActivity(), "Already Selected Please Select Another One", Toast.LENGTH_LONG).show();
                } else {
                    // mVindecator2.setBackgroundColor((getResources().getColor(R.color.popular_tag_color)));

                    if (mSkills[mCount - 1].length() + mSkills[mCount - 2].length() > 30) {
                        if (mSkill4.getText().equals("")) {
                            mSkill4.setVisibility(View.VISIBLE);

                            mSkill4.setText(mSkills[mCount]);
                        } else if (mSkills[mCount].length() > 25) {
                            mSkill7.setText(mSkills[mCount]);
                            mSkill7.setVisibility(View.VISIBLE);

                        } else {
                            if ((mSkills[mCount - 1].length() > 25)) {
                                mSkill7.setText(mSkills[mCount]);
                                mSkill7.setVisibility(View.VISIBLE);
                            } else {
                                mSkill5.setVisibility(View.VISIBLE);

                                mSkill5.setText(mSkills[mCount]);
                            }
                        }

                    } else {
                        if (mSkills[mCount].length() > 25) {
                            mSkill4.setText(mSkills[mCount]);
                            mSkill4.setVisibility(View.VISIBLE);

                        } else {
                            mSkill3.setVisibility(View.VISIBLE);

                            mSkill3.setText(mSkills[mCount]);
                        }
                    }
                }
            } else if (mCount == 1) {
                mSkill1.setVisibility(View.VISIBLE);
                //   mVindecator.setBackgroundColor((getResources().getColor(R.color.popular_tag_color)));
                mSkill1.setText(mSkills[mCount]);

            }
            mCount++;
        }
    }
}
