package com.wordpress.lonelytripblog.bbblastshots;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wordpress.lonelytripblog.bbblastshots.data.Shot;
import com.wordpress.lonelytripblog.bbblastshots.data.ShotsProvider;


import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.net.ssl.SSLContext;


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
        mPresenter = new LogicHandler(this);
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

    // Show loaded shots.
    // If some old shots got deleted - delete corresponding pictures from cache.
    @Override
    public void showShots(List<Shot> shots) {
        this.shots.removeAll(shots);
        if (this.shots.size() > 0) {
            removeShotsFromCache(this.shots);
        }
        this.shots.clear();
        this.shots.addAll(shots);
        adapter.notifyDataSetChanged();
    }

    public void removeShotsFromCache(List<Shot> shotsList) {
        for (Shot shot : shotsList) {
            Picasso.with(this).invalidate(shot.getUrl());
        }
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
