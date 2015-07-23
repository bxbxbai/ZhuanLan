package io.bxbxbai.zhuanlan.support;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.util.LruCache;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.jakewharton.disklrucache.DiskLruCache;
import io.bxbxbai.zhuanlan.App;

import java.io.File;

/**
 * 实现一个简单的在内存中的图片LruCache，创建一个Cache 的时候需要设定这个Cache 的最大容量
 * 父类LruCache会根据这个大小去创建一个LinkedHashMap。<br/>
 * 这个LruCache也实现了ImageCache接口，在使用ImageLoader的时候，可以使用这个Cache去缓存图片。
 *
 * @author bxbxbai
 */
public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    /**
     * 创建一个BitmapLruCache
     * @param maxSize
     */
    public BitmapLruCache(int maxSize) {
        super(maxSize);
    }

    public BitmapLruCache() {
        this((int) (Runtime.getRuntime().maxMemory() / 1024) / 8);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return value.getByteCount();
        }
        return value.getRowBytes() * value.getHeight();
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }


    private static final String CACHE_FOLDER_NAME = "image_cache";

//    private static DiskLruCache mDiskLruCache = DiskLruCache.open(getDiskCacheDir(App.getInstance(), CACHE_FOLDER_NAME),
//            getAppVersion(App.getInstance()) , 1, 10*1024*1024);


    //该方法会判断当前sd卡是否存在，然后选择缓存地址
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
    //获得应用version号码
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
