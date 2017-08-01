package com.wordpress.lonelytripblog.bbblastshots;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wordpress.lonelytripblog.bbblastshots.data.Shot;
import com.wordpress.lonelytripblog.bbblastshots.data.ShotsProvider;


import java.util.ArrayList;
import java.util.List;

public class BbblastActivityView extends AppCompatActivity implements MVPContract.View {

    private List<Shot> shots;
    private ShotsRecyclerAdapter adapter;
    private TextView emptyView;
    private ProgressBar loadingIndicator;
    private SwipeRefreshLayout swipe;
    private MVPContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbblast);
        // Right now provide fake shots in mock folder.
        // Should be removed when model will be developed.
        mPresenter = new LogicHandler(this, new ShotsProvider());
        emptyView = (TextView) findViewById(R.id.emptyView);
        loadingIndicator = (ProgressBar) findViewById(R.id.load_in_progress);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.shots);
        shots = new ArrayList<>();
        adapter = new ShotsRecyclerAdapter(shots);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.updateShots();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.requestShots();
    }

    @Override
    public void showShots(List<Shot> shots) {
        this.shots.clear();
        this.shots.addAll(shots);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingState() {
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingState() {
        loadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public boolean isEmptyViewShown() {
        return emptyView.isShown();
    }

    @Override
    public void hideRefreshing() {
        swipe.setRefreshing(false);
    }
}
