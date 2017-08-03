package com.wordpress.lonelytripblog.bbblastshots.data;

import com.squareup.picasso.Picasso;
import com.wordpress.lonelytripblog.bbblastshots.BbblastApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Shots concrete provider. Responsible for updating shots, database.
 * Corresponds model in MVP pattern.
 */

public class ShotsProvider implements ProviderInterface {

    // Local cache for shots
    private List<Shot> shots;
    private InternetProvider internetProvider;
    private ProviderInterface databaseProvider;
    private Realm realm;

    public ShotsProvider() {
        internetProvider = new InternetProvider();
        realm = Realm.getDefaultInstance();
        databaseProvider = new DatabaseProvider(realm);
    }

    // If local cache has no shots - request shots from database, if database empty - request from internet.
    // After getting shots from internet - save them to database and local cache.
    // After getting shots from database - save them to local cache.
    @Override
    public void getShots(final LoadShotsCallback callback) {
        if (shots == null || shots.size() == 0) {
            databaseProvider.getShots(new LoadShotsCallback() {
                @Override
                public void onShotsLoaded(List<Shot> shotsFromDB) {
                    if (shotsFromDB != null && shotsFromDB.size() != 0) {
                        callback.onShotsLoaded(shotsFromDB);
                        shots = shotsFromDB;
                    } else {
                        internetProvider.getShots(new LoadShotsCallback() {
                            @Override
                            public void onShotsLoaded(List<Shot> shotsFromInternet) {
                                if (shotsFromInternet != null && shotsFromInternet.size() != 0) {
                                    realm.beginTransaction();
                                    realm.copyToRealmOrUpdate(shotsFromInternet);
                                    realm.commitTransaction();
                                }
                                shots = shotsFromInternet;
                                callback.onShotsLoaded(shotsFromInternet);
                            }
                        });
                    }
                }
            });
        } else {
            callback.onShotsLoaded(shots);
        }
    }

    @Override
    public void getNewShots(final LoadShotsCallback callback) {
        internetProvider.setLastShots( (shots!= null && shots.size() != 0) ? shots: null);
        internetProvider.getNewShots(new LoadShotsCallback() {
            @Override
            public void onShotsLoaded(List<Shot> newShots) {
                // If user doesn't have access to the internet - result will be null,
                // If there are no new shots - newShots with size 0.
                if (newShots != null && newShots.size() != 0) {
                    // Shots to be removed - all shots minus size of the new shots
                    List<Shot> shotsToBeRemoved = shots.subList(shots.size()-1-newShots.size(), shots.size()-1);
                    //shots.removeAll(shotsToBeRemoved);
                    newShots.addAll(shots.subList(0, shots.size()-newShots.size()));
                    shots = newShots;
                    removeShots(shotsToBeRemoved);
                    RealmResults<Shot> results = realm.where(Shot.class).findAll();
                    realm.beginTransaction();
                    results.deleteAllFromRealm();
                    realm.copyToRealm(shots);
                    realm.commitTransaction();
                }
                callback.onShotsLoaded(shots);
            }
        });
    }

    public void removeShots(List<Shot> shotsToBeRemoved) {
        for (Shot shot : shotsToBeRemoved) {
            Picasso.with(BbblastApplication.getAppContext()).invalidate(shot.getUrl());
        }
    }

}
