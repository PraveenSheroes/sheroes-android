package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityTags;
import appliedlife.pvtltd.SHEROES.presenters.CommunityTagsPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityTagsView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 07-02-2017.
 */

public class CommunitySearchTagsFragment extends BaseFragment implements CommunityTagsView, BaseHolderInterface {
    @Inject
    CommunityTagsPresenter mcommunityListPresenter;
    @Bind(R.id.rv_home_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_selected_tag1)
    TextView mTag1;
    @Bind(R.id.tv_selected_tag2)
    TextView mTag2;
    @Bind(R.id.tv_selected_tag3)
    TextView mTag3;
    @Bind(R.id.tv_selected_tag4)
    TextView mTag4;
    @Bind(R.id.tv_selected_tag5)
    TextView mTag5;
    @Bind(R.id.tv_selected_tag6)
    TextView mTag6;
    @Bind(R.id.tv_selected_tag7)
    TextView mTag7;
    @Bind(R.id.view)
    View mVindecator;
    @Bind(R.id.view1)
    View mVindecator1;
    @Bind(R.id.view2)
    View mVindecator2;
    @Bind(R.id.tv_community_tag_submit)
    TextView tv_community_tag_submit;
    @Bind(R.id.et_search_edit_text)
    EditText mEt_search_edit_text;

    @Bind(R.id.tv_no_of_tags)
    TextView mTv_no_of_tags;
    int mCount=1;
    String []mTags=new String[4];

    private MyCommunityTagListener mHomeActivityIntractionListner;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private final String mTAG = LogUtils.makeLogTag(CreateCommunityFragment.class);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof MyCommunityTagListener) {
                mHomeActivityIntractionListner = (MyCommunityTagListener) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(mTAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + mTAG + AppConstants.SPACE + exception.getMessage());
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View v = inflater.inflate(R.layout.community_tag_list, container, false);
        ButterKnife.bind(this, v);
        mcommunityListPresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mEt_search_edit_text.setHint("Search Tags");
        mTv_no_of_tags.setVisibility(View.GONE);
        tv_community_tag_submit.setVisibility(View.GONE);
        return v;
    }
    @OnClick(R.id.tv_back_community_tag)
    public void communityTagBackClick()
    {
        mHomeActivityIntractionListner.onBackPress();
    }
    @OnClick(R.id.tv_community_tag_submit)
    public void tagSubmitPress()
    {

        mHomeActivityIntractionListner.onTagsSubmit(mTags);

    }

    @Override
    public void getCommunityTagsList(List<CommunityTags> data) {
       mAdapter.setSheroesGenericListData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNwError() {

    }


    @Override
    public void startActivityFromHolder(Intent intent) {

    }


    @Override
    public void handleOnClick(BaseResponse sheroesListDataItem, View view) {
        mTv_no_of_tags.setVisibility(View.VISIBLE);
        if (sheroesListDataItem instanceof CommunityTags) {
            CommunityTags communityTags = (CommunityTags) sheroesListDataItem;
            if (mCount<=3) {
                mTags[mCount] = communityTags.getName();
                if (mCount == 2) {
                    mTv_no_of_tags.setText("2/3");

                    mVindecator1.setBackgroundColor((getResources().getColor(R.color.popular_tag_color)));

                    if (mTags[mCount - 1].length() > 25) {
                        mTag4.setVisibility(View.VISIBLE);
                        mTag4.setText(mTags[mCount]);
                    } else if (mTags[mCount - 1].length() < 25) {
                        if (mTags[mCount].length() > 25) {
                            mTag4.setVisibility(View.VISIBLE);

                            mTag4.setText(mTags[mCount]);
                        }
                        else {
                            mTag2.setVisibility(View.VISIBLE);

                            mTag2.setText(mTags[mCount]);
                        }
                    }

                } else if (mCount == 3) {
                    mTv_no_of_tags.setText("3/3");
                    tv_community_tag_submit.setVisibility(View.VISIBLE);
                    mVindecator2.setBackgroundColor((getResources().getColor(R.color.popular_tag_color)));

                    if (mTags[mCount - 1].length() + mTags[mCount - 2].length() > 30) {
                        if (mTag4.getText().equals("")) {
                            mTag4.setVisibility(View.VISIBLE);

                            mTag4.setText(mTags[mCount]);
                        }
                        else if (mTags[mCount].length() > 25) {
                            mTag7.setText(mTags[mCount]);
                            mTag7.setVisibility(View.VISIBLE);

                        }
                        else {
                            if((mTags[mCount-1].length() > 25))
                            {
                                mTag7.setText(mTags[mCount]);
                                mTag7.setVisibility(View.VISIBLE);
                            }
                            else {
                                mTag5.setVisibility(View.VISIBLE);

                                mTag5.setText(mTags[mCount]);
                            }
                        }

                    } else {
                         if (mTags[mCount].length() > 25) {
                            mTag4.setText(mTags[mCount]);
                            mTag4.setVisibility(View.VISIBLE);

                        }
                        else {
                             mTag3.setVisibility(View.VISIBLE);

                             mTag3.setText(mTags[mCount]);
                         }
                    }
                } else if (mCount == 1) {
                    mTv_no_of_tags.setText("1/3");

                    mTag1.setVisibility(View.VISIBLE);
                    mVindecator.setBackgroundColor((getResources().getColor(R.color.popular_tag_color)));
                    mTag1.setText(mTags[mCount]);

                }
                mCount++;
            }

            //  getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());
            //mHomeActivityIntractionListner.onAddFriendSubmit(communityList.getName(), communityList.getBackground());
        }
       // getActivity().getFragmentManager().popBackStack();

     //   getDialog().cancel();


    }

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
    public void showError(String s) {

    }

    public interface MyCommunityTagListener {
        void onErrorOccurence();
        void onTagsSubmit(String[] tagsval);
        void onBackPress();
    }
}