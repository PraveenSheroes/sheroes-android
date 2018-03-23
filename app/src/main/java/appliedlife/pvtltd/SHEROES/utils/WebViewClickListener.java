package appliedlife.pvtltd.SHEROES.utils;

import android.content.Context;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.views.activities.AlbumActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;

/**
 * Created by Ujjwal on 24-10-2017.
 */
public class WebViewClickListener {
    Context mContext;

    /**
     * Instantiate the interface and set the context
     */
    public WebViewClickListener(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void openImageActivity(String imageSource) {
        UserPostSolrObj userPostSolrObj = new UserPostSolrObj();
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add(imageSource);
        userPostSolrObj.setImageUrls(imageUrls);
        AlbumActivity.navigateTo((ArticleActivity) mContext, userPostSolrObj, ArticleActivity.SCREEN_LABEL, null);
    }
}
