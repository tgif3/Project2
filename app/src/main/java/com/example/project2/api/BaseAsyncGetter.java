package com.example.project2.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alireza on 4/24/17.
 * Heyyyyy
 */

public abstract class BaseAsyncGetter<T> implements Callback<T> {

    public void applyAsync() {
        Call<T> call = getCall();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            beat(response.body());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        fail();
    }

    protected abstract Call<T> getCall();

    public abstract void beat(T body);

    public abstract void fail();
}
