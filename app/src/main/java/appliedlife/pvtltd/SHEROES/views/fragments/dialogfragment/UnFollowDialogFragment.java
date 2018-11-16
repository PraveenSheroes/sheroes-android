package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UnFollowDialogFragment extends BaseDialogFragment {

    private UserSolrObj mUserSolrObj;
    private String mSourceScreenName;

    @Bind(R.id.user_img_icon)
    CircleImageView circleImageView;

    @Bind(R.id.title)
    TextView title;

    @Inject
    AppUtils mAppUtils;

    @Inject
    HomePresenter mHomePresenter;

    @BindDimen(R.dimen.dp_size_87)
    int profileSizeSmall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (getActivity() == null) return null;

        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.unfollow_confirmation_dialog, container, false);
        mHomePresenter.attachView(this);
        Parcelable parcelable = getArguments().getParcelable(AppConstants.USER);
        mSourceScreenName = getArguments().getString(AppConstants.SOURCE_NAME);

        ButterKnife.bind(this, view);
        if (null != parcelable) {
            mUserSolrObj = Parcels.unwrap(parcelable);
        }

        if (StringUtil.isNotNullOrEmptyString(mUserSolrObj.getImageUrl())) {
            String imageUrl = CommonUtil.getThumborUri(mUserSolrObj.getImageUrl(), profileSizeSmall, profileSizeSmall);
            circleImageView.setCircularImage(true);
            circleImageView.bindImage(imageUrl);
        }

        title.setText(getString(R.string.unfollow_profile, mUserSolrObj.getNameOrTitle()));

        return view;
    }

    @OnClick(R.id.cancel)
    public void onCancelClick() {
        dismiss();
    }

    @OnClick(R.id.unfollow)
    public void  unFollowClick() {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserSolrObj.getIdOfEntityOrParticipant()))
                        .name(mUserSolrObj.getNameOrTitle())
                        //  .isMentor(isChampion)
              git          //  .isOwnProfile(isOwnProfile)
                        .build();
        AnalyticsManager.trackEvent(Event.PROFILE_UNFOLLOWED, mSourceScreenName, properties);
        PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
        publicProfileListRequest.setIdOfEntityParticipant(mUserSolrObj.getIdOfEntityOrParticipant());

        mHomePresenter.getUnFollowFromPresenter(publicProfileListRequest, mUserSolrObj);
        dismiss();
    }
}
