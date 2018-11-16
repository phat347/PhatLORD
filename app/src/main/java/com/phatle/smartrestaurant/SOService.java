package com.phatle.smartrestaurant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

public interface SOService {

    @GET("/api/json/get/EyukwNPTr")
    Observable<List<RestaurantDrawerItem>> getAnswers();
}
