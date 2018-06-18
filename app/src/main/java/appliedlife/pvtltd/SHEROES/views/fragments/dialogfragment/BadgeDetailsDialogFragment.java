package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.ProgressbarView;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.EditUserProfileActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.DashProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ravi on 02/05/18.
 * Dialog to display the details of community badge
 */

public class BadgeDetailsDialogFragment extends BaseDialogFragment {

    //region private member variable
    public static final String SCREEN_NAME = "Profile Badge Dialog";
    //endregion

    //region private member variable
    //endregion

    @Inject
    Preference<Configuration> mConfiguration;

    //region Bind view variables
    //endregion

    //region Fragment methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (getActivity() == null) return null;

        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.community_badge_detail, container, false);
//        Parcelable parcelable = getArguments().getParcelable(AppConstants.USER);
        ButterKnife.bind(this, view);

        //Add analytics screen open Event
          /*  if (getArguments() != null && getArguments().getString(AppConstants.SOURCE_NAME) != null) {
                String strengthLevel = userLevel(mUserSolrObj).name();
                String sourceScreenName = getArguments().getString(AppConstants.SOURCE_NAME);
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .isMentor(mUserSolrObj.isAuthorMentor())
                                .profileStrength(strengthLevel)
                                .build();
                AnalyticsManager.trackScreenView(SCREEN_NAME, sourceScreenName, properties);
            }
        }*/

        return view;
    }
    //endregion

    //region onclick method
    @OnClick(R.id.cross)
    protected void crossClick() {
        dismiss();
    }

}