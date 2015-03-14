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
import com.google.gson.reflect.TypeToken;
import io.bxbxbai.zhuanlan.bean.Post;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建一个用于获取gson 的request，向这个类传入Http请求方式，url，需要解析的类
 * @author bxbxbai
 */
public class GsonRequest<T> extends Request<T> {
    private static final String TAG = "GsonRequest";

    private Gson GSON = new Gson();

    private final Class<T> mClazz;
    private Type type;

    private Listener mListener;
    private Map<String, String> mHeader;

    public GsonRequest(String url, ErrorListener errorListener) {
        this(Method.GET, url, null, null, null, errorListener);
        type = new TypeToken<List<Post>>() { }.getType();
    }

    public GsonRequest(String url, Class clazz, Listener<T> listener, ErrorListener errorListener) {
        this(Method.GET, url, clazz, null, listener, errorListener);
    }

    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> header,
                       Listener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        mClazz = clazz;
        mListener = listener;
        mHeader = header;
    }

    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> header,
                       ErrorListener errorListener) {
        this(method, url, clazz, header, null, errorListener);
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            if (type != null) {
                return Response.success(GSON.fromJson(json, type),
                        HttpHeaderParser.parseCacheHeaders(response));
            } else {
                return Response.success(GSON.fromJson(json, mClazz),
                        HttpHeaderParser.parseCacheHeaders(response));
            }
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
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    public void setSuccessListener(Listener listener) {
        mListener = listener;
    }
}
