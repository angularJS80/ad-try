package com.example.jcompia.tutoralnavi3.mvp.weather;

import android.util.Log;

import com.example.jcompia.tutoralnavi3.govweather.API;
import com.example.jcompia.tutoralnavi3.govweather.APIRx;
import com.example.jcompia.tutoralnavi3.govweather.data.WetherSpcnwsInfoServiceVO;
import com.example.jcompia.tutoralnavi3.mvp.weather.imp.ITaskContract;
import com.example.jcompia.tutoralnavi3.mvp.weather.repo.WeatherRepository;
import com.example.jcompia.tutoralnavi3.mvp.weather.rest.service.WeatherAsyncTask;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskPresenter implements ITaskContract.Presenter {
    WeatherRepository weatherRepository;
    private Gson GSON = new Gson();
    String responseString;
    ITaskContract.View view;


    public TaskPresenter(ITaskContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public String getWeather() {
        String responseString = null;
        String baseURL ="http://newsky2.kma.go.kr";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api= retrofit.create(API.class);

        Call<WetherSpcnwsInfoServiceVO> call = api.getWeatherInformation(getinput());


        WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask(call);
        /*레트로핏 서버통신관련 호출대상 종료 */

        try {
            Log.e("바뒤", "바뒤 : "+call.request().toString());
            // 레토르핏 호출
             responseString = weatherAsyncTask.execute(getinput()).get();
            Log.e("결과", "결과: "+responseString );
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.showWeather(responseString);

        return responseString;


    }


    @Override
    public String getWeatherRx() {
        String baseURL ="http://newsky2.kma.go.kr";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        APIRx api= retrofit.create(APIRx.class);
        Observable<WetherSpcnwsInfoServiceVO> observable = api.getWeatherInformation(getinput())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());


        Observer<? super WetherSpcnwsInfoServiceVO> wetherObserver = new Observer<WetherSpcnwsInfoServiceVO>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("wetherObserver", "onSubscribe : "+d.toString() );
            }

            @Override
            public void onNext(WetherSpcnwsInfoServiceVO wetherSpcnwsInfoServiceVO) {
                Log.e("wetherObserver", "onNext : "+wetherSpcnwsInfoServiceVO.toString() );
                responseString = GSON.toJson(wetherSpcnwsInfoServiceVO.getResponse().getBody());

            }

            @Override
            public void onError(Throwable e) {
                Log.e("wetherObserver", "onError : "+e.toString() );
            }

            @Override
            public void onComplete() {
                Log.e("wetherObserver", "onComplete : ");
            }
        };

        Log.e("getWeatherRx", "responseString : "+responseString);
        observable.subscribe(wetherObserver);
        return responseString;
    }

    public Observable<WetherSpcnwsInfoServiceVO> getWeatherObsable() {
        String baseURL ="http://newsky2.kma.go.kr";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        APIRx api= retrofit.create(APIRx.class);
        Observable<WetherSpcnwsInfoServiceVO> observable = api.getWeatherInformation(getinput())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
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
