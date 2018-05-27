package com.example.jcompia.tutoralnavi3.mvp.movi.model;

import android.app.TaskStackBuilder;
import android.content.SharedPreferences;
import android.renderscript.RenderScript;

import com.example.jcompia.tutoralnavi3.govweather.APIRx;
import com.example.jcompia.tutoralnavi3.govweather.data.WetherSpcnwsInfoServiceVO;
import com.example.jcompia.tutoralnavi3.mvp.movi.imp.ApiRequest;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

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
    private SharedPreferences appData ;
    Gson gson = new Gson();


    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Map appDataMap = new HashMap();
                    String moviUserInfo = "";
                    Map moviUserMap = new HashMap();
                    if(appData!=null){

                         moviUserInfo = appData.getString("movi-user-info","");
                         moviUserMap = gson.fromJson(moviUserInfo, HashMap.class);
                    }

                    String token = (String) moviUserMap.get("token");
                    {"error":false,"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YTkyNTA4Y2NmNTkxZDRlN2MwMTE3OTkiLCJ1c2VybmFtZSI6IjEiLCJwYXNzd29yZCI6IjEiLCJfX3YiOjAsImlhdCI6MTUyNzM4MjgwMywiZXhwIjoxNTI3Mzg0MjQzfQ.zqQHqcge0QQPaLeNwsk_Csf-9QN5IEhLxuwelQooFZQ"}
                    token="";


                    Request newRequest  = chain.request().newBuilder()
                            .addHeader("Authorization", token )
                            .addHeader("Content-Type", "application/json")

                            .build();
                    return chain.proceed(newRequest);
                }
            })
            .addNetworkInterceptor(new StethoInterceptor())
            .build();

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

    public Observable postLogin(Map map){
        //Map<String, String> map = new HashMap<>();
        Observable observable =  apiRequest.postLogin(map).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }



    // 설정값을 불러오는 함수
    private Map load(String key) {


        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        String jsonString = appData.getString(key, "");
        return gson.fromJson(jsonString, HashMap.class);

    }

}
