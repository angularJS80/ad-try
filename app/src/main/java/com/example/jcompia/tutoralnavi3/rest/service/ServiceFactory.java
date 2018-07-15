package com.example.jcompia.tutoralnavi3.rest.service;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     * @param clazz Java interface of the retrofit service
     * @param endPoint REST endpoint url
     * @param context
     * @param account
     * @return retrofit service with defined endpoint
     */
    public static <T> T createRetrofitService(final Class<T> clazz, final String endPoint, Context context, Account account) {

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", ""+getToken(context,account) )
                        .addHeader("Content-Type", "application/json")

                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endPoint)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        T service = retrofit .create(clazz);
        return service;
    }

    public static String getToken(Context context,Account account){
        //Account account = GenericAccountService.GetAccount();
        AccountManager accountManager = (AccountManager)  context.getSystemService(Context.ACCOUNT_SERVICE);
        String authToken = accountManager.peekAuthToken(account,"full_access");
        if(authToken==null ){
            authToken="";
        }
        /*
        AccountManagerFuture<Bundle> accountManagerFuture = accountManager.getAuthToken(account, "com.example.jcompia.tutoralnavi3.AccountType", null, (Activity) context,null, null);

        Bundle result = null;
        try {
            result = accountManagerFuture.getResult();
        } catch (OperationCanceledException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AuthenticatorException e) {
            e.printStackTrace();
        }

        String authToken =  result.getString(AccountManager.KEY_AUTHTOKEN);*/
        Log.e("authToken ", "authToken : "+authToken );
        return authToken;
    }



}
