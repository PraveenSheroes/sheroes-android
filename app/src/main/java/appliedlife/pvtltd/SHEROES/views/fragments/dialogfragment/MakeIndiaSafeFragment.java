package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.LatLongWithLocation;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.MakeIndiaSafeDetail;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.MakeIndiaSafeResponse;
import appliedlife.pvtltd.SHEROES.presenters.CreateCommunityPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.CustomeDataList;
import appliedlife.pvtltd.SHEROES.views.activities.MakeIndiaSafeMapActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 28-07-2017.
 */

public class MakeIndiaSafeFragment extends BaseFragment {
    private static final String SCREEN_LABEL = "Make India Safe Screen";
    private final String TAG = LogUtils.makeLogTag(MakeIndiaSafeFragment.class);
    @Bind(R.id.rv_make_india_list)
    RecyclerView mRecyclerView;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    @Inject
    AppUtils mAppUtils;
    @Inject
    Preference<LoginResponse> mUserPreference;
    LatLongWithLocation mLatLongWithLocation;
    @Inject
    CreateCommunityPresenter mCreateCommunityPresenter;
    private File localImageSaveForChallenge;
    private MakeIndiaSafeDetail mMakeIndiaSafeDetail;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar mProgressBar;
    public static MakeIndiaSafeFragment createInstance(LatLongWithLocation latLongWithLocation) {
        MakeIndiaSafeFragment makeIndiaSafeFragment = new MakeIndiaSafeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.LAT_LONG_DETAIL, latLongWithLocation);
        makeIndiaSafeFragment.setArguments(bundle);
        return makeIndiaSafeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.make_india_safe, container, false);
        ButterKnife.bind(this, view);
        mCreateCommunityPresenter.attachView(this);
        setProgressBar(mProgressBar);
        if (null != getArguments()) {
            Bundle bundle = getArguments();
            mLatLongWithLocation = bundle.getParcelable(AppConstants.LAT_LONG_DETAIL);
        }
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (MakeIndiaSafeMapActivity) getActivity());
        mAdapter.setSheroesGenericListData(CustomeDataList.makeIndiaSafeItemList(mLatLongWithLocation));
        ((MakeIndiaSafeMapActivity)getActivity()).mFlMapLayout.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void createCommunitySuccess(BaseResponse baseResponse) {
        if (baseResponse instanceof CreateCommunityResponse) {
            CreateCommunityResponse createCommunityResponse = ((CreateCommunityResponse) baseResponse);
            communityPostResponse(createCommunityResponse);
        } else if (baseResponse instanceof MakeIndiaSafeResponse) {
            MakeIndiaSafeResponse makeIndiaSafeResponse = ((MakeIndiaSafeResponse) baseResponse);
            makeIndiaSafeResponse(makeIndiaSafeResponse);
        }
    }

    private void communityPostResponse(CreateCommunityResponse createCommunityResponse) {
        if (StringUtil.isNotNullOrEmptyString(createCommunityResponse.getStatus())) {
            switch (createCommunityResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    if (null != mMakeIndiaSafeDetail) {
                        if (mMakeIndiaSafeDetail.isLinkClicked()) {
                            Toast.makeText(getActivity(), "Awesome! Thanks for helping SHEROES #MakeIndiaSafe", Toast.LENGTH_SHORT).show();
                            ((MakeIndiaSafeMapActivity) getActivity()).backClick();
                        } else {
                            FeedDetail feedDetail = createCommunityResponse.getFeedDetail();
                            if (null != feedDetail) {
                                mLatLongWithLocation.setEntityOrParticipantId(feedDetail.getEntityOrParticipantId());
                                mCreateCommunityPresenter.getMakeIndiaSafeFromPresenter(mAppUtils.makeIndiaSafeRequestBuilder(mLatLongWithLocation));
                            }
                        }
                    }
                    break;
                case AppConstants.FAILED:
                    break;
                default:
            }
        }
    }

    private void makeIndiaSafeResponse(MakeIndiaSafeResponse makeIndiaSafeResponse) {
        if (StringUtil.isNotNullOrEmptyString(makeIndiaSafeResponse.getStatus())) {
            switch (makeIndiaSafeResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    ((MakeIndiaSafeMapActivity) getActivity()).backClick();
                    Uri uri = Uri.parse("file://" + localImageSaveForChallenge.getAbsolutePath());
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    share.putExtra(Intent.EXTRA_TEXT, mLatLongWithLocation.getDescription());
                    share.setType("image/*");
                    share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    getActivity().startActivity(Intent.createChooser(share, "Share image File"));
                    break;
                case AppConstants.FAILED:
                    break;
                default:
            }
        }
    }

    public void makeCommunityPostRequest(MakeIndiaSafeDetail makeIndiaSafeDetail) {
        mMakeIndiaSafeDetail = makeIndiaSafeDetail;
        mCreateCommunityPresenter.postCommunityList(makeIndiaSafeDetail.getCommunityPostCreateRequest());
    }

    public void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                ((MakeIndiaSafeMapActivity) getActivity()).selectImageFrmCamera();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_CODE_FOR_LOCATION);
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            ((MakeIndiaSafeMapActivity) getActivity()).selectImageFrmCamera();
        }
    }

    public void setImageOnHolder(MakeIndiaSafeDetail mMakeIndiaSafeDetail, Bitmap photo, File localImageSaveForChallenge) {
        this.localImageSaveForChallenge = localImageSaveForChallenge;
        List<MakeIndiaSafeDetail> makeItemsList = new ArrayList<>();
        MakeIndiaSafeDetail makeIndiaSafeDetail = mMakeIndiaSafeDetail;
        makeIndiaSafeDetail.setBitmap(photo);
        makeIndiaSafeDetail.setLocalImageSaveForChallenge(localImageSaveForChallenge);
        makeIndiaSafeDetail.setPicClick(true);
        makeItemsList.add(makeIndiaSafeDetail);
        mAdapter.setSheroesGenericListData(makeItemsList);
        mAdapter.notifyDataSetChanged();
        ((MakeIndiaSafeMapActivity)getActivity()).mFlMapLayout.setVisibility(View.GONE);
    }


    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}

