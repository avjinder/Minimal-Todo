package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TodoNotificationServiceTest {
    public static final String TODOTEXT = "com.avjindersekhon.todonotificationservicetext";
    public static final String TODOUUID = "com.avjindersekhon.todonotificationserviceuuid";
    private TodoNotificationService todoNotificationService;

    @Before
    public void init() {
        NotificationManager manager = mock(NotificationManager.class);
        Notification notification = mock(Notification.class);
        todoNotificationService = new TodoNotificationService(manager,notification);
    }

    @Test
    public void testIntent() {
        Intent intent = mock(Intent.class);
        when(intent.getStringExtra(anyString())).thenReturn(TODOTEXT);
        todoNotificationService.onHandleIntent(intent);
    }

}