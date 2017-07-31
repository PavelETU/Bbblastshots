package com.wordpress.lonelytripblog.bbblastshots;

/**
 * Business logic goes here.
 * Class corresponds to presenter in MVP pattern.
 */

public class LogicHandler implements MVPContract.Presenter {
    private MVPContract.View mView;

    public LogicHandler(MVPContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void requestShots() {

    }

    @Override
    public void updateShots() {

    }
}
