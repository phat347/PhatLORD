package com.phatle.smartrestaurant.Retrofit2GoogleMap;
import com.phatle.smartrestaurant.GoogleMapModel.GoogleMapResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import retrofit2.http.GET;
public interface SOService2 {
    @GET("/maps/api/directions/json")
    Observable<GoogleMapResponse> getAnswers(@Query("origin") String origin,@Query("destination") String destination,@Query("key") String key,@Query("language") String language);
}
