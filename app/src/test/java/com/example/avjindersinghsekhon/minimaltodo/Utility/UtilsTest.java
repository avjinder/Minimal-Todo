package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.example.avjindersinghsekhon.minimaltodo.R;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UtilsTest {

    @Test
    public void getToolbarHeight() {
        Context context = Mockito.mock(Context.class);
        Resources.Theme theme = Mockito.mock(Resources.Theme.class);
        TypedArray typedArray = Mockito.mock(TypedArray.class);
        when(context.getTheme()).thenReturn(theme);
        when(theme.obtainStyledAttributes(new int[]{R.attr.actionBarSize})).thenReturn(typedArray);
        when(typedArray.getDimension(0,0)).thenReturn(2.0f);
        int i = Utils.getToolbarHeight(context);
        Assert.assertEquals(i,2);
    }
}