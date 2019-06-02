package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.test.mock.MockContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class StoreRetrieveDataTest {

    StoreRetrieveData storeRetrieveData;
    @Before
    public void init() {
        storeRetrieveData = new StoreRetrieveData(new MockContext(),"filename");
    }

    @Test
    public void toJSONArray() throws JSONException {
        ToDoItem item = new ToDoItem("test",true,new Date());
        ArrayList<ToDoItem> items = new ArrayList<>();
        items.add(item);
        JSONArray array = StoreRetrieveData.toJSONArray(items);
        Assert.assertNotNull(array);
    }

//    @Test
//
//    public void saveToFile() throws IOException, JSONException {
//        ToDoItem item = new ToDoItem("test",true,new Date());
//        ArrayList<ToDoItem> items = new ArrayList<>();
//        items.add(item);
//        storeRetrieveData.saveToFile(items);
//    }

    @Test
    public void loadFromFile() {
    }
}