package com.phatle.smartrestaurant.Retrofit2GoogleMap;

public class ApiUtils2 {
    public static final String BASE_URL = "https://maps.googleapis.com";

    public static SOService2 getSOService() {
        return RetrofitClient2.getClient(BASE_URL).create(SOService2.class);
    }
}
