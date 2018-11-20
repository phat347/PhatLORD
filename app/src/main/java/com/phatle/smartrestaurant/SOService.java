package com.phatle.smartrestaurant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

public interface SOService {

    @GET("/api/json/get/EyukwNPTr")
    Observable<List<RestaurantDrawerItem>> getAnswers();

    @GET("/api/json/get/4kWs-in6H")
    Observable<List<RestaurantDrawerItem>> getBookmarkAnswers();
}
