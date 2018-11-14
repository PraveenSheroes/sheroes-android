package appliedlife.pvtltd.SHEROES.social;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import appliedlife.pvtltd.SHEROES.utils.AppConstants;

public class GoogleConnectHelper implements GoogleApiClient.OnConnectionFailedListener {
    //region member variables
    private static GoogleConnectHelper mInstance;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions mGso;
    private OnGoogleConnectListener mOnGoogleConnectListener;
    //endregion member variables

    //region interface
    public interface OnGoogleConnectListener {
        void onGoogleSuccess(String name, String email);

        void dismissProgress();
    }
    //endregion interface

    //region constructor
    public static GoogleConnectHelper getInstance() {
        if (mInstance == null) {
            mInstance = new GoogleConnectHelper();
        }
        return mInstance;
    }

    private GoogleConnectHelper() {
        mGso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .build();
    }
    //endregion constructor

    //region public methods
    public void initialize(Context context, OnGoogleConnectListener onGoogleConnectListener) {
        mOnGoogleConnectListener = onGoogleConnectListener;
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage((FragmentActivity) context, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGso)
                .build();
    }


    public void signIn(Activity activity) {
        //Creating an intent
        signOut();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        //Starting intent for result
        activity.startActivityForResult(signInIntent, AppConstants.REQUEST_CODE_FOR_GOOGLE_PLUS);
    }

    public void signOut() {
        //Check is required otherwise illegal state exception might be thrown
        if (null != mGoogleApiClient && mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AppConstants.REQUEST_CODE_FOR_GOOGLE_PLUS:
                if (resultCode == Activity.RESULT_OK) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    handleSignInResult(result);
                } else {
                    mOnGoogleConnectListener.dismissProgress();
                }
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    //endregion public methods

    //region private methods
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();

            mOnGoogleConnectListener.onGoogleSuccess(personName, personEmail);
        }
    }
    //endregion private methods
}
