package com.example.avjindersinghsekhon.toodle;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.datetimepicker.time.RadialPickerLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddToDoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, com.android.datetimepicker.date.DatePickerDialog.OnDateSetListener, com.android.datetimepicker.time.TimePickerDialog.OnTimeSetListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{
    private Date mLastEdited;
    private EditText mToDoTextBodyEditText;
    private SwitchCompat mToDoDateSwitch;
    private TextView mLastSeenTextView;
    private LinearLayout mUserDateSpinnerContainingLinearLayout;
    private TextView mReminderTextView;
//    private MaterialSpinner mDateSpinner;
//    private MaterialSpinner mTimeSpinner;
//    private ArrayAdapter<CharSequence> mDateAdaper;
//    private ArrayAdapter<CharSequence> mTimeAdapter;
    private Button mChooseDateButton;
    private Button mChooseTimeButton;
    private ToDoItem mUserToDoItem;
    private FloatingActionButton mToDoSendFloatingActionButton;
    public static final String DATE_FORMAT = "MMM d, yyyy";
    public static final String DATE_FORMAT_MONTH_DAY = "MMM d";
    public static final String DATE_FORMAT_TIME = "H:m";

    private String mUserEnteredText;
    private boolean mUserHasReminder;
    private Toolbar mToolbar;
    private Date mUserReminderDate;
    private String mUserColor;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.new_to_do_layout);
        setContentView(R.layout.new_to_do_test);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //Get Default Drawable for up arrow, and apply custom color

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        if(upArrow!=null){
            upArrow.setColorFilter(getResources().getColor(R.color.icons), PorterDuff.Mode.SRC_ATOP);
        }

        if(getSupportActionBar()!=null){
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

        }

        mUserToDoItem = (ToDoItem)getIntent().getSerializableExtra(MainActivity.TODOITEM);

        mUserEnteredText = mUserToDoItem.getToDoText();
        mUserHasReminder = mUserToDoItem.hasReminder();
        mUserReminderDate = mUserToDoItem.getToDoDate();
        mUserColor = mUserToDoItem.getTodoColor();


        if(mUserToDoItem.getLastEdited()==null) {
            mLastEdited = new Date();
        }
        else{
            mLastEdited = mUserToDoItem.getLastEdited();
        }


        mUserDateSpinnerContainingLinearLayout = (LinearLayout)findViewById(R.id.toDoEnterDateLinearLayout);
        mToDoTextBodyEditText = (EditText)findViewById(R.id.userToDoEditText);
        mToDoDateSwitch = (SwitchCompat)findViewById(R.id.toDoHasDateSwitchCompat);
        mLastSeenTextView = (TextView)findViewById(R.id.toDoLastEditedTextView);
        mToDoSendFloatingActionButton = (FloatingActionButton)findViewById(R.id.makeToDoFloatingActionButton);
        mReminderTextView = (TextView)findViewById(R.id.newToDoDateTimeReminderTextView);

        if(mUserHasReminder){
            setReminderTextView();
        }

        if(mUserHasReminder){
//            mUserDateSpinnerContainingLinearLayout.setVisibility(View.VISIBLE);
            setEnterDateLayoutVisibleWithAnimations(true);
        }


        mToDoTextBodyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserEnteredText = s.toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mToDoTextBodyEditText.setText(mUserEnteredText);

        String lastSeen = formatDate(DATE_FORMAT, mLastEdited);
        mLastSeenTextView.setText(String.format(getResources().getString(R.string.last_edited), lastSeen));

        setEnterDateLayoutVisible(mToDoDateSwitch.isChecked());

        mToDoDateSwitch.setChecked(mUserHasReminder);
        mToDoDateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mUserHasReminder = isChecked;
                setEnterDateLayoutVisibleWithAnimations(isChecked);
            }
        });


        mToDoSendFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeResult();
                finish();
            }
        });



        mChooseDateButton = (Button)findViewById(R.id.newToDoChooseDateButton);
        mChooseTimeButton = (Button)findViewById(R.id.newToDoChooseTimeButton);

        mChooseDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = mUserToDoItem.getToDoDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                if(Build.VERSION.SDK_INT< Build.VERSION_CODES.LOLLIPOP){
                    com.android.datetimepicker.date.DatePickerDialog datePickerDialog = com.android.datetimepicker.date.DatePickerDialog.newInstance(AddToDoActivity.this, year, month, day);
                    datePickerDialog.show(getFragmentManager(), "DateFragment");
                }
                else{
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddToDoActivity.this, R.style.CustomDialog, AddToDoActivity.this, year, month, day);
                datePickerDialog.show();

                }


            }
        });

        mChooseTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = mUserToDoItem.getToDoDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                boolean is24Hour = false;
                if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
                    com.android.datetimepicker.time.TimePickerDialog timePickerDialog = com.android.datetimepicker.time.TimePickerDialog.newInstance(AddToDoActivity.this, hour, minute, is24Hour);
                    timePickerDialog.show(getFragmentManager(), "TimeFragment");

                }
                else {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddToDoActivity.this, R.style.CustomDialog, AddToDoActivity.this, hour, minute, is24Hour);
                    timePickerDialog.show();
                }
            }
        });

//        mDateAdaper = ArrayAdapter.createFromResource(this, R.array.date_options,R.layout.date_spinner_item);
//        mTimeAdapter = ArrayAdapter.createFromResource(this, R.array.time_options,R.layout.date_spinner_item);

//        String[] date = getResources().getStringArray(R.array.date_options);
//        String[] time = getResources().getStringArray(R.array.time_options);

//        mDateAdaper.setDropDownViewResource(R.layout.date_dropdown_item);
//        mTimeAdapter.setDropDownViewResource(R.layout.date_dropdown_item);
//
//        mDateSpinner = (MaterialSpinner)findViewById(R.id.toDoDateSpinner);
//        mTimeSpinner = (MaterialSpinner)findViewById(R.id.toDoTimeSpinner);
//
////
//        mDateSpinner.setAdapter(mDateAdaper);
//        mTimeSpinner.setAdapter(mTimeAdapter);
//
//        mDateSpinner.setSelection(0);
//        mTimeSpinner.setSelection(0);
//
//        mDateSpinner.setOnItemSelectedListener(this);
//        mTimeSpinner.setOnItemSelectedListener(this);




    }

    public void setReminderTextView(){
        Date date = mUserReminderDate;
        String dateString = formatDate("d MMM", date);
        String timeString = formatDate("h:m", date);
        String amPmString = formatDate("a", date);
        String finalString = String.format(getResources().getString(R.string.remind_date_and_time), dateString, timeString, amPmString);
        mReminderTextView.setText(finalString);
    }

    public void makeResult(){
        Intent i = new Intent();
        if(mUserEnteredText.length()>0){

            String capitalizedString = Character.toUpperCase(mUserEnteredText.charAt(0))+mUserEnteredText.substring(1);
            mUserToDoItem.setToDoText(capitalizedString);
        }
        else{
            mUserToDoItem.setToDoText(mUserEnteredText);
        }
        mUserToDoItem.setLastEdited(mLastEdited);
        mUserToDoItem.setHasReminder(mUserHasReminder);
        mUserToDoItem.setToDoDate(mUserReminderDate);
        mUserToDoItem.setTodoColor(mUserColor);
        i.putExtra(MainActivity.TODOITEM, mUserToDoItem);
        setResult(RESULT_OK, i);
    }

    @Override
    public void onBackPressed() {
        makeResult();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityName(this)!=null){
                    makeResult();
                    NavUtils.navigateUpFromSameTask(this);
                }
//                finish();
                return true;
            case R.id.toDoSetColorMenuItem:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static String formatDate(String formatString, Date dateToFormat){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        return simpleDateFormat.format(dateToFormat);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Internal DatePicker
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //call some method
    }

    //Internal TimePicker
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    }

    //External Library DatePicker
    @Override
    public void onDateSet(com.android.datetimepicker.date.DatePickerDialog datePickerDialog, int i, int i1, int i2) {
    }

    //External Library TimePicker
    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {

    }

    public void setEnterDateLayoutVisible(boolean checked){
        if(checked){
            mUserDateSpinnerContainingLinearLayout.setVisibility(View.VISIBLE);
        }
        else{
            mUserDateSpinnerContainingLinearLayout.setVisibility(View.INVISIBLE);
        }
    }

    public void setEnterDateLayoutVisibleWithAnimations(boolean checked){
        if(checked){
            setReminderTextView();
            mUserDateSpinnerContainingLinearLayout.animate().alpha(1.0f).setDuration(500).setListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            mUserDateSpinnerContainingLinearLayout.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    }
            );
        }
        else{
            mUserDateSpinnerContainingLinearLayout.animate().alpha(0.0f).setDuration(500).setListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mUserDateSpinnerContainingLinearLayout.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }
            );
        }

    }
}

