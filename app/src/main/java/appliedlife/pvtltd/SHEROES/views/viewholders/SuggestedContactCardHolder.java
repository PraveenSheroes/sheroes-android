package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.ContactDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 19/02/18.
 */

public class SuggestedContactCardHolder extends BaseViewHolder<UserSolrObj> {
    @Bind(R.id.tv_suggested_contact_name)
    TextView tvContactName;
    @Bind(R.id.btn_follow_friend)
    Button btnFollowFriend;
    private ContactDetailCallBack mPostDetailCallback;
    private UserSolrObj mUseSolarObj;
    public SuggestedContactCardHolder(View itemView, ContactDetailCallBack postDetailCallBack) {
        super(itemView);
        this.mPostDetailCallback = postDetailCallBack;
        ButterKnife.bind(this, itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);

    }

    @Override
    public void bindData(UserSolrObj userSolrObj, Context context, int position) {
        this.mUseSolarObj =userSolrObj;
        mUseSolarObj.setItemPosition(position);
        if(StringUtil.isNotNullOrEmptyString(mUseSolarObj.getNameOrTitle()))
        {
            String str= mUseSolarObj.getNameOrTitle();
            tvContactName.setText(str);
        }
    }
    @OnClick( R.id.btn_follow_friend)
    public void inviteFriendClicked() {
        mPostDetailCallback.onSuggestedContactClicked(mUseSolarObj, btnFollowFriend);
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {

    }
}
