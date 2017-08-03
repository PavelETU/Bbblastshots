package com.wordpress.lonelytripblog.bbblastshots;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;

/**
 * Init Realm. Inject dependency in the future.
 */

public class BbblastApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        BbblastApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return BbblastApplication.context;
    }
}
