package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.avjindersinghsekhon.minimaltodo.Main.MainFragment;

import java.util.ArrayList;
import java.util.UUID;

public class DeleteNotificationService extends IntentService {

    private ToDoFileStore toDoFileStore;
    private ArrayList<ToDoItem> mToDoItems;
    private ToDoItem mItem;

    public DeleteNotificationService() {
        super("DeleteNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        toDoFileStore = new ToDoFileStore(this, MainFragment.FILENAME);
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
        SharedPreferences sharedPreferences = getSharedPreferences(MainFragment.SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MainFragment.CHANGE_OCCURRED, true);
        editor.apply();
    }

    private void saveData() {
        try {
            toDoFileStore.saveToFile(mToDoItems);
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
            return toDoFileStore.loadFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}
