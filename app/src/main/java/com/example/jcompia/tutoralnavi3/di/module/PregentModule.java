package com.example.jcompia.tutoralnavi3.di.module;

import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.jcompia.tutoralnavi3.di.ApplicationContext;
import com.example.jcompia.tutoralnavi3.di.PerPregent;
import com.example.jcompia.tutoralnavi3.mvp.movi.pregenter.TestPregenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by janisharali on 25/12/16.
 */

@Module
public class PregentModule {

    @Provides
    @PerPregent
    TestPregenter provideTestPregenter() {
       return  new TestPregenter();
    }
}
