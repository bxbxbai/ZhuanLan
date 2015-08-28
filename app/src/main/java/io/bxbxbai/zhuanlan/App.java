package io.bxbxbai.zhuanlan;

import android.app.Application;
import android.view.Choreographer;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import io.bxbxbai.common.StopWatch;
import io.bxbxbai.common.T;
import io.bxbxbai.common.core.RequestManager;
import io.bxbxbai.zhuanlan.core.db.ZhuanlanDataSource;

/**
 *
 * @author bxbxbai
 */
public class App extends Application {
    private static App mContext;

    public static final String PACKAGE_NAME = "io.bxbxbai.zhuanlan";

    private RefWatcher mRefWatcher;
    /**
     * 开发测试模式
     */
    private static final boolean DEVELOPER_MODE = true;

    private static final Choreographer.FrameCallback FRAME_CALLBACK = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long frameTimeNanos) {
            StopWatch.log("doFrame: " + frameTimeNanos);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mRefWatcher = LeakCanary.install(this);
        RequestManager.init(this);
        T.init(this);
        ZhuanlanDataSource dataSource = new ZhuanlanDataSource(getApplicationContext());
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

        Choreographer choreographer = Choreographer.getInstance();
        choreographer.postFrameCallback(FRAME_CALLBACK);
    }

    public static RefWatcher getRefWatcher() {
        return getInstance().mRefWatcher;
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
