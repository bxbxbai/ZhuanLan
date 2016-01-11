package io.bxbxbai.zhuanlan.core;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by xuebin on 15/12/16.
 */
public abstract class SimpleCallback<T> implements Callback<T> {

    @Override
    public final void onResponse(Response<T> response, Retrofit retrofit) {
        if (response.isSuccess()) {
            onResponse(response.body(), response.code(), response.message());
        } else {
            onResponseFail();
        }
    }

    @Override
    public void onFailure(Throwable t) {

    }

    /**
     * on response return
     * @param result result
     * @param code http code
     * @param msg http msg
     */
    public abstract void onResponse(final T result, int code, String msg);

    public void onResponseFail() {

    }
}
