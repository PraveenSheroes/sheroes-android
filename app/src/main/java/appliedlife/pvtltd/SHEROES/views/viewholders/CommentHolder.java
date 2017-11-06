package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.hashTagColorInString;
import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.linkifyURLs;

/**
 * Created by Praveen_Singh on 24-01-2017.
 */

public class CommentHolder extends BaseViewHolder<Comment> {
    private final String TAG = LogUtils.makeLogTag(CommentHolder.class);
    @Inject
    DateUtil mDateUtil;
    @Inject
    Preference<LoginResponse> userPreference;
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#323940'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";
    @Bind(R.id.li_list_comment)
    LinearLayout liListComment;
    @Bind(R.id.iv_list_comment_profile_pic_verified)
    ImageView ivListCommentProfilePicVerified;
    @Bind(R.id.iv_list_comment_profile_pic)
    CircleImageView ivListCommentProfilePic;
    @Bind(R.id.tv_list_user_comment)
    TextView tvUserComment;
    @Bind(R.id.tv_user_comment_list_menu)
    TextView tvUserCommentListMenu;
    @Bind(R.id.tv_list_user_comment_time)
    TextView tvListCommentTime;

    Context mContext;
    BaseHolderInterface viewInterface;
    private Comment dataItem;
    private long mAdminId;
    public CommentHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            if (null != userPreference.get().getUserSummary().getUserBO()) {
                mAdminId = userPreference.get().getUserSummary().getUserBO().getUserTypeId();
            }
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public void bindData(Comment item, final Context context, int position) {
        this.dataItem = item;
        this.mContext = context;
        if (StringUtil.isNotNullOrEmptyString(dataItem.getPostedDate())) {
            tvListCommentTime.setText(dataItem.getPostedDate());
        } else {
            tvListCommentTime.setText(mContext.getString(R.string.ID_JUST_NOW));
        }

        if (dataItem.isMyOwnParticipation()||mAdminId==AppConstants.TWO_CONSTANT) {
            tvUserCommentListMenu.setVisibility(View.VISIBLE);
        } else {
            tvUserCommentListMenu.setVisibility(View.GONE);
        }
        ivListCommentProfilePic.setCircularImage(true);
        if (item.isAnonymous()&&StringUtil.isNotNullOrEmptyString(dataItem.getParticipantName())) {
            ivListCommentProfilePic.setImageResource(R.drawable.ic_anonomous);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(dataItem.getParticipantName()).append(AppConstants.COLON).append(AppConstants.SPACE).append(dataItem.getComment());
            Spannable getCommentString = new SpannableString(stringBuilder.toString());
            int size = dataItem.getParticipantName().length() + 1;
            getCommentString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, size, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            getCommentString.setSpan(new StyleSpan(Typeface.BOLD), 0, size, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            tvUserComment.setText(hashTagColorInString(getCommentString.toString()));
            linkifyURLs(tvUserComment);
            ivListCommentProfilePicVerified.setVisibility(View.GONE);
        } else {
            if (StringUtil.isNotNullOrEmptyString(dataItem.getComment())&&StringUtil.isNotNullOrEmptyString(dataItem.getParticipantName())) {
                ivListCommentProfilePic.bindImage(dataItem.getParticipantImageUrl());
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(dataItem.getParticipantName()).append(AppConstants.COLON).append(AppConstants.SPACE).append(dataItem.getComment());
                Spannable getCommentString = new SpannableString(stringBuilder.toString());
                int size = dataItem.getParticipantName().length() + 1;
                getCommentString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, size, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                getCommentString.setSpan(new StyleSpan(Typeface.BOLD), 0, size, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                tvUserComment.setText(hashTagColorInString(getCommentString.toString()));
                linkifyURLs(tvUserComment);
                if (!dataItem.getParticipantName().equalsIgnoreCase(mContext.getString(R.string.ID_COMMUNITY_ANNONYMOUS))) {
                    if (dataItem.isVerifiedMentor()) {
                        ivListCommentProfilePicVerified.setVisibility(View.VISIBLE);
                    } else {
                        ivListCommentProfilePicVerified.setVisibility(View.GONE);
                    }
                }else {
                    ivListCommentProfilePicVerified.setVisibility(View.GONE);
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
    @OnClick(R.id.tv_list_user_comment)
    public void onCommentWithNameClick() {
        if (dataItem.isVerifiedMentor()) {
            viewInterface.championProfile(dataItem, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }
    }


    @Override
    public void onClick(View view) {
    }

}