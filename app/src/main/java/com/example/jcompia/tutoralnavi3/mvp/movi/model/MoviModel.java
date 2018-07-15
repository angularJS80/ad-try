package com.example.jcompia.tutoralnavi3.mvp.movi.model;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.renderscript.RenderScript;
import android.util.Log;

import com.example.jcompia.tutoralnavi3.data.AccountManagerHelper;
import com.example.jcompia.tutoralnavi3.fragment.GenericAccountService;
import com.example.jcompia.tutoralnavi3.govweather.APIRx;
import com.example.jcompia.tutoralnavi3.govweather.data.WetherSpcnwsInfoServiceVO;
import com.example.jcompia.tutoralnavi3.mvp.movi.imp.ApiRequest;
import com.facebook.login.Login;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

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

    @Inject
    public MoviModel(){

    }

    final String baseURL ="http://211.249.60.229:38080/api/";
    private SharedPreferences appData ;
    public static final String PREFS_NAME = "LoginPrefs";
    Gson gson = new Gson();
    @Inject
    AccountManagerHelper accountManagerHelper;

    AccountManager accountManager;

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

                    String token="";
                    token = getToken();

                    Request newRequest  = chain.request().newBuilder()
                            .addHeader("Authorization", token )
                            .addHeader("Content-Type", "application/json")

                            .build();
                    return chain.proceed(newRequest);
                }
            })
            .addNetworkInterceptor(new StethoInterceptor())
            .connectTimeout(1, TimeUnit.MINUTES)
            .build();

    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
    final ApiRequest apiRequest = retrofit.create(ApiRequest.class);

    public MoviModel(AccountManagerHelper accountManagerHelper) {
        this.accountManagerHelper = accountManagerHelper;
    }


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

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public String getToken(){
        accountManager= accountManagerHelper.getmAccountManager();
        Account account ;
        account = GenericAccountService.GetAccount();
        Account[] accounts = accountManager.getAccountsByType("com.example.jcompia.tutoralnavi3.AccountType");
        Log.e("Loginfragment accounts", ""+accounts.length);
        if(accounts.length>0){
            account = accounts[0];
        }
        String authToken = accountManager.peekAuthToken(account,"full_access");
        if(authToken==null ){
            authToken="";
        }
        return authToken;
    }

    public void saveToken(String token){
        Account account ;
        account = GenericAccountService.GetAccount();
        Account[] accounts = accountManager.getAccountsByType("com.example.jcompia.tutoralnavi3.AccountType");
        Log.e("Loginfragment accounts", ""+accounts.length);
        if(accounts.length>0){
            account = accounts[0];
        }

        accountManager.addAccountExplicitly(account, null, null);
        accountManager.setAuthToken(account,"full_access",token);

    }
}
