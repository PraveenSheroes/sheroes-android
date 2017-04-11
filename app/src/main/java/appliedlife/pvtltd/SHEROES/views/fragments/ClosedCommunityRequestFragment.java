package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 08-03-2017.
 */

public class ClosedCommunityRequestFragment extends BaseDialogFragment {
    private final String TAG = LogUtils.makeLogTag(ClosedCommunityRequestFragment.class);
    @Bind(R.id.tv_closed_community_highly_intrested)
    TextView mTvClosedCommunityHIghlyIntrested;
    @Bind(R.id.tv_closed_community_already_member)
    TextView mTvClosedCommunityAlreadyMember;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.closed_community_request_fragment, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        return view;
    }

    @OnClick(R.id.tv_closed_community_highly_intrested)
    public void highlyIntrestedOnClick() {

    }

    @OnClick(R.id.tv_closed_community_already_member)
    public void alreadyMemberOnClick() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismissAllowingStateLoss();//dismiss dialog on back button press
            }
        };
    }

}
