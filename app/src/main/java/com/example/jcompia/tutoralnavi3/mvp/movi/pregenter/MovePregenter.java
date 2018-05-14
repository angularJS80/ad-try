package com.example.jcompia.tutoralnavi3.mvp.movi.pregenter;

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
    final MoviModel moviModel = new MoviModel();
    MoviAdapter moviAdapter;

    public MovePregenter(MoviAdapter moviAdapter){
        this.moviAdapter = moviAdapter;
    }

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


    public Map<String, String> getinput(){
        /*날씨 전송데이터값 시작 */
        final String serviceKey= "hOoqoTjEflU73a4GVB%2FWraajQopg6BxoSZQ6Ie6OMIBG%2FaUoktc7ep2jDZhsJVFHI62DzbqG7pnPbdPauLuM7g%3D%3D";
        final String fromTmFc = "20180501";
        final String toTmFc = "20180508";
        final int numOfRows = 1;
        final int pageNo = 1;
        final int pageSize = 1;
        final int startPage = 1;
        final String stnId = "108";
        final String _type ="json";

        Map<String, String> map = new HashMap<>();
        map.put("serviceKey", serviceKey);
        map.put("fromTmFc", fromTmFc);
        map.put("toTmFc", toTmFc);
        map.put("numOfRows", numOfRows+"");
        map.put("pageSize", pageSize+"");
        map.put("pageNo", pageNo+"");
        map.put("startPage", startPage+"");
        map.put("stnId", stnId);
        map.put("_type", _type);
        /*날씨 전송데이터값 종료 */
        return map;
    }

}
