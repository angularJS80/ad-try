package com.example.jcompia.tutoralnavi3.mvp.movi.dagger.module;

import com.example.jcompia.tutoralnavi3.mvp.movi.adapter.MoviAdapter;
import com.example.jcompia.tutoralnavi3.mvp.movi.pregenter.MovePregenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by yongbeom on 2018. 5. 22..
 */
@Module
public class MoviModule {
    @Provides
    @Singleton
    MovePregenter provideMovePregenter(MoviAdapter moviAdapter) {
        return new MovePregenter();
    }

    @Provides
    @Singleton
    MoviAdapter provideMoviAdapter() {
        return new MoviAdapter();
    }
}
