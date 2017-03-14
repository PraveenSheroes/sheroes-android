package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.IntroViewPagerAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sheroes on 08/03/17.
 */

public class IntroFragment extends BaseFragment {
    private final String TAG = LogUtils.makeLogTag(IntroFragment.class);
    private final String SCREEN_NAME = "Intro_fragment_screen";


    @Bind(R.id.iv_btn1)
    ImageView mIv_btn1;
    @Bind(R.id.iv_btn2)
    ImageView mIv_btn2;
    @Bind(R.id.iv_btn3)
    ImageView mIv_btn3;
    @Bind(R.id.imageviewPager)
    ViewPager _mViewPager;
    int i=0;

    private IntroViewPagerAdapter _adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpView();

        setTab();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_welcome_container, container, false);
        ButterKnife.bind(this, view);

        return view;
    }




    @OnClick(R.id.iv_btn1)
        public  void radio_btn_click_1()
        {
            mIv_btn1.setImageResource(R.drawable.ic_circle_w);
            _mViewPager.setCurrentItem(0);

        }

    @OnClick(R.id.iv_btn2)
    public  void radio_btn_click_2()
    {
        mIv_btn2.setImageResource(R.drawable.ic_circle_w);
        _mViewPager.setCurrentItem(0);

    }

    @OnClick(R.id.iv_btn3)
    public  void radio_btn_click_3()
    {
        mIv_btn3.setImageResource(R.drawable.ic_circle_w);
        _mViewPager.setCurrentItem(0);

    }


    private void setUpView() {


        _adapter = new IntroViewPagerAdapter(getActivity(), getFragmentManager());
        _mViewPager.setAdapter(_adapter);
        _mViewPager.setCurrentItem(0);

        //initButton();

    }


    private void setTab() {
        _mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrollStateChanged(int position) {

            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                mIv_btn1.setImageResource(R.drawable.ic_circle_red);
                mIv_btn2.setImageResource(R.drawable.ic_circle_red);
                mIv_btn3.setImageResource(R.drawable.ic_circle_red);
                btnAction(position);
            }

        });

    }
    private void btnAction(int action) {
        switch (action) {

            case 0:
                mIv_btn1.setImageResource(R.drawable.ic_circle_w);
                i=0;
                break;
            case 1:
                mIv_btn2.setImageResource(R.drawable.ic_circle_w);
                i=1;
                break;
            case 2:
                mIv_btn3.setImageResource(R.drawable.ic_circle_w);
                i=2;
                break;

        }
    }

    private void setButton(Button btn, String text, int h, int w) {
        btn.setWidth(w);
        btn.setHeight(h);
        btn.setText(text);
    }

}
