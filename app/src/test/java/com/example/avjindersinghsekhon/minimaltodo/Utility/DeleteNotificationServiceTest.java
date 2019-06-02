package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.test.mock.MockContext;

import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultActivity;
import com.example.avjindersinghsekhon.minimaltodo.Main.MainFragment;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import androidx.annotation.TransitionRes;
import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteNotificationServiceTest {

    private DeleteNotificationService deleteNotificationService;
    StoreRetrieveData mock = mock(StoreRetrieveData.class);

    @Rule
    public ExpectedException expectedException;
    @Before
    public void init() {
        deleteNotificationService = new DeleteNotificationService("name",mock);
    }


    @Test
    public void onHandleIntent() throws IOException, JSONException {
        ToDoItem item = new ToDoItem("item1",true,new Date());
        ArrayList<ToDoItem> items = new ArrayList<>();
        items.add(item);
        when(mock.loadFromFile()).thenReturn(items);
        Intent intent = mock(Intent.class);
        deleteNotificationService.onHandleIntent(intent);
    }

    @Test
    public void onHandleIntentExceptionHandled() throws IOException, JSONException {
        ToDoItem item = new ToDoItem("item1",true,new Date());
        ArrayList<ToDoItem> items = new ArrayList<>();
        items.add(item);
        when(mock.loadFromFile()).thenThrow(RuntimeException.class);
        Intent intent = mock(Intent.class);
        deleteNotificationService.onHandleIntent(intent);
    }

    @Test
    public void datachangedTest() throws IOException, JSONException {
        deleteNotificationService.onDestroy();
    }
    @Test
    public void testDefaultConstructor() throws IOException, JSONException {
        Assert.assertTrue(new DeleteNotificationService()!=null);
    }

    @Test
    public void testId() throws IOException, JSONException {
        SharedPreferences sharedPreferences = mock(SharedPreferences.class);
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        UUID id = new UUID(2,3) ;
        ToDoItem item = new ToDoItem("item1",true,new Date());
        ArrayList<ToDoItem> items = new ArrayList<>();
        item.mTodoIdentifier = id;
        items.add(item);
        when(mock.loadFromFile()).thenReturn(items);
        Intent intent = mock(Intent.class);
        deleteNotificationService.sharedPreferences = sharedPreferences;
        when(sharedPreferences.edit()).thenReturn(editor);
        when(intent.getSerializableExtra(TodoNotificationService.TODOUUID)).thenReturn(id);
        deleteNotificationService.onHandleIntent(intent);
    }

    @Test()
    public void testNullIntent()
    {
        deleteNotificationService.onHandleIntent(null);
    }

}