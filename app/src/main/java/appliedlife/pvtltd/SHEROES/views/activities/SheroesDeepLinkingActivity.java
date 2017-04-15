package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;

/**
 * Created by Ajit Kumar on 11-04-2017.
 */

public class SheroesDeepLinkingActivity extends Activity {
    private Uri mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deeplinking_layout);
        if (null != getIntent()) {
            String baseUrl = "";
            int fullLength = 0;
            int indexOfFourthBackSlace;
            Intent intent = getIntent();
            if (null != intent.getData()) {
                mData = intent.getData();
                if (StringUtil.isNotNullOrEmptyString(mData.toString())) {
                    String url = mData.toString();

                    try {
                        if (null != url) {
                            indexOfFourthBackSlace = findNthIndexOf(url, AppConstants.BACK_SLASH, 4);
                            if (indexOfFourthBackSlace > 0) {
                                baseUrl = url.substring(0, indexOfFourthBackSlace);
                                //When Fourth back slace not available
                                if (baseUrl.equalsIgnoreCase(AppConstants.EMPTY_STRING)) {
                                    baseUrl = url;
                                } else {
                                    if (baseUrl.equalsIgnoreCase(AppConstants.USER_URL)) {
                                        baseUrl = url;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                    fullLength = url.length();

                    if (StringUtil.isNotNullOrEmptyString(baseUrl)) {

                        callActivities(url, baseUrl, fullLength);
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), AppConstants.INVALID_URL, Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void callActivities(String url, String baseUrl, int fullLength) {
        String text = AppConstants.EMPTY_STRING;
        //In case of Article
        if (AppConstants.ARTICLE_URL.equalsIgnoreCase(baseUrl) && AppConstants.ARTICLE_URL.length() < fullLength) {
            try {
                int sareid = url.lastIndexOf("/");
                String id = url.substring(sareid + 1, url.length());
                byte[] id1 = Base64.decode(id, Base64.DEFAULT);
                text = new String(id1, "UTF-8");
                Intent into = new Intent(SheroesDeepLinkingActivity.this, ArticleDetailActivity.class);
                into.putExtra(AppConstants.ARTICLE_ID, Long.parseLong(text));
                startActivity(into);
                finish();
            } catch (Exception e) {
                Intent into = new Intent(this, HomeActivity.class);
                startActivity(into);
                finish();
            }
        } else if (AppConstants.JOB_URL.equalsIgnoreCase(baseUrl) && AppConstants.JOB_URL.length() < fullLength) {
            try {
                int sareid = url.lastIndexOf("/");
                String id = url.substring(sareid + 1, url.length());
                byte[] id1 = Base64.decode(id, Base64.DEFAULT);
                text = new String(id1, "UTF-8");
                Intent into = new Intent(SheroesDeepLinkingActivity.this, JobDetailActivity.class);
                into.putExtra(AppConstants.JOB_ID, Long.parseLong(text));
                startActivity(into);
                finish();
            } catch (Exception e) {
                Intent into = new Intent(this, HomeActivity.class);
                startActivity(into);
                finish();
            }

        }
        //In case of communities
        else if (AppConstants.COMMUNITY_URL.equalsIgnoreCase(baseUrl) && AppConstants.COMMUNITY_URL.length() < fullLength) {
            try {
                int indexOfSecondBackSlace = findNthIndexOf(url, AppConstants.BACK_SLASH, 5);
                int sareid = url.lastIndexOf("/");
                String communityId = url.substring(indexOfSecondBackSlace + 1, sareid);
                String postid = url.substring(sareid + 1, url.length());
                byte[] id1 = Base64.decode(postid, Base64.DEFAULT);
                text = new String(id1, "UTF-8");
                byte[] id2 = Base64.decode(communityId, Base64.DEFAULT);
                String newcommunityId = new String(id2, "UTF-8");
                Intent into = new Intent(SheroesDeepLinkingActivity.this, CommunitiesDetailActivity.class);
                into.putExtra(AppConstants.COMMUNITY_ID, Long.parseLong(newcommunityId));
                into.putExtra(AppConstants.COMMUNITY_POST_ID, Long.parseLong(text));
                startActivity(into);
                finish();
            } catch (Exception e) {
                Intent into = new Intent(this, HomeActivity.class);
                startActivity(into);
                finish();

            }

        }
        //In case of profile
        else if ((AppConstants.USER_PROFILE_URL).equalsIgnoreCase(baseUrl)) {
            Intent into = new Intent(this, ProfileActicity.class);
            startActivity(into);
            finish();

        } else {
            Toast.makeText(SheroesDeepLinkingActivity.this, AppConstants.WEB_BROWSER_MASSAGE, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            finish();
        }

    }

    public static int findNthIndexOf(String str, String needle, int occurence)
            throws IndexOutOfBoundsException {
        int index = -1;
        Pattern p = Pattern.compile(needle, Pattern.MULTILINE);
        Matcher m = p.matcher(str);
        while (m.find()) {
            if (--occurence == 0) {
                index = m.start();
                break;
            }
        }
        if (index < 0) throw new IndexOutOfBoundsException();
        return index;
    }


}