package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.ButterKnife;

/**
 * Created by Praveen on 06/12/17.
 */

public class MentorProfileDetailFragment extends BaseFragment {
    private static final String SCREEN_LABEL = "Mentor Profile Detail Screen";
    private final String TAG = LogUtils.makeLogTag(MentorProfileDetailFragment.class);
    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;

    public static MentorProfileDetailFragment createInstance() {
        MentorProfileDetailFragment mentorProfileDetailFragment = new MentorProfileDetailFragment();
        return mentorProfileDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.mentor_profile_detail_layout, container, false);
        ButterKnife.bind(this, view);
        mMoEHelper = MoEHelper.getInstance(getActivity());
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        LogUtils.info(TAG,"########Metor profile");
        return view;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }


}


