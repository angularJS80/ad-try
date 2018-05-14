package com.example.jcompia.tutoralnavi3.mvp.movi.imp;

import com.example.jcompia.tutoralnavi3.mvp.comm.imp.IBasePresenter;
import com.example.jcompia.tutoralnavi3.mvp.comm.imp.IBaseView;
import com.example.jcompia.tutoralnavi3.mvp.movi.pregenter.MovePregenter;

import java.util.Map;

/**
 * Created by yongbeom on 2018. 5. 12..
 */

public interface IMoveTaskContractor {
    interface Pregenter extends IBasePresenter{
        void getMovieList(Map map);

    }

    interface View extends IBaseView<Pregenter>{
        void setMoviList();
        void setLoginResult();

    }

}
