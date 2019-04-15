package com.example.project2.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface CommentAPI {
    @GET("/posts/{ID}/comments/")
    Call<List<Comment>> getComment(@Path("ID") int ID);
}
