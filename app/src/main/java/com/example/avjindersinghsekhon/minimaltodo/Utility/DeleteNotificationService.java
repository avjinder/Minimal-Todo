package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.avjindersinghsekhon.minimaltodo.Main.MainFragment;

import java.util.ArrayList;
import java.util.UUID;

public class DeleteNotificationService extends IntentService {

    public DeleteNotificationService(String name, StoreRetrieveData storeRetrieveData) {
        super(name);
        this.storeRetrieveData = storeRetrieveData;
        if(storeRetrieveData == null)
            storeRetrieveData = new StoreRetrieveData(this, MainFragment.FILENAME);
    }

    private StoreRetrieveData storeRetrieveData;
    private ArrayList<ToDoItem> mToDoItems;
    public SharedPreferences sharedPreferences;
    private ToDoItem mItem;

    public DeleteNotificationService() {

        this("DeleteNotificationService",null);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        UUID todoID = (UUID) intent.getSerializableExtra(TodoNotificationService.TODOUUID);

        mToDoItems = loadData();
        if (mToDoItems != null) {
            for (ToDoItem item : mToDoItems) {
                if (item.getIdentifier().equals(todoID)) {
                    mItem = item;
                    break;
                }
            }

            if (mItem != null) {
                mToDoItems.remove(mItem);
                dataChanged();
                saveData();
            }

        }

    }

    private void dataChanged() {
        if(sharedPreferences == null)
            sharedPreferences = getSharedPreferences(MainFragment.SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MainFragment.CHANGE_OCCURED, true);
        editor.apply();
    }

    private void saveData() {
        try {
            storeRetrieveData.saveToFile(mToDoItems);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveData();
    }

    private ArrayList<ToDoItem> loadData() {
        try {
            return storeRetrieveData.loadFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}
