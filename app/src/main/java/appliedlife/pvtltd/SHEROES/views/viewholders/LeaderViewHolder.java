package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.f2prateek.rx.preferences.Preference;

import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.LeaderObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityOpenAboutFragment;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 18/09/17.
 */

public class LeaderViewHolder extends BaseViewHolder<FeedDetail> {
    @Bind(R.id.profile_pic)
    ImageView mProfilePic;

    @Bind(R.id.crown)
    ImageView mCrown;

    @Bind(R.id.name)
    TextView mName;

    @Bind(R.id.likes_comments_count)
    TextView mDescription;

    @BindDimen(R.dimen.dp_size_40)
    int mUserPicSize;

    @BindDimen(R.dimen.dp_size_24)
    int mCrownHeight;

    @BindDimen(R.dimen.dp_size_18)
    int mCrownWidth;

    @Inject
    Preference<LoginResponse> userPreference;
    BaseHolderInterface viewInterface;
    public LeaderViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
    }

    @Override
    public void bindData(FeedDetail feedDetail, final Context context, int position) {
        LeaderObj leaderObj = null;
        if (feedDetail instanceof LeaderObj) {
            leaderObj = (LeaderObj) feedDetail;
        }
        if (leaderObj != null) {
            mDescription.setText(leaderObj.getDescription());
            if (CommonUtil.isNotEmpty(leaderObj.crownUrl)) {
                String trophyImageUrl = CommonUtil.getImgKitUri(leaderObj.crownUrl, mCrownWidth, mCrownHeight);
                Glide.with(mCrown.getContext())
                        .load(trophyImageUrl)
                        .into(mCrown);
            }
            mName.setText(leaderObj.getNameOrTitle());
            if (leaderObj.getThumbnailImageUrl() != null && CommonUtil.isNotEmpty(leaderObj.getThumbnailImageUrl())) {
                String userImage = CommonUtil.getImgKitUri(leaderObj.getThumbnailImageUrl(), mUserPicSize, mUserPicSize);
                Glide.with(mProfilePic.getContext())
                        .load(userImage)
                        .bitmapTransform(new CommunityOpenAboutFragment.CircleTransform(context))
                        .into(mProfilePic);
            }
        }
    }
    @Override
    public void viewRecycled() {

    }
    @Override
    public void onClick(View view) {
    /*    if (viewInterface instanceof FeedItemCallback) {
            ((FeedItemCallback) viewInterface).onChampionProfileClicked();
        }*/
    }

}