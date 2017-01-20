package appliedlife.pvtltd.SHEROES.basecomponents;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.errorview.NetworkTimeoutDialog;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Base activity for all activities.
 */
public class BaseActivity extends AppCompatActivity {
    private final String TAG = LogUtils.makeLogTag(BaseActivity.class);
    private static final String NETWORK_TIMEOUT = "NETWORK_TIMEOUT";
    protected boolean mIsSavedInstance;
    public boolean mIsDestroyed;
    protected SheroesApplication mSheroesApplication;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSheroesApplication = (SheroesApplication)this.getApplicationContext();
    }
    public void startNewActivity(Class<?> activityClass,View transitionImage,String tag) {
        Intent myIntent = new Intent(this, activityClass);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,transitionImage, tag);
        ActivityCompat.startActivity(this, myIntent, options.toBundle());
        startActivity(myIntent);
    }
    public void callFirstFragment(int layout, Fragment fragment) {
        if (!mIsDestroyed) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(layout, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
    /**
     * @param finishParentOnBackOrTryagain pass true:- if desired result is to finish the page on press of tryagain or press of back key else
     *                                     pass false:- to just dismiss the dialog on try again and or press of back key in case you want to handle it your self say a retry
     * @return
     */
    protected DialogFragment showNetworkTimeoutDoalog(boolean finishParentOnBackOrTryagain)
    {
        NetworkTimeoutDialog fragment = (NetworkTimeoutDialog) getFragmentManager().findFragmentByTag(NETWORK_TIMEOUT);
        if (fragment == null)
        {
            fragment = new NetworkTimeoutDialog();
            Bundle b = new Bundle();
            b.putBoolean(BaseDialogFragment.DISMISS_PARENT_ON_OK_OR_BACK, finishParentOnBackOrTryagain);
            fragment.setArguments(b);
        }
        if (!fragment.isVisible() && !fragment.isAdded() && !isFinishing() && !mIsSavedInstance && !mIsDestroyed)
        {
            fragment.show(getFragmentManager(), NETWORK_TIMEOUT);
        }
        return fragment;
    }
    @Override
    protected void onDestroy()
    {
        mIsDestroyed = true;
        clearReferences();
        super.onDestroy();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        mIsSavedInstance = true;
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSheroesApplication.setCurrentActivityName(this.getClass().getSimpleName());
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(mSheroesApplication !=null) {
            mSheroesApplication.notifyIfAppInBackground();
        }
        clearReferences();
    }
    private void clearReferences(){
        String currActivityName = mSheroesApplication.getCurrentActivityName();
        if (this.getClass().getSimpleName().equals(currActivityName))
            mSheroesApplication.setCurrentActivityName(null);
    }
}
