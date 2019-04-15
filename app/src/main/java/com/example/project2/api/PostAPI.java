package com.example.project2.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

interface PostAPI {
    @GET("/posts/")
    Call<List<Post>> getPost();
}
