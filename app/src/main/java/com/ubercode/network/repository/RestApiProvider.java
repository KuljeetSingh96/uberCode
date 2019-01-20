package com.ubercode.network.repository;



import com.ubercode.network.api.ApiConstants;
import com.ubercode.network.api.RestService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by kuljeetsingh on 01/20/19.
 */


public class RestApiProvider {
    public static RestService getRestApi() {
        OkHttpClient okHttpClient = getOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.FLICKER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(RestService.class);
    }

    public static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(httpLoggingInterceptor);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.connectTimeout(60, TimeUnit.SECONDS);
        return builder.build();
    }
}
