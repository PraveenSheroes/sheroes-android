package appliedlife.pvtltd.SHEROES.utils;

import android.content.Context;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
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
        FeedDetail feedDetail = new FeedDetail();
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add(imageSource);
        // TODO: ujjwal
        //feedDetail.setImageUrls(imageUrls);
        AlbumActivity.navigateTo((ArticleActivity) mContext, feedDetail, ArticleActivity.SCREEN_LABEL, null);
    }
}
