package io.bxbxbai.zhuanlan;

import android.app.Application;
import android.view.Choreographer;

import com.facebook.stetho.Stetho;

import io.bxbxbai.common.StopWatch;
import io.bxbxbai.common.Tips;
import io.bxbxbai.zhuanlan.core.DataCenter;

/**
 *
 * @author bxbxbai
 */
public class ZhuanlanApplication extends Application {
    private static ZhuanlanApplication mContext;

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
        Tips.init(this);
        DataCenter.init(this, "zhuanlan.db");

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

    private void initStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

    public static ZhuanlanApplication getInstance() {
        return mContext;
    }
}
