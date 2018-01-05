package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import butterknife.ButterKnife;

/**
 * Created by ravi on 03/01/18.
 */

public class ProfileUsersCommunityListFragment extends BaseFragment {


    public static ProfileUsersCommunityListFragment createInstance(long communityId) {
        ProfileUsersCommunityListFragment profileUsersCommunityListFragment = new ProfileUsersCommunityListFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("COMMUNITY_ID", communityId); //todo -profile- change here
        profileUsersCommunityListFragment.setArguments(bundle);
        return profileUsersCommunityListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.followed_mentor_listing_layout, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public String getScreenName() {
        return null;
    }
}
