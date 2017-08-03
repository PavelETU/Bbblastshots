package com.wordpress.lonelytripblog.bbblastshots;

import com.wordpress.lonelytripblog.bbblastshots.data.ProviderInterface;
import com.wordpress.lonelytripblog.bbblastshots.data.Shot;
import com.wordpress.lonelytripblog.bbblastshots.data.ShotsProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for presenters logic.
 */

@RunWith(MockitoJUnitRunner.class)
public class LogicTest {


    @Mock
    private MVPContract.View mView;

    @Mock
    ProviderInterface mProvider;

    @Captor
    private ArgumentCaptor<ProviderInterface.LoadShotsCallback> captor;

    private MVPContract.Presenter mPresenter;

    @Before
    public void setupPresenter() {
        mPresenter = new LogicHandler(mView, mProvider);
    }

    // Verifying right behavior for loading shots
    @Test
    public void loadFromRepositoryAndShowInView() {

        List<Shot> allShots = new ShotsProvider().getShots();
        mPresenter.requestShots();

        // Stubbing with allShots
        verify(mProvider).getShots(captor.capture());
        captor.getValue().onShotsLoaded(allShots);

        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).showLoadingState();
        inOrder.verify(mView).hideLoadingState();
        verify(mView).showShots(allShots);

    }

    // Verifying right behavior for updating shots
    @Test
    public void updateRepositoryAndShowInView() {
        List<Shot> allShots = new ShotsProvider().getShots();
        mPresenter.updateShots();

        // Stubbing with allShots
        verify(mProvider).getNewShots(captor.capture());
        captor.getValue().onShotsLoaded(allShots);

        verify(mView).hideRefreshing();
        verify(mView).showShots(allShots);

    }

    // Test empty model
    @Test
    public void noConnectionAndNoShotsSaved() {
        mPresenter.requestShots();

        // Stubbing with no shots
        verify(mProvider).getShots(captor.capture());
        captor.getValue().onShotsLoaded(null);

        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).showLoadingState();
        inOrder.verify(mView).hideLoadingState();
        inOrder.verify(mView).showEmptyView();
        verify(mView, never()).showShots(null);
    }

    // Test empty model first and then updated model with shots
    @Test
    public void noConnectionAndNoShotsSavedAndThenConnectionAppears() {

        List<Shot> allShots = new ShotsProvider().getShots();
        mPresenter.requestShots();

        // Stubbing with no shots
        verify(mProvider).getShots(captor.capture());
        captor.getValue().onShotsLoaded(null);

        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).showLoadingState();
        inOrder.verify(mView).hideLoadingState();
        inOrder.verify(mView).showEmptyView();

        // Set true to isEmptyViewShown
        when(mView.isEmptyViewShown()).thenReturn(true);
        // Update shots and return shots this time
        mPresenter.updateShots();
        // Stubbing with all shots
        verify(mProvider).getNewShots(captor.capture());
        captor.getValue().onShotsLoaded(allShots);

        inOrder.verify(mView).hideRefreshing();
        inOrder.verify(mView).hideEmptyView();
        inOrder.verify(mView).showShots(allShots);
    }

    // Verifying right behavior for updating shots
    @Test
    public void updateRepositoryWithNoInternet() {
        mPresenter.updateShots();

        // Stubbing with allShots
        verify(mProvider).getNewShots(captor.capture());
        captor.getValue().onShotsLoaded(null);

        verify(mView).hideRefreshing();
        verify(mView).showToastWithNoInternetConnection();
        verify(mView, never()).showShots(null);
    }

    // Test toast with no new shots displays properly
    @Test
    public void updateRepositoryWithNoNewShots() {
        List<Shot> emptyList = new ArrayList<Shot>();
        mPresenter.updateShots();

        // Stubbing with allShots
        verify(mProvider).getNewShots(captor.capture());
        captor.getValue().onShotsLoaded(emptyList);

        verify(mView).hideRefreshing();
        verify(mView).showToastWithNoNewShots();
        verify(mView, never()).showShots(emptyList);
    }
}
