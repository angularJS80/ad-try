package com.example.jcompia.tutoralnavi3.govweather;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.jcompia.tutoralnavi3.govweather.data.WetherSpcnwsInfoServiceVO;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jun on 2017-11-26.
 */

public class ApiConnector {
    public static final String baseURL ="http://newsky2.kma.go.kr";

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static void helloworld(final Context context){
       //WetherSpcnwsInfoService/WeatherInformation?
        // fromTmFc=20171126&toTmFc=20171126&numOfRows=1&pageSize=1&pageNo=1&startPage=1&stnId=108&_type=json


        //final String serviceKey= context.getResources().getString(R.string.gov_weather_api_key);
        final String serviceKey= "hOoqoTjEflU73a4GVB%2FWraajQopg6BxoSZQ6Ie6OMIBG%2FaUoktc7ep2jDZhsJVFHI62DzbqG7pnPbdPauLuM7g%3D%3D";
        final String fromTmFc = "20171203";
        final String toTmFc = "20171203";
        final int numOfRows = 1;
        final int pageNo = 1;
        final int pageSize = 1;
        final int startPage = 1;
        final String stnId = "108";
        final String _type ="json";

        new Thread(new Runnable() {
            @Override
            public void run() {
                API api = retrofit.create(API.class);
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
                URL uri = null;

                Call<WetherSpcnwsInfoServiceVO> call = api.getWeatherInformation(map);
                try {
                    Log.e("바뒤", "바뒤 : "+call.request().toString());
                    final String str = call.execute().body().getResponse().getBody().getItems().getItem().getT1();
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
