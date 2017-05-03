package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.JobFilterActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 04-05-2017.
 */

public class FunctionalAreaDialogFragment extends BaseDialogFragment {
    private final String TAG = LogUtils.makeLogTag(JobLocationSearchDialogFragment.class);
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.rv_func_area_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_done_fun_area)
    TextView tvDoneFunArea;
    private GenericRecyclerViewAdapter mAdapter;
    private String sourceName;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.functional_area_dialog, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            sourceName = getArguments().getString(AppConstants.SOURCE_NAME);
        }
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (StringUtil.isNotNullOrEmptyString(sourceName) && AppConstants.PROFILE_FRAGMENT.equalsIgnoreCase(sourceName)) {
            mAdapter = new GenericRecyclerViewAdapter(getActivity(), (ProfileActicity) getActivity());
            tvDoneFunArea.setVisibility(View.GONE);
        } else {
            mAdapter = new GenericRecyclerViewAdapter(getActivity(), (JobFilterActivity) getActivity());
        }
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        if (StringUtil.isNotEmptyCollection(functionalAreaValues())) {
            mAdapter.setSheroesGenericListData(functionalAreaValues());
            mAdapter.notifyDataSetChanged();
        }
        return view;
    }

    private List<OnBoardingData> functionalAreaValues() {
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            mMasterDataResult = mUserPreferenceMasterData.get().getData();
        }
        if (null != mMasterDataResult && null != mMasterDataResult.get(AppConstants.MASTER_DATA_FUNCTIONAL_AREA_KEY)) {
            HashMap<String, ArrayList<LabelValue>> hashMap = mMasterDataResult.get(AppConstants.MASTER_DATA_FUNCTIONAL_AREA_KEY);
            List<OnBoardingData> listBoardingList = new ArrayList<>();
            Set<String> lookingForCategorySet = hashMap.keySet();
            for (String lookingForCategory : lookingForCategorySet) {
                OnBoardingData boardingData = new OnBoardingData();
                boardingData.setFragmentName(AppConstants.MASTER_DATA_FUNCTIONAL_AREA_KEY);
                boardingData.setCategory(lookingForCategory);
                boardingData.setBoardingDataList(hashMap.get(lookingForCategory));
                listBoardingList.add(boardingData);
            }
            return listBoardingList;
        }
        return null;
    }

    @OnClick(R.id.tv_done_fun_area)
    public void onSaveJobLocation() {
        ((JobFilterActivity) getActivity()).onDoneFunctionArea();
    }

    @OnClick(R.id.iv_fun_area_back)
    public void onSearchBack() {
        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth);
    }

}
