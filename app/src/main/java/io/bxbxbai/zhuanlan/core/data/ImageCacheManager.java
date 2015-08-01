package io.bxbxbai.zhuanlan.core.data;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import io.bxbxbai.zhuanlan.App;

/**
 * ImageLoader是用于网络上通过HTTP方式去加载图片，其中他会用到一个RequestQueue，以及一个ImageCache。<br/>
 * 而ImageListener是用于ImageLoader加载图片后的回调接口，成功返回一个response该怎么处理？如果出现网络错误该怎么处理？
 * 这些都是在ImageListener的onResponse和onErrorResponse方法中处理的。
 *
 * Created by baia on 14-6-5.
 */
public final class ImageCacheManager {
    private static final String TAG = "ImageCacheManager";

    public static final int ONE_MB = 1024 * 1024;

    //内存的1/8当做Cache
    private static final int MEM_CACHE_SIZE = ONE_MB * ((ActivityManager) App.getInstance()
            .getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 8;

    private static ImageLoader sImageLoader = new ImageLoader(RequestManager.getRequestQueue(), new BitmapLruCache(MEM_CACHE_SIZE));

    private ImageCacheManager(){

    }

    public static ImageLoader.ImageContainer loadImage(String requestUrl,
                                                       ImageLoader.ImageListener listener){
        return loadImage(requestUrl, listener, 0, 0);
    }

    public static ImageLoader.ImageContainer loadImage(String url, ImageLoader.ImageListener listener,
                                                       int maxWidth, int maxHeight){
        return sImageLoader.get(url, listener, maxWidth, maxHeight);
    }

    public static ImageLoader.ImageListener getImageListener(final ImageView view,
                              final Drawable defaultImage, final Drawable errorImage){

        return new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if(response.getBitmap() != null ){
                    if(!isImmediate && defaultImage != null) {
                        //response 中有bitmap，那么创建一个渐变效果
                        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{
                                defaultImage,
                                new BitmapDrawable(App.getInstance().getResources(), response.getBitmap())
                        });
                        transitionDrawable.setCrossFadeEnabled(true);
                        view.setImageDrawable(transitionDrawable);
                        transitionDrawable.startTransition(100);
                    } else {
                        //否则直接现实response中的图片
                        view.setImageBitmap(response.getBitmap());
                    }
                } else if (defaultImage != null ){
                    //response中没有图片，那么现实默认的图片
                    view.setImageDrawable(defaultImage);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if(errorImage != null) {
                    view.setImageDrawable(errorImage);
                }
            }
        };
    }


}
