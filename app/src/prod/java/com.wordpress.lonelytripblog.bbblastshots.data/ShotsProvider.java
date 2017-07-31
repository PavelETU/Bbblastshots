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

    public ShotsProvider() {

    }


    @Override
    public void getShots(LoadShotsCallback callback) {
    }

    @Override
    public void getNewShots(LoadShotsCallback callback) {
    }

    public List<Shot> getShots() {
        return shots;
    }

    public void setShots(List<Shot> shots) {
        this.shots = shots;
    }
}
