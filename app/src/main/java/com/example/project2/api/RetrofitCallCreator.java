package com.example.project2.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitCallCreator {
    private final static RetrofitCallCreator INSTANCE;

    static {
        INSTANCE = new RetrofitCallCreator();
    }

    private final Retrofit.Builder builder;
    private OkHttpClient.Builder httpClient;

    private RetrofitCallCreator() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(10, TimeUnit.SECONDS);
        httpClient.readTimeout(20, TimeUnit.SECONDS);
        httpClient.addInterceptor(loggingInterceptor);

        builder = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson));
    }

    public static RetrofitCallCreator getInstance() {
        return INSTANCE;
    }

    public <S> S createService(Class<S> serviceClass) {
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
