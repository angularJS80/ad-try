package com.example.jcompia.tutoralnavi3.dagger;

import android.app.Activity;

import com.example.jcompia.tutoralnavi3.FaceBookLoginActivity;
import com.example.jcompia.tutoralnavi3.MainActivity;
import com.example.jcompia.tutoralnavi3.MoviActivity;
import com.example.jcompia.tutoralnavi3.mvp.movi.dagger.module.MoviModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;


@Module
public abstract class ActivityBindingModule {

    //@ActivityScoped
   @ContributesAndroidInjector(modules = MoviModule.class)
    abstract FaceBookLoginActivity faceBookLoginActivity();
}
