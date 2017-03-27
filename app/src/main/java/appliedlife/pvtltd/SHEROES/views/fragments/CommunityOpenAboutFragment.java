package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityList;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostCreateResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.Docs;
import appliedlife.pvtltd.SHEROES.models.entities.community.Member;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.SelectedCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.presenters.OwnerPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CreateCommunityView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 01-02-2017.
 */

public class CommunityOpenAboutFragment extends BaseFragment implements CommunityView, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, CreateCommunityView, EditNameDialogListener{



    private AboutCommunityActivityIntractionListner mShareCommunityIntractionListner;
    private final String TAG = LogUtils.makeLogTag(CommunityOpenAboutFragment.class);
    private String[] marraySpinner;
    @Bind(R.id.rv_about_community_owner_list)
    RecyclerView mAboutCommunityOwnerRecyclerView;
    @Bind(R.id.iv_community_cover_img)
    ImageView miv_community_cover_img;
    @Bind(R.id.tv_about_community_des)
    TextView mtv_about_community_des;
    @Bind(R.id.tv_community_name)
    TextView mtv_community_name;
    @Bind(R.id.tv_community_tags)
    TextView mtv_community_tags;
    @Bind(R.id.tv_community_members)
    TextView mtv_community_members;
    @Bind(R.id.tv_community_requested)
    TextView mtv_community_requested;
    @Inject
    OwnerPresenter mOwnerPresenter;
    @Bind(R.id.iv_about_community)
    ImageView mOptionIv;

    @Bind(R.id.fb_about_community_logo)
    FloatingActionButton mfb_about_community_logo;

    @Bind(R.id.tv_community_invite)
    TextView tv_community_invite;
    FeedDetail mFeedDetail;
    private FragmentListRefreshData mFragmentListRefreshData;
    int iCurrentSelection;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    Long mcommunityid;
    String joinTxt="";

    /* public static CommunityOpenAboutFragment createInstance(int itemsCount) {
         CommunityOpenAboutFragment communitiesDetailFragment = new CommunityOpenAboutFragment();
         return communitiesDetailFragment;
     }*/
    private PopupWindow popupWindow;

    /* public static CommunityOpenAboutFragment createInstance(int itemsCount) {
         CommunityOpenAboutFragment communitiesDetailFragment = new CommunityOpenAboutFragment();
         return communitiesDetailFragment;
     }*/
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
        mtv_community_requested.setVisibility(View.GONE);
        mOwnerPresenter.attachView(this);


        if(null!=getArguments())
        {
            mFeedDetail =getArguments().getParcelable(AppConstants.COMMUNITY_DETAIL);
            mcommunityid=mFeedDetail.getIdOfEntityOrParticipant();
            Glide.with(this)
                    .load(mFeedDetail.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(miv_community_cover_img);
            Glide.with(this)
                    .load(mFeedDetail.getThumbnailImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(mfb_about_community_logo);
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getListDescription())) {
                mtv_about_community_des.setText(mFeedDetail.getListDescription());
            }
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getNameOrTitle())) {
                mtv_community_name.setText(mFeedDetail.getNameOrTitle());
            }
            int count = mFeedDetail.getNoOfMembers();
            mtv_community_requested.setText(mFeedDetail.getNoOfPendingRequest() + " Requested");
            mtv_community_members.setText(""+count+" Members");
            displayTabAsCommunityType(mFeedDetail);
        }

        mLayoutManager = new LinearLayoutManager(getActivity());
        mAboutCommunityOwnerRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunitiesDetailActivity) getActivity());

        mAboutCommunityOwnerRecyclerView.setLayoutManager(mLayoutManager);
        mAboutCommunityOwnerRecyclerView.setAdapter(mAdapter);

        OwnerListRequest ownerListRequest = new OwnerListRequest();
        ownerListRequest.setAppVersion("String");
        ownerListRequest.setCloudMessagingId("String");
        ownerListRequest.setCommunityId(mcommunityid);
        ownerListRequest.setDeviceUniqueId("String");
        ownerListRequest.setLastScreenName("String");
        ownerListRequest.setScreenName("String");
        mOwnerPresenter.getCommunityOwnerList(ownerListRequest);
        return view;
    }

    private void displayTabAsCommunityType(FeedDetail mFeedDetail)
    {
        //   boolean isCommunity=mFeedDetail.isClosedCommunity();
        if (mFeedDetail.isClosedCommunity()) {
            mtv_community_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_lock, 0);
        } else {
            mtv_community_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if(mFeedDetail.isOwner() || mFeedDetail.isMember())
        {
            tv_community_invite.setText(getActivity().getString(R.string.ID_INVITE));

        }
        if(mFeedDetail.isOwner())
        {
            mtv_community_requested.setVisibility(View.VISIBLE);
        }
        if (!mFeedDetail.isMember() && !mFeedDetail.isOwner() && !mFeedDetail.isRequestPending()) {
            tv_community_invite.setText(getActivity().getString(R.string.ID_JOIN));

           /* if(dataItem.isClosedCommunity())
            {
                tvFeaturedCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                tvFeaturedCommunityJoin.setText(mContext.getString(R.string.ID_REQUESTED));
                tvFeaturedCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_community_requested);
                tvFeaturedCommunityJoin.setVisibility(View.VISIBLE);
            }
            else
            {
                tvFeaturedCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
                tvFeaturedCommunityJoin.setText(mContext.getString(R.string.ID_JOIN));
                tvFeaturedCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                tvFeaturedCommunityJoin.setVisibility(View.VISIBLE);
            }*/



           /* tvFeaturedCommunityJoin.setTextColor(ContextCompat.getColor(mContext, R.color.footer_icon_text));
            tvFeaturedCommunityJoin.setText(mContext.getString(R.string.ID_JOIN));
            tvFeaturedCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
            tvFeaturedCommunityJoin.setVisibility(View.VISIBLE);*/
        } else if (mFeedDetail.isRequestPending()) {

        } else if (mFeedDetail.isOwner() || mFeedDetail.isMember()) {

        }
        else
        {
            mtv_community_name.setVisibility(View.GONE);
        }
    }
    @OnClick(R.id.tv_about_community_back)
    public void clickBackButton() {
        mShareCommunityIntractionListner.onClose();
    }

    @OnClick(R.id.tv_about_community_share)
    public void clickCommunityShare() {
        mShareCommunityIntractionListner.shareClick();
    }

    @OnClick(R.id.tv_community_requested)
    public void requestClick() {
        mShareCommunityIntractionListner.requestClick();
    }

    @OnClick(R.id.tv_community_members)
    public void membersClick() {
        mShareCommunityIntractionListner.memberClick();
    }

    @OnClick(R.id.tv_community_invite)
    public void inviteMembersClick() {
        joinTxt=tv_community_invite.getText().toString();
        if(joinTxt.equals("Join"))
        {
            CommunityJoinRegionDialogFragment communityJoinRegionDialogFragment=new CommunityJoinRegionDialogFragment();
            Bundle b = new Bundle();
            b.putParcelable(BaseDialogFragment.DISMISS_PARENT_ON_OK_OR_BACK, mFeedDetail);
            communityJoinRegionDialogFragment.setArguments(b);
            communityJoinRegionDialogFragment.setListener(this);
            communityJoinRegionDialogFragment.show(getActivity().getFragmentManager(), "dialog");
        }
        else
            mShareCommunityIntractionListner.inviteClick();
    }

    @OnClick(R.id.tv_community_add_more)
    public void addMoreClick() {
        mShareCommunityIntractionListner.ownerClick();
    }

    @OnClick(R.id.iv_about_community)
    void onOptionClick() {
        clickMenuItem(mOptionIv);
    }
    public void callRemoveOwner(long community_id)
    {
        OwnerListRequest ownerListRequest = new OwnerListRequest();
        ownerListRequest.setAppVersion("String");
        ownerListRequest.setCloudMessagingId("String");
        ownerListRequest.setCommunityId(mcommunityid);
        ownerListRequest.setDeviceUniqueId("String");
        ownerListRequest.setLastScreenName("String");
        ownerListRequest.setScreenName("String");
        mOwnerPresenter.getCommunityOwnerList(ownerListRequest);

    }
    private void clickMenuItem(View view) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.menu_option_layout, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
            }
        });
        final TextView tvEdit = (TextView) popupView.findViewById(R.id.tv_article_menu_edit);
        final TextView tvLeave = (TextView) popupView.findViewById(R.id.tv_article_menu_delete);
        tvLeave.setText(getActivity().getString(R.string.ID_LEAVE));
        tvEdit.setVisibility(View.VISIBLE);
        tvLeave.setVisibility(View.VISIBLE);
        popupWindow.showAsDropDown(view, -180, 0);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareCommunityIntractionListner.createCommunityClick(mFeedDetail);
                popupWindow.dismiss();
            }
        });
        tvLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    public void dismissPopup() {
        if (popupWindow != null) popupWindow.dismiss();
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
        //  item = (String) parent.getItemAtPosition(mPosition);

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

        void ownerClick();

        void requestClick();

        void createCommunityClick(FeedDetail feedDetail);

        void shareClick();

        void onErrorOccurence();

        void onClose();
    }


    @Override
    public void getityCommunityListSuccess(List<CommunityList> data) {

    }

    @Override
    public void getSelectedCommunityListSuccess(List<Docs> selected_community_response) {

    }




    @Override
    public void getOwnerListSuccess(List<Member> ownerListResponse) {
        mAdapter.setSheroesGenericListData(ownerListResponse);
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void postCreateCommunitySuccess(CreateCommunityResponse createCommunityResponse) {

    }

    @Override
    public void addPostCreateCommunitySuccess(CommunityPostCreateResponse createCommunityResponse) {

    }

    @Override
    public void getOwnerListDeactivateSuccess(DeactivateOwnerResponse deactivateOwnerResponse) {

    }

    @Override
    public void postCreateCommunityOwnerSuccess(CreateCommunityOwnerResponse createCommunityOwnerResponse) {

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
    public void onFinishEditDialog(String inputText) {

        LogUtils.info("value", inputText);
    }

    public interface CreateCommunityActivityPostIntractionListner {

        void onErrorOccurence();

    }


}
