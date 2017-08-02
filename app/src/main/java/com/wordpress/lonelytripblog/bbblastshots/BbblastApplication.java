package com.wordpress.lonelytripblog.bbblastshots;

import android.app.Application;

import io.realm.Realm;

/**
 * Init Realm. Inject dependency in the future.
 */

public class BbblastApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
