package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.GetInterestJobResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GoodAt;
import appliedlife.pvtltd.SHEROES.presenters.OnBoardingPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.OnBoardingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.COMMUNITY_OWNER;

/**
 * Created by priyanka on 26/03/17.
 */

public class ProfileOpportunityTypeFragment extends BaseFragment implements BaseHolderInterface, OnBoardingView {
    private static final String SCREEN_LABEL = "Profile Opportunity Screen";
    private static final String TAG = "ProfileOpportunityTypeFragment";

    @Inject
    OnBoardingPresenter mOnBoardingPresenter;
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
    @Bind(R.id.btn_save_about_me_details)
    Button mbtnSaveAboutMe;
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.pb_profile_progress_bar)
    ProgressBar pb_profile_progress_bar;
    String skill1, skill2;
    int mCount = 1,mNcount=0;
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    String[] mSkills = new String[4];
    long[] mSkillsId = new long[4];
    private GenericRecyclerViewAdapter mAdapter;
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    HashMap<String, HashMap<String, ArrayList<LabelValue>>> data = new HashMap<>();
    private List<GoodAt> listFeelter = new ArrayList<GoodAt>();
    List<String> jobAtList = new ArrayList<>();
    private HashMap<String,Long> oppertunity=new HashMap<String,Long>();
    long optid;
    private OppertunitiesCallback mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OppertunitiesCallback) context;
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, "Activity must implements OppertunitiesCallback", exception);
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

        mOnBoardingPresenter.attachView(this);
        setProgressBar(pb_profile_progress_bar);
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
    @OnClick(R.id.btn_save_about_me_details)
    public void saveAboutMe()
    {
        Set<Long> skillIds = new HashSet<>();
        for(int i=0;i<mSkillsId.length;i++)
        {
            if(mSkillsId[i]>0)
            {
                skillIds.add(mSkillsId[i]);
            }
        }
        mOnBoardingPresenter.getLookingForHowCanToPresenter(mAppUtils.profileOpertunityTypeRequestBuilder(skillIds,AppConstants.LOOKING_FOR_HOW_CAN,AppConstants.LOOKING_FOR_HOW_CAN_TYPE));

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

    @Override
    public void championProfile(BaseResponse baseResponse, int championValue) {

    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {

    }


    @OnClick(R.id.tv_looking_back)
    public void OnclickLookingBack() {
        getActivity().onBackPressed();

    }

    @Override
    public void getAllDataResponse(GetAllData getAllData) {

    }

    @Override
    public void getIntersetJobResponse(GetInterestJobResponse getInterestJobResponse) {

    }

    @Override
    public void getBoardingJobResponse(BoardingDataResponse boardingDataResponse) {
        switch (boardingDataResponse.getStatus()) {
            case AppConstants.SUCCESS: {
                mCallback.onSaveSuccess();
                break;
            }
            case AppConstants.FAILED: {
                mCallback.onShowErrorDialog(boardingDataResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), COMMUNITY_OWNER);
                break;
            }
            default: {
                mCallback.onShowErrorDialog(AppConstants.HTTP_401_UNAUTHORIZED, COMMUNITY_OWNER);
                break;
            }
        }
    }

    @Override
    public void showDataList(List<FeedDetail> feedDetailList) {

    }

    @Override
    public void joinResponse(CommunityFeedSolrObj communityFeedSolrObj) {

    }

    @Override
    public void unJoinResponse(CommunityFeedSolrObj communityFeedSolrObj) {

    }


    @OnClick(R.id.tv_selected_skill1)
    void onTag1Click() {
        mSkill1.setText("");
        mCount--;
        if (StringUtil.isNotNullOrEmptyString(mSkill3.getText().toString())) {
            mSkillsId[mNcount]=0;
            mNcount++;
            mSkill1.setText(mSkill2.getText());
            mSkill2.setText(mSkill3.getText());
            mSkill1.setVisibility(View.VISIBLE);
            mSkill2.setVisibility(View.VISIBLE);
            mSkill3.setVisibility(View.GONE);

        }
        else if (StringUtil.isNotNullOrEmptyString(mSkill2.getText().toString())) {
            mSkillsId[mNcount]=0;
            mNcount++;
            mSkill1.setText(mSkill2.getText());
            mSkill2.setVisibility(View.GONE);

        }
         else {
            mSkillsId[mNcount]=0;
            mNcount++;
            mSkill1.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.tv_selected_skill2)
    void onTag2Click() {
        mSkill2.setText("");
        mCount--;
        if (StringUtil.isNotNullOrEmptyString(mSkill3.getText().toString())) {
            mSkill2.setText(mSkill3.getText());
            mSkill3.setVisibility(View.GONE);
            mSkillsId[mCount]=0;
        } else
            mSkill2.setVisibility(View.GONE);


    }
    @OnClick(R.id.tv_selected_skill3)
    void onTag3Click() {
        mSkill3.setText("");
        mCount--;
        mSkill3.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_selected_skill4)
    void onTag4Click() {
        mSkill4.setText("");
        mCount--;

        if (StringUtil.isNotNullOrEmptyString(mSkill5.getText().toString())) {
            mSkill4.setText(mSkill5.getText());
            mSkill5.setVisibility(View.GONE);
        }
        if (StringUtil.isNotNullOrEmptyString(mSkill6.getText().toString())) {
            mSkill5.setText(mSkill6.getText());
            mSkill5.setVisibility(View.VISIBLE);
            mSkill6.setVisibility(View.GONE);
        } else
            mSkill4.setVisibility(View.GONE);


    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    public interface ProfileOpportunityTypeListiner {
     void OnLookinBack();


    }

    public void setProfileOpportunityTypeText(String id,String value)
    {
        mbtnSaveAboutMe.setEnabled(true);
        mbtnSaveAboutMe.setBackgroundColor(getResources().getColor(R.color.red));

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
                mbtnSaveAboutMe.setEnabled(true);
                mSkill1.setVisibility(View.VISIBLE);
                //   mVindecator.setBackgroundColor((getResources().getColor(R.color.popular_tag_color)));
                mSkill1.setText(mSkills[mCount]);

            }
            mCount++;
        }
    }

    public interface OppertunitiesCallback{
        void onSaveSuccess();
        void onShowErrorDialog(String errorReason,FeedParticipationEnum feedParticipationEnum);
    }
}
