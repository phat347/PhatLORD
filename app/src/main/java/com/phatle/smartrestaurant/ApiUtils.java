package com.phatle.smartrestaurant;

public class ApiUtils {

    public static final String BASE_URL = "https://next.json-generator.com";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
