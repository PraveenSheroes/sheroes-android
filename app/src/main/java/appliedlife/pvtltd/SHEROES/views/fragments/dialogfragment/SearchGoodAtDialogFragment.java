package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
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

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.community.PopularTag;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityTagsView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 26/03/17.
 */

public class SearchGoodAtDialogFragment extends BaseDialogFragment implements CommunityTagsView, BaseHolderInterface, HomeView, ProfileView {
    private static final String SCREEN_LABEL = "Search Good At Screen";
    @Inject
    ProfilePersenter mprofilePresenter;
    @Inject
    AppUtils mAppUtils;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    @Bind(R.id.rv_home_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_selected_skill1)
    TextView mTag1;
    @Bind(R.id.tv_selected_skill2)
    TextView mTag2;
    @Bind(R.id.tv_selected_skill3)
    TextView mTag3;
    @Bind(R.id.tv_selected_skill4)
    TextView mTag4;
    @Bind(R.id.tv_selected_skill5)
    TextView mTag5;
    @Bind(R.id.tv_selected_skill6)
    TextView mTag6;
    @Bind(R.id.tv_selected_skill7)
    TextView mTag7;
    @Bind(R.id.view)
    View mVindecator;
    @Bind(R.id.view1)
    View mVindecator1;
    @Bind(R.id.view2)
    View mVindecator2;
    @Bind(R.id.tv_skill_submit)
    TextView tv_community_tag_submit;
    @Bind(R.id.et_search_edit_text_profile)
    EditText mEt_search_edit_text;
    @Bind(R.id.pb_search_skill_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.tv_no_of_skill)
    TextView mTv_no_of_tags;
    @Bind(R.id.tv_skill_text)
    TextView mtv_tag_text;
    @Bind(R.id.tv_skill_title)
    TextView mtv_tag_title;
    @Bind(R.id.ll_indecator)
    LinearLayout ll_indecator;
    String tag1, tag2;
    private List<PopularTag> listFeelter = new ArrayList<PopularTag>();
    Map<String, String> myMap;
    int mCount = 1;
    private Handler mHandler = new Handler();

    private String mSearchDataName = AppConstants.EMPTY_STRING;
    String[] mTags = new String[4];

    long[] mTagsid = new long[4];

    private FragmentListRefreshData mFragmentListRefreshData;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    HashMap<String, HashMap<String, ArrayList<LabelValue>>> data = new HashMap<>();

    PopularTag filterList;
    FeedDetail mFeedDetail;
    String encImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View v = inflater.inflate(R.layout.profile_skill_list, container, false);
        ButterKnife.bind(this, v);
        mprofilePresenter.attachView(this);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.ALL_SEARCH, AppConstants.NO_REACTION_CONSTANT);

        if (null != getArguments()) {
            mFeedDetail = Parcels.unwrap(getArguments().getParcelable(AppConstants.COMMUNITIES_DETAIL));
            encImage = getArguments().getString(AppConstants.COVER_IMAGE);
        }
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
           // setMasterData(mUserPreferenceMasterData.get().getData());
        } else {
           // mprofilePresenter.getMasterDataToPresenter();
        }
        mEt_search_edit_text.setHint("Search Tags");
        editTextWatcher();
        mTv_no_of_tags.setVisibility(View.GONE);
        tv_community_tag_submit.setVisibility(View.GONE);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        AnalyticsManager.trackScreenView(SCREEN_LABEL);
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_MY_PROFILE_PROFESSIONAL_EDIT_GOOD_AT_SEARCH_AT));
        return v;
    }

    @OnClick(R.id.tv_back_skill_list)
    public void communityTagBackClick() {
    dismiss();
    }

    @OnClick(R.id.tv_skill_submit)

    public void tagSubmitPress() {

        ((ProfileActicity) getActivity()).onTagsSubmit(mTags, mTagsid);

        dismiss();
    }

    private void setMasterData(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {
        LogUtils.error("Master Data", data + "");
        HashMap<String, ArrayList<LabelValue>> hashMap = mapOfResult.get(AppConstants.MASTER_DATA_TAGS_KEY);
        List<LabelValue> labelValueArrayList = hashMap.get(AppConstants.MASTER_DATA_POPULAR_CATEGORY);
        filterList = new PopularTag();
        filterList.setName("Popular Tag");
        List<String> jobAtList = new ArrayList<>();
        myMap = new HashMap<>();
        if (StringUtil.isNotEmptyCollection(labelValueArrayList)) {
            for (int i = 0; i < labelValueArrayList.size(); i++) {
                String abc = labelValueArrayList.get(i).getLabel();
                String id = "" + labelValueArrayList.get(i).getValue();
                myMap.put(abc, id);
                jobAtList.add(abc);

                //jobAtList.add(id);
            }
            filterList.setBoardingDataList(jobAtList);
            listFeelter.add(filterList);
        }
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setSheroesGenericListData(listFeelter);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_selected_skill1)
    void onTag1Click() {
        mTag1.setText("");
        mCount--;
        if (StringUtil.isNotNullOrEmptyString(mTag2.getText().toString())) {
            mTag1.setText(mTag2.getText());
            mTag2.setVisibility(View.GONE);
            mTv_no_of_tags.setText("2/3");

            mVindecator1.setBackgroundColor((getResources().getColor(R.color.popular_tag_color)));
        }
        if (StringUtil.isNotNullOrEmptyString(mTag3.getText().toString())) {
            mTag1.setText(mTag1.getText());
            mTag2.setVisibility(View.VISIBLE);
            mTag3.setVisibility(View.GONE);
        } else
            mTag1.setVisibility(View.GONE);

    }

    @OnClick(R.id.tv_selected_skill2)
    void onTag2Click() {
        mTag2.setText("");
        mCount--;
        if (StringUtil.isNotNullOrEmptyString(mTag3.getText().toString())) {
            mTag2.setText(mTag3.getText());
            mTag3.setVisibility(View.GONE);
        } else
            mTag2.setVisibility(View.GONE);


    }

    @OnClick(R.id.tv_selected_skill3)
    void onTag3Click() {
        mTag3.setText("");
        mCount--;
        mTag3.setVisibility(View.GONE);


    }

    @OnClick(R.id.tv_selected_skill4)
    void onTag4Click() {
        mTag4.setText("");
        mCount--;

        if (StringUtil.isNotNullOrEmptyString(mTag5.getText().toString())) {
            mTag4.setText(mTag5.getText());
            mTag5.setVisibility(View.GONE);
        }
        if (StringUtil.isNotNullOrEmptyString(mTag6.getText().toString())) {
            mTag5.setText(mTag6.getText());
            mTag5.setVisibility(View.VISIBLE);
            mTag6.setVisibility(View.GONE);
        } else
            mTag4.setVisibility(View.GONE);


    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {
        setMasterData(mapOfResult);
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

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

    @Override
    public void getTagListSuccess(GetTagData getAllData) {
        List<Doc> tagDetailList=getAllData.getDocs();
        if(StringUtil.isNotEmptyCollection(tagDetailList)) {
            mAdapter.setCallForRecycler(AppConstants.ALL_DATA_SUB_TYPE);
            mAdapter.setSheroesGenericListData(tagDetailList);
            mAdapter.notifyDataSetChanged();
            mtv_tag_text.setVisibility(View.GONE);
            mtv_tag_title.setVisibility(View.GONE);
            mTv_no_of_tags.setVisibility(View.GONE);
            mTag1.setVisibility(View.GONE);
            mTag2.setVisibility(View.GONE);
            mTag3.setVisibility(View.GONE);
            mTag4.setVisibility(View.GONE);
            mTag5.setVisibility(View.GONE);
            mTag6.setVisibility(View.GONE);
            ll_indecator.setVisibility(View.GONE);
        }
    }

    @Override
    public void getSuccessForAllResponse(BaseResponse success, FeedParticipationEnum feedParticipationEnum) {

    }


    @Override
    public void startActivityFromHolder(Intent intent) {

    }
    @Override
    public void handleOnClick(BaseResponse sheroesListDataItem, View view) {
        TextView textView = (TextView) view.findViewById(R.id.tv_community_tag_data);
        // textView.setBackgroundResource(R.drawable.selected_tag_shap);

        // getActivity().getFragmentManager().popBackStack();

        //   getDialog().cancel();
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setSheroesGenericListData(listFeelter);

        mAdapter.notifyDataSetChanged();

        mTv_no_of_tags.setVisibility(View.VISIBLE);
        if (StringUtil.isNotNullOrEmptyString(mTag1.getText().toString())) {
            mTag1.setVisibility(View.VISIBLE);
        }
        if (StringUtil.isNotNullOrEmptyString(mTag2.getText().toString())) {
            mTag2.setVisibility(View.VISIBLE);
        }
        if (StringUtil.isNotNullOrEmptyString(mTag3.getText().toString())) {
            mTag3.setVisibility(View.VISIBLE);
        }
        if (StringUtil.isNotNullOrEmptyString(mTag4.getText().toString())) {
            mTag4.setVisibility(View.VISIBLE);
        }
        if (StringUtil.isNotNullOrEmptyString(mTag5.getText().toString())) {
            mTag5.setVisibility(View.VISIBLE);
        }
        if (StringUtil.isNotNullOrEmptyString(mTag6.getText().toString())) {
            mTag6.setVisibility(View.VISIBLE);
        }
        if (StringUtil.isNotNullOrEmptyString(mTag7.getText().toString())) {
            mTag7.setVisibility(View.VISIBLE);
        }
        mtv_tag_text.setVisibility(View.GONE);
        mtv_tag_title.setVisibility(View.GONE);
        mTv_no_of_tags.setVisibility(View.GONE);
        ll_indecator.setVisibility(View.GONE);
        if (mCount <= 3) {
            if (sheroesListDataItem instanceof Doc) {

                mTags[mCount] = ((Doc) sheroesListDataItem).getTitle();
                mTagsid[mCount] = ((Doc) sheroesListDataItem).getId();

            } else {
                mTags[mCount] = textView.getText().toString();
                mTagsid[mCount] = Long.parseLong(myMap.get(textView.getText()));
            }
            if (mCount == 2) {

                if (mTags[mCount].equals(mTags[mCount - 1])) {
                    mCount = mCount - 1;
                    Toast.makeText(getActivity(), "Already Selected Please Select Another One", Toast.LENGTH_LONG).show();
                } else {
                    mTv_no_of_tags.setText("2/3");

                    mVindecator1.setBackgroundColor((getResources().getColor(R.color.popular_tag_color)));

                    if (mTags[mCount - 1].length() > 25) {
                        mTag4.setVisibility(View.VISIBLE);
                        mTag4.setText(mTags[mCount]);
                    } else if (mTags[mCount - 1].length() < 25) {
                        if (mTags[mCount].length() > 25) {
                            mTag4.setVisibility(View.VISIBLE);

                            mTag4.setText(mTags[mCount]);
                        } else {
                            mTag2.setVisibility(View.VISIBLE);

                            mTag2.setText(mTags[mCount]);
                        }
                    }
                }
            } else if (mCount == 3) {
                tag1 = mTags[mCount];
                tag2 = mTags[mCount - 2];

                if ((mTags[mCount].equals(mTags[mCount - 1])) || (tag1.equals(tag2))) {
                    mCount = mCount - 1;
                    Toast.makeText(getActivity(), "Already Selected Please Select Another One", Toast.LENGTH_LONG).show();
                } else {
                    mTv_no_of_tags.setText("3/3");

                    mVindecator2.setBackgroundColor((getResources().getColor(R.color.popular_tag_color)));

                    if (mTags[mCount - 1].length() + mTags[mCount - 2].length() > 30) {
                        if (mTag4.getText().equals("")) {
                            mTag4.setVisibility(View.VISIBLE);

                            mTag4.setText(mTags[mCount]);
                        } else if (mTags[mCount].length() > 25) {
                            mTag7.setText(mTags[mCount]);
                            mTag7.setVisibility(View.VISIBLE);

                        } else {
                            if ((mTags[mCount - 1].length() > 25)) {
                                mTag7.setText(mTags[mCount]);
                                mTag7.setVisibility(View.VISIBLE);
                            } else {
                                mTag5.setVisibility(View.VISIBLE);

                                mTag5.setText(mTags[mCount]);
                            }
                        }

                    } else {
                        if (mTags[mCount].length() > 25) {
                            mTag4.setText(mTags[mCount]);
                            mTag4.setVisibility(View.VISIBLE);

                        } else {
                            mTag3.setVisibility(View.VISIBLE);

                            mTag3.setText(mTags[mCount]);
                        }
                    }
                }
            } else if (mCount == 1) {
                mTv_no_of_tags.setText("1/3");
                tv_community_tag_submit.setVisibility(View.VISIBLE);
                mTag1.setVisibility(View.VISIBLE);
                mVindecator.setBackgroundColor((getResources().getColor(R.color.popular_tag_color)));
                mTag1.setText(mTags[mCount]);

            }
            mCount++;
        }


    }

    protected void editTextWatcher() {
        mEt_search_edit_text.addTextChangedListener(dataSearchTextWatcher());
        mEt_search_edit_text.setFocusableInTouchMode(true);
        mEt_search_edit_text.requestFocus();
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
                mprofilePresenter.getSkillFromPresenter(mAppUtils.getAllDataRequestBuilder(AppConstants.SKILL_SUB_TYPE, mSearchDataName, AppConstants.ALL_SEARCH));
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
    public void championProfile(BaseResponse baseResponse, int championValue) {

    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {

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
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth);
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
        List<Doc> tagDetailList=getAllData.getDocs();
        if(StringUtil.isNotEmptyCollection(tagDetailList)) {
            mAdapter.setCallForRecycler(AppConstants.ALL_DATA_SUB_TYPE);
            mAdapter.setSheroesGenericListData(tagDetailList);
            mAdapter.notifyDataSetChanged();
            mtv_tag_text.setVisibility(View.GONE);
            mtv_tag_title.setVisibility(View.GONE);
            mTv_no_of_tags.setVisibility(View.GONE);
            mTag1.setVisibility(View.GONE);
            mTag2.setVisibility(View.GONE);
            mTag3.setVisibility(View.GONE);
            mTag4.setVisibility(View.GONE);
            mTag5.setVisibility(View.GONE);
            mTag6.setVisibility(View.GONE);
            ll_indecator.setVisibility(View.GONE);
        }
    }

    @Override
    public void getProfileListSuccess(List<Doc> feedDetailList) {

    }
}