package com.wordpress.lonelytripblog.bbblastshots.data;


import io.realm.Realm;

/**
 * Class for providing shots from the database.
 */


public class DatabaseProvider implements ProviderInterface {

    private static DatabaseProvider INSTANCE = null;
    Realm realm;

    public static DatabaseProvider getInstance(Realm realm) {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseProvider(realm);
        }
        return INSTANCE;
    }


    private DatabaseProvider(Realm realm) {
        this.realm = realm;
    }

    @Override
    public void getShots(LoadShotsCallback callback) {
        callback.onShotsLoaded(realm.copyFromRealm(realm.where(Shot.class).findAll()));
    }

    @Override
    public void getNewShots(LoadShotsCallback callback) {
        getShots(callback);
    }
}
