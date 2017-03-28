package appliedlife.pvtltd.SHEROES.views.activities;

import android.os.Bundle;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Priyanka on 30/01/17.
 */

public class Feedback_ThankyouActivity extends BaseActivity
{
    private final String TAG = LogUtils.makeLogTag(Feedback_ThankyouActivity.class);
    @Bind(R.id.tv_setting_tittle)
    TextView mTv_terms;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.setting_feedback_thankyou);
        ButterKnife.bind(this);
        mTv_terms.setText(R.string.ID_FEEDBACK);

    }

    @OnClick(R.id.iv_back_setting)
    public void onbacklick() {

        finish();
    }


}
