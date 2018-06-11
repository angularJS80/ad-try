package com.example.jcompia.tutoralnavi3.di.component;

import com.example.jcompia.tutoralnavi3.MoviActivity;
import com.example.jcompia.tutoralnavi3.di.PerActivity;
import com.example.jcompia.tutoralnavi3.di.module.ActivityModule;

import dagger.Component;

/**
 * Created by janisharali on 08/12/16.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MoviActivity moviActivity);

}
