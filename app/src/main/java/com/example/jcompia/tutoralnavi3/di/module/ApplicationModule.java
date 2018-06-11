package com.example.jcompia.tutoralnavi3.di.module;

import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


import com.example.jcompia.tutoralnavi3.di.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by janisharali on 25/12/16.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application app) {
        mApplication = app;
    }

    @Provides
    @ApplicationContext
        // 어플리케이션 컨텍스는 주입받은 어플리케이션 컨텍스트가 된다
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    AccountManager provideAccountManager(){return  (AccountManager) mApplication.getSystemService(Context.ACCOUNT_SERVICE);}

    @Provides
    SharedPreferences provideSharedPrefs() {
        return mApplication.getSharedPreferences("demo-prefs", Context.MODE_PRIVATE);
    }
}
