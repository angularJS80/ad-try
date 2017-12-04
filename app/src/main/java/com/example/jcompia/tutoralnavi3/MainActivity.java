package com.example.jcompia.tutoralnavi3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;

    public static Context mContext;
    private ProgressDialog mProgressDialog;
    public static GoogleSignInAccount acct;
    private GoogleApiClient mGoogleApiClient;

    private GoogleService googleService; // 서비스 객체
    private boolean mBound = false;    // 서비스 연결 여부

    boolean isService = false; // 서비스 중인 확인용





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        menuSetting(); // 메뉴바 세팅

        GoogleApplication.getInstance().initialize(this);

        //GoogleConnection(); // 같은 컨넥션인데 왜 구글 사인화면에서 계속 로딩바가 돌지????? 컨넥션을 수동으로 해줘야?
        mGoogleApiClient = GoogleApplication.getInstance().GoogleConnection();
        mGoogleApiClient.connect(); // 컨넥션을 수동으로 해줘야??
        GoogleApplication.getInstance().setGoogleApiClient(mGoogleApiClient );
        GoogleApplication.getInstance().onStart();

        // 어플리케이션에서 서비스 동작하기위해 시도중....
        // 어플리케이션을 활용하여 구글 컨넥션 케쉬값으로 자동로그인처리까지 해봄..


        //startService(new Intent(getApplicationContext(), GoogleService.class));
        //Intent intent = new Intent(this, GoogleService.class);
        //bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
      // ((GoogleService)GoogleService.mContext).getGoogleApiClient();
    }

    /*private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            GoogleService.LocalBinder binder = (GoogleService.LocalBinder) service;
            googleService = binder.getService();
            googleService.setGoogleApiClient(mGoogleApiClient);

            //mGoogleApiClient = googleService.getGoogleApiClient();
           // googleService.GoogleSignStart();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };*/

    private void menuSetting(){
        toolbar = (Toolbar) findViewById(R.id.navi_actionbar); // supeertActionBar 에 주입하기 위해서
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.togle_open,R.string.togle_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new Fragment();
                //Toast.makeText(MainActivity.this, "onNavigationItemSelected!"+item.getItemId(),     Toast.LENGTH_SHORT).show();
                switch (item.getItemId()){
                    case R.id.navi_home: startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        return true;
                    case R.id.navi_account: startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        return true;
                    case R.id.navi_webview: startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
                        return true;
                    case R.id.navi_googleSign:
                        startActivity(new Intent(getApplicationContext(), GoogleSignActivity.class));
                        //((GoogleSignActivity)GoogleSignActivity.mContext).signIn();
                        return true;
                    case R.id.navi_googleSignOut:
                        //signOut();
                        GoogleApplication.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), GoogleSignActivity.class));
                        return true;

                    case R.id.navi_firebasesavedata:
                        //Toast.makeText(MainActivity.this, "GoogleSignActivity.acct"+googleService.getGoogleSignAccount(),     Toast.LENGTH_SHORT).show();
                        if(acct!=null){
                            FireBaseTester fireBaseTester = new FireBaseTester(MainActivity.this);
                            fireBaseTester.setMsg("onMenu FireBase Clicked!");
                            fireBaseTester .firebaseAuthWithGoogle(googleService.getGoogleSignAccount());
                        }
                    case R.id.navi_todoList: startActivity(new Intent(getApplicationContext(), TodoActivity.class));
                        return true;

                        case R.id.navi_fragment: startActivity(new Intent(getApplicationContext(), FragmentActivity.class));
                        return true;

                    case R.id.navi_fragment_logout:
                        startActivity(new Intent(getApplicationContext(), FragmentActivity.class));
                        ((FragmentActivity)FragmentActivity.mContext).logOut();
                        return true;


                }

//                        finish();
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }


    /* 상위메뉴 클릭받는 함수 필요*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Toast.makeText(MainActivity.this, "onOptionsItemSelected!"+id,     Toast.LENGTH_SHORT).show();
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
          //  Toast.makeText(MainActivity.this, "actionBarDrawerToggleSelected!"+item.getItemId(),     Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }


    public void GoogleConnection(){
        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.server_client_id))
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        // connection failed, should be handled
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
        //GoogleApplication.getInstance().setGoogleApiClient(mGoogleApiClient);
        //GoogleApplication.getInstance().onStart();
        //signInCheck(mGoogleApiClient);
    }

    public void signInCheck(GoogleApiClient mGoogleApiClient) {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
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
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
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
            Toast.makeText(mContext, "handleSignInResult getEmail:" + acct.getEmail()+"getAccount:" + acct.getAccount()+"getIdToken:" + acct.getIdToken(),     Toast.LENGTH_SHORT).show();

            // mStatusTextView.setText(acct.getDisplayName());
            //updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
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

    public GoogleApiClient getGoogleApiClient(){
        return this.mGoogleApiClient;
    }
    public GoogleSignInAccount getGoogleSignInAccount(){
        return this.acct;
    }

}
