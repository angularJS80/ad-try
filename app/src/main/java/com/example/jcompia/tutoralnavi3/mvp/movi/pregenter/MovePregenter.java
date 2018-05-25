package com.example.jcompia.tutoralnavi3.mvp.movi.pregenter;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.jcompia.tutoralnavi3.govweather.APIRx;
import com.example.jcompia.tutoralnavi3.govweather.data.WetherSpcnwsInfoServiceVO;
import com.example.jcompia.tutoralnavi3.mvp.movi.adapter.MoviAdapter;
import com.example.jcompia.tutoralnavi3.mvp.movi.imp.ApiRequest;
import com.example.jcompia.tutoralnavi3.mvp.movi.imp.IMoveTaskContractor;
import com.example.jcompia.tutoralnavi3.mvp.movi.model.MoviModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yongbeom on 2018. 5. 12..
 */

public class MovePregenter implements IMoveTaskContractor.Pregenter {
    private Gson GSON = new Gson();
    MoviModel moviModel = new MoviModel();
    MoviAdapter moviAdapter;
    private SharedPreferences appData;
    Gson gson = new Gson();
/*
    public MovePregenter(MoviAdapter moviAdapter){
        this.moviAdapter = moviAdapter;
    }*/

    @Override
    public void start() {

    }

    @Override
    public void getMovieList(Map map) {
        Observable observable = moviModel.getMoviList(getinput());
        Function function = new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                Log.d("map",o.toString());
                return o;
            }
        };


        observable
                .map(function)
                .subscribe(getMoviListObserver());
    }




    public Observer getMoviListObserver(){
        Observer<List<Map>> moviListObserver = new Observer<List<Map>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("moviListObserver", "onSubscribe : "+d.toString() );
            }

            @Override
            public void onNext(List<Map> list) {
                Log.e("moviListObserver", "onNext : ");
                moviAdapter.setMoviList(list);

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.e("moviListObserver", "onError : "+e.toString() );
            }

            @Override
            public void onComplete() {
                Log.e("moviListObserver", "onComplete : ");
            }
        };
        return moviListObserver;
    }


    public void postLogin(Map map) {
        Observable observable = moviModel.postLogin(map);
        Function function = new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                Log.d("map",o.toString());
                return o;
            }
        };


        observable
                .map(function)
                .subscribe(postLoginObserver());
    }




    public Observer postLoginObserver(){
        Observer moviListObserver = new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("moviListObserver", "onSubscribe : "+d.toString() );
            }

            @Override
            public void onNext(Object obj) {
                Log.e("moviListObserver", "onNext : ");
                appData.edit().putString("movi-user-info",gson.toJson(obj).toString());
                appData.edit().apply();
                getMovieList(new HashMap());

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.e("moviListObserver", "onError : "+e.toString() );
            }

            @Override
            public void onComplete() {
                Log.e("moviListObserver", "onComplete : ");
            }
        };
        return moviListObserver;
    }








    public Map<String, String> getinput() {


        Map<String, String> map = new HashMap<>();

        /*날씨 전송데이터값 종료 */
        return map;
    }


    public RecyclerView.Adapter getMoviAdapter() {
        return this.moviAdapter;
    }

    public void setMoviAdapter( RecyclerView.Adapter moviAdapter ) {
         this.moviAdapter = (MoviAdapter)moviAdapter;
    }

}
