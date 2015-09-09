package com.example.avjindersinghsekhon.toodle;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import fr.ganfra.materialspinner.MaterialSpinner;

public class AddToDoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private MaterialSpinner mDateSpinner;
    private Date mUserReminderDate;
    private EditText mToDoTextBodyEditText;
    private SwitchCompat mToDoDateSwitch;
    private TextView mLastSeenTextView;
    private LinearLayout mUserDateSpinnerContainingLinearLayout;
    private MaterialSpinner mTimeSpinner;
    private ArrayAdapter<CharSequence> mDateAdaper;
    private ArrayAdapter<CharSequence> mTimeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_to_do_layout);

        mUserDateSpinnerContainingLinearLayout = (LinearLayout)findViewById(R.id.toDoEnterDateLinearLayout);
        mToDoTextBodyEditText = (EditText)findViewById(R.id.userToDoEditText);
        mToDoDateSwitch = (SwitchCompat)findViewById(R.id.toDoHasDateSwitchCompat);
        mLastSeenTextView = (TextView)findViewById(R.id.toDoLastEditedTextView);
        setEnterDateLayoutVisible(mToDoDateSwitch.isChecked());

        mToDoDateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setEnterDateLayoutVisibleWithAnimations(isChecked);
            }
        });


        mDateAdaper = ArrayAdapter.createFromResource(this, R.array.date_options,R.layout.date_spinner_item);
        mTimeAdapter = ArrayAdapter.createFromResource(this, R.array.time_options,R.layout.date_spinner_item);

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




        Toolbar mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
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
            default:
                return super.onOptionsItemSelected(item);
        }
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

