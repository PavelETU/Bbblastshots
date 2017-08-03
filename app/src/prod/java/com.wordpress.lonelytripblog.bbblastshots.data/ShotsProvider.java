package com.wordpress.lonelytripblog.bbblastshots.data;

import com.squareup.picasso.Picasso;
import com.wordpress.lonelytripblog.bbblastshots.BbblastApplication;

import java.util.List;


import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Shots concrete provider. Responsible for updating shots, database.
 * Corresponds model in MVP pattern.
 */


public class ShotsProvider implements ProviderInterface {

    private static ShotsProvider INSTANCE = null;

    // Local cache for shots
    private List<Shot> shots;
    private final InternetProvider internetProvider;
    private final ProviderInterface databaseProvider;
    private final Realm realm;

    private ShotsProvider(InternetProvider internetProvider,
                          ProviderInterface databaseProvider, Realm realm) {
        this.internetProvider = internetProvider;
        this.realm = realm;
        this.databaseProvider = databaseProvider;
    }

    public static ShotsProvider getInstance(InternetProvider internetProvider,
                                            ProviderInterface databaseProvider,
                                            Realm realm) {
        if (INSTANCE == null) {
            INSTANCE = new ShotsProvider(internetProvider, databaseProvider, realm);
        }
        return INSTANCE;
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
                    if (shots == null || shots.size() == 0) {
                        shots = newShots;
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(shots);
                        realm.commitTransaction();
                    } else {
                        // Shots to be removed - all shots minus size of the new shots
                        if (shots.size() == newShots.size()) {
                            removeShots(shots);
                        } else {
                            removeShots(shots.subList(shots.size() - 1 - newShots.size(), shots.size() - 1));
                        }
                        newShots.addAll(shots.subList(0, shots.size() - newShots.size()));
                        shots = newShots;
                        RealmResults<Shot> results = realm.where(Shot.class).findAll();
                        realm.beginTransaction();
                        results.deleteAllFromRealm();
                        realm.copyToRealm(shots);
                        realm.commitTransaction();
                    }
                    callback.onShotsLoaded(shots);
                } else {
                    callback.onShotsLoaded(newShots);
                }
            }
        });
    }
    // Remove pictures from cache
    public void removeShots(List<Shot> shotsToBeRemoved) {
        for (Shot shot : shotsToBeRemoved) {
            Picasso.with(BbblastApplication.getAppContext()).invalidate(shot.getUrl());
        }
    }

}
