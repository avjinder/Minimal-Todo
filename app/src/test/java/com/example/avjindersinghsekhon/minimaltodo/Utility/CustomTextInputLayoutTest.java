package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.TypedArray;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomTextInputLayoutTest extends AndroidJUnitRunner {

    private Context context = new MockContext();

    @Before
    public void setUp() throws Exception {


        layout = new CustomTextInputLayout(context);

        assertNotNull(context);

    }
    private static final int[] APPCOMPAT_CHECK_ATTRS = { android.support.design.R.attr.colorPrimary };
    CustomTextInputLayout layout;
    @Test
    @Ignore
    public void addView() {
        TypedArray typedArray = mock(TypedArray.class);

//        when(context.obtainStyledAttributes(APPCOMPAT_CHECK_ATTRS)).thenReturn(typedArray);
        when(typedArray.hasValue(0)).thenReturn(true);
        typedArray.recycle();
        String hint = "hint";
        EditText v = mock(EditText.class);
        ActionBar.LayoutParams layoutParams = mock(ActionBar.LayoutParams.class);
        when(v.getHint()).thenReturn(hint);
        layout.addView(v,0,layoutParams);
        Assert.assertEquals(layout.getmHint(),hint);
    }
}