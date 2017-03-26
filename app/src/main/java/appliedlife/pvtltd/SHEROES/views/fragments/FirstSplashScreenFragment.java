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
import appliedlife.pvtltd.SHEROES.views.adapters.WelcomeViewPagerAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sheroes on 08/03/17.
 */

public class FirstSplashScreenFragment extends BaseFragment implements ViewPager.OnPageChangeListener{
    private final String TAG = LogUtils.makeLogTag(FirstSplashScreenFragment.class);
    @Bind(R.id.iv_btn1)
    ImageView mIv_btn1;
    @Bind(R.id.iv_btn2)
    ImageView mIv_btn2;
    @Bind(R.id.iv_btn3)
    ImageView imageView3;
    @Bind(R.id.imageviewPager)
    ViewPager viewPager;
    int i=0;

    private WelcomeViewPagerAdapter welcomeViewPagerAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView();
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
            viewPager.setCurrentItem(0);

        }

    @OnClick(R.id.iv_btn2)
    public  void radio_btn_click_2()
    {
        mIv_btn2.setImageResource(R.drawable.ic_circle_w);
        viewPager.setCurrentItem(0);

    }

    @OnClick(R.id.iv_btn3)
    public  void radio_btn_click_3()
    {
        imageView3.setImageResource(R.drawable.ic_circle_w);
        viewPager.setCurrentItem(0);

    }


    private void setUpView() {
        welcomeViewPagerAdapter = new WelcomeViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(welcomeViewPagerAdapter);
        viewPager.setCurrentItem(0);

        //initButton();

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
                imageView3.setImageResource(R.drawable.ic_circle_w);
                i=2;
                break;

        }
    }

    private void setButton(Button btn, String text, int h, int w) {
        btn.setWidth(w);
        btn.setHeight(h);
        btn.setText(text);
    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        mIv_btn1.setImageResource(R.drawable.ic_circle_red);
        mIv_btn2.setImageResource(R.drawable.ic_circle_red);
        imageView3.setImageResource(R.drawable.ic_circle_red);
        btnAction(position);
    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see ViewPager#SCROLL_STATE_IDLE
     * @see ViewPager#SCROLL_STATE_DRAGGING
     * @see ViewPager#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
