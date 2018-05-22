package com.example.jcompia.tutoralnavi3.dagger;

import android.app.Application;

/**
 * Created by yongbeom on 2018. 5. 22..
 */

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {

    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides // 싱글톤형태로 어플리케이션 인스턴스를 제공해준다
    @Singleton
    Application providesApplication() {
        return application;
    }
}
