package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.WorkExperienceAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 10/02/17.
 */

public class ProfessionalWorkExperienceActivity extends BaseActivity implements WorkExperienceAdapter.ExperienceCallable {
    private final String TAG = LogUtils.makeLogTag(ProfessionalWorkExperienceActivity.class);
    private static final String SCREEN_LABEL = "Work Experiences Screen";
    @Bind(R.id.btn_continue_work_experience)
    Button mWorkExperienceContinueBtn;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private String mSelectedCategory = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        renderLoginFragmentView();
        ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_MY_PROFILE_PROFESSIONAL_VIEW_ADDED_WORK_EXPERIENCE));
    }

    private void renderLoginFragmentView() {
        setContentView(R.layout.activity_professional_workexperience);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayout);
        mRecyclerView.setAdapter(new WorkExperienceAdapter(this, getResources().getStringArray(R.array.work_exp_arr)));
    }

    @OnClick(R.id.btn_continue_work_experience)
    public void onContinueClick() {
        Intent intent = new Intent(this, WorkExperienceActivity.class);
        intent.putExtra(AppConstants.WORK_EXPERIENCE_CATE, mSelectedCategory);
        startActivity(intent);
        onBackClick();
    }

    @Override
    public void onItemSelect(String category) {
        mWorkExperienceContinueBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        mWorkExperienceContinueBtn.setVisibility(View.VISIBLE);
        mSelectedCategory = category;
    }

    @Override
    public void onItemUnSelect() {
        mWorkExperienceContinueBtn.setVisibility(View.GONE);
        mWorkExperienceContinueBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.grey2));
        mSelectedCategory = "";
    }

    @OnClick(R.id.iv_back_work_exp)
    public void onBackClick() {
        finish();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}
