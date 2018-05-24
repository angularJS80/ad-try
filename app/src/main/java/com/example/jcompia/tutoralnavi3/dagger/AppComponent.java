package com.example.jcompia.tutoralnavi3.dagger;


import android.app.Application;

import com.example.jcompia.tutoralnavi3.MainActivity;
import com.example.jcompia.tutoralnavi3.MoviActivity;
import com.example.jcompia.tutoralnavi3.kakao.GlobalApplication;
import com.example.jcompia.tutoralnavi3.mvp.movi.dagger.module.MoviModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by mertsimsek on 25/05/2017.
 */
@Singleton
@Component(modules = { // 1. 최상위 컴포넌트 알파컴포넌트 에서 시작점 AppCompoent 인터페이스에 GlobalApplication 주입받아야 된다.
        AppModule.class // 2. AppModule 로 부터 GlobalApplication 의 인스턴스를 받는다 (기본 )
        ,AndroidInjectionModule.class // 다양한(엑티비티 프래그먼트 등상위 5종 ) 주입기(기본)

        //, ActivityBindingModule.class
        , ActivityModule.class // 엑티비티별로 주입기를 받아온다 주입기는 ActivityInjector에 정의된 빌드를 통해 받아짐  바인드 정의 ( 엑티비티별)
        , MoviModule.class // MoviActivity 에서 실 사용할 @ingect 자원들 정의

})
public interface AppComponent

/*
extends AndroidInjector<GlobalApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
*/


{
    void inject(GlobalApplication app);
}
