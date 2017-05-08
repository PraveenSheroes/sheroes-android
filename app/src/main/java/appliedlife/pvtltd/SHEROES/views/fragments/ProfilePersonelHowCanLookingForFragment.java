package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.GetInterestJobResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.presenters.OnBoardingPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.OnBoardingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.OnBoardingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.COMMUNITY_OWNER;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.profileOpertunityTypeRequestBuilder;

/**
 * Created by Praveen_Singh on 05-05-2017.
 */

public class ProfilePersonelHowCanLookingForFragment extends BaseFragment implements OnBoardingView {
    private final String TAG = LogUtils.makeLogTag(ProfilePersonelHowCanLookingForFragment.class);
    @Bind(R.id.rv_how_can_sheroes_help_list)
    RecyclerView mRecyclerView;
    private GenericRecyclerViewAdapter mAdapter;
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    @Inject
    OnBoardingPresenter mOnBoardingPresenter;
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.li_how_sheroes_looking_for_strip_for_add_item)
    public LinearLayout mLiStripForAddItem;
    private List<LabelValue> mSelectedTag = new ArrayList<>();
    int mCurrentIndex = 0;
    int first, second, third, fourth;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar mProgressBar;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    @Bind(R.id.tv_about_me_tittle)
    TextView mCollapeTitleTxt;
    @Bind(R.id.tv_save_looking_for_details)
    TextView mTvSaveLookingForDetail;
    @Bind(R.id.al_community_open_about)
    AppBarLayout mAppBarLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.profile_looking_for_fragment, container, false);
        ButterKnife.bind(this, view);
        mOnBoardingPresenter.attachView(this);
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            mMasterDataResult = mUserPreferenceMasterData.get().getData();
        }
        setTitle();
        setProgressBar(mProgressBar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (ProfileActicity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        if (StringUtil.isNotEmptyCollection(setFilterValues())) {
            mAdapter.setSheroesGenericListData(setFilterValues());
            manager.scrollToPositionWithOffset(0, 0);
            mAdapter.notifyDataSetChanged();
        }
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mRecyclerView, manager, new FragmentListRefreshData()) {
            @Override
            public void onHide() {
                ((OnBoardingActivity) getActivity()).mLiStripForAddItem.setVisibility(View.VISIBLE);
            }

            @Override
            public void onShow() {
                ((OnBoardingActivity) getActivity()).mLiStripForAddItem.setVisibility(View.VISIBLE);

            }

            @Override
            public void dismissReactions() {

            }
        });
        return view;
    }

    private void setTitle() {
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

    }

    private List<OnBoardingData> setFilterValues() {
        if (null != mMasterDataResult && null != mMasterDataResult.get(AppConstants.MASTER_DATA_OPPORTUNITY_KEY)) {
            {
                HashMap<String, ArrayList<LabelValue>> hashMap = mMasterDataResult.get(AppConstants.MASTER_DATA_OPPORTUNITY_KEY);
                List<OnBoardingData> listBoardingList = new ArrayList<>();
                Set<String> lookingForCategorySet = hashMap.keySet();
                for (String lookingForCategory : lookingForCategorySet) {
                    OnBoardingData boardingData = new OnBoardingData();
                    boardingData.setFragmentName(AppConstants.HOW_SHEROES_CAN_HELP);
                    boardingData.setCategory(lookingForCategory);
                    boardingData.setBoardingDataList(hashMap.get(lookingForCategory));
                    listBoardingList.add(boardingData);
                }
                return listBoardingList;
            }
        }
        return null;
    }

    @OnClick(R.id.tv_looking_back)
    public void onBackPressClick() {
        ((ProfileActicity) getActivity()).onBackPressed();
    }

    @OnClick(R.id.tv_save_looking_for_details)
    public void saveAboutMe() {
        Set<Long> skillIds = new HashSet<>();
        for (LabelValue labelValue : mSelectedTag) {
            skillIds.add(labelValue.getValue());
        }
        if (skillIds.size() > 0) {
            mOnBoardingPresenter.getLookingForHowCanToPresenter(profileOpertunityTypeRequestBuilder(skillIds, AppConstants.LOOKING_FOR_HOW_CAN, AppConstants.LOOKING_FOR_HOW_CAN_TYPE));
        }
    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(errorMsg, feedParticipationEnum);
    }


    public void setItemOnHeader(View view) {
        mLiStripForAddItem.removeAllViews();
        mLiStripForAddItem.removeAllViewsInLayout();
        selectTagOnClick(view);
        renderSelectedAddedItem(mLiStripForAddItem, mSelectedTag);

    }

    private void selectTagOnClick(View view) {
        //scrollView.fullScroll(ScrollView.FOCUS_DOWN);
     //   scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        LabelValue labelValue = (LabelValue) view.getTag();
        boolean flag = true;
        mCurrentIndex = 0;
        if (StringUtil.isNotEmptyCollection(mSelectedTag)) {
            for (LabelValue listValue : mSelectedTag) {
                if (listValue.getValue() == labelValue.getValue()) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                mSelectedTag.add(labelValue);
            } else {
                Toast.makeText(getContext(), "Already selected", Toast.LENGTH_SHORT).show();
            }
        } else {
            mSelectedTag.add(labelValue);
        }
    }

    public void renderSelectedAddedItem(LinearLayout liStripForAddItem, List<LabelValue> labelValues) {

        if (StringUtil.isNotEmptyCollection(labelValues)) {
            int row = 0;
            for (int index = 0; index <= row; index++) {
                first = second = third = fourth = 0;
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout liRow = (LinearLayout) layoutInflater.inflate(R.layout.tags_onboarding_ui_layout, null);
                int column = 3;
                row = cloumnViewTwo(liRow, row, column, labelValues);
                liStripForAddItem.addView(liRow);
            }
        }
        if(StringUtil.isNotEmptyCollection(mSelectedTag))
        {
            mTvSaveLookingForDetail.setVisibility(View.VISIBLE);
        }else
        {
            mTvSaveLookingForDetail.setVisibility(View.GONE);
        }
    }

    private int cloumnViewTwo(LinearLayout liRow, int passedRow, int column, List<LabelValue> labelValueList) {

        if (mCurrentIndex < labelValueList.size()) {
            int lengthString = labelValueList.get(mCurrentIndex).getLabel().length();
            if (first == 1 && second == 1) {
                passedRow += 1;
                return passedRow;
            } else if (second == 2 || third == 2) {
                passedRow += 1;
                return passedRow;
            } else if (second == 1 && third == 1) {
                passedRow += 1;
                return passedRow;
            } else if (fourth == 1 && second == 1) {
                passedRow += 1;
                return passedRow;
            } else if (fourth >= 1 && lengthString > 30) {
                passedRow += 1;
                return passedRow;
            }
            if (lengthString > 30) {
                if (column < 3) {
                    passedRow += 1;
                    return passedRow;
                } else {
                    first++;
                    inflateTagData(liRow, labelValueList);
                    passedRow += 1;
                    mCurrentIndex++;
                }

            } else if (lengthString <= 30 && lengthString > 15) {

                if (column < 2) {
                    passedRow += 1;
                    return passedRow;
                } else {
                    second++;
                    inflateTagData(liRow, labelValueList);
                    mCurrentIndex++;
                    passedRow = cloumnViewTwo(liRow, passedRow, column - 1, labelValueList);
                }

            } else if (lengthString >= 10 && lengthString <= 15) {

                if (column < 1) {
                    passedRow += 1;
                    return passedRow;
                } else {
                    third++;
                    inflateTagData(liRow, labelValueList);
                    mCurrentIndex++;
                    passedRow = cloumnViewTwo(liRow, passedRow, column - 1, labelValueList);
                }

            } else if (lengthString >= 5 && lengthString < 10) {
                if (column < 1) {
                    passedRow += 1;
                    return passedRow;
                } else {
                    fourth++;
                    inflateTagData(liRow, labelValueList);
                    mCurrentIndex++;
                    passedRow = cloumnViewTwo(liRow, passedRow, column - 1, labelValueList);
                }
            }

        }
        return passedRow;
    }

    private void inflateTagData(LinearLayout liRow, List<LabelValue> stringList) {
        LayoutInflater columnInflate = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout liTagLable = (LinearLayout) columnInflate.inflate(R.layout.tag_selected_item_ui, null);
        final TextView mTvTagData = (TextView) liTagLable.findViewById(R.id.tv_selected);
        mTvTagData.setText(stringList.get(mCurrentIndex).getLabel());
        mTvTagData.setTag(stringList.get(mCurrentIndex));
        mTvTagData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LabelValue labelValue = (LabelValue) mTvTagData.getTag();
                mLiStripForAddItem.removeAllViews();
                mLiStripForAddItem.removeAllViewsInLayout();
                List<LabelValue> lookingTemp = mSelectedTag;
                mCurrentIndex = 0;
                if (StringUtil.isNotEmptyCollection(lookingTemp)) {
                    lookingTemp.remove(labelValue);
                }
                renderSelectedAddedItem(mLiStripForAddItem, lookingTemp);
            }
        });
        liRow.addView(liTagLable);
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
                ((ProfileActicity) getActivity()).onBackPressed();
                break;
            case AppConstants.FAILED:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(boardingDataResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), COMMUNITY_OWNER);
                break;
            default: {
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), COMMUNITY_OWNER);
                break;
            }
        }
    }
}
