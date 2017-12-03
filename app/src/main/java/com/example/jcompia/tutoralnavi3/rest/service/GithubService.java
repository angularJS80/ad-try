package com.example.jcompia.tutoralnavi3.rest.service;

import com.example.jcompia.tutoralnavi3.rest.model.Github;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;




public interface GithubService {
    String SERVICE_ENDPOINT = "http://211.249.60.229:58080/hero/";

    @POST("api/authenticate")
    Observable<Map> authenticateUser(@Body Github param);

    @POST("hero/getList")
    Observable<Github> getHeroList();

    /*@GET("/users/{login}") // 레스트 방식일때 아래 Path 로 매핑해준다.
    Observable<Github> getUser(@Path("login") String login);
    */
}
