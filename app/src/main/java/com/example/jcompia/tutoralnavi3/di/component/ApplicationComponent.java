package com.example.jcompia.tutoralnavi3.di.component;

import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;

import com.example.jcompia.tutoralnavi3.data.AccountManagerHelper;
import com.example.jcompia.tutoralnavi3.data.SharedPrefsHelper;
import com.example.jcompia.tutoralnavi3.di.ApplicationContext;
import com.example.jcompia.tutoralnavi3.di.module.ApplicationModule;
import com.example.jcompia.tutoralnavi3.kakao.GlobalApplication;
import com.example.jcompia.tutoralnavi3.mvp.movi.model.MoviModel;
import com.example.jcompia.tutoralnavi3.mvp.movi.pregenter.MovePregenter;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by janisharali on 08/12/16.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
/*PregentModule 를 제외 하면 MoviPregent 를 제공받을 모듈이 없지만, MoviPregent 의 생성자에 @Inject 로 제공해 줄 수 있다.*/
/*@Module 의 @Provider 를 통해 제공되는 생성자는 @Componet 에서 제공되는 메소드를 통해 컴포넌트가 모듈에서 찾아 준다.*/
public interface ApplicationComponent {

    void inject(GlobalApplication globalApplication);

    @ApplicationContext
        // 어플리케이션 컨텍스트를 찾아보니 범위를 지정하는 스코프였다. 그렇다면 이범위에 들어와 있는 컨텍스트 세터가 있을것이다.
    Context getContext();

    Application getApplication();

    AccountManagerHelper getAccountManagerHelper();
    SharedPrefsHelper getPreferenceHelper();
    //MovePregenter getMovePregenter();
    //MoviModel getMoviModel();
    //@PerPregent
    MovePregenter getMovePregenter();
    //MoviModel getMoviModel();

}
