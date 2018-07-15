package com.example.jcompia.tutoralnavi3.di.component;

import com.example.jcompia.tutoralnavi3.MoviActivity;
import com.example.jcompia.tutoralnavi3.di.PerActivity;
import com.example.jcompia.tutoralnavi3.di.module.ActivityModule;
import com.example.jcompia.tutoralnavi3.mvp.movi.model.MoviModel;
import com.example.jcompia.tutoralnavi3.mvp.movi.pregenter.MovePregenter;

import dagger.Component;

/**
 * Created by janisharali on 08/12/16.
 */

@PerActivity
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(MoviActivity moviActivity);
    //void inject(MovePregenter movePregenter);
    //void inject(MoviModel moviModel);

}
