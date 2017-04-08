package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;

import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Priyanka on 30/01/17.
 */

public class Feedback_ThankyouActivity extends BaseActivity {
    private final String TAG = LogUtils.makeLogTag(Feedback_ThankyouActivity.class);
    @Bind(R.id.tv_setting_tittle)
    TextView mTvTerms;
    @Bind(R.id.iv_feeback_facebook)
    ImageView mIvFeedbackFacebook;
    @Bind(R.id.iv_feead_back_instragram)
    ImageView mIvFeeadBackInstragram;
    @Bind(R.id.iv_feedback_twitter)
    ImageView mIvFeedbackTwitter;




    private  Uri mSheroesFacebookUrl = Uri.parse(AppConstants.SHEROESFACEBOOKLINK);
    private  Uri mSheroesTwitterUrl = Uri.parse(AppConstants.SHEROESTWITTERLINK);
    private  Uri mSheroesIstragramUrl = Uri.parse(AppConstants.SHEROESISTRAGRAMLINK);

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.setting_feedback_thankyou);
        ButterKnife.bind(this);
        mTvTerms.setText(R.string.ID_FEEDBACK);

    }

    @OnClick(R.id.iv_back_setting)
    public void onbacklick() {


        finish();


    }


    //click on facebook icon

    @OnClick(R.id.iv_feeback_facebook)
    public  void OnclickFacebookIcon()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, mSheroesFacebookUrl);
        startActivity(intent);
    }

    //click on twitter icon

    @OnClick(R.id.iv_feedback_twitter)
    public  void OnclickTwitterIcon()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, mSheroesTwitterUrl);
        startActivity(intent);
    }
    //click on istragramicon

    @OnClick(R.id.iv_feead_back_instragram)
    public  void OnclickIstragramIcon()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, mSheroesIstragramUrl);
        startActivity(intent);
    }



}
