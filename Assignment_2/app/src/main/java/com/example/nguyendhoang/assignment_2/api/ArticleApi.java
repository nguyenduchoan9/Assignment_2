package com.example.nguyendhoang.assignment_2.api;

import com.example.nguyendhoang.assignment_2.model.ArticleSearchResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Nguyen.D.Hoang on 10/18/2016.
 */

public interface ArticleApi {
    @GET("articlesearch.json")
    Call<ArticleSearchResult> getSearch(@QueryMap(encoded = true) Map<String, String> options);
}
