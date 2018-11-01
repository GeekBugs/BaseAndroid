package com.f1reking.base.base;

import android.app.Application;
import android.content.Context;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @author F1ReKing
 * @date 2018/11/1 16:27
 * @Description
 */
public class BaseLibApplication extends Application {

    private RefWatcher mRefWatcher;
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    public static RefWatcher getRefWatcher(Context context) {
        BaseLibApplication application = (BaseLibApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        mRefWatcher = LeakCanary.install(this);
    }
}
