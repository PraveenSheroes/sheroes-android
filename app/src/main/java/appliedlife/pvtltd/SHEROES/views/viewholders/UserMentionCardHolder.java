package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.UserMentionSuggestionTagCallback;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.Mention;
import appliedlife.pvtltd.SHEROES.usertagging.suggestions.interfaces.Suggestible;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 05/03/18.
 */

public class UserMentionCardHolder extends BaseViewHolder<Suggestible> {
    private Suggestible suggestible;
    private UserMentionSuggestionTagCallback userMentionSuggestionTagCallback;
    @Bind(R.id.li_social_user)
    LinearLayout liSocialUser;
    @Bind(R.id.iv_user_pic)
    CircleImageView ivUserPicCircleIcon;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_text)
    TextView mText;
    @Bind(R.id.view_line)
    View viewLine;


    public UserMentionCardHolder(View itemView, UserMentionSuggestionTagCallback userMentionSuggestionTagCallback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.userMentionSuggestionTagCallback = userMentionSuggestionTagCallback;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);

    }

    @Override
    public void bindData(Suggestible suggestible, Context context, int position) {
        this.suggestible = suggestible;
        int userMentionRowCreater = ((Mention) suggestible).getUserId();
        if (userMentionRowCreater == AppConstants.USER_MENTION_NO_RESULT_FOUND) {
            liSocialUser.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
            mText.setVisibility(View.VISIBLE);
            String name = ((Mention) suggestible).getName();
            if (StringUtil.isNotNullOrEmptyString(name)) {
                mText.setText(name);
            } else {
                mText.setText(context.getString(R.string.no_result_found));
            }
        } else {
            String name = ((Mention) suggestible).getName();
            liSocialUser.setVisibility(View.VISIBLE);
            mText.setVisibility(View.GONE);
            viewLine.setVisibility(View.VISIBLE);
            if (StringUtil.isNotNullOrEmptyString(name)) {
                mText.setVisibility(View.GONE);
                mTvName.setText(name);

            }
            String imageUrl = ((Mention) suggestible).getAuthorImageUrl();
            if (StringUtil.isNotNullOrEmptyString(imageUrl)) {
                ivUserPicCircleIcon.setCircularImage(true);
                ivUserPicCircleIcon.bindImage(imageUrl);
            }
        }
    }

    @OnClick(R.id.li_social_user)
    public void inviteFriendClicked() {
        userMentionSuggestionTagCallback.onSuggestedUserClicked(suggestible, liSocialUser);
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {

    }
}

