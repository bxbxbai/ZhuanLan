package io.bxbxbai.zhuanlan;

import android.app.Application;
import android.view.WindowManager;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;
import io.bxbxbai.zhuanlan.data.BitmapLruCache;
import io.bxbxbai.zhuanlan.db.ZhuanlanDataSource;

/**
 *
 * @author bxbxbai
 */
public class App extends Application {
    private static App mContext;

    public static final String PACKAGE_NAME = "io.bxbxbai.zhuanlan";

    private ZhuanlanDataSource dataSource;

    /**
     * 开发测试模式
     */
    private static final boolean DEVELOPER_MODE = true;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        dataSource = new ZhuanlanDataSource(getApplicationContext());
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

        initStetho();
    }


    private void initStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

    public static App getInstance() {
        return mContext;
    }
}
