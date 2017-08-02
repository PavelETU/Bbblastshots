package com.wordpress.lonelytripblog.bbblastshots.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shots concrete provider. Responsible for updating shots, database.
 * Corresponds model in MVP pattern.
 */

public class ShotsProvider implements ProviderInterface {

    private List<Shot> shots;
    private ProviderInterface internetProvider;

    public ShotsProvider() {
        internetProvider = new InternetProvider();
    }


    @Override
    public void getShots(LoadShotsCallback callback) {
        internetProvider.getShots(callback);
    }

    @Override
    public void getNewShots(LoadShotsCallback callback) {
        internetProvider.getNewShots(callback);
    }

}
