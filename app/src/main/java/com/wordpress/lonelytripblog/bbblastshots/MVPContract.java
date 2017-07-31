package com.wordpress.lonelytripblog.bbblastshots;

import com.wordpress.lonelytripblog.bbblastshots.data.Shot;

import java.util.List;

/**
 * Class describes roles of View and LogicHandler in MVP pattern.
 */

public class MVPContract {
    interface View {
        void showShots(List<Shot> shots);
        void showLoadingState();
        void hideLoadingState();
        void showEmptyView();
        void hideEmptyView();
        boolean isEmptyViewShown();
        void hideRefreshing();
    }

    interface Presenter {
        void requestShots();
        void updateShots();
    }
}
