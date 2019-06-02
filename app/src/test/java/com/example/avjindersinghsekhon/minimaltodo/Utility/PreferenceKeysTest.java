package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.test.mock.MockResources;

import org.junit.Assert;
import org.junit.Test;

import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.*;

public class PreferenceKeysTest {

    @Test
    public void testClassNotNull() {
        Assert.assertTrue(new PreferenceKeys(new MockResources())!=null);
    }

}