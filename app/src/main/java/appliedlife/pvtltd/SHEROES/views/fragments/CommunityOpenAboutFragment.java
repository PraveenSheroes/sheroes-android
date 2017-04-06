package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.Member;
import appliedlife.pvtltd.SHEROES.models.entities.community.MembersList;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMember;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.presenters.MembersPresenter;
import appliedlife.pvtltd.SHEROES.presenters.OwnerPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.AllMembersView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CreateCommunityView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.getMemberRequestBuilder;

/**
 * Created by Ajit Kumar on 01-02-2017.
 */

public class CommunityOpenAboutFragment extends BaseFragment implements CommunityView, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, CreateCommunityView, AllMembersView {
    private final String TAG = LogUtils.makeLogTag(CommunityOpenAboutFragment.class);
    private String[] marraySpinner;
    @Inject
    MembersPresenter mMemberpresenter;
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    HomePresenter mHomePresenter;
    List<Member> ownerListResponse;
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

    @Bind(R.id.iv_community_post_icon)
    ImageView mfb_about_community_logo;

    @Bind(R.id.tv_community_join_invite)
    TextView mTvJoinInviteView;

    @Bind(R.id.tv_community_add_more)
    TextView tv_community_add_more;

    @Bind(R.id.pb_communities_open_about_progress_bar)
    ProgressBar mProgressbar;
    TextView tvLeave;
    TextView tvEdit;
    FeedDetail mFeedDetail;
    private FragmentListRefreshData mFragmentListRefreshData;
    int iCurrentSelection;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    Long mcommunityid;
    String joinTxt = "";
    private PopupWindow popupWindow;
    private CommunityEnum communityEnum = null;
    @Inject
    AppUtils mAppUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.community_open_about_fragment, container, false);
        ButterKnife.bind(this, view);
        mtv_community_requested.setVisibility(View.GONE);
        mOwnerPresenter.attachView(this);
        mMemberpresenter.attachView(this);
        mHomePresenter.attachView(this);
        setProgressBar(mProgressbar);
        if (null != getArguments()) {
            communityEnum = (CommunityEnum) getArguments().getSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT);
            mFeedDetail = getArguments().getParcelable(AppConstants.COMMUNITY_DETAIL);
            mcommunityid = mFeedDetail.getIdOfEntityOrParticipant();
            Glide.with(this)
                    .load(mFeedDetail.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(miv_community_cover_img);
            Glide.with(this).load(mFeedDetail.getThumbnailImageUrl()).transform(new CircleTransform(getActivity())).into(mfb_about_community_logo);
           /* Glide.with(this)
                    .load(mFeedDetail.getThumbnailImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(mfb_about_community_logo);*/
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getListDescription())) {
                mtv_about_community_des.setText(mFeedDetail.getListDescription());
            }
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getNameOrTitle())) {
                mtv_community_name.setText(mFeedDetail.getNameOrTitle());
            }
            if (null != mFeedDetail.getTags()) {
                if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getTags().toString()))
                    mtv_community_tags.setText(mFeedDetail.getTags().toString());
            }
            int count = mFeedDetail.getNoOfMembers();
            mtv_community_requested.setText(mFeedDetail.getNoOfPendingRequest() + AppConstants.SPACE + getString(R.string.ID_COMMUNITY_REQUESTED));
            mtv_community_members.setText(count + AppConstants.SPACE + getString(R.string.ID_MEMBERS));
            displayTabAsCommunityType(mFeedDetail);
        }

        mLayoutManager = new LinearLayoutManager(getActivity());
        mAboutCommunityOwnerRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunitiesDetailActivity) getActivity());

        mAboutCommunityOwnerRecyclerView.setLayoutManager(mLayoutManager);
        mAboutCommunityOwnerRecyclerView.setAdapter(mAdapter);
        OwnerListRequest ownerListRequest = mAppUtils.ownerRequestBuilder(mcommunityid);
        mOwnerPresenter.getCommunityOwnerList(ownerListRequest);
        return view;
    }

    public void refreshMemberCount() {
        Toast.makeText(getActivity(), "click", Toast.LENGTH_LONG).show();
        mMemberpresenter.getAllMembers(getMemberRequestBuilder(mFeedDetail.getIdOfEntityOrParticipant(), mFragmentListRefreshData.getPageNo()));

    }

    private void displayTabAsCommunityType(FeedDetail mFeedDetail) {
        //   boolean isCommunity=mFeedDetail.isClosedCommunity();
        if (mFeedDetail.isClosedCommunity()) {
            mtv_community_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_lock, 0);
        } else {
            mtv_community_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if (null != communityEnum) {
            switch (communityEnum) {
                case SEARCH_COMMUNITY:
                    if (!mFeedDetail.isMember() && !mFeedDetail.isOwner() && !mFeedDetail.isRequestPending()) {
                        mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.footer_icon_text));
                        mTvJoinInviteView.setText(getString(R.string.ID_JOIN));
                        mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                    } else if (mFeedDetail.isRequestPending()) {
                        mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        mTvJoinInviteView.setText(getString(R.string.ID_REQUESTED));
                        mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_feed_community_requested);
                        mTvJoinInviteView.setEnabled(false);
                    } else if (mFeedDetail.isOwner() || mFeedDetail.isMember()) {
                        mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        mTvJoinInviteView.setText(getString(R.string.ID_INVITE));
                        mTvJoinInviteView.setText(getActivity().getString(R.string.ID_INVITE));
                        mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_community_invite);
                        mTvJoinInviteView.setVisibility(View.VISIBLE);
                    } else {
                        mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        mTvJoinInviteView.setText(getString(R.string.ID_INVITE));
                        mTvJoinInviteView.setText(getActivity().getString(R.string.ID_INVITE));
                        mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_community_invite);
                        mTvJoinInviteView.setVisibility(View.VISIBLE);
                    }
                    if (mFeedDetail.isOwner()) {
                        tv_community_add_more.setVisibility(View.VISIBLE);
                        mtv_community_requested.setVisibility(View.VISIBLE);
                    }
                    break;
                case FEATURE_COMMUNITY:
                    if (!mFeedDetail.isMember() && !mFeedDetail.isOwner() && !mFeedDetail.isRequestPending()) {
                        mTvJoinInviteView.setText(getActivity().getString(R.string.ID_JOIN));
                        mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.footer_icon_text));
                        mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                    } else if (mFeedDetail.isRequestPending()) {
                        mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        mTvJoinInviteView.setText(getString(R.string.ID_REQUESTED));
                        mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_feed_community_requested);
                        mTvJoinInviteView.setEnabled(false);
                    } else if (mFeedDetail.isOwner() || mFeedDetail.isMember()) {
                        mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        mTvJoinInviteView.setText(getString(R.string.ID_JOINED));
                        mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
                        mTvJoinInviteView.setVisibility(View.VISIBLE);
                        mTvJoinInviteView.setEnabled(false);
                    }
                    if (mFeedDetail.isOwner()) {
                        tv_community_add_more.setVisibility(View.VISIBLE);
                    }
                    break;
                case MY_COMMUNITY:
                    if (mFeedDetail.isClosedCommunity()) {
                        if (mFeedDetail.isOwner()) {
                            mtv_community_requested.setVisibility(View.VISIBLE);
                            tv_community_add_more.setVisibility(View.VISIBLE);
                        } else if (mFeedDetail.isMember()) {
                            mtv_community_requested.setVisibility(View.GONE);
                            tv_community_add_more.setVisibility(View.GONE);
                        }
                    } else {
                        if (mFeedDetail.isOwner()) {
                            mtv_community_requested.setVisibility(View.GONE);
                            tv_community_add_more.setVisibility(View.VISIBLE);
                        } else if (mFeedDetail.isMember()) {
                            mtv_community_requested.setVisibility(View.GONE);
                            tv_community_add_more.setVisibility(View.GONE);
                        }
                    }
                    mTvJoinInviteView.setText(getActivity().getString(R.string.ID_INVITE));
                    mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_community_invite);
                    mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + communityEnum);
            }

        }
    }

    @OnClick(R.id.tv_about_community_back)
    public void clickBackButton() {
        ((CommunitiesDetailActivity) getActivity()).onClose();
    }

    @OnClick(R.id.tv_about_community_share)
    public void clickCommunityShare() {
        ((CommunitiesDetailActivity) getActivity()).shareClick();
    }

    @OnClick(R.id.tv_community_requested)
    public void requestClick() {
        ((CommunitiesDetailActivity) getActivity()).requestClick();
    }

    @OnClick(R.id.tv_community_members)
    public void membersClick() {
        ((CommunitiesDetailActivity) getActivity()).memberClick();
    }

    @OnClick(R.id.tv_community_join_invite)
    public void inviteJoinOnClick() {
        joinTxt = mTvJoinInviteView.getText().toString();
        if (joinTxt.equalsIgnoreCase(getString(R.string.ID_JOIN))) {
            if (mFeedDetail.isClosedCommunity())
                ((CommunitiesDetailActivity) getActivity()).inviteJoinEventClickFromAboutPage(joinTxt, mFeedDetail, this);
            else {
                callJoinApi();
            }
        } else {
            ((CommunitiesDetailActivity) getActivity()).inviteJoinEventClick(joinTxt, mFeedDetail);
        }
    }

    @OnClick(R.id.tv_community_add_more)
    public void addMoreClick() {
        ((CommunitiesDetailActivity) getActivity()).ownerClick();
    }

    @OnClick(R.id.iv_about_community)
    void onOptionClick() {
        clickMenuItem(mOptionIv);
    }

    private void callJoinApi() {
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            List<Long> userIdList = new ArrayList();
            userIdList.add((long) mUserPreference.get().getUserSummary().getUserId());
            mHomePresenter.communityJoinFromPresenter(AppUtils.communityRequestBuilder(userIdList, mFeedDetail.getIdOfEntityOrParticipant(), ""));

        }
    }

    public void callRemoveOwner(long community_id) {
        OwnerListRequest ownerListRequest = mAppUtils.ownerRequestBuilder(mcommunityid);
        mOwnerPresenter.getCommunityOwnerList(ownerListRequest);

    }

    public void refreshData(int size) {
        mtv_community_members.setText(size + AppConstants.SPACE + getString(R.string.ID_MEMBERS));
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
        tvEdit = (TextView) popupView.findViewById(R.id.tv_article_menu_edit);
        tvLeave = (TextView) popupView.findViewById(R.id.tv_article_menu_delete);
        popupWindow.showAsDropDown(view, 20, -10);
        tvLeave.setText(getActivity().getString(R.string.ID_LEAVE));
        if (mFeedDetail.isOwner()) {
            tvEdit.setVisibility(View.VISIBLE);
            if (StringUtil.isNotEmptyCollection(ownerListResponse)) {
                tvLeave.setVisibility(View.VISIBLE);
            } else {
                tvLeave.setVisibility(View.GONE);
            }
        } else {
            tvLeave.setVisibility(View.VISIBLE);
        }

        popupWindow.showAsDropDown(view, -180, 0);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CommunitiesDetailActivity) getActivity()).createCommunityClick(mFeedDetail);
                popupWindow.dismiss();
            }
        });
        tvLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mShareCommunityIntractionListner.
                callRemoveMember();
                popupWindow.dismiss();
            }
        });


    }

    public void callRemoveMember() {
        LoginResponse loginResponse = mUserPreference.get();
        UserSummary userSummary = loginResponse.getUserSummary();
        RemoveMember removeMember = mAppUtils.removeMemberRequestBuilder(mcommunityid, userSummary.getUserId());
        mMemberpresenter.unJoinedApi(removeMember);
    }

    public void dismissPopup() {
        if (popupWindow != null) popupWindow.dismiss();
    }


    @Override
    public void getSuccessForAllResponse(String success, FeedParticipationEnum feedParticipationEnum) {
        switch (success) {
            case AppConstants.SUCCESS:
                mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                mTvJoinInviteView.setText(getString(R.string.ID_JOINED));
                mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
                mTvJoinInviteView.setVisibility(View.VISIBLE);
                break;
            case AppConstants.FAILED:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(AppConstants.HTTP_401_UNAUTHORIZED, ERROR_JOIN_INVITE);
                break;
            default:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(AppConstants.HTTP_401_UNAUTHORIZED, ERROR_JOIN_INVITE);

        }

    }

    @Override
    public void getCreateCommunityResponse(LoginResponse loginResponse) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (null != view) {
            String item = (String) parent.getItemAtPosition(position);
        }

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

    @Override
    public void getSelectedCommunityListSuccess(List<CommunityPostResponse> selected_community_response) {

    }

    @Override
    public void getOwnerListSuccess(OwnerListResponse ownerResponse) {
        List<Member> ownerList = ownerResponse.getMembers();
        if (StringUtil.isNotEmptyCollection(ownerList)) {
            this.ownerListResponse = ownerList;
            for (Member member : ownerList) {
                if (mFeedDetail.isOwner() && ownerList.size() > AppConstants.ONE_CONSTANT) {
                    member.setOwner(true);
                } else {
                    member.setOwner(false);
                }
            }
            mAdapter.setSheroesGenericListData(ownerList);
            mAdapter.notifyDataSetChanged();
        }

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
    public void getAllMembers(List<MembersList> data) {

    }

    @Override
    public void removeMember(String status) {
        switch (status) {
            case AppConstants.SUCCESS:
                mTvJoinInviteView.setTextColor(ContextCompat.getColor(getContext(), R.color.footer_icon_text));
                mTvJoinInviteView.setText(getString(R.string.ID_JOIN));
                mTvJoinInviteView.setBackgroundResource(R.drawable.rectangle_feed_commnity_join);
                ((CommunitiesDetailActivity) getActivity()).onLeaveClick();
                break;
            case AppConstants.FAILED:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(AppConstants.HTTP_401_UNAUTHORIZED, ERROR_JOIN_INVITE);
                break;
            default:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(AppConstants.HTTP_401_UNAUTHORIZED, ERROR_JOIN_INVITE);
        }
    }


    @Override
    public void dialogValue(String dilogval) {

    }

    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }
}
