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

import com.facebook.stetho.Stetho;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        menuSetting(); // 메뉴바 세팅
        setGoogleApi(); // 구글Api 세팅

    }

    private void setGoogleApi(){
        // 구글Api 초기화
        GoogleApplication.getInstance().initialize(this);

        //구글클라이언트로 구글에 연결
        GoogleApplication.getInstance().GoogleConnection().connect(); // 컨넥션을 수동으로 해줘야??

        //구글 연결인스턴스를 구글Api 에 주입
        //GoogleApplication.getInstance().setGoogleApiClient(mGoogleApiClient );

        //자동로그인 처리
        GoogleApplication.getInstance().silentSignIn();

        // 어플리케이션에서 서비스 동작하기위해 시도중....
        // 어플리케이션을 활용하여 구글 컨넥션 케쉬값으로 자동로그인처리까지 해봄..


        //startService(new Intent(getApplicationContext(), GoogleService.class));
        //Intent intent = new Intent(this, GoogleService.class);
        //bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        // ((GoogleService)GoogleService.mContext).getGoogleApiClient();
    }

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
                    case R.id.navi_mvphome: startActivity(new Intent(getApplicationContext(), MvpHomeActivity.class));
                        return true;
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


                    case R.id.navi_todoList: startActivity(new Intent(getApplicationContext(), TodoActivity.class));
                        return true;

                        case R.id.navi_fragment: startActivity(new Intent(getApplicationContext(), FragmentActivity.class));
                        return true;

                    case R.id.navi_fragment_logout:
                        startActivity(new Intent(getApplicationContext(), FragmentActivity.class));
                        ((FragmentActivity)FragmentActivity.mContext).logOut();
                        return true;
                    case R.id.navi_sample_kakaologin:
                        startActivity(new Intent(getApplicationContext(), KakaoTalkMainActivity.class));
                        return true;

                    case R.id.navi_sample_facelogin:
                        startActivity(new Intent(getApplicationContext(), FaceBookLoginActivity.class));
                        return true;
                    case R.id.navi_movilist:
                        startActivity(new Intent(getApplicationContext(), MoviActivity.class));
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
/*
    public void handleSignInResult(GoogleSignInResult result) {
        Toast.makeText(mContext, "handleSignInResult:" + result.isSuccess(),     Toast.LENGTH_SHORT).show();
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
    }*/

    public void showProgressDialog(Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
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


    /*public GoogleApiClient getGoogleApiClient(){
        return this.mGoogleApiClient;
    }*/


}
