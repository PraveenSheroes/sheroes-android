package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.ContactDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.UserContactDetail;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 14/02/18.
 */

public class ContactCardHolder extends BaseViewHolder<UserContactDetail> {
    @Bind(R.id.tv_contact_name)
    TextView tvContactName;
    @Bind(R.id.btn_invite_friend)
    Button btnInviteFriend;
    private ContactDetailCallBack mPostDetailCallback;
    private UserContactDetail contactDetail;
    public ContactCardHolder(View itemView, ContactDetailCallBack postDetailCallBack) {
        super(itemView);
        this.mPostDetailCallback = postDetailCallBack;
        ButterKnife.bind(this, itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);

    }

    @Override
    public void bindData(UserContactDetail contactDetail, Context context, int position) {
        this.contactDetail=contactDetail;
        contactDetail.setItemPosition(position);
        if(StringUtil.isNotNullOrEmptyString(contactDetail.getName()))
        {
            String str=contactDetail.getName();
            tvContactName.setText(str);
        }
    }
    @OnClick( R.id.btn_invite_friend)
    public void inviteFriendClicked() {
        mPostDetailCallback.onContactClicked(contactDetail,btnInviteFriend);
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {

    }
}
