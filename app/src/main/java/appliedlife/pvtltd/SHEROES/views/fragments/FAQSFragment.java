package appliedlife.pvtltd.SHEROES.views.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQS;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQSResponse;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMemberListResponse;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.SHEPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SHEView;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class FAQSFragment extends BaseFragment implements SHEView {
    public static final String SCREEN_LABEL = "FAQS Screen";
    @Inject
    SHEPresenter shePresenter;

    @Bind(R.id.rv_list_faqs)
    RecyclerView mRecyclerView;

    @Bind(R.id.pb_faqs_progress_bar)
    ProgressBar pbFAQsProgreeBar;

    @Bind(R.id.scroll_view_faqs)
    ScrollView svFaqs;

    GenericRecyclerViewAdapter mAdapter;

    private MoEHelper mMoEHelper;
    private MoEngageUtills moEngageUtills;
    private PayloadBuilder payloadBuilder;
    private long startedTime;
    private LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        ButterKnife.bind(this, view);
        shePresenter.attachView(this);
        setProgressBar(pbFAQsProgreeBar);
        assignNavigationRecyclerListView();
        payloadBuilder = new PayloadBuilder();
        mMoEHelper = MoEHelper.getInstance(getActivity());
        moEngageUtills = MoEngageUtills.getInstance();
        startedTime = System.currentTimeMillis();
        moEngageUtills.entityMoEngageViewFAQ(getActivity(),mMoEHelper,payloadBuilder,startedTime);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) ((HomeActivity) getActivity()).mToolbar.getLayoutParams();
        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) ((HomeActivity) getActivity()).mAppBarLayout.getLayoutParams();
        params.setScrollFlags(0);
        appBarLayoutParams.setBehavior(null);
        ((HomeActivity) getActivity()).mAppBarLayout.setLayoutParams(appBarLayoutParams);

        return view;
    }

    private void assignNavigationRecyclerListView() {
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        shePresenter.getAllFAQS(AppUtils.sheFAQSRequestBuilder());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        DrawerViewHolder.selectedOptionName = AppConstants.NAV_FAQ;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return shePresenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        shePresenter.detachView();
        long timeSpent=System.currentTimeMillis() - startedTime;
        moEngageUtills.entityMoEngageViewFAQ(getActivity(),mMoEHelper,payloadBuilder,timeSpent);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) ((HomeActivity) getActivity()).mToolbar.getLayoutParams();
        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) ((HomeActivity) getActivity()).mAppBarLayout.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
        ((HomeActivity) getActivity()).mAppBarLayout.setLayoutParams(appBarLayoutParams);
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public void getAllFAQs(FAQSResponse faqsResponse) {
        if(faqsResponse!=null && faqsResponse.getListOfFAQs()!=null){
            List<FAQS> newFaqList = new ArrayList<>();

            int i = 1;
            int size = faqsResponse.getListOfFAQs().size();
            for(FAQS faqs : faqsResponse.getListOfFAQs()){

                if(i == size){
                    faqs.setLast(true);
                }else{
                    faqs.setLast(false);
                }

                faqs.setItemSelected(false);

                if(faqs.getAnswer()!=null){
                    if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                        faqs.setAnswer(Html.fromHtml(faqs.getAnswer(), 0).toString()); // for 24 api and more
                    } else {
                        faqs.setAnswer(Html.fromHtml(faqs.getAnswer()).toString());
                    }
                }
                if(faqs.getQuestion()!=null){
                    if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                        faqs.setQuestion(Html.fromHtml(faqs.getQuestion(), 0).toString()); // for 24 api and more
                    } else {
                        faqs.setQuestion(Html.fromHtml(faqs.getQuestion()).toString());
                    }
                }
                newFaqList.add(faqs);

                i++;
            }
            mAdapter.setSheroesGenericListData(newFaqList);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void  setDataChange(FAQS faqs) {
        mAdapter.setFAQOnPosition(faqs, faqs.getItemPosition());
        mLayoutManager.scrollToPosition(faqs.getItemPosition());
        mAdapter.notifyDataSetChanged();
        if(faqs.isLast()){
            goToLastInScrollView();
        }
    }

    public void goToLastInScrollView() {
        svFaqs.post(new Runnable() {
            @Override
            public void run() {
                svFaqs.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @Override
    public void getAllICCMembers(ICCMemberListResponse iccMemberListResponse) {

    }


    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

}
