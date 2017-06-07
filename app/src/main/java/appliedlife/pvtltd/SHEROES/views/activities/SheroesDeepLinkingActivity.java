package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;

/**
 * Created by Ajit Kumar on 11-04-2017.
 */

public class SheroesDeepLinkingActivity extends BaseActivity {
    private Uri mData;
    private int indexOfFourthBackSlace;
    private MoEHelper mMoEHelper;
    private MoEngageUtills moEngageUtills;
    private PayloadBuilder payloadBuilder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        moEngageUtills.entityMoEngageDeeplink(this, mMoEHelper, payloadBuilder);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (null != mMoEHelper) {
            mMoEHelper.onStart(this);
        }
        callDeepLinkingData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMoEHelper.onResume(this);
    }

    private void callDeepLinkingData() {
        if (null != getIntent()) {
            Intent intent = getIntent();
            if (null != intent.getData()) {
                mData = intent.getData();
                getDeeplinkUrlFromNotification(mData.toString());
            }else
            {
                if(null!=intent.getExtras()) {
                    String deepLink =intent.getExtras().getString(AppConstants.DEEP_LINK_URL);
                    getDeeplinkUrlFromNotification(deepLink);
                }
            }
        }else
        {
            Intent into = new Intent(this, HomeActivity.class);
            into.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(into);
            finish();
        }
    }

    private void getDeeplinkUrlFromNotification(String urlOfSharedCard)
    {
        String baseUrl = "";
        int fullLength = 0;
        if (StringUtil.isNotNullOrEmptyString(urlOfSharedCard)) {
            try {
                if (StringUtil.isNotNullOrEmptyString(urlOfSharedCard)) {
                    if (urlOfSharedCard.contains(AppConstants.CHALLENGE_URL) || urlOfSharedCard.contains(AppConstants.CHALLENGE_URL_COM)) {
                        try {
                            Intent into = new Intent(SheroesDeepLinkingActivity.this, HomeActivity.class);
                            int indexOfFirstEqual = AppUtils.findNthIndexOf(urlOfSharedCard, "=", 1);
                            String challengeUrl = urlOfSharedCard.substring(indexOfFirstEqual + 1, urlOfSharedCard.length());
                            if (StringUtil.isNotNullOrEmptyString(challengeUrl)) {
                                String ChallengeId = challengeUrl;
                                byte[] challengeBytes = Base64.decode(ChallengeId, Base64.DEFAULT);
                                String newChallengeId = new String(challengeBytes, AppConstants.UTF_8);
                                into.putExtra(AppConstants.CHALLENGE_ID, Long.parseLong(newChallengeId));
                                into.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(into);
                                finish();
                            }
                        } catch (Exception e) {
                            Intent into = new Intent(this, HomeActivity.class);
                            into.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(into);
                            finish();
                        }
                    } else if (urlOfSharedCard.contains(AppConstants.HELPLINE_URL) || urlOfSharedCard.contains(AppConstants.HELPLINE_URL_COM)) {
                        Intent helplineIntent = new Intent(SheroesDeepLinkingActivity.this, HomeActivity.class);
                        helplineIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        helplineIntent.putExtra(AppConstants.HELPLINE_CHAT, AppConstants.HELPLINE_CHAT);
                        startActivity(helplineIntent);
                        finish();
                    } else {
                        indexOfFourthBackSlace = AppUtils.findNthIndexOf(urlOfSharedCard, AppConstants.BACK_SLASH, 4);
                        if (indexOfFourthBackSlace > 0) {
                            baseUrl = urlOfSharedCard.substring(0, indexOfFourthBackSlace);
                            //When Fourth back slace not available
                            if (baseUrl.equalsIgnoreCase(AppConstants.EMPTY_STRING)) {
                                baseUrl = urlOfSharedCard;
                            } else {
                                if (baseUrl.equalsIgnoreCase(AppConstants.USER_URL)) {
                                    baseUrl = urlOfSharedCard;
                                }
                            }
                        }
                    }
                } else {
                    Intent homeFeed = new Intent(this, HomeActivity.class);
                    homeFeed.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(homeFeed);
                    finish();
                }
            } catch (Exception e) {
                Intent homeFeed = new Intent(this, HomeActivity.class);
                homeFeed.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(homeFeed);
                finish();
            }
            fullLength = urlOfSharedCard.length();

            if (StringUtil.isNotNullOrEmptyString(baseUrl)) {
                callActivities(urlOfSharedCard, baseUrl, fullLength);
            }

        } else {
            Toast.makeText(getApplicationContext(), AppConstants.INVALID_URL, Toast.LENGTH_SHORT).show();
        }
    }


    private void callActivities(String urlSharedViaSocial, String baseUrl, int fullLength) {
        String dataIdString = AppConstants.EMPTY_STRING;
        //In case of Article
        if (AppConstants.ARTICLE_URL.equalsIgnoreCase(baseUrl) || AppConstants.ARTICLE_URL_COM.equalsIgnoreCase(baseUrl) && AppConstants.ARTICLE_URL.length() < fullLength) {
            try {
                int sareid = urlSharedViaSocial.lastIndexOf(AppConstants.BACK_SLASH);
                String id = urlSharedViaSocial.substring(sareid + 1, fullLength);
                byte[] id1 = Base64.decode(id, Base64.DEFAULT);
                dataIdString = new String(id1, AppConstants.UTF_8);
                Intent articleDetail = new Intent(SheroesDeepLinkingActivity.this, ArticleDetailActivity.class);
                articleDetail.putExtra(AppConstants.ARTICLE_ID, Long.parseLong(dataIdString));
                articleDetail.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(articleDetail);
                finish();
            } catch (Exception e) {
                Intent into = new Intent(this, HomeActivity.class);
                into.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(into);
                finish();
            }
        } else if (AppConstants.JOB_URL.equalsIgnoreCase(baseUrl) || AppConstants.JOB_URL_COM.equalsIgnoreCase(baseUrl) && AppConstants.JOB_URL.length() < fullLength) {
            try {
                int sareid = urlSharedViaSocial.lastIndexOf(AppConstants.BACK_SLASH);
                String id = urlSharedViaSocial.substring(sareid + 1, fullLength);
                byte[] id1 = Base64.decode(id, Base64.DEFAULT);
                dataIdString = new String(id1, AppConstants.UTF_8);
                Intent jobDetail = new Intent(SheroesDeepLinkingActivity.this, JobDetailActivity.class);
                jobDetail.putExtra(AppConstants.JOB_ID, Long.parseLong(dataIdString));
                jobDetail.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(jobDetail);
                finish();
            } catch (Exception e) {
                Intent into = new Intent(this, HomeActivity.class);
                into.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(into);
                finish();
            }

        }
        /*
        "solr_ignore_deep_link_url": "https://sheroes.in/communities/test/Mjcz/MTE0NjI=",

        "solr_ignore_deep_link_url": "https://sheroes.in/communities/sheroes-community/NTc0"
        * */
        //In case of communities
        else if (AppConstants.COMMUNITY_URL.equalsIgnoreCase(baseUrl) || AppConstants.COMMUNITY_URL_COM.equalsIgnoreCase(baseUrl) && AppConstants.COMMUNITY_URL.length() < fullLength) {
            try {
                Intent into = new Intent(SheroesDeepLinkingActivity.this, CommunitiesDetailActivity.class);
                String communityDetail = urlSharedViaSocial.substring(indexOfFourthBackSlace, urlSharedViaSocial.length());
                int countBackSlash = countBackSlash(communityDetail);
                if (countBackSlash > 2) {
                    String splitCommPostUrl[] = communityDetail.split(AppConstants.BACK_SLASH);
                    if (null != splitCommPostUrl && splitCommPostUrl.length > 0) {
                        if (StringUtil.isNotNullOrEmptyString(splitCommPostUrl[splitCommPostUrl.length - 1]) && StringUtil.isNotNullOrEmptyString(splitCommPostUrl[splitCommPostUrl.length - 2])) {
                            String postid = splitCommPostUrl[splitCommPostUrl.length - 1];
                            postid = postid.replace("=", AppConstants.EMPTY_STRING);
                            String communityId = splitCommPostUrl[splitCommPostUrl.length - 2];
                            communityId = communityId.replace("=", AppConstants.EMPTY_STRING);
                            byte[] communityPostBytes = Base64.decode(postid, Base64.DEFAULT);
                            dataIdString = new String(communityPostBytes, AppConstants.UTF_8);
                            byte[] communityBytes = Base64.decode(communityId, Base64.DEFAULT);
                            String newCommunityId = new String(communityBytes, AppConstants.UTF_8);
                            into.putExtra(AppConstants.COMMUNITY_ID, Long.parseLong(newCommunityId));
                            into.putExtra(AppConstants.COMMUNITY_POST_ID, Long.parseLong(dataIdString));
                        }
                    }
                } else {
                    int indexOfSecondBackSlace = AppUtils.findNthIndexOf(communityDetail, AppConstants.BACK_SLASH, 2);
                    String communityId = communityDetail.substring(indexOfSecondBackSlace + 1, communityDetail.length());
                    byte[] communityBytes = Base64.decode(communityId, Base64.DEFAULT);
                    String newCommunityId = new String(communityBytes, AppConstants.UTF_8);
                    into.putExtra(AppConstants.COMMUNITY_ID, Long.parseLong(newCommunityId));
                }
                into.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(into);
                finish();
            } catch (Exception e) {
                Intent into = new Intent(this, HomeActivity.class);
                into.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(into);
                finish();

            }

        }
        //In case of profile
        else if ((AppConstants.USER_PROFILE_URL).equalsIgnoreCase(baseUrl) || AppConstants.USER_PROFILE_URL_COM.equalsIgnoreCase(baseUrl) && AppConstants.USER_PROFILE_URL.length() < fullLength) {
            Intent into = new Intent(this, ProfileActicity.class);
            into.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(into);
            finish();

        } else {
            Toast.makeText(SheroesDeepLinkingActivity.this, AppConstants.WEB_BROWSER_MASSAGE, Toast.LENGTH_SHORT).show();
            Intent into = new Intent(Intent.ACTION_VIEW);
            into.setData(Uri.parse(urlSharedViaSocial));
            into.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(into);
            finish();
        }

    }

    private int countBackSlash(String url) {
        int counter = 0;
        for (int i = 0; i < url.length(); i++) {
            if (url.charAt(i) == '/') {
                counter++;
            }
        }
        return counter;
    }
}