package appliedlife.pvtltd.SHEROES.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.SpamContentType;
import appliedlife.pvtltd.SHEROES.models.Spam;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamPostRequest;

/**
 * Created by ravi on 13/04/18.
 * Common functionality for spams
 */

public class SpamUtil {

    /**
     * Add the spam options to radio group
     * @param context context
     * @param spamOptions spam options list
     * @param radioGroup radio Group
     */
    public static void  addRadioToView(Context context, List<Spam> spamOptions, RadioGroup radioGroup) {
        if (spamOptions != null) {
            int i = 0;
            for (final Spam spam : spamOptions) {
                RadioButton radioButton = (RadioButton) LayoutInflater.from(context).inflate(R.layout.radio_button_custom, null);
                RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(CommonUtil.convertDpToPixel(8, context), CommonUtil.convertDpToPixel(10, context), CommonUtil.convertDpToPixel(8, context), 0);
                radioButton.setText(spam.getLabel());
                radioButton.setLayoutParams(layoutParams);

                radioButton.setId(i);
                radioButton.setTag(spam);
                radioGroup.addView(radioButton);
                i++;
            }
        }
    }

    //Hide radio button other than the currenly selected
    public static void hideSpamReason(RadioGroup radioGroup, int selectedOptionId) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            int id = radioGroup.getChildAt(i).getId();
            if (id != selectedOptionId) {
                radioGroup.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    public static SpamPostRequest spamRequestBuilder(FeedDetail feedDetail, View view, long loggedInUserId) {

        if (feedDetail instanceof UserPostSolrObj) {
            if (view.getId() == R.id.tv_feed_community_post_user_menu || view.getId() == R.id.post_menu) {
                return createSpamPostRequest((UserPostSolrObj) feedDetail, false, loggedInUserId);
            } else if (view.getId() == R.id.tv_feed_community_post_user_comment_post_menu) {
                return createSpamPostRequest((UserPostSolrObj) feedDetail, true, loggedInUserId);
            }
        } else if (feedDetail instanceof ArticleSolrObj) {
            if (view.getId() == R.id.tv_feed_article_user_comment_post_menu || view.getId() == R.id.delete) {
                return createArticleCommentSpamRequest((ArticleSolrObj) feedDetail, loggedInUserId);
            }
        } else if(feedDetail instanceof UserSolrObj) {
            return createProfileSpamByUser((UserSolrObj) feedDetail, loggedInUserId);
        }
        return null;
    }

    private static SpamPostRequest createProfileSpamByUser(UserSolrObj userSolrObj, long currentUserId) {

        SpamPostRequest spamPostRequest = new SpamPostRequest();
        spamPostRequest.setModelType("USER");
        spamPostRequest.setCommunityId(0);
        spamPostRequest.setSpamReportedOn(userSolrObj.getIdOfEntityOrParticipant());
        spamPostRequest.setModelId(userSolrObj.getIdOfEntityOrParticipant());
        spamPostRequest.setSpamContentType(SpamContentType.USER);
        spamPostRequest.setSpamReportedBy(currentUserId);

        return spamPostRequest;
    }

    public static SpamPostRequest spamRequestBuilder(Comment comment, View view, long currentUserId) {
        SpamPostRequest spamPostRequest = new SpamPostRequest();
        spamPostRequest.setModelId(comment.getCommentsId());
        spamPostRequest.setSpamReportedBy(currentUserId);//todo - chk with the case of admin , community post
        spamPostRequest.setCommunityId(Long.valueOf(comment.getCommunityId()));
        spamPostRequest.setModelType("COMMENT");
        spamPostRequest.setSpamContentType(SpamContentType.COMMENT);
        spamPostRequest.setSpamReportedOn(comment.getParticipantId());
        return spamPostRequest;
    }

    private static SpamPostRequest createArticleCommentSpamRequest(ArticleSolrObj userPostSolrObj, long currentUserId) {

        SpamPostRequest spamPostRequest = new SpamPostRequest();

        spamPostRequest.setSpamReportedBy(currentUserId);//todo - chk with the case of admin , community post
        if(userPostSolrObj.getLastComments().size()>0) {
            Comment comment = userPostSolrObj.getLastComments().get(0);

            spamPostRequest.setModelId(comment.getCommentsId());
            //spamPostRequest.setCommunityId(Long.valueOf(comment.getCommunityId())); //not id for article
            spamPostRequest.setModelType("ARTICLE_COMMENT");
            spamPostRequest.setSpamContentType(SpamContentType.COMMENT);
            spamPostRequest.setSpamReportedOn(comment.getParticipantId());
        }
        return spamPostRequest;
    }

    private static SpamPostRequest createSpamPostRequest(UserPostSolrObj userPostSolrObj, boolean isComment, long currentUserId) {

        SpamPostRequest spamPostRequest = new SpamPostRequest();
        spamPostRequest.setSpamReportedBy(currentUserId);
        if(isComment) {
            if(userPostSolrObj.getLastComments().size()>0) {
                Comment comment = userPostSolrObj.getLastComments().get(0);

                spamPostRequest.setModelId(comment.getCommentsId());
                spamPostRequest.setCommunityId(Long.valueOf(comment.getCommunityId()));
                spamPostRequest.setModelType(SpamContentType.COMMENT.name());
                spamPostRequest.setSpamContentType(SpamContentType.COMMENT);
                spamPostRequest.setSpamReportedOn(comment.getParticipantId());
            }
        } else {
            spamPostRequest.setModelType(SpamContentType.POST.name());
            spamPostRequest.setSpamContentType(SpamContentType.POST);
            spamPostRequest.setCommunityId(userPostSolrObj.getCommunityId());
            spamPostRequest.setSpamReportedOn(userPostSolrObj.getAuthorId());
            spamPostRequest.setModelId(userPostSolrObj.getIdOfEntityOrParticipant());
        }

        return spamPostRequest;

    }

}
