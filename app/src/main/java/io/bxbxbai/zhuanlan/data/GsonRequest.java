package io.bxbxbai.zhuanlan.data;

import android.util.Log;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 创建一个用于获取gson 的request，向这个类传入Http请求方式，url，需要解析的类
 * Created by baia on 14-6-4.
 */
public class GsonRequest<T> extends Request<T> {
    private static final String TAG = "GsonRequest";

    private final Gson mGson = new Gson();
    private final Class<T> mClazz;
    private final Listener mListener;
    private final Map<String, String> mHeader;

    public GsonRequest(String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
        this(Method.GET, url, clazz, null, listener, errorListener);
    }

    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> header,
                       Listener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        mClazz = clazz;
        mListener = listener;
        mHeader = header;
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(json, mClazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        }catch (UnsupportedEncodingException e){
            Log.e(TAG, e.getMessage(), e);
            return Response.error(new ParseError(e));
        }catch (JsonSyntaxException e){
            Log.e(TAG, e.getMessage(), e);
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
