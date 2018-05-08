package com.example.jcompia.tutoralnavi3.mvp.weather.rest.service;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.widget.TextView;

import com.example.jcompia.tutoralnavi3.govweather.API;
import com.example.jcompia.tutoralnavi3.govweather.data.WetherSpcnwsInfoServiceVO;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;


public class WeatherAsyncTask extends AsyncTask<Map<String, String>, Void, String> {
    Call call ;
    private  Gson GSON = new Gson();

    public WeatherAsyncTask(Call call){
        this.call = call;
    }

    @Override
    protected String doInBackground(Map<String, String>... maps) {
        Object retVal = null;
        try {
            retVal = this.call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return GSON.toJson(retVal);
    }

    @MainThread
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
