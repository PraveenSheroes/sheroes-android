package appliedlife.pvtltd.SHEROES.views.fragments;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.community.PopularTag;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 02/04/17.
 */


public class SearchIAmInterestIn extends BaseFragment implements BaseHolderInterface ,ProfileView{
    @Inject
    ProfilePersenter mProfilePresenter;
    @Inject
    AppUtils mAppUtils;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    @Bind(R.id.rv_home_list)
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
    @Bind(R.id.pb_search_skill_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.tv_no_of_skill)
    TextView mTvNumberOfSkills;
    @Bind(R.id.tv_skill_text)
    TextView mTvSkillText;
    @Bind(R.id.tv_skill_title)
    TextView mTvSkilltitle;
    @Bind(R.id.ll_indecator)
    LinearLayout ll_indecator;
    String skill1, skill2;
    private List<PopularTag> listFeelter = new ArrayList<PopularTag>();
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    int mCount = 1;
    private Handler mHandler = new Handler();
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    String[] mSkills = new String[4];
    Long[] mSkillsId = new Long[4];
    private FragmentListRefreshData mFragmentListRefreshData;
    private MyProfileSearchGoodAtListener mHomeActivityIntractionListner;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private final String mTAG = LogUtils.makeLogTag(SearchGoodAt.class);
    HashMap<String, HashMap<String, ArrayList<LabelValue>>> data = new HashMap<>();
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayList<HashMap<String, Long>> skillDetails = new ArrayList<HashMap<String, Long>>();
    HashMap<String, Long> skiilMap = new HashMap<String, Long>();
    HashMap<String, String> skiilIdMap = new HashMap<String, String>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof MyProfileSearchGoodAtListener) {
                mHomeActivityIntractionListner = (MyProfileSearchGoodAtListener) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(mTAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + mTAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);

        View v = inflater.inflate(R.layout.profile_skill_list, container, false);
        ButterKnife.bind(this, v);
        mProfilePresenter.attachView(this);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.ALL_SEARCH, AppConstants.NO_REACTION_CONSTANT);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mEtSearchEditTextProfile.setHint("Search Interests");
        editTextWatcher();
        mTvNumberOfSkills.setVisibility(View.GONE);
        mTvSkillSubmit.setVisibility(View.GONE);
        super.setAllInitializationForFeeds(mFragmentListRefreshData, mAdapter, mLayoutManager, mRecyclerView, null, mAppUtils, mProgressBar);
        return v;
    }


    @OnClick(R.id.tv_back_skill_list)
    public void communityTagBackClick() {
        mHomeActivityIntractionListner.onBackPress();
    }

    @OnClick(R.id.tv_skill_submit)
    public void tagSubmitPress() {
        skillDetails.add(skiilMap);
       // skillDetails.add(skiilIdMap);
       // mHomeActivityIntractionListner.onGoodAtSubmit(skillDetails);

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        // List<FeedDetail> feedDetailList=feedResponsePojo.getFeedDetails();
        /*if (StringUtil.isNotEmptyCollection(feedDetailList) && mAdapter != null) {
            mAdapter.setCallForRecycler(AppConstants.FEED_SUB_TYPE);
            mAdapter.setSheroesGenericListData(feedDetailList);
            mAdapter.notifyDataSetChanged();
        }*/
    }





    @OnClick(R.id.et_search_edit_text_profile)

    public void SearchClickText() {


        mHomeActivityIntractionListner.openfrag();


    }


    @Override
    public void startActivityFromHolder(Intent intent) {

    }


    @Override
    public void handleOnClick(BaseResponse sheroesListDataItem, View view) {
        mRecyclerView.setVisibility(View.GONE);
        TextView textView = (TextView) view.findViewById(R.id.tv_community_tag_data);
        // textView.setBackgroundResource(R.drawable.selected_tag_shap);

        // getActivity().getFragmentManager().popBackStack();

        //   getDialog().cancel();


/*
        mTv_no_of_Skills.setVisibility(View.VISIBLE);
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
        mTvSkillText.setVisibility(View.VISIBLE);
        mTvSkilltitle.setVisibility(View.VISIBLE);
        /*mTv_no_of_Skills.setVisibility(View.VISIBLE);*/
        ll_indecator.setVisibility(View.VISIBLE);
        if (mCount <= 3) {
            if (sheroesListDataItem instanceof Doc) {
                String skill = mSkills[mCount] = ((Doc) sheroesListDataItem).getTitle();
                long skillId = mSkillsId[mCount] = ((Doc) sheroesListDataItem).getId();
                skiilMap.put(skill, skillId);
                skiilIdMap.put("skill", skill);

            }

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
                mTvSkillSubmit.setVisibility(View.VISIBLE);
                mSkill1.setVisibility(View.VISIBLE);
                //   mVindecator.setBackgroundColor((getResources().getColor(R.color.popular_tag_color)));
                mSkill1.setText(mSkills[mCount]);

            }
            mCount++;
        }


    }

    protected void editTextWatcher() {


        mEtSearchEditTextProfile.addTextChangedListener(dataSearchTextWatcher());
        mEtSearchEditTextProfile.setFocusableInTouchMode(true);
        mEtSearchEditTextProfile.requestFocus();


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
                mProfilePresenter.getSkillFromPresenter(mAppUtils.getAllDataRequestBuilder(AppConstants.INTEREST_SCREEN, mSearchDataName, AppConstants.ALL_SEARCH));
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
    public void backListener(int id) {

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

    }


    public interface MyProfileSearchGoodAtListener {

        void onErrorOccurence();

        void onGoodAtSubmit(ArrayList<HashMap<String, String>> skillDetails);

        void onBackPress();

        void openfrag();


    }
}