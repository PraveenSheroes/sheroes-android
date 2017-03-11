package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.ProfileWorkExperienceSelfEmploymentFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 10/02/17.
 */

public class ProfessionalWorkExperienceActivity extends BaseActivity {

    private final String TAG = LogUtils.makeLogTag(ProfessionalWorkExperienceActivity.class);
    @Bind(R.id.tv_self_emp)
    TextView mTv_self_emp;
    @Bind(R.id.cb_self_emp)
    CheckBox mCb_self_emp;
    @Bind(R.id.tv_job)
    TextView mTv_job;
    @Bind(R.id.cb_job)
    CheckBox mCb_job;
    @Bind(R.id.tv_freelance_project)
    TextView mTv_freelance_project;
    @Bind(R.id.cb_freelance_project)
    CheckBox mCb_freelance_project;
    @Bind(R.id.tv_personal_project)
    TextView mTv_personal_project;
    @Bind(R.id.cb_personal_project)
    CheckBox mCb_personal_project;
    @Bind(R.id.tv_school_project)
    TextView mTv_school_project;
    @Bind(R.id.cb_school_project)
    CheckBox mCb_school_project;
    @Bind(R.id.tv_intership)
    TextView mTv_intership;
    @Bind(R.id.cb_intership)
    CheckBox mCb_intership;
    @Bind(R.id.btn_continue_work_experience)
    Button mBtn_continue_work_experience;
    int flag = 0;
    String value;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        renderLoginFragmentView();
    }


    public void renderLoginFragmentView() {
        setContentView(R.layout.activity_professional_workexperience);
        ButterKnife.bind(this);
        /*  LoginFragment frag = new LoginFragment();
        callFirstFragment(R.id.fl_fragment_container, frag);*/
    }

    @OnClick(R.id.tv_job)
    public void onclick_self_empolyment_text() {

        setcolor(mTv_job, mCb_job);
    }

    @OnClick(R.id.tv_self_emp)

    public void onclick_job_text() {

        setcolor(mTv_self_emp, mCb_self_emp);
        value = mTv_self_emp.getText().toString();
    }

    @OnClick(R.id.tv_freelance_project)
    public void onclick_Freelance_text() {
        setcolor(mTv_freelance_project, mCb_freelance_project);
    }

    @OnClick(R.id.tv_personal_project)
    public void onclick_personal_project_text() {
        setcolor(mTv_personal_project, mCb_personal_project);
    }

    @OnClick(R.id.tv_school_project)
    public void onclick_school_project_text() {

        setcolor(mTv_school_project, mCb_school_project);

    }

    @OnClick(R.id.tv_intership)
    public void onclick_intership_text() {

        setcolor(mTv_intership, mCb_intership);
    }

    @OnClick(R.id.cb_job)
    public void onclick_checkbox_job() {

        setcolor(mTv_self_emp, mCb_job);
    }

    @OnClick(R.id.cb_self_emp)
    public void onclick_checkbox_self_emp() {

        setcolor(mTv_self_emp, mCb_self_emp);
    }

    @OnClick(R.id.cb_freelance_project)
    public void onclick_checkbox_freelance_project() {

        setcolor(mTv_freelance_project, mCb_freelance_project);
    }

    @OnClick(R.id.cb_personal_project)
    public void onclick_checkbox_personal_project() {

        setcolor(mTv_personal_project, mCb_personal_project);
    }

    @OnClick(R.id.cb_school_project)
    public void onclick_checkbox_school_project() {

        setcolor(mTv_school_project, mCb_school_project);
    }

    @OnClick(R.id.cb_intership)
    public void onclick_checkbox_intership() {

        setcolor(mTv_intership, mCb_intership);
    }


//function for set color of text and button

    public void setcolor(TextView textview, CheckBox checkbox) {
        if (flag == 0) {

            textview.setTextColor(getResources().getColor(R.color.search_tab_text));
            mBtn_continue_work_experience.setBackgroundColor(getResources().getColor(R.color.red));
            checkbox.setChecked(true);
            flag = 1;

        } else {

            textview.setTextColor(getResources().getColor(R.color.searchbox_hint_text_color));
            mBtn_continue_work_experience.setBackgroundColor(getResources().getColor(R.color.grey2));
            checkbox.setChecked(false);
            flag = 0;

        }

    }

    //clik on continue button

    @OnClick(R.id.btn_continue_work_experience)

    public void OnButton_Click() {

        if (value.equals("Self employment")) {

            ButterKnife.bind(this);


        } else {

        }

    }

    @OnClick(R.id.iv_back_work_exp)
    public void Onback_Click() {

        finish();

    }


}
