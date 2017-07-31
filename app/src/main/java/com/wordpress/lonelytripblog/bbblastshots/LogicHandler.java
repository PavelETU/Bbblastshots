package com.wordpress.lonelytripblog.bbblastshots;

import com.wordpress.lonelytripblog.bbblastshots.data.ProviderInterface;
import com.wordpress.lonelytripblog.bbblastshots.data.Shot;

import java.util.List;

/**
 * Business logic goes here.
 * Class corresponds to presenter in MVP pattern.
 */

public class LogicHandler implements MVPContract.Presenter {
    private MVPContract.View mView;
    private ProviderInterface mProvider;

    public LogicHandler(MVPContract.View mView, ProviderInterface mProvider) {
        this.mView = mView;
        this.mProvider = mProvider;
    }

    @Override
    public void requestShots() {
        mView.showLoadingState();
        mProvider.getShots(new ProviderInterface.LoadShotsCallback() {
            @Override
            public void onShotsLoaded(List<Shot> shots) {
                mView.hideLoadingState();
                if (shots == null || shots.size() == 0) {
                    mView.showEmptyView();
                } else {
                    mView.showShots(shots);
                }
            }
        });
    }

    @Override
    public void updateShots() {
        mProvider.getNewShots(new ProviderInterface.LoadShotsCallback() {
            // When callback is triggered hide refreshing and check the result.
            // If we have no shots - display empty view (if it hasn't been displayed).
            // If we have shots - display them (if empty view is displayed - hide it).
            @Override
            public void onShotsLoaded(List<Shot> shots) {
                mView.hideRefreshing();
                if (shots != null && shots.size() != 0) {
                    if (mView.isEmptyViewShown()) {
                        mView.hideEmptyView();
                    }
                    mView.showShots(shots);
                } else {
                    if (!mView.isEmptyViewShown()) {
                        mView.showEmptyView();
                    }
                }
            }
        });
    }
}
