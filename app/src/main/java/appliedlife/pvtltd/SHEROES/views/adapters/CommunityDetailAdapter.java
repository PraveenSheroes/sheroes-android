package appliedlife.pvtltd.SHEROES.views.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityTab;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HelplineFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.NavigateToWebViewFragment;

/**
 * Adapter for community detail
 */
public class CommunityDetailAdapter extends FragmentPagerAdapter {

    //region member variable
    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();
    //endregion member variable

    //region constructor
    public CommunityDetailAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    //endregion constructor

    //region override methods
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
    //endregion

    //region instance methods
    //Add tabs to community adapter
    public void addCommunityTabs(CommunityFeedSolrObj communityFeedSolrObj) {
        if (!CommonUtil.isEmpty(communityFeedSolrObj.communityTabs)) {
            List<CommunityTab> communityTabs = communityFeedSolrObj.communityTabs;

            for (CommunityTab communityTab : communityTabs) {
                if (communityTab.type.equalsIgnoreCase(CommunityDetailActivity.TabType.NATIVE.getName())) {
                    FeedFragment feedFragment = new FeedFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(CommunityTab.COMMUNITY_TAB_OBJ, Parcels.wrap(communityTab));
                    bundle.putString(FeedFragment.PRIMARY_COLOR, communityFeedSolrObj.communityPrimaryColor);
                    bundle.putString(FeedFragment.TITLE_TEXT_COLOR, communityFeedSolrObj.titleTextColor);
                    feedFragment.setArguments(bundle);
                    addFragment(feedFragment, communityTab.title);
                } else if (communityTab.type.equalsIgnoreCase(CommunityDetailActivity.TabType.HTML.getName())) {
                    NavigateToWebViewFragment webViewFragment = NavigateToWebViewFragment.newInstance(null, communityTab.dataHtml, "", false);
                    addFragment(webViewFragment, communityTab.title);
                } else if (communityTab.type.equalsIgnoreCase(CommunityDetailActivity.TabType.WEB.getName())) {
                    NavigateToWebViewFragment webViewFragment = NavigateToWebViewFragment.newInstance(communityTab.dataUrl, null, "", false);
                    addFragment(webViewFragment, communityTab.title);
                } else if (communityTab.type.equalsIgnoreCase(CommunityDetailActivity.TabType.WEB_CUSTOM_TAB.getName())) {
                    NavigateToWebViewFragment webViewFragment = NavigateToWebViewFragment.newInstance(communityTab.dataUrl, null, "", false, true);
                    addFragment(webViewFragment, communityTab.title);
                } else if (communityTab.type.equalsIgnoreCase(CommunityDetailActivity.TabType.FRAGMENT.getName())) {
                    if (communityTab.dataUrl.equalsIgnoreCase(AppConstants.HELPLINE_URL) || communityTab.dataUrl.equalsIgnoreCase(AppConstants.HELPLINE_URL_COM)) {
                        HelplineFragment helplineFragment = HelplineFragment.createInstance(communityFeedSolrObj.getNameOrTitle());
                        addFragment(helplineFragment, communityTab.title);
                    }
                }
            }
        }
    }

    private void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }
    //endregion

}
