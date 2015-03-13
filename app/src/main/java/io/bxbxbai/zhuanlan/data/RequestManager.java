package io.bxbxbai.zhuanlan.data;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import io.bxbxbai.zhuanlan.App;

/**
 * 维护一个全局的Request队列，添加Request或者取消Request
 *
 * Created by baia on 14-6-5.
 */
public final class RequestManager {

    private static final RequestQueue mRequestQueue = Volley.newRequestQueue(App.getInstance());

    private RequestManager(){

    }

    public static final void addRequest(Request<?>  request, Object tag){
        if(tag != null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }

    public static final void cancelAll(Object tag ){
        mRequestQueue.cancelAll(tag);
    }

    public static final RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}
