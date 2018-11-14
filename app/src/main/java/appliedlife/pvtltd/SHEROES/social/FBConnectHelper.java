package appliedlife.pvtltd.SHEROES.social;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

public class FBConnectHelper {
    //region member variables
    private CallbackManager mCallbackManager;
    private OnFbSignInListener mFbSignInListener;
    //endregion member variables

    //region interface
    public interface OnFbSignInListener {
        void OnFbSuccess(GraphResponse graphResponse, AccessToken accessToken);

        void OnFbError(String errorMessage);

        void onFbCancel();
    }
    //endregion interface

    //region constructor
    public FBConnectHelper(OnFbSignInListener onFbSignInListener) {
        this.mFbSignInListener = onFbSignInListener;
    }
    //endregion constructor

    //region public methods
    public void connectFb() {
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        callFbGraphApi(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        mFbSignInListener.onFbCancel();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        mFbSignInListener.OnFbError(exception.getMessage());
                    }
                });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCallbackManager != null) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    //endregion public methods

    //region private methods
    private void callFbGraphApi(LoginResult loginResult) {
        final AccessToken accessToken = loginResult.getAccessToken();

        // Facebook Email address
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mFbSignInListener.OnFbSuccess(response, accessToken);
                    }
                });
        try {
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,last_name,first_name");
            request.setParameters(parameters);
            request.executeAsync();
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
        }
    }
    //endregion private methods

}
