package appliedlife.pvtltd.SHEROES.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.SpamContentType;
import appliedlife.pvtltd.SHEROES.models.DeactivationReason;
import appliedlife.pvtltd.SHEROES.models.Spam;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamPostRequest;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;

/**
 * Created by ravi on 13/04/18.
 * Common functionality for spams
 */

public class SpamUtil {

    /**
     * Add the spam options to radio group
     *
     * @param context     context
     * @param deactivationReasons deactivation reasons
     * @param radioGroup  radio Group
     */
    public static void addDeactivationReasonsToRadioGroup(Context context, List<DeactivationReason> deactivationReasons, RadioGroup radioGroup) {
        if (deactivationReasons != null) {
            int i = 0;
            for (final DeactivationReason deactivationReason : deactivationReasons) {
                RadioButton radioButton = (RadioButton) LayoutInflater.from(context).inflate(R.layout.radio_button_custom, null);
                RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(CommonUtil.convertDpToPixel(16, context), CommonUtil.convertDpToPixel(10, context), CommonUtil.convertDpToPixel(8, context), 0);
                radioButton.setText(deactivationReason.getDeactivationReason());
                radioButton.setLayoutParams(layoutParams);
                radioButton.setId(i);
                radioButton.setTag(deactivationReason);
                radioGroup.addView(radioButton);
                i++;
            }
        }
    }

    /**
     * Add the spam options to radio group
     *
     * @param context     context
     * @param spamOptions spam options list
     * @param radioGroup  radio Group
     */
    public static void addRadioToView(Context context, List<Spam> spamOptions, RadioGroup radioGroup) {
        if (spamOptions != null) {
            int i = 0;
            for (final Spam spam : spamOptions) {
                RadioButton radioButton = (RadioButton) LayoutInflater.from(context).inflate(R.layout.radio_button_custom, null);
                RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(CommonUtil.convertDpToPixel(16, context), CommonUtil.convertDpToPixel(10, context), CommonUtil.convertDpToPixel(8, context), 0);
                radioButton.setText(spam.getLabel());
                radioButton.setLayoutParams(layoutParams);
                radioButton.setId(i);
                radioButton.setTag(spam);
                radioGroup.addView(radioButton);
                i++;
            }
        }
    }

    //Hide radio button other than the currently selected
    public static void hideSpamReason(RadioGroup radioGroup, int selectedOptionId) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            int id = radioGroup.getChildAt(i).getId();
            if (id != selectedOptionId) {
                radioGroup.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    public static SpamPostRequest createProfileSpamByUser(UserSolrObj userSolrObj, long currentUserId) {

        SpamPostRequest spamPostRequest = new SpamPostRequest();
        spamPostRequest.setModelType(SpamContentType.USER.name());
        spamPostRequest.setCommunityId(Long.parseLong("0"));
        spamPostRequest.setSpamReportedOn(userSolrObj.getIdOfEntityOrParticipant());
        spamPostRequest.setModelId(userSolrObj.getIdOfEntityOrParticipant());
        spamPostRequest.setSpamReportedBy(currentUserId);

        return spamPostRequest;
    }

    public static SpamPostRequest spamCommentRequestBuilder(Comment comment, long currentUserId) {
        SpamPostRequest spamPostRequest = new SpamPostRequest();
        spamPostRequest.setModelId(comment.getCommentsId());
        spamPostRequest.setSpamReportedBy(currentUserId);
        if(StringUtil.isNotNullOrEmptyString(comment.getCommunityId())) {
            spamPostRequest.setCommunityId(Long.valueOf(comment.getCommunityId()));
        }
        spamPostRequest.setModelType(SpamContentType.COMMENT.name());
        spamPostRequest.setSpamReportedOn(comment.getParticipantUserId());
        return spamPostRequest;
    }

    public static SpamPostRequest spamArticleCommentRequestBuilder(Comment comment, long currentUserId) {
        SpamPostRequest spamPostRequest = new SpamPostRequest();
        spamPostRequest.setModelId(comment.getCommentsId());
        spamPostRequest.setSpamReportedBy(currentUserId);
        spamPostRequest.setModelType(SpamContentType.ARTICLE_COMMENT.name());
        spamPostRequest.setSpamReportedOn(comment.getParticipantUserId());
        return spamPostRequest;
    }

    public static SpamPostRequest createSpamPostRequest(UserPostSolrObj userPostSolrObj, boolean isComment, long currentUserId) {

        SpamPostRequest spamPostRequest = new SpamPostRequest();
        spamPostRequest.setSpamReportedBy(currentUserId);
        if (isComment) {
            if (userPostSolrObj.getLastComments().size() > 0) {
                Comment comment = userPostSolrObj.getLastComments().get(0);

                spamPostRequest.setModelId(comment.getCommentsId());
                spamPostRequest.setCommunityId(Long.valueOf(comment.getCommunityId()));
                spamPostRequest.setModelType(SpamContentType.COMMENT.name());
                spamPostRequest.setSpamReportedOn(comment.getParticipantUserId());
            }
        } else {
            spamPostRequest.setModelType(SpamContentType.POST.name());
            spamPostRequest.setCommunityId(userPostSolrObj.getCommunityId());
            spamPostRequest.setSpamReportedOn(userPostSolrObj.getAuthorId());
            spamPostRequest.setModelId(userPostSolrObj.getIdOfEntityOrParticipant());
        }

        return spamPostRequest;

    }

}
