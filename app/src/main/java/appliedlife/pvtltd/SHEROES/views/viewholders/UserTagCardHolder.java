package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.UserTagCallback;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.TaggedUserPojo;
import appliedlife.pvtltd.SHEROES.usertagging.suggestions.interfaces.Suggestible;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 05/03/18.
 */

public class UserTagCardHolder extends BaseViewHolder<Suggestible> {
    private Suggestible suggestible;
    private UserTagCallback userTagCallback;
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


    public UserTagCardHolder(View itemView, UserTagCallback userTagCallback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.userTagCallback = userTagCallback;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);

    }

    @Override
    public void bindData(Suggestible suggestible, Context context, int position) {
        this.suggestible = suggestible;
        int userId = ((TaggedUserPojo) suggestible).getUserId();
        if(userId==0)
        {
            liSocialUser.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
            mText.setVisibility(View.VISIBLE);
        }else
        {
            String name = ((TaggedUserPojo) suggestible).getName();
            liSocialUser.setVisibility(View.VISIBLE);
            mText.setVisibility(View.GONE);
            viewLine.setVisibility(View.VISIBLE);
            if (StringUtil.isNotNullOrEmptyString(name)) {
                mText.setVisibility(View.GONE);
                mTvName.setText(name);

            }
            String imageUrl = ((TaggedUserPojo) suggestible).getAuthorImageUrl();
            if (StringUtil.isNotNullOrEmptyString(imageUrl)) {
                ivUserPicCircleIcon.setCircularImage(true);
                ivUserPicCircleIcon.bindImage(imageUrl);
            }
        }
    }

    @OnClick(R.id.li_social_user)
    public void inviteFriendClicked() {
        userTagCallback.onSuggestedUserClicked(suggestible, liSocialUser);
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {

    }
}

