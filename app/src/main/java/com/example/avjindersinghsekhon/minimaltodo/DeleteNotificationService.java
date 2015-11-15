package com.example.avjindersinghsekhon.minimaltodo;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.UUID;

public class DeleteNotificationService extends IntentService {

    private StoreRetrieveData storeRetrieveData;
    private ArrayList<ToDoItem> mToDoItems;
    private ToDoItem mItem;

    public DeleteNotificationService(){
        super("DeleteNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        storeRetrieveData = new StoreRetrieveData(this, MainActivity.FILENAME);
        UUID todoID = (UUID)intent.getSerializableExtra(TodoNotificationService.TODOUUID);

        mToDoItems = loadData();
        if(mToDoItems!=null){
            for(ToDoItem item : mToDoItems){
                if(item.getIdentifier().equals(todoID)){
                    mItem = item;
                    break;
                }
            }

            if(mItem!=null){
                mToDoItems.remove(mItem);
                dataChanged();
                saveData();
            }

        }

    }

    private void dataChanged(){
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MainActivity.CHANGE_OCCURED, true);
        editor.apply();
    }

    private void saveData(){
        try{
            storeRetrieveData.saveToFile(mToDoItems);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveData();
    }

    private ArrayList<ToDoItem> loadData(){
        try{
            return storeRetrieveData.loadFromFile();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }
}
