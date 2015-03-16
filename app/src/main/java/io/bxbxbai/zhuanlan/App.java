package io.bxbxbai.zhuanlan;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.view.WindowManager;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import io.bxbxbai.zhuanlan.data.BitmapLruCache;
import io.bxbxbai.zhuanlan.db.DailyNewsDataSource;

/**
 * Created by baia on 14-6-4.
 */
public class App extends Application {
    private static App mContext;

    private DailyNewsDataSource dataSource;

    /** 开发测试模式 */
    private static final boolean DEVELOPER_MODE = true;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        dataSource = new DailyNewsDataSource(getApplicationContext());
        dataSource.open();

//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//        .detectDiskReads()
//        .detectDiskWrites()
//        .detectNetwork()
//        .penaltyLog()
//        .build());
//
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//        .detectActivityLeaks()
//        .detectLeakedSqlLiteObjects()
//        .penaltyLog()
//        .penaltyDeath()
//        .build());

        
    }


    public DailyNewsDataSource getDataSource() {
        return dataSource;
    }

    public static App getInstance(){
        return mContext;
    }

    private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();


    public WindowManager.LayoutParams getWindowManagerParams() {
        return wmParams;
    }

    private RequestQueue mRequestQueue;

    private ImageLoader imageLoader;

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getInstance());
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        if (imageLoader == null) {
            imageLoader = new ImageLoader(getRequestQueue(), new BitmapLruCache());
        }
        return imageLoader;
    }
}
