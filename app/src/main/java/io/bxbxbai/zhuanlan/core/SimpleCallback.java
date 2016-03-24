package io.bxbxbai.zhuanlan.core;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xuebin on 15/12/16.
 */
public abstract class SimpleCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onResponse(response.body(), response.code(), response.message());
        } else {
            onResponseFail();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }

    /**
     * on response return
     *
     * @param result result
     * @param code   http code
     * @param msg    http msg
     */
    public abstract void onResponse(final T result, int code, String msg);

    public void onResponseFail() {

    }
}
