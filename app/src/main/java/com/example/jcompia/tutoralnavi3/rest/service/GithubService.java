package com.example.jcompia.tutoralnavi3.rest.service;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;




public interface GithubService {
    String SERVICE_ENDPOINT = "http://192.168.1.12:8080/hero/";

    @POST("api/authenticate")
    Observable<Map> authenticateUser(@Body Map param);

    @POST("hero/getList")
    Observable<Map> getHeroList(@Body Map param);

    /*@GET("/users/{login}") // 레스트 방식일때 아래 Path 로 매핑해준다.
    Observable<Github> getUser(@Path("login") String login);
    */
}
