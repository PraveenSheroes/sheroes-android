package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityList;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityType;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.CommunityTypeAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 13-01-2017.
 */

public class CommunityTypeFragment extends BaseDialogFragment implements CommunityView,CommunityTypeAdapter.CommunityTypeAdapterCallback {
    @Bind(R.id.tv_community_type_submit)
    TextView mcommunity_type_submit;
    @Bind(R.id.lv_community_type_listview)
    ListView mCommunityTypelistView;
    CommunityTypeAdapter madapter;
    private boolean finishParent;
    public static final String DISMISS_PARENT_ON_OK_OR_BACK = "DISMISS_PARENT_ON_OK_OR_BACK";
    String[] ItemName;
    List<CommunityType> rowItem;
    private MyDialogFragmentListener mHomeActivityIntractionListner;
    private final String TAG = LogUtils.makeLogTag(CommunityTypeFragment.class);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    CommunityTypeFragment(CreateCommunityFragment context) {
        try {
            if (context instanceof MyDialogFragmentListener) {
                mHomeActivityIntractionListner = (MyDialogFragmentListener)context;
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.community_type, container, false);
        ButterKnife.bind(this, view);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
       // tryAgain.setOnClickListener(this);
//*-
//        finishParent = getArguments().getBoolean(DISMISS_PARENT_ON_OK_OR_BACK, false);
        setCancelable(true);


        rowItem = new ArrayList<CommunityType>();
        ItemName = getResources().getStringArray(R.array.name);

        for(int i = 0 ; i < ItemName.length ; i++)
        {
            CommunityType itm = new CommunityType(ItemName[i]);
            rowItem.add(itm);
        }

        madapter = new CommunityTypeAdapter(getActivity(), rowItem,mcommunity_type_submit);
        madapter.setCallback(this);
        mCommunityTypelistView.setAdapter(madapter);


        return view;
    }
    @OnClick(R.id.tv_community_type_submit)
    public void onDoneClick()
    {
       Toast.makeText(getActivity(), mCommunityTypelistView.getSelectedItem()+"",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onStart() {
        super.onStart();

    }
    public void doneClick(String community_type)
    {
        mHomeActivityIntractionListner.onAddFriendSubmit(community_type,"");
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismissAllowingStateLoss();//dismiss dialog on back button press
                if (finishParent) {
                    getActivity().finish();
                }
            }
        };
    }




    @Override
    public void getityCommunityListSuccess(List<CommunityList> data) {

    }

    @Override
    public void showNwError() {

    }
    @Override
    public void communityType(String communitytype) {
        Toast.makeText(getActivity(),communitytype,Toast.LENGTH_LONG).show();
        getDialog().cancel();
        mHomeActivityIntractionListner.onAddFriendSubmit(communitytype,"");

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
    public void showError(String s) {

    }


    public interface MyDialogFragmentListener {
        void onErrorOccurence();
        void onAddFriendSubmit(String communitynm, String image);
    }
}
