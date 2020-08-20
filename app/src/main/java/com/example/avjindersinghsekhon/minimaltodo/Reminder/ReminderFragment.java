package com.example.avjindersinghsekhon.minimaltodo.Reminder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.avjindersinghsekhon.minimaltodo.Analytics.AnalyticsApplication;
import com.example.avjindersinghsekhon.minimaltodo.AppDefault.AppDefaultFragment;
import com.example.avjindersinghsekhon.minimaltodo.Main.MainActivity;
import com.example.avjindersinghsekhon.minimaltodo.Main.MainFragment;
import com.example.avjindersinghsekhon.minimaltodo.R;
import com.example.avjindersinghsekhon.minimaltodo.Utility.StoreRetrieveData;
import com.example.avjindersinghsekhon.minimaltodo.Utility.ToDoItem;
import com.example.avjindersinghsekhon.minimaltodo.Utility.TodoNotificationService;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import fr.ganfra.materialspinner.MaterialSpinner;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Math.abs;

public class ReminderFragment extends AppDefaultFragment {
    private TextView mtoDoTextTextView;
    private Button mRemoveToDoButton;
    private MaterialSpinner mSnoozeSpinner;
    private String[] snoozeOptionsArray;
    private StoreRetrieveData storeRetrieveData;
    private ArrayList<ToDoItem> mToDoItems;
    private ToDoItem mItem;
    public static final String EXIT = "com.avjindersekhon.exit";
    private TextView mSnoozeTextView;
    private boolean mSpinnerInitialized;
    String theme;
    AnalyticsApplication app;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        app = (AnalyticsApplication) getActivity().getApplication();
        app.send(this);

        theme = getActivity().getSharedPreferences(MainFragment.THEME_PREFERENCES, MODE_PRIVATE).getString(MainFragment.THEME_SAVED, MainFragment.LIGHTTHEME);
        if (theme.equals(MainFragment.LIGHTTHEME)) {
            getActivity().setTheme(R.style.CustomStyle_LightTheme);
        } else {
            getActivity().setTheme(R.style.CustomStyle_DarkTheme);
        }
        storeRetrieveData = new StoreRetrieveData(getContext(), MainFragment.FILENAME);
        mToDoItems = MainFragment.getLocallyStoredData(storeRetrieveData);

        ((AppCompatActivity) getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));

        mSpinnerInitialized = false;
        Intent i = getActivity().getIntent();
        UUID id = (UUID) i.getSerializableExtra(TodoNotificationService.TODOUUID);
        mItem = null;
        for (ToDoItem toDoItem : mToDoItems) {
            if (toDoItem.getIdentifier().equals(id)) {
                mItem = toDoItem;
                break;
            }
        }

        snoozeOptionsArray = getResources().getStringArray(R.array.snooze_options);
        mRemoveToDoButton = (Button) view.findViewById(R.id.toDoReminderRemoveButton);
        mtoDoTextTextView = (TextView) view.findViewById(R.id.toDoReminderTextViewBody);
        mSnoozeTextView = (TextView) view.findViewById(R.id.reminderViewSnoozeTextView);
        mSnoozeSpinner = (MaterialSpinner) view.findViewById(R.id.todoReminderSnoozeSpinner);

//        mtoDoTextTextView.setBackgroundColor(item.getTodoColor());
        mtoDoTextTextView.setText(mItem.getToDoText());

        if (theme.equals(MainFragment.LIGHTTHEME)) {
            mSnoozeTextView.setTextColor(getResources().getColor(R.color.secondary_text));
        } else {
            mSnoozeTextView.setTextColor(Color.WHITE);
            mSnoozeTextView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_snooze_white_24dp, 0, 0, 0
            );
        }

        mRemoveToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.send(this, "Action", "Todo Removed from Reminder Activity");
                mToDoItems.remove(mItem);
                changeOccurred();
                saveData();
            }
        });

        mSnoozeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!mSpinnerInitialized) {
                    mSpinnerInitialized = true;
                    return;
                }
                Date date = setNewTimeAndDate(i);
                mItem.setToDoDate(date);
                mItem.setHasReminder(true);
                Log.d("OskarSchindler", "Date Changed to: " + date);
                changeOccurred();
                saveData();
                closeApp();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, snoozeOptionsArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_text_view, snoozeOptionsArray);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        mSnoozeSpinner.setAdapter(adapter);
        mSnoozeSpinner.setSelection(mSnoozeSpinner.getCount()-1);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_reminder;
    }

    private void closeApp() {
        Intent i = new Intent(getContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        i.putExtra(EXIT, true);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainFragment.SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(EXIT, true);
        editor.apply();
        startActivity(i);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.menu_reminder, menu);
        return true;
    }

    private void changeOccurred() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainFragment.SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MainFragment.CHANGE_OCCURED, true);
//        editor.commit();
        editor.apply();
    }

    private Date setNewTimeAndDate(int spinnerPosition) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        switch (spinnerPosition){
            case 0: {
                calendar.setTime(date);
                calendar.add(Calendar.MINUTE, 10);
                break;
            }
            case 1: {
                calendar.setTime(date);
                calendar.add(Calendar.MINUTE, 30);
                break;
            }
            case 2: {
                calendar.setTime(date);
                calendar.add(Calendar.MINUTE, 60);
                break;
            }
            case 3: {
                calendar.setTime(date);
                calendar.add(Calendar.HOUR, 24);
                break;
            }
            case 4: {
                calendar.setTime(date);
                calendar.add(Calendar.HOUR, 24);
                calendar.set(Calendar.HOUR_OF_DAY, 8);
                calendar.set(Calendar.MINUTE, 0);
                break;
            }
            case 5: {
                calendar.setTime(date);
                calendar.add(Calendar.HOUR, 24);
                calendar.set(Calendar.HOUR_OF_DAY, 12);
                calendar.set(Calendar.MINUTE, 0);
                break;
            }
            case 6: {
                calendar.setTime(date);
                calendar.add(Calendar.HOUR, 24);
                calendar.set(Calendar.HOUR_OF_DAY, 18);
                calendar.set(Calendar.MINUTE, 0);
                break;
            }
            case 7: {
                calendar.setTime(date);
                calendar.add(Calendar.HOUR, 48);
                break;
            }
            case 8: {
                calendar = giveNextWeekday(Calendar.FRIDAY);
                break;
            }
            case 9: {
                calendar = giveNextWeekday(Calendar.MONDAY);
                break;
            }

            default:
                throw new IllegalStateException("Unexpected value: " + spinnerPosition);
        }

       app.send(this, "Action", "Snoozed");


        return calendar.getTime();
    }

    private int valueFromSpinner() {
        return mSnoozeSpinner.getSelectedItemPosition();
    }

    private Calendar giveNextWeekday (int weekday) {
        Calendar today = Calendar.getInstance();
        Date date = new Date();
        today.setTime(date);
        int dayOfCurrentWeek = today.get(Calendar.DAY_OF_WEEK);
        int daysUntilWeekday = abs(weekday - dayOfCurrentWeek);
        if (weekday < dayOfCurrentWeek ) daysUntilWeekday = 7 - daysUntilWeekday;
        if (daysUntilWeekday == 0) daysUntilWeekday = 7;
        Calendar nextWeekday = (Calendar)today.clone();
        nextWeekday.add(Calendar.DAY_OF_WEEK, daysUntilWeekday);
        return nextWeekday;
    }



    private void saveData() {
        try {
            storeRetrieveData.saveToFile(mToDoItems);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }


    public static ReminderFragment newInstance() {
        return new ReminderFragment();
    }
}
