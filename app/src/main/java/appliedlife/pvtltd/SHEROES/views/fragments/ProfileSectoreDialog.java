package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by priyanka on 11/04/17.
 */

public class ProfileSectoreDialog extends BaseDialogFragment {
    @Bind(R.id.rv_sector_list)
    RecyclerView mRecyclerView;
    private GenericRecyclerViewAdapter mAdapter;
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sector_list_fragment, container, false);
        ButterKnife.bind(this, view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (null != getArguments()) {
            Bundle mBusSeatMapDataInBundle = getArguments();
            mMasterDataResult = (HashMap<String, HashMap<String, ArrayList<LabelValue>>>) mBusSeatMapDataInBundle.getSerializable(AppConstants.TAG_LIST);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), (ProfileActicity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        if (StringUtil.isNotEmptyCollection(setFilterValues())) {
            mAdapter.setSheroesGenericListData(setFilterValues());
            mAdapter.notifyDataSetChanged();
        }

        setCancelable(true);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismissAllowingStateLoss();//dismiss dialog on back button press
                dismiss();
            }
        };
    }

    private List<LabelValue> setFilterValues() {
        if (null != mMasterDataResult && null != mMasterDataResult.get(AppConstants.MASTER_DATA_SECTOR_KEY) && null != mMasterDataResult.get(AppConstants.MASTER_DATA_SECTOR_KEY).get(AppConstants.MASTER_DATA_POPULAR_CATEGORY)) {
            HashMap<String, ArrayList<LabelValue>> hashMap = mMasterDataResult.get(AppConstants.MASTER_DATA_SECTOR_KEY);
            List<LabelValue> labelValueArrayList = hashMap.get(AppConstants.MASTER_DATA_POPULAR_CATEGORY);
            return labelValueArrayList;
        }
        return null;
    }

}
