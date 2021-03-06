package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IFollowCallback;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author ravi
 * This class handle the unfollow confirmation dilaog , if user press unfollow button will send api call to unfollow
 * particular user.
 */
public class UnFollowDialogFragment extends BaseDialogFragment implements HomeView {

    //region injected variable
    @Inject
    HomePresenter mHomePresenter;
    //endregion injected variable

    //region bind variable
    @Bind(R.id.user_img_icon)
    CircleImageView mCircleImageView;
    @Bind(R.id.title)
    TextView mTitle;
    @BindDimen(R.dimen.dp_size_87)
    int mProfilePicSize;
    //endregion bind variable

    //region view variable
    private UserSolrObj mUserSolrObj;
    private PublicProfileListRequest mUnFollowRequest;
    private Activity mContext;
    //endregion view variable

    //region member variable
    private String mSourceScreenName;
    private boolean mIsChampion;
    private boolean mIsSelfProfile;
    //endregion member variable

    //region override methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (getActivity() == null) return null;

        mContext = getActivity();
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.unfollow_confirmation_dialog, container, false);
        ButterKnife.bind(this, view);
        mHomePresenter.attachView(this);
        Parcelable parcelable = getArguments().getParcelable(AppConstants.USER);
        Parcelable unFollowRequestParcelable = getArguments().getParcelable(AppConstants.UNFOLLOW);
        mSourceScreenName = getArguments().getString(AppConstants.SOURCE_NAME);

        mIsChampion = getArguments().getBoolean(AppConstants.IS_CHAMPION_ID);
        mIsSelfProfile = getArguments().getBoolean(AppConstants.IS_SELF_PROFILE);

        if (null != parcelable) {
            mUserSolrObj = Parcels.unwrap(parcelable);
        }

        if (null != unFollowRequestParcelable) {
            mUnFollowRequest = Parcels.unwrap(unFollowRequestParcelable);
        }

        if (StringUtil.isNotNullOrEmptyString(mUserSolrObj.getImageUrl())) {
            String imageUrl = CommonUtil.getThumborUri(mUserSolrObj.getImageUrl(), mProfilePicSize, mProfilePicSize);
            mCircleImageView.setCircularImage(true);
            mCircleImageView.bindImage(imageUrl);
        }

        mTitle.setText(getString(R.string.unfollow_profile, mUserSolrObj.getNameOrTitle()));

        return view;
    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (feedParticipationEnum) {
            case FOLLOW_UNFOLLOW:
                if (mContext instanceof IFollowCallback) {
                    UserSolrObj userSolrObj = (UserSolrObj) baseResponse;
                    ((IFollowCallback) mContext).onProfileFollowed(userSolrObj);
                    mContext = null;
                }
                break;
            default:
        }
    }
    //endregion override methods

    //region onclick methods
    @OnClick(R.id.cancel)
    public void onCancelClick() {
        dismiss();
    }

    @OnClick(R.id.unfollow)
    public void unFollowClick() {
        if (null == mUnFollowRequest) return;

        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserSolrObj.getIdOfEntityOrParticipant()))
                        .name(mUserSolrObj.getNameOrTitle())
                        .isMentor(mIsChampion)
                        .isOwnProfile(mIsSelfProfile)
                        .build();
        AnalyticsManager.trackEvent(Event.PROFILE_UNFOLLOWED, mSourceScreenName, properties);

        mHomePresenter.getUnFollowFromPresenter(mUnFollowRequest, mUserSolrObj);
        dismiss();
    }
    //endregion onclick methods
}
