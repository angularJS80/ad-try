package com.example.jcompia.tutoralnavi3.di.module;

import android.app.Activity;
import android.content.Context;


import com.example.jcompia.tutoralnavi3.di.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by janisharali on 08/12/16.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
        // 엑티비티 컨텍스트는 주입받은 엑티비티가 된다
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }
}
