package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.community.PopularTag;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.GetInterestJobResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.presenters.OnBoardingPresenter;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CreateCommunityActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustomeCollapsableToolBar.CustomCollapsingToolbarLayout;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.OnBoardingView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.COMMUNITY_OWNER;

/**
 * Created by priyanka on 26/03/17.
 */

public class ProfileGoodAtFragment extends BaseFragment implements BaseHolderInterface, ProfileView, OnBoardingView {

    @Inject
    ProfilePersenter mProfilePresenter;
    @Inject
    AppUtils mAppUtils;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    @Bind(R.id.rv_good_at_list)
    RecyclerView mRecyclerView;
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
    @Bind(R.id.view)
    View mVindecator;
    @Bind(R.id.view1)
    View mVindecator1;
    @Bind(R.id.view2)
    View mVindecator2;
    @Bind(R.id.tv_skill_submit)
    TextView mTvSkillSubmit;
    @Bind(R.id.et_search_edit_text_profile)
    EditText mEtSearchEditTextProfile;
    @Bind(R.id.pb_search_Skill_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.tv_no_of_skill)
    TextView mtv_no_of_skill;
    @Bind(R.id.tv_skill_text)
    TextView mtv_skill_text;
    @Bind(R.id.tv_skill_title)
    TextView mtv_skill_title;
    @Bind(R.id.ll_indecator)
    LinearLayout ll_indecator;
    @Bind(R.id.cl_profile_good_at)
    public CustomCollapsingToolbarLayout mCustomCollapsingToolbarLayout;


    String Skill1, Skill2;

    private List<PopularTag> listFeelter = new ArrayList<PopularTag>();
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    ArrayList<HashMap<String, String>> skillDetails = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> skillDetailsId = new ArrayList<HashMap<String, String>>();
    int mCount = 1;
    private Handler mHandler = new Handler();
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    String[] mSkills = new String[4];
    private FragmentListRefreshData mFragmentListRefreshData;
    private MyProfileyGoodAtListener mHomeActivityIntractionListner;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private final String mSkill = LogUtils.makeLogTag(CreateCommunityFragment.class);
    HashMap<String, HashMap<String, ArrayList<LabelValue>>> data = new HashMap<>();
    String [] skills=new String[4];
    long [] skillsid=new long[4];
    HashMap<String,Long> goodAtId=new HashMap<String,Long>();
    @Inject
    OnBoardingPresenter mOnBoardingPresenter;
    @Bind(R.id.tv_good_at_tittle)
    TextView mCollapeTitleTxt;
    @Bind(R.id.al_profile_good_at)
    AppBarLayout mAppBarLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mHomeActivityIntractionListner = (MyProfileyGoodAtListener) getActivity();
        } catch (ClassCastException exception) {
            LogUtils.error(mSkill, "Activity must implements MyProfileyGoodAtListener",exception);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);

        View v = inflater.inflate(R.layout.profile_good_at_fragment, container, false);
        ButterKnife.bind(this, v);
        mProfilePresenter.attachView(this);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.ALL_SEARCH, AppConstants.NO_REACTION_CONSTANT);
        mTvSkillSubmit.setVisibility(View.GONE);
        mOnBoardingPresenter.attachView(this);

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


        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            data = mUserPreferenceMasterData.get().getData();
            LogUtils.error("Master Data", data + "");
            HashMap<String, ArrayList<LabelValue>> hashMap = data.get(AppConstants.MASTER_DATA_SKILL_KEY);
            List<LabelValue> labelValueArrayList = hashMap.get(AppConstants.MASTER_DATA_DEFAULT_CATEGORY);
            PopularTag filterList = new PopularTag();
            filterList.setName("Popular");
            List<String> jobAtList = new ArrayList<>();

            if(null !=labelValueArrayList) {
                for (int i = 0; i < labelValueArrayList.size(); i++) {
                    String abc = labelValueArrayList.get(i).getLabel();
                    goodAtId.put(abc,labelValueArrayList.get(i).getValue());
                    jobAtList.add(abc);
                }
            }
            filterList.setBoardingDataList(jobAtList);
            listFeelter.add(filterList);

        }
        if (null != getArguments()) {

            if (null != getArguments().getStringArray(AppConstants.SKILL_LIST)) {
                skills =  getArguments().getStringArray(AppConstants.SKILL_LIST);
                String Skillval = "";
                for (int i = 1; i < skills.length; i++) {
                    if(StringUtil.isNotNullOrEmptyString(skills[i]))
                    setSkillValue(skills[i]);
                }
                Skillval = Skillval.replaceAll("null", "");
            }
            if (null != getArguments().getLongArray(AppConstants.SKILL_LIST_ID)) {
                skillsid =getArguments().getLongArray(AppConstants.SKILL_LIST_ID);
                String Skillval = "";
                for (int i = 1; i < skillsid.length; i++) {
                   // setSkillValue(skillDetailsId.get(i).get("skill"));
                }
                Skillval = Skillval.replaceAll("null", "");
            }
            // mEt_create_community_Skills.setText(Skillval);
        }
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setSheroesGenericListData(listFeelter);
        mAdapter.notifyDataSetChanged();
        mEtSearchEditTextProfile.setHint("Search Skill");
        mtv_no_of_skill.setVisibility(View.GONE);
        super.setInitializationForProfile(mFragmentListRefreshData, mAdapter, mLayoutManager, mRecyclerView, mAppUtils, mProgressBar);

        return v;
    }

    @OnClick(R.id.tv_back_good_at_skill)
    public void communitySkillBackClick() {
        getActivity().onBackPressed();
    }


    @OnClick(R.id.tv_skill_submit)
    public void SkillSubmitPress() {

        Set<Long> skillIds = new HashSet<>();
        for(int i=0;i<skillsid.length;i++)
        {
            if(skillsid[i]>0)
            {
                skillIds.add(skillsid[i]);

            }
        }

        mOnBoardingPresenter.getJobAtToPresenter(mAppUtils.boardingJobAtRequestBuilder(skillIds));

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {

    }



    @Override
    public void onBackPressed(int id) {

    }

    @Override
    public void visitingCardOpen(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {

    }

    @Override
    public void callFragment(int id) {

    }

    @Override
    public void getEducationResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getPersonalBasicDetailsResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getprofiletracelflexibilityResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getProfessionalBasicDetailsResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getProfessionalWorkLocationResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getProfileVisitingCardResponse(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {

    }

    @Override
    public void getUserData(UserProfileResponse userProfileResponse) {

    }

    @Override
    public void getProfileListSuccess(GetTagData getAllData) {

    }

    @Override
    public void getProfileListSuccess(List<Doc> feedDetailList) {
        mAdapter.setCallForRecycler(AppConstants.ALL_DATA_SUB_TYPE);
        mAdapter.setSheroesGenericListData(feedDetailList);
        mAdapter.notifyDataSetChanged();
        mtv_skill_text.setVisibility(View.GONE);
        mtv_skill_title.setVisibility(View.GONE);
        mtv_no_of_skill.setVisibility(View.GONE);
        mSkill1.setVisibility(View.GONE);
        mSkill2.setVisibility(View.GONE);
        mSkill3.setVisibility(View.GONE);
        mSkill4.setVisibility(View.GONE);
        mSkill5.setVisibility(View.GONE);
        mSkill6.setVisibility(View.GONE);
        ll_indecator.setVisibility(View.GONE);

    }


    @OnClick(R.id.et_search_edit_text_profile)

    public void SearchClickText() {


        ((ProfileActicity)getActivity()).callSearchGoodAtDialog();


    }



    public void setGoodAt(String[] tagsval, long[] tagsid) {
        skillsid = tagsid;
        StringBuilder stringBuilder = new StringBuilder();
        if (tagsval.length > 0) {

            for (int i = 1; i < tagsval.length; i++) {
                if (StringUtil.isNotNullOrEmptyString(tagsval[i]))
                    setSkillValue(tagsval[i]);
            }
        }
       // mEtSearchEditTextProfile.setText(stringBuilder.substring(0, stringBuilder.length() - 1));
    }


    @Override
    public void handleOnClick(BaseResponse sheroesListDataItem, View view) {
        TextView textView = (TextView) view.findViewById(R.id.tv_community_tag_data);
        // textView.setBackgroundResource(R.drawable.selected_Skill_shap);

        // getActivity().getFragmentManager().popBackStack();

        //   getDialog().cancel();
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setSheroesGenericListData(listFeelter);

        mAdapter.notifyDataSetChanged();

/*
        mtv_no_of_skill.setVisibility(View.VISIBLE);
*/
        if (StringUtil.isNotNullOrEmptyString(mSkill1.getText().toString())) {
            mSkill1.setVisibility(View.VISIBLE);
        }
        if (StringUtil.isNotNullOrEmptyString(mSkill2.getText().toString())) {
            mSkill2.setVisibility(View.VISIBLE);
        }
        if (StringUtil.isNotNullOrEmptyString(mSkill3.getText().toString())) {
            mSkill3.setVisibility(View.VISIBLE);
        }
        if (StringUtil.isNotNullOrEmptyString(mSkill4.getText().toString())) {
            mSkill4.setVisibility(View.VISIBLE);
        }
        if (StringUtil.isNotNullOrEmptyString(mSkill5.getText().toString())) {
            mSkill5.setVisibility(View.VISIBLE);
        }
        if (StringUtil.isNotNullOrEmptyString(mSkill6.getText().toString())) {
            mSkill6.setVisibility(View.VISIBLE);
        }
        if (StringUtil.isNotNullOrEmptyString(mSkill7.getText().toString())) {
            mSkill7.setVisibility(View.VISIBLE);
        }
        mtv_skill_text.setVisibility(View.VISIBLE);
        mtv_skill_title.setVisibility(View.VISIBLE);
        /*mtv_no_of_skill.setVisibility(View.VISIBLE);*/
        ll_indecator.setVisibility(View.VISIBLE);
        if (mCount <= 3) {
            if (sheroesListDataItem instanceof Doc) {
                mSkills[mCount] = ((Doc) sheroesListDataItem).getTitle();

            } else {
                mSkills[mCount] = textView.getText().toString();
                skillsid[mCount-1]=goodAtId.get(mSkills[mCount]);
            }
            if (mCount == 2) {

                if (mSkills[mCount].equals(mSkills[mCount - 1])) {
                    mCount = mCount - 1;
                    Toast.makeText(getActivity(), "Already Selected Please Select Another One", Toast.LENGTH_LONG).show();
                } else {
                    //  mVindecator1.setBackgroundColor((getResources().getColor(R.color.popular_Skill_color)));

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
                Skill1 = mSkills[mCount];
                Skill2 = mSkills[mCount - 2];

                if ((mSkills[mCount].equals(mSkills[mCount - 1])) || (Skill1.equals(Skill2))) {
                    mCount = mCount - 1;
                    Toast.makeText(getActivity(), "Already Selected Please Select Another One", Toast.LENGTH_LONG).show();
                } else {
                    // mVindecator2.setBackgroundColor((getResources().getColor(R.color.popular_Skill_color)));

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
                mTvSkillSubmit.setVisibility(View.VISIBLE);
                mSkill1.setVisibility(View.VISIBLE);
                //   mVindecator.setBackgroundColor((getResources().getColor(R.color.popular_Skill_color)));
                mSkill1.setText(mSkills[mCount]);

            }
            mCount++;
        }


    }

    private void setSkillValue(String value) {
        if (mCount <= 3) {
            mTvSkillSubmit.setVisibility(View.VISIBLE);

            mSkills[mCount] = value;


            if (mCount == 2) {

                if (mSkills[mCount].equals(mSkills[mCount - 1])) {
                    mCount = mCount - 1;
                    Toast.makeText(getActivity(), "Already Selected Please Select Another One", Toast.LENGTH_LONG).show();
                } else {
                    //  mVindecator1.setBackgroundColor((getResources().getColor(R.color.popular_Skill_color)));

                    if (mSkills[mCount - 1].length() > 20) {
                        mSkill4.setVisibility(View.VISIBLE);
                        mSkill4.setText(mSkills[mCount]);
                    } else if (mSkills[mCount - 1].length() < 20) {
                        if (mSkills[mCount].length() > 20) {
                            mSkill4.setVisibility(View.VISIBLE);

                            mSkill4.setText(mSkills[mCount]);
                        } else {
                            mSkill2.setVisibility(View.VISIBLE);

                            mSkill2.setText(mSkills[mCount]);
                        }
                    }
                }
            } else if (mCount == 3) {
                Skill1 = mSkills[mCount];
                Skill2 = mSkills[mCount - 2];

                if ((mSkills[mCount].equals(mSkills[mCount - 1])) || (Skill1.equals(Skill2))) {
                    mCount = mCount - 1;
                    Toast.makeText(getActivity(), "Already Selected Please Select Another One", Toast.LENGTH_LONG).show();
                } else {
                    // mVindecator2.setBackgroundColor((getResources().getColor(R.color.popular_Skill_color)));

                    if (mSkills[mCount - 1].length() + mSkills[mCount - 2].length() > 25) {
                        if (mSkill4.getText().equals("")) {
                            mSkill4.setVisibility(View.VISIBLE);

                            mSkill4.setText(mSkills[mCount]);
                        } else if (mSkills[mCount].length() > 20) {
                            mSkill7.setText(mSkills[mCount]);
                            mSkill7.setVisibility(View.VISIBLE);

                        } else {
                            if ((mSkills[mCount - 1].length() > 20)) {
                                mSkill7.setText(mSkills[mCount]);
                                mSkill7.setVisibility(View.VISIBLE);
                            } else {
                                mSkill5.setVisibility(View.VISIBLE);

                                mSkill5.setText(mSkills[mCount]);
                            }
                        }

                    } else {
                        if (mSkills[mCount].length() > 20) {
                            mSkill4.setText(mSkills[mCount]);
                            mSkill4.setVisibility(View.VISIBLE);

                        } else {
                            mSkill3.setVisibility(View.VISIBLE);

                            mSkill3.setText(mSkills[mCount]);
                        }
                    }
                }
            } else if (mCount == 1) {
                mTvSkillSubmit.setVisibility(View.VISIBLE);
                mSkill1.setVisibility(View.VISIBLE);
                //   mVindecator.setBackgroundColor((getResources().getColor(R.color.popular_Skill_color)));
                mSkill1.setText(mSkills[mCount]);

            }
            mCount++;

        }
    }

    private TextWatcher dataSearchTextWatcher() {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable inputSearch) {
                /**As soon as user starts typing take the scroll to top **/
             /*   mSearchDataName = inputSearch.toString();
                if (!((HomeSearchActivity) getActivity()).mIsDestroyed) {
                    mAdapter.getFilter().filter(mSearchDataName);
                }*/

                if (StringUtil.isNotNullOrEmptyString(inputSearch.toString()) && inputSearch.toString().length() > AppConstants.THREE_CONSTANT) {
                    mSearchDataName = inputSearch.toString();
                    /**hitting the servers to get data if length is greater than threshold defined **/
                    mHandler.removeCallbacks(mFilterTask);
                    mHandler.postDelayed(mFilterTask, AppConstants.SEARCH_CONSTANT_DELAY);
                }
            }
        };
    }

    Runnable mFilterTask = new Runnable() {
        @Override
        public void run() {
            if (!isDetached()) {
                // mSearchDataName = mSearchDataName.trim().replaceAll(AppConstants.SPACE, AppConstants.EMPTY_STRING);
                mProfilePresenter.getSkillFromPresenter(mAppUtils.getAllDataRequestBuilder(AppConstants.SKILL_SUB_TYPE, mSearchDataName, AppConstants.ALL_SEARCH));
            }
        }
    };

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {

    }

    @Override
    public void setListData(BaseResponse data, boolean flag) {

    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {

    }


    @Override
    public void startActivityFromHolder(Intent intent) {

    }

    @Override
    public List getListData() {
        return null;
    }


    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void startNextScreen() {

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
            case AppConstants.SUCCESS:
                ((ProfileActicity)getActivity()).getSupportFragmentManager().popBackStack();
                break;
            case AppConstants.FAILED:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(boardingDataResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), COMMUNITY_OWNER);
                break;
            default:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(AppConstants.HTTP_401_UNAUTHORIZED, COMMUNITY_OWNER);
        }

    }


    public interface MyProfileyGoodAtListener {

        void onErrorOccurence();

        void OnSearchClick();


    }

}
