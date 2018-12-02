package com.example.avjindersinghsekhon.minimaltodo.Utility;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

public class StoreRetrieveData {
    private Context mContext;
    private static String mFileName;

    public StoreRetrieveData(Context context, String filename) {
        mContext = context;
        mFileName = filename;
    }

    public static JSONArray toJSONArray(ArrayList<ToDoItem> items) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (ToDoItem item : items) {
            JSONObject jsonObject = item.toJSON();
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    public static void saveToFolder(ArrayList<ToDoItem> items) throws JSONException, IOException {
        String state = Environment.getExternalStorageState();

        //external storage availability check
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            System.out.println("media unmounted");
            Log.e("fail", "saveToFolder: unmounted");
            return;
        }

        File file = new File(Environment.getExternalStorageDirectory(), mFileName);
        FileOutputStream outputStream;

        try {
            file.createNewFile();
            //second argument of FileOutputStream constructor indicates whether to append or create new file if one exists
            outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(toJSONArray(items).toString());
            outputStreamWriter.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void saveToFile(ArrayList<ToDoItem> items) throws JSONException, IOException {
        FileOutputStream fileOutputStream;
        OutputStreamWriter outputStreamWriter;
        fileOutputStream = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);

        outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        outputStreamWriter.write(toJSONArray(items).toString());
        outputStreamWriter.close();
        fileOutputStream.close();
    }

    public ArrayList<ToDoItem> loadFromFile() throws IOException, JSONException {
        ArrayList<ToDoItem> items = new ArrayList<>();
        BufferedReader bufferedReader = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = mContext.openFileInput(mFileName);
            StringBuilder builder = new StringBuilder();
            String line;
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            JSONArray jsonArray = (JSONArray) new JSONTokener(builder.toString()).nextValue();
            for (int i = 0; i < jsonArray.length(); i++) {
                ToDoItem item = new ToDoItem(jsonArray.getJSONObject(i));
                items.add(item);
            }


        } catch (FileNotFoundException fnfe) {
            //do nothing about it
            //file won't exist first time app is run
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }

        }
        return items;
    }

}
