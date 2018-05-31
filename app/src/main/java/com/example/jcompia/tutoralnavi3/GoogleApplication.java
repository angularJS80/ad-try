package com.example.jcompia.tutoralnavi3;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class GoogleApplication extends Application{

    private static GoogleApplication googleApplication;
    public GoogleApplication() { googleApplication = this; }
    public static GoogleApplication getInstance() {
        if(googleApplication==null){
            googleApplication = new GoogleApplication();
        }
        return googleApplication;
    }
    public void initialize(Context context) {
        this.mContext = context;
    }


    public static Context mContext;
    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    //private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;
    public static GoogleSignInAccount acct;
    OptionalPendingResult<GoogleSignInResult> opr;

    public GoogleApiClient GoogleConnection(){
        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        Log.d(TAG, "#######################mContext.getString(R.string.server_client_id:" + mContext.getString(R.string.server_client_id));
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(mContext.getString(R.string.server_client_id))
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage(new FragmentActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        // connection failed, should be handled

                        Log.d(TAG, "#######################onConnectionFailed:" + connectionResult);
                        Toast.makeText(mContext, "onConnectionFailed!"+ connectionResult,     Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END build_client]

        // [START customize_button]
        // Customize sign-in button. The sign-in button can be displayed in
        // multiple sizes and color schemes. It can also be contextually
        // rendered based on the requested scopes. For example. a red button may
        // be displayed when Google+ scopes are requested, but a white button
        // may be displayed when only basic profile is requested. Try adding the
        // Scopes.PLUS_LOGIN scope to the GoogleSignInOptions to see the
        // difference.

        // signInButton.setSize(SignInButton.SIZE_STANDARD);
        // signInButton.setScopes(gso.getScopeArray());
        // [END customize_button]
        //onStart();

        return mGoogleApiClient;
    }

    public void silentSignIn() {


        opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        Log.d(TAG, "#######################opr.toString:" + opr.isDone());

        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            //Toast.makeText(mContext, "Got cached sign-in",     Toast.LENGTH_SHORT).show();

            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            //showProgressDialog();
            ((MainActivity)MainActivity.mContext).showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    ((MainActivity)MainActivity.mContext).hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    public void handleSignInResult(GoogleSignInResult result) {
        //Toast.makeText(mContext, "handleSignInResult:" + result.isSuccess(),     Toast.LENGTH_SHORT).show();
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();
            //Toast.makeText(mContext, "handleSignInResult getEmail:" + acct.getEmail()+"getAccount:" + acct.getAccount()+"getIdToken:" + acct.getIdToken(),     Toast.LENGTH_SHORT).show();

            // mStatusTextView.setText(acct.getDisplayName());
            //updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        //updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }


    public GoogleSignInAccount getGoogleSignAccount(){
        System.out.print(this.acct);
        return this.acct;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient){
        this.mGoogleApiClient = googleApiClient;
    }

    public OptionalPendingResult<GoogleSignInResult> getOpr() {
        return opr;
    }

    public GoogleApiClient getGoogleApiClient(){
        return this.mGoogleApiClient ;
    }
}
