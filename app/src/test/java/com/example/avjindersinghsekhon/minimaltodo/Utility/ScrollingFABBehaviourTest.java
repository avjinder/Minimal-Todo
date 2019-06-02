package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.Snackbar.SnackbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.VideoView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ScrollingFABBehaviourTest
{
    Context context = (Context) mock(Context.class);
    AttributeSet mockAttributeSet = (AttributeSet) mock(AttributeSet.class);

    public ScrollingFABBehaviourTest() {}

    @Before
    public void init() { Resources.Theme theme = (Resources.Theme) mock(Resources.Theme.class);
        TypedArray mockType = (TypedArray) mock(TypedArray.class);
        when(context.getTheme()).thenReturn(theme);
        when(theme.obtainStyledAttributes(new int[] { 2130903043 })).thenReturn(mockType);
        when(Float.valueOf(mockType.getDimension(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))).thenReturn(Float.valueOf(1.0F));
        scrollingFABBehaviour = new ScrollingFABBehaviour(context, mockAttributeSet); }

    private ScrollingFABBehaviour scrollingFABBehaviour;
    @Test
    public void layoutDependsOn() {
        CoordinatorLayout mockCoord = (CoordinatorLayout) mock(CoordinatorLayout.class);
        FloatingActionButton fab = (FloatingActionButton) mock(FloatingActionButton.class);
        Snackbar.SnackbarLayout mockSnack = (Snackbar.SnackbarLayout) mock(Snackbar.SnackbarLayout.class);
        Toolbar mockTool = (Toolbar) mock(Toolbar.class);
        VideoView mockVideoView = (VideoView) mock(VideoView.class);
        Assert.assertTrue(scrollingFABBehaviour.layoutDependsOn(mockCoord, fab, mockSnack));
        Assert.assertTrue(scrollingFABBehaviour.layoutDependsOn(mockCoord, fab, mockTool));
        Assert.assertFalse(scrollingFABBehaviour.layoutDependsOn(mockCoord, fab, mockVideoView));
    }

    @Test
    public void onDependentViewChanged() {
        CoordinatorLayout mockCoord = (CoordinatorLayout) mock(CoordinatorLayout.class);
        FloatingActionButton fab = (FloatingActionButton) mock(FloatingActionButton.class);
        Snackbar.SnackbarLayout mockSnack = (Snackbar.SnackbarLayout) mock(Snackbar.SnackbarLayout.class);

        scrollingFABBehaviour.onDependentViewChanged(mockCoord,fab,mockSnack);

        Toolbar mockTool = (Toolbar) mock(Toolbar.class);
        VideoView mockVideoView = (VideoView) mock(VideoView.class);
        CoordinatorLayout.LayoutParams mockLp = mock(CoordinatorLayout.LayoutParams.class);
        when(fab.getLayoutParams()).thenReturn(mockLp);
        scrollingFABBehaviour.onDependentViewChanged(mockCoord,fab,mockTool);

    }
}
