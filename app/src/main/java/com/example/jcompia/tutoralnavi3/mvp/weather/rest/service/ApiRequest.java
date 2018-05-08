package com.example.jcompia.tutoralnavi3.mvp.weather.rest.service;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiRequest {
    @POST("/")
    Observable<Map> getW(@Body Map param);

}
