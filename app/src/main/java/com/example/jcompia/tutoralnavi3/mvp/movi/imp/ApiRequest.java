package com.example.jcompia.tutoralnavi3.mvp.movi.imp;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by yongbeom on 2018. 5. 12..
 */

public interface ApiRequest {

    @POST("/api/fileList")
    Observable<List<Map>> getMoviList(@Body Map param);

    @POST("/authcate")
    Observable<Map> postLogin(@Body Map map);
}
