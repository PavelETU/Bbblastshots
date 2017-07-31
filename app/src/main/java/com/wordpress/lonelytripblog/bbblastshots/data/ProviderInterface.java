package com.wordpress.lonelytripblog.bbblastshots.data;

import java.util.List;

/**
 * Repository will provide shots to the presenter.
 * Repository is the model in MVP pattern.
 */

public interface ProviderInterface {
    interface LoadShotsCallback {
        void onShotsLoaded(List<Shot> shots);
    }
    void getShots(LoadShotsCallback callback);
    void getNewShots(LoadShotsCallback callback);
}
