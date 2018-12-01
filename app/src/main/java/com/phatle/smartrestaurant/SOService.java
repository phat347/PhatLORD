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

    @GET("/api/json/get/4JllAnhTB")
    Observable<List<NotificationResponse>> getNotificationAnswers();

    @GET("/api/json/get/N16jf5uCr")
    Observable<VersionResponse> getVersion();

    @GET("/api/json/get/4ykF0L9AH")
    Observable<List<NationalTeamResponse>> getNationalAnswers();

    @GET("/api/json/get/VJSJ6WsRS")
    Observable<List<MatchResponse>> getMatchAnswers();

}
