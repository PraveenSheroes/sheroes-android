package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 08/03/17.
 */

public class WelcomeScreenFirstFragment extends BaseFragment {
    private final String TAG = LogUtils.makeLogTag(WelcomeFragment.class);
    private final String SCREEN_NAME = "WelcomeScreenFirstFragment";


    @Bind(R.id.tv_click_to_join)
    TextView mtv_click_to_join;
    @Bind(R.id.intro_footer)
    LinearLayout ll_intro_footer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.fragment_welcome_screen1, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.tv_click_to_join)
    public void onJoinClick()
    {

        ll_intro_footer.setVisibility(View.VISIBLE);
    }

}
