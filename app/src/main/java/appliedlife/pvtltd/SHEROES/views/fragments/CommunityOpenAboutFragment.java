package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityList;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.presenters.OwnerPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CreateCommunityView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Ajit Kumar on 01-02-2017.
 */

public class CommunityOpenAboutFragment extends BaseFragment implements CommunityView, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, CreateCommunityView, EditNameDialogListener {

    private AboutCommunityActivityIntractionListner mShareCommunityIntractionListner;
    private final String TAG = LogUtils.makeLogTag(CommunityOpenAboutFragment.class);
    private String[] marraySpinner;
    @Bind(R.id.rv_about_community_owner_list)
    RecyclerView mAboutCommunityOwnerRecyclerView;
    @Bind(R.id.sp_about_community)
    Spinner mSpaboutCommunityspn;
    List<String> mSpinnerMenuItems;
    @Inject
    OwnerPresenter mOwnerPresenter;
    int iCurrentSelection;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    public static CommunityOpenAboutFragment createInstance(int itemsCount) {
        CommunityOpenAboutFragment communitiesDetailFragment = new CommunityOpenAboutFragment();
        return communitiesDetailFragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof AboutCommunityActivityIntractionListner) {
                mShareCommunityIntractionListner = (AboutCommunityActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.community_open_about_fragment, container, false);
        ButterKnife.bind(this, view);
        //  mcreate_community_post.setText(R.string.ID_SHARE_COMMUNITY);
        mOwnerPresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAboutCommunityOwnerRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunitiesDetailActivity) getActivity());

        mAboutCommunityOwnerRecyclerView.setLayoutManager(mLayoutManager);
        mAboutCommunityOwnerRecyclerView.setAdapter(mAdapter);
        mOwnerPresenter.getCommunityList();


        Fabric.with(getActivity(), new Crashlytics());

        mSpinnerMenuItems=new ArrayList();
        mSpinnerMenuItems.add(getActivity().getString(R.string.ID_EDIT));
        mSpinnerMenuItems.add(getActivity().getString(R.string.ID_LEAVE));
        mSpinnerMenuItems.add(AppConstants.SPACE);

        ArrayAdapter spinClockInWorkSiteAdapter = new ArrayAdapter(getActivity(), R.layout.about_community_spinner_row, mSpinnerMenuItems);

        mSpaboutCommunityspn.setAdapter(spinClockInWorkSiteAdapter);
        mSpaboutCommunityspn.setOnItemSelectedListener(this);
         spinClockInWorkSiteAdapter = new ArrayAdapter(getActivity(), R.layout.about_community_spinner_row_back, mSpinnerMenuItems);

        mSpaboutCommunityspn.setAdapter(spinClockInWorkSiteAdapter);
         iCurrentSelection = mSpaboutCommunityspn.getSelectedItemPosition();
        mSpaboutCommunityspn.setSelection(2);

        mSpaboutCommunityspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (null != arg1) {
                    String msupplier = mSpaboutCommunityspn.getItemAtPosition(arg2).toString();
                    ((TextView) arg1).setTextSize(16);
                    ((TextView) arg1).setTextColor(
                            getResources().getColorStateList(R.color.fully_transparent));
                    LogUtils.info("Selected item : ", msupplier);
                    if(msupplier.equals("Edit"))
                    {
                        mShareCommunityIntractionListner.createCommunityClick();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                LogUtils.info("Selected item : ", "nothing");

            }
        });
        return view;
    }
    @OnClick(R.id.tv_about_community_back)
    public void clickBackButton()
    {
        mShareCommunityIntractionListner.onClose();
    }
    @OnClick(R.id.tv_about_community_share)
    public void clickCommunityShare()
    {
        mShareCommunityIntractionListner.shareClick();
    }
    @OnClick(R.id.tv_community_requested)
    public void requestClick()
    {
        mShareCommunityIntractionListner.requestClick();
    }
    @OnClick(R.id.tv_community_members)
    public void membersClick() {
        mShareCommunityIntractionListner.memberClick();
    }
    @OnClick(R.id.tv_community_invite)
    public void inviteMembersClick() {
        mShareCommunityIntractionListner.inviteClick();
    }
  /*  @OnClick(R.id.tv_community_menu)
    public void menueClick()
    {
        PopupMenu popup = new PopupMenu(getActivity(), mCommunityMenu);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.community_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getActivity(),"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        popup.show();//showing popup menu

//closing the setOnClickListener method
    }*/
   /* public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.actions, popup.getMenu());
        popup.show();
    }*/

    @Override
    public void getCreateCommunityResponse(LoginResponse loginResponse) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (null != view) {
            String item = (String) parent.getItemAtPosition(position);
        }
        //  item = (String) parent.getItemAtPosition(position);

      /*  if(null !=parent)
        ((TextView) parent.getChildAt(0)).setTextColor(Color.TRANSPARENT);
*/

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != view) {
            String item = (String) parent.getItemAtPosition(position);
        }
    }


    public interface AboutCommunityActivityIntractionListner {
        void memberClick();
        void inviteClick();
        void requestClick();
        void createCommunityClick();
        void shareClick();
        void onErrorOccurence();

        void onClose();
    }


    @Override
    public void getityCommunityListSuccess(List<CommunityList> data) {

    }

    @Override
    public void getOwnerListSuccess(List<OwnerList> data) {
        mAdapter.setSheroesGenericListData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNwError() {

    }

    @Override
    public void dialogValue(String dilogval) {

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

    @Override
    public void onFinishEditDialog(String inputText) {

        LogUtils.info("value", inputText);
    }

    public interface CreateCommunityActivityPostIntractionListner {

        void onErrorOccurence();

    }


}