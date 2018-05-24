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


@Module(subcomponents = {ActivityInjector.MainActivityInjector.class,ActivityInjector.MoviActivityInjector.class})
public abstract class ActivityModule {

    @Binds // 어떤 엑티비티가 @inject 를 사용하기위해 에 엑티비티 자체를 받아서 AndroidInjector.Factory 를 통해 빌드될지 선언한다
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindMainctivityInjectorFactory(ActivityInjector.MainActivityInjector.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(MoviActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindMoviActivityInjectorFactory(ActivityInjector.MoviActivityInjector.Builder builder);

}
