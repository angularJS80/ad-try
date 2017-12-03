package com.example.jcompia.tutoralnavi3.govweather;

import com.example.jcompia.tutoralnavi3.govweather.data.WetherSpcnwsInfoServiceVO;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by jun on 2017-11-26.
 */

public interface  API {
    @GET("/service/WetherSpcnwsInfoService/WeatherInformation")
    Call<WetherSpcnwsInfoServiceVO> getWeatherInformation(
            @QueryMap(encoded = true) Map<String, String> options
    );

}
