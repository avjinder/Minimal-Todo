package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.app.Fragment;
import android.app.FragmentController;
import android.test.AndroidTestCase;

import com.example.avjindersinghsekhon.minimaltodo.Main.MainActivity;
import com.example.avjindersinghsekhon.minimaltodo.Main.MainFragment;

import org.junit.Assert;
import androidx.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Ignore
public class RecyclerViewEmptySupportTest extends AndroidTestCase {

    FragmentController controller;
    private ActivityController<MainActivity> activity;
    private RecyclerViewEmptySupport recyclerViewEmptySupport;

    @Before
    public void init() {
        activity =  Robolectric.buildActivity(MainActivity.class).create().start().pause().resume();


    }

    @Test
    public void assertT() {
        Assert.assertTrue(true);
    }

}