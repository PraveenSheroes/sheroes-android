package appliedlife.pvtltd.SHEROES.views.activities;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by priyanka on 07/03/17.
 */

public class ProfessionalAddEducationActivity extends BaseActivity {

    private final String TAG = LogUtils.makeLogTag(ProfessionalAddEducationActivity.class);

    @Bind(R.id.tv_setting_tittle)
    TextView mTv_setting_tittle;
    @Bind(R.id.iv_back_setting)
    ImageView mIv_back_setting;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        renderAddEducationFragmentView();
    }


    public void renderAddEducationFragmentView() {
        setContentView(R.layout.fragment_professional_addeducation);
        ButterKnife.bind(this);
        mTv_setting_tittle.setText(R.string.ID_ADD_EDUCATION);

    }



    @OnClick(R.id.iv_back_setting)
    public void backOnclick()
    {
        //overridePendingTransition(R.anim.top_to_bottom_exit,R.anim.top_bottom_exit_anim);
        finish();
    }

}
