package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 24-01-2017.
 */

public class CommentHolder extends BaseViewHolder<CommentReactionDoc> {
    private final String TAG = LogUtils.makeLogTag(CommentHolder.class);
    @Inject
    Preference<LoginResponse> userPreference;
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#323940'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";
    @Bind(R.id.li_list_comment)
    LinearLayout liListComment;
    @Bind(R.id.iv_list_comment_profile_pic)
    CircleImageView ivListCommentProfilePic;
    @Bind(R.id.tv_list_user_comment)
    TextView tvUserComment;
    @Bind(R.id.tv_user_comment_list_menu)
    TextView tvUserCommentListMenu;
    Context mContext;
    BaseHolderInterface viewInterface;
    private CommentReactionDoc dataItem;

    public CommentHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public void bindData(CommentReactionDoc item, final Context context, int position) {
        this.dataItem = item;
        this.mContext = context;
        if (dataItem.isMyOwnParticipation()) {
            tvUserCommentListMenu.setVisibility(View.VISIBLE);
        }
        ivListCommentProfilePic.setCircularImage(true);
        if(item.isAnonymous())
        {
            String userName = LEFT_HTML_TAG_FOR_COLOR +mContext.getString(R.string.ID_ANONYMOUS)+ RIGHT_HTML_TAG_FOR_COLOR;
            ivListCommentProfilePic.setImageResource(R.drawable.ic_add_city_icon);
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvUserComment.setText(Html.fromHtml(userName + AppConstants.SPACE + item.getComment(), 0)); // for 24 api and more
            } else {
                tvUserComment.setText(Html.fromHtml(userName + AppConstants.SPACE + item.getComment()));// or for older api
            }
        }
        else {
            if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getFirstName())) {
                String userName = LEFT_HTML_TAG_FOR_COLOR + userPreference.get().getUserSummary().getFirstName() + RIGHT_HTML_TAG_FOR_COLOR;
                if (StringUtil.isNotNullOrEmptyString(dataItem.getComment())) {
                    ivListCommentProfilePic.bindImage(dataItem.getParticipantImageUrl());
                    if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                        tvUserComment.setText(Html.fromHtml(userName + AppConstants.SPACE + dataItem.getComment(), 0)); // for 24 api and more
                    } else {
                        tvUserComment.setText(Html.fromHtml(userName + AppConstants.SPACE + dataItem.getComment()));// or for older api
                    }
                }
            }
        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_user_comment_list_menu)
    public void onCommentMenuClick() {
        dataItem.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(dataItem, tvUserCommentListMenu);
    }

    @Override
    public void onClick(View view) {
    }

}