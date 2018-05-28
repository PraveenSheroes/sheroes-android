package appliedlife.pvtltd.SHEROES.social;

/*
 * Helper clas for Sign In using google account
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;

public class GooglePlusHelper implements OnConnectionFailedListener {
    private static final String TAG = "GooglePlusHelper";
    private static GooglePlusHelper mInstance;
    private SocialListener mSocialListener;
    private GoogleApiClient mGoogleApiClient;
    private Dialog dialog;
    private Context mContext;
    private static final int GOOGLE_SIGN_IN = 9001;
    private SocialPerson mSocialPerson;

    private GooglePlusHelper() {
    }

    public static GooglePlusHelper getInstance() {
        if (mInstance == null) {
            mInstance = new GooglePlusHelper();
        }
        return mInstance;
    }

    /**
     * Configure sign-in to request the user's ID, email address, and basic
     * profile. ID and basic profile are included in DEFAULT_SIGN_IN.
     * @param context Context
     */
    public void initializeGoogleAPIClient(Context context) {
        mContext = context;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage((FragmentActivity)mContext /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    // An unresolvable error has occurred and Google APIs (including Sign-In) will not
    // be available
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        LogUtils.info(TAG,"Connection failed" + connectionResult);
        dismissDialog();
        showErrorMessage();
    }

    /**
     * If user is connected then clears which account is connected to app.
     */
    public void signOut() {
        //Check is required otherwise illegal state exception might be thrown
        if (mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        }
    }


    /**
     * Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
     * @param requestCode Request code(GOOGLE_SIGN_IN)
     * @param resultCode
     * @param data intent with extra information regarding google sign in result
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GOOGLE_SIGN_IN:
                if (resultCode == Activity.RESULT_OK) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    handleSignInResult(result);
                } else {
                    dismissDialog();
                    showErrorMessage();
                }
                break;

            default:
                LogUtils.info(TAG,"Request is not supported");
                break;
        }
    }

    /**
     * This method will fetch users profile info and send to server for verification
     * if google sign in result success
     * @param result GoogleSignInResult
     */
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String firstName = "";
            String lastName = "";
            if (personName != null) {
                String[] names = personName.split(" ");
                firstName = names[0];
                lastName = names[names.length - 1];
            }
            String personEmail = acct.getEmail();
            String imageURL = "";
            if (acct.getPhotoUrl() != null) {
                imageURL = acct.getPhotoUrl().toString();
            }
            String idToken = acct.getIdToken();
            String socialId = acct.getId();
            mSocialPerson = new SocialPerson(firstName, lastName, personEmail, 0, null,
                    SocialPerson.LOGIN_TYPE_GOOGLE,socialId,imageURL,idToken);
            if (mSocialListener != null) {
                mSocialListener.userLoggedIn(mSocialPerson);
                signOut();
            }

        } else {
            showErrorMessage();
        }
    }

    /**
     * If Dialog is shown then dismiss
     */
    public void dismissDialog() {
        try {
            if(dialog!=null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (IllegalArgumentException e) {
            LogUtils.error(this.getClass().getName(), e.toString(),e);
        }
    }

    /**
     * Will show a error message
     */
    private void showErrorMessage() {
        Toast.makeText(AppUtils.getInstance().getApplicationContext(), R.string.ID_GENERIC_ERROR, Toast.LENGTH_SHORT).show();
    }

}
