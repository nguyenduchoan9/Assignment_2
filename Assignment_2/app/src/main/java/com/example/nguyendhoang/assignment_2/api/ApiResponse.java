package com.example.nguyendhoang.assignment_2.api;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nguyen.D.Hoang on 10/18/2016.
 */

public class ApiResponse {

    @SerializedName("response")
    private JsonObject response;

    @SerializedName("status")
    private String status;

    public JsonObject getResponse() {
        if(null == response){
            return new JsonObject();
        }
        return response;
    }

    public String getStatus() {
        return status;
    }
}
