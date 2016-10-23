package com.example.nguyendhoang.assignment_2.util;

import com.example.nguyendhoang.assignment_2.BuildConfig;
import com.example.nguyendhoang.assignment_2.Common.Constant;
import com.example.nguyendhoang.assignment_2.api.ApiResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nguyen.D.Hoang on 10/18/2016.
 */

public class RetrofitUltils {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final Gson GSON = new Gson();

    public static Retrofit get() {
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient client() {
        return new OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor())
                .addInterceptor(responseInterceptor())
                .addInterceptor(loggingInterceptor())
                .build();
    }

    private static Interceptor responseInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                ResponseBody body = response.body();
                ApiResponse apiResponse = GSON.fromJson(body.string(), ApiResponse.class);
                body.close();

                response= response.newBuilder()
                        .body(ResponseBody.create(JSON, apiResponse.getResponse().toString()))
                        .build();
                return response;
            }
        };
    }

    private static Interceptor apiKeyInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter("api_key", BuildConfig.API_KEY)
                        .build();
                request = request.newBuilder()
                        .url(url)
                        .build();
                return chain.proceed(request);

            }
        };
    }

    private static HttpLoggingInterceptor loggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor =new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
}
