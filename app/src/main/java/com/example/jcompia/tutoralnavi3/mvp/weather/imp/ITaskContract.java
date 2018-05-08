package com.example.jcompia.tutoralnavi3.mvp.weather.imp;

import com.example.jcompia.tutoralnavi3.mvp.comm.imp.IBasePresenter;
import com.example.jcompia.tutoralnavi3.mvp.comm.imp.IBaseView;

public interface ITaskContract  {
    interface Presenter extends IBasePresenter {
        void getWeather();
    }
    interface View extends IBaseView<Presenter> {
        void showWeather(String weather);
    }
}
