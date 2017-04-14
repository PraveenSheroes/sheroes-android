package appliedlife.pvtltd.SHEROES.views.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.BaseWorkExperienceFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ExperienceFreelancerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ExperienceJobAndInternshipFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ExperienceSchoolAndPersonalFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ExperienceSelfEmployedFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class WorkExperienceActivity extends BaseActivity {
    private static final String TAG = "WorkExperienceActivity";
    @Bind(R.id.txt_sub_title)
    TextView mSubTitleTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_experience);
        ButterKnife.bind(this);
        String category = getIntent().getExtras().getString(AppConstants.WORK_EXPERIENCE_CATE);
        if (category != null && getFragment(category) != null) {
            replaceFragment(getFragment(category));
        } else {
            LogUtils.error(TAG, "Selected Category null");
        }
        mSubTitleTxt.setText(category);
    }

    @Nullable
    private BaseWorkExperienceFragment getFragment(@NonNull String category) {
        if (category.equalsIgnoreCase(getString(R.string.ID_SELF_EMP))) {
            return new ExperienceSelfEmployedFragment();
        } else if (category.equalsIgnoreCase(getString(R.string.ID_JOB)) || category.equalsIgnoreCase(getString(R.string.ID_INTERSHIP))) {
            return new ExperienceJobAndInternshipFragment();
        } else if (category.equalsIgnoreCase(getString(R.string.ID_SCHOOL_PROJECT)) || category.equalsIgnoreCase(getString(R.string.ID_PERSONAL_PROJECT))) {
            new ExperienceSchoolAndPersonalFragment();
        } else if (category.equalsIgnoreCase(getString(R.string.ID_FRELANCE_PROJECT))) {
            return new ExperienceFreelancerFragment();
        } else {
            LogUtils.error(TAG, "Emp type mismatch");
        }
        return null;
    }
}
