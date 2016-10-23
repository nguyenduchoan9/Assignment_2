package com.example.nguyendhoang.assignment_2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nguyen.D.Hoang on 10/18/2016.
 */

public class ArticleSearchResult {

    @SerializedName("docs")
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }
}
