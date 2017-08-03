package com.wordpress.lonelytripblog.bbblastshots;

import android.app.Application;
import android.content.Context;

import com.wordpress.lonelytripblog.bbblastshots.data.DatabaseProvider;
import com.wordpress.lonelytripblog.bbblastshots.data.InternetProvider;
import com.wordpress.lonelytripblog.bbblastshots.data.ShotsProvider;

import io.realm.Realm;

/**
 * Init Realm. Inject dependency in the future.
 */

public class BbblastApplication extends Application {
    private static Context context;
    private static Realm realm;
    @Override
    public void onCreate() {
        Realm.init(this);
        BbblastApplication.context = getApplicationContext();
        realm = Realm.getDefaultInstance();
        super.onCreate();
    }

    public static Context getAppContext() {
        return BbblastApplication.context;
    }

    public static ShotsProvider provideShotsProvider() {
        return ShotsProvider.getInstance(InternetProvider.getInstance(),
                DatabaseProvider.getInstance(realm), realm);
    }
}
