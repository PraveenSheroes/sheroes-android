package appliedlife.pvtltd.SHEROES.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.ReferrerBus;

/**
 * Created by Ujjwal on 21-03-2018.
 */

public class CustomInstallTrackersReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle extras = intent.getExtras();
        if (null == extras) {
            return;
        }
        final String referrer = extras.getString("referrer");
        if (null == referrer) {
            return;
        }

        SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
        SharedPreferences.Editor editor= prefs.edit();
        editor.putString(AppConstants.REFERRER, referrer);

        final Matcher sourceMatcher = UTM_SOURCE_PATTERN.matcher(referrer);
        final String source = find(sourceMatcher);
        if (null != source) {
            editor.putString(AppConstants.UTM_SOURCE, source).apply();
        }

        final Matcher mediumMatcher = UTM_MEDIUM_PATTERN.matcher(referrer);
        final String medium = find(mediumMatcher);
        if (null != medium) {
            editor.putString(AppConstants.UTM_MEDIUM, medium).apply();
        }

        final Matcher campaignMatcher = UTM_CAMPAIGN_PATTERN.matcher(referrer);
        final String campaign = find(campaignMatcher);
        if (null != campaign) {
            editor.putString(AppConstants.UTM_CAMPAIGN, campaign);
        }

        final Matcher contentMatcher = UTM_CONTENT_PATTERN.matcher(referrer);
        final String content = find(contentMatcher);
        if (null != content) {
            editor.putString(AppConstants.UTM_CONTENT, content);
        }

        final Matcher termMatcher = UTM_TERM_PATTERN.matcher(referrer);
        final String term = find(termMatcher);
        if (null != term) {
            editor.putString(AppConstants.UTM_TERM, term);
        }
        editor.apply();

        //send event of received referrer
        ReferrerBus.getInstance().post(true);
    }


    private String find(Matcher matcher) {
        if (matcher.find()) {
            final String encoded = matcher.group(2);
            if (null != encoded) {
                try {
                    return URLDecoder.decode(encoded, "UTF-8");
                } catch (final UnsupportedEncodingException e) {
                    LogUtils.error(LOGTAG, "Could not decode a parameter into UTF-8");
                }
            }
        }
        return null;
    }

    private final Pattern UTM_SOURCE_PATTERN = Pattern.compile("(^|&)utm_source=([^&#=]*)([#&]|$)");
    private final Pattern UTM_MEDIUM_PATTERN = Pattern.compile("(^|&)utm_medium=([^&#=]*)([#&]|$)");
    private final Pattern UTM_CAMPAIGN_PATTERN = Pattern.compile("(^|&)utm_campaign=([^&#=]*)([#&]|$)");
    private final Pattern UTM_CONTENT_PATTERN = Pattern.compile("(^|&)utm_content=([^&#=]*)([#&]|$)");
    private final Pattern UTM_TERM_PATTERN = Pattern.compile("(^|&)utm_term=([^&#=]*)([#&]|$)");

    private static final String LOGTAG = "MixpanelAPI.InstRfrRcvr";
}
