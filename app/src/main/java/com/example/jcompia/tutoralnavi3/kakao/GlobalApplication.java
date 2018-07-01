package com.example.jcompia.tutoralnavi3.kakao;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.example.jcompia.tutoralnavi3.di.component.ApplicationComponent;
import com.example.jcompia.tutoralnavi3.di.component.DaggerApplicationComponent;
import com.example.jcompia.tutoralnavi3.di.module.ApplicationModule;
import com.facebook.FacebookSdk;
import com.facebook.stetho.Stetho;
import com.kakao.auth.KakaoSDK;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;


public class GlobalApplication extends Application {
    private static GlobalApplication mInstance;
    private static volatile Activity currentActivity = null;
    private ApplicationComponent component;


    private void setupGraph() {
        component = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        component.inject(this);
    }

    public ApplicationComponent getComponent(){
        return component;
    }



    public ApplicationComponent component() {
        return component;
    }
    public static Activity getCurrentActivity() {
        Log.d("TAG", "++ currentActivity : " + (currentActivity != null ? currentActivity.getClass().getSimpleName() : ""));
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }

    /**
     * singleton
     * @return singleton
     */
    public static GlobalApplication getGlobalApplicationContext() {
        if(mInstance == null)
            throw new IllegalStateException("this application does not inherit GlobalApplication");
        return mInstance;
    }

    public static GlobalApplication get(Context context) {
        return (GlobalApplication) context.getApplicationContext();
    }


    @Override
    public void onCreate() {

        super.onCreate();
        // 인식 문제 있을땐 ReBuild
        setupGraph();

        //크롬 디버깅을 사용하도록 초기화
        Stetho.initializeWithDefaults(this);

        mInstance = this;
        KakaoSDK.init(new KakaoSDKAdapter());

        FacebookSdk.sdkInitialize(getApplicationContext());
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.jcompia.tutoralnavi3",  // replace with your unique package name
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

}
