package com.example.jcompia.tutoralnavi3.dagger;

import com.example.jcompia.tutoralnavi3.MainActivity;
import com.example.jcompia.tutoralnavi3.MoviActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by yongbeom on 2018. 5. 22..
 */

public interface ActivityInjector {
    @Subcomponent
    public interface MainActivityInjector extends AndroidInjector<MainActivity> {

        @Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<MainActivity> {
        }
    }

    @Subcomponent
    public interface MoviActivityInjector extends AndroidInjector<MoviActivity> {

        @Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<MoviActivity> {
        }
    }
}


