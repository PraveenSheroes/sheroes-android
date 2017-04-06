package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityType;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.Docs;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.PopularTag;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.CommunityTypeAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 13-01-2017.
 */

public class CommunityTypeFragment extends BaseDialogFragment implements CommunityView, CommunityTypeAdapter.CommunityTypeAdapterCallback, HomeView {
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    @Inject
    HomePresenter mHomePresenter;
    @Bind(R.id.tv_community_type_submit)
    TextView mcommunity_type_submit;
    @Bind(R.id.lv_community_type_listview)
    ListView mCommunityTypelistView;
    CommunityTypeAdapter madapter;
    @Bind(R.id.rl_done)
    RelativeLayout rl_done;
    Long typeId;
    String[] ItemName;
    List<CommunityType> rowItem;
    private MyDialogFragmentListener mHomeActivityIntractionListner;
    private final String TAG = LogUtils.makeLogTag(CommunityTypeFragment.class);
    HashMap<String, HashMap<String, ArrayList<LabelValue>>> data = new HashMap<>();
    private List<PopularTag> listFeelter = new ArrayList<PopularTag>();
    Context context;
    List<String> jobAtList = new ArrayList<>();
    HashMap<String, Long> typeid;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setListener(MyDialogFragmentListener listener) {
        this.mHomeActivityIntractionListner = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);

        View view = inflater.inflate(R.layout.community_type, container, false);
        ButterKnife.bind(this, view);
        mHomePresenter.attachView(this);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);


        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            setMasterData(mUserPreferenceMasterData.get().getData());
        } else {
            mHomePresenter.getMasterDataToPresenter();

        }
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            data = mUserPreferenceMasterData.get().getData();
            LogUtils.error("Master Data", data + "");
            HashMap<String, ArrayList<LabelValue>> hashMap = data.get(AppConstants.MASTER_DATA_COMMUNITY_TYPE_KEY);
            List<LabelValue> labelValueArrayList = hashMap.get(AppConstants.MASTER_DATA_POPULAR_CATEGORY);
            PopularTag filterList = new PopularTag();
            filterList.setName("Community Type");

            for (int i = 0; i < labelValueArrayList.size(); i++) {
                String abc = labelValueArrayList.get(i).getLabel();

                jobAtList.add(abc);
            }
            filterList.setBoardingDataList(jobAtList);
            listFeelter.add(filterList);

        }


        rowItem = new ArrayList<CommunityType>();
        ItemName = getResources().getStringArray(R.array.name);

        for (int i = 0; i < ItemName.length; i++) {
            CommunityType itm = new CommunityType(ItemName[i]);
            rowItem.add(itm);
        }


        return view;
    }

    private void setMasterData(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {
        if (null != mapOfResult.get(AppConstants.MASTER_DATA_COMMUNITY_TYPE_KEY) && null != mapOfResult.get(AppConstants.MASTER_DATA_COMMUNITY_TYPE_KEY).get(AppConstants.MASTER_DATA_POPULAR_CATEGORY))
            LogUtils.error("Master Data", data + "");
        HashMap<String, ArrayList<LabelValue>> hashMap = mapOfResult.get(AppConstants.MASTER_DATA_COMMUNITY_TYPE_KEY);
        List<LabelValue> labelValueArrayList = hashMap.get(AppConstants.MASTER_DATA_POPULAR_CATEGORY);
        PopularTag filterList = new PopularTag();
        filterList.setName("Popular Tag");
        List<String> jobAtList = new ArrayList<>();
        typeid = new HashMap<String, Long>();
        if (StringUtil.isNotEmptyCollection(labelValueArrayList)) {
            for (int i = 0; i < labelValueArrayList.size(); i++) {
                String abc = labelValueArrayList.get(i).getLabel();
                typeid.put(abc, labelValueArrayList.get(i).getValue());
                jobAtList.add(abc);
            }
            filterList.setBoardingDataList(jobAtList);
            listFeelter.add(filterList);
        }
        madapter = new CommunityTypeAdapter(getActivity(), jobAtList, mcommunity_type_submit);
        madapter.setCallback(this);
        mCommunityTypelistView.setAdapter(madapter);
        madapter.notifyDataSetChanged();
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {
        setMasterData(mapOfResult);
    }

    @OnClick(R.id.rl_done)
    public void onDoneClick() {
        typeId = typeid.get(mCommunityTypelistView.getSelectedItem());
        Toast.makeText(getActivity(), mCommunityTypelistView.getSelectedItem() + "", Toast.LENGTH_LONG).show();
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
                    getActivity().finish();
            }
        };
    }


    @Override
    public void getSelectedCommunityListSuccess(List<Docs> selected_community_response) {

    }

    @Override
    public void getOwnerListSuccess(OwnerListResponse ownerListResponse) {

    }

    @Override
    public void createCommunitySuccess(CreateCommunityResponse createCommunityResponse) {

    }

    @Override
    public void getOwnerListDeactivateSuccess(DeactivateOwnerResponse deactivateOwnerResponse) {

    }

    @Override
    public void postCreateCommunityOwner(CreateCommunityOwnerResponse createCommunityOwnerResponse) {

    }


    @Override
    public void communityType(String communitytype) {

        typeId = typeid.get(communitytype);
        Toast.makeText(getActivity(), communitytype, Toast.LENGTH_LONG).show();
        getDialog().cancel();
        mHomeActivityIntractionListner.onAddFriendSubmit(communitytype, typeId);

    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {

    }

    @Override
    public void getTagListSuccess(List<Doc> feedDetailList) {

    }

    @Override
    public void getSuccessForAllResponse(String success, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void getDB(List<RecentSearchData> recentSearchDatas) {

    }

    public interface MyDialogFragmentListener {
        void onAddFriendSubmit(String communitynm, Long typeId);
    }
}
