package com.example.project2.api;


import java.util.List;

import retrofit2.Call;

public abstract class PostGetter extends BaseAsyncGetter<List<Post>> {

    @Override
    protected Call<List<Post>> getCall() {
        PostAPI api = RetrofitCallCreator.getInstance().createService(PostAPI.class);
        return api.getPost();
    }
}