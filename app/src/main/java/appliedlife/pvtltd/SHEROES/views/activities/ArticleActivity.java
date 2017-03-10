package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.CustomeDataList;
import appliedlife.pvtltd.SHEROES.views.adapters.ImageFullViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.BookmarksFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeSpinnerFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 09-03-2017.
 */

public class ArticleActivity extends BaseActivity implements FragmentIntractionWithActivityListner, ImageFullViewAdapter.HomeActivityIntraction {
    private final String TAG = LogUtils.makeLogTag(ArticleActivity.class);
    @Bind(R.id.tv_spinner_icon)
    TextView mTvSpinnerIcon;
    @Bind(R.id.tv_search_box)
    TextView mTvSearchBox;
    @Bind(R.id.iv_home_notification_icon)
    ImageView mIvHomeNotification;
    @Bind(R.id.fragment_bookmark)
    FrameLayout mFramLayout;
    private int mClickValue;
    private FragmentOpen mFragmentOpen;
    private FeedDetail mFeedDetail;
    @Bind(R.id.fl_home_footer_list)
    public FrameLayout mFlHomeFooterList;
    private HomeSpinnerFragment mHomeSpinnerFragment;
    private List<HomeSpinnerItem> mHomeSpinnerItemList = new ArrayList<>();
    @Bind(R.id.tv_home)
    TextView mTvHome;
    @Bind(R.id.tv_communities)
    TextView mTvCommunities;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        if (null != getIntent() && null != getIntent().getExtras()) {
            mClickValue = getIntent().getExtras().getInt(AppConstants.HOME_FRAGMENT);
        }
        mFragmentOpen = new FragmentOpen();
        mFragmentOpen.setArticleFragment(true);
        setAllValues(mFragmentOpen);
        renderLoginFragmentView();
    }

    public void renderLoginFragmentView() {
        setContentView(R.layout.activity_bookmark);
        ButterKnife.bind(this);
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setText(AppConstants.EMPTY_STRING);
        mTvHome.setText(AppConstants.EMPTY_STRING);
        mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_ARTICLES));
        mTvSpinnerIcon.setVisibility(View.VISIBLE);
        mHomeSpinnerItemList = CustomeDataList.makeSpinnerListRequest();
        ArticlesFragment articlesFragment = new ArticlesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_bookmark, articlesFragment, BookmarksFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
    }

    @OnClick(R.id.tv_drawer_navigation)
    public void drawerNavigationClick() {
        mClickValue = AppConstants.TWO_CONSTANT;
        bookMarkResultToHome();
    }

    @OnClick(R.id.rl_article_header)
    public void searchHeaderClick() {
        mClickValue = AppConstants.THREE_CONSTANT;
        bookMarkResultToHome();
    }

    @OnClick(R.id.tv_home)
    public void homeOnClick() {
        mClickValue = AppConstants.NO_REACTION_CONSTANT;
        bookMarkResultToHome();
    }

    @OnClick(R.id.tv_communities)
    public void communityOnClick() {
        mClickValue = AppConstants.ONE_CONSTANT;
        bookMarkResultToHome();
    }

    @OnClick(R.id.tv_spinner_icon)
    public void openSpinnerOnClick() {
        if (!mFragmentOpen.isOpen()) {
            mHomeSpinnerFragment = new HomeSpinnerFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(AppConstants.HOME_SPINNER_FRAGMENT, (ArrayList<? extends Parcelable>) mHomeSpinnerItemList);
            mHomeSpinnerFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                    .replace(R.id.fragment_spinner, mHomeSpinnerFragment, HomeSpinnerFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
            mFragmentOpen.setOpen(true);
        } else {
            onBackPressed();
        }
    }

    private void bookMarkResultToHome() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.ARTICLE_FRAGMENT, mClickValue);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onShowErrorDialog() {
        Toast.makeText(this, "Please check your email id, password", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startActivityFromHolder(Intent intent) {

    }

    @Override
    public void setAllValues(FragmentOpen fragmentOpen) {
        super.setAllValues(fragmentOpen);
    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FeedDetail) {
            mFeedDetail = (FeedDetail) baseResponse;
            int id = view.getId();
            if (id == R.id.tv_article_bookmark) {
                bookMarkTrending();
            } else {
                super.feedCardsHandled(view, baseResponse);
            }
        } else if (baseResponse instanceof HomeSpinnerItem) {
            HomeSpinnerItem homeSpinnerItem = (HomeSpinnerItem) baseResponse;
            String spinnerHeaderName = homeSpinnerItem.getName();
            if (StringUtil.isNotNullOrEmptyString(spinnerHeaderName)) {
                if (spinnerHeaderName.equalsIgnoreCase(AppConstants.FEED_ARTICLE)) {
                    mFragmentOpen.setOpen(true);
                } else {
                    for (int i = 0; i < mHomeSpinnerItemList.size(); i++) {
                        mHomeSpinnerItemList.get(i).setDone(true);
                    }
                    mTvSpinnerIcon.setText(spinnerHeaderName);
                    mFragmentOpen.setOpen(true);
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(ArticlesFragment.class.getName());
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        ((ArticlesFragment) fragment).categorySearchInArticle(homeSpinnerItem);
                    }
                }
            } else {
                mHomeSpinnerItemList.clear();
                mHomeSpinnerItemList = CustomeDataList.makeSpinnerListRequest();
            }
            onBackPressed();
        }
    }

    @Override
    public void setListData(BaseResponse data, boolean isCheked) {
        List<HomeSpinnerItem> localList = new ArrayList<>();
        if (StringUtil.isNotEmptyCollection(mHomeSpinnerItemList)) {
            HomeSpinnerItem passedHomeItem = (HomeSpinnerItem) data;
            for (HomeSpinnerItem homeSpinnerItem : mHomeSpinnerItemList) {
                if (homeSpinnerItem.getId().equalsIgnoreCase(passedHomeItem.getId())) {
                    homeSpinnerItem.setChecked(passedHomeItem.isChecked());
                    localList.add(homeSpinnerItem);
                } else {
                    localList.add(homeSpinnerItem);
                }
            }
        }
        mHomeSpinnerItemList.clear();
        mHomeSpinnerItemList.addAll(localList);
    }

    @Override
    public List getListData() {
        return mHomeSpinnerItemList;
    }

    private void bookMarkTrending() {
        Fragment articleFragment = getSupportFragmentManager().findFragmentByTag(ArticlesFragment.class.getName());
        if (AppUtils.isFragmentUIActive(articleFragment)) {
            ((ArticlesFragment) articleFragment).bookMarkForCard(mFeedDetail);
        }
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
    }

    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen) {

    }
}

