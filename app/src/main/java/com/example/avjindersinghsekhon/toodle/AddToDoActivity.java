package com.example.avjindersinghsekhon.toodle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class AddToDoActivity extends AppCompatActivity {
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_to_do_layout);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
    }
}
