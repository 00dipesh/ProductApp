package com.practise.productapp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

public class Constant {

    private static final String BASE_URL = "https://dummyjson.com/";

    public static WebServices getWebService() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(7000, TimeUnit.SECONDS);
        okHttpClient.readTimeout(7000, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(7000, TimeUnit.SECONDS);

        return new retrofit2.Retrofit.Builder()                                   // Retrofit client.
                .baseUrl(BASE_URL)                                       // Base domain URL.
                .addConverterFactory(GsonConverterFactory.create())     // Added converter factory.
                .client(okHttpClient.build())
                .build()                                                // Build client.
                .create(WebServices.class);
    }
}
