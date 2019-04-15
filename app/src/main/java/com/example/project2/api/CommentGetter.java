package com.example.project2.api;

import java.util.List;

import retrofit2.Call;

public abstract class CommentGetter extends BaseAsyncGetter<List<Comment>> {
    private int ID;

    public CommentGetter(int ID) {
        this.ID = ID;
    }

    @Override
    protected Call<List<Comment>> getCall() {
        CommentAPI api = RetrofitCallCreator.getInstance().createService(CommentAPI.class);
        return api.getComment(ID);
    }
}