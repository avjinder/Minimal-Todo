package com.example.avjindersinghsekhon.toodle;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import fr.ganfra.materialspinner.MaterialSpinner;

public class AddToDoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private MaterialSpinner mDateSpinner;
    private Date mLastEdited;
    private EditText mToDoTextBodyEditText;
    private SwitchCompat mToDoDateSwitch;
    private TextView mLastSeenTextView;
    private LinearLayout mUserDateSpinnerContainingLinearLayout;
    private MaterialSpinner mTimeSpinner;
    private ArrayAdapter<CharSequence> mDateAdaper;
    private ArrayAdapter<CharSequence> mTimeAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_to_do_layout);

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

        String lastSeen = formatDate(DATE_FORMAT, mLastEdited);
        mLastSeenTextView.setText(String.format(getResources().getString(R.string.last_edited), lastSeen));

        setEnterDateLayoutVisible(mToDoDateSwitch.isChecked());

        mToDoDateSwitch.setChecked(mUserHasReminder);
        mToDoDateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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




        mDateAdaper = ArrayAdapter.createFromResource(this, R.array.date_options,R.layout.date_spinner_item);
        mTimeAdapter = ArrayAdapter.createFromResource(this, R.array.time_options,R.layout.date_spinner_item);

//        String[] date = getResources().getStringArray(R.array.date_options);
//        String[] time = getResources().getStringArray(R.array.time_options);

        mDateAdaper.setDropDownViewResource(R.layout.date_dropdown_item);
        mTimeAdapter.setDropDownViewResource(R.layout.date_dropdown_item);

        mDateSpinner = (MaterialSpinner)findViewById(R.id.toDoDateSpinner);
        mTimeSpinner = (MaterialSpinner)findViewById(R.id.toDoTimeSpinner);

//
        mDateSpinner.setAdapter(mDateAdaper);
        mTimeSpinner.setAdapter(mTimeAdapter);

        mDateSpinner.setSelection(0);
        mTimeSpinner.setSelection(0);

        mDateSpinner.setOnItemSelectedListener(this);
        mTimeSpinner.setOnItemSelectedListener(this);




    }

    public void makeResult(){
        Intent i = new Intent();
        mUserToDoItem.setToDoText(mUserEnteredText);
        mUserToDoItem.setHasReminder(mUserHasReminder);
        mUserToDoItem.setToDoDate(mUserReminderDate);
        mUserToDoItem.setTodoColor(mUserColor);
        i.putExtra(MainActivity.TODOITEM, mUserToDoItem);
        setResult(RESULT_OK, i);
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

