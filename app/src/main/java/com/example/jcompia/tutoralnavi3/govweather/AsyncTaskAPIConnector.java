package com.example.jcompia.tutoralnavi3.govweather;


import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.widget.TextView;
import com.example.jcompia.tutoralnavi3.govweather.data.WetherSpcnwsInfoServiceVO;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class AsyncTaskAPIConnector extends AsyncTask<Map<String, String>, Void, String>{

    Context context;
    TextView tv;

    public AsyncTaskAPIConnector(Context context, TextView tv){
        this.context = context;
        this.tv = tv;
    }

    private static final String baseURL ="http://newsky2.kma.go.kr";
    public final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected String doInBackground(Map<String, String>... maps) {
        API api= retrofit.create(API.class);
        Call<WetherSpcnwsInfoServiceVO> call = api.getWeatherInformation(maps[0]);
        String retVal = null;
        try {
            retVal = call.execute().body().getResponse().getBody().getItems().getItem().getT1();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @MainThread
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        tv.setText(s);
    }
}
