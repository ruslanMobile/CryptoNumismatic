package com.example.cryptonumismatic.request;

import com.example.cryptonumismatic.utils.Api;
import com.example.cryptonumismatic.utils.Credentials;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private Retrofit.Builder retrofit;
    private Api api;
    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    //створення об'єкту для роботи з Retrofit
    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Credentials.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        api = retrofit.build().create(Api.class);
    }

    public Api getApi(){
        return api;
    }
}
