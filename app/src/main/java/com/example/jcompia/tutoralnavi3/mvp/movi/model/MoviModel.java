package com.example.jcompia.tutoralnavi3.mvp.movi.model;

import android.app.TaskStackBuilder;
import android.renderscript.RenderScript;

import com.example.jcompia.tutoralnavi3.govweather.APIRx;
import com.example.jcompia.tutoralnavi3.govweather.data.WetherSpcnwsInfoServiceVO;
import com.example.jcompia.tutoralnavi3.mvp.movi.imp.ApiRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yongbeom on 2018. 5. 12..
 */

public class MoviModel {
    final String baseURL ="http://211.249.60.229:38080/api/";


    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request newRequest  = chain.request().newBuilder()
                    .addHeader("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YTkyNTA4Y2NmNTkxZDRlN2MwMTE3OTkiLCJ1c2VybmFtZSI6IjEiLCJwYXNzd29yZCI6IjEiLCJfX3YiOjAsImlhdCI6MTUyNjk5NjIyNywiZXhwIjoxNTI2OTk3NjY3fQ.dBzcC7bqlYYFsif7XhG2eRoZSPm6Z0o3SNaEr7jbc1c" )
                    .addHeader("Content-Type", "application/json")

                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
    final ApiRequest apiRequest = retrofit.create(ApiRequest.class);



    public Observable getMoviList(Map map){
        //Map<String, String> map = new HashMap<>();
        Observable<List<Map>> observable =  apiRequest.getMoviList(map).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

}
