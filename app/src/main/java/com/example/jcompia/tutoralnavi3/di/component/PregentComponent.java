package com.example.jcompia.tutoralnavi3.di.component;

import android.app.Application;
import android.content.Context;

import com.example.jcompia.tutoralnavi3.MoviActivity;
import com.example.jcompia.tutoralnavi3.data.AccountManagerHelper;
import com.example.jcompia.tutoralnavi3.data.SharedPrefsHelper;
import com.example.jcompia.tutoralnavi3.di.ApplicationContext;
import com.example.jcompia.tutoralnavi3.di.PerPregent;
import com.example.jcompia.tutoralnavi3.di.module.ApplicationModule;
import com.example.jcompia.tutoralnavi3.di.module.PregentModule;
import com.example.jcompia.tutoralnavi3.kakao.GlobalApplication;
import com.example.jcompia.tutoralnavi3.mvp.movi.pregenter.MovePregenter;
import com.example.jcompia.tutoralnavi3.mvp.movi.pregenter.TestPregenter;

import junit.framework.Test;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by janisharali on 08/12/16.
 */

@PerPregent
@Component(dependencies = {ActivityComponent.class},modules = {PregentModule.class})
/*PregentModule 를 제외 하면 MoviPregent 를 제공받을 모듈이 없지만, MoviPregent 의 생성자에 @Inject 로 제공해 줄 수 있다.*/
/*@Module 의 @Provider 를 통해 제공되는 생성자는 @Componet 에서 제공되는 메소드를 통해 컴포넌트가 모듈에서 찾아 준다.*/
public interface PregentComponent {

    // 테스트 프레젠터가 ActivityCompoent 에서 제공하는 컨텍스트 등을 사용 가능 ?
    TestPregenter getTestPregenter();
}
