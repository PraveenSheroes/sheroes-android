package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleDetailPojo;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleDetailActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 08-02-2017.
 */

public class ArticleDetailHolder extends BaseViewHolder<ArticleDetailPojo> {
    private final String TAG = LogUtils.makeLogTag(ArticleCardHolder.class);
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#50e3c2'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";
    @Bind(R.id.iv_article_detail_card_circle_icon)
    CircleImageView ivArticleDetailCardCircleIcon;
    @Bind(R.id.tv_html_data)
    TextView tvHtmlData;
    @Bind(R.id.tv_article_detail_tag)
    TextView tvArticleDetailTag;
    @Bind(R.id.tv_article_detail_time)
    TextView tvArticleDetailTime;
    @Bind(R.id.tv_article_detail_icon_name)
    TextView tvArticleDetailIconName;
    @Bind(R.id.tv_article_detail_description)
    TextView tvArticleDetailDescription;
    @Bind(R.id.tv_article_detail_user_reaction)
    TextView tvArticleDetailUserReaction;
    @Bind(R.id.tv_article_detail_user_comment)
    TextView tvArticleDetailUserComment;
    @Bind(R.id.tv_article_detail_total_reactions)
    TextView tvArticleDetailTotalReaction;
    @Bind(R.id.tv_article_detail_total_replies)
    TextView tvArticleDetailTotalReplies;
    @Bind(R.id.tv_article_detail_user_comment_post)
    TextView tvArticleDetailUserCommentPost;
    @Bind(R.id.li_article_detail_join_conversation)
    LinearLayout liArticleDetailJoinConversation;
    @Bind(R.id.iv_article_detail_register_user_pic)
    CircleImageView ivArticleDetailRegisterUserPic;
    @Bind(R.id.iv_article_detail_user_pic)
    CircleImageView ivArticleDetailUserPic;

    @Bind(R.id.tv_article_detail_view_more)
    TextView tvArticleDetailViewMore;
    @Bind(R.id.tv_article_detail_user_comment_post_menu)
    TextView tvArticleDetailUserCommentPostMenu;
    @Bind(R.id.tv_article_detail_user_comment_post_menu_second)
    TextView tvArticleDetailUserCommentPostMenuSecond;
    @Bind(R.id.tv_article_detail_user_comment_post_menu_third)
    TextView tvArticleDetailUserCommentPostMenuThird;
    BaseHolderInterface viewInterface;
    private ArticleDetailPojo dataItem;
    private FeedDetail mFeedDetail;
    private Context mContext;

    public ArticleDetailHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;

    }

    @Override
    public void bindData(ArticleDetailPojo item, final Context context, int position) {
        this.dataItem = item;
        this.mContext=context;
        imageOperations(context);
    }

    private void imageOperations(Context context) {
        mFeedDetail = dataItem.getFeedDetail();
        if (null != mFeedDetail) {
            if(StringUtil.isNotNullOrEmptyString(mFeedDetail.getNameOrTitle())) {
                ((ArticleDetailActivity) mContext).mCollapsingToolbarLayout.setTitle(mFeedDetail.getNameOrTitle());
            }
            String feedCircleIconUrl = mFeedDetail.getAuthorImageUrl();
            if (StringUtil.isNotNullOrEmptyString(feedCircleIconUrl)) {
                ivArticleDetailCardCircleIcon.setCircularImage(true);
                ivArticleDetailCardCircleIcon.bindImage(feedCircleIconUrl);
            }
            String description = mFeedDetail.getDescription();
            if (StringUtil.isNotNullOrEmptyString(description)) {
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    tvHtmlData.setText(Html.fromHtml(description, 0)); // for 24 api and more
                } else {
                    tvHtmlData.setText(Html.fromHtml(description));// or for older api
                }
            }
        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_article_detail_user_reaction)
    public void userReactionClick() {
        viewInterface.handleOnClick(mFeedDetail, tvArticleDetailUserReaction);
    }

    @OnClick(R.id.tv_article_detail_user_comment)
    public void userCommentClick() {
        viewInterface.handleOnClick(mFeedDetail, tvArticleDetailUserComment);
    }

    @OnClick(R.id.tv_article_detail_total_replies)
    public void userRepliesClick() {
        viewInterface.handleOnClick(mFeedDetail, tvArticleDetailTotalReplies);
    }

    @OnClick(R.id.li_article_detail_join_conversation)
    public void joinConversationClick() {
        viewInterface.handleOnClick(mFeedDetail, liArticleDetailJoinConversation);
    }

    @OnClick(R.id.tv_article_detail_view_more)
    public void viewMoreClick() {
        viewInterface.handleOnClick(mFeedDetail, tvArticleDetailViewMore);
    }

    @OnClick(R.id.tv_article_detail_user_comment_post_menu)
    public void menuClick() {
        viewInterface.handleOnClick(mFeedDetail, tvArticleDetailUserCommentPostMenu);
    }

    @OnClick(R.id.tv_article_detail_user_comment_post_menu_second)
    public void menuSecondClick() {
        viewInterface.handleOnClick(mFeedDetail, tvArticleDetailUserCommentPostMenuSecond);
    }

    @OnClick(R.id.tv_article_detail_user_comment_post_menu_third)
    public void menuThirdClick() {
        viewInterface.handleOnClick(mFeedDetail, tvArticleDetailUserCommentPostMenuThird);
    }

    @Override
    public void onClick(View view) {

    }

}
